Welcome to DrRacket, version 6.10 [3m].
Language: racket, with debugging; memory limit: 128 MB.
> (differences '(2 7 10 3 6))
'(5 3 -7 3)
> (differences '(1 3))
'(2)
> (differences '())
'()
> (differences '(1 2 -3 -4 7 12))
'(1 -5 -1 11 5)
> (differences '(1 4 12 -4 0 4 0))
'(3 8 -16 4 4 -4)
> (differences '(1 0 0 3 -8 4 0))
'(-1 0 3 -11 12 -4)
> (shape '((a 34)(b 77)(g 6)))
'(() () ())
> (shape '(a b c))
'()
> (shape '())
'()
> (shape '(() a ((c () x)())))
'(() ((()) ()))
> (shape '(1 (2 (3 4))))
'((()))
> (shape '(1 5 (2 3) (4) 6))
'(() ())
> (deep-add '(5 a b 8 2))
15
> (deep-add '((4 (6 1)) 2 3 (4)))
20
> (deep-add '(these (aren't 77) (all 32 (numbers 93 here))))
202
> (deep-add '())
0
> (deep-add '(no numbers here))
0
> (deep-add '((4 8 3 -4) (-4 7 9 -1)))
22
> (deep-add '((3 -9 1 8 -4) (-3 2 -1 -8 4 7)))
0
> (zip '(a b c d e) '(32 7 10 3 1))
'((a 32) (b 7) (c 10) (d 3) (e 1))
> (zip '() '())
'()
> (zip '((a b) c (d e f)) '(c (a) (b c)))
'(((a b) c) (c (a)) ((d e f) (b c)))
> (zip '((a) c (e) g) '(b (d) f (h)))
'(((a) b) (c (d)) ((e) f) (g (h)))
> (zip '(1 2 5 13 24) '(1 3 8 21 45))
'((1 1) (2 3) (5 8) (13 21) (24 45))
> (zip '(1 2 -3) '(50 40 30 20))
. . zip: first list too short
> 
