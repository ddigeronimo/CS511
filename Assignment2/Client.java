/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.List;

public class Client {

    private int id;
    private List<Exercise> routine;

    public Client(int id) {
	this.id = id; 
	this.routine = new List<Exercise>(); //?
    }

    public void addExercise(Exercise e) {
	// Assume it adds e onto routine
    }

    public static Client generateRandom(int id, Map<WeightPlateSize,int> noOfWeightPlates) {

    }

    public static Client generateRandom(int id) {
	Client c = new Client(id);
    }
}
