#lang plai

(define-type TJOE
  [num (n number?)]
  [id (name symbol?)]
  [add (lhs TJOE?) (rhs TJOE?)]
  [sub (lhs TJOE?) (rhs TJOE?)]
  [mul (lhs TJOE?) (rhs TJOE?)]
  [div (lhs TJOE?) (rhs TJOE?)]
  [neg (n TJOE?)]
  [equ (lhs TJOE?) (rhs TJOE?)]
  [ilt (lhs TJOE?) (rhs TJOE?)]
  [iff (expr TJOE?) (true TJOE?) (false TJOE?)]
  [fun (arg-name symbol?) (arg-type Type?) (body TJOE?)]
  [app (fun-expr TJOE?) (arg TJOE?)]
)

(define-type TJOE-value
  [numV (n number?)]
  [booleanV (b boolean?)]
  [closureV (param symbol?)
            (body TJOE?)
            (type Type?)
            (ds DefrdSub?)]
  )

(define-type DefrdSub
  [mtSub]
  [aSub (name symbol?) (value TJOE-value?) (ds DefrdSub?)]
)

(define (lookup name ds)
  (type-case DefrdSub ds
    [mtSub () (error 'lookup "no binding found for id ~a" name)]
    [aSub (bound-name bound-value rest-ds)
          (if (symbol=? name bound-name)
              bound-value
              (lookup name rest-ds))]
))

(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(symbol? sexp) (id sexp)]
        [(eq? (first sexp) '+) (if (= (length sexp) 3) (add (parse (second sexp)) (parse (third sexp))) (error 'add "Incorrect Syntax! {+ <TJOE> <TJOE>}"))]
        [(eq? (first sexp) '-) (if (or (= (length sexp) 3) (= (length sexp) 2)) (if (null? (cddr sexp)) (neg (parse (second sexp))) (sub (parse (second sexp)) (parse (third sexp)))) (error 'sub "Incorrect Syntax! {- <TJOE> <TJOE>} or {- <TJOE>}"))]
        [(eq? (first sexp) '*) (if (= (length sexp) 3) (mul (parse (second sexp)) (parse (third sexp))) (error 'mul "Incorrect Syntax! {* <TJOE> <TJOE>}"))]
        [(eq? (first sexp) '/) (if (= (length sexp) 3) (div (parse (second sexp)) (parse (third sexp))) (error 'div "Incorrect Syntax! {/ <TJOE> <TJOE>}"))]
        [(eq? (first sexp) '=) (if (= (length sexp) 3) (equ (parse (second sexp)) (parse (third sexp))) (error '= "Incorrect Syntax! {= <TJOE> <TJOE>}"))]
        [(eq? (first sexp) '<) (if (= (length sexp) 3) (ilt (parse (second sexp)) (parse (third sexp))) (error '< "Incorrect Syntax! {< <TJOE> <TJOE>}"))]
        [(eq? (first sexp) 'with) (if (and (= (length sexp) 3) (= (length (second sexp)) 4)) (app (fun (first (second sexp)) (type-parse (third (second sexp))) (parse (third sexp))) (parse (fourth (second sexp)))) (error 'with "Incorrect Syntax! {with {<id> : <type> <TJOE>} <TJOE>}"))]
        [(eq? (first sexp) 'if) (if (= (length sexp) 4) (iff (parse (second sexp)) (parse (third sexp)) (parse (fourth sexp))) (error 'if "Incorrect Sytax! {if <TJOE> <TJOE> <TJOE>}"))]
        [(eq? (first sexp) 'fun) (if (and (= (length sexp) 3) (= (length (second sexp)) 3)) (fun (first (second sexp)) (type-parse (third (second sexp))) (parse (third sexp))) (error 'fun "Incorrect Syntax! {fun {<id> : <type>} <TJOE>}"))]
        [else (if (= (length sexp) 2) (app (parse (first sexp)) (parse (second sexp))) (error 'app "Incorrect Syntax! {<TJOE> <TJOE>}"))]
))

(define (interp expr ds)
  (type-case TJOE expr
    [num (n) (numV n)]
    [add (l r) (calc '+ (interp l ds) (interp r ds))]
    [sub (l r) (calc '- (interp l ds) (interp r ds))]
    [mul (l r) (calc '* (interp l ds) (interp r ds))]
    [div (l r) (calc '/ (interp l ds) (interp r ds))]
    [neg (n) (calc '* (numV -1) (interp n ds))]
    [equ (l r) (equal? (interp l ds) (interp r ds))]
    [ilt (l r) (< (numV-n (interp l ds)) (numV-n (interp r ds)))]
    [iff (e t f) (if (interp e ds) (interp t ds) (interp f ds))]
    [id (v) (lookup v ds)]
    [fun (arg type body) (closureV arg type body ds)]
    [app (fun-expr arg-expr)
         (let ((fun-closure (interp fun-expr ds)))
           (interp (closureV-body fun-closure)
                   (aSub (closureV-param fun-closure)
                         (interp arg-expr ds)
                         (closureV-ds fun-closure))))]
))

(define (calc s a b)
  (numV
  (case s
    ['+ (+ (numV-n a) (numV-n b))]
    ['- (- (numV-n a) (numV-n b))]
    ['* (* (numV-n a) (numV-n b))]
    ['/ (quotient (numV-n a) (numV-n b))]
   )))

; ---------------------------------------------------------------------------------------------------------------------------------------------
; --                          ***                          New Stuff for Assignment #7                          ***                          --
; ---------------------------------------------------------------------------------------------------------------------------------------------

(define (type-lookup name te)
  (type-case TypeEnv te
    [mtTypeSub () (error 'type-lookup "could not find type for ~a" name)]
    [aType (bound-id bound-type rest-te)
           (if (symbol=? name bound-id)
               bound-type
               (type-lookup name rest-te))]
))

(define-type Type
  [numType]
  [boolType]
  [funType (domain Type?)
           (codomain Type?)])

(define-type TypeEnv
  [mtTypeSub]
  [aType (id symbol?) (type Type?) (te TypeEnv?)]
)

(define (check tree fun-expr arg-expr te)
  (type-case Type (get-type fun-expr te)
    [funType (domain codomain) (if (equal? domain (get-type arg-expr te)) codomain (error 'get-type "The argument type must match the type of the function's domain"))]
    [else (if (equal? (get-type fun-expr te) (get-type arg-expr te)) (get-type arg-expr te) (error 'get-type "Could not get type from app"))]))

(define (get-type tree te)
  (cond [(TJOE? tree)
  (type-case TJOE tree
    [num (n) (if (number? n) (numType) (error 'get-type "Wrong Type! {num (numType)}" n))]
    [add (l r) (if (and (equal? (get-type l te) (numType)) (equal? (get-type r te) (numType))) (numType) (error 'get-type "Wrong Type! {+ (numType) (numType)}"))]
    [sub (l r) (if (and (equal? (get-type l te) (numType)) (equal? (get-type r te) (numType))) (numType) (error 'get-type "Wrong Type! {- (numType) (numType)}"))]
    [mul (l r) (if (and (equal? (get-type l te) (numType)) (equal? (get-type r te) (numType))) (numType) (error 'get-type "Wrong Type! {* (numType) (numType)}"))]
    [div (l r) (if (and (equal? (get-type l te) (numType)) (equal? (get-type r te) (numType))) (numType) (error 'get-type "Wrong Type! {/ (numType) (numType)}"))]
    [neg (n) (if (equal? (get-type n te) (numType)) (numType) (error 'get-type "Wrong Type! {- (numType)}"))]
    [equ (l r) (if (equal? (get-type l te) (get-type r te)) (boolType) (error 'get-type "Wrong Type! {= (numType) (numType)}"))]
    [ilt (l r) (if (and (equal? (get-type l te) (numType)) (equal? (get-type r te) (numType))) (boolType) (error 'get-type "Wrong Type! {< (numType) (numType)}"))]
    [iff (e t f) (if (and (equal? (get-type e te) (boolType)) (equal? (get-type t te) (get-type f te))) (get-type t te) (error 'get-type "Wrong Type! {if (boolType) (TJOE) (TJOE)}   (the two TJOE's must be of the same type!"))]
    [fun (arg type body) (funType type (get-type body (aType arg type te)))]
    [app (fun-expr arg-expr) (if (funType? (get-type fun-expr te)) (check tree fun-expr arg-expr te) (error 'get-type "Wrong Type! {(funType) (Type?)} - instead of funType, got ~a" (get-type fun-expr te)))]
    [id (name) (type-lookup name te)]
  )]

        [else (error 'hi "found")])
)

(define (type-parse sexp)
  (cond [(list? sexp) (funType (type-parse (first sexp)) (type-parse (third sexp)))]
        [(eq? sexp 'number) (numType)]
        [(eq? sexp 'boolean) (boolType)]
))

(define (gtp sexp) (get-type (parse sexp) (mtTypeSub)))
