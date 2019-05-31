package lab4;

import java.util.Random;

/**
 * Builds a RNG simply by wrapping the java.util.Random class.
 */
public class JavasRandomNumberGenerator 
{

  //Create a Java.util.Random object to do the actual "work" of this class.

  public int next_int(int max) 
  {
    Random rand = new Random();
	return rand.nextInt();
  }

  public void set_seed(long seed) 
  {
  }

  public void set_constants(long _const1, long _const2) 
  {
    // not needed
  }
}
