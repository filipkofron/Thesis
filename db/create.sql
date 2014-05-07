SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`User` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NULL,
  `salt` VARCHAR(32) NULL,
  PRIMARY KEY (`id`),
  INDEX `USERNAME` (`username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Vendor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Vendor` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Food`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Food` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `gtin` VARCHAR(16) NULL,
  `name` VARCHAR(64) NULL,
  `description` TEXT NULL,
  `category_id` INT NOT NULL,
  `default_use_by` MEDIUMTEXT NULL,
  `amount_measure` INT NOT NULL,
  `amount` FLOAT NULL,
  `user_id` INT NOT NULL,
  `price` FLOAT NULL,
  `vendor_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Food_Category_idx` (`category_id` ASC),
  INDEX `NAME` (`name` ASC),
  INDEX `fk_Food_User1_idx` (`user_id` ASC),
  INDEX `fk_Food_Vendor1_idx` (`vendor_id` ASC),
  INDEX `gtin_idx` (`gtin` ASC),
  CONSTRAINT `fk_Food_Category`
    FOREIGN KEY (`category_id`)
    REFERENCES `mydb`.`Category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Food_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Food_Vendor1`
    FOREIGN KEY (`vendor_id`)
    REFERENCES `mydb`.`Vendor` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Inventory` (
  `id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `food_id` INT NOT NULL,
  `use_by` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Inventory_User1_idx` (`user_id` ASC),
  INDEX `fk_Inventory_Food1_idx` (`food_id` ASC),
  CONSTRAINT `fk_Inventory_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Inventory_Food1`
    FOREIGN KEY (`food_id`)
    REFERENCES `mydb`.`Food` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `added_on` DATETIME NOT NULL,
  `review_text` TEXT NULL,
  `food_id` INT NOT NULL,
  `rating` FLOAT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Review_User1_idx` (`user_id` ASC),
  INDEX `fk_Review_Food1_idx` (`food_id` ASC),
  CONSTRAINT `fk_Review_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Review_Food1`
    FOREIGN KEY (`food_id`)
    REFERENCES `mydb`.`Food` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Edit`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Edit` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `food_id` INT NOT NULL,
  `edit_on` DATETIME NOT NULL,
  `message` TEXT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Edit_User1_idx` (`user_id` ASC),
  INDEX `fk_Edit_Food1_idx` (`food_id` ASC),
  CONSTRAINT `fk_Edit_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Edit_Food1`
    FOREIGN KEY (`food_id`)
    REFERENCES `mydb`.`Food` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`Image`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Image` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NOT NULL,
  `food_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Image_Food1_idx` (`food_id` ASC),
  INDEX `fk_Image_User1_idx` (`user_id` ASC),
  CONSTRAINT `fk_Image_Food1`
    FOREIGN KEY (`food_id`)
    REFERENCES `mydb`.`Food` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Image_User1`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`User` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
