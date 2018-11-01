# COSC 3410 - Programming Languages
#### Assignment #7

**Goal:** Write a type checker for the TJOE (Typed JOE) language described below. Your type checker should use the parser from Assignment #5 (with slight changes for the type specifications) so that it will include syntax error checking as in that assignment. In a type checker, the source code is run through the parser to get abstract syntax trees. Then, it is *not* run through interp! The result of the type checker does not include the value of an expression - only its type. Your type checker should implement type judgements like those in Chapter 25 of Krishnamurthi.

**Language spec:** The only changes to the JOE language are the addition of type declarations in the with and fun expressions.
```
<TJOE> ::= <num>
         | <id>
         | {+ <TJOE> <TJOE>}
         | {- <TJOE> <TJOE>}
         | {* <TJOE> <TJOE>}
         | {/ <TJOE> <TJOE>}
         | {- <TJOE>}
         | {= <TJOE> <TJOE>}           ;; equality test
         | {< <TJOE> <TJOE>}           ;; less than test
         | {with {<id> : <type> <TJOE>} <TJOE>} ;; using fun rewrite
         | {if <TJOE> <TJOE> <TJOE>}
         | {fun {<id> : <type>} <TJOE>}
         | {<TJOE> <TJOE>}             ;; function app
<type> ::= number
         | boolean
         | ( <type> -> <type> )  ;; the ordinary parens and "->" are
                                 ;;  new symbols in the language
```
**Note:** The above grammar assumes the parser will rewrite "with" expressions into "fun" expressions. This means that the "with" syntax needs to include the type declaration that we will need in the rewritten form. However, if you want to have the type checker directly handle "with", then you don't need that type declaration. In other words, you can compute the type of a "with" expression without any special type declaration (since the named-expression gives you the type of the identifier).
**One difference from Krishnamurthi:** In the text, Krishnamurthi has
```
         | {fun {<id> : <type>} : <type> <TJOE>}
```
for the "fun" expression. That is, you specify a type for the argument and also a type for the result. This means that in the type judgment on p.245, Krishnamurthi explains that you compute the type of the function body and check that it matches the return type specified in the declaration (which is called tau-sub-2 there). This extra declaration is essential for recursive functions. Since I've left recursive functions out of the JOE language, I've also simplified the grammar. That means you still get the type of the function body, but you don't compare it to anything. It just tells you the return type of the function.
**Method:** You'll need a new datatype for "type"s Something like
```
(define-type Type
  [numType]
  [boolType]
  [funType (domain Type?)
           (codomain Type?)])
```
The trees in the TJOE datatype will need to have fields for the Type entries. I would recommend writing a little "type-parse" function which takes things like '(number -> number) and turns them into a Type tree like (funType (numType) (numType)). You can then call that type-parser from parse when you need to read a type declaration.
When you need to compare two types, you can do it using "equal?". Remember you can also see if a Type is a certain variant (e.g. is it a function type?) using the predicates numType?, etc. defined by define-type (see [the page on PLAI Scheme](http://docs.racket-lang.org/plai/plai-scheme.html)).

Your type checker should be organized as a function (say "get-type") which takes a TJOE abstract syntax tree and a type environment and returns a Type value. You'll need to define a TypeEnv datatype (like Env) to store type bindings. For instance, (get-type (parse '5) (mtTypeSub)) should return (numType) and (get-type (parse '{fun {n : number} {+ n n}}) (mtTypeSub)) should return (funType (numType) (numType)). If get-type is called on an invalid expression, then some sort of "bad type" error should be returned. The more specific you can make the error message, the easier it will be for the programmer to find the error.

**Hand-in:** You should submit your Scheme code, sample runs on the test programs provided, and at least two new test programs which you write.
