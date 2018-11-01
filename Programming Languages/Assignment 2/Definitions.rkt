#lang racket

; Written by Jack Pfeiffer

; difference: list -> list
; returns a list of the difference between each successive term.

(define differences
  (lambda (lat)
    (cond [(or (null? lat) (null? (cdr lat))) '()] ; If either the list is empty or only has one element, return empty list that will ultimetly be returned
          [else (cons (- (car (cdr lat)) (car lat)) (differences (cdr lat)))] ; add the difference of the first two elements to the list to be returned
)))

; zip: list list -> list
; combines elements in both list based on placement

(define zip
  (lambda (f s)
    (cond [(< (length f) (length s)) (error 'zip "first list too short")]    ; If the lists are not the same length
          [(> (length f) (length s)) (error 'zip "second list too short")]   ; print out an error message
          [(null? f) '()] ; If the lists are emepty return an empty list.
          [else (cons (list (car f) (car s)) (zip (cdr f) (cdr s)))] ; otherwise make a list of the first two elements and add it to the rest of the zipped elements
)))

; deep-add: list -> number
; returns the sum of all the numeric elements in the list

(define deep-add
  (lambda (s)
    (cond [(null? s) 0] ; if the list is empty return sum of 0
          [(number? (car s)) (+ (car s) (deep-add (cdr s)))] ; if the first element of the list is a number add it to the sum of the rest of elements
          [(list? (car s)) (+ (deep-add (car s)) (deep-add (cdr s)))] ; If the first element of the list is a list sum up the included elements and add to the rest of the elements.
          [else (deep-add (cdr s))] ; otherwise if either case does not pass continue on with the rest of the list
)))

; shape: list -> list
; returns the structure of the list without any elements

(define shape
  (lambda (s)
    (cond [(null? s) '()] ; If the size of the list is empty return an empty list
          [(list? (car s)) (cons (shape (car s)) (shape (cdr s)))] ; if the first element is a list add the shape of the first element (list) to the returning list.
          [else (shape (cdr s))] ; otherwise move on with the rest of the list.
)))
