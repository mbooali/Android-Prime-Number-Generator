# Android-Prime-Number-Generator

1.	Introduction
In this project we have developed an Android application (with a pure java core functions to do arithmatic operations and implementation of mathematical algorithms). This application mainly is contains two main parts:
 
Figure 1 - Prime Number Generator Flow Chart
The main concern of this application is to examine the running time of key generation algorithms on mobile devices. The first part is a block which gets a initial value as seed and generate a random number for given length. This block is using Mersenne Twister algorithm to generate random number.
1.1.	Mersenne Twister 
The following piece of pseudocode generates uniformly distributed 32-bit integers in the range [0, 232 − 1] with the MT19937 algorithm and this procedure is used as my guideline to implement the random number generator block:
 // Create a length 624 array to store the state of the generator
 int[0..623] MT
 int index = 0
 
 // Initialize the generator from a seed
 function initialize_generator(int seed) {
     index := 0
     MT[0] := seed
     for i from 1 to 623 { // loop over each other element
         MT[i] := lowest 32 bits of(1812433253 * (MT[i-1] xor (right shift by 30 bits(MT[i-1]))) + i) // 0x6c078965
     }
 }
 
 // Extract a tempered pseudorandom number based on the index-th value,
 // calling generate_numbers() every 624 numbers
 function extract_number() {
     if index == 0 {
         generate_numbers()
     }
 
     int y := MT[index]
     y := y xor (right shift by 11 bits(y))
     y := y xor (left shift by 7 bits(y) and (2636928640)) // 0x9d2c5680
     y := y xor (left shift by 15 bits(y) and (4022730752)) // 0xefc60000
     y := y xor (right shift by 18 bits(y))

     index := (index + 1) mod 624
     return y
 }
 
 // Generate an array of 624 untempered numbers
 function generate_numbers() {
     for i from 0 to 623 {
         int y := (MT[i] and 0x80000000)                       // bit 31 (32nd bit) of MT[i]
                        + (MT[(i+1) mod 624] and 0x7fffffff)   // bits 0-30 (first 31 bits) of MT[...]
         MT[i] := MT[(i + 397) mod 624] xor (right shift by 1 bit(y))
         if (y mod 2) != 0 { // y is odd
             MT[i] := MT[i] xor (2567483615) // 0x9908b0df
         }
     }
 }

The second part is a another block which playing role of primility checker in this system. This block equipped to two different algorithms. These two algorithms are Miller-Rabin and Solovay-Strassen Algorithms. The main concepts of these algorithms will be discussed as follows:
1.2.	Solovay Strassen
The Solovay–Strassen primality test, developed by Robert M. Solovay and Volker Strassen, is a probabilistic test to determine if a number is composite or probably prime. It has been largely superseded by the Baillie-PSW primality test and the Miller–Rabin primality test, but has great historical importance in showing the practical feasibility of the RSA cryptosystem.
Euler proved that for any prime number p and any integer a,

where  is the Legendre symbol. The Jacobi symbol is a generalisation of the Legendre symbol to , where n can be any odd integer. The Jacobi symbol can be computed in time O((log n)²) using Jacobi's generalization of law of quadratic reciprocity. Given an odd number n we can contemplate whether or not the congruence

holds for various values of the "base" a, given that a is relatively prime to n. If n is prime then this congruence is true for all a. So if we pick values of a at random and test the congruence, then as soon as we find an a which doesn't fit the congruence we know that n is not prime (but this does not tell us a nontrivial factorization of n). This base a is called an Euler witness for n; it is a witness for the compositeness of n. The base a is called an Euler liar for n if the congruence is true while n is composite.
For every composite odd n at least half of all bases
 
are (Euler) witnesses: this contrasts with the Fermat primality test, for which the proportion of witnesses may be much smaller. Therefore, there are no (odd) composite n without lots of witnesses, unlike the case of Carmichael numbers for Fermat's test. 
Here is a psuedo code which are used as guide-line to implement the primility checker function:
Inputs: n, a value to test for primality; k, a parameter that determines the accuracy of the test
Output: composite if n is composite, otherwise probably prime
repeat k times:
   choose a randomly in the range [2,n − 1]
   x ←  
   if x = 0 or   then return composite
return probably prime

1.3.	Miller-Rabin 
The Miller–Rabin primality test or Rabin–Miller primality test is a primality test: an algorithm which determines whether a given number is prime, similar to the Fermat primality test and the Solovay–Strassen primality test. Its original version, due to Gary L. Miller, is deterministic, but the determinism relies on the unproven generalized Riemann hypothesis; Michael O. Rabin modified it to obtain an unconditional probabilistic algorithm. 
Just like the Fermat and Solovay–Strassen tests, the Miller–Rabin test relies on an equality or set of equalities that hold true for prime values, then checks whether or not they hold for a number that we want to test for primality.

