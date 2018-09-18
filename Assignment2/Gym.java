/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

public class Gym {

    private static final int GYM_SIZE = 30;

    private static final in GYM_REGISTERED_CLIENTS = 10000;

    private Map<WeightPlateSize,int> noOfWeightPlates;

    private Set<Integer> clients; // For generating fresh client ids

    private ExecutorService executor;

    // TODO: More Semaphores?
    Semaphore numApparatus = new Semaphore(0); // TODO: Should this be 0? 
   
    public void run() {

    }


}
