# -*- coding: utf-8 -*-
"""Generise abplat.sql sa samo INSERT naredbama; _country, _region, _district, _city iz srbloc."""
import re
import os

SCRIPT_DIR = os.path.dirname(os.path.abspath(__file__))
ABPLAT_PATH = os.path.join(SCRIPT_DIR, "abplat.sql")
OUT_PATH = os.path.join(SCRIPT_DIR, "abplat.sql.new")
SRLOC_PATH = os.path.join(SCRIPT_DIR, "srbloc.sql")


def parse_srbloc():
    with open(SRLOC_PATH, "r", encoding="utf-8") as f:
        content = f.read()
    regions = []
    for m in re.finditer(r"\((\d+),\s*1,\s*'([^']+)'\)", content):
        regions.append((int(m.group(1)), m.group(2)))
    # Odsecamo sekciju do _city i posle _city
    before_city = content.split("INSERT INTO _city")[0]
    after_city = "INSERT INTO _city" + content.split("INSERT INTO _city", 1)[1]
    districts = []
    for m in re.finditer(r"\('([^']+)',\s*(\d+)\)", before_city):
        name, rid = m.group(1), int(m.group(2))
        districts.append((name, rid))
    cities = []
    for m in re.finditer(r"\('([^']+)',\s*(\d+)\)", after_city):
        name, did = m.group(1), int(m.group(2))
        cities.append((name, did))
    return regions, districts, cities


def extract_insert_blocks(path):
    with open(path, "r", encoding="utf-8") as f:
        lines = f.readlines()
    blocks = {}
    i = 0
    while i < len(lines):
        line = lines[i]
        if line.strip().startswith("INSERT INTO `_"):
            match = re.match(r"INSERT INTO `(_\w+)`", line)
            if match:
                table = match.group(1)
                block_lines = [line]
                i += 1
                while i < len(lines):
                    block_lines.append(lines[i])
                    if lines[i].strip().endswith(");") and not lines[i].strip().rstrip().endswith(","):
                        i += 1
                        break
                    i += 1
                blocks[table] = "".join(block_lines).rstrip()
                continue
        i += 1
    return blocks


def main():
    regions, districts, cities = parse_srbloc()
    blocks = extract_insert_blocks(ABPLAT_PATH)

    # _country
    out_country = "INSERT INTO `_country` (`id`, `name`) VALUES\n(1, 'Srbija');"

    # _region (country_id, id, name)
    out_region = "INSERT INTO `_region` (`country_id`, `id`, `name`) VALUES\n" + ",\n".join(
        f"(1, {r[0]}, '{r[1]}')" for r in regions
    ) + ";"

    # _district (id, region_id, name)
    out_district = "INSERT INTO `_district` (`id`, `region_id`, `name`) VALUES\n" + ",\n".join(
        f"({i+1}, {d[1]}, '{d[0]}')" for i, d in enumerate(districts)
    ) + ";"

    # _city (id, district_id, name)
    out_city = "INSERT INTO `_city` (`id`, `district_id`, `name`) VALUES\n" + ",\n".join(
        f"({i+1}, {c[1]}, '{c[0]}')" for i, c in enumerate(cities)
    ) + ";"

    # _location: Kraljevo je u Raškom okrugu = region 3, district 15; Beograd = region 1, district 1
    out_location = """INSERT INTO `_location` (`id`, `country_id`, `region_id`, `district_id`, `city`, `address`) VALUES
(1, 1, 3, 15, 'Kraljevo', 'Karadjordjeva 171'),
(2, 1, 1, 1, 'Grad Beograd', 'Studentska 5');"""

    order = [
        "_company", "_company_support", "_calendar_events", "_conversation",
        "_conversation_participant", "_message", "_movable_asset", "_project",
        "_project_task", "_project_task_users", "_project_users",
        "_task_note_seq", "_task_status", "_task_status_seq",
        "_user", "_user_profiles_change_request_seq", "_user_roles",
    ]

    header = """-- abplat.sql - samo ubacivanje podataka
-- _country, _region, _district, _city iz srbloc.sql

SET FOREIGN_KEY_CHECKS = 0;

"""

    parts = [header, out_country, "\n\n", out_region, "\n\n", out_district, "\n\n", out_city, "\n\n", out_location]

    for t in order:
        if t in blocks:
            parts.append("\n\n")
            parts.append(blocks[t])

    # _user_profile ima 3 odvojena INSERT bloka u abplat
    with open(ABPLAT_PATH, "r", encoding="utf-8") as f:
        text = f.read()
    for m in re.finditer(r"(INSERT INTO `_user_profile`[^;]+;)", text, re.DOTALL):
        parts.append("\n\n")
        parts.append(m.group(1).strip())

    parts.append("\n\nSET FOREIGN_KEY_CHECKS = 1;\n")

    with open(OUT_PATH, "w", encoding="utf-8") as f:
        f.write("".join(parts))

    print("Napisan", OUT_PATH)


if __name__ == "__main__":
    main()
