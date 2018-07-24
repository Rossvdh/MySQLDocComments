-- use this file for testing generation of doc comments from SQL code for sprocs
-- a bunch of stuff inserted by mysqldump has been removed

--
-- Dumping routines for database 'library'
--

DELIMITER ;;
CREATE DEFINER=`Ross`@`%` FUNCTION `GetSeriesID`(bookID INT) RETURNS int(11)
	BEGIN
	DECLARE sId INT;
	SELECT (SELECT series FROM Fiction WHERE id = bookID) INTO sId;
	RETURN sId;
	END ;;
	DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`Ross`@`%` FUNCTION `IsFiction`(idNum INT) RETURNS int(11)
	BEGIN
	DECLARE yes INT;
	SELECT EXISTS(SELECT * FROM Fiction WHERE id = idNum) INTO yes;
	RETURN yes;
	END ;;
	DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`Ross`@`%` PROCEDURE `addAuthor`(nam VARCHAR(100))
	BEGIN
	INSERT INTO Authors (name) VALUES (nam);
	END ;;
	DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`Ross`@`%` PROCEDURE `addBook`(tit VARCHAR(100), cost INT, shop INT, month INT, year INT, pub INT)
	BEGIN
	INSERT INTO AllBooks (title, price, placeBought, mnth, yr, firstpub) VALUES (tit, cost, shop, month, year, pub);
	END ;;
	DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`Ross`@`%` PROCEDURE `searchAuthorID`(author INT)
	BEGIN
	SELECT B.id as bookID, title, A.id as authorID, name, role FROM AllBooks B, Roles R, Authors A, LinkBookAuthor L
	WHERE B.id = bookID AND roleID = R.id AND A.id = author AND authorID = author;
	END ;;
	DELIMITER ;

DELIMITER ;;
CREATE DEFINER=`Ross`@`localhost` PROCEDURE `searchDateGreater`(m INT, y INT)
	BEGIN
	SELECT title, author, price, mnth, yr FROM AllBooks WHERE (mnth > m AND yr >= y) OR yr > y;
	END ;;
	DELIMITER ;