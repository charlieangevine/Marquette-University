Welcome to DrRacket, version 6.10 [3m].
Language: racket, with debugging; memory limit: 128 MB.
> (dropcount '(1 2 3 2 1))
2
> (dropcount '(1 2 3 4 5))
0
> (dropcount '(7 3.1 5 -2 -3 10 4))
4
> (dropcount '(1 -1 2 -2 3 -3))
3
> (dropcount '(36 64 212 2 8))
1
> (beginning? '(a b c) '(a b c d e))
#t
> (beginning? '(a b c) '(a b))
#f
> (beginning? '(ant box cow) '(ant box cow))
#t
> (beginning? '() '(1 2 3))
#t
> (beginning? '(this is nested) '(this is (nested)))
#f
> (beginning? '(green (eggs)) '(green eggs and ham))
#f
> (beginning? '(mary had a) '(mary had a little lamb))
#t
> (sublist? '(c a b) '(a b a c a))
#f
> (sublist? '(green red green) '(green purple green red green))
#t
> (sublist? '(1 2 3) '())
#f
> (sublist? '(a b) '(d e (a b) c))
#f
> (sublist? '((4 5) 6) '(4 3 7 (4 5) 6 8))
#t
> (sublist? '(humpty dumpty) '(humpty dumpty (sat on a)))
#t
> (sublist? '(go stop go) '((go stop go) go stop))
#f
> 
