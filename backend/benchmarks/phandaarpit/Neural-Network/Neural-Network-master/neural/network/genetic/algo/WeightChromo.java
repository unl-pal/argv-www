package neural.network.genetic.algo;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by arpit on 11/4/15.
 */
public class WeightChromo implements Comparable{

    private ArrayList<Double> weightArray;
    private double fitness = 0;
    public static int size_WeightArray = -1;

    public WeightChromo(int sizeOfWeightArray)
    {
        if(size_WeightArray==-1)
        {
            size_WeightArray = sizeOfWeightArray;
        }

        weightArray = new ArrayList<Double>(sizeOfWeightArray);
        for(int i=0; i<sizeOfWeightArray; i++)
        {
            weightArray.add(Math.random()-Math.random());
        }
    }

    public WeightChromo()
    {
        weightArray = new ArrayList<Double>(size_WeightArray);
        for(int i=0; i<size_WeightArray; i++)
        {
            weightArray.add(Math.random()-Math.random());
        }
    }

    public ArrayList<Double> getWeightArray()
    {
        return this.weightArray;
    }

    public void setFitness(double error_neural_network)
    {
        if(error_neural_network !=0 ) {
            this.fitness = 1.0 / error_neural_network;
        }
    }

    public double getFitness()
    {
        return fitness;
    }

    public void setGene(int i, Double value) {
        weightArray.set(i,value);

        fitness = 0;
    }

    @Override
    public int compareTo(Object o) {
        WeightChromo chromo = (WeightChromo)o;

        if(this.getFitness()>chromo.getFitness()) {
            return -1;
        }
        else {
            return 1;
        }
    }
}
