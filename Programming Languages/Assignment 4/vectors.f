C     VECTOR CALCULATOR
C     JACK PFEIFFER
C
      PROGRAM VECTOR     
      REAL :: PI = 3.1415926
5     FORMAT ('RAD=',F5.2)
10    FORMAT ('DEG=',F5.1)
C      
      REAL A(3)
      REAL B(3)
      PRINT *, 'Enter two vectors.'
      PRINT *, 'Enter 0 0 0 to exit.'
1     PRINT *,''
      READ *,(A(I),I=1,3)
      IF(A(1).EQ.0 .AND. A(2).EQ.0 .AND. A(3).EQ.0) CALL GOODBYE
      READ *,(B(I),I=1,3)
      IF(B(1).EQ.0 .AND. B(2).EQ.0 .AND. B(3).EQ.0) CALL GOODBYE
      PRINT 5,ANGLE(A,B)
      PRINT 10,DEG(ANGLE(A,B))
      GOTO 1
      END
C     
      FUNCTION ANGLE(A,B)
      REAL A(3)
      REAL B(3)
      ANGLE = DOT(A,B) / (ALEN(A) * ALEN(B))
      ANGLE = ACOS(ANGLE)
      RETURN
      END
C   
      FUNCTION ALEN(A)
      REAL A(3)
      LEN = SQRT(A(1)**2 + A(2)**2 + A(3)**2)
      RETURN
      END
C     
      FUNCTION DOT(A,B)
      REAL A(3)
      REAL B(3)
      DOT = A(1) * B(1) + A(2) * B(2) + A(3) * B(3)
      RETURN
      END
C      
      FUNCTION DEG(R)
      REAL, PARAMETER :: PI = 3.1415926
      DEG = r * (180 / PI)
      RETURN
      END
      
      SUBROUTINE GOODBYE 
      PRINT *,'GOODBYE.'
      STOP
      END
