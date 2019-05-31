package controllers;

import java.util.Random;
import java.util.Map;

/**
 * Provides controllers for this application.
 */
public class Application {

  /**
   * Returns the home page.
   *
   * @return The resulting home page.
   */
  public static Object index() {
    return new Object();
  }

  /**
   * Returns newContact, a simple example of a second page to illustrate navigation.
   * @param id input this thing.
   * @return The newContact.
   */
  public static Object newContact(long id) {
    Map<String, Boolean> telephoneTypeMap;
    return new Object();

  }

  /**
   * Returns a postContact.
   * @return the postContact.
   */
  public static Object postContact() {
    Random rand = new Random();
	if (rand.nextBoolean()) {
      return new Object();
    }
    else {
      return new Object();
    }
  }
}