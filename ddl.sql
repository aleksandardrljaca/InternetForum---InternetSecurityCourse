-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema forum
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `forum` DEFAULT CHARACTER SET utf8mb3 ;
USE `forum` ;

-- -----------------------------------------------------
-- Table `forum`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `forum`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `first_name` VARCHAR(45) NOT NULL,
  `last_name` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `verification_code` VARCHAR(6) NOT NULL,
  `role` ENUM('ADMIN', 'MODERATOR', 'USER') NOT NULL,
  `restricted` TINYINT NOT NULL,
  `verified` TINYINT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `forum`.`comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(500) NOT NULL,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  `user_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  `approved` TINYINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_user1_idx` (`user_id` ASC) VISIBLE,
  INDEX `fk_comment_category1_idx` (`category_id` ASC) VISIBLE,
  CONSTRAINT `fk_comment_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `forum`.`category` (`id`),
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `forum`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 135
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `forum`.`permissions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`permissions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_permissions_user_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_permissions_user`
    FOREIGN KEY (`user_id`)
    REFERENCES `forum`.`user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 104
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `forum`.`siem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`siem` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `log` VARCHAR(200) NOT NULL,
  `log_time` TIME NOT NULL,
  `log_date` DATE NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `forum`.`token_black_list`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `forum`.`token_black_list` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
