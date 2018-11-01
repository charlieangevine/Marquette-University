DROP TABLE book CASCADE CONSTRAINTS;
CREATE TABLE book (
  bookid    number primary key,
  title     varchar(40) not null,
  pname     varchar(40),
  foreign key (pname) references publisher(pname)
);

DROP TABLE publisher CASCADE CONSTRAINTS;
CREATE TABLE publisher (
  pname     varchar(40) primary key,
  address   varchar(40),
  phone     varchar(15)
);

DROP TABLE book_authors CASCADE CONSTRAINTS;
CREATE TABLE book_authors (
  bookid    number(25) not null,
  aname     varchar(25),
  primary key (bookid, aname),
  foreign key (bookid) references book(bookid)
);

DROP TABLE book_copies CASCADE CONSTRAINTS;
CREATE TABLE book_copies (
  bookid        number(5) not null,
  branchid      number(5),
  no_of_copies  number(5) check(no_of_copies > 0),
  primary key (bookid, branchid),
  foreign key (bookid) references book(bookid),
  foreign key (branchid) references library_branch(branchid)
);

DROP TABLE book_loans CASCADE CONSTRAINTS;
CREATE TABLE book_loans (
  bookid        number(5) not null,
  branchid      number(5) not null,
  cardno        number(5) not null,
  dateout       date,
  duedate       date,
  foreign key (bookid) references book(bookid),
  foreign key (branchid) references library_branch(branchid),
  foreign key (cardno) references borrower(cardno),
  constraint duedate check (months_between(duedate, dateout) = 1)
);

DROP TABLE library_branch CASCADE CONSTRAINTS;
CREATE TABLE library_branch (
  branchid      number primary key,
  branchname    varchar(25) not null,
  address       varchar(25)
);

DROP TABLE borrower CASCADE CONSTRAINTS;
CREATE TABLE borrower (
  cardno        number primary key,
  bname         varchar(25) not null,
  address       varchar(25),
  phone         varchar(25)
);
  