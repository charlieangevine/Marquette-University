set echo on;

--1. List the names of authors who have authored more than 3 books
SELECT AUTHORNAME FROM BOOK_AUTHORS GROUP BY AUTHORNAME HAVING COUNT(*) >= 3;

--2. Print the names of borrowers whose phone number starts with area code â€œ414â€?.
SELECT NAME FROM BORROWER WHERE PHONE LIKE '414%';

--3. Retrieve the names of borrowers who have never checked out any books.
SELECT NAME FROM BORROWER WHERE CARDNO NOT IN (SELECT CARDNO FROM BOOK_LOANS);

--4. List the titles of books written by â€œRingerâ€? author?
SELECT TITLE FROM BOOK WHERE BOOKID IN (SELECT BOOKID FROM BOOK_AUTHORS WHERE AUTHORNAME = 'Ringer');

--5.  List the name(s) of borrowers, who have loaned books ONLY published 
--    by â€œNew Moon Booksâ€? publisher?
SELECT NAME FROM BORROWER WHERE CARDNO IN (SELECT CARDNO FROM BOOK_LOANS WHERE NOT BOOKID IN (SELECT BOOKID FROM BOOK WHERE NOT PUBLISHER = 'New Moon Books'));


--6. How many copies of the book titled â€œBut Is It User Friendly?â€? 
--   are owned by each library branch?
SELECT * FROM BOOK_COPIES WHERE BOOKID = (SELECT BOOKID FROM BOOK WHERE TITLE = 'But Is It User Friendly?');
      
--7. List the book titles co-authored by more than 2 people.
SELECT TITLE FROM BOOK WHERE BOOKID IN (SELECT BOOKID FROM BOOK_AUTHORS GROUP BY BOOKID HAVING COUNT(*) >= 2);

--8. Print the names of borrowers who have borrowed the highest number of books.
SELECT CARDNO, COUNT(CARDNO), (SELECT NAME FROM BORROWER WHERE CARDNO = BOOK_LOANS.CARDNO) FROM BOOK_LOANS GROUP BY CARDNO ORDER BY COUNT(CARDNO) DESC;

--9. Print the names of borrowers who have not yet returned the books.
SELECT NAME FROM BORROWER WHERE CARDNO IN (SELECT CARDNO FROM BOOK_LOANS WHERE DATEIN IS NULL);
                  
--10. Print the BookId, book title and average rating received for each book. 
--    Shows the results sorted in decreasing order of average rating received. 
--    Do not show books below an average rating of 3.0
SELECT * FROM 
    (SELECT BOOKID, TITLE FROM BOOK) 
    b LEFT JOIN (SELECT BOOKID, AVG(RATING) AS RATING FROM BOOK_LOANS GROUP BY BOOKID) bl ON b.BOOKID = bl.BOOKID
    WHERE RATING IS NOT NULL AND RATING >= 3 
    ORDER BY RATING DESC;

--11. For each book that is loaned out from the "Sharpstown" branch 
--    and which are not yet returned to the library, retrieve the book title,
--    the borrower's name, and the borrower's address.
SELECT TITLE, NAME, ADDRESS FROM 
    BOOK_LOANS bl LEFT JOIN BOOK b ON b.BOOKID = bl.BOOKID LEFT JOIN BORROWER bo ON bl.CARDNO = bo.CARDNO 
    WHERE BRANCHID IN (SELECT BRANCHID FROM LIBRARY_BRANCH WHERE BRANCHNAME = 'Sharpstown') AND DATEIN IS NULL;

--12. Print the total number of borrowers in the database.
SELECT COUNT(*) FROM BORROWER;

-- 13.	Print the names of tough reviewers. Tough reviewers are the borrowers who have given the lowest overall 
-- rating value that a book has for each of the books they have rated.

SELECT bl.BOOKID, bl.CARDNO, (SELECT NAME FROM BORROWER WHERE CARDNO = bl.CARDNO) as BORROWER, RATING FROM BOOK_LOANS bl 
    WHERE RATING = (SELECT MIN(RATING) FROM BOOK_LOANS WHERE BOOKID =  bl.BOOKID);

--14.  Print the names of borrowers and the count of number of books that they have reviewed.  
--Show the results sorted in decreasing order of number of books reviewed. 
--Display the count as zero for the borrowers who have not reviewed any book.

SELECT NAME, COALESCE(REVIEWS, 0) AS NUM_REVIEWS FROM 
    BORROWER bo LEFT JOIN (SELECT CARDNO, COUNT(CARDNO) AS REVIEWS FROM BOOK_LOANS GROUP BY CARDNO) bl ON bo.CARDNO = bl.CARDNO;


--15.  Print the names and addresses of all publishers in the database.
SELECT NAME, ADDRESS FROM PUBLISHER;

set echo off;