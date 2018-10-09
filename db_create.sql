-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema totalizator
-- -----------------------------------------------------
-- Система Тотализатор. Клиент делает Ставки разных видов (победа, ничья, поражения, точный результат и пр.) на Соревнования. Букмекер устанавливает уровень выигрыша. Администратор управляет Пользователями, создает (изменяет) Соревнования, а также фиксирует их результаты.
DROP SCHEMA IF EXISTS `totalizator` ;

-- -----------------------------------------------------
-- Schema totalizator
--
-- Система Тотализатор. Клиент делает Ставки разных видов (победа, ничья, поражения, точный результат и пр.) на Соревнования. Букмекер устанавливает уровень выигрыша. Администратор управляет Пользователями, создает (изменяет) Соревнования, а также фиксирует их результаты.
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `totalizator` DEFAULT CHARACTER SET utf8 ;
USE `totalizator` ;

-- -----------------------------------------------------
-- Table `totalizator`.`sport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`sport` (
  `id` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `totalizator`.`sport_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`sport_team` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'ID команды',
  `wins` TINYINT(5) NULL DEFAULT 0 COMMENT 'Победы',
  `loses` TINYINT(5) NULL DEFAULT 0 COMMENT 'Проигрыши',
  `sport_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_sport_team_sport1_idx` (`sport_id` ASC),
  CONSTRAINT `fk_sport_team_sport1`
    FOREIGN KEY (`sport_id`)
    REFERENCES `totalizator`.`sport` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Описание спортивных команд.';


-- -----------------------------------------------------
-- Table `totalizator`.`competition`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`competition` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Competition ID',
  `team1_id` INT NOT NULL COMMENT 'ID первой команды',
  `team2_id` INT NOT NULL COMMENT 'ID второй команды',
  `start` DATETIME NOT NULL COMMENT 'When competition will start',
  `finish` DATETIME NOT NULL COMMENT 'When competition will finish',
  `state` ENUM('Acceptence of bets', 'Completion of bets', 'Completed') NULL DEFAULT 'Acceptence of bets' COMMENT 'Состояние соревнования\nЕсть 4 следующих состояния:\n1. Прием ставок (прием ставок завершается за 2 часа до начала соревнования\n3. Ставки не принимаются\n4. Завершено',
  `result` VARCHAR(5) NULL DEFAULT 'null',
  `sport_id` INT NOT NULL,
  `is_active` BINARY(1) NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  INDEX `fk_competition_sport_team1_idx` (`team1_id` ASC),
  INDEX `fk_competition_sport_team2_idx` (`team2_id` ASC),
  INDEX `fk_competition_sport1_idx` (`sport_id` ASC),
  CONSTRAINT `fk_competition_sport_team1`
    FOREIGN KEY (`team1_id`)
    REFERENCES `totalizator`.`sport_team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_competition_sport_team2`
    FOREIGN KEY (`team2_id`)
    REFERENCES `totalizator`.`sport_team` (`id`)
    ON DELETE RESTRICT
    ON UPDATE CASCADE,
  CONSTRAINT `fk_competition_sport1`
    FOREIGN KEY (`sport_id`)
    REFERENCES `totalizator`.`sport` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Competition';


-- -----------------------------------------------------
-- Table `totalizator`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`user` (
  `login` VARCHAR(20) NOT NULL COMMENT 'Логин пользователя.\n\nСделан первичным ключом т.к.:\nа) уникален для каждого пользователя (по нему можно определить)\nб) именно логин используется для поиска записи о пользователе в таблице',
  `password` VARCHAR(40) NOT NULL COMMENT 'Пароль',
  `e-mail` VARCHAR(45) NOT NULL COMMENT 'E-mail',
  `role` ENUM('Administrator', 'User', 'Bookmaker') NOT NULL DEFAULT 'User' COMMENT 'Роль описываемого пользователя в системе:\n1. Администратор (только один)\n2. Пользователь (может делать ставки)\n3. Букмекер (устанавливает уровень выйгрыша)',
  PRIMARY KEY (`login`))
ENGINE = InnoDB
COMMENT = 'User\'s info';


