/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mbooali.primenumbergeneration.utils;

/**
 *
 * @author mabouali
 */
/**
 ** Java Program to Implement SolovayStrassen Primality Test Algorithm
 **/
 
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Random;
 
/** Class SolovayStrassen **/
public class SolovayStrassen
{
    /** Function to calculate jacobi (a/b) **/
    public long Jacobi(BigInteger a, BigInteger b)
    {
        if (b.signum() != 1 || b.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            return 0;
        long j = 1L;
        if (a.compareTo(BigInteger.ZERO) == -1)
        {
            a = a.negate();
            if (b.mod(BigInteger.valueOf(4)) == BigInteger.valueOf(3))
                j = -j;
        }
        while (!a.equals(BigInteger.ZERO))
        {
            while (a.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            {
                a = a.shiftRight(1);
                BigInteger b_mod_8 = b.mod(BigInteger.valueOf(8));
                
                if (b_mod_8.equals(BigInteger.valueOf(3)) ||b_mod_8.equals(BigInteger.valueOf(5)))
                    j = -j;
            }
 
            BigInteger temp = a;
            a = b;
            b = temp;
 
            BigInteger a_mod_8 = b.mod(BigInteger.valueOf(8));
            
            if (a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && b.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)))
                j = -j;
            
            a = a.mod(b);
        }
        if (b.equals(BigInteger.ONE))
            return j;
        return 0;
    }
    
    public boolean isPrime(BigInteger n)
    {
        return isPrime(n, 50);
    }
    /** Function to check if prime or not **/
    public boolean isPrime(BigInteger n, int iteration)
    {
        /** base case **/
        if (n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE))
            return false;
        /** base case - 2 is prime **/
        if (n.equals(BigInteger.valueOf(2)))
            return true;
        /** an even number other than 2 is composite **/
        if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.valueOf(0)))
            return false;
 
        Random rand = new Random();
        for (int i = 0; i < iteration; i++)
        {
            BigInteger r = (new BigInteger(n.bitLength(), rand)).abs();
            BigInteger a = r.mod(n.subtract(BigInteger.ONE)).add(BigInteger.ONE);
            BigInteger jacobian = (BigInteger.valueOf(Jacobi(a, n)).add(n)).mod(n);
            BigInteger mod = a.modPow((n.subtract(BigInteger.ONE)).shiftRight(1), n);
            if(jacobian.equals(BigInteger.ZERO) || !mod.equals(jacobian) )
                return false;
        }
        return true;        
    }
    /** Function to calculate (a ^ b) % c **/
    public BigInteger modPow(BigInteger a, BigInteger b, BigInteger c)
    {
        BigInteger res = BigInteger.ONE;
        for (BigInteger i = BigInteger.ZERO; i.compareTo(b) == -1 ; i = i.add(BigInteger.ONE))
        {
            res = res.multiply(a);
            res = res.mod(c); 
        }
        return res.mod(c);
    }
    /** Main function **/
    public static void main (String[] args) 
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("SolovayStrassen Primality Algorithm Test\n");
        /** Make an object of SolovayStrassen class **/
        SolovayStrassen ss = new SolovayStrassen();
        /** Accept number **/
        System.out.println("Enter number\n");
        BigInteger num = scan.nextBigInteger();
        /** Accept number of iterations **/
//        System.out.println("\nEnter number of iterations");
//        int k = scan.nextInt();
        /** check if prime **/
        boolean prime = ss.isPrime(num);
        if (prime)
            System.out.println("\n"+ num +" is prime");
        else
            System.out.println("\n"+ num +" is composite");        
    }
}
