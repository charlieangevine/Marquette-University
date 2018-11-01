#lang plai

(define-type CWAE
  [num (n number?)]
  [id (name symbol?)]
  [add (lhs CWAE?) (rhs CWAE?)]
  [sub (lhs CWAE?) (rhs CWAE?)]
  [mul (lhs CWAE?) (rhs CWAE?)]
  [div (lhs CWAE?) (rhs CWAE?)]
  [minus (n CWAE?)]
  [with (name symbol?) (named-expr CWAE?) (body CWAE?)]
  [ilt (e1 CWAE?) (e2 CWAE?) (v1 CWAE?) (v2 CWAE?)]
)

(define (calc expr)
  (type-case CWAE expr
     [num (n) n]
     [add (l r) (+ (calc l) (calc r))]
     [sub (l r) (- (calc l) (calc r))]
     [mul (l r) (* (calc l) (calc r))]
     [div (l r) (/ (calc l) (calc r))]
     [with (bound-id named-expr bound-body)
           (calc (subst bound-body bound-id (num (calc named-expr))))]
     [id (v) (error 'calc "free identifier")]
     [ilt (e1 e2 v1 v2) (cond [(< (calc e1) (calc e2)) (calc v1)] [else (calc v2)])]
     [minus (n) (* -1 (calc n))]
  ))


(define (parse sexp)
  (cond [(number? sexp) (num sexp)]
        [(symbol? sexp) (id sexp)]
        [(list? sexp)
                (case (first sexp)
                  [(+) (cond [(= (length sexp) 3) (add (parse (second sexp)) (parse (third sexp)))]
                             [else (error 'parse-add "given wrong amount of arguments")])]
                  [(-) (cond [(= (length sexp) 2) (minus (parse (second sexp)))]
                             [(= (length sexp) 3) (sub (parse (second sexp)) (parse (third sexp)))]
                             [else (error 'parse-sub "given wrong amount of arugments")])]
                  [(with) (cond [(and (= (length sexp) 3) (list? (second sexp))) (with (first (second sexp))
                                (parse (second (second sexp)))
                                (parse (third sexp)))]
                                [else (error 'parse-with "given wrong amount of arguments")])]
                  [(*) (cond [(= (length sexp) 3) (mul (parse (second sexp))
                            (parse (third sexp)))]
                             [else (error 'parse-mult "given wrong amount of arguments")])]
                  [(/) (cond [(= (length sexp) 3) (div (parse (second sexp))
                            (parse (third sexp)))]
                             [else (error 'parse-div "given wrong amount of arguments")])]
                  [(if<) (cond [(= (length sexp) 5) (ilt (parse (second sexp))
                              (parse (third sexp))
                              (parse (fourth sexp))
                              (parse (fifth sexp)))]
                               [else (error 'parse-if< "given wrong amount of arguments")])]
                  [else (error 'parse "an error has occurred, either unknown operator or wrong amount of arguments given")]
                  )]
        ))
               

(define(subst expr sub-id val)
    (type-case CWAE expr
        [num (n) expr]
        [add (l r) (add (subst l sub-id val) (subst r sub-id val))]
        [sub (l r) (sub (subst l sub-id val) (subst r sub-id val))]
        [mul (l r) (mul (subst l sub-id val) (subst r sub-id val))]
        [div (l r) (div (subst l sub-id val) (subst r sub-id val))]
        [with (bound-id named-expr bound-body)
              (if (symbol=? bound-id sub-id)
                 (with bound-id (subst named-expr sub-id val) bound-body)
                 (with bound-id (subst named-expr sub-id val) (subst bound-body sub-id val)))]
        [id (v) (if (symbol=? v sub-id) val expr)]
        [ilt (e1 e2 v1 v2) (ilt (subst e1 sub-id val) (subst e2 sub-id val) (subst v1 sub-id val) (subst v2 sub-id val))]
        [minus (n) (minus (subst n sub-id val))]
   ))
