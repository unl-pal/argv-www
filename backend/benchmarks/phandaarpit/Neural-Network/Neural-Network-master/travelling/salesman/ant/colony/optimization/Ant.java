package travelling.salesman.ant.colony.optimization;

import java.util.Random;
import java.util.ArrayList;

/**
 * Created by arpit on 17/4/15.
 */
public class Ant {
    public ArrayList<Integer> tour;
    public ArrayList<Boolean> visitedOrNot;
    public int sizeOfTheTour;

    public Ant(int sizeOfTheTour)
    {
        this.sizeOfTheTour= sizeOfTheTour;
        tour = new ArrayList<Integer>(sizeOfTheTour);
        visitedOrNot = new ArrayList<Boolean>(sizeOfTheTour);
    }

    public void visitTown(int town, int currentIndex) {
    }

    public boolean visited(int i) {
        Random rand = new Random();
		return rand.nextBoolean();
    }

    public void clear() {
        for (int i = 0; i < sizeOfTheTour; i++) {
		}
    }
}
