Welcome to DrRacket, version 6.10 [3m].
Language: plai, with debugging; memory limit: 128 MB.
> (parse '{* 2 2})
(mul (num 2) (num 2))
> (parse '{* 4 {* 2 1}})
(mul (num 4) (mul (num 2) (num 1)))
> (calc (parse '{* 4 {* 4 3}}))
48
> (calc (parse '{* 7 3}))
21
> (parse '{/ 12 5})
(div (num 12) (num 5))
> (parse '{/ 48 {/ 8 2}})
(div (num 48) (div (num 8) (num 2)))
> (calc (parse '{/ 100 10}))
10
> (calc (parse '{/ {/ 48 4} {/ 9 3}}))
4
> (parse '{- 33})
(minus (num 33))
> (parse '{- -33})
(minus (num -33))
> (calc (parse '{- -1}))
1
> (calc (parse '{- {- 402}}))
402
> (parse '{if< 44 33 22 104})
(ilt (num 44) (num 33) (num 22) (num 104))
> (parse '{if< {+ 4 3} {* 4 3} {/ 4 2} {- 33}})
(ilt (add (num 4) (num 3)) (mul (num 4) (num 3)) (div (num 4) (num 2)) (minus (num 33)))
> (calc (parse '{if< {+ 4 3} {* 4 3} {/ 4 2} {- 33}}))
2
> (calc (parse '{if< -2 2 4 -4}))
4
> (parse '{with {num1 -33} {with {num2 876} {with {num3 305} {if< num1 num2 {if< num2 num3 num3 num2} {if< num1 num3 num3 num1}}}}})
(with
 'num1
 (num -33)
 (with
  'num2
  (num 876)
  (with
   'num3
   (num 305)
   (ilt (id 'num1) (id 'num2) (ilt (id 'num2) (id 'num3) (id 'num3) (id 'num2)) (ilt (id 'num1) (id 'num3) (id 'num3) (id 'num1))))))
> (calc (parse '{with {a 1} {with {b 3} {with {c 2} {/ {+ {- b} {- {* b b} {* {* 4 a} c}}} {* 2 a}}}}}))
-1
> {parse '{+ 4 5 6}}
. . parse-add: given wrong amount of arguments
> {parse '{+ 2}}
. . parse-add: given wrong amount of arguments
> {parse '{with 6}}
. . parse-with: given wrong amount of arguments
> {parse '{- 6 5 2}}
. . parse-sub: given wrong amount of arugments
> {parse '{+}}
. . parse-add: given wrong amount of arguments
> 
