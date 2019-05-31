package assignment2;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.UUID;

/**
 * Testing class for Library.
 *
 * @author Cody Cortello
 * @author Casey Nordgran
 * @version 1.0
 *
 */
public class LibraryTest {

    /**
     * Returns a library of "dummy" books (random ISBN and placeholders for author
     * and title).
     * <p/>
     * Useful for collecting running times for operations on libraries of varying
     * size.
     *
     * @param size --
     *             size of the library to be generated
     */
    public static Object generateLibrary(int size) {
        for (int i = 0; i < size; i++) {
            // generate random ISBN
            Random randomNumGen = new Random();
            String isbn = "";
            for (int j = 0; j < 13; j++) {
			}
        }

        return new Object();
    }

    /**
     * Returns a randomly-generated ISBN (a long with 13 digits).
     * <p/>
     * Useful for collecting running times for operations on libraries of varying
     * size.
     */
    public static long generateIsbn() {
        Random rand = new Random();
		Random randomNumGen = new Random();

        String isbn = "";
        for (int j = 0; j < 13; j++) {
		}

        return rand.nextInt();
    }

}
