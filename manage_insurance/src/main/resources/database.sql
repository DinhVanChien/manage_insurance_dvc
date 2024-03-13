-- CREATE Database
CREATE DATABASE INSURANCE CHARACTER SET utf8;

-- TABLE insurance
CREATE TABLE `insurance`.`insurance` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `insurance_number` VARCHAR(15) NOT NULL,
  `start_date` DATE NULL,
  `end_date` DATE NULL,
  `place_of_register` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));
  
  -- TABLE company
  CREATE TABLE `insurance`.`company` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `company_name` VARCHAR(255) NOT NULL,
  `address` VARCHAR(500) NULL,
  `email` VARCHAR(125) NULL,
  `telephone` VARCHAR(13) NULL,
  PRIMARY KEY (`ID`));

  -- TABLE Role
  CREATE TABLE `insurance`.`role` (
  `role_id` INT NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`role_id`));

-- TABLE USERS
CREATE TABLE `insurance`.`users` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `insurance_id` INT NULL,
  `company_id` INT NULL,
  `username` VARCHAR(20) NULL,
  `password` VARCHAR(20) NULL,
  `full_name` VARCHAR(100) NULL,
  `user_sex_division` CHAR(1) NULL,
  `birth_date` DATE NULL,
  PRIMARY KEY (`ID`),
  INDEX `insurance_id_idx` (`insurance_id` ASC) VISIBLE,
  INDEX `company_id_fk_idx` (`company_id` ASC) VISIBLE,
  CONSTRAINT `insurance_id_fk`
    FOREIGN KEY (`insurance_id`)
    REFERENCES `insurance`.`insurance` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `company_id_fk`
    FOREIGN KEY (`company_id`)
    REFERENCES `insurance`.`company` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- TABLE user_role
CREATE TABLE `user_role` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `user_id_fk_idx` (`user_id`),
  KEY `role_id_fk_idx` (`role_id`),
  CONSTRAINT `role_id_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3


