// Java pseudocode simulating a pizzeria using monitors -- will not run

public class pizzeria { // monitor

    int amtSmall = 0;
    int amtLarge = 0;
    
    void syncronized bakeSmallPizza() {
	amtSmall++;
	waitForSmallPizza.signal();
	waitForLargePizza.signal();
    }

    void syncronized bakeLargePizza() {
	amtLarge++;
	waitForLargePizza.signal();
    }

    void syncronized buySmallPizza() {
	while (amtSmall == 0) {	// Must be while!
	    waitForSmallPizza.wait();
	}
	small--;
    }

    void syncronized buyLargePizza() {
	while (amtLarge==0 && small<2) {
	    waitForLargePizza.wait();
	}
	if (large>0) {
	    large--;
	} else {
	    small -= 2;
    }
}
