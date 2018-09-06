/*
 * CS 511 HW 1
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

import java.util.List;
import java.util.ArrayList;

public class AssignmentOne {

    // Create a PrimeFinder -> Probably don't need this, don't delete until sure
    // PrimeFinder pf = new PrimeFinder();

    // Call PrimeFinder methods to generate the list of primes
    // public static List<Integer> lprimes(List<Integer[]> intervals) {
    //     // TODO
    // }

    // Main method -> deal with arguments, create list of intervals, pass them to lprimes
    public static void main(String[] args) {
        // Parse command line args
        if (args.length%2 != 0) {
            System.out.println("You must supply an even amount of numbers.");
            System.exit(0);
        }
        Integer val1;
        Integer val2;
        Integer previousVal2 = 1;
        Integer[] interval = new Integer[2];
        List<Integer[]> listOfIntervals = new ArrayList<Integer[]>();
        for (int i = 0; i < args.length; i+=2) {
            val1 = Integer.parseInt(args[i]);
            val2 = Integer.parseInt(args[i+1]);
            if (val1 >= val2 || previousVal2 >= val1) {
                System.out.println("Numbers must be in ascending order and greater than 1.");
                System.exit(0);
            }
            previousVal2 = val2;
            interval[0] = val1;
            interval[1] = val2;
            listOfIntervals.add(interval);
        }
    }
}

