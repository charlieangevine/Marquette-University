# Database Course - Assignment 3

Consider the LIBRARY relational schema shown below, which is used to keep track of books, borrowers, and book loans. Referential integrity constraints are shown as directed arcs.

![image002.jpg](http://www.mscs.mu.edu/~praveen/Teaching/Fa18/Db/Assignments/Assign3-Db-Fa18_files/image002.jpg)


 

### Part I : Tables + Constraints

Write ```create-library.sql``` script which has SQL statements for creating the tables and constraints for the library database system.

**Constraints**: Be sure to include primary keys, and foreign keys. The data types of tables for the most part are self explanatory. I will explain the ones which are little ambiguous. All the IDs such as BookId, BranchId, and CardNo are integers. DateOut and DueDate  columns are of type DATE which indicate date a book is loaned out and date a book is due respectively. In Oracle, columns of type DATE stores date and time. Include a constraint such that DueDate is one month from the date a book is loaned out. Enforce users to require a value for book title, branch name, and borrower name. The number of copies of a book should be a positive value.

 

### Part II : Data Manipulation

Load each of your tables with at least 10-15 rows (more is better), and include your insert statements in a separate file titled ```insert-library.sql```.