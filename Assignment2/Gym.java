/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Gym implements Runnable {

    private static final int GYM_SIZE = 30;
    private static final int GYM_REGISTERED_CLIENTS = 10000;

    private Map<WeightPlateSize,Integer> noOfWeightPlates;

    // For generating fresh client ids
    private Set<Integer> clients; 

    // For generating random numbers
    private Random rand;

    // Thread Executor Service
    private ExecutorService executor = Executors.newFixedThreadPool(GYM_SIZE);

    // Semaphores
    // One for access to each plate size
    public Semaphore grabSmall = new Semaphore(1);
    public Semaphore grabMedium = new Semaphore(1);
    public Semaphore grabLarge = new Semaphore(1);
    // One for the amount of each plate size
    public Semaphore amtSmall = new Semaphore(noOfWeightPlates.get(WeightPlateSize.SMALL_3KG));
    public Semaphore amtMedium = new Semaphore(noOfWeightPlates.get(WeightPlateSize.MEDIUM_5KG));
    public Semaphore amtLarge = new Semaphore(noOfWeightPlates.get(WeightPlateSize.LARGE_10KG));
    // One for each machine
    public Semaphore legPress = new Semaphore(5);
    public Semaphore barbell = new Semaphore(5);
    public Semaphore hackSquat = new Semaphore(5);
    public Semaphore legExtension = new Semaphore(5);
    public Semaphore legCurl = new Semaphore(5);
    public Semaphore latPull = new Semaphore(5);
    public Semaphore pecDeck = new Semaphore(5);
    public Semaphore cableCross = new Semaphore(5);
    
    // Gym object, used for initializations and such
    public Gym() {
        // Initialize noOfWeightPlates
        noOfWeightPlates = new HashMap<WeightPlateSize,Integer>();
        noOfWeightPlates.put(WeightPlateSize.SMALL_3KG, 110);
        noOfWeightPlates.put(WeightPlateSize.MEDIUM_5KG, 90);
        noOfWeightPlates.put(WeightPlateSize.LARGE_10KG, 75);

        // Initialize rand
        rand = new Random();

        // Initialize clients --> used a HashSet bc you can't just use Set
        clients = new HashSet<Integer>();

    }

    // TODO: This whole method 
    public void run() {
	
    }

    // See big paragraph on pg. 4
}
