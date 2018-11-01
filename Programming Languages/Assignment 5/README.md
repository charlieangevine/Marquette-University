# COSC 3410 - Programming Languages
#### Assignment #5

**Goal:** Write an interpreter for the JOE language described below. Your interpreter should include error checking (see below) and use an environment to record deferred substitutions. The parser should convert any "with" expressions into the corresponding "fun" expression (see p.45 of Krishnamurthi).

**Language spec:** The strings of letters in the book's language names were getting ridiculous, so I decided to just name this language "JOE" (it doesn't stand for anything). Here's the grammar for JOE expressions:
```
<JOE> ::= <num>
         | <id>
         | {+ <JOE> <JOE>}
         | {- <JOE> <JOE>}
         | {* <JOE> <JOE>}
         | {/ <JOE> <JOE>}           ;; integer divide
         | {- <JOE>}
         | {= <JOE> <JOE>}           ;; equality test
         | {< <JOE> <JOE>}           ;; less than test
         | {with {<id> <JOE>} <JOE>} ;; using fun rewrite
         | {if <JOE> <JOE> <JOE>}
         | {fun {<id>} <JOE>}
         | {<JOE> <JOE>}             ;; function app
```         
**Notes:** I'm replacing the "if<" with a more general "if" (like in Scheme) and conditional expressions. The values of the conditional expressions should be boolean, which means you'll want to extend the possible values from just numV and closureV to include booleanV. You can use the Scheme boolean values #t and #f for true and false.
Remember that "quotient" is the integer divide function in Scheme.

**Error checking:** Your parser should check the following requirements:

* Arithmetic and comparisons should have a legal operator symbol and two operands (except for "-" which can also have one operand).
* "if" should have three operands.
* Function application (lists not starting with an operator symbol or keyword) should have exactly two entries.
* "with" should have two operands - the first a list of length 2 beginning with an <id>.
* "fun" should have two operands, the first a list of length 1 containing an <id>.

**Hand-in:** You should submit your Scheme code, sample runs on the test programs provided, and at least two new test programs which you write.
