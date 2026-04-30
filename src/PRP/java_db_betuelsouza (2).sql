-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Tempo de geração: 30-Abr-2026 às 08:44
-- Versão do servidor: 9.1.0
-- versão do PHP: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `java_db_betuelsouza`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE IF NOT EXISTS `cliente` (
  `cod_cliente` int NOT NULL AUTO_INCREMENT,
  `nome_cliente` varchar(80) NOT NULL,
  `categoria_cliente` varchar(50) NOT NULL,
  `data_nasc` date NOT NULL,
  `nif` int NOT NULL,
  `email` varchar(150) NOT NULL,
  `genero` varchar(40) NOT NULL,
  `cartao_identificacao` int NOT NULL,
  `numero_telefone` int NOT NULL,
  `redes_sociais` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `morada` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `zona_postal` int NOT NULL,
  `cod_postal` int NOT NULL,
  `localidade` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nacionalidade` varchar(70) NOT NULL,
  `pais` varchar(80) NOT NULL,
  `data_registo` date NOT NULL,
  `imagem` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `OBS` text NOT NULL,
  PRIMARY KEY (`cod_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`cod_cliente`, `nome_cliente`, `categoria_cliente`, `data_nasc`, `nif`, `email`, `genero`, `cartao_identificacao`, `numero_telefone`, `redes_sociais`, `morada`, `zona_postal`, `cod_postal`, `localidade`, `nacionalidade`, `pais`, `data_registo`, `imagem`, `OBS`) VALUES
