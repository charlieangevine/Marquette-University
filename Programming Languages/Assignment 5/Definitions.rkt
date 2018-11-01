#lang plai

(define-type JOE
  [num (n number?)]
  [boo (b boolean?)]
  [id (name symbol?)]
  [add (lhs JOE?) (rhs JOE?)]
  [sub (lhs JOE?) (rhs JOE?)]
  [mul (lhs JOE?) (rhs JOE?)]
  [div (lhs JOE?) (rhs JOE?)]
  [neg (n JOE?)]
  [equ (lhs JOE?) (rhs JOE?)]
  [ilt (lhs JOE?) (rhs JOE?)]
  [with (name symbol?) (named-expr JOE?) (body JOE?)]
  [iff (expr JOE?) (true JOE?) (false JOE?)]
  [fun (arg-name symbol?) (body JOE?)]
  [app (fun-expr JOE?) (arg JOE?)]
)

(define-type JOE-value
  [numV (n number?)]
  [booleanV (b boolean?)]
  [closureV (param symbol?)
            (body JOE?)
            (ds DefrdSub?)]
  )

(define-type DefrdSub
  [mtSub]
  [aSub (name symbol?) (value JOE-value?) (ds DefrdSub?)]
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
        [(boolean? sexp) (boo sexp)]
        [(eq? (first sexp) '+) (if (= (length sexp) 3) (add (parse (second sexp)) (parse (third sexp))) (error 'add "Incorrect Syntax! Expected 2 Arguments"))]
        [(eq? (first sexp) '-) (if (or (= (length sexp) 3) (= (length sexp) 2)) (if (null? (cddr sexp)) (neg (parse (second sexp))) (sub (parse (second sexp)) (parse (third sexp)))) (error 'sub "Incorrect Syntax! Expected 2 Arguments"))]
        [(eq? (first sexp) '*) (if (= (length sexp) 3) (mul (parse (second sexp)) (parse (third sexp))) (error 'mul "Incorrect Syntax! Expected 2 Arguments"))]
        [(eq? (first sexp) '/) (if (= (length sexp) 3) (div (parse (second sexp)) (parse (third sexp))) (error 'div "Incorrect Syntax! Expected 2 Arguments"))]
        [(eq? (first sexp) '=) (if (= (length sexp) 3) (equ (parse (second sexp)) (parse (third sexp))) (error '= "Incorrect Syntax! Expected 2 Arguments"))]
        [(eq? (first sexp) '<) (if (= (length sexp) 3) (ilt (parse (second sexp)) (parse (third sexp))) (error '< "Incorrect Syntax! Expected 2 Arguments"))]
        [(eq? (first sexp) 'with) (if (and (= (length sexp) 3) (= (length (second sexp)) 2)) (app (fun (car (second sexp)) (parse (third sexp))) (parse (second (second sexp)))) (error 'with "Incorrect Syntax! Expected 2 Arguments, First Argument should be list of two."))]
        ;[(eq? (first sexp) 'with) (with (first (second sexp)) (parse (second (second sexp))) (parse (third sexp)))]
        [(eq? (first sexp) 'if) (if (= (length sexp) 4) (iff (parse (second sexp)) (parse (third sexp)) (parse (fourth sexp))) (error 'if "Incorrect Sytax! Expected 3 Arguments"))]
        [(eq? (first sexp) 'fun) (if (= (length sexp) 3) (fun (car (second sexp)) (parse (third sexp))) (error 'fun "Incorrect Syntax! Expected 2 Arguments"))]
        [else (app (parse (first sexp)) (parse (second sexp)))]
))

(define (interp expr ds)
  (type-case JOE expr
    [num (n) (numV n)]
    [boo (b) (booleanV b)]
    [add (l r) (calc '+ (interp l ds) (interp r ds))]
    [sub (l r) (calc '- (interp l ds) (interp r ds))]
    [mul (l r) (calc '* (interp l ds) (interp r ds))]
    [div (l r) (calc '/ (interp l ds) (interp r ds))]
    [neg (n) (calc '* (numV -1) (interp n ds))]
    [equ (l r) (equal? (interp l ds) (interp r ds))]
    [ilt (l r) (< (numV-n (interp l ds)) (numV-n (interp r ds)))]
    [iff (e t f) (if (interp e ds) (interp t ds) (interp f ds))]
    [with (bound-id named-expr bound-body) (interp bound-body (aSub bound-id (interp named-expr ds) ds))]
    [id (v) (lookup v ds)]
    [fun (arg body) (closureV arg body ds)]
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

(define (length list) (cond [(null? list) 0] [else (+ 1 (length (cdr list)))]))

(define (run expr)  (interp (parse expr) (mtSub)))

;{with {mod {fun {n} {fun {b} {- n {* b {/ n b}}}}}}
;            {with {multiple? {fun {n} {fun {m} {= {{mod n }m} 0} }}}
;                  {{multiple? 121} 11}}}

;{with {mod {fun {n} {fun {b} {- n {* b {/ n b}}}}}}
;            {with {multiple? {fun {n} {fun {m} {= {{mod n }m} 0} }}}
;                  {{multiple? 121} 7}}}

;{with {mod {fun {n} {fun {b} {- n {* b {/ n b}}}}}}
;            {with {isEvenPositive? {fun {n} {if {< {- n} 0} {= {{mod n} 2} 0} #f}}}
;                  {isEvenPositive? 11} }}
