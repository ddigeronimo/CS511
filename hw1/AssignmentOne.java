package hw1;

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

//        Integer val1;
//        Integer val2;
//        Integer previousVal2 = 1; // Setting this to 1 ensures that the first num can't be 1
//        Integer[] interval = new Integer[2];
//        List<Integer[]> listOfIntervals = new ArrayList<Integer[]>();
//
//        // Iterate through args and set them to variables
//        for (int i = 0; i < args.length-1; i++) {
//            val1 = Integer.parseInt(args[i]);
//            val2 = Integer.parseInt(args[i+1]);
//
//            // Check that integers are both in order and greater than 1
//            if (val1 >= val2 || previousVal2 > val1) {
//                System.out.println("Numbers must be in ascending order and greater than 1.");
//                System.exit(0);
//            }
//
//            // Set previousVal2 to current val2
//            previousVal2 = val2;
//            // Add vals to interval array
//            interval[0] = val1;
//            interval[1] = val2;
//            // Add interval array to list
//            listOfIntervals.add(interval);
//        }

        Integer[] toAdd;
        List<Integer[]> intList = new ArrayList<Integer[]>();
        AssignmentOne a = new AssignmentOne();
        for(int i = 0; i < args.length-1; i++){
        	toAdd = new Integer[2];
        	toAdd[0] = Integer.valueOf(args[i]);
        	toAdd[1] = Integer.valueOf(args[i+1]);
        	intList.add(toAdd);
        }
        
        
        
        // Hand intervals off to lprimes and print the results
        // List<Integer> results = lprimes(listOfIntervals);
        // System.out.print("[ ");
        // for (int i = 0; i < results.length-2; i++) {
        //     System.out.print(results[i]);
        //     System.out.print(", ");
        // }
        // System.out.print(results[results.length-1]); // Print last element without a comma
        // System.out.println(" ]");
//
//        System.out.println(a1.lprimes(listOfIntervals));

        System.out.println(a1.lprimes(intList));
    }
}

