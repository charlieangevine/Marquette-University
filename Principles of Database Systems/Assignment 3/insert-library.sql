
-- PUBLISHER (PName, Address, Phone)

INSERT INTO PUBLISHER VALUES ('Charles Scribners Sons', '597 5th Ave', '8004251543');
INSERT INTO PUBLISHER VALUES ('Richard Bentley', '1819 Dorset Street', '8002546894');
INSERT INTO PUBLISHER VALUES ('Simon and Schuster', '57 Littlefield Street Avon', '8003541579');
INSERT INTO PUBLISHER VALUES ('The Russian Messenger', '1808 Capital Ave', '8002651285');
INSERT INTO PUBLISHER VALUES ('Ballantine Books', '101 Fifth Avenue', '8006571236');
INSERT INTO PUBLISHER VALUES ('Chatto and Windus', '20 Vauxhall Bridge Rd', '8009754523');
INSERT INTO PUBLISHER VALUES ('Thomas Egerton', '272 Blackburn Rd', '8005267441');
INSERT INTO PUBLISHER VALUES ('Little, Brown and Company', '1837 Charles Rd', '8006322586');
INSERT INTO PUBLISHER VALUES ('Harvill Secker', '1910 Young Rd', '8007452545');
INSERT INTO PUBLISHER VALUES ('Smith, Elder and Co.', '65 Cornhill street', '8006549852');


-- BOOK (BookId, Title, PName)

INSERT INTO BOOK VALUES (0, 'The Great Gatsby', 'Charles Scribners Sons');
INSERT INTO BOOK VALUES (1, 'Moby Dick', 'Richard Bentley');
INSERT INTO BOOK VALUES (2, 'Hamlet', 'Simon and Schuster');
INSERT INTO BOOK VALUES (3, 'War and Peace', 'The Russian Messenger');
INSERT INTO BOOK VALUES (4, 'Fahrenheit 451', 'Ballantine Books');
INSERT INTO BOOK VALUES (5, 'The Adventures of Huckleberry Finn', 'Chatto and Windus');
INSERT INTO BOOK VALUES (6, 'Pride and Prejudice', 'Thomas Egerton');
INSERT INTO BOOK VALUES (7, 'The Catcher in the Rye', 'Little, Brown and Company');
INSERT INTO BOOK VALUES (8, 'Nineteen Eighty Four', 'Harvill Secker');
INSERT INTO BOOK VALUES (9, 'Jane Eyre', 'Smith, Elder and Co.');


-- BOOK_AUTHORS (BookId, AName)

INSERT INTO BOOK_AUTHORS VALUES (0, 'F. Scott Fitzgerald');
INSERT INTO BOOK_AUTHORS VALUES (1, 'Herman Melville');
INSERT INTO BOOK_AUTHORS VALUES (2, 'William Shakespeare');
INSERT INTO BOOK_AUTHORS VALUES (3, 'Leo Tolstoy');
INSERT INTO BOOK_AUTHORS VALUES (4, 'Ray Bradbury');
INSERT INTO BOOK_AUTHORS VALUES (5, 'Mark Twain');
INSERT INTO BOOK_AUTHORS VALUES (6, 'Jane Austen');
INSERT INTO BOOK_AUTHORS VALUES (7, 'J. D. Salinger');
INSERT INTO BOOK_AUTHORS VALUES (8, 'George Orwell');
INSERT INTO BOOK_AUTHORS VALUES (9, 'Charlotte Brontë');


-- BORROWER (CardNo, BName, Address, Phone)

INSERT INTO BORROWER VALUES (0, 'Jack', '452 Grand Ave', '4144885214');
INSERT INTO BORROWER VALUES (1, 'Alex', '252 Daisy ln', '4142548547');
INSERT INTO BORROWER VALUES (2, 'Brad', '223 Comet Ct', '4145231623');
INSERT INTO BORROWER VALUES (3, 'Mike', '985 Spring St', '4145124878');
INSERT INTO BORROWER VALUES (4, 'Sam', '156 Park ln', '4145268923');
INSERT INTO BORROWER VALUES (5, 'Lukas', '1564 Harvest Ave', '4146546158');
INSERT INTO BORROWER VALUES (6, 'Nick', '2435 Apple Ct', '4147854523');
INSERT INTO BORROWER VALUES (7, 'Oscar', '1564 Acorn ave', '4145247854');
INSERT INTO BORROWER VALUES (8, 'Eric', '1564 7th st', '4142457812');
INSERT INTO BORROWER VALUES (9, 'Tyler', '2334 23rd st', '4141257496');


-- LIBRARY_BRANCH (BranchId, BranchName, Address)

INSERT INTO LIBRARY_BRANCH VALUES (0, 'Downtown', '100 Main St.');
INSERT INTO LIBRARY_BRANCH VALUES (1, 'Central', '154 N 12th St');
INSERT INTO LIBRARY_BRANCH VALUES (2, 'Parkside', '048 Lincoln way');
INSERT INTO LIBRARY_BRANCH VALUES (3, 'Southedge', '206 S 32nd St');
INSERT INTO LIBRARY_BRANCH VALUES (4, 'Lakefront', '980 Water St');
INSERT INTO LIBRARY_BRANCH VALUES (5, 'Thomas Jefferson', '451 Maple Ave');
INSERT INTO LIBRARY_BRANCH VALUES (6, 'Washington', '001 Washington Blvd');
INSERT INTO LIBRARY_BRANCH VALUES (7, 'University', '434 State St');
INSERT INTO LIBRARY_BRANCH VALUES (8, 'Grand', '325 Grand Station Ave');
INSERT INTO LIBRARY_BRANCH VALUES (9, 'Historical Archives', '324 1st St');