-- -----------------------------------------------------
-- Table `totalizator`.`personal_result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`personal_result` (
  `user_login` VARCHAR(20) NOT NULL,
  `last_bet` DECIMAL(5,2) NULL DEFAULT NULL COMMENT 'Размер последней ставки',
  `last_gain` DECIMAL(10,2) NULL DEFAULT NULL COMMENT 'Сумма денег, полученная на последней ставке.',
  `all_bet` DECIMAL(10,2) NULL DEFAULT 0.0 COMMENT 'Сколько всего поставил пользователь денег за все время',
  `all_gain` DECIMAL(10,2) NULL DEFAULT 0.0 COMMENT 'Сколько всего получил пользователь за все время',
  PRIMARY KEY (`user_login`),
  CONSTRAINT `fk_table1_user1`
    FOREIGN KEY (`user_login`)
    REFERENCES `totalizator`.`user` (`login`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Результаты пользователей.\n\nВсе данные (кроме размера последней сделанной ставки) будут заполнятся триггеррами).';


-- -----------------------------------------------------
-- Table `totalizator`.`competition_m2m_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`competition_m2m_user` (
  `user_login` VARCHAR(20) NOT NULL,
  `competition_id` INT UNSIGNED NOT NULL COMMENT 'Competition ID',
  `result` CHAR(1) NOT NULL DEFAULT 'x' COMMENT 'Результат матча:\n1. 1 - выйграла первая команда\n2. x - ничья\n3. 2 - выйграла вторая команда',
  PRIMARY KEY (`user_login`, `competition_id`),
  INDEX `fk_competition_has_user_competition_idx` (`competition_id` ASC),
  INDEX `fk_competition_m2m_user_personal_result1_idx` (`user_login` ASC),
  CONSTRAINT `fk_competition_has_user_competition`
    FOREIGN KEY (`competition_id`)
    REFERENCES `totalizator`.`competition` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_competition_m2m_user_personal_result1`
    FOREIGN KEY (`user_login`)
    REFERENCES `totalizator`.`personal_result` (`user_login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
COMMENT = 'Show what campetition have user';


-- -----------------------------------------------------
-- Table `totalizator`.`team_vs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`team_vs` (
  `team1_id` INT NOT NULL COMMENT 'ID первой команды',
  `team2_id` INT NOT NULL COMMENT 'ID второй команды',
  `team1_wins` TINYINT(5) NULL DEFAULT 0 COMMENT 'Количество попед первой команды',
  `team2_wins` TINYINT(5) NULL DEFAULT 0 COMMENT 'Количество попед первой команды',
  `quantity` MEDIUMINT(8) NULL DEFAULT 0 COMMENT 'Общее количество матчей',
  INDEX `fk_team_vs_sport_team1_idx` (`team1_id` ASC),
  INDEX `fk_team_vs_sport_team2_idx` (`team2_id` ASC),
  PRIMARY KEY (`team1_id`, `team2_id`),
  CONSTRAINT `fk_team_vs_sport_team1`
    FOREIGN KEY (`team1_id`)
    REFERENCES `totalizator`.`sport_team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_team_vs_sport_team2`
    FOREIGN KEY (`team2_id`)
    REFERENCES `totalizator`.`sport_team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB
COMMENT = 'Статистика противостояния команд\n\nИспользуя данные этой таблицы пользователи будут принимать решения о результатах матчей и размере своих ставок.';


-- -----------------------------------------------------
-- Table `totalizator`.`result`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`result` (
  `correct` TINYINT(2) NOT NULL DEFAULT 8 COMMENT 'Количество верных прогнозов',
  `betters` INT NOT NULL DEFAULT 0 COMMENT 'Количество пользователей, которые верно предсказали correct матчей',
  `bets` DECIMAL(10,2) NOT NULL DEFAULT 0 COMMENT 'Общая сумма ставок всех пользователей, которые верно предсказали correct матчей',
  `pool_part` TINYINT(2) NULL DEFAULT 10 COMMENT 'Размер фонда (в процентах), который разделят участники, предсказавшие correct матчей.',
  `pool` DECIMAL(10,2) NULL DEFAULT 0.0 COMMENT 'Размер пула (в денежных единицах).\n',
  `coefficient` DECIMAL(10,2) NULL DEFAULT 0.0 COMMENT 'Размер коэффицента, на который домножается ставка пользователя.',
  PRIMARY KEY (`correct`))
ENGINE = InnoDB
COMMENT = 'Таблица с результатами тотализатора.\n\nДанные betters, bets и coefficient будут заполнятся приложением после того, как закончатся соревнования.\nЗаполнятся в ручную будет только поле pool_part.';


-- -----------------------------------------------------
-- Table `totalizator`.`language`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`language` (
  `id` CHAR(3) NOT NULL,
  `name` VARCHAR(40) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
COMMENT = 'Список доступных языков';


-- -----------------------------------------------------
-- Table `totalizator`.`language_has_sport_team`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`language_has_sport_team` (
  `language_id` CHAR(3) NOT NULL,
  `sport_team_id` INT NOT NULL,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`language_id`, `sport_team_id`),
  INDEX `fk_language_has_sport_team_sport_team1_idx` (`sport_team_id` ASC),
  INDEX `fk_language_has_sport_team_language1_idx` (`language_id` ASC),
  CONSTRAINT `fk_language_has_sport_team_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `totalizator`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_has_sport_team_sport_team1`
    FOREIGN KEY (`sport_team_id`)
    REFERENCES `totalizator`.`sport_team` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `totalizator`.`language_m2m_sport`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`language_m2m_sport` (
  `language_id` CHAR(3) NOT NULL,
  `sport_id` INT NOT NULL,
  `sport` VARCHAR(45) NULL,
  PRIMARY KEY (`language_id`, `sport_id`),
  INDEX `fk_language_has_sport_sport1_idx` (`sport_id` ASC),
  INDEX `fk_language_has_sport_language1_idx` (`language_id` ASC),
  CONSTRAINT `fk_language_has_sport_language1`
    FOREIGN KEY (`language_id`)
    REFERENCES `totalizator`.`language` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_language_has_sport_sport1`
    FOREIGN KEY (`sport_id`)
    REFERENCES `totalizator`.`sport` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `totalizator`.`card_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `totalizator`.`card_info` (
  `user_login` VARCHAR(20) NOT NULL,
  `card_number` VARCHAR(19) NOT NULL,
  `is_used` BINARY(1) NULL DEFAULT 1,
  PRIMARY KEY (`user_login`, `card_number`),
  CONSTRAINT `fk_card_info_personal_result1`
    FOREIGN KEY (`user_login`)
    REFERENCES `totalizator`.`personal_result` (`user_login`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

USE `totalizator`;

DELIMITER $$
USE `totalizator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `totalizator`.`competition_AFTER_UPDATE` AFTER UPDATE ON `competition` FOR EACH ROW
BEGIN
	if new.state ='Completed' then
		set @team1 = conv(substring_index(new.result, ':', 1), 10, 10);
        set @team2 = conv(substring_index(new.result, ':', -1), 10, 10);
        if @team1 > @team2 then
			update sport_team
            set wins = wins + 1
            where id = new.team1_id;
            -- ---------------------
            update sport_team
            set loses = loses + 1
            where id = new.team2_id;
            -- ---------------------
            update team_vs
            set team1_wins = team1_wins + 1,
                quantity = quantity + 1
            where team1_id = new.team1_id and team2_id = new.team2_id;
            -- ---------------------
            update team_vs
            set team2_wins = team2_wins + 1,
                quantity = quantity + 1
            where team2_id = new.team1_id and team1_id = new.team2_id;
		elseif @team2 > @team1 then
			update sport_team
            set wins = wins + 1
            where id = new.team2_id;
            -- ---------------------
            update sport_team
            set loses = loses + 1
            where id = new.team1_id;
            -- ---------------------
            update team_vs
            set team1_wins = team1_wins + 1, 
                quantity = quantity + 1
            where team1_id = new.team2_id and team2_id = new.team1_id;
            -- ---------------------
            update team_vs
            set team2_wins = team2_wins + 1,
                quantity = quantity + 1
            where team2_id = new.team2_id and team1_id = new.team1_id;
		else
			update team_vs
            set quantity = quantity + 1
            where (team2_id = new.team2_id and team1_id = new.team1_id) or
					(team2_id = new.team1_id and team1_id = new.team2_id);
		end if;
	end if;
END$$

USE `totalizator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `totalizator`.`user_AFTER_INSERT` AFTER INSERT ON `user` FOR EACH ROW
BEGIN
	if new.role = 'User' then
			insert into totalizator.personal_result (user_login, all_bet, all_gain) 
			value (new.login, 0.0, 0.0);
	end if;
END$$

USE `totalizator`$$
CREATE DEFINER = CURRENT_USER TRIGGER `totalizator`.`personal_result_BEFORE_UPDATE` BEFORE UPDATE ON `personal_result` FOR EACH ROW
BEGIN
	if new.last_bet is not null and (new.last_bet <> old.last_bet or old.last_bet is null) then
        set new.all_bet = old.all_bet + new.last_bet;
        update result
        set pool = pool + (pool_part * new.last_bet / 100);
        -- 10% пула уходит букмекеру
	end if;
    if new.last_gain is not null and (new.last_gain <> old.last_gain or old.last_gain is null) then
		set new.all_gain = old.all_gain + new.last_gain;
	end if;
END$$


DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
