select * from _region;
select * from _district;
select * from _city;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE _region;
TRUNCATE TABLE _district;
TRUNCATE TABLE _city;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO _region (id, country_id, name) VALUES
(1, 1, 'Beograd'),
(2, 1, 'Vojvodina'),
(3, 1, 'Zapadna i Centralna Srbija'),
(4, 1, 'Istočna i Južna Srbija'),
(5, 1, 'Kosovo i Metohija');

-- Beograd (region_id = 1)
INSERT INTO _district (name, region_id) VALUES
('Grad Beograd', 1);

-- Vojvodina (region_id = 2)
INSERT INTO _district (name, region_id) VALUES
('Severnobački okrug', 2),
('Srednjobanatski okrug', 2),
('Severnobanatski okrug', 2),
('Južnobanatski okrug', 2),
('Zapadnobački okrug', 2),
('Južnobački okrug', 2),
('Sremski okrug', 2);

-- Zapadna i Centralna Srbija (region_id = 3)
INSERT INTO _district (name, region_id) VALUES
('Mačvanski okrug', 3),
('Kolubarski okrug', 3),
('Šumadijski okrug', 3),
('Pomoravski okrug', 3),
('Zlatiborski okrug', 3),
('Moravički okrug', 3),
('Raški okrug', 3),
('Rasinski okrug', 3);

-- Istočna i Južna Srbija (region_id = 4)
INSERT INTO _district (name, region_id) VALUES
('Podunavski okrug', 4),
('Braničevski okrug', 4),
('Borski okrug', 4),
('Zaječarski okrug', 4),
('Nišavski okrug', 4),
('Toplički okrug', 4),
('Pirotski okrug', 4),
('Jablanički okrug', 4),
('Pčinjski okrug', 4);

-- Kosovo i Metohija (region_id = 5)
INSERT INTO _district (name, region_id) VALUES
('Kosovski okrug', 5),
('Pećki okrug', 5),
('Prizrenski okrug', 5),
('Kosovsko-mitrovački okrug', 5),
('Kosovsko-pomoravski okrug', 5);

INSERT INTO _city (name, district_id) VALUES
('Beograd', 1);

-- Severnobački okrug (district_id = 2)
INSERT INTO _city (name, district_id) VALUES
('Bačka Topola', 2),
('Subotica', 2),
('Kula', 2),
('Ada', 2),
('Kanjiža', 2),
('Senta', 2),
('Čoka', 2);

-- Srednjobanatski okrug (district_id = 3)
INSERT INTO _city (name, district_id) VALUES
('Zrenjanin', 3),
('Novi Bečej', 3),
('Nova Crnja', 3),
('Žitište', 3),
('Sečanj', 3);

-- Severnobanatski okrug (district_id = 4)
INSERT INTO _city (name, district_id) VALUES
('Kikinda', 4),
('Ada', 4),
('Kanjiža', 4),
('Bačka Topola', 4);

-- Južnobanatski okrug (district_id = 5)
INSERT INTO _city (name, district_id) VALUES
('Pančevo', 5),
('Vršac', 5),
('Plandište', 5),
('Kovin', 5),
('Alibunar', 5);

-- Zapadnobački okrug (district_id = 6)
INSERT INTO _city (name, district_id) VALUES
('Sombor', 6),
('Apattin', 6),
('Odžaci', 6),
('Kula', 6),
('Bačka Palanka', 6);

-- Južnobački okrug (district_id = 7)
INSERT INTO _city (name, district_id) VALUES
('Novi Sad', 7),
('Beočin', 7),
('Bački Petrovac', 7),
('Vrbas', 7),
('Temerin', 7),
('Titel', 7);

-- Sremski okrug (district_id = 8)
INSERT INTO _city (name, district_id) VALUES
('Sremska Mitrovica', 8),
('Ruma', 8),
('Inđija', 8),
('Stara Pazova', 8),
('Šid', 8),
('Sremski Karlovci', 8);

-- Mačvanski okrug (district_id = 9)
INSERT INTO _city (name, district_id) VALUES
('Šabac', 9),
('Bogatić', 9),
('Koceljeva', 9),
('Loznica', 9),
('Mali Zvornik', 9);

-- Kolubarski okrug (district_id = 10)
INSERT INTO _city (name, district_id) VALUES
('Valjevo', 10),
('Lajkovac', 10),
('Mionica', 10),
('Osečina', 10),
('Ub', 10),
('Užice', 10);  -- Ubajedno s Kolubarskim, može se korigovati

-- Šumadijski okrug (district_id = 11)
INSERT INTO _city (name, district_id) VALUES
('Kragujevac', 11),
('Aranđelovac', 11),
('Topola', 11),
('Rača', 11),
('Batočina', 11);

