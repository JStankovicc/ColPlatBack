#!/usr/bin/env python3
"""
Spaja newabp.sql (operativni podaci) sa referentnim geografskim podacima iz alldb.sql.
Izlaz: newabp_with_alldb_geo.sql
"""
from __future__ import annotations

import re
from pathlib import Path

ROOT = Path(__file__).resolve().parent
NEWABP = ROOT / "newabp.sql"
ALDB = ROOT / "alldb.sql"
OUT = ROOT / "newabp_with_alldb_geo.sql"
PHPMYADMIN_IMPORT = ROOT / "newabp_phpmyadmin_import.sql"

IDEMPOTENT_NOTE = """-- Idempotent uvoz: CREATE TABLE IF NOT EXISTS (ne menja postojeću strukturu tabele).
-- INSERT IGNORE: redovi u konfliktu sa PRIMARY KEY / UNIQUE se preskaču (bez dupliranja podataka).
-- Napomena: ALTER TABLE na kraju dumpa može prijaviti grešku ako indeksi već postoje — tada taj deo preskoči ručno.

"""


def make_sql_idempotent(sql: str) -> str:
    """CREATE IF NOT EXISTS + INSERT IGNORE za ponovni uvoz bez dupliranja."""
    sql = re.sub(
        r"^CREATE TABLE (?!IF NOT EXISTS)(`[^`]+`)",
        r"CREATE TABLE IF NOT EXISTS \1",
        sql,
        flags=re.MULTILINE,
    )
    sql = re.sub(
        r"^INSERT INTO ",
        "INSERT IGNORE INTO ",
        sql,
        flags=re.MULTILINE,
    )
    return sql


# Tačan marker pre ALTER blokova u phpMyAdmin dumpu (prazna linija + zaglavlje)
INDEXES_MARKER = "\n\n--\n-- Indexes for dumped tables\n--\n"


def write_phpmyadmin_import(geo_path: Path) -> None:
    """
    Varianta za phpMyAdmin: ista šema i podaci kao newabp_with_alldb_geo.sql, ali
    indeksi / AUTO_INCREMENT / FK se primenjuju kroz proceduru koja ignoriše
    greške duplog ključa (ponovni uvoz bez ručnog preskakanja ALTER bloka).
    """
    text = geo_path.read_text(encoding="utf-8")
    pos = text.find(INDEXES_MARKER)
    if pos == -1:
        raise SystemExit("Nije pronađen blok 'Indexes for dumped tables' u geo SQL-u")
    before = text[:pos]
    rest = text[pos + len(INDEXES_MARKER) :]
    commit_at = rest.find("\nCOMMIT;\n")
    if commit_at == -1:
        raise SystemExit("Nije pronađen COMMIT u geo SQL-u")
    alter_block = rest[:commit_at].lstrip("\n")
    after_commit = rest[commit_at:]

    header = """-- =============================================================================
-- ABPlat — uvoz u phpMyAdmin (ponovljiv, bez dupliranja redova)
-- Generisano iz newabp_with_alldb_geo.sql zajedno sa build_newabp_alldb_geo.py
--
-- Podaci: CREATE TABLE IF NOT EXISTS + INSERT IGNORE (postojeći PK/UNIQUE se ne dupliraju)
-- Indeksi i FK: kroz proceduru; dupli indeks/PK/FK se ignorišu (MySQL 1061, 1068, 1826)
--
-- Pre uvoza: u phpMyAdmin uključi "Enable foreign key checks" po želji; skript isključuje
-- provere tokom uvoza pa ih na kraju uključuje. Ako import prijavi grešku na DELIMITER,
-- u kartici SQL podesi delimiter na ;; ili pokreni fajl kroz mysql klijent.
-- =============================================================================

CREATE DATABASE IF NOT EXISTS `abplat` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `abplat`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
SET UNIQUE_CHECKS = 0;

"""

    proc_open = """
-- ----- Indeksi, AUTO_INCREMENT, spoljašnji ključevi (idempotentno) -----
DROP PROCEDURE IF EXISTS `_abplat_apply_indexes_fk`;
DELIMITER ;;
CREATE PROCEDURE `_abplat_apply_indexes_fk`()
BEGIN
  DECLARE CONTINUE HANDLER FOR 1061, 1068, 1826 BEGIN END;

"""
    proc_close = """
END;;
DELIMITER ;
CALL `_abplat_apply_indexes_fk`();
DROP PROCEDURE IF EXISTS `_abplat_apply_indexes_fk`;

SET FOREIGN_KEY_CHECKS = 1;
SET UNIQUE_CHECKS = 1;

"""

    out = header + before + proc_open + alter_block + "\n" + proc_close + after_commit
    PHPMYADMIN_IMPORT.write_text(out, encoding="utf-8")