(1, 'Ana Sofia Rodrigues', 'Premium', '1985-03-12', 123456789, 'ana.rodrigues@email.pt', 'Feminino', 51234567, 912345678, '@anasofia', 'Rua Augusta, 45, 1º Esq.', 110, 1045, 'Lisboa', 'Portuguesa', 'Portugal', '2023-01-15', 'ana_sofia.jpg', 'Cliente VIP, compras mensais'),
(2, 'João Pedro Mendes', 'Regular', '1990-07-22', 234567890, 'joao.mendes@email.pt', 'Masculino', 62345678, 923456789, '@joaopedro', 'Avenida dos Aliados, 123, 3º', 400, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2024-02-20', 'joao_mendes.png', 'Preferencialmente compra aos fins de semana'),
(3, 'Maria Inês Lopes', 'Ocasional', '2000-11-05', 345678901, 'ines.lopes@email.com', 'Feminino', 73456789, 934567890, '@marines', 'Travessa do Cabral, 8, R/C', 105, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2025-01-10', 'maria_ines.jpg', ''),
(4, 'Carlos Alberto Sousa', 'Premium', '1978-09-30', 456789012, 'carlos.sousa@email.pt', 'Masculino', 84567890, 965432109, 'carlossalmeida', 'Rua de Santa Catarina, 250, 2º', 400, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2022-11-05', 'carlos_sousa.jpg', 'Cliente com cartão empresa'),
(5, 'Marta Filipa Castro', 'Regular', '1995-12-18', 567890123, 'marta.castro@email.pt', 'Feminino', 95678901, 912345670, '@martacastro', 'Praça da Liberdade, 56, 4º Frt', 400, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2024-05-12', 'marta_castro.png', ''),
(6, 'Rui Manuel Ferreira', 'Ocasional', '1982-04-25', 678901234, 'rui.ferreira@email.com', 'Masculino', 10678901, 936789012, 'ruiferreira', 'Rua das Flores, 78, 1ºDto', 120, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2023-09-30', 'rui_ferreira.jpg', 'Compra apenas em promoções'),
(7, 'Sofia Margarida Nunes', 'Premium', '1988-06-14', 789012345, 'sofia.nunes@email.pt', 'Feminino', 11789012, 925678901, '@sofinunes', 'Alameda D. Afonso Henriques, 90, 6ºA', 190, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2021-07-19', 'sofia_nunes.png', 'Assinatura anual de produtos'),
(8, 'Tiago André Martins', 'Regular', '2001-01-29', 890123456, 'tiago.martins@email.pt', 'Masculino', 12890123, 967890123, '@tiagomartins', 'Rua da Boavista, 345, 5º Esq', 410, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2024-08-22', 'tiago_martins.jpg', ''),
(9, 'Patrícia Alexandra Lima', 'Ocasional', '1992-09-08', 901234567, 'patricia.lima@email.com', 'Feminino', 13901234, 912348765, '@patilima', 'Largo do Carmo, 12, 2º', 111, 1000, 'Lisboa', 'Brasileira', 'Brasil', '2025-03-01', 'patricia_lima.png', 'Residente temporária em Portugal'),
(10, 'Fernando José Oliveira', 'Premium', '1975-02-17', 112345678, 'fernando.oliveira@email.pt', 'Masculino', 14012345, 961234567, 'fernando_oliveira', 'Rua do Ouro, 222, 3ºDto', 110, 1000, 'Albufeira', 'Portuguesa', 'Portugal', '2020-12-10', 'fernando_oliveira.jpg', 'Cliente desde a abertura'),
(11, 'Catarina Isabel Pereira', 'Regular', '1998-10-03', 223456789, 'catarina.pereira@email.pt', 'Feminino', 15123456, 934567812, '@catarinapereira', 'Avenida da República, 67, 8ºA', 105, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2023-06-25', 'catarina_pereira.png', ''),
(12, 'Hugo Miguel Almeida', 'Ocasional', '1993-05-21', 334567890, 'hugo.almeida@email.com', 'Masculino', 16234567, 925678943, '@hugomiguel', 'Rua de Cedofeita, 89, R/C', 405, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2024-11-14', 'hugo_almeida.jpg', ''),
(13, 'Liliana Cristina Matos', 'Premium', '1980-12-01', 445678901, 'liliana.matos@email.pt', 'Feminino', 17345678, 913456789, '@lilianamatos', 'Rua do Almada, 432, 1º', 400, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2022-04-07', 'liliana_matos.png', 'Compra para toda a família'),
(14, 'David Bruno Cardoso', 'Regular', '1996-07-19', 556789012, 'david.cardoso@email.pt', 'Masculino', 18456789, 967890123, '@davidcardoso', 'Travessa da Trindade, 21, 3º', 120, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2023-10-29', 'david_cardoso.jpg', ''),
(15, 'Isabel Maria Costa', 'Ocasional', '1987-04-11', 667890123, 'isabel.costa@email.com', 'Feminino', 19567890, 921234567, 'isabelcosta', 'Rua de S. Bento, 150, 4º Frt', 120, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2025-02-18', 'isabel_costa.png', ''),
(16, 'Ricardo Emanuel Marques', 'Premium', '1973-08-27', 778901234, 'ricardo.marques@email.pt', 'Masculino', 20678901, 933456789, '@ricardomarques', 'Avenida Fernão de Magalhães, 88, 7ºB', 300, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2021-01-12', 'ricardo_marques.jpg', 'Cliente com grandes volumes'),
(17, 'Beatriz Leonor Gonçalves', 'Regular', '2002-03-09', 889012345, 'beatriz.goncalves@email.pt', 'Feminino', 21789012, 912398765, '@beatrizg', 'Rua da Sofia, 234, 2º Esq', 300, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2024-09-05', 'beatriz_goncalves.png', ''),
(18, 'Vitor Manuel Dias', 'Ocasional', '1991-11-30', 990123456, 'vitor.dias@email.com', 'Masculino', 22890123, 925443322, '@vitordias', 'Largo de São Domingos, 5, R/C', 110, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2023-07-21', 'vitor_dias.jpg', ''),
(19, 'Tatiana Sofia Ramos', 'Premium', '1984-06-23', 101234567, 'tatiana.ramos@email.pt', 'Feminino', 23901234, 961112233, '@tatianaramos', 'Rua do Alecrim, 48, 3º', 120, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2020-05-16', 'tatiana_ramos.png', 'Cliente com preferência de entrega'),
(20, 'André Filipe Barbosa', 'Regular', '1999-02-14', 112345679, 'andre.barbosa@email.pt', 'Masculino', 24012345, 934567899, '@andrebarbosa', 'Praça do Comércio, 15, 6ºA', 110, 1000, 'Lisboa', 'Portuguesa', 'Portugal', '2025-01-30', 'andre_barbosa.jpg', ''),
(21, 'aaaaaaaa', 'aaaaaaa', '2000-01-01', 999999999, 'aaa@aa.aa', 'Masculino', 999999999, 999999999, 'aaaaaaaaaaaaaaaaaaa', 'aaaaaaaaaaa', 123, 1234, 'Lisboa', 'aaaaaaaa', 'aaaaaaaa', '2000-01-01', 'aaaaaaaa.png', 'aaaaaaa');

-- --------------------------------------------------------

--
-- Estrutura da tabela `fatura`
--

DROP TABLE IF EXISTS `fatura`;
CREATE TABLE IF NOT EXISTS `fatura` (
  `codFatura` int NOT NULL AUTO_INCREMENT,
  `codVenda` int NOT NULL,
  `numeroFatura` varchar(50) NOT NULL,
  `dataFatura` datetime NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `nomePdf` varchar(255) NOT NULL,
  PRIMARY KEY (`codFatura`),
  KEY `fk_fatura_venda` (`codVenda`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `fatura`
--

INSERT INTO `fatura` (`codFatura`, `codVenda`, `numeroFatura`, `dataFatura`, `total`, `nomePdf`) VALUES
(1, 3, 'FT 2026/0001', '2026-04-29 15:21:19', 2109.40, 'faturas/FT 2026_0001.pdf'),
(2, 4, 'FT 2026/0002', '2026-04-29 15:29:49', 1219.70, 'faturas/FT 2026_0002.pdf'),
(3, 5, 'FT 2026/0003', '2026-04-29 18:22:38', 1359.70, 'faturas/FT 2026_0003.pdf');

-- --------------------------------------------------------

--
-- Estrutura da tabela `item_venda`
--

DROP TABLE IF EXISTS `item_venda`;
CREATE TABLE IF NOT EXISTS `item_venda` (
  `codItem` int NOT NULL AUTO_INCREMENT,
  `codVenda` int NOT NULL,
  `codProduto` int NOT NULL,
  `quantidade` int NOT NULL DEFAULT '1',
  `precoUnitario` decimal(10,2) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL,
  PRIMARY KEY (`codItem`),
  KEY `fk_item_venda_venda` (`codVenda`),
  KEY `fk_item_venda_produto` (`codProduto`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `item_venda`
--

INSERT INTO `item_venda` (`codItem`, `codVenda`, `codProduto`, `quantidade`, `precoUnitario`, `subtotal`) VALUES
(1, 1, 1, 1, 439.90, 439.90),
(2, 1, 5, 2, 134.90, 269.80),
(3, 1, 10, 1, 94.90, 94.90),
(4, 2, 1, 2, 439.90, 879.80),
(5, 3, 1, 1, 439.90, 439.90),
(6, 3, 2, 1, 479.90, 479.90),
(7, 3, 5, 1, 134.90, 134.90),
(8, 3, 6, 1, 899.90, 899.90),
(9, 3, 9, 1, 59.90, 59.90),
(10, 3, 10, 1, 94.90, 94.90),
(11, 4, 1, 1, 439.90, 439.90),
(12, 4, 2, 1, 479.90, 479.90),
(13, 4, 3, 1, 299.90, 299.90),
(14, 5, 1, 2, 439.90, 879.80),
(15, 5, 2, 1, 479.90, 479.90);

-- --------------------------------------------------------

--
-- Estrutura da tabela `leituras`
--

DROP TABLE IF EXISTS `leituras`;
CREATE TABLE IF NOT EXISTS `leituras` (
  `cod_leituras` int NOT NULL AUTO_INCREMENT,
  `sensor_cod` int NOT NULL,
  `valor` decimal(10,2) NOT NULL,
  `data_hora` datetime NOT NULL,
  PRIMARY KEY (`cod_leituras`),
  KEY `sensor_cod` (`sensor_cod`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `leituras`
--

INSERT INTO `leituras` (`cod_leituras`, `sensor_cod`, `valor`, `data_hora`) VALUES
(1, 1, 22.50, '2026-01-10 08:30:00'),
(2, 1, 23.10, '2026-01-10 09:15:00'),
(3, 1, 22.80, '2026-01-10 10:00:00'),
(4, 2, 65.00, '2026-01-10 08:30:00'),
(5, 2, 64.50, '2026-01-10 09:15:00'),
(6, 2, 66.20, '2026-01-10 10:00:00'),
(7, 3, 450.00, '2026-01-10 08:30:00'),
(8, 3, 520.00, '2026-01-10 09:15:00'),
(9, 3, 480.00, '2026-01-10 10:00:00'),
(10, 4, 12.00, '2026-01-10 08:30:00'),
(11, 4, 12.50, '2026-01-10 09:15:00'),
(12, 4, 11.80, '2026-01-10 10:00:00');

-- --------------------------------------------------------

--
-- Estrutura da tabela `produto`
--

DROP TABLE IF EXISTS `produto`;
CREATE TABLE IF NOT EXISTS `produto` (
  `codProduto` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `preco` decimal(10,2) NOT NULL,
  `imagem` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`codProduto`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `produto`
--

INSERT INTO `produto` (`codProduto`, `nome`, `preco`, `imagem`) VALUES
(1, 'Processador Intel Core i7-13700K', 439.90, 'cpu_intelcore7_13700K.jpg'),
(2, 'Processador AMD Ryzen 7 7800X3D', 479.90, 'cpu_ryzen7_7800x3d.jpg'),
(3, 'Motherboard MSI MAG Z790 Tomahawk WiFi', 299.90, 'mb_z790_tomahawk.jpg'),
(4, 'Motherboard ASUS ROG Strix B650-A Gaming', 269.90, 'mb_b650_strix.jpg'),
(5, 'Memória RAM Corsair Vengeance DDR5 32GB (2x16GB) 6000MHz', 134.90, 'ram_ddr5_32gb.jpg'),
(6, 'Placa Gráfica NVIDIA GeForce RTX 4070 Ti', 899.90, 'gpu_rtx4070ti.jpg'),
(7, 'Placa Gráfica AMD Radeon RX 7800 XT', 649.90, 'gpu_rx7800xt.jpg'),
(8, 'SSD Samsung 990 Pro 2TB NVMe M.2', 179.90, 'ssd_990pro_2tb.jpg'),
(9, 'SSD Kingston NV2 1TB NVMe M.2', 59.90, 'ssd_nv2_1tb.jpg'),
(10, 'Disco Rígido Seagate Barracuda 4TB 3.5\"', 94.90, 'hdd_4tb.jpg'),
(11, 'Fonte de Alimentação Corsair RM850x 850W 80+ Gold', 149.90, 'psu_rm850x.jpg'),
(12, 'Caixa ATX Fractal Design Meshify 2', 159.90, 'case_meshify2.jpg'),
(13, 'Cooler CPU Noctua NH-D15', 109.90, 'cooler_nhd15.jpg'),
(14, 'Monitor LG UltraGear 27GP850-B 27\" QHD 165Hz', 349.90, 'monitor_27gp850.jpg'),
(15, 'Monitor Dell S2722QC 27\" 4K UHD', 319.90, 'monitor_s2722qc.jpg'),
(16, 'Teclado Mecânico Logitech G915 TKL', 199.90, 'keyboard_g915.jpg'),
(17, 'Rato Gaming Razer DeathAdder V3', 79.90, 'mouse_deathadderv3.jpg'),
(18, 'Headset HyperX Cloud II Wireless', 149.90, 'headset_cloud2.jpg'),
(19, 'Placa de Som Creative Sound BlasterX AE-5 Plus', 129.90, 'soundcard_ae5.jpg'),
(20, 'Pasta Térmica Arctic MX-6 4g', 7.90, 'thermalpaste_mx6.jpg');

-- --------------------------------------------------------

--
-- Estrutura da tabela `sensor`
--

DROP TABLE IF EXISTS `sensor`;
CREATE TABLE IF NOT EXISTS `sensor` (
  `cod_sensor` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(100) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `unidade` int NOT NULL,
  PRIMARY KEY (`cod_sensor`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `sensor`
--

INSERT INTO `sensor` (`cod_sensor`, `nome`, `tipo`, `unidade`) VALUES
(1, 'Sensor de Temperatura', 'Temperatura', 2),
(2, 'Sensor de Luminosdade', 'Luminosdade', 3);

-- --------------------------------------------------------

--
-- Estrutura da tabela `venda`
--

DROP TABLE IF EXISTS `venda`;
CREATE TABLE IF NOT EXISTS `venda` (
  `codVenda` int NOT NULL AUTO_INCREMENT,
  `codCliente` int NOT NULL,
  `dataVenda` datetime NOT NULL,
  `total` decimal(10,2) NOT NULL DEFAULT '0.00',
  PRIMARY KEY (`codVenda`),
  KEY `fk_venda_cliente` (`codCliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Extraindo dados da tabela `venda`
--

INSERT INTO `venda` (`codVenda`, `codCliente`, `dataVenda`, `total`) VALUES
(1, 1, '2026-04-29 15:08:10', 804.60),
(2, 11, '2026-04-29 15:08:31', 879.80),
(3, 1, '2026-04-29 15:21:16', 2109.40),
(4, 1, '2026-04-29 15:29:47', 1219.70),
(5, 17, '2026-04-29 18:22:25', 1359.70);

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `fatura`
--
ALTER TABLE `fatura`
  ADD CONSTRAINT `fk_fatura_venda` FOREIGN KEY (`codVenda`) REFERENCES `venda` (`codVenda`);

--
-- Limitadores para a tabela `item_venda`
--
ALTER TABLE `item_venda`
  ADD CONSTRAINT `fk_item_venda_produto` FOREIGN KEY (`codProduto`) REFERENCES `produto` (`codProduto`),
  ADD CONSTRAINT `fk_item_venda_venda` FOREIGN KEY (`codVenda`) REFERENCES `venda` (`codVenda`);

--
-- Limitadores para a tabela `venda`
--
ALTER TABLE `venda`
  ADD CONSTRAINT `fk_venda_cliente` FOREIGN KEY (`codCliente`) REFERENCES `cliente` (`cod_cliente`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
