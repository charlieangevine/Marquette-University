# COSC 3410 - Programming Languages
## Assignment #2

For each of the problems below, submit a file containing the define for the function and also a sample run showing the test cases listed with the problem and at least two other test cases which you make up. Each function should contain a comment giving a brief description of the function and saying who wrote it.
Problems should be submitted using the D2L dropbox by 11:59PM on Friday.

NOTE: for this assignment I've added a few other conditions:

The main change is that I would like each function to be accompanied by a list of the cases that you have divided the input into and a description of the output in each case. Try to use normal English to write these (rather than just sort of repeating the Scheme code). This could be in comments in the Scheme code file or in a separate document if you prefer.

I would also like the cond pairs to be enclosed in square brackets rather than parentheses (like in Krishnamurthy's examples and my class examples). This should help you keep track of whether you have a test and a value in each pair.

Finally, each function should have comments saying who wrote it (once per file is OK) and giving the "signature" of the function. That is, the types of the parameters and result. Again Krishnamurthy gives these as one-line comments when introducing new functions. For instance, beginning? (from HW #1) would be
```
  ; beginning?: list list --> boolean
````
Do all four problems. Be sure to use good functional style.

1. One way to study a sequence of numbers is to compute the differences between successive terms. If you reach all zeros after k steps, it means the original sequence can be generated by a degree k polynomial. Write a function differences which takes a list of numbers as a parameter and returns the list of differences. That is, a list containing the second minus the first, third minus the second, etc.

#### Examples:
```
  (differences '(2 7 10 3 6))
 '(5 3 -7 3)
```
```
  (differences '(1 3))
 '(2)
```
```
  (differences '())
 '()
```
```
  (differences '(1 2 -3 -4 7 12))
 '(1 -5 -1 11 5)
```
2. Write a function zip which takes two lists as parameters and returns a list of pairs where the first pair consists of the first elements of the two parameters, the second pair has the second elements of the parameters, etc. If the lists are not the same length, you should use the builtin function error to report an error. The function error takes two parameters, an atom for the name of the function, and a string for error message. Thus, you might use something like
```
 (error 'zip "first list too short")
 ```
Examples:
```
  (zip '(a b c d e) '(32 7 10 3 1) )
 '((a 32) (b 7) (c 10) (d 3) (e 1))
```
```
  (zip '() '() )
 '()
```
```
  (zip '((a b) c (d e f)) '(c (a) (b c)) )
 '(((a b) c) (c (a)) ((d e f) (b c)))
```
```
  (zip '(1 2 -3) '(50 40 30 20))
 x x zip: first list too short
 ```
3. Write a function deep-add which takes a list as a parameter and computes the sum of all numbers which appear anywhere in the list or in sublists. You may find it useful to know that Scheme has a function number? which returns #t if its argument is numeric atom and #f otherwise.

Examples:
```
  (deep-add '(5 a b 8 2))
 15
```
```
  (deep-add '((4 (6 1)) 2 3 (4)))
 20
```
```
  (deep-add '(these (aren't 77) (all 32 (numbers 93 here))))
 202
```
```
  (deep-add '())
 0
```
```
  (deep-add '(no numbers here))
 0
 ```
4. Write a function shape which takes a list as a parameter and returns a list in which all atoms have been removed. That is, the parentheses should still all be there, but nothing else. One could imagine doing this by turning the list into a string and deleting everything except the parentheses. You shouldn't do that, but rather use standard list manipulation functions (like we have been doing) to produce the answer. This function is a bit tricky, but work out different cases by hand and think about how to turn that into Scheme.

Examples:
```
  (shape '((a 34)(b 77)(g 6)) )
 '(() () ())
```
```
  (shape '(a b c) )
 '()
```
```
  (shape '() )
 '()
```
```
  (shape '(() a ((c () x)())) )
 '(() ((()) ()))
 ```
