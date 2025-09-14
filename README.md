## SimpletronV2

```
Author           Reece Hutchison  
Instructor       Franco Carlacci  
Class            CS3790  
Department       Computer Science  
Assignment #     1
```

This assignment is my implementation of a simulator for a 
computer called the **SimpletronV2**. The SimpletronV2
runs program written in the Simpletron Machine Language,
or SML. The simulator follows the Von Neumann architecture,
meaning each instruction cycle has a fetch, a decode, then 
an execute. 


### How to compile and run the java code:
Compile the program (be in project root)
```
javac -d out $(find src/main/java -name "*.java")
```
Run the project
```
java -cp out simpletron.Main
```

### Description of the two assigned problems:

Euclid's Greatest Common Divisor Algorithm (program 1):  
--> this program is found in **program1.txt**

Pseudocode.
```
INPUT m, n

WHILE n != 0
    temp <- n
    r <- m mod n
    m <- temp
    n <- r
END WHILE

OUTPUT m
HALT
```
SML.
```
// INPUT m, n
READ 50  // m
READ 51  // n

// WHILE n != 0
LOAD 51  // LOOP 
BRANCHZERO 17  // terminate loop if n = 0

    // tmp <- n
    LOAD 51
    STORE 52

    // r <- m mod n (remainder)
    LOAD 50          //  =  m
    DIVIDE 51        //  =  m / n
    MULTIPLY 51      //  =  (m / n) * n
    STORE 53         //  =  store 
    LOAD 50          
    SUBTRACT 53      // remainder = m - ((m / n) * n)
    STORE 51         // n = remainder

    // m <- temp
    LOAD 52
    STORE 50

BRANCH 3  // JUMP TO LOOP

// OUTPUT m
WRITE 50
HALT 0000/
```
Important note for this algorithm is the use of m - floor(m / n) * n = m mod n.

---
Find Min and Max elements in an Array (program 2):  
--> this program is found in **program2.txt**

Pseudocode.
```
// by the way this loop indexing is backwards from the actual
// sml code but it gets the point across easier...  

INPUT n                 // number of elements
INPUT a[0..n-1]         // the array values

min <- a[0]
max <- a[0]

i <- 1
WHILE i < n
    IF a[i] < min THEN
        min <- a[i]
    END IF
    IF a[i] > max THEN
        max <- a[i]
    END IF
    i <- i + 1
END WHILE

OUTPUT min
OUTPUT max
HALT
```
SML. 
```
// INPUT n
READ 100 // store n (amount of elements from 1 - 5)
LOAD 100  // accumulator will act as loop counter until input is read
  
// INPUT a[0..n-1]
BRANCHZERO 28
SWAP
DEC
SWAP
READ 101  // store value in location 101

BRANCHZERO 28
SWAP
DEC
SWAP
READ 102  // store value in location 102

BRANCHZERO 28
SWAP
DEC
SWAP
READ 103  // store value in location 103

BRANCHZERO 28
SWAP
DEC
SWAP
READ 104  // store value in location 104

BRANCHZERO 28
SWAP
DEC
SWAP
READ 105  // store value in location 105

LOAD 101  // load first element in the array

// min <- a[0]
STORE 110  // store in min

// max <- a[0]
STORE 111  // store in max

// i <- 1
LOADIM 101  // load in value for index register (first location of array)
SWAP  // put value in index register 
LOAD 100 // load in loop counter

// WHILE i < n 
BRANCHZERO 51  // LOOP

    SWAP  // decrement the loop counter by swaping into index register
    DEC   // then decrementing and swapping back
    SWAP
    
    STORE 100  // stash the loop counter to free up the accumulator
    
    // IF a[i] < min THEN
    LOAD 110  // AKA load the min into the accumulator register
    SUBTRACTX  // min - array[i]
    BRANCHNEG 44  // skip storing element if not less then min
        // min <- a[i]
        LOADIDX
        STORE 110  // store element in min
    // END IF

    // IF a[i] > max THEN
    LOADIDX  // this time load element into the accumulator register
    SUBTRACT 111  // array[i] - max
    BRANCHNEG 49  // skit storing the element if it is not more then max
        // max <- a[i]
        STORE 111  // store element in max
    // END IF

    INC  // increase index
    BRANCH 33  // JUMP TO LOOP
    
    WRITE 110  // write min to console
    WRITE 111  // write max to console
    
    HALT 0000 
```
NOTE: range for n is **1 - 5**... just copy-paste in more of those 
starting things for more range... 

### Input file format:  
- each line in the file must be a 6 digit integer
- '+' (or emitted sign) means positive
- '-' means negative
- program can have blank lines
- no comments  

The example given program for the assignment would 
look like this: 
```
100007
100008
200007
300008
250009
110009
450000
```