-- javadoc style comments for documentation have been added.
-- a bunch of stuff inserted by mysqldump has been removed

--
-- Dumping routines for database 'library'
--

/**
Returns an int with the ID number of the series that the given book is in
@param INT bookID ID number of the book
@return INT ID number of the book's series
*/
DELIMITER ;;
CREATE DEFINER=`Ross`@`%` FUNCTION `GetSeriesID`(bookID INT) RETURNS int(11)
	BEGIN
	DECLARE sId INT;
	SELECT (SELECT series FROM Fiction WHERE id = bookID) INTO sId;
	RETURN sId;
	END ;;
	DELIMITER ;

/**
Returns 1 if the given Book is in the Fiction table, 0 otherwise
@param Int idNum ID number of the Book
@return int 1 if the book is Fiction, 0 otherwise.
*/
DELIMITER ;;
CREATE DEFINER=`Ross`@`%` FUNCTION `IsFiction`(idNum INT) RETURNS int(11)
	BEGIN
	DECLARE yes INT;
	SELECT EXISTS(SELECT * FROM Fiction WHERE id = idNum) INTO yes;
	RETURN yes;
	END ;;
	DELIMITER ;

/**
Adds an Author with the given name to the Authors table. Automatically inserts an ID number
@param VARCHAR(100) name the author's full name
*/
DELIMITER ;;
CREATE DEFINER=`Ross`@`%` PROCEDURE `addAuthor`(nam VARCHAR(100))
	BEGIN
	INSERT INTO Authors (name) VALUES (nam);
	END ;;
	DELIMITER ;

/**
Returns the books with which the given Author has a role
@param Int author the Author's ID number
@col AllBooks.id as bookID
@col title
@col Authors.id as authorID
@col name
@col role
*/
DELIMITER ;;
CREATE DEFINER=`Ross`@`%` PROCEDURE `searchAuthorID`(author INT)
	BEGIN
	SELECT B.id as bookID, title, A.id as authorID, name, role FROM AllBooks B, Roles R, Authors A, LinkBookAuthor L
	WHERE B.id = bookID AND roleID = R.id AND A.id = author AND authorID = author;
	END ;;
	DELIMITER ;

/**
Returns the books bought after the given date. After such that mnth > m, and yr >= y
@param INT m minimum month
@param INT y minimum yr
@col title
@col author
@col price
@col mnth
@col yr
*/
DELIMITER ;;
CREATE DEFINER=`Ross`@`localhost` PROCEDURE `searchDateGreater`(m INT, y INT)
	BEGIN
	SELECT title, author, price, mnth, yr FROM AllBooks WHERE (mnth > m AND yr >= y) OR yr > y;
	END ;;
	DELIMITER ;