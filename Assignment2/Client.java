/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Client implements Runnable {
	
	private int id;
	private List<Exercise> routine;
	
	// Constructor for client
	public Client(int id){
		this.id = id;
		routine = new ArrayList<Exercise>();
	}

	// Adds an exercise e onto the list routine
	public void addExercise(Exercise e){
		this.routine.add(e);
	}

	// Generate a random client using a given id
	public static Client generateRandom(int id) {
		// Instantiate client
		Client c = new Client(id);
		// Generate a random number of exercises
		Random rand = new Random();
		// Since the number must be within 15 and 20, we choose a random number between 0 and 5 and add it to 15
        // This gives us the possibility of getting 15, 16, 17, 18, 9, and 20 
		int numberOfExercises = rand.nextInt(5) + 15;
		for (int i = 0; i < numberOfExercises; i++) {
			c.addExercise(Exercise.generateRandom(Gym.getNoOfWeightPlates()));
		}
		return c;
	}

	
	private void workout(Exercise ex) {
		Map<WeightPlateSize, Integer> weights = ex.weightGetter();
		try {
			// Get and store the amount of each weight needed
			int smallNeeded = weights.get(WeightPlateSize.SMALL_3KG);
			int mediumNeeded = weights.get(WeightPlateSize.MEDIUM_5KG);
			int largeNeeded = weights.get(WeightPlateSize.LARGE_10KG);
			// Create an array representing the amount remaining of each plate 
			// [remaining small, remaining medium, remaining large]
			int remaingWeights[] = new int[3];
			// Check to see if the proper number of plates are present 
			while(true){
				Gym.grabSmall.acquire();
				Gym.grabMedium.acquire();
				Gym.grabLarge.acquire();
				remaingWeights[0] = Gym.platesRemaining.get(WeightPlateSize.SMALL_3KG);
				remaingWeights[1] = Gym.platesRemaining.get(WeightPlateSize.MEDIUM_5KG);
				remaingWeights[2] = Gym.platesRemaining.get(WeightPlateSize.LARGE_10KG);
				// If we have more of each weight than we need, we are in the clear and can break out of the loop
				if (smallNeeded <= remaingWeights[0] && mediumNeeded <= remaingWeights[1] && largeNeeded <= remaingWeights[2]) {
					break;
				}
				// If we need more of any weight than we have, we release the semaphore and try again
				Gym.grabSmall.release();
				Gym.grabMedium.release();
				Gym.grabLarge.release();
			}
			// Now that we know we have enough weights, we acquire and update Gym.platesRemaining
			try {
				for(int i = 0; i < largeNeeded; i++) {
					Gym.amtLarge.acquire();
				}
				Gym.platesRemaining.put(WeightPlateSize.LARGE_10KG, remaingWeights[2] - largeNeeded);
				Gym.grabLarge.release();
				
				for(int i = 0; i < mediumNeeded; i++) {
					Gym.amtMedium.acquire();
				}
				Gym.platesRemaining.put(WeightPlateSize.MEDIUM_5KG, remaingWeights[1] - mediumNeeded);
				Gym.grabMedium.release();
								
				
				for(int i = 0; i < smallNeeded; i++) {
					Gym.amtSmall.acquire();
				}
				Gym.platesRemaining.put(WeightPlateSize.SMALL_3KG, remaingWeights[0] - smallNeeded);
				Gym.grabSmall.release(); 
				
				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			// Client uses the machine
			System.out.println("Client " + id + " using " + ex.apparatusGetter() + " for " + ex.durationGetter() + " minutes.");
			Thread.sleep(ex.durationGetter());
			System.out.println("Client " + id + " done with " + ex.apparatusGetter() + ".");

			// Client is done, they put back the plates
			Gym.grabSmall.acquire();
			int numRemain = Gym.platesRemaining.get(WeightPlateSize.SMALL_3KG);
			for(int i = 0; i < smallNeeded; i++) {
				Gym.amtSmall.release();
			}
			Gym.platesRemaining.put(WeightPlateSize.SMALL_3KG, numRemain + smallNeeded);

			Gym.grabSmall.release();

			Gym.grabMedium.acquire();
			numRemain = Gym.platesRemaining.get(WeightPlateSize.MEDIUM_5KG); 
			for(int i = 0; i < mediumNeeded; i++) {
				Gym.amtMedium.release();	
			}
			Gym.platesRemaining.put(WeightPlateSize.MEDIUM_5KG, numRemain + mediumNeeded);

			Gym.grabMedium.release();

			Gym.grabLarge.acquire();
			numRemain = Gym.platesRemaining.get(WeightPlateSize.LARGE_10KG);
			for(int i = 0; i < largeNeeded; i++) {
				Gym.amtLarge.release();
			}
			Gym.platesRemaining.put(WeightPlateSize.LARGE_10KG, numRemain + largeNeeded);
			Gym.grabLarge.release();

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public void run() {
		for(Exercise ex : routine){
			// Determine which apparatus is being used for each semaphore
			Semaphore ap = Gym.apparatusPerms.get(ex.apparatusGetter());
			try {
				ap.acquire();
				workout(ex);
				ap.release();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