def parse_triples(s: str) -> list[tuple[int, int, str]]:
    """Parsira (a, b, 'name') iz INSERT VALUES ... (bez spoljašnjih zagrada)."""
    out: list[tuple[int, int, str]] = []
    i = 0
    while i < len(s):
        if s[i] != "(":
            i += 1
            continue
        i += 1
        j = s.find(",", i)
        a = int(s[i:j])
        i = j + 1
        j = s.find(",", i)
        b = int(s[i:j])
        i = j + 1
        if s[i] != "'":
            raise ValueError(f"očekivan navodnik na poziciji {i}: {s[i : i + 30]!r}")
        i += 1
        name_chars: list[str] = []
        while i < len(s):
            if s[i] == "'":
                if i + 1 < len(s) and s[i + 1] == "'":
                    name_chars.append("'")
                    i += 2
                    continue
                break
            name_chars.append(s[i])
            i += 1
        name = "".join(name_chars)
        out.append((a, b, name))
        while i < len(s) and s[i] not in "()":
            i += 1
        if i < len(s) and s[i] == ",":
            i += 1
    return out


def extract_insert_values(aldb: str, table: str) -> str:
    m = re.search(rf"INSERT INTO `{re.escape(table)}` VALUES (.+?);", aldb, re.DOTALL)
    if not m:
        raise SystemExit(f"Nema INSERT za `{table}` u alldb.sql")
    return m.group(1).strip()


def sql_str(s: str) -> str:
    return "'" + s.replace("'", "''") + "'"


def format_geo_insert(rows: list[tuple[int, int, str]], table: str, columns: str) -> str:
    lines = [f"({a}, {b}, {sql_str(name)})" for a, b, name in rows]
    return f"INSERT INTO `{table}` ({columns}) VALUES\n" + ",\n".join(lines) + ";"


def load_alldb_text() -> str:
    raw = ALDB.read_bytes()
    # Poznata oštećenja u _city (UTF-8 nastavak zamenjen ?)
    raw = raw.replace(b"Pe\xc4?'", b"Pe\xc4\x87'")
    return raw.decode("utf-8", errors="replace")


