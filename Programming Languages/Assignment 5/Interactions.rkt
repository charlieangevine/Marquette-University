Welcome to DrRacket, version 6.10 [3m].
Language: plai, with debugging; memory limit: 128 MB.
> (run '{with {x 2}
  {with {y 12}
    {+ {* x x} {* y y}}
}})
(numV 148)
> (run '{- 1 {- 2 {- 3 {- 4 {- 5 6}}}}})
(numV -3)
> (run '{with {x 3} {= x 2}})
#f
> (run '{with {x 3} {= x 3}})
#t
> (run '{if {= {* 5 21} {* 7 15}}
    9999
    5555
})
(numV 9999)
> (run '{with {x 9}    ; you can change these vals, but the larger one should
  {with {y 3}  ; always wind up at the left of the final number
               ; and the smaller one at the right
     {with  {min  {if {< x y}
                     x 
                     y}}
        {with  {max  {if {< x y}
                        y 
                        x}}
           {+ {* max 1000} min} ; output will be best if smaller is
 }}}}  )
(numV 9003)
> (run '{with {double {fun {n} {* 2 n}}}
  {double 12}})
(numV 24)
> (run '{with {abs {fun {x} {if {< x 0} {- x} x}}} ;; absolute value
   {abs -101}
})
(numV 101)
> (run '{with {double {fun {n} {* 2 n}}}
   {with {abs {fun {x} {if {< x 0} {- x} x}}}
      {double {abs -101}}
   }
}
)
(numV 202)
> (run '{with {mod-base {fun {b}
                  {fun {n}
                    {- n {* b {/ n b}}}
                  }
                }
      }
  {{mod-base 7} 11} ;; Compute 11 mod 7
})
(numV 4)
> (run '{with {mod {fun {n}
                  {fun {b}
                    {- n {* b {/ n b}}}
                  }
                }
      }
  {{mod 100} 7} ;; Compute 100 mod 7
}
)
(numV 2)
> (run '{with {mod {fun {n} {fun {b} {- n {* b {/ n b}}}}}}
            {with {multiple? {fun {n} {fun {m} {= {{mod n }m} 0} }}}
                  {{multiple? 121} 11}}})
#t
> (run '{with {mod {fun {n} {fun {b} {- n {* b {/ n b}}}}}}
            {with {multiple? {fun {n} {fun {m} {= {{mod n }m} 0} }}}
                  {{multiple? 121} 7}}})
#f
> (run '{with {mod {fun {n} {fun {b} {- n {* b {/ n b}}}}}}
            {with {isEvenPositive? {fun {n} {if {< {- n} 0} {= {{mod n} 2} 0} #f}}}
                  {isEvenPositive? 11} }})
#f
> 
