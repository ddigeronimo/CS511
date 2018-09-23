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

public enum ApparatusType {
    LEGPRESSMACHINE,
    BARBELL,
    HACKSQUATMACHINE,
    LEGEXTENSIONMACHINE,
    LEGCURLMACHINE,
    LATPULLDOWNMACHINE,
    PECDECKMACHINE,
    CABLECROSSOVERMACHINE;

    // Method for choosing a random apparatus type
    // NOTE: This method was found on Stack Overflow
    private static List<ApparatusType> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static int SIZE = VALUES.size();
    private static Random rand = new Random();

    public static ApparatusType randomApparatus() {
        return VALUES.get(rand.nextInt(SIZE));
    }
}



