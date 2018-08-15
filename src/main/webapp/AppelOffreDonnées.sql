3-- phpMyAdmin SQL Dump
-- version 4.4.3
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Lun 17 Août 2015 à 16:14
-- Version du serveur :  5.6.24
-- Version de PHP :  5.6.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `appelOffre`
--



--
-- Contenu de la table `users`
--

INSERT INTO `users` (`username`, `enabled`, `nom`, `password`) VALUES
('admin', 0, 'Kaporal Phoenix', '$2a$10$SLQQ9WZux0hmoMTpXFu2au3/lAkmbNHLvVx84utZEJSDbyTcDNwEO'),
('brice', 1, 'GUEMKAM Brice', '$2a$10$7/84X0KVW4ws3x92R1z0/uJ.0X0qmHw4HCTP7gn1EskUXgLdkS.n6'),
('cfera', 1, 'BALAFERA CRUZ', '$2a$10$VQjChglEC6W92txpuEebY.OSiCXerXw6WXwjc2aVubrS/sdlkvjC2'),
('delaroos', 1, 'KANMOGNE DELANO ROOSVELT', '$2a$10$YYW0YMecApPzYqc94nuD0uZ0qWRELzsPV73NaqRZvSstEtfcXf/nO'),
('kamguy', 1, 'KAMSU GUY', '$2a$10$0GgcWnUX0lPMMI9r57iQbeNU1OeAKP4Vz/zOfVCM7zWHl8.x1MYFK'),
('maman', 1, 'moimeme', '$2a$10$FupuKgRyA1nXflNQQwogTu/FX2QG6WeMxLPNX6nrPjNuL7o3fQzsy'),
('moimeme', 1, 'moimeme', '$2a$10$AwyDu7AwTOOmmfYo3NagO.71rwUKbu/1I.ds7SbrMZ9ZjlJ7SEoyW'),
('rome bond', 1, 'MANGUELE EBONDA Romuald', '$2a$10$yWIZIUorfdnjf6dBb8jyGOxBengMhJg0WmxIxg8SAAJMVmUmF4eYq'),
('sando', 1, 'GUEMKAM SANDO Brice', '$2a$10$1sW7LimIXxeZkSBjAIjBjuFxXK50HzrdmioLvtp.eIrzTvs18IoWe');