First, a lemma about square roots of unity in the finite field Z/pZ, where p is prime and p > 2. Certainly 1 and −1 always yield 1 when squared modulo p; call these trivial square roots of 1. There are no nontrivial square roots of 1 modulo p (a special case of the result that, in a field, a polynomial has no more zeroes than its degree). To show this, suppose that x is a square root of 1 modulo p. Then:
 
 
In other words, prime p divides the product (x − 1)(x + 1). By Euclid's lemma it divides one of the factors x − 1 or x + 1, implying that x is congruent to either 1 or −1 modulo p. Now, let n be prime with n > 2. It follows that n − 1 is even and we can write it as 2s·d, where s and d are positive integers (d is odd). For each a in (Z/nZ)*, either.
  	Or 	 
For some 0 ≤ r ≤ s − 1. To show that one of these must be true, recall Fermat's little theorem, that for a prime number n:	  
By the lemma above, if we keep taking square roots of an−1, we will get either 1 or −1. If we get −1 then the second equality holds and it is done. If we never get −1, then when we have taken out every power of 2, we are left with the first equality.
The Miller–Rabin primality test is based on the contrapositive of the above claim. That is, if we can find an a such that
  	and	 
for all 0 ≤ r ≤ s − 1, then n is not prime. We call a a witness for the compositeness of n (sometimes misleadingly called a strong witness, although it is a certain proof of this fact). Otherwise a is called a strong liar, and n is a strong probable prime to base a. The term "strong liar" refers to the case where n is composite but nevertheless the equations hold as they would for a prime.
Every odd composite n has many witnesses a, however, no simple way of generating such an a is known. The solution is to make the test probabilistic: we choose a non-zero a in Z/nZ randomly, and check whether or not it is a witness for the compositeness of n. If n is composite, most of the choices for a will be witnesses, and the test will detect n as composite with high probability. There is, nevertheless, a small chance that we are unlucky and hit an a which is a strong liar for n. We may reduce the probability of such error by repeating the test for several independently chosen a.
For testing large numbers, it is common to choose random bases a, as, a priori, we don't know the distribution of witnesses and liars among the numbers 1, 2, ..., n − 1. In particular, Arnault [3] gave a 397-digit composite number for which all bases a less than 307 are strong liars. As expected this number was reported to be prime by the Maple isprime() function, which implemented the Miller–Rabin test by checking the specific bases 2,3,5,7, and 11. However, selection of a few specific small bases can guarantee identification of composites for n less than some maximum determined by said bases. This maximum is generally quite large compared to the bases. As random bases lack such determinism for small n, specific bases are better in some circumstances.
Input: n > 3, an odd integer to be tested for primality;
Input: k, a parameter that determines the accuracy of the test
Output: composite if n is composite, otherwise probably prime
write n − 1 as 2s·d with d odd by factoring powers of 2 from n − 1
WitnessLoop: repeat k times:
   pick a random integer a in the range [2, n − 2]
   x ← ad mod n
   if x = 1 or x = n − 1 then do next WitnessLoop
   repeat s − 1 times:
      x ← x2 mod n
      if x = 1 then return composite
      if x = n − 1 then do next WitnessLoop
   return composite
return probably prime

In the next topic you will see some screenshots from android application which used to examine the runnig time and quality of execution of these three algorithms in mobile environment, but honestly android part of this project is just a shell for implemented algorithms in java.  
2.	Android App Screen Shots

                                



 
3.	Results
As a result of this project I wanted to show the running time of this application and generation time of keys on Android device Samsung Galaxy Note 3 SM-N900 and in DELL PC with Core i7-4770 CPU and 8 GB Ram DDR3. Detailed results are included as attachments but there are some other indices and graphs.
3.1.	Running on PC
 
 
In addition, we have two other interesting results:
Average needed time to increase key size by one bit or average effort to generate one bit of key:
Using Miller-Rabin: 2.266004423 ms
Using Solovay-Strassen: 2.271048499 ms
3.2.	Running on Android Phone
 
Figure 7 - Time to generate prime number using Miller-Rabin
 
Figure 8 - Time to generate prime number using Solovay-Strassen
In addition, we have two other interesting results:
Average needed time to increase key size by one bit or average effort to generate one bit of key:
Using Miller-Rabin: 4.105083925 ms
Using Solovay-Strassen: 8.989686086 ms

4.	Future Works
Finally, the future work arising from this project can be categorized as follows:
•	Port NTL library for running on Android and iOS
•	More optimized implementation of Mersenne Twister Algorithm
•	More optimized implementation of Solovay-Strassen Algorithm
•	More optimized implementation of Miller-Rabin Algorithm
•	Implement with fully support from multi-thread architecture
•	Replacement of Random Number Generator and Primility Test algorithms by other ones which are different in required Calculation power or memory
Appendix A
How to run the application:
JDK 7 is installed
You can check if you have JDK or not.
 
Figure 9 - it shows java is installed
Simple Modification  
Figure 10 - Go to the folder of source files
You can open Main.java (the tester application) and set the value of minimum key and maximum key length and see the results:





Compile the source codes
 
Figure 12 - compile .java files

 
Figure 13 - Running the tester application
