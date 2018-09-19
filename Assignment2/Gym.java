/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.EnumMap;

public class Gym {

    private static final int GYM_SIZE = 30;

    private static final in GYM_REGISTERED_CLIENTS = 10000;

    private Map<WeightPlateSize,int> noOfWeightPlates = new EnumMap<WeightPlateSize,int>(WeightPlateSize.class);
    noOfWeightPlates.put(GFG.SMALL_3KG, 110);
    noOfWeightPlates.put(GFG.MEDIUM_5KG, 90);
    noOfWeightPlates.put(GFG.LARGE_10KG, 75);

    private Set<Integer> clients; // For generating fresh client ids

    private ExecutorService executor;

    // TODO: More Semaphores?
    Semaphore numApparatus = new Semaphore(0); // TODO: Should this be 0? 
   
    public void run() {

    }

    // See big paragraph on pg. 4
}
