from fibo import *
import time as time_
import xlwt
import xlrd

funcs = [ fib, mfib, matFib, binFib ]
n_values = {
    "fib": 30,
    "mfib": 1500,
    "matFib": 5000,
    "binFib": 1400
}

def main():
    wb = xlwt.Workbook()
    for f in funcs:
        ws = wb.add_sheet(f.__name__)
        n = n_values[f.__name__]
        
        ws.write(0,0, "n = " + str(n))
        
        for o in range(100):
            ws.write(0, o+1, "Run " + str(o))
        
        for b in range(20):
            print("Batch " + str(b))
            run(ws, f, n, b, 100)
    print("All done.")
    wb.save("hw4.xls")
    
def run(ws, func, n, batch, runs):
    ws.write(batch+1, 0, "Batch " + str(batch))
    for i in range(runs):
        if i % 25 == 0:
            print("  Calculating " + func.__name__ + "(" + str(n) + ")")
        t = time(func, n)
        
        ws.write(batch+1, i+1, t)
        

def time(func, n):
    start = time_.time()
    func(n)
    f = {0:0, 1:1}
    return (time_.time() - start) * (1000**2)
    

main()