def main() -> None:
    aldb = load_alldb_text()

    dist_inner = extract_insert_values(aldb, "_district")
    city_inner = extract_insert_values(aldb, "_city")
    region_inner = extract_insert_values(aldb, "_region")

    districts = parse_triples(dist_inner)
    cities_old = parse_triples(city_inner)
    regions = parse_triples(region_inner)

    dist_ids = {d[0] for d in districts}
    for cid, did, name in cities_old:
        if did not in dist_ids:
            raise SystemExit(f"Grad {cid} ima nepoznat district_id={did}")
    # Isti kao alldb: (id, district_id, name) — JPA City mapira districtId, ne regionId.
    city_rows: list[tuple[int, int, str]] = list(cities_old)

    district_block = f"""-- Table structure for table `_district`
--

CREATE TABLE `_district` (
  `id` int NOT NULL,
  `region_id` int NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_district`
--

{format_geo_insert(districts, "_district", "`id`, `region_id`, `name`")}

-- --------------------------------------------------------

"""

    city_block = f"""-- Table structure for table `_city`
--

CREATE TABLE `_city` (
  `id` int NOT NULL,
  `district_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_city`
--

{format_geo_insert(city_rows, "_city", "`id`, `district_id`, `name`")}

-- --------------------------------------------------------

"""

    country_block = """-- Table structure for table `_country`
--

CREATE TABLE `_country` (
  `id` smallint NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_country`
--

INSERT INTO `_country` (`id`, `name`) VALUES
(1, 'Srbija');

-- --------------------------------------------------------

"""

    region_block = f"""-- Table structure for table `_region`
--

CREATE TABLE `_region` (
  `country_id` smallint NOT NULL,
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_region`
--

{format_geo_insert(regions, "_region", "`country_id`, `id`, `name`")}

-- --------------------------------------------------------

"""

    nb = NEWABP.read_text(encoding="utf-8")

    header = (
        "-- Spoj: operativni sadržaj iz newabp.sql + referentni podaci "
        "država / regiona / okruga / gradova iz alldb.sql.\n"
        "-- _city: district_id (JPA City); hijerarhija regija → okrug → grad preko _district.region_id.\n"
        "-- _location: country_id, region_id, district_id, city (naziv), address — kao Location entitet.\n\n"
    )
    if nb.startswith("SET SQL_MODE"):
        nb = header + nb
    else:
        nb = header + nb

    nb = re.sub(
        r"(?s)-- Table structure for table `_city`.*?-- --------------------------------------------------------\n\n",
        city_block,
        nb,
        count=1,
    )

    nb = re.sub(
        r"(?s)-- Table structure for table `_country`.*?-- --------------------------------------------------------\n\n",
        country_block,
        nb,
        count=1,
    )

    nb = re.sub(
        r"(?s)-- Table structure for table `_region`.*?-- --------------------------------------------------------\n\n",
        region_block,
        nb,
        count=1,
    )

    marker = "-- Table structure for table `_support_types_change_request`"
    if marker not in nb:
        raise SystemExit(f"Marker nije pronađen: {marker}")
    nb = nb.replace(marker, district_block + marker, 1)

    location_block = """-- Table structure for table `_location`
--

CREATE TABLE `_location` (
  `id` bigint NOT NULL,
  `country_id` smallint NOT NULL,
  `region_id` int NOT NULL,
  `district_id` int NOT NULL,
  `city` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_location`
--

INSERT INTO `_location` (`id`, `country_id`, `region_id`, `district_id`, `city`, `address`) VALUES
(1, 1, 3, 15, 'Kraljevo', 'Karadjordjeva 171'),
(2, 1, 1, 1, 'Grad Beograd', 'Studentska 5');

-- --------------------------------------------------------

"""

    nb = re.sub(
        r"(?s)-- Table structure for table `_location`.*?-- --------------------------------------------------------\n\n",
        location_block,
        nb,
        count=1,
    )

    nb = nb.replace(
        """ALTER TABLE `_region`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_support_types_change_request`""",
        """ALTER TABLE `_region`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_district`
--
ALTER TABLE `_district`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_district_name` (`name`),
  ADD KEY `FK_district_region` (`region_id`);

--
-- Indexes for table `_support_types_change_request`""",
        1,
    )

    nb = nb.replace(
        """-- AUTO_INCREMENT for table `_city`
--
ALTER TABLE `_city`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;""",
        """-- AUTO_INCREMENT for table `_city`
--
ALTER TABLE `_city`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=149;""",
        1,
    )

    nb = nb.replace(
        """-- AUTO_INCREMENT for table `_region`
--
ALTER TABLE `_region`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;""",
        """-- AUTO_INCREMENT for table `_region`
--
ALTER TABLE `_region`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;""",
        1,
    )

    nb = nb.replace(
        """ALTER TABLE `_region`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `_task_note`""",
        """ALTER TABLE `_region`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `_district`
--
ALTER TABLE `_district`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `_task_note`""",
        1,
    )

    if "Idempotent uvoz:" not in nb:
        marker = "-- phpMyAdmin SQL Dump"
        if marker in nb:
            nb = nb.replace(marker, IDEMPOTENT_NOTE + marker, 1)
        else:
            nb = IDEMPOTENT_NOTE + nb
    nb = make_sql_idempotent(nb)

    OUT.write_text(nb, encoding="utf-8")
    print("Wrote", OUT, "cities:", len(city_rows), "districts:", len(districts), "regions:", len(regions))
    write_phpmyadmin_import(OUT)
    print("Wrote", PHPMYADMIN_IMPORT)


if __name__ == "__main__":
    main()
