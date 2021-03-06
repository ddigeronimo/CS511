/*
 * CS 511 HW 1
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

import java.util.List;
import java.util.ArrayList;

public class AssignmentOne {

    // Use PrimeFinders to generate the list of primes, uses threads
    public static List<Integer> lprimes(List<Integer[]> intervals) {

        // Create a list of primes
        List<Integer> primes = new ArrayList<Integer>();

        // Create a list of threads, one per interval
        List<Thread> threads = new ArrayList<Thread>(intervals.size());

        // Create a list of PrimeFinder objects, also one per interval
        List<PrimeFinder> primeFinders = new ArrayList<PrimeFinder>(intervals.size());

        // Iterate through intervals, creating a new PrimeFinder and Thread for each
        for (int i = 0; i < intervals.size(); i++) {
            primeFinders.add(new PrimeFinder(intervals.get(i)[0], intervals.get(i)[1]));
            threads.add(new Thread(primeFinders.get(i)));
        }

        // Start threads
        for (int i = 0; i < threads.size(); i++) {
            threads.get(i).start();
        }

        // Handle threads -> joining and exceptions
        for (int i = 0; i < threads.size(); i++) {
            try {
            	threads.get(i).join();
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            } 
        }

        // Add primes to list
        for (int i = 0; i < primeFinders.size(); i++) {
            primes.addAll(primeFinders.get(i).getPrimesList());
        }

        // Return list of primes
        return primes;
    }

    // Main method -> deal with arguments, create list of intervals, pass them to lprimes
    public static void main(String[] args) {

        // Create an AssignmentOne object
        AssignmentOne a1 = new AssignmentOne();

        // Ensure an even amount of numbers are supplied
        if (args.length%2 != 0) {
            System.out.println("You must supply an even amount of numbers.");
            System.exit(0);
        }

        // Create interval array and list of intervals
        Integer[] interval;
        List<Integer[]> intervals = new ArrayList<Integer[]>();

        // Iterate through args and set them to variables
        Integer val1, val2;
        Integer prevVal2 = 1;
        for(int i = 0; i < args.length-1; i++){
            interval = new Integer[2];
            val1 = Integer.valueOf(args[i]);
            val2 = Integer.valueOf(args[i+1]);
            if (val1 <= val2 || prevVal2 <= val1) {
                interval[0] = val1;
                interval[1] = val2;
                intervals.add(interval);
                prevVal2 = val2;
            } else {
                System.out.println("Values must be greater than 1 and in ascending numerical order.");
                System.exit(0);
            }
        }
        
        // Hand intervals off to lprimes and print the results
        System.out.println(a1.lprimes(intervals));
    }
}

