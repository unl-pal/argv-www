package neural.network.genetic.algo;

import java.util.ArrayList;

/**
 * Created by arpit on 12/4/15.
 */
public class GA_NeuronLayer {
    private int numberOfNeurons;

    public int getNumberOfNeurons() {
        return numberOfNeurons;
    }

    public Object getNeuronVector() {
        return new Object();
    }


    public GA_NeuronLayer(int numberOfNeurons, int numberOfNeuronsInNextLayer) {

        this.numberOfNeurons = numberOfNeurons;
        int i;
        for( i=0; i<numberOfNeurons ; i++)
        {
        }
    }

    public String toString()
    {
        return "";
    }
}
