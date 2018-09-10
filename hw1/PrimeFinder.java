package hw1;

/*
 * CS 511 HW 1
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

import java.util.List;
import java.util.ArrayList;

public class PrimeFinder implements Runnable {

    private Integer start;
    private Integer end;
    private List<Integer> primes;

    // Constructs a PrimeFinder
    PrimeFinder(Integer startNum, Integer endNum) {
    	primes = new ArrayList<Integer>();
        start = startNum;
        end = endNum;
    }

    // Returns the value of the attribute primes
    public List<Integer> getPrimesList() {
        return primes;
    }

    // Determines whether its argument is prime or not, based on AKS algorithm
    public Boolean isPrime(int n) {
        if (n == 2) {
            return false;
        }
        if (n == 3) {
            return true;
        }
        if (n%2 == 0) {
            return false;
        }
        if (n%3 == 0) {
            return false;
        }

        int a = 5;
        int b = 2;

        while (a*a <= n) {
            if (n%a == 0) {
                return false;
            }
            a += b;
            b = 6 - b;
        }
        return true;
    }

    // Adds all primes in [this.start, this.end] to the attribute primes
    public void run() {
        //Integer i = start;
        //while(i != end) {
    	for(int i = this.start; i<this.end; ++i){
            if (isPrime(i)) { // Will there be type issues, since isPrime takes an int?
                // Add i to primes
            	primes.add(new Integer(i));
                //i++;
            } //else {
                //i++;
            }
        }
    }


//}
