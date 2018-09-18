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
	id = id; // Will this cause a problem because of the shared name?
	routine = new List<Exercise>(); //?
    }

    public void addExercise(Exercise e) {

    }

    public static Client generateRandom(int id, Map<WeightPlateSize,int> noOfWeightPlates) {

    }

    // Does this need to be included? See pg 4 of the HW
    public static Client generateRandom(int id) {

    }
}
