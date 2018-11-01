import time as time_
from fibo import *

##########################################
#            Assignment #5
#   Measure the time it takes to calculate the nth Fibonacci number fn.
#   
##########################################

##########################################
#            DRIVER FUNCTIONS            #
##########################################
class color:
    GREEN = '\033[92m'
    YELLOW = '\033[93m'
    RED = '\033[91m'
    BOLD = '\033[1m'
    END = '\033[0m'

def run(func, n):
    f = {0:0, 1:1} # CLEAR MEMORIZATION DICTIONARY FOR EACH RUN
    
    start = time_.time() ## Gives the time in seconds
    a = func(n)
    end = time_.time()
    diff = end - start
    
    diff = diff * (1000**2) # Microseconds
    
    diff = int(round(diff * 100)) / 100
        
    return a, diff

def time(func, n):
    a,t= run(func,n)
    pprint(func,n,a,t)

def pprint(func,n,a,t):
    print("   " + func.__name__ + "(" + str(n) + ") = " + str(a))
    
    u = 0
    units = ["microseconds", "milliseconds", "seconds"]
    c = [color.GREEN, color.YELLOW, color.RED]
    while(t > 1000 and u < 2):
        t /= 1000
        u += 1
    t = int(round(t * 100)) / 100
    
    print(color.BOLD +  c[u] + "   Took " + str(t) + " " + units[u] + color.END)
    
def avg(func, n, o):
    tt = 0
    a,_ = run(func, n)
    for i in range(o):
        _,t = run(func, n)
        tt += t
    tt = tt / o
    pprint(func,n,a,tt)
        

def parse(s):
    fun = s[0 : s.find("(")]
    split = s[s.find("(") + 1 : s.find(")")].split(":")
    start = int(split[0])
    end = int(split[0])
    average = 0
    if "*" in s:
        average = int(s.split("*")[1])
    if len(split) > 1:
        start = int(split[0])
        end = int(split[1])
    return fun, start, end, average

def main():
    funcs = { "fib": fib, "mfib": mfib, "matFib": matFib, "binFib": binFib }
    
    s = input(">> ")
    while s != "exit": 
        fun,start,end,average = parse(s)
        if fun in funcs:
            method = funcs[fun]
            for i in range(start, end + 1):
                if not(average == 0):
                    print("Running " + method.__name__ + "(" + str(i) + ") x" + str(average) + " times ...")
                    avg(method, i, average)
                else:
                    print("Running " + method.__name__ + "(" + str(i) + ") ...")
                    time(method, i)
        elif fun == "all":
            for method in funcs.values():
                if not(average == 0):
                    print("Running " + method.__name__ + "(" + str(end) + ") x" + str(average) + " times ...")
                    avg(method, end, average)
                else:
                    print("Running " + method.__name__ + "(" + str(end) + ") ...")
                    time(method, end)
        else:
            print("Could not interpret '" + s + "'!")
            
        s = input(">> ")
main()