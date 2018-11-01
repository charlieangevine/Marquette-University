# COSC 3410 - Assignment #4
### FORTRAN

**BASIC GOAL (This will earn a grade of B):** Write a program in FORTRAN (gfortran) which reads two 3-dimensional vectors from the user and prints out the two vectors and the angle between them.

Each vector should be stored as a 3-element array of reals and the program should use user-defined functions for the length of a vector and the dot product.

**METHOD:** I'll remind you what the dot product is below, but the basic formula of this program involves the dot product, the lengths of the vectors, and the cosine of the angle between them. If V and W are vectors, and we write Dot(V,W) for the dot product and Len(V) for the length, then

```Dot(V,W) = Len(V) * Len(W) * Cos(angle)```
This tells us that, if we could compute the dot product and the length of the vectors, we would have
```Cos(angle) = Dot(V,W) / (Len(V) * Len(W))```
and then we could find the angle by using the inverse cosine function (which is called ACOS in F77):
```angle = ACOS( Dot(V,W) / (Len(V) * Len(W)) )```
This will give us the angle between the vectors in radians.
While ACOS is an intrinsic (built-in) function, you need to define Dot and Len. If (a,b,c) is a 3D vector, then the Length is just SQRT(a**2 + b**2 + c**2), where SQRT is another intrinsic function (for the square root of a number).

If (a,b,c) and (x,y,z) are two vectors, the Dot product is a*x + b*y + c*z.

You should write two functions (probably named ALEN and DOT) which return these values. ALEN should take one parameter (the array containing the three vector entries) and DOT should take two parameters (arrays for each of the vectors) and each should return one real value.

**EXTRA (to earn a grade higher than B):** The basic assignment calls for reading two vectors and printing them with the angle. One obvious improvement to this program is adding a loop to be able to enter more than one pair of vectors.

Another idea would be to allow vectors longer than 3 entries. Both the dot product and length formulas can be generalized to more than 3 dimensions. In this case, you would need to read in the length of the vectors, then the entries, and then print the vectors and the angle.

You could also use a simple formula to convert the radians angle answer into degrees.

**SAMPLE DATA:** Here is some sample data to help you test your program:

|V1          |	V2            |	Angle(radians)|	Angle(degrees)|
|------------|----------------|---------------|---------------|
|0. 1. 0.    |	2. 0. 0.      |	1.57 .        |	90            |
|1. -0.2 0.5 |	-1. 0.2 -0.5 .|	3.1416 .      |	180           |
|2. 2. 0.    |	1. 1. -1.     |	0.6155 .      |	35            |
|1.2 0.3 7.5 |	2.0 -4.0 1.5 .|	1.216         |	70            |
|1. 2. 0.4   |	-2. -3. 0.    |	2.9256 .      |	168           |

**HAND-IN:** Your program listing along with a sample run showing how your program works and showing off any special features.
