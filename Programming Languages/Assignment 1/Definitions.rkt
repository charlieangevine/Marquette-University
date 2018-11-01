#lang racket

; the value of (dropcount s) is the number of instances in the list where the value drops from a higher value to a lower value.
; Author: Jack Pfeiffer
(define dropcount
  (lambda (s)
    (cond [(null? (cdr s)) 0]
          [(< (car (cdr s)) (car s)) (+ 1 (dropcount (cdr s)))]
          [else (+ 0 (dropcount (cdr s)))]
)))

; the value of (beginning? a b) is a boolean value representing whether or not the list a is the beginning segment of list b.
; Author: Jack Pfeiffer
(define beginning?
  (lambda (a b)
    (cond [(null? a) #t]
          [(null? b) #f]
          [(equal? (car a) (car b)) (beginning? (cdr a) (cdr b))]
          [else #f]
)))

; the value of (sublist? a b) is a boolean value represting if list a is a sublist of list b.
; Author: Jack Pfeiffer
(define sublist?
  (lambda (a b)
    (cond [(null? b) #f]
          [(beginning? a b) #t]
          [else (sublist? a (cdr b))]
)))
