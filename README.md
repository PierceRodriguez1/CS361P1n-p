# Project 1: P1 Deterministic Finite Automata 

* Author: Nolan Stetz
* Class: CS361 Section 2
* Semester: Spring 2024 

## Overview

This Java application is essentially a simulation for a DFA. This allows you to create a start state, final state 
and any number of 'normal' states. As well as transitions on a certain character. We can show if the string is accepted or
not by using our accepts method and we can perform swaps on a transition from a certain state to another. We also have a nicely
formatted toString to output the DFA's contents.

## Reflection

There were some definite struggles during this project. We had a really tough time implementing the swaps method. It doesn't work properly
even up to now. I will continue to work on it in my free time to eventually get it but for now I have to stop working on this project and 
continue on other things. The idea of creating a DFA seemed daunting at first but now once I look back at it I should've enjoyed it more and
took advantage of the time I got to work on this. Overall I had a really good time and enjoyed putting it together. 

- What worked well and what was a struggle? 
  - Swap method was a challenge and a recusive approach to 'accepts' was really easy compared to iterative or something else.
- What concepts still aren't quite clear?
  -I still need to better understand the swap method and how it works with created basically a temporary state to handle the swap.
- What techniques did you use to make your code easy to debug and modify?
  - I used to have some println's in my code to see where it was failing but once I moved to IntelliJ it was a lot easier to debug with their suite of tools.
- What would you change about your design process?
  - Starting earlier and putting in more hours before the deadline. That was our downfall.
- If you could go back in time, what would you tell yourself about doing this project?
  - Start early, and if I encounter a problem, ask for help. As well as communicating with your teammates what you are working on so there are no merge conflicts.

## Compiling and Using

To compile, execute the following command in the main project directory:
```
$ javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java
```

Run the compiled class with the command:
```
$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/core.jar org.junit.runner.JUnitCore test.dfa.DFATest
```
This will compile and run the tester with the two lines above. The output will be presented in the terminal.

## Sources used

- No sources used except GeeksForGeeks and Oracle.com to understand and see which dataset we wanted to use:
    https://docs.oracle.com/javase/8/docs/api/java/util/HashSet.html
    https://www.geeksforgeeks.org/hashset-in-java/
