# COSC 3410 - Prolog
#### Assignment #6
#### Solving Puzzles

**GOAL:** Write a Prolog program to solve a logic puzzle. I didn't intend for the assignment itself to sound like a logic puzzle, but things sort of got carried away. As I explained in class, there are several options in this assignment.

**OPTION I** (this would earn a B) is very much like the Homes puzzle:
Four drivers: Norma, Andy, Edward, and Olivia have been charged with four traffic violations: running a stop sign, speeding, making an illegal left turn, and running a red light. Use the clues to find out which law each suspect violated.

#### Clues
```
1. Norma did not run either a red light or a stop sign.
2. Andy was never stopped for speeding.
3. Edward made an illegal left turn.
4. Olivia was always careful to stop at a stop sign.
```
This puzzle is adapted (without permission) from Dandy Lion - Great Chocolate Caper. I assume that's a book, but I don't really know - my son did this problem for school homework a few years ago.

**METHOD:** I'd suggest recording each charge with a structure like charge(X,Y) to mean that driver X was charged with crime Y. Then keep a list of charge structures and list membership requirements on that list (like in the homes example). So, instead of
```
homes([home(norm,_), home(alex,_), ...]).
```
to define the initial information, you might do something like
```
crimes([charge(norma,_), charge(andy,_), ...]).
```
Then use the clues to create membership subgoals (like in the homes example). The easiest way to deal with the clues which imply that someone didn't commit certain crimes is to list the remaining crimes as options (using ; for OR).

**OPTION II** (this would earn an A-) is very much like the Zebra puzzle:
Four courses are taught, one per hour, starting from 9AM to Noon. Each course meets in a specified classroom, has a subject and teacher and each teacher uses a distinctive method of presenting their lecture. Based on the clues below, figure out who teaches which classes, where and when.

#### Clues:
```
1. English is taught in Room 120.
2. The History teacher uses an overhead projector.
3. Ms. Smith teaches in Room 200.
4. The Math class meets in the hour before the class in Room 233.
5. The course in Room 200 uses models.
6. Mr. Whitherspoon uses the blackboard.
7. Ms. Johnson teaches the hour before Science is taught.
8. Room 105 holds a class at 11:00AM.
9. Mr. Adams teaches in Room 233.
10. A lecturn is used in the class at 9:00AM.
```

**METHOD:** I'd suggest recording each course with a structure like course(S,R,T,P) to mean that subject S is taught in room R by teacher T using presentation technique P. Then keep a list of course structures in order by class time and state membership requirements on that list (like in the zebra example). So, instead of
```
houses([house(_,_,_,_,_), house(_,_,_,_,_), ...]).
```
to define the initial information, you might do something like
```
courses([course(_,_,_,_), course(_,_,_,_), ...]).
```
Then use the clues to create membership subgoals (like in the zebra example). You may need to define a predicate to deal with the clues indicating that certain courses come just before or after another course.

**IN GENERAL:** Remember, the goal here is to write a Prolog program which consists of versions of the above clues and let the computer solve the problem. Don't just solve the problem yourself and then have the computer print that.

**EXTRA:** To earn an A you could do BOTH Option I and Option II. Or you could do some other logic puzzle (along with one of I or II). Here's another puzzle (also from Dandy Lion):

Six suspects (Alice, Joan, Sam, Nancy, Edward, Oscar) are questioned by the police. Use the following clues to determine in which order the suspects were questioned.

#### Clues
```
1. Alice was either the first or the last suspect questioned.
2. Joan was the third suspect questioned.
3. Sam was neither the first nor the second suspect questioned.
4. Nancy was questioned before Sam and after Oscar.
5. Edward was questioned before Joan and after another woman
(the women are Alice, Joan, and Nancy).
```

You'll probably have to think (and maybe experiment) a bit to figure out how to describe this in Prolog.

**HAND-IN:** Your Prolog source code (the .pl file) and a sample run showing your solution for each problem which you worked on.