#### Assignment #3
GOAL: Extend the WAE language to include multiply and divide operators (*, /) and a conditional expression:
```
 (if< expr1 expr2 val1 val2)
 ```
which has the value val1 if ```expr1``` is less than ```expr2``` and ```val2``` otherwise. So,
```
    {with {a 5} {if< a 10 77 99}}
```
would have the value ```77```.
Also add a unary minus [ new tree (minus (operand CWAE?)) ]. For instance,
```
  {with {x -12} {- x}}
  ```
has value ```12```.
In other words, you should write a parser and interpreter for the grammar:
```
<CWAE> ::= <num>
         | <id>
         | {+ <CWAE> <CWAE>}
         | {- <CWAE> <CWAE>}
         | {* <CWAE> <CWAE>}
         | {/ <CWAE> <CWAE>}
         | {- <CWAE>}
         | {with {<id> <CWAE>} <CWAE>}
         | {if< <CWAE> <CWAE> <CWAE> <CWAE>}
```
Your parser should include error checking for illegal input.

**METHOD**: Work a little at a time. Don't try to do everything at once and then see if it runs (it almost surely won't). Also remember you need to be using #lang plai in order for define-type to work.

I would start by adding in multiplication and division. This is just like we did in class. Notice in this case that you're starting with the WAE language (in class we added the operations to the AE language). You'll need to modify the define-type to include the new tree variants, then change the parser to handle the new variants, and then update calc to compute with the new trees.

For the other additions (if< and unary -) you'll need to go through these same steps (adjust data type, parser, calc).

You can start including error checking at any point. For example, you could begin by just adding error checking to the WAE grammar. That means the parser should only accept input that matches the grammar. So, every input should be a number, a symbol, or a list starting with + or - or with. The lists starting with + and - should be length 3. Lists starting with with should have length 3 and the second entry should be a list of length 2 (where the first entry is just a symbol).

Notice once you add unary minus, then there are two legal possibilities for lists starting with -. They can be length 3 (and get parsed into a sub tree) or length 2 (and get parsed into a minus tree).

A sample program:
```
{with {x 9}    ; you can change these vals, but the larger one should
  {with {y 3}  ; always wind up at the left of the final number
               ; and the smaller one at the right
     {with  {min  {if< x y
                     x
                     y}}
        {with  {max  {if< x y
                        y
                        x}}
           {+ {* max 1000} min} ; output will be best if smaller is
 }}}}                           ; no more than two digits
 ```
Some examples of illegal input (the parser should report syntax errors for these):
````
{+ 4 5 6}
{+ 2}
{with 6}
{with 'x 6}
{3 4 5}
{- 6 5 2}
{+}
{with {x 32} {+ x 4} {+ x 10}}
````
