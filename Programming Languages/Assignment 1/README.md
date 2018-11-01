# COSC 3410 - Programming Languages
## Assignment #1

For each of the problems below, submit a file containing the define for the function and also a sample run showing the test cases listed with the problem and at least two other test cases which you make up. Each function should contain a comment giving a brief description of the function and saying who wrote it.
Problems should be submitted using the D2L dropbox by 11:59PM on Friday.

Do all three problems.

1. Write a function dropcount which takes one list of numeric atoms as parameter and returns an integer. The function should count how many times adjacent numbers decrease when reading from left to right. So, for instance, (dropcount '(3 6 3 2 2 4 1)) would be 3 because the value descreases between the second and third entries [6 3], the third and fourth [3 2], and sixth and seventh [4 1], but not between the other adjacent pairs. Be sure to use good recursive style.

#### Examples:

  ```
  (dropcount '(1 2 3 2 1))
 2 
 ```

 ``` 
 (dropcount '(1 2 3 4 5))
 0
```
```
  (dropcount '(7 3.1 5 -2 -3 10 4))
 4
 ```
2. Write a function beginning? which takes two lists as parameters and returns a boolean value. The function should test if the first list is equal to the beginning segment of the second list. Be sure to use good recursive style.

#### Examples:
```
  (beginning? '(a b c) '(a b c d e))
 #t
 ```
```
  (beginning? '(a b c) '(a b))
 #f
```
```
  (beginning? '(ant box cow) '(ant box cow))
 #t
```
```
  (beginning? '() '(1 2 3))
 #t
```
```
  (beginning? '(this is nested) '(this is (nested)))
 #f
 ```
3. Write a function sublist? which takes two lists as parameters and returns a boolean value. The function should check if the first list is a sublist of the second list. Be sure to use good recursive style. You might find the beginning? function from Part 2 useful.

#### Examples:
```
  (sublist? '(c a b) '(a b a c a))
 #f
```
```
  (sublist? '(green red green) '(green purple green red green))
 #t
```
```
  (sublist? '(1 2 3) '())
 #f
```
```
  (sublist? '(a b) '(d e (a b) c))
 #f
```
```
  (sublist? '((4 5) 6) '(4 3 7 (4 5) 6 8))
 #t
 ```
