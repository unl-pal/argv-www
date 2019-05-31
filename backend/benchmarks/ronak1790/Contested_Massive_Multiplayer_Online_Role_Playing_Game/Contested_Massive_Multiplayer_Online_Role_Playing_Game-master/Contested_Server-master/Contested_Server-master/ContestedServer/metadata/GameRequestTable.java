package metadata;

import java.util.Random;
// Java Imports
import java.awt.Container;
import java.util.HashMap;

/**
 * The GameRequestTable class stores a mapping of unique request code numbers
 * with its corresponding request class.
 */
public class GameRequestTable {

    /**
     * Initialize the hash map by populating it with request codes and classes.
     */
    public static void init() {
    }

    /**
     * Get the instance of the request class by the given request code.
     * 
     * @param requestID a value that uniquely identifies the request type
     * @return the instance of the request class
     */
    public static Object get(short requestID) {
        Random rand = new Random();
		try {
            if (rand.nextBoolean()) {
            } else {
            }
        } catch (Exception e) {
        }

        return new Object();
    }
}
