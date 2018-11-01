import math

##########################################
#           REGULAR FIBONACCI            #
##########################################

def fib(n):
    if n == 0 or n == 1:
        return n
    return fib(n - 1) + fib(n - 2)



##########################################
#         MEMORIZATION FIBONACCI         #
##########################################

f = {0:0, 1:1}
def mfib(n):
    if n in f:
        return f[n]
    f[n] = mfib(n - 1) + mfib(n - 2)
    return f[n]



##########################################
#            MATRIX FIBONACCI            #
##########################################

def matFib(n):
    m = [[1, 1], [1, 0]]
    for i in range(n):
        m = mult(m, [[1, 1], [1, 0]])
    return m[1][1]
    
def mult(A, B):
    if len(A[0]) != len(B):
        print("Cannot multiply the two matrics. Incorrect dimensions.")
        return
    
    C = [[0 for row in range(len(B[0]))] for col in range(len(A))]
    for i in range(len(A)):
        for j in range(len(B[0])):
            for k in range(len(A[0])):
                C[i][j] += A[i][k] * B[k][j]
    return C



##########################################
#           BINET'S FIBBONACCI           #
##########################################

def binFib(n):
    o = (1 + math.sqrt(5)) / 2
    v = (1 - math.sqrt(5)) / 2
    return math.floor((o**n - v**n) / math.sqrt(5))