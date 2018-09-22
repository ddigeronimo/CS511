/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.EnumMap;
import java.util.concurrent;

public class Gym {

    private static final int GYM_SIZE = 30;

    private static final int GYM_REGISTERED_CLIENTS = 10000;

    private Map<WeightPlateSize,int> noOfWeightPlates = new EnumMap<WeightPlateSize,int>(WeightPlateSize.class);
    noOfWeightPlates.put(WeightPlateSize.SMALL_3KG, 110);
    noOfWeightPlates.put(WeightPlateSize.MEDIUM_5KG, 90);
    noOfWeightPlates.put(WeightPlateSize.LARGE_10KG, 75);

    // For generating fresh client ids
    private Set<Integer> clients; 

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
    
    public void run() {
	
    }

    // See big paragraph on pg. 4
}
