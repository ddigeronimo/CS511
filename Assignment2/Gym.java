/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package CS511.Assignment2;

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

    // Represent the weight plate amounts with a map
    private static Map<WeightPlateSize,Integer> noOfWeightPlates;

    // Getter for weightPlates
    public static Map<WeightPlateSize,Integer> numPlateGetter() {
        return noOfWeightPlates;
    }

    // Organize apparatus semaphores using a map, meaning we won't have to use big sets of if/else if statements
    private Map<ApparatusType, Semaphore> apparatusPerm;

    // For generating fresh client ids
    private Set<Client> clients; 

    // For generating random numbers
    private Random rand;

    // Thread Executor Service
    private ExecutorService executor;

    // Semaphores
    // One for access to each plate size
    public Semaphore grabWeight = new Semaphore(1);
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
        // Initialize and fill noOfWeightPlates
        noOfWeightPlates = new HashMap<WeightPlateSize,Integer>();
		noOfWeightPlates.put(WeightPlateSize.SMALL_3KG, 110);
        noOfWeightPlates.put(WeightPlateSize.MEDIUM_5KG, 90);
        noOfWeightPlates.put(WeightPlateSize.LARGE_10KG, 75);

        // Initialize and fill apparatusPerm
        apparatusPerm = new HashMap<ApparatusType, Semaphore>();
        apparatusPerm.put(ApparatusType.LEGPRESSMACHINE, legPress);
        apparatusPerm.put(ApparatusType.BARBELL, barbell);
        apparatusPerm.put(ApparatusType.HACKSQUATMACHINE, hackSquat);
        apparatusPerm.put(ApparatusType.LEGEXTENSIONMACHINE, legExtension);
        apparatusPerm.put(ApparatusType.LEGCURLMACHINE, legCurl);
        apparatusPerm.put(ApparatusType.LATPULLDOWNMACHINE, latPull);
        apparatusPerm.put(ApparatusType.PECDECKMACHINE, pecDeck);
        apparatusPerm.put(ApparatusType.CABLECROSSOVERMACHINE, cableCross);

        // Initialize rand
        rand = new Random();

        // Initialize and fill clients --> used a HashSet bc you can't just use Set
        clients = new HashSet<Client>();
        for (int i = 1; i < GYM_REGISTERED_CLIENTS; i++) {
            clients.add(Client.generateRandom(i));
        }

        // Initialize executor
        executor = Executors.newFixedThreadPool(GYM_SIZE); 
    }

    // Run method, simulates gym
    /* We use a run method inside of a run method for the following reason:
    *  The second run method is executed by the executor, creating a new thread  
    */
    public void run() {
        for (Client c: clients) {
            final Client current = c;
            executor.execute(new Runnable() {
                public void run() {
                    for (Exercise ex : current.routineGetter()) {
                    	//Map<WeightPlateSize,Integer> amtOfWeights = ex.weightGetter();
                        try {
                            // Get permissions to start
                            grabWeight.acquire();
                            // Get permission to use machine
                            apparatusPerm.get(ex.apparatusGetter()).acquire();
                            // Get permission to get weights, first get weight amts
                            
                            // Grab small weights
                            amtSmall.acquire(ex.weightGetter().get(WeightPlateSize.SMALL_3KG));
                            // Grab medium weights
                            amtMedium.acquire(ex.weightGetter().get(WeightPlateSize.MEDIUM_5KG));
                            // Grab large weights
                            amtLarge.acquire(ex.weightGetter().get(WeightPlateSize.LARGE_10KG));
                            // Print acquire statement

                            // Stop grabbing weights
                            grabWeight.release();
                            // Print exercise statement

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // Put back weights
                        // Small Weights
                        amtSmall.release(ex.weightGetter().get(WeightPlateSize.SMALL_3KG));
                        // Medium weights
                        amtMedium.release(ex.weightGetter().get(WeightPlateSize.MEDIUM_5KG));
                        // Large weights
                        amtLarge.release(ex.weightGetter().get(WeightPlateSize.LARGE_10KG));
                        // Release machine
                        apparatusPerm.get(ex.apparatusGetter()).release();
                        // Print release statement
                        
                    }
                }
            });
        }
        // Kill executor
        executor.shutdown();
    }

}
