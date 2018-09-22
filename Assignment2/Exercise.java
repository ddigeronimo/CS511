/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.Map;
import java.util.HashMap;

public class Exercise {

    private ApparatusType at;
    private Map<WeightPlateSize,Integer> weight; // Amount of how many of each weight you're using
    private int duration;
       
    public Exercise(ApparatusType at, Map<WeightPlateSize,Integer> weight, int duration) {
	    this.at = at;
	    this.weight = weight;
	    this.duration = duration;
	    // Is this right?
    }

    public static Exercise generateRandom(Map<WeightPlateSize,Integer> weight) {
    }

}