--
-- Contenu de la table `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `role`, `user_username`) VALUES
(1, 'ROLE_ADMIN', 'admin'),
(2, 'ROLE_ADMIN', 'sando'),
(7, 'ROLE_COMMERCIAL', 'kamguy'),
(8, 'ROLE_COMMERCIAL', 'delaroos'),
(10, 'ROLE_TRESORIER', 'rome bond'),
(12, 'ROLE_TRESORIER', 'cfera');


--
-- Contenu de la table `Banque`
--

INSERT INTO `Banque` (`id`, `version`, `code`, `libelle`, `deleted`) VALUES
(1, 2, 'BQ2015001', 'AFRILAND FIRST BANK', 1),
(2, 2, 'BQ2015002', 'COMMERCIAL BANK OF CAMEROON', 0),
(3, 2, 'BQ2015003', 'UBA BANK', 0),
(4, 1, 'BQ2015004', 'UBC BANK', 0),
(5, 2, 'BQ2015005', 'ATLANTIC BANK', 0);


--
-- Contenu de la table `Filiale`
--

INSERT INTO `Filiale` (`id`, `version`, `agence`, `code`, `nom`, `deleted`) VALUES
(1, 1, 'Bafoussam', 'CF20150001', 'Silver Rims Ltd', 1),
(2, 4, 'Mokolo - Yaoundé', 'CF20150002', 'The Stanton Hoods Corp', 0),
(3, 0, 'Mokolo - Yaoundé', 'CF20150003', 'Pacific rims', 0);


--
-- Contenu de la table `TypeCaution`
--

INSERT INTO `TypeCaution` (`id`, `version`, `code`, `nom`, `pourcentage`, `deleted`) VALUES
(1, 2, 'TD20150001', 'Paiement Par Avaliseur ', '24%', 0),
(2, 0, 'TD20150002', 'Paiement mensuel', '25%', 0),
(3, 1, 'TD20150003', 'Paiement Cash', '50%', 1);

--
-- Contenu de la table `TypeMateriel`
--

INSERT INTO `TypeMateriel` (`id`, `version`, `code`, `nom`, `deleted`) VALUES
(1, 5, 'TM20150001', 'Matériaux de Jardinage ', 1),
(2, 0, 'TM20150002', 'Objets Décoratifs', 0),
(3, 0, 'TM20150003', 'Pièces Automobile', 0),
(4, 1, 'TM20150004', 'Matériaux de construction', 0),
(5, 1, 'TM201507001', 'Matériel de Charpenterie ', 0),
(6, 1, 'TM201507001', 'Matériel de Charpenterie ', 0),
(7, 2, 'TM201508001', 'Matériel Chirurgical', 0);


--
-- Contenu de la table `Materiel`
--

INSERT INTO `Materiel` (`id`, `version`, `code`, `description`, `nom`, `typeMateriel_id`, `deleted`) VALUES
(1, 0, 'MJ20150001', 'pour rattisser', 'Rateau', 1, 0),
(2, 0, 'MJ20150002', 'pour tailler les fleurs', 'Grands Sciseaux', 1, 0),
(3, 0, 'OD2015001', 'Pot de fleurs pour trefles', 'Pots de trèfles', 2, 0),
(4, 0, 'OD2015002', 'Tableaux décoratifs pour salon', 'Van ghogg', 2, 0),
(5, 0, 'PA20150001', 'Roues pour courses automobiles', 'Roues Konîg', 3, 0),
(6, 0, 'PA20150002', 'Peintures particulières pour véhicules de courses', 'Vinyls', 3, 0),
(7, 3, 'MC20150001', 'matériel pour le transport de sables, gravier et ciment', 'Brouette', 4, 1),
(8, 0, 'MC20150002', 'utilisé pour tourner le béton ', 'Pelle', 4, 0);

--
-- Contenu de la table `AppelOffre`
--

INSERT INTO `AppelOffre` (`id`, `version`, `dateDepot`, `delaiDeValidite`, `etat`, `intitule`, `maitreDouvrage`, `numero`, `pieceJointe1`, `pieceJointe2`, `pieceJointe3`, `pieceJointe4`, `pieceJointe5`, `pieceJointe6`, `pieceJointe7`, `pieceJointe8`, `filiale_id`, `dateModification`, `user_user_role_id`, `deleted`) VALUES
(2, 7, '2015-07-28', '12', 'En cours', 'LA PRO-FORMAT POUR FOKOU', 'SANDO', 'AP20150725', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, '2015-08-10', 2, 0),
(3, 11, '2015-08-17', '12', 'En cours', 'TEST', 'SANDO', 'AP20150727',  NULL, NULL, NULL, '', NULL, NULL, NULL, NULL, 2, '2015-08-17', 2, 0);

--
-- Contenu de la table `Caution`
--

INSERT INTO `Caution` (`id`, `version`, `dateDebut`, `dateFin`, `montant`, `montantMarche`, `numero`, `referenceMarche`, `appelOffre_id`, `banque_id`, `commercial_user_role_id`, `typeCaution_id`, `deleted`) VALUES
(5, 0, '2015-07-25', '2015-08-25', 100000, 1000000, 'CA2015072501', 'RM2015072501', 2, 1, 7, 2, 0),
(6, 0, '2015-07-27', '2015-08-31', 600000, 1000000, 'CA2015072502', 'RM2015072502', 2, 2, 8, 3, 0),
(8, 0, '2015-08-05', '2015-08-18', 3000000, 5000000, 'CA2015072505', 'RM2015072501', 3, 1, 7, 3, 0),
(9, 0, '2015-08-01', '2015-08-19', 9000000, 5000000, 'CA2015072502', 'RM2015072502', 3, 2, 7, 3, 0);

--
-- Contenu de la table `LigneAppel`
--

INSERT INTO `LigneAppel` (`id`, `version`, `prixUnitaire`, `quantite`, `appelOffre_id`, `materiel_id`, `deleted`) VALUES
(7, 0, 1000, 5, 2, 1, 0),
(8, 0, 2500, 3, 2, 2, 0),
(9, 0, 6300, 5, 2, 3, 0),
(12, 0, 5000, 2, 3, 3, 0),
(13, 0, 25000, 5, 3, 7, 0);



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;