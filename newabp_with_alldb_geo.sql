-- Spoj: operativni sadržaj iz newabp.sql + referentni podaci država / regiona / okruga / gradova iz alldb.sql.
-- _city: district_id (JPA City); hijerarhija regija → okrug → grad preko _district.region_id.
-- _location: country_id, region_id, district_id, city (naziv), address — kao Location entitet.

-- Idempotent uvoz: CREATE TABLE IF NOT EXISTS (ne menja postojeću strukturu tabele).
-- INSERT IGNORE: redovi u konfliktu sa PRIMARY KEY / UNIQUE se preskaču (bez dupliranja podataka).
-- Napomena: ALTER TABLE na kraju dumpa može prijaviti grešku ako indeksi već postoje — tada taj deo preskoči ručno.

-- phpMyAdmin SQL Dump
-- version 5.2.1deb3
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Apr 13, 2026 at 06:27 PM
-- Server version: 8.0.45-0ubuntu0.24.04.1
-- PHP Version: 8.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `abplat`
--

-- --------------------------------------------------------

--
-- Table structure for table `_calendar_events`
--

CREATE TABLE IF NOT EXISTS `_calendar_events` (
  `created_at` datetime(6) NOT NULL,
  `created_by_user_id` bigint NOT NULL,
  `end_date_time` datetime(6) NOT NULL,
  `id` bigint NOT NULL,
  `start_date_time` datetime(6) NOT NULL,
  `team_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `priority` enum('HIGH','LOW','NORMAL','URGENT') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_calendar_events`
--

INSERT IGNORE INTO `_calendar_events` (`created_at`, `created_by_user_id`, `end_date_time`, `id`, `start_date_time`, `team_id`, `updated_at`, `description`, `title`, `priority`) VALUES
('2026-06-28 09:00:00.000000', 1, '2026-07-10 11:30:00.000000', 1, '2026-07-10 10:00:00.000000', 2, '2026-06-28 09:00:00.000000', 'Prezentacija modula magacina i dashboarda potencijalnom klijentu.', 'Demo ABPlat platforme', 'HIGH'),
('2026-07-03 14:20:00.000000', 2, '2026-07-07 10:00:00.000000', 2, '2026-07-07 09:00:00.000000', 2, '2026-07-03 14:20:00.000000', 'Planiranje zadataka za Development tim za narednu nedelju.', 'Sprint planning', 'NORMAL'),
('2026-07-15 11:00:00.000000', 1, '2026-07-22 15:00:00.000000', 3, '2026-07-22 14:00:00.000000', 1, '2026-07-15 11:00:00.000000', 'Pregled pipeline-a, leadova i aktivnih ponuda.', 'Mesečni sync prodaje', 'NORMAL'),
('2026-07-28 08:30:00.000000', 1, '2026-08-05 12:00:00.000000', 4, '2026-08-05 08:00:00.000000', NULL, '2026-07-28 08:30:00.000000', 'Fizička provera zaliha i usklađivanje sa sistemom.', 'Inventura magacina', 'URGENT'),
('2026-08-12 16:45:00.000000', 2, '2026-08-20 17:00:00.000000', 5, '2026-08-20 16:00:00.000000', 2, '2026-08-12 16:45:00.000000', 'Analiza završenih taskova i planiranje poboljšanja za sledeći sprint.', 'Retrospektiva tima', 'NORMAL');

-- --------------------------------------------------------

--
-- Table structure for table `_city`
--

CREATE TABLE IF NOT EXISTS `_city` (
  `id` int NOT NULL,
  `district_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_city`
--

INSERT IGNORE INTO `_city` (`id`, `district_id`, `name`) VALUES
(1, 1, 'Beograd'),
(2, 2, 'Bačka Topola'),
(3, 2, 'Subotica'),
(4, 2, 'Kula'),
(5, 2, 'Ada'),
(6, 2, 'Kanjiža'),
(7, 2, 'Senta'),
(8, 2, 'Čoka'),
(9, 3, 'Zrenjanin'),
(10, 3, 'Novi Bečej'),
(11, 3, 'Nova Crnja'),
(12, 3, 'Žitište'),
(13, 3, 'Sečanj'),
(14, 4, 'Kikinda'),
(15, 4, 'Ada'),
(16, 4, 'Kanjiža'),
(17, 4, 'Bačka Topola'),
(18, 5, 'Pančevo'),
(19, 5, 'Vršac'),
(20, 5, 'Plandište'),
(21, 5, 'Kovin'),
(22, 5, 'Alibunar'),
(23, 6, 'Sombor'),
(24, 6, 'Apatin'),
(25, 6, 'Odžaci'),
(26, 6, 'Kula'),
(27, 6, 'Bačka Palanka'),
(28, 7, 'Novi Sad'),
(29, 7, 'Beočin'),
(30, 7, 'Bački Petrovac'),
(31, 7, 'Vrbas'),
(32, 7, 'Temerin'),
(33, 7, 'Titel'),
(34, 8, 'Sremska Mitrovica'),
(35, 8, 'Ruma'),
(36, 8, 'Inđija'),
(37, 8, 'Stara Pazova'),
(38, 8, 'Šid'),
(39, 8, 'Sremski Karlovci'),
(40, 9, 'Šabac'),
(41, 9, 'Bogatić'),
(42, 9, 'Koceljeva'),
(43, 9, 'Loznica'),
(44, 9, 'Mali Zvornik'),
(45, 10, 'Valjevo'),
(46, 10, 'Lajkovac'),
(47, 10, 'Mionica'),
(48, 10, 'Osečina'),
(49, 10, 'Ub'),
(50, 10, 'Užice'),
(51, 11, 'Kragujevac'),
(52, 11, 'Aranđelovac'),
(53, 11, 'Topola'),
(54, 11, 'Rača'),
(55, 11, 'Batočina'),
(56, 12, 'Jagodina'),
(57, 12, 'Paraćin'),
(58, 12, 'Svilajnac'),
(59, 12, 'Ćuprija'),
(60, 12, 'Despotovac'),
(61, 13, 'Užice'),
(62, 13, 'Arilje'),
(63, 13, 'Bajina Bašta'),
(64, 13, 'Nova Varoš'),
(65, 13, 'Prijepolje'),
(66, 13, 'Priboj'),
(67, 13, 'Požega'),
(68, 13, 'Čajetina'),
(69, 14, 'Čačak'),
(70, 14, 'Gornji Milanovac'),
(71, 14, 'Ivanjica'),
(72, 14, 'Lučani'),
(73, 14, 'Požega'),
(74, 15, 'Kraljevo'),
(75, 15, 'Vrnjačka Banja'),
(76, 15, 'Raška'),
(77, 15, 'Novi Pazar'),
(78, 15, 'Tutin'),
(79, 15, 'Vlasotince'),
(80, 16, 'Kruševac'),
(81, 16, 'Aleksandrovac'),
(82, 16, 'Varvarin'),
(83, 16, 'Trstenik'),
(84, 16, 'Ćićevac'),
(85, 17, 'Smederevo'),
(86, 17, 'Smederevska Palanka'),
(87, 17, 'Velika Plana'),
(88, 17, 'Požarevac'),
(89, 17, 'Žabari'),
(90, 18, 'Požarevac'),
(91, 18, 'Golubac'),
(92, 18, 'Kučevo'),
(93, 18, 'Petrovac na Mlavi'),
(94, 18, 'Žabari'),
(95, 19, 'Bor'),
(96, 19, 'Negotin'),
(97, 19, 'Kladovo'),
(98, 19, 'Majdanpek'),
(99, 20, 'Zaječar'),
(100, 20, 'Knjaževac'),
(101, 20, 'Negotin'),
(102, 20, 'Bor'),
(103, 21, 'Niš'),
(104, 21, 'Ražanj'),
(105, 21, 'Svrljig'),
(106, 21, 'Merošina'),
(107, 22, 'Prokuplje'),
(108, 22, 'Blace'),
(109, 22, 'Kuršumlija'),
(110, 22, 'Žitorađa'),
(111, 23, 'Pirot'),
(112, 23, 'Babušnica'),
(113, 23, 'Dimitrovgrad'),
(114, 23, 'Pirot'),
(115, 24, 'Leskovac'),
(116, 24, 'Vlasotince'),
(117, 24, 'Bošnjace'),
(118, 24, 'Lebane'),
(119, 24, 'Crna Trava'),
(120, 24, 'Medveđa'),
(121, 25, 'Vranje'),
(122, 25, 'Bujanovac'),
(123, 25, 'Preševo'),
(124, 25, 'Surdulica'),
(125, 25, 'Trgovište'),
(126, 26, 'Priština'),
(127, 26, 'Obilić'),
(128, 26, 'Gračanica'),
(129, 26, 'Lipljan'),
(130, 26, 'Podujevo'),
(131, 27, 'Peć'),
(132, 27, 'Istok'),
(133, 27, 'Klina'),
(134, 27, 'Dečani'),
(135, 28, 'Prizren'),
(136, 28, 'Suva Reka'),
(137, 28, 'Dragash'),
(138, 28, 'Rahovec'),
(139, 28, 'Mališevo'),
(140, 29, 'Severna Mitrovica'),
(141, 29, 'Leposavić'),
(142, 29, 'Zvečan'),
(143, 29, 'Zubin Potok'),
(144, 30, 'Gnjilane'),
(145, 30, 'Vitina'),
(146, 30, 'Kamenica'),
(147, 30, 'Novo Brdo'),
(148, 30, 'Parteš');

-- --------------------------------------------------------

--
-- Table structure for table `_company`
--

CREATE TABLE IF NOT EXISTS `_company` (
  `number_of_profiles` int NOT NULL,
  `terms_and_conditions_accepted` bit(1) NOT NULL,
  `billing_details_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `location_id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `registry_num` varchar(255) DEFAULT NULL,
  `company_logo_pic` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_company`
--

INSERT IGNORE INTO `_company` (`number_of_profiles`, `terms_and_conditions_accepted`, `billing_details_id`, `created_at`, `id`, `location_id`, `updated_at`, `name`, `registry_num`, `company_logo_pic`) VALUES
(35, b'1', NULL, '2026-02-10 08:49:21.162591', 1, 1, '2026-02-10 08:49:21.179913', 'ABPlat', '123456789', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `_company_support`
--

CREATE TABLE IF NOT EXISTS `_company_support` (
  `company_id` bigint NOT NULL,
  `support_types` enum('CHAT','EMAIL','PHONE') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_company_support`
--

INSERT IGNORE INTO `_company_support` (`company_id`, `support_types`) VALUES
(1, 'CHAT'),
(1, 'EMAIL'),
(1, 'PHONE');

-- --------------------------------------------------------

--
-- Table structure for table `_contact`
--

CREATE TABLE IF NOT EXISTS `_contact` (
  `contacts_list_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` enum('CLOSED','CONTACTED','NEW','OFFERED','REJECTED','STALLED') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_contacts_list`
--

CREATE TABLE IF NOT EXISTS `_contacts_list` (
  `city_id` int DEFAULT NULL,
  `country_id` smallint DEFAULT NULL,
  `region_id` int DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL,
  `team_id` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` enum('CLIENT','LEAD') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_contact_message`
--

CREATE TABLE IF NOT EXISTS `_contact_message` (
  `responded` bit(1) NOT NULL,
  `seen` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `sent_by` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_conversation`
--

CREATE TABLE IF NOT EXISTS `_conversation` (
  `is_group` bit(1) NOT NULL,
  `company_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL,
  `last_message_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_conversation`
--

INSERT IGNORE INTO `_conversation` (`is_group`, `company_id`, `created_at`, `id`, `last_message_at`, `updated_at`, `name`) VALUES
(b'0', 1, '2026-02-10 08:52:54.265543', 1, '2026-06-15 14:30:22.000000', '2026-06-15 14:30:22.000000', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `_conversation_participant`
--

CREATE TABLE IF NOT EXISTS `_conversation_participant` (
  `archived` bit(1) NOT NULL,
  `muted` bit(1) NOT NULL,
  `conversation_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `joined_at` datetime(6) NOT NULL,
  `last_read_at` datetime(6) DEFAULT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_conversation_participant`
--

INSERT IGNORE INTO `_conversation_participant` (`archived`, `muted`, `conversation_id`, `id`, `joined_at`, `last_read_at`, `user_id`) VALUES
(b'0', b'0', 1, 1, '2026-02-10 08:52:54.319155', '2026-06-15 14:30:22.000000', 1),
(b'0', b'0', 1, 2, '2026-02-10 08:52:54.322455', '2026-06-15 14:30:22.000000', 2);

-- --------------------------------------------------------

--
-- Table structure for table `_country`
--

CREATE TABLE IF NOT EXISTS `_country` (
  `id` smallint NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_country`
--

INSERT IGNORE INTO `_country` (`id`, `name`) VALUES
(1, 'Srbija');

-- --------------------------------------------------------

--
-- Table structure for table `_department`
--

CREATE TABLE IF NOT EXISTS `_department` (
  `department_type` tinyint DEFAULT NULL,
  `company_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL
) ;

-- --------------------------------------------------------

--
-- Table structure for table `_event_participants`
--

CREATE TABLE IF NOT EXISTS `_event_participants` (
  `event_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `status` enum('ACCEPTED','DECLINED','INVITED','NO_RESPONSE','TENTATIVE') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_location`
--

CREATE TABLE IF NOT EXISTS `_location` (
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

INSERT IGNORE INTO `_location` (`id`, `country_id`, `region_id`, `district_id`, `city`, `address`) VALUES
(1, 1, 3, 15, 'Kraljevo', 'Karadjordjeva 171'),
(2, 1, 1, 1, 'Grad Beograd', 'Studentska 5');

-- --------------------------------------------------------

--
-- Table structure for table `_location_seq`
--

CREATE TABLE IF NOT EXISTS `_location_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_location_seq`
--

INSERT IGNORE INTO `_location_seq` (`next_val`) VALUES
(1),
(51);

-- --------------------------------------------------------

--
-- Table structure for table `_message`
--

CREATE TABLE IF NOT EXISTS `_message` (
  `conversation_id` bigint NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  `content` tinytext NOT NULL,
  `status` enum('DELIVERED','READ','SENT') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_message`
--

INSERT IGNORE INTO `_message` (`conversation_id`, `created_at`, `id`, `sender_id`, `content`, `status`) VALUES
(1, '2026-06-15 09:12:00.000000', 1, 1, 'Dobro jutro Kosta, kako napreduje modul za magacin?', 'READ'),
(1, '2026-06-15 09:18:30.000000', 2, 2, 'Dobro jutro! CRUD za artikle je gotov, radim na pretrazi po lokaciji.', 'READ'),
(1, '2026-06-15 10:05:15.000000', 3, 1, 'Odlično. Da li možeš da dodaš filter po statusu zaliha?', 'READ'),
(1, '2026-06-15 10:22:40.000000', 4, 2, 'Može, ubaciću danas. Treba li i export u Excel?', 'READ'),
(1, '2026-06-15 11:45:00.000000', 5, 1, 'Da, export bi bio koristan za inventuru na kraju meseca.', 'READ'),
(1, '2026-06-15 13:10:20.000000', 6, 2, 'U redu, krećem sa tim posle ručka. Demo je i dalje za petak?', 'READ'),
(1, '2026-06-15 13:55:00.000000', 7, 1, 'Da, petak u 10h. Fokus na magacin i dashboard.', 'READ'),
(1, '2026-06-15 14:30:22.000000', 8, 2, 'Dogovoreno, šaljem build čim završim export.', 'DELIVERED');

-- --------------------------------------------------------

--
-- Table structure for table `_movable_asset`
--

CREATE TABLE IF NOT EXISTS `_movable_asset` (
  `amount` int NOT NULL,
  `movable_asset_status` tinyint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `current_movable_asset_issued_by_id` bigint DEFAULT NULL,
  `current_movable_asset_user_id` bigint DEFAULT NULL,
  `id` bigint NOT NULL,
  `insurance_date` datetime(6) DEFAULT NULL,
  `movable_asset_company_id` bigint DEFAULT NULL,
  `movable_asset_location_id` bigint DEFAULT NULL,
  `purchase_date` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `barcode` varchar(255) DEFAULT NULL,
  `category` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `identifier` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `serial_number` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL
) ;

--
-- Dumping data for table `_movable_asset`
--

INSERT IGNORE INTO `_movable_asset` (`amount`, `movable_asset_status`, `created_at`, `current_movable_asset_issued_by_id`, `current_movable_asset_user_id`, `id`, `insurance_date`, `movable_asset_company_id`, `movable_asset_location_id`, `purchase_date`, `updated_at`, `barcode`, `category`, `comment`, `identifier`, `manufacturer`, `model`, `name`, `serial_number`, `type`, `unit`) VALUES
(1, 6, '2026-03-01 10:00:00.000000', 1, 1, 1, '2027-03-01 00:00:00.000000', 1, 2, '2025-09-15 00:00:00.000000', '2026-03-01 10:00:00.000000', 'ABP-LPT-001', 'IT Oprema', 'Radni laptop za razvoj', 'ABP-001', 'Dell', 'Latitude 5540', 'Laptop', 'DL5540-2025-0842', 'Laptop', 'Komad'),
(1, 6, '2026-03-01 10:05:00.000000', 1, 2, 2, '2027-03-01 00:00:00.000000', 1, 2, '2025-10-20 00:00:00.000000', '2026-03-01 10:05:00.000000', 'ABP-LPT-002', 'IT Oprema', 'Radni laptop za razvoj', 'ABP-002', 'Lenovo', 'ThinkPad T14', 'Laptop', 'LN-T14-2025-1198', 'Laptop', 'Komad'),
(1, 0, '2026-03-05 09:00:00.000000', NULL, NULL, 3, NULL, 1, 2, '2026-01-10 00:00:00.000000', '2026-03-05 09:00:00.000000', 'ABP-MON-001', 'IT Oprema', 'Rezerva u kancelariji Beograd', 'ABP-003', 'LG', '27UL500', 'Monitor', 'LG27-88421', 'Monitor', 'Komad'),
(1, 6, '2026-04-01 11:00:00.000000', 1, 1, 4, '2027-04-01 00:00:00.000000', 1, 2, '2026-02-01 00:00:00.000000', '2026-04-01 11:00:00.000000', 'ABP-PHN-001', 'IT Oprema', 'Službeni telefon', 'ABP-004', 'Apple', 'iPhone 15', 'Telefon', 'F17QK92MNP', 'Mobilni telefon', 'Komad'),
(1, 0, '2026-04-10 08:30:00.000000', NULL, NULL, 5, NULL, 1, 1, '2024-11-05 00:00:00.000000', '2026-04-10 08:30:00.000000', 'ABP-PRJ-001', 'IT Oprema', 'Za prezentacije i demo klijentima', 'ABP-005', 'Epson', 'EB-W49', 'Projektor', 'EP-W49-33210', 'Projektor', 'Komad'),
(1, 0, '2026-05-01 09:00:00.000000', NULL, NULL, 6, NULL, 1, 2, '2025-06-01 00:00:00.000000', '2026-05-01 09:00:00.000000', 'ABP-PRT-001', 'IT Oprema', 'Zajednički štampač u open space-u', 'ABP-006', 'HP', 'LaserJet Pro M404', 'Štampač', 'VN8CK29001', 'Štampač', 'Komad'),
(1, 6, '2026-05-15 14:00:00.000000', 1, 2, 7, '2027-05-15 00:00:00.000000', 1, 2, '2024-03-01 00:00:00.000000', '2026-05-15 14:00:00.000000', 'BG-123-AB', 'Vozila', 'Službeno vozilo za terenske obilaske', 'ABP-007', 'Škoda', 'Octavia', 'Automobil', 'TMBER6NH5K0123456', 'Automobil', 'Komad'),
(8, 0, '2026-05-20 10:00:00.000000', NULL, NULL, 8, NULL, 1, 2, '2025-12-01 00:00:00.000000', '2026-05-20 10:00:00.000000', NULL, 'Nameštaj', 'Ergonomske stolice za open space', 'ABP-008', 'Nowy Styl', 'Groove', 'Kancelarijska stolica', NULL, 'Nameštaj', 'Komad'),
(1, 1, '2026-06-01 08:00:00.000000', 1, NULL, 9, NULL, 1, 1, '2023-08-15 00:00:00.000000', '2026-06-15 11:00:00.000000', 'ABP-TOOL-001', 'Alati', 'Na servisu - neispravan akumulator', 'ABP-009', 'Makita', 'DHP484', 'Akumulatorska bušilica', 'MK-484-7782', 'Alat', 'Komad'),
(1, 0, '2026-06-10 09:00:00.000000', NULL, NULL, 10, '2027-06-10 00:00:00.000000', 1, 2, '2025-12-10 00:00:00.000000', '2026-06-10 09:00:00.000000', 'ABP-SRV-001', 'IT Oprema', 'Lokalni dev/staging server', 'ABP-010', 'HP', 'ProLiant DL380', 'Server', 'HP-DL380-0091', 'Server', 'Komad'),
(1, 0, '2026-06-15 10:00:00.000000', NULL, NULL, 11, NULL, 1, 2, '2026-04-01 00:00:00.000000', '2026-06-15 10:00:00.000000', 'ABP-TAB-001', 'IT Oprema', 'Za demo aplikacije na terenu', 'ABP-011', 'Samsung', 'Galaxy Tab S9', 'Tablet', 'SM-X710-5521', 'Tablet', 'Komad'),
(1, 6, '2026-06-20 08:00:00.000000', 1, 1, 12, NULL, 1, 1, '2026-01-20 00:00:00.000000', '2026-06-20 08:00:00.000000', 'ABP-SCN-001', 'Magacin', 'Skener za inventuru magacina', 'ABP-012', 'Zebra', 'DS2208', 'Barcode skener', 'ZB-2208-4412', 'Skener', 'Komad');

-- --------------------------------------------------------

--
-- Table structure for table `_region`
--

CREATE TABLE IF NOT EXISTS `_region` (
  `country_id` smallint NOT NULL,
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_region`
--

INSERT IGNORE INTO `_region` (`country_id`, `id`, `name`) VALUES
(1, 1, 'Beograd'),
(1, 2, 'Vojvodina'),
(1, 3, 'Zapadna i Centralna Srbija'),
(1, 4, 'Istočna i Južna Srbija'),
(1, 5, 'Kosovo i Metohija');

-- --------------------------------------------------------

--
-- Table structure for table `_district`
--

CREATE TABLE IF NOT EXISTS `_district` (
  `id` int NOT NULL,
  `region_id` int NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_district`
--

INSERT IGNORE INTO `_district` (`id`, `region_id`, `name`) VALUES
(1, 1, 'Grad Beograd'),
(2, 2, 'Severnobački okrug'),
(3, 2, 'Srednjobanatski okrug'),
(4, 2, 'Severnobanatski okrug'),
(5, 2, 'Južnobanatski okrug'),
(6, 2, 'Zapadnobački okrug'),
(7, 2, 'Južnobački okrug'),
(8, 2, 'Sremski okrug'),
(9, 3, 'Mačvanski okrug'),
(10, 3, 'Kolubarski okrug'),
(11, 3, 'Šumadijski okrug'),
(12, 3, 'Pomoravski okrug'),
(13, 3, 'Zlatiborski okrug'),
(14, 3, 'Moravički okrug'),
(15, 3, 'Raški okrug'),
(16, 3, 'Rasinski okrug'),
(17, 4, 'Podunavski okrug'),
(18, 4, 'Braničevski okrug'),
(19, 4, 'Borski okrug'),
(20, 4, 'Zaječarski okrug'),
(21, 4, 'Nišavski okrug'),
(22, 4, 'Toplički okrug'),
(23, 4, 'Pirotski okrug'),
(24, 4, 'Jablanički okrug'),
(25, 4, 'Pčinjski okrug'),
(26, 5, 'Kosovski okrug'),
(27, 5, 'Pečki okrug'),
(28, 5, 'Prizrenski okrug'),
(29, 5, 'Kosovsko-mitrovački okrug'),
(30, 5, 'Kosovsko-pomoravski okrug');

-- --------------------------------------------------------

--
-- Table structure for table `_team`
--

CREATE TABLE IF NOT EXISTS `_team` (
  `id` int NOT NULL,
  `company_id` bigint DEFAULT NULL,
  `department_id` bigint NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_team_users`
--

CREATE TABLE IF NOT EXISTS `_team_users` (
  `team_id` int NOT NULL,
  `user_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_user`
--

CREATE TABLE IF NOT EXISTS `_user` (
  `active` bit(1) NOT NULL,
  `cookies_enabled` bit(1) NOT NULL,
  `terms_and_conditions` bit(1) NOT NULL,
  `company_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_profile_id` bigint DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_user`
--

INSERT IGNORE INTO `_user` (`active`, `cookies_enabled`, `terms_and_conditions`, `company_id`, `created_at`, `id`, `updated_at`, `user_profile_id`, `email`, `password`) VALUES
(b'1', b'1', b'1', 1, '2026-02-10 08:49:20.662965', 1, '2026-02-10 08:49:20.849524', 1, 'jovan.stankovic@abplat.com', '$2a$10$zKJunCvJZcjpX9voROnH1e6oktlzU..fnIYmHFZ4uDvxnI2.lUkOW'),
(b'1', b'1', b'1', 1, '2026-02-10 08:49:21.099012', 2, '2026-02-10 08:49:21.104121', 2, 'kosta.markovic@abplat.com', '$2a$10$u/QEkowGJyZBzJGSs3doLeySD48tw0QccYHHxKF2xKNL2VL29zDtC');

-- --------------------------------------------------------

--
-- Table structure for table `_user_profile`
--

CREATE TABLE IF NOT EXISTS `_user_profile` (
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_setting_id` bigint NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `profile_pic` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_user_profile`
--

INSERT IGNORE INTO `_user_profile` (`created_at`, `id`, `updated_at`, `user_setting_id`, `display_name`, `first_name`, `last_name`, `profile_pic`) VALUES
('2026-02-10 08:49:20.928935', 1, '2026-02-10 08:49:20.928965', 1, 'Jovan Stankovic', 'Jovan', 'Stankovic', 0x89504e470d0a1a0a0000000d494844520000038400000212040300000066281ce20000000f504c5445d0d0d0707172e6e6e6ffffff9f9fa021d47b94000011a24944415478daecdd5d7a23370e055088e505b83c5a005dd40218d62c40d664ff6b1a5bb6d351b72debe78224c0cb97045f1ea2e66990205125c9fa3ecaf23ebc846fff22ff1aefffc1d11ff05728defe48693da1fb6d84d7ff4ec29ec37589f2f30889847d8667d3ef0fc542c2ee363fb9728495841d85456e196fa948c21ec2b4c8cd83841d84f700beafa7246c1ade0bf88948c246612a02198184ad42810dcb670cbb84250a708444c2dae122e0110a09ab86a2301209eb855954462826093ffe993eef192d8451b4c66a70360c1216511c8984fa6114d571ac6a48a8192a0bbe8e4242cdb048859148a8175611149948a81566a934020975c26a82ff1892101aa628350709e16165c177431202c3ea82af852909916103c1374312c2c2244d462221ee53361a8584a0304a5bc3ce090d3c66d84ef0e36cc196ef7d6153c1774312def7a4685bc1a32109ef0a7363c1b7bb3612de133617ecff41e1ce098b7430261276f1bcf65d8624bc39944e4622e18da174330a096f0aa3746648c22bc3dc91a004125e1f16e96a4c24ece2c517972fcd744b28dd8d42c2abc2dc1f61afef3d75da2f2cd2e198d8f235d0a63f3f4868a14d6fb089df2561ee54f0fd7448c29fc322dd8e89841785d2f12824bc20cc3d130612fe1c16e97a4c24fc3194ce4721e10f61ee9d3090f07c58a4fb3191f06c2806c64ac23361b6401848f87d6822098f2f8f92f09b30da2014bed9f45d588c087eb49dd8f2fd3344cf747cfdf385f8bad004cf0fb4f54408ad654258e4ef97f97dfc2d0bf65b840309b51f590bcbc327dfe7d807a42209bf0a714918e2dff317638b442c24fc23cc38c097f99bb10d1158d190f0b71035b9cbc37c666c175c1a9250e7a9c3ef53f0c3f010516948c2d310b4883ecc3f8e47906121e1498849c2b0992f18a0c57422e1bf43cce5e8f2325f34b6016948425c12860b0551868184bfc25433078f23e0d29084a8240cd708cedb084b43128292f0b24a066c38f1cda68f10319bbbf9ca01395bf4f064700f8488fbed385f3d0ea0c62109214918e61b46c0a42109114978ed4688db0e2712264812eee69b06623b2c244424619c6f1c07441a921090099b5b09014b69206169b68cbe8d27c0a5d0f084f75fcc5c772d03af4ac3f0844d93f0b5a2813ddb3d2c212009e7bb06200dc7260424e1f37d848083451a9ab07912220e166164c2f64908d90d87cec2f64988d80da781df6c8aed931092864ddf92694a78ffb1feae33e1e7c0f49cc624bcbf98d901040157346158c23e92709eef5fcf4725043cf40411049c2ba64109a5876206d4b0284312961e4e14c0826640c2d8473103ea399511091bb67a550a9af108733feb2862250d03127653cca06e688623cc3dada38895741a8e30f6b48e225652198d10d0667a4012026ad2341821a09841aea38895b4d51ba3adfa85d2d73a0a5949cb502ddfd2d93a0a5949a7a1087357478ae33d29e26838122160be5eb08480cd5046220424a1800531afc88c43d8ee7d34d50b1a198810305b1b34e13692b0ea0f6bc1b742c8b1621a863076b81562be3ea18c42281d6e85b0cd7008c2dce35668f7db135a10c62eb742c866d8e2db131a1042be095f4170de033e57188230f7b91542ae4907214424e1b306e12364251de0cd26c8778f6e340811d7a4320dd0f245aca341451052cf8c40187bad6630f58cf827846c853b1d42443df3595c3826ccdd5633a07a66724f18fbad66305ff62cee091193a47237832a49dd1342d651a5821453927eaea4246c509082ea19714e0899a387be098b6b42cc8fbd3e6b1162ea99e09a10b38e6e48d88e103245a25590824ad2ca5f06559950ba2e484125a978cec2dc3be11e77ac70fa66138670a747882949c56fcb17f35ba1060893dfaebdf47da6005d74bfbf77ef9310b38eea9d2920afa81d776bb78498923de89d2950a70af14a08da0a2d1026af5928bd9f295007c3b7cdd025613640b817d44aea9210b4488926e11309cf84a0ad50afd584244c2e095149f86c81707249982d1082cef6af2ba94742d4e46c3409b724fc3e4c260851074349ccc2160d5f24e1e4f0cd26d456186c1006872d5fd8dca80aa2ae672a7ec36cb5ff136c2b54263ca03e67f29785b0bfdeba84a81bb663cfd017613642f824d8cdd011216a2bd4bd5f03128a3b42d8ccecac10266784b06a46f77e0d493839238c5608b791845f87d90a21ec9efb63336416d6cf42127e1de2b642e55b6ee042fa5ecff8c942338473c4d6336e08715ba172a302491848d8a251812414576f36c99084c553cb174838db21f4d4b52f63124e8e08b321c203f0b33a228c860845a02ba917421994b090b005e19e59f8455846250c6e08f3a8841309eb3f3ac32cfc3a0416a4dacf5d6009abfcfe4f1542b194850781d6333e0891d58c7e163e915099504c114e4e08f3b859189cbcd994c7cd4271d2f28d24344e98a073f2608b30f9c84219772f948984d6b3d007611c390b830bc23c72160ab390843d10cac80b69855fa4d427c45e725b3b17ba202c631306078479ec85742221b3b03d611c3b0b49683e0b3f4f1596df6c12635978007fe064bee59bc45816ee49683d0bd18413092d3f0aec83308e4e18cc136612320b2b130674fd4542eb59689f50ac11a23ff0c7a9c22e6122616216562644affc1fa70a12fe227c21615d42f88408092b1366666120a1754231fe66137e4236e6088db77cc55a166ef1848584a7e359995048689d900be96958cc113ee23f712021099d119af9cda6d3a781cd1266fc843c909084b5096d2fa4f8f24efb29c40309d5b350b9dbb457f84bc72cac4a7820e16928e608153ef1f17a8684d5082309d509957bbed1de42aada2f4c1a59688f30196ef96a10ea360cb71a8493e5aebd06a1ea25e95648689cf09184a7a1c6aaa47bc3f644c20a843b73848184152f490f243c0db339c23d092b10066bf76bc727494958eb6c1f4958632f24a179c28db1cb19d3843a3bcbb3b1cb1966614dc2471256c9c20763277b6d42d57ea1ce84883942c32d5f9d0909c64ef624ac4918485887f0c5d8b19084f50e864ac7c2b787818d121625c26763c742c359a845b8335690320beb1d0c49588b30183b5390b01ea190b012a15abb2992b016e1c656414ac26aa78a4712fe1e66ad29d9d92a484958ad57b1b749a8d92f8c6a5362ab207d7b9cdb68cb578d50a9248d24ac47f86caa2025612dc24712562414530529096bd5330712d6ccc2174bd50c092b5db1e9553324ac54cf3c92b02aa158aa664858a99e1112d6cdc21743d50c09bfccc28da16a868475fa4d4f24ac4c08af67f666096df60b159ecad7fcebc6966f8dc3fd3692b036e1b399833d09eb6c864f24ac4e18ec6c8524acb119aa6e8524acb1193e320beb3e0a8cdf0c9f48d8803098d90a49586133d4dd0ab91756d80c1f99854db210b8191e48d88410784d2a246c93851b235b2109f57b86ca5ba1f2979668f60b9318d90c95b7c2b71f6d32daf25527446d86caeba8e1aebd3621ea019aad300b5b65e1cec2ed9a69c2456c6c867b123623049d0cb5b7427e37b7f6c9507d2be437e46b6f86ea5ba1e585349ad80cf7ccc2868490cd3092b065166e2c6c85fce534e59ee123099b128a856a863f04abfc008d90b02921e0a63b5a27d4ec17ae1516d28d01c269b1fb532316082b14a496098bfef43c1b2848030955099f487836d49f9e9d01c28584e7c603099985fa9fb190d07a169a268ccc42eb0b6964165a27cccc42123ac8c24042eb593891d07a16da262ccc42fd8554b55fa8ff48be892c5498d86a2ddf0a8406b23099265c988524749085ab6dc2c82cb44e98998524349f85810ba9f52c24a1f92c9c8c132ecc42eb848959c82c349f858984d6b370b54e1887cf42f3849959a84da8db2fd46ffa769f854169626bb57cf509bbcfc2c93c61611692d078162ee609d7d1b39084e6b3b0d8278ccc42eb8479ec2c0c24b49e859303c23276167a205cc7cec24042eb59b8785848e3d0595848683d0b5d10e691b3305420d4ee172e298f9c8593e2c4d66af92ea90c9d852e08d791b37021a1f52c2c3e0823b3d03a611e370b0309ad67e1e484b0300bad136ad633cbdddf90bf0daa37a424fc6144c46f36291a162f845a256900fd10ec3e320b7f0895ea99f030cf7d1b06129e9f9fcd0c1b5b9d37b02637846b9f858cfe86b89050bb90f9f738448d6aa606a17ebf50e325435421a3bc21aa4f6ca596afc24b86b842e6e4a77fd086c11121786e96cdac32d045cde48830f5bd0d6addd4782244feed0e7a82e8a22679228c5d17325a45cdea8930f75dc89c2ca61159cd300b95cff3ba1be2e48a30f55ec86818fa22c4d43371ae343045cdea8b30f65fc8c04ff9ce08b38142067cca0fce08170b850c76439c9c112613850cb4fd94bc65e15d9b4b887383716751b3d622acd22fbcf3bb4b6a1632b09b9a506962d76a84d94a21832a6a267784ab99420654d40412362c6420454df14778e34a1ae7c6e3d6a266f147182d1532f7dfd44c0e0993a942e6eea2c623e162ab90b9b7a8491e09b3b142e6bea266f54878e59612e2dcd1b8b6a8092e0993b942e68e9b9ac925e162af90b9fd999ad52761b657c8dc5cd438258c160b99db0c27a784c9b6e035454d55c25afdc2e5e26bd2de0a995b6e6a8af24c3669f92e975e93f657c85c6f18969109b5de5aaa7adb36b9255c6d96a2d717358b5fc268b690b9aea8297e09b3dd42e69a9b9ae0380b8be142e68aa2c633e1d9cd306c6633e37c51533c1346cb85ccc5eda7c53361b65dc89c14353f34ecbd12ae269a837716a68b6fc268ba14bdac302dbe09b3ed52f4921662709e85c578297a4161ba38275ccd97a23f16a6a53661c57ee1d7affb06cb825fdc98865a33d9a2e5fbe5ebbed1b4e0177938b9274cce04ff305cfd13666782bf1986e49fb078133c359c06205c9dd4a2df18961108b38f5af41bc36504c2e24f709e977faeb847205caddfc99cbb6b2b6310667f82ff182e63101e57d2ddec6c1c1fc69806215c3d0abef780cb288459e23c7b340ccb2884c55531fa6b1c1fb9188370ddb8149cb7a50961ed7ee131fccb2961fd996cd1f23d86d127e1ff06224c3ef7c27520429f2be9d35084c9ed3a3a0ce1f2ec751d1d87f0bffe04ff331861f29a84e310fa2b68fedfdeb925b70dc35034b6ba008dc80534a417a021b400a7f2fed7d4491d779ab88e6d892271c1dbbff3d10ff10c643c88a86f4ea1d84c669a5268adc996da53682ca171a13d85c6dea4a14585a6129a3eb5a8d05418ce15a3b0cabcf08c96129ac247577fe47bc6d15667a6458586464ed2a8423b1d9aa15985661aa55db30aad84a19776151a09c3b961853626bf5e5a5668a2ae704d2b8c16c2509a56180e6682b0598506ca7b695d21fcd8d0c5d6150a7a18262a042fef5da8aeb0e2bcf08ce0e57dac7874f547be1f081d864ea810bccb16a9306137bb9d5061c26e76472a04dfdb764285e04bbf910a2f380107211522774a2315826f8c3aa1c27f364677b8230a2a841ddf3b2a045f37f4890ac177d5e64085d8bb6a15b7d1146d367d46b0365b57f3ac948d7cff225461310815822f3a0915fe0f1358414185d7f80a53500815826fc97454780b279c5c860a6fac58ec6072192abcf5995888be0c157e87003d9a5ea810fc7e7e8a54f82daa9f3ab940857750f9abb44f54781775bf4a759d955285aa5fa5b342856ae685103783bdb6b35235f285b8192c54f818aabd84315321f8b5d241a8f0617cd5fb1aa5c20751a3c14885cfa0c2cac2052ac4be9338242a7c1295fd1cbe7f6a990a9f445dd5610c54f834aabad036072a5c8093a654860a17a19a94a64f54b810777a52192a5c864a1cea3c1c7d9b4d7aafd244ad87a376e4fb09938664940a57e1a8a09ca042e87b1873a0c2d558b53c1c121566c08a0e5d0a549803a7ba06a930035672f8d194a1c20c58e76b16bd042acc86351c0e42855971ac60900af3626187b35061761c8b1ba4c2cc58b2e7dd09150af497f2304ee3a250f5bcf02b96f98b181ee4342046be5758a2b8e8850a37c5b1442243859be2b6498def840a3747396cfa12a5c21238be6d5a0d526109dc6d9389062a2c87e3162118a8b028fec81e82810a0b63d65fc44e021556b8eb3de592f84b001fdf82c294a9be38a23ebe058521aeff49f4c08f6f41e16a8947f0c7b7a0f05de261a540708550f3c25b97a3242d0845bf477d5ee491ef6d8cf2f36d490052a12614393c68d1bf5cfe2b15eac370f4f7c26fffe7537b54a818258d2fa793bffe773aeed3250b30f4bcbf01e1aa2a29feea5ad20000000049454e44ae426082),
('2026-02-10 08:49:20.973832', 2, '2026-02-10 08:49:20.974040', 1, 'Kosta Markovic', 'Kosta', 'Markovic', 0x89504e470d0a1a0a0000000d494844520000038400000212040300000066281ce20000000f504c5445d0d0d0707172e6e6e6ffffff9f9fa021d47b94000011a24944415478daecdd5d7a23370e055088e505b83c5a005dd40218d62c40d664ff6b1a5bb6d351b72debe78224c0cb97045f1ea2e66990205125c9fa3ecaf23ebc846fff22ff1aefffc1d11ff05728defe48693da1fb6d84d7ff4ec29ec37589f2f30889847d8667d3ef0fc542c2ee363fb9728495841d85456e196fa948c21ec2b4c8cd83841d84f700beafa7246c1ade0bf88948c246612a02198184ad42810dcb670cbb84250a708444c2dae122e0110a09ab86a2301209eb855954462826093ffe993eef192d8451b4c66a70360c1216511c8984fa6114d571ac6a48a8192a0bbe8e4242cdb048859148a8175611149948a81566a934020975c26a82ff1892101aa628350709e16165c177431202c3ea82af852909916103c1374312c2c2244d462221ee53361a8584a0304a5bc3ce090d3c66d84ef0e36cc196ef7d6153c1774312def7a4685bc1a32109ef0a7363c1b7bb3612de133617ecff41e1ce098b7430261276f1bcf65d8624bc39944e4622e18da174330a096f0aa3746648c22bc3dc91a004125e1f16e96a4c24ece2c517972fcd744b28dd8d42c2abc2dc1f61afef3d75da2f2cd2e198d8f235d0a63f3f4868a14d6fb089df2561ee54f0fd7448c29fc322dd8e89841785d2f12824bc20cc3d130612fe1c16e97a4c24fc3194ce4721e10f61ee9d3090f07c58a4fb3191f06c2806c64ac23361b6401848f87d6822098f2f8f92f09b30da2014bed9f45d588c087eb49dd8f2fd3344cf747cfdf385f8bad004cf0fb4f54408ad654258e4ef97f97dfc2d0bf65b840309b51f590bcbc327dfe7d807a42209bf0a714918e2dff317638b442c24fc23cc38c097f99bb10d1158d190f0b71035b9cbc37c666c175c1a9250e7a9c3ef53f0c3f010516948c2d310b4883ecc3f8e47906121e1498849c2b0992f18a0c57422e1bf43cce5e8f2325f34b6016948425c12860b0551868184bfc25433078f23e0d29084a8240cd708cedb084b43128292f0b24a066c38f1cda68f10319bbbf9ca01395bf4f064700f8488fbed385f3d0ea0c62109214918e61b46c0a42109114978ed4688db0e2712264812eee69b06623b2c244424619c6f1c07441a921090099b5b09014b69206169b68cbe8d27c0a5d0f084f75fcc5c772d03af4ac3f0844d93f0b5a2813ddb3d2c212009e7bb06200dc7260424e1f37d848083451a9ab07912220e166164c2f64908d90d87cec2f64988d80da781df6c8aed931092864ddf92694a78ffb1feae33e1e7c0f49cc624bcbf98d901040157346158c23e92709eef5fcf4725043cf40411049c2ba64109a5876206d4b0284312961e4e14c0826640c2d8473103ea399511091bb67a550a9af108733feb2862250d03127653cca06e688623cc3dada38895741a8e30f6b48e225652198d10d0667a4012026ad2341821a09841aea38895b4d51ba3adfa85d2d73a0a5949cb502ddfd2d93a0a5949a7a1087357478ae33d29e26838122160be5eb08480cd5046220424a1800531afc88c43d8ee7d34d50b1a198810305b1b34e13692b0ea0f6bc1b742c8b1621a863076b81562be3ea18c42281d6e85b0cd7008c2dce35668f7db135a10c62eb742c866d8e2db131a1042be095f4170de033e57188230f7b91542ae4907214424e1b306e12364251de0cd26c8778f6e340811d7a4320dd0f245aca341451052cf8c40187bad6630f58cf827846c853b1d42443df3595c3826ccdd5633a07a66724f18fbad66305ff62cee091193a47237832a49dd1342d651a5821453927eaea4246c509082ea19714e0899a387be098b6b42cc8fbd3e6b1162ea99e09a10b38e6e48d88e103245a25590824ad2ca5f06559950ba2e484125a978cec2dc3be11e77ac70fa66138670a747882949c56fcb17f35ba1060893dfaebdf47da6005d74bfbf77ef9310b38eea9d2920afa81d776bb78498923de89d2950a70af14a08da0a2d1026af5928bd9f295007c3b7cdd025613640b817d44aea9210b4488926e11309cf84a0ad50afd584244c2e095149f86c81707249982d1082cef6af2ba94742d4e46c3409b724fc3e4c260851074349ccc2160d5f24e1e4f0cd26d456186c1006872d5fd8dca80aa2ae672a7ec36cb5ff136c2b54263ca03e67f29785b0bfdeba84a81bb663cfd017613642f824d8cdd011216a2bd4bd5f03128a3b42d8ccecac10266784b06a46f77e0d493839238c5608b791845f87d90a21ec9efb63336416d6cf42127e1de2b642e55b6ee042fa5ecff8c942338473c4d6336e08715ba172a302491848d8a251812414576f36c99084c553cb174838db21f4d4b52f63124e8e08b321c203f0b33a228c860845a02ba917421994b090b005e19e59f8455846250c6e08f3a8841309eb3f3ac32cfc3a0416a4dacf5d6009abfcfe4f1542b194850781d6333e0891d58c7e163e915099504c114e4e08f3b859189cbcd994c7cd4271d2f28d24344e98a073f2608b30f9c84219772f948984d6b3d007611c390b830bc23c72160ab390843d10cac80b69855fa4d427c45e725b3b17ba202c631306078479ec85742221b3b03d611c3b0b49683e0b3f4f1596df6c12635978007fe064bee59bc45816ee49683d0bd18413092d3f0aec83308e4e18cc136612320b2b130674fd4542eb59689f50ac11a23ff0c7a9c22e6122616216562644affc1fa70a12fe227c21615d42f88408092b1366666120a1754231fe66137e4236e6088db77cc55a166ef1848584a7e359995048689d900be96958cc113ee23f712021099d119af9cda6d3a781cd1266fc843c909084b5096d2fa4f8f24efb29c40309d5b350b9dbb457f84bc72cac4a7820e16928e608153ef1f17a8684d5082309d509957bbed1de42aada2f4c1a59688f30196ef96a10ea360cb71a8493e5aebd06a1ea25e95648689cf09184a7a1c6aaa47bc3f644c20a843b73848184152f490f243c0db339c23d092b10066bf76bc727494958eb6c1f4958632f24a179c28db1cb19d3843a3bcbb3b1cb1966614dc2471256c9c20763277b6d42d57ea1ce84883942c32d5f9d0909c64ef624ac4918485887f0c5d8b19084f50e864ac7c2b787818d121625c26763c742c359a845b8335690320beb1d0c49588b30183b5390b01ea190b012a15abb2992b016e1c656414ac26aa78a4712fe1e66ad29d9d92a484958ad57b1b749a8d92f8c6a5362ab207d7b9cdb68cb578d50a9248d24ac47f86caa2025612dc24712562414530529096bd5330712d6ccc2174bd50c092b5db1e9553324ac54cf3c92b02aa158aa664858a99e1112d6cdc21743d50c09bfccc28da16a868475fa4d4f24ac4c08af67f666096df60b159ecad7fcebc6966f8dc3fd3692b036e1b399833d09eb6c864f24ac4e18ec6c8524acb119aa6e8524acb1193e320beb3e0a8cdf0c9f48d8803098d90a49586133d4dd0ab91756d80c1f99854db210b8191e48d88410784d2a246c93851b235b2109f57b86ca5ba1f2979668f60b9318d90c95b7c2b71f6d32daf25527446d86caeba8e1aebd3621ea019aad300b5b65e1cec2ed9a69c2456c6c867b123623049d0cb5b7427e37b7f6c9507d2be437e46b6f86ea5ba1e585349ad80cf7ccc2868490cd3092b065166e2c6c85fce534e59ee123099b128a856a863f04abfc008d90b02921e0a63b5a27d4ec17ae1516d28d01c269b1fb532316082b14a496098bfef43c1b2848030955099f487836d49f9e9d01c28584e7c603099985fa9fb190d07a169a268ccc42eb0b6964165a27cccc42123ac8c24042eb593891d07a16da262ccc42fd8554b55fa8ff48be892c5498d86a2ddf0a8406b23099265c988524749085ab6dc2c82cb44e98998524349f85810ba9f52c24a1f92c9c8c132ecc42eb848959c82c349f858984d6b370b54e1887cf42f3849959a84da8db2fd46ffa769f854169626bb57cf509bbcfc2c93c61611692d078162ee609d7d1b39084e6b3b0d8278ccc42eb8479ec2c0c24b49e859303c23276167a205cc7cec24042eb59b8785848e3d0595848683d0b5d10e691b3305420d4ee172e298f9c8593e2c4d66af92ea90c9d852e08d791b37021a1f52c2c3e0823b3d03a611e370b0309ad67e1e484b0300bad136ad633cbdddf90bf0daa37a424fc6144c46f36291a162f845a256900fd10ec3e320b7f0895ea99f030cf7d1b06129e9f9fcd0c1b5b9d37b02637846b9f858cfe86b89050bb90f9f738448d6aa606a17ebf50e325435421a3bc21aa4f6ca596afc24b86b842e6e4a77fd086c11121786e96cdac32d045cde48830f5bd0d6addd4782244feed0e7a82e8a22679228c5d17325a45cdea8930f75dc89c2ca61159cd300b95cff3ba1be2e48a30f55ec86818fa22c4d43371ae343045cdea8b30f65fc8c04ff9ce08b38142067cca0fce08170b850c76439c9c112613850cb4fd94bc65e15d9b4b887383716751b3d622acd22fbcf3bb4b6a1632b09b9a506962d76a84d94a21832a6a267784ab99420654d40412362c6420454df14778e34a1ae7c6e3d6a266f147182d1532f7dfd44c0e0993a942e6eea2c623e162ab90b9b7a8491e09b3b142e6bea266f54878e59612e2dcd1b8b6a8092e0993b942e68e9b9ac925e162af90b9fd999ad52761b657c8dc5cd438258c160b99db0c27a784c9b6e035454d55c25afdc2e5e26bd2de0a995b6e6a8af24c3669f92e975e93f657c85c6f18969109b5de5aaa7adb36b9255c6d96a2d717358b5fc268b690b9aea8297e09b3dd42e69a9b9ae0380b8be142e68aa2c633e1d9cd306c6633e37c51533c1346cb85ccc5eda7c53361b65dc89c14353f34ecbd12ae269a837716a68b6fc268ba14bdac302dbe09b3ed52f4921662709e85c578297a4161ba38275ccd97a23f16a6a53661c57ee1d7affb06cb825fdc98865a33d9a2e5fbe5ebbed1b4e0177938b9274cce04ff305cfd13666782bf1986e49fb078133c359c06205c9dd4a2df18961108b38f5af41bc36504c2e24f709e977faeb847205caddfc99cbb6b2b6310667f82ff182e63101e57d2ddec6c1c1fc69806215c3d0abef780cb288459e23c7b340ccb2884c55531fa6b1c1fb9188370ddb8149cb7a50961ed7ee131fccb2961fd996cd1f23d86d127e1ff06224c3ef7c27520429f2be9d35084c9ed3a3a0ce1f2ec751d1d87f0bffe04ff331861f29a84e310fa2b68fedfdeb925b70dc35034b6ba008dc80534a417a021b400a7f2fed7d4491d779ab88e6d892271c1dbbff3d10ff10c643c88a86f4ea1d84c669a5268adc996da53682ca171a13d85c6dea4a14585a6129a3eb5a8d05418ce15a3b0cabcf08c96129ac247577fe47bc6d15667a6458586464ed2a8423b1d9aa15985661aa55db30aad84a19776151a09c3b961853626bf5e5a5668a2ae704d2b8c16c2509a56180e6682b0598506ca7b695d21fcd8d0c5d6150a7a18262a042fef5da8aeb0e2bcf08ce0e57dac7874f547be1f081d864ea810bccb16a9306137bb9d5061c26e76472a04dfdb764285e04bbf910a2f380107211522774a2315826f8c3aa1c27f364677b8230a2a841ddf3b2a045f37f4890ac177d5e64085d8bb6a15b7d1146d367d46b0365b57f3ac948d7cff225461310815822f3a0915fe0f1358414185d7f80a53500815826fc97454780b279c5c860a6fac58ec6072192abcf5995888be0c157e87003d9a5ea810fc7e7e8a54f82daa9f3ab940857750f9abb44f54781775bf4a759d955285aa5fa5b342856ae685103783bdb6b35235f285b8192c54f818aabd84315321f8b5d241a8f0617cd5fb1aa5c20751a3c14885cfa0c2cac2052ac4be9338242a7c1295fd1cbe7f6a990a9f445dd5610c54f834aabad036072a5c8093a654860a17a19a94a64f54b810777a52192a5c864a1cea3c1c7d9b4d7aafd244ad87a376e4fb09938664940a57e1a8a09ca042e87b1873a0c2d558b53c1c121566c08a0e5d0a549803a7ba06a930035672f8d194a1c20c58e76b16bd042acc86351c0e42855971ac60900af3626187b35061761c8b1ba4c2cc58b2e7dd09150af497f2304ee3a250f5bcf02b96f98b181ee4342046be5758a2b8e8850a37c5b1442243859be2b6498def840a3747396cfa12a5c21238be6d5a0d526109dc6d9389062a2c87e3162118a8b028fec81e82810a0b63d65fc44e021556b8eb3de592f84b001fdf82c294a9be38a23ebe058521aeff49f4c08f6f41e16a8947f0c7b7a0f05de261a540708550f3c25b97a3242d0845bf477d5ee491ef6d8cf2f36d490052a12614393c68d1bf5cfe2b15eac370f4f7c26fffe7537b54a818258d2fa793bffe773aeed3250b30f4bcbf01e1aa2a29feea5ad20000000049454e44ae426082);

-- --------------------------------------------------------

--
-- Table structure for table `_user_roles`
--

CREATE TABLE IF NOT EXISTS `_user_roles` (
  `user_id` bigint NOT NULL,
  `roles` enum('ADMIN','PROJECT','PROJECT_MANAGEMENT','SALES','SALES_MANAGEMENT','INVENTORY_MANAGEMENT','ASSET_MANAGEMENT','VEHICLE_MANAGEMENT','HR_MANAGEMENT','HR') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_user_roles`
--

INSERT IGNORE INTO `_user_roles` (`user_id`, `roles`) VALUES
(1, 'PROJECT'),
(1, 'SALES'),
(1, 'ADMIN'),
(1, 'PROJECT_MANAGEMENT'),
(1, 'SALES_MANAGEMENT'),
(1, 'INVENTORY_MANAGEMENT'),
(1, 'ASSET_MANAGEMENT'),
(1, 'VEHICLE_MANAGEMENT'),
(1, 'HR_MANAGEMENT'),
(1, 'HR'),
(2, 'PROJECT'),
(2, 'SALES'),
(2, 'PROJECT_MANAGEMENT'),
(2, 'SALES_MANAGEMENT');

-- --------------------------------------------------------

--
-- Table structure for table `_department`
-- Dumping data for table `_department`
--

INSERT IGNORE INTO `_department` (`id`, `company_id`, `department_type`) VALUES
(1, 1, 4),
(2, 1, 5),
(3, 1, 1),
(4, 1, 3);

-- --------------------------------------------------------

--
-- Table structure for table `_team`
-- Dumping data for table `_team`
--

INSERT IGNORE INTO `_team` (`id`, `company_id`, `department_id`, `description`, `name`) VALUES
(1, 1, 1, 'Tim zadužen za prodaju i akviziciju', 'Sales Team'),
(2, 1, 2, 'Tim zadužen za razvoj i održavanje proizvoda', 'Development Team'),
(3, 1, 3, 'Tim menadžmenta kompanije', 'Management Team');

-- --------------------------------------------------------

--
-- Table structure for table `_team_users`
-- Dumping data for table `_team_users`
--

INSERT IGNORE INTO `_team_users` (`team_id`, `user_id`) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `_contacts_list`
-- Dumping data for table `_contacts_list`
--

INSERT IGNORE INTO `_contacts_list` (`id`, `company_id`, `team_id`, `country_id`, `region_id`, `city_id`, `name`, `description`, `status`) VALUES
(1, 1, 1, 1, 1, 1, 'Potencijalni klijenti', 'Lista potencijalnih klijenata za kontaktiranje', 'LEAD'),
(2, 1, 1, 1, 3, 74, 'Aktivni klijenti', 'Lista trenutno aktivnih klijenata', 'CLIENT'),
(3, 1, 1, 1, 1, 1, 'Arhivirani klijenti', 'Lista klijenata sa kojima trenutno ne sarađujemo', 'LEAD');

-- --------------------------------------------------------

--
-- Table structure for table `_contact`
-- Dumping data for table `_contact`
--

INSERT IGNORE INTO `_contact` (`id`, `contacts_list_id`, `name`, `company_name`, `phone_number`, `email`, `status`) VALUES
(1, 1, 'Marko Petrović', 'Tech Solutions d.o.o.', '+381601234567', 'marko.petrovic@techsolutions.rs', 'NEW'),
(2, 1, 'Ana Jovanović', 'Digital Media Group', '+381642345678', 'ana.jovanovic@dmg.com', 'CONTACTED'),
(3, 2, 'Nikola Đorđević', 'Smart Systems', '+381653456789', 'nikola.djordjevic@smartsystems.rs', 'OFFERED'),
(4, 2, 'Jelena Nikolić', 'Inovation Hub', '+381664567890', 'jelena.nikolic@ihub.rs', 'CLOSED'),
(5, 3, 'Milan Stanković', 'Old Tech Ltd.', '+381675678901', 'milan.stankovic@oldtech.com', 'STALLED'),
(6, 3, 'Jovana Pavlović', 'Former Partners Inc.', '+381686789012', 'jovana.pavlovic@formerpartners.com', 'REJECTED');

-- --------------------------------------------------------

--
-- Table structure for table `_event_participants`
-- Dumping data for table `_event_participants`
--

INSERT IGNORE INTO `_event_participants` (`id`, `event_id`, `user_id`, `status`) VALUES
(1, 1, 1, 'ACCEPTED'),
(2, 1, 2, 'ACCEPTED'),
(3, 2, 1, 'ACCEPTED'),
(4, 2, 2, 'ACCEPTED'),
(5, 3, 1, 'ACCEPTED'),
(6, 3, 2, 'TENTATIVE'),
(7, 4, 1, 'ACCEPTED'),
(8, 4, 2, 'INVITED'),
(9, 5, 1, 'ACCEPTED'),
(10, 5, 2, 'ACCEPTED');

-- --------------------------------------------------------

--
-- Table structure for table `_facility`
--

CREATE TABLE IF NOT EXISTS `_facility` (
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `closed_at` time DEFAULT NULL,
  `company_id` bigint NOT NULL,
  `id` bigint NOT NULL,
  `location_id` bigint DEFAULT NULL,
  `open_at` time DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `facility_type` varchar(31) NOT NULL,
  `max_desk_capacity` int DEFAULT NULL,
  `manager_id` bigint DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `purpose` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_facility`
--

INSERT IGNORE INTO `_facility` (`id`, `company_id`, `location_id`, `name`, `code`, `open_at`, `closed_at`, `facility_type`, `created_at`, `updated_at`, `max_desk_capacity`, `manager_id`) VALUES
(1, 1, 2, 'ABPlat Kancelarija Beograd', 'BGOFF-001', '08:00:00', '17:00:00', 'OFFICE', '2026-06-30 12:00:00.000000', '2026-06-30 12:00:00.000000', 30, NULL),
(2, 1, 1, 'ABPlat Glavni Magacin', 'MAG-KRV-001', '07:00:00', '16:00:00', 'WAREHOUSE', '2026-06-30 12:00:00.000000', '2026-06-30 12:00:00.000000', NULL, 1);

--

-- --------------------------------------------------------

--

--
-- Table structure for table `_workstation`
--

CREATE TABLE IF NOT EXISTS `_workstation` (
  `id` bigint NOT NULL,
  `facility_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `_workstation`
--

INSERT IGNORE INTO `_workstation` (`id`, `facility_id`, `name`) VALUES
(1, 1, 'Radno mesto 101'),
(2, 1, 'Radno mesto 102'),
(3, 1, 'Radno mesto 103');

-- --------------------------------------------------------

--
-- Table structure for table `_supplier`
--

CREATE TABLE IF NOT EXISTS `_supplier` (
  `id` bigint NOT NULL,
  `company_id` bigint NOT NULL,
  `name` varchar(255) NOT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` text,
  `notes` text,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_supplier` (`id`, `company_id`, `name`, `contact_name`, `email`, `phone`, `address`, `notes`, `active`, `created_at`, `updated_at`) VALUES
(1, 1, 'Tech Distribucija d.o.o.', 'Milan Nikolić', 'prodaja@techdistribucija.rs', '+38111223344', 'Bulevar oslobođenja 120, Beograd', 'IT oprema i periferija', b'1', '2026-05-01 09:00:00.000000', '2026-05-01 09:00:00.000000'),
(2, 1, 'Office Plus d.o.o.', 'Ana Jović', 'narudzbe@officeplus.rs', '+38111334455', 'Batajnički drum 15, Beograd', 'Kancelarijski materijal', b'1', '2026-05-01 09:00:00.000000', '2026-05-01 09:00:00.000000'),
(3, 1, 'EL-Komponente', 'Petar Ilić', 'info@elkomponente.rs', '+38118365511', 'Industrijska zona, Kraljevo', 'Kablovi i elektro komponente', b'1', '2026-05-01 09:00:00.000000', '2026-05-01 09:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `_product`
--

CREATE TABLE IF NOT EXISTS `_product` (
  `id` bigint NOT NULL,
  `company_id` bigint NOT NULL,
  `supplier_id` bigint DEFAULT NULL,
  `sku` varchar(100) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text,
  `barcode` varchar(100) DEFAULT NULL,
  `unit` varchar(50) NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `min_stock_level` int NOT NULL,
  `reorder_point` int NOT NULL,
  `active` bit(1) NOT NULL,
  `product_type` enum('GENERAL_MERCHANDISE','OFFICE_SUPPLIES','HOUSEHOLD_GOODS','PERSONAL_CARE','TOYS','SPORTS_EQUIPMENT','PET_SUPPLIES','ELECTRONICS','COMPUTERS','MOBILE_DEVICES','CONSUMER_ELECTRONICS','ACCESSORIES','NETWORK_EQUIPMENT','APPLIANCES','CLOTHING_TEXTILE','FOOTWEAR','FASHION_ACCESSORIES','PERISHABLE_FOOD','NON_PERISHABLE_FOOD','FROZEN_FOOD','DAIRY_PRODUCTS','MEAT_PRODUCTS','FRUITS_VEGETABLES','BEVERAGES','ALCOHOLIC_BEVERAGES','HAZARDOUS_MATERIAL','FLAMMABLE','TOXIC','CORROSIVE','FRAGILE','OVERSIZED','HEAVY_GOODS','TEMPERATURE_SENSITIVE','HIGH_VALUE','SPARE_PARTS','RAW_MATERIALS','SEMI_FINISHED_GOODS','FINISHED_GOODS','INDUSTRIAL_EQUIPMENT','CONSTRUCTION_MATERIALS','AUTOMOTIVE_PARTS','MACHINERY','CHEMICALS','PHARMACEUTICALS','MEDICAL_DEVICES','COSMETICS','PACKAGING_MATERIALS','PALLETS','CONTAINERS','BOOKS','MEDIA','ARTWORK','COLLECTIBLES','DIGITAL') DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_product` (`id`, `company_id`, `supplier_id`, `sku`, `name`, `description`, `barcode`, `unit`, `category`, `min_stock_level`, `reorder_point`, `active`, `product_type`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'ABP-SKU-001', 'USB-C punjač 65W', 'Univerzalni punjač za laptopove', '8590001000001', 'kom', 'IT oprema', 10, 20, b'1', 'ELECTRONICS', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(2, 1, 3, 'ABP-SKU-002', 'Ethernet kabl CAT6 5m', 'Mrežni kabl za patch panel', '8590001000002', 'kom', 'Mreža', 25, 50, b'1', 'NETWORK_EQUIPMENT', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(3, 1, 2, 'ABP-SKU-003', 'Kancelarijski papir A4', 'Risma 500 listova, 80g', '8590001000003', 'ris', 'Kancelarija', 30, 60, b'1', 'OFFICE_SUPPLIES', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(4, 1, 2, 'ABP-SKU-004', 'Toner HP M404', 'Originalni toner za LaserJet M404', '8590001000004', 'kom', 'Kancelarija', 5, 10, b'1', 'OFFICE_SUPPLIES', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(5, 1, 3, 'ABP-SKU-005', 'HDMI kabl 2m', 'HDMI 2.0 za monitore i projektore', '8590001000005', 'kom', 'Periferija', 15, 30, b'1', 'ACCESSORIES', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(6, 1, 1, 'ABP-SKU-006', 'SSD 512GB Samsung', 'NVMe SSD za radne stanice', '8590001000006', 'kom', 'IT oprema', 8, 15, b'1', 'COMPUTERS', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(7, 1, 1, 'ABP-SKU-007', 'Bežični miš Logitech', 'Ergonomske miševi za kancelariju', '8590001000007', 'kom', 'Periferija', 12, 25, b'1', 'COMPUTERS', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(8, 1, 2, 'ABP-SKU-008', 'Barcode etikete rolna', 'Termalne etikete 1000 kom', '8590001000008', 'rola', 'Magacin', 20, 40, b'1', 'PACKAGING_MATERIALS', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(9, 1, 1, 'ABP-SKU-009', 'UPS Eaton 1500VA', 'Izlazni UPS za server sobu', '8590001000009', 'kom', 'IT oprema', 2, 4, b'1', 'ELECTRONICS', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000'),
(10, 1, 2, 'ABP-SKU-010', 'Kartonska kutija 40x30', 'Pakovanje za slanje opreme', '8590001000010', 'kom', 'Magacin', 50, 100, b'1', 'PACKAGING_MATERIALS', '2026-05-10 10:00:00.000000', '2026-05-10 10:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `_warehouse_zone`
--

CREATE TABLE IF NOT EXISTS `_warehouse_zone` (
  `id` bigint NOT NULL,
  `warehouse_id` bigint NOT NULL,
  `type` enum('RECEPTION','STOCK','COMMISSIONING','SHIPPING') NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_warehouse_zone` (`id`, `warehouse_id`, `type`, `code`, `name`) VALUES
(1, 2, 'RECEPTION', 'REC-01', 'Zona prijema'),
(2, 2, 'STOCK', 'STK-01', 'Glavno skladište'),
(3, 2, 'COMMISSIONING', 'COM-01', 'Zona komisioniranja'),
(4, 2, 'SHIPPING', 'SHP-01', 'Zona otpreme');

-- --------------------------------------------------------

--
-- Table structure for table `_warehouse_aisle`
--

CREATE TABLE IF NOT EXISTS `_warehouse_aisle` (
  `id` bigint NOT NULL,
  `zone_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_warehouse_aisle` (`id`, `zone_id`, `name`, `code`, `created_at`, `updated_at`) VALUES
(1, 2, 'Prolaz A', 'A', '2026-06-01 08:00:00.000000', '2026-06-01 08:00:00.000000'),
(2, 2, 'Prolaz B', 'B', '2026-06-01 08:00:00.000000', '2026-06-01 08:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `_warehouse_shelf`
--

CREATE TABLE IF NOT EXISTS `_warehouse_shelf` (
  `id` bigint NOT NULL,
  `aisle_id` bigint DEFAULT NULL,
  `level` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_warehouse_shelf` (`id`, `aisle_id`, `level`, `name`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'Polica A1', '2026-06-01 08:00:00.000000', '2026-06-01 08:00:00.000000'),
(2, 1, 2, 'Polica A2', '2026-06-01 08:00:00.000000', '2026-06-01 08:00:00.000000'),
(3, 2, 1, 'Polica B1', '2026-06-01 08:00:00.000000', '2026-06-01 08:00:00.000000'),
(4, 2, 2, 'Polica B2', '2026-06-01 08:00:00.000000', '2026-06-01 08:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `_storage_location`
--

CREATE TABLE IF NOT EXISTS `_storage_location` (
  `id` bigint NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `shelf_id` bigint DEFAULT NULL,
  `barcode` varchar(255) NOT NULL,
  `max_weight` double DEFAULT NULL,
  `current_weight` double DEFAULT NULL,
  `max_volume` double DEFAULT NULL,
  `current_volume` double DEFAULT NULL,
  `preferred_product_id` bigint DEFAULT NULL,
  `preferred_type` enum('GENERAL_MERCHANDISE','OFFICE_SUPPLIES','HOUSEHOLD_GOODS','PERSONAL_CARE','TOYS','SPORTS_EQUIPMENT','PET_SUPPLIES','ELECTRONICS','COMPUTERS','MOBILE_DEVICES','CONSUMER_ELECTRONICS','ACCESSORIES','NETWORK_EQUIPMENT','APPLIANCES','CLOTHING_TEXTILE','FOOTWEAR','FASHION_ACCESSORIES','PERISHABLE_FOOD','NON_PERISHABLE_FOOD','FROZEN_FOOD','DAIRY_PRODUCTS','MEAT_PRODUCTS','FRUITS_VEGETABLES','BEVERAGES','ALCOHOLIC_BEVERAGES','HAZARDOUS_MATERIAL','FLAMMABLE','TOXIC','CORROSIVE','FRAGILE','OVERSIZED','HEAVY_GOODS','TEMPERATURE_SENSITIVE','HIGH_VALUE','SPARE_PARTS','RAW_MATERIALS','SEMI_FINISHED_GOODS','FINISHED_GOODS','INDUSTRIAL_EQUIPMENT','CONSTRUCTION_MATERIALS','AUTOMOTIVE_PARTS','MACHINERY','CHEMICALS','PHARMACEUTICALS','MEDICAL_DEVICES','COSMETICS','PACKAGING_MATERIALS','PALLETS','CONTAINERS','BOOKS','MEDIA','ARTWORK','COLLECTIBLES','DIGITAL') DEFAULT NULL,
  `is_occupied` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_storage_location` (`id`, `name`, `shelf_id`, `barcode`, `max_weight`, `current_weight`, `max_volume`, `current_volume`, `preferred_product_id`, `preferred_type`, `is_occupied`, `created_at`, `updated_at`) VALUES
(1, 'Lokacija A1-01', 1, 'LOC-KRV-A1-01', 200, 45, 2, 0.4, 1, 'ELECTRONICS', b'1', '2026-06-01 08:30:00.000000', '2026-06-20 10:00:00.000000'),
(2, 'Lokacija A1-02', 1, 'LOC-KRV-A1-02', 200, 30, 2, 0.3, 2, 'NETWORK_EQUIPMENT', b'1', '2026-06-01 08:30:00.000000', '2026-06-20 10:00:00.000000'),
(3, 'Lokacija A2-01', 2, 'LOC-KRV-A2-01', 150, 80, 1.5, 0.8, 3, 'OFFICE_SUPPLIES', b'1', '2026-06-01 08:30:00.000000', '2026-06-20 10:00:00.000000'),
(4, 'Lokacija A2-02', 2, 'LOC-KRV-A2-02', 100, 12, 1, 0.2, 4, 'OFFICE_SUPPLIES', b'1', '2026-06-01 08:30:00.000000', '2026-06-20 10:00:00.000000'),
(5, 'Lokacija B1-01', 3, 'LOC-KRV-B1-01', 200, 25, 2, 0.5, 5, 'ACCESSORIES', b'1', '2026-06-01 08:30:00.000000', '2026-06-20 10:00:00.000000'),
(6, 'Lokacija B1-02', 3, 'LOC-KRV-B1-02', 100, 8, 1, 0.15, 6, 'COMPUTERS', b'1', '2026-06-01 08:30:00.000000', '2026-06-20 10:00:00.000000'),
(7, 'Prijemna rampa', NULL, 'LOC-KRV-REC-01', 500, 120, 5, 1.2, NULL, NULL, b'1', '2026-06-01 08:30:00.000000', '2026-06-28 09:00:00.000000'),
(8, 'Otpremna rampa', NULL, 'LOC-KRV-SHP-01', 500, 0, 5, 0, NULL, NULL, b'0', '2026-06-01 08:30:00.000000', '2026-06-01 08:30:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `_warehouse_user`
--

CREATE TABLE IF NOT EXISTS `_warehouse_user` (
  `warehouse_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `role` enum('ADMIN','MANAGER','WORKER','VIEWER') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_warehouse_user` (`warehouse_id`, `user_id`, `role`) VALUES
(2, 1, 'ADMIN'),
(2, 2, 'WORKER');

-- --------------------------------------------------------

--
-- Table structure for table `_inventory_item`
--

CREATE TABLE IF NOT EXISTS `_inventory_item` (
  `id` bigint NOT NULL,
  `storage_location_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `amount` double NOT NULL,
  `last_updated` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_inventory_item` (`id`, `storage_location_id`, `product_id`, `amount`, `last_updated`) VALUES
(1, 1, 1, 35, '2026-06-20 10:00:00.000000'),
(2, 2, 2, 80, '2026-06-20 10:00:00.000000'),
(3, 3, 3, 120, '2026-06-20 10:00:00.000000'),
(4, 4, 4, 6, '2026-06-20 10:00:00.000000'),
(5, 5, 5, 42, '2026-06-20 10:00:00.000000'),
(6, 6, 6, 14, '2026-06-20 10:00:00.000000'),
(7, 6, 7, 22, '2026-06-20 10:00:00.000000'),
(8, 3, 8, 55, '2026-06-20 10:00:00.000000'),
(9, 7, 10, 200, '2026-06-28 09:00:00.000000'),
(10, 1, 9, 3, '2026-06-18 14:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `_warehouse_stock`
--

CREATE TABLE IF NOT EXISTS `_warehouse_stock` (
  `id` bigint NOT NULL,
  `warehouse_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_warehouse_stock` (`id`, `warehouse_id`, `product_id`, `amount`) VALUES
(1, 2, 1, 35),
(2, 2, 2, 80),
(3, 2, 3, 120),
(4, 2, 4, 6),
(5, 2, 5, 42),
(6, 2, 6, 14),
(7, 2, 7, 22),
(8, 2, 8, 55),
(9, 2, 9, 3),
(10, 2, 10, 200);

-- --------------------------------------------------------

--
-- Table structure for table `_warehouse_task`
--

CREATE TABLE IF NOT EXISTS `_warehouse_task` (
  `id` bigint NOT NULL,
  `assigned_user_id` bigint NOT NULL,
  `source_location_id` bigint DEFAULT NULL,
  `destination_location_id` bigint NOT NULL,
  `completed` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `_warehouse_task` (`id`, `assigned_user_id`, `source_location_id`, `destination_location_id`, `completed`, `created_at`) VALUES
(1, 2, 7, 1, b'0', '2026-06-28 09:15:00.000000'),
(2, 2, 3, 8, b'1', '2026-06-25 11:00:00.000000');

-- --------------------------------------------------------

--
-- Table structure for table `warehouse_task_items`
--

CREATE TABLE IF NOT EXISTS `warehouse_task_items` (
  `task_id` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `amount` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT IGNORE INTO `warehouse_task_items` (`task_id`, `product_id`, `amount`) VALUES
(1, 1, 15),
(2, 3, 10);

-- --------------------------------------------------------

--
-- Table structure for table `_project`
--

CREATE TABLE IF NOT EXISTS `_project` (
  `company_id` bigint DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `team_id` bigint DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_project_task`
--

CREATE TABLE IF NOT EXISTS `_project_task` (
  `date_due` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `project_id` bigint DEFAULT NULL,
  `status_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` enum('HIGH','LOW','NORMAL','URGENT') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_project_task_users`
--

CREATE TABLE IF NOT EXISTS `_project_task_users` (
  `project_task_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_project_users`
--

CREATE TABLE IF NOT EXISTS `_project_users` (
  `project_id` bigint NOT NULL,
  `user_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_task_note`
--

CREATE TABLE IF NOT EXISTS `_task_note` (
  `date_time` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `project_task_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_task_note_seq`
--

CREATE TABLE IF NOT EXISTS `_task_note_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_task_status`
--

CREATE TABLE IF NOT EXISTS `_task_status` (
  `id` bigint NOT NULL,
  `project_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `_task_status_seq`
--

CREATE TABLE IF NOT EXISTS `_task_status_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--

-- --------------------------------------------------------

--
-- Indexes for dumped tables
--

--
-- Indexes for table `_facility`
--
ALTER TABLE `_facility`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtirx3lw2bl3bvadd2smj1dvy3` (`company_id`),
  ADD KEY `FKb56hb5snuobejknvap83sk5un` (`location_id`),
  ADD KEY `FK_facility_manager` (`manager_id`);

--
-- Indexes for table `_workstation`
--
ALTER TABLE `_workstation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2cnf4ls1dhj1i7qcj7pxf2ioo` (`facility_id`);

--
-- Indexes for table `_calendar_events`
--
ALTER TABLE `_calendar_events`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_city`
--
ALTER TABLE `_city`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_company`
--
ALTER TABLE `_company`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_company_support`
--
ALTER TABLE `_company_support`
  ADD KEY `FKi2u7oary0sb9o0e9jatfjti1s` (`company_id`);

--
-- Indexes for table `_contact`
--
ALTER TABLE `_contact`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKawpq1iwa6o1gprtuunecukw3o` (`contacts_list_id`);

--
-- Indexes for table `_contacts_list`
--
ALTER TABLE `_contacts_list`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_contact_message`
--
ALTER TABLE `_contact_message`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_conversation`
--
ALTER TABLE `_conversation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_conv_company` (`company_id`),
  ADD KEY `idx_conv_lastmsgat` (`last_message_at`);

--
-- Indexes for table `_conversation_participant`
--
ALTER TABLE `_conversation_participant`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_conv_participant` (`conversation_id`,`user_id`),
  ADD KEY `idx_part_user` (`user_id`),
  ADD KEY `idx_part_conv` (`conversation_id`);

--
-- Indexes for table `_country`
--
ALTER TABLE `_country`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_department`
--
ALTER TABLE `_department`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_event_participants`
--
ALTER TABLE `_event_participants`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK2a9grniblyaefdl1c5678e8pc` (`event_id`);

--
-- Indexes for table `_location`
--
ALTER TABLE `_location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_message`
--
ALTER TABLE `_message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `idx_msg_conv` (`conversation_id`),
  ADD KEY `idx_msg_created` (`created_at`);

--
-- Indexes for table `_movable_asset`
--
ALTER TABLE `_movable_asset`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKl5yxncngv9ghx1dvune22ht5l` (`movable_asset_company_id`),
  ADD KEY `FK25gpcp1116o2ew4g0gujpqf80` (`current_movable_asset_user_id`),
  ADD KEY `FK8gtjc5u6pn65dwcqxgt24b97h` (`current_movable_asset_issued_by_id`),
  ADD KEY `FK985dyariy5iroojy83jvumods` (`movable_asset_location_id`);

--
-- Indexes for table `_project`
--
ALTER TABLE `_project`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_project_task`
--
ALTER TABLE `_project_task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKjskfa33s2rfy7mif4s6lyk4gh` (`project_id`),
  ADD KEY `FK9mcblkdrchw8ij6of6pmpb648` (`user_id`);

--
-- Indexes for table `_project_task_users`
--
ALTER TABLE `_project_task_users`
  ADD PRIMARY KEY (`project_task_id`,`user_id`),
  ADD KEY `FKtfd1tnd1q2atgfp98wgkqvce4` (`user_id`);

--
-- Indexes for table `_project_users`
--
ALTER TABLE `_project_users`
  ADD PRIMARY KEY (`project_id`,`user_id`),
  ADD KEY `FKhrjs46xxdlccvu4a3k36fdtda` (`user_id`);

--
-- Indexes for table `_region`
--
ALTER TABLE `_region`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_district`
--
ALTER TABLE `_district`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_district_name` (`name`),
  ADD KEY `FK_district_region` (`region_id`);

--
-- Indexes for table `_task_note`
--
ALTER TABLE `_task_note`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn0ed9k4qtkq1x62000vshm7ij` (`project_task_id`);

--
-- Indexes for table `_task_status`
--
ALTER TABLE `_task_status`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_team`
--
ALTER TABLE `_team`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6385i6jstycbmw7yqa871tjl7` (`department_id`);

--
-- Indexes for table `_team_users`
--
ALTER TABLE `_team_users`
  ADD KEY `FKj5kx03r23jyk2gmowubborxmu` (`team_id`);

--
-- Indexes for table `_user`
--
ALTER TABLE `_user`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_user_profile`
--
ALTER TABLE `_user_profile`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `_user_roles`
--
ALTER TABLE `_user_roles`
  ADD KEY `FK1knb08qasyc3njr6m6je05u4f` (`user_id`);

--
-- Indexes for table `_supplier`
--
ALTER TABLE `_supplier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_supplier_company` (`company_id`);

--
-- Indexes for table `_product`
--
ALTER TABLE `_product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_product_company` (`company_id`),
  ADD KEY `FK_product_supplier` (`supplier_id`);

--
-- Indexes for table `_warehouse_zone`
--
ALTER TABLE `_warehouse_zone`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_warehouse_zone_warehouse` (`warehouse_id`);

--
-- Indexes for table `_warehouse_aisle`
--
ALTER TABLE `_warehouse_aisle`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_warehouse_aisle_zone` (`zone_id`);

--
-- Indexes for table `_warehouse_shelf`
--
ALTER TABLE `_warehouse_shelf`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_warehouse_shelf_aisle` (`aisle_id`);

--
-- Indexes for table `_storage_location`
--
ALTER TABLE `_storage_location`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_storage_location_barcode` (`barcode`),
  ADD KEY `FK_storage_location_shelf` (`shelf_id`),
  ADD KEY `FK_storage_location_preferred_product` (`preferred_product_id`);

--
-- Indexes for table `_warehouse_user`
--
ALTER TABLE `_warehouse_user`
  ADD PRIMARY KEY (`warehouse_id`,`user_id`),
  ADD UNIQUE KEY `uk_warehouse_user` (`warehouse_id`,`user_id`),
  ADD KEY `idx_wu_warehouse` (`warehouse_id`),
  ADD KEY `idx_wu_user` (`user_id`);

--
-- Indexes for table `_inventory_item`
--
ALTER TABLE `_inventory_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_inventory_item_storage_location` (`storage_location_id`),
  ADD KEY `FK_inventory_item_product` (`product_id`);

--
-- Indexes for table `_warehouse_stock`
--
ALTER TABLE `_warehouse_stock`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_warehouse_stock_warehouse` (`warehouse_id`),
  ADD KEY `FK_warehouse_stock_product` (`product_id`);

--
-- Indexes for table `_warehouse_task`
--
ALTER TABLE `_warehouse_task`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_warehouse_task_assigned_user` (`assigned_user_id`),
  ADD KEY `FK_warehouse_task_source_location` (`source_location_id`),
  ADD KEY `FK_warehouse_task_destination_location` (`destination_location_id`);

--
-- Indexes for table `warehouse_task_items`
--
ALTER TABLE `warehouse_task_items`
  ADD KEY `FK_warehouse_task_items_task` (`task_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `_calendar_events`
--
ALTER TABLE `_calendar_events`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `_city`
--
ALTER TABLE `_city`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=149;

--
-- AUTO_INCREMENT for table `_company`
--
ALTER TABLE `_company`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `_contact`
--
ALTER TABLE `_contact`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `_contacts_list`
--
ALTER TABLE `_contacts_list`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `_contact_message`
--
ALTER TABLE `_contact_message`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_conversation`
--
ALTER TABLE `_conversation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `_conversation_participant`
--
ALTER TABLE `_conversation_participant`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `_country`
--
ALTER TABLE `_country`
  MODIFY `id` smallint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `_department`
--
ALTER TABLE `_department`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_event_participants`
--
ALTER TABLE `_event_participants`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `_facility`
--
ALTER TABLE `_facility`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `_workstation`
--
ALTER TABLE `_workstation`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `_location`
--
ALTER TABLE `_location`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `_message`
--
ALTER TABLE `_message`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `_movable_asset`
--
ALTER TABLE `_movable_asset`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `_project`
--
ALTER TABLE `_project`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_project_task`
--
ALTER TABLE `_project_task`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_task_note`
--
ALTER TABLE `_task_note`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_task_status`
--
ALTER TABLE `_task_status`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_region`
--
ALTER TABLE `_region`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `_district`
--
ALTER TABLE `_district`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT for table `_team`
--
ALTER TABLE `_team`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `_user`
--
ALTER TABLE `_user`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `_user_profile`
--
ALTER TABLE `_user_profile`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `_supplier`
--
ALTER TABLE `_supplier`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `_product`
--
ALTER TABLE `_product`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `_warehouse_zone`
--
ALTER TABLE `_warehouse_zone`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `_warehouse_aisle`
--
ALTER TABLE `_warehouse_aisle`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `_warehouse_shelf`
--
ALTER TABLE `_warehouse_shelf`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `_storage_location`
--
ALTER TABLE `_storage_location`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `_inventory_item`
--
ALTER TABLE `_inventory_item`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `_warehouse_stock`
--
ALTER TABLE `_warehouse_stock`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `_warehouse_task`
--
ALTER TABLE `_warehouse_task`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `_company_support`
--
ALTER TABLE `_company_support`
  ADD CONSTRAINT `FKi2u7oary0sb9o0e9jatfjti1s` FOREIGN KEY (`company_id`) REFERENCES `_company` (`id`);

--
-- Constraints for table `_contact`
--
ALTER TABLE `_contact`
  ADD CONSTRAINT `FKawpq1iwa6o1gprtuunecukw3o` FOREIGN KEY (`contacts_list_id`) REFERENCES `_contacts_list` (`id`);

--
-- Constraints for table `_conversation_participant`
--
ALTER TABLE `_conversation_participant`
  ADD CONSTRAINT `FKg972eklo6pbmd3splltm0he9v` FOREIGN KEY (`conversation_id`) REFERENCES `_conversation` (`id`);

--
-- Constraints for table `_event_participants`
--
ALTER TABLE `_event_participants`
  ADD CONSTRAINT `FK2a9grniblyaefdl1c5678e8pc` FOREIGN KEY (`event_id`) REFERENCES `_calendar_events` (`id`);

--
-- Constraints for table `_facility`
--
ALTER TABLE `_facility`
  ADD CONSTRAINT `FKb56hb5snuobejknvap83sk5un` FOREIGN KEY (`location_id`) REFERENCES `_location` (`id`),
  ADD CONSTRAINT `FKtirx3lw2bl3bvadd2smj1dvy3` FOREIGN KEY (`company_id`) REFERENCES `_company` (`id`),
  ADD CONSTRAINT `FK_facility_manager` FOREIGN KEY (`manager_id`) REFERENCES `_user` (`id`);

--
-- Constraints for table `_workstation`
--
ALTER TABLE `_workstation`
  ADD CONSTRAINT `FK2cnf4ls1dhj1i7qcj7pxf2ioo` FOREIGN KEY (`facility_id`) REFERENCES `_facility` (`id`);

--
-- Constraints for table `_message`
--
ALTER TABLE `_message`
  ADD CONSTRAINT `FK58x69j9pqcyn75ls5t6o67nd7` FOREIGN KEY (`conversation_id`) REFERENCES `_conversation` (`id`);

--
-- Constraints for table `_movable_asset`
--
ALTER TABLE `_movable_asset`
  ADD CONSTRAINT `FK25gpcp1116o2ew4g0gujpqf80` FOREIGN KEY (`current_movable_asset_user_id`) REFERENCES `_user` (`id`),
  ADD CONSTRAINT `FK8gtjc5u6pn65dwcqxgt24b97h` FOREIGN KEY (`current_movable_asset_issued_by_id`) REFERENCES `_user` (`id`),
  ADD CONSTRAINT `FK985dyariy5iroojy83jvumods` FOREIGN KEY (`movable_asset_location_id`) REFERENCES `_location` (`id`),
  ADD CONSTRAINT `FKl5yxncngv9ghx1dvune22ht5l` FOREIGN KEY (`movable_asset_company_id`) REFERENCES `_company` (`id`);

--
-- Constraints for table `_project_task`
--
ALTER TABLE `_project_task`
  ADD CONSTRAINT `FK9mcblkdrchw8ij6of6pmpb648` FOREIGN KEY (`user_id`) REFERENCES `_user` (`id`),
  ADD CONSTRAINT `FKjskfa33s2rfy7mif4s6lyk4gh` FOREIGN KEY (`project_id`) REFERENCES `_project` (`id`);

--
-- Constraints for table `_project_task_users`
--
ALTER TABLE `_project_task_users`
  ADD CONSTRAINT `FKj4q9rf7pbun0uxbxghwsajicr` FOREIGN KEY (`project_task_id`) REFERENCES `_project_task` (`id`),
  ADD CONSTRAINT `FKtfd1tnd1q2atgfp98wgkqvce4` FOREIGN KEY (`user_id`) REFERENCES `_user` (`id`);

--
-- Constraints for table `_project_users`
--
ALTER TABLE `_project_users`
  ADD CONSTRAINT `FKhrjs46xxdlccvu4a3k36fdtda` FOREIGN KEY (`user_id`) REFERENCES `_user` (`id`),
  ADD CONSTRAINT `FKmouiiifi6xxxungof951ddejg` FOREIGN KEY (`project_id`) REFERENCES `_project` (`id`);

--
-- Constraints for table `_task_note`
--
ALTER TABLE `_task_note`
  ADD CONSTRAINT `FKn0ed9k4qtkq1x62000vshm7ij` FOREIGN KEY (`project_task_id`) REFERENCES `_project_task` (`id`);

--
-- Constraints for table `_team`
--
ALTER TABLE `_team`
  ADD CONSTRAINT `FK6385i6jstycbmw7yqa871tjl7` FOREIGN KEY (`department_id`) REFERENCES `_department` (`id`);

--
-- Constraints for table `_team_users`
--
ALTER TABLE `_team_users`
  ADD CONSTRAINT `FKj5kx03r23jyk2gmowubborxmu` FOREIGN KEY (`team_id`) REFERENCES `_team` (`id`);

--
-- Constraints for table `_user_roles`
--
ALTER TABLE `_user_roles`
  ADD CONSTRAINT `FK1knb08qasyc3njr6m6je05u4f` FOREIGN KEY (`user_id`) REFERENCES `_user` (`id`);

--
-- Constraints for table `_supplier`
--
ALTER TABLE `_supplier`
  ADD CONSTRAINT `FK_supplier_company` FOREIGN KEY (`company_id`) REFERENCES `_company` (`id`);

--
-- Constraints for table `_product`
--
ALTER TABLE `_product`
  ADD CONSTRAINT `FK_product_company` FOREIGN KEY (`company_id`) REFERENCES `_company` (`id`),
  ADD CONSTRAINT `FK_product_supplier` FOREIGN KEY (`supplier_id`) REFERENCES `_supplier` (`id`);

--
-- Constraints for table `_warehouse_zone`
--
ALTER TABLE `_warehouse_zone`
  ADD CONSTRAINT `FK_warehouse_zone_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `_facility` (`id`);

--
-- Constraints for table `_warehouse_aisle`
--
ALTER TABLE `_warehouse_aisle`
  ADD CONSTRAINT `FK_warehouse_aisle_zone` FOREIGN KEY (`zone_id`) REFERENCES `_warehouse_zone` (`id`);

--
-- Constraints for table `_warehouse_shelf`
--
ALTER TABLE `_warehouse_shelf`
  ADD CONSTRAINT `FK_warehouse_shelf_aisle` FOREIGN KEY (`aisle_id`) REFERENCES `_warehouse_aisle` (`id`);

--
-- Constraints for table `_storage_location`
--
ALTER TABLE `_storage_location`
  ADD CONSTRAINT `FK_storage_location_preferred_product` FOREIGN KEY (`preferred_product_id`) REFERENCES `_product` (`id`),
  ADD CONSTRAINT `FK_storage_location_shelf` FOREIGN KEY (`shelf_id`) REFERENCES `_warehouse_shelf` (`id`);

--
-- Constraints for table `_warehouse_user`
--
ALTER TABLE `_warehouse_user`
  ADD CONSTRAINT `FK_warehouse_user_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `_facility` (`id`),
  ADD CONSTRAINT `FK_warehouse_user_user` FOREIGN KEY (`user_id`) REFERENCES `_user` (`id`);

--
-- Constraints for table `_inventory_item`
--
ALTER TABLE `_inventory_item`
  ADD CONSTRAINT `FK_inventory_item_product` FOREIGN KEY (`product_id`) REFERENCES `_product` (`id`),
  ADD CONSTRAINT `FK_inventory_item_storage_location` FOREIGN KEY (`storage_location_id`) REFERENCES `_storage_location` (`id`);

--
-- Constraints for table `_warehouse_stock`
--
ALTER TABLE `_warehouse_stock`
  ADD CONSTRAINT `FK_warehouse_stock_product` FOREIGN KEY (`product_id`) REFERENCES `_product` (`id`),
  ADD CONSTRAINT `FK_warehouse_stock_warehouse` FOREIGN KEY (`warehouse_id`) REFERENCES `_facility` (`id`);

--
-- Constraints for table `_warehouse_task`
--
ALTER TABLE `_warehouse_task`
  ADD CONSTRAINT `FK_warehouse_task_assigned_user` FOREIGN KEY (`assigned_user_id`) REFERENCES `_user` (`id`),
  ADD CONSTRAINT `FK_warehouse_task_destination_location` FOREIGN KEY (`destination_location_id`) REFERENCES `_storage_location` (`id`),
  ADD CONSTRAINT `FK_warehouse_task_source_location` FOREIGN KEY (`source_location_id`) REFERENCES `_storage_location` (`id`);

--
-- Constraints for table `warehouse_task_items`
--
ALTER TABLE `warehouse_task_items`
  ADD CONSTRAINT `FK_warehouse_task_items_task` FOREIGN KEY (`task_id`) REFERENCES `_warehouse_task` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;