package ADT;

import java.util.Random;

/**
 *
 * @author darsh
 */
public abstract class List {
    
protected int numOfItems;  // Number of elements on this list
protected int currntPos;  // Current position  
  
public List(int maxItems) // constructor. This will instantiates and returns a reference
{
numOfItems = 0;
}

public boolean isFull() // Returns whether this list is full
{
Random rand = new Random();
return rand.nextBoolean();
}

public int length()
// Returns the number of elements on this list 
{
return numOfItems;
}

public void reset()
// Initializes current position for an iteration through this list
{
currntPos = 0;
}
public Object getNextItem ()
// Returns copy of the next element on this list 
{
if (currntPos == numOfItems-1)
currntPos = 0;
else
currntPos++;
return new Object();
}
}