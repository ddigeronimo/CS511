/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Gym implements Runnable {
	
	private static final int GYM_SIZE = 30;
	private static final int GYM_REGISTERED_CLIENTS = 10000;
	
	private Map<WeightPlateSize,Integer> noOfWeightPlates;
	
	// Remaining number of weight plates
	static Map<WeightPlateSize,Integer> platesRemaining; 
	// Because of issues with interacting things being static/not static, we need a pointer to noOfWeightPlates
	private static Map<WeightPlateSize,Integer> platesPointer; 
    // Organize apparatus semaphores using a map, meaning we won't have to use big sets of if/else if statements
	static Map<ApparatusType, Semaphore> apparatusPerms; 
	
	private Set<Integer> clients;
	private ExecutorService executor;
	private Random rand;
	
	// Semaphores
	// One for the amount of each plate size
	final static Semaphore amtSmall= new Semaphore(110);
	final static Semaphore amtMedium= new Semaphore(90);
	final static Semaphore amtLarge= new Semaphore(75);
	// One for access to each plate size
	final static Semaphore grabSmall= new Semaphore(1);
	final static Semaphore grabMedium= new Semaphore(1);
	final static Semaphore grabLarge= new Semaphore(1);
	// One for each machine, such that there are 5 of each
	final static Semaphore legPress = new Semaphore(5);
	final static Semaphore barbell = new Semaphore(5);
	final static Semaphore hackSquat = new Semaphore(5);
	final static Semaphore legExtension = new Semaphore(5);
	final static Semaphore legCurl = new Semaphore(5);
	final static Semaphore latPull = new Semaphore(5);
	final static Semaphore pecDeck = new Semaphore(5);
	final static Semaphore cableCross = new Semaphore(5);
	
	// Generates a ID to use when creating a new client
	private int IDGen(int max) {
		int id = 0;
		 do {
			id = rand.nextInt(GYM_REGISTERED_CLIENTS) + 1;
		} while (clients.contains(id));
		return id;
	}
	
	// Getter for noOfWeightPlates, has to use a pointer because static
	public static Map<WeightPlateSize, Integer> getNoOfWeightPlates(){
		return platesPointer;
	}
	
	// Gym object, used for initializations and such
	public Gym() {
		// Initialize and fill platesRemaining
		platesRemaining = new HashMap<WeightPlateSize, Integer>();
		platesRemaining.put(WeightPlateSize.LARGE_10KG, 75);
		platesRemaining.put(WeightPlateSize.MEDIUM_5KG, 90);
		platesRemaining.put(WeightPlateSize.SMALL_3KG, 110);
		
		// Initialize and fill noOfWeightPlates
		noOfWeightPlates = new HashMap<WeightPlateSize, Integer>();
		noOfWeightPlates.put(WeightPlateSize.LARGE_10KG, 75);
		noOfWeightPlates.put(WeightPlateSize.MEDIUM_5KG, 90);
		noOfWeightPlates.put(WeightPlateSize.SMALL_3KG, 110);
		
		// Set platesPointer equal to noOfWeightPlates
		platesPointer = noOfWeightPlates;
		
		// Initialize and fill apparatusPerms
		apparatusPerms = new HashMap<ApparatusType, Semaphore>();
		apparatusPerms.put(ApparatusType.LEGPRESSMACHINE, legPress);
		apparatusPerms.put(ApparatusType.BARBELL, barbell);
		apparatusPerms.put(ApparatusType.HACKSQUATMACHINE, hackSquat);
		apparatusPerms.put(ApparatusType.LEGEXTENSIONMACHINE, legExtension);
		apparatusPerms.put(ApparatusType.LEGCURLMACHINE, legCurl);
		apparatusPerms.put(ApparatusType.LATPULLDOWNMACHINE, latPull);
		apparatusPerms.put(ApparatusType.PECDECKMACHINE, pecDeck);
		apparatusPerms.put(ApparatusType.CABLECROSSOVERMACHINE, cableCross);

		// Set clients
		clients = new HashSet<Integer>();
		
		// Set rand
		rand = new Random();
	}
	
	// Executable run method
	public void run(){
		// Create thread pool that does not exceed gym capacity
		Client client;
		executor = Executors.newFixedThreadPool(GYM_SIZE);		
		// Randomly generate all clients, then execute them
		// That comment sounded really bad oops
		for(int i = 0; i < GYM_REGISTERED_CLIENTS; i++) {
			client = Client.generateRandom(IDGen(GYM_REGISTERED_CLIENTS));
			executor.execute(client);
		}
		// End executor service
		executor.shutdown();
	}
	
}