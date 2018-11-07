/*
 * CS 511 HW 2
 * Dylan DiGeronimo and Ryan Locke
 * I pledge my honor that I have abided by the Stevens Honor System
 */

package Assignment2;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.List;

public enum WeightPlateSize {
    SMALL_3KG,
    MEDIUM_5KG,
    LARGE_10KG;

    // Method for choosing a random apparatus type
    // NOTE: This method was found on Stack Overflow
    private static final List<WeightPlateSize> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random rand = new Random();

    public WeightPlateSize randomWeightPlateSize() {
        return VALUES.get(rand.nextInt(SIZE));
    }
}
