/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.List;
import java.util.Random;

public class Client {

    private int id;
    private List<Exercise> routine;

    public Client(int id) {
	    this.id = id; 
	    this.routine = new List<Exercise>(); 
    }

    // Adds an exercise e onto the list routine
    public void addExercise(Exercise e) {
    	this.routine.add(e);
    }

    // Generate a random client using a given id and amount of weight plates
    public static Client generateRandom(int id, Map<WeightPlateSize,Integer> noOfWeightPlates) {
        // Instantiate client
        Client c = new Client(id);
        // Generate a random number of exercises
        Random rand = new Random();
        // Since the number must be within 15 and 20, we choose a random number between 0 and 5 and add it to 15
        // This gives us the possibility of getting 15, 16, 17, 18, 9, and 20 
        int numberOfExercises = rand.nextInt(5) + 15;
        for (int i = 0; i < numberOfExercises; i++) {
            c.addExercise(Exercise.generateRandom(noOfWeightPlates));
        } 
        return c;
    }

    // If only ID is supplied, call gernerateRandom method using numPlateGetter
    public static Client generateRandom(int id) {
        return generateRandom(id, Gym.numPlateGetter());
    }
}
