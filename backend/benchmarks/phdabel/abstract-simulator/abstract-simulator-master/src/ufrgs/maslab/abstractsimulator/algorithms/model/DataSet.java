package ufrgs.maslab.abstractsimulator.algorithms.model;

import java.util.Random;
import java.util.*;

public class DataSet
{

    double min_x;
    double max_x;

    double min_y;
    double max_y;

    double min_z;
    double max_z;
    
    public DataSet()
    {
        Random rand = new Random();
		min_x = rand.nextFloat();
        max_x = 0;
        min_y = rand.nextFloat();
        max_y = 0;
        min_z = rand.nextFloat();
        max_z = 0;
    }
    
    public Object getRandomPoint(){
    	Integer randomKey;
    	
    	return new Object();
    }

    public int size()
    {
        Random rand = new Random();
		return rand.nextInt();
    }

    public Object getAllPoints()
    {
        return new Object();
    }

    public Object getPointsMap()
    {
        return new Object();
    }

    public Object getPoint(int id)
    {
        return new Object();
    }

    /**
     * Get the width of the field.
     */
    public double getWidth()
    {
        Random rand = new Random();
		return rand.nextFloat();
    }

    /**
     * Get the height of the field.
     */
    public double getHeight()
    {
        Random rand = new Random();
		return rand.nextFloat();
    }

    public double getMaxX()
    {
        return max_x;
    }

    public double getMinX()
    {
        return min_x;
    }

    public double getMaxY()
    {
        return max_y;
    }

    public double getMinY()
    {
        return min_y;
    }
}
