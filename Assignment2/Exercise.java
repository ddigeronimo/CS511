/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Exercise {

    private ApparatusType at;
    private Map<WeightPlateSize,Integer> weight; // Amount of how many of each weight you're using
    private int duration;
    private static Random rand = new Random();
       
    public Exercise(ApparatusType at, Map<WeightPlateSize,Integer> weight, int duration) {
	    this.at = at;
	    this.weight = weight;
	    this.duration = duration;
    }

    public static Exercise generateRandom(Map<WeightPlateSize,Integer> weight) {
        // First, generate a random apparatus type
        ApparatusType a = ApparatusType.randomApparatus();
        // Then, generate a random duration, between 1 and 10 (+1 ensures no 0, but max must be one less than desired)
        int randDuration = rand.nextInt(9) + 1;
        // Finally, return a new exercise with these values and weight
        Exercise randExercise = new Exercise(a, weight, randDuration);
        return randExercise;
    }

}
