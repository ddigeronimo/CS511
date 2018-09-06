/*
 * CS 511 HW 1
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

import java.util.List;
import java.util.ArrayList;

public class AssignmentOne extends PrimeFinder {

    // Create a PrimeFinder
    // PrimeFinder pf = new PrimeFinder();

    // Call PrimeFinder methods
    // public static List<Integer> lprimes(List<Integer[]> intervals) {
    //     // TODO
    // }

    // Main method -> deal with arguments, create list of intervals, pass them to lprimes
    public static void main(String[] args) {
        // Parse command line args
        Integer val1;
        Integer val2;
        Integer[] interval = new Integer[2];
        List<Integer[]> listOfIntervals = new ArrayList<Integer[]>();
        for (int i = 0; i < args.length-1; i++) {
            val1 = Integer.parseInt(args[i]);
            val2 = Integer.parseInt(args[i+1]);
            interval[0] = val1;
            interval[1] = val2;
            listOfIntervals.add(interval);
        }
    }
}

