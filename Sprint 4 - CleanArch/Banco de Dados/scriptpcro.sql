-- ===========================================
-- BANCO DE DADOS MimaStore (corrigido para camelCase conforme seu DDL)
-- ===========================================

CREATE DATABASE IF NOT EXISTS `MimaStore`
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;

USE `MimaStore`;

-- ===========================================
-- TABELA: usuario
-- ===========================================
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `telefone` VARCHAR(20) NOT NULL,
  `endereco` VARCHAR(255) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `cargo` VARCHAR(50) NOT NULL,
  `imagem` VARCHAR(500),
  `recoveryToken` VARCHAR(500),
  `recoveryTokenExpiry` DATETIME
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- TABELA: cliente
-- ===========================================
CREATE TABLE IF NOT EXISTS `cliente` (
  `idCliente` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `telefone` VARCHAR(20) NOT NULL,
  `endereco` VARCHAR(255) NOT NULL,
  `cpf` VARCHAR(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- TABELA: fornecedor
-- ===========================================
CREATE TABLE IF NOT EXISTS `fornecedor` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(100) NOT NULL,
  `telefone` VARCHAR(20),
  `email` VARCHAR(100),
  `endereco` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- TABELAS AUXILIARES (tamanho, cor, material, categoria)
-- ===========================================
CREATE TABLE IF NOT EXISTS `tamanho` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `cor` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `material` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `categoria` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `nome` VARCHAR(255)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- TABELA: item
-- ===========================================
CREATE TABLE IF NOT EXISTS `item` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `codigo` VARCHAR(255),
  `qtdEstoque` INT,
  `nome` VARCHAR(255),
  `preco` DOUBLE,
  `fkTamanho` INT NULL,
  `fkCor` INT NULL,
  `fkMaterial` INT NULL,
  `fkCategoria` INT NULL,
  `fkFornecedor` INT NULL,
  FOREIGN KEY (`fkTamanho`) REFERENCES `tamanho`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`fkCor`) REFERENCES `cor`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`fkMaterial`) REFERENCES `material`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`fkCategoria`) REFERENCES `categoria`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`fkFornecedor`) REFERENCES `fornecedor`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- TABELA: venda
-- ===========================================
CREATE TABLE IF NOT EXISTS `venda` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `valorTotal` DOUBLE DEFAULT 0.0,
  `data` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `fkCliente` INT NULL,
  `fkFuncionario` INT NULL,
  CONSTRAINT `fk_venda_cliente` FOREIGN KEY (`fkCliente`) REFERENCES `cliente`(`idCliente`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_venda_funcionario` FOREIGN KEY (`fkFuncionario`) REFERENCES `usuario`(`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- TABELA: itemvenda
-- ===========================================
CREATE TABLE IF NOT EXISTS `itemvenda` (
  `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `fkItem` INT NULL,
  `fkItemFornecedor` INT NULL,
  `fkVenda` INT NULL,
  `qtdParaVender` INT,
  FOREIGN KEY (`fkItem`) REFERENCES `item`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`fkItemFornecedor`) REFERENCES `fornecedor`(`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  FOREIGN KEY (`fkVenda`) REFERENCES `venda`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===========================================
-- INSERÇÕES DE TESTE
-- ===========================================
INSERT INTO `usuario` (`nome`, `email`, `senha`, `telefone`, `endereco`, `cargo`)
VALUES
('John Doe', 'john@doe.com', '$2a$10$0/TKTGxdREbWaWjWYhwf6e9P1fPOAMMNqEnZgOG95jnSkHSfkkIrC', '1111-11111', 'Rua do Bacana', 'Funcionario'),
('Ana Souza', 'ana.gerente@mimastore.com', 'senha123', '11999990000', 'Av. Central', 'Gerente'),
('Carlos Lima', 'carlos.funcionario@mimastore.com', 'senha123', '11988887777', 'Rua das Laranjeiras', 'Funcionario');

INSERT INTO `cliente` (`nome`, `email`, `telefone`, `endereco`, `cpf`) VALUES
('João Pereira', 'joao@gmail.com', '11977776666', 'Rua das Flores', '12345678901'),
('Maria Silva', 'maria@gmail.com', '11966665555', 'Rua dos Sonhos', '98765432100');

INSERT INTO `fornecedor` (`nome`, `telefone`, `email`) VALUES
('Fornecedora A', '1133221100', 'contato@fornecedoraa.com'),
('Fornecedora B', '1144332211', 'suporte@fornecedorab.com');

INSERT INTO `cor` (`nome`) VALUES ('Vermelho'), ('Azul'), ('Preto');
INSERT INTO `categoria` (`nome`) VALUES ('Camiseta'), ('Calça'), ('Acessório');
INSERT INTO `material` (`nome`) VALUES ('Algodão'), ('Jeans'), ('Couro');
INSERT INTO `tamanho` (`nome`) VALUES ('P'), ('M'), ('G'), ('GG');

INSERT INTO `item` (`codigo`, `qtdEstoque`, `nome`, `preco`, `fkFornecedor`, `fkCor`, `fkCategoria`, `fkMaterial`, `fkTamanho`) VALUES
('CAMV021M', 50, 'Camiseta Vermelha M', 39.90, 1, 1, 1, 1, 2),
('CAL567G', 30, 'Calça Jeans G', 79.90, 2, 2, 2, 2, 3),
('ACS678P', 20, 'Acessório Preto P', 119.90, 1, 3, 3, 3, 1);

INSERT INTO `venda` (`valorTotal`, `fkCliente`, `fkFuncionario`) VALUES
(159.80, 1, 1),
(119.90, 2, 2);

INSERT INTO `itemvenda` (`fkItem`, `fkItemFornecedor`, `fkVenda`, `qtdParaVender`) VALUES
(1, 1, 1, 2),
(2, 2, 1, 1),
(3, 1, 2, 1);