-- Pomoravski okrug (district_id = 12)
INSERT INTO _city (name, district_id) VALUES
('Jagodina', 12),
('Paraćin', 12),
('Svilajnac', 12),
('Ćuprija', 12),
('Despotovac', 12);

-- Zlatiborski okrug (district_id = 13)
INSERT INTO _city (name, district_id) VALUES
('Užice', 13),
('Arilje', 13),
('Bajina Bašta', 13),
('Nova Varoš', 13),
('Prijepolje', 13),
('Priboj', 13),
('Požega', 13),
('Čajetina', 13);

-- Moravički okrug (district_id = 14)
INSERT INTO _city (name, district_id) VALUES
('Čačak', 14),
('Gornji Milanovac', 14),
('Ivanjica', 14),
('Lućani', 14),
('Požega', 14);

-- Raški okrug (district_id = 15)
INSERT INTO _city (name, district_id) VALUES
('Kraljevo', 15),
('Vrnjačka Banja', 15),
('Raška', 15),
('Novi Pazar', 15),
('Tutin', 15),
('Vlasotince', 15);

-- Rasinski okrug (district_id = 16)
INSERT INTO _city (name, district_id) VALUES
('Kruševac', 16),
('Aleksandrovac', 16),
('Varvarin', 16),
('Trstenik', 16),
('Ćićevac', 16);

-- Podunavski okrug (district_id = 17)
INSERT INTO _city (name, district_id) VALUES
('Smederevo', 17),
('Smederevska Palanka', 17),
('Velika Plana', 17),
('Pozarevac', 17),
('Žabari', 17);

-- Braničevski okrug (district_id = 18)
INSERT INTO _city (name, district_id) VALUES
('Požarevac', 18),
('Golubac', 18),
('Kučevo', 18),
('Petrovac na Mlavi', 18),
('Žabari', 18);

-- Borski okrug (district_id = 19)
INSERT INTO _city (name, district_id) VALUES
('Bor', 19),
('Negotin', 19),
('Kladovo', 19),
('Majdanpek', 19);

-- Zaječarski okrug (district_id = 20)
INSERT INTO _city (name, district_id) VALUES
('Zaječar', 20),
('Knjaževac', 20),
('Negotin', 20),
('Bor', 20);

-- Nišavski okrug (district_id = 21)
INSERT INTO _city (name, district_id) VALUES
('Niš', 21),
('Ražanj', 21),
('Svrljig', 21),
('Merošina', 21);

-- Toplički okrug (district_id = 22)
INSERT INTO _city (name, district_id) VALUES
('Prokuplje', 22),
('Blace', 22),
('Kuršumlija', 22),
('Žitorađa', 22);

-- Pirotski okrug (district_id = 23)
INSERT INTO _city (name, district_id) VALUES
('Pirot', 23),
('Babušnica', 23),
('Dimitrovgrad', 23),
('Pirot', 23);

-- Jablanički okrug (district_id = 24)
INSERT INTO _city (name, district_id) VALUES
('Leskovac', 24),
('Vlasotince', 24),
('Bošnjace', 24),
('Lebane', 24),
('Crna Trava', 24),
('Medveđa', 24);

-- Pčinjski okrug (district_id = 25)
INSERT INTO _city (name, district_id) VALUES
('Vranje', 25),
('Bujanovac', 25),
('Preševo', 25),
('Surdulica', 25),
('Trgovište', 25);

-- Kosovski okrug (district_id = 26)
INSERT INTO _city (name, district_id) VALUES
('Priština', 26),
('Obilić', 26),
('Gračanica', 26),
('Lipljan', 26),
('Podujevo', 26);

-- Pećki okrug (district_id = 27)
INSERT INTO _city (name, district_id) VALUES
('Peć', 27),
('Istok', 27),
('Klina', 27),
('Dečani', 27);

-- Prizrenski okrug (district_id = 28)
INSERT INTO _city (name, district_id) VALUES
('Prizren', 28),
('Suva Reka', 28),
('Dragash', 28),
('Rahovec', 28),
('Mališevo', 28);

-- Kosovsko-mitrovački okrug (district_id = 29)
INSERT INTO _city (name, district_id) VALUES
('Severna Mitrovica', 29),
('Leposavić', 29),
('Zvečan', 29),
('Zubin Potok', 29);

-- Kosovsko-pomoravski okrug (district_id = 30)
INSERT INTO _city (name, district_id) VALUES
('Gnjilane', 30),
('Vitina', 30),
('Kamenica', 30),
('Novo Brdo', 30),
('Parteš', 30);