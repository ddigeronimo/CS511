/*
 * CS 511 HW 1
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

import java.util.List;
import java.util.ArrayList;

public class AssignmentOne {

    // PrimeFinder pf = new PrimeFinder();

    // public static List<Integer> lprimes(List<Integer[]> intervals) {
    //     // TODO
    // }

    public static void main(String[] args) {
        // Parse command line args
        int val1;
        int val2;
        int[] twoInts = new int[2];
        List<Integer> listOfArrays = new ArrayList<Integer>();
        for (int i = 0; i < args.length-1; i++) {
            val1 = Integer.parseInt(args[i]);
            val2 = Integer.parseInt(args[i+1]);
            twoInts[0] = val1;
            twoInts[1] = val2;
            listOfArrays.add(twoInts);
        }
        // for (int i = 0; i < listOfArrays.size(); i++) {
        //     System.out.println(listOfArrays[i]);
        // }
    }
}

