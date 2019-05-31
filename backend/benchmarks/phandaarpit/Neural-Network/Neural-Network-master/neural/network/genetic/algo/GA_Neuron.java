package neural.network.genetic.algo;

import java.util.ArrayList;

/**
 * Created by arpit on 11/4/15.
 */
public class GA_Neuron {
    ArrayList<Double> weightsForOutputs;
    int indexInLayer;
    double input;
    double output;
    double error;

    public Object getWeightsForOutputs() {
        return weightsForOutputs;
    }

    public GA_Neuron(int numberOfOutputs, int indexInLayer)
    {
        this.indexInLayer = indexInLayer;
        this.weightsForOutputs = new ArrayList<Double>();

        for(int i=0; i<numberOfOutputs; i++)
        {
        }
    }

    public double getOutputVal()
    {
        return this.output;
    }

    public String getInput() {
        return ""+this.input;
    }

//    public void setError(double d) {
//        this.error = d;
//    }

    public String toString()
    {
        return "Input: "+this.input+" Output: "+this.output+" Error: "+this.error+"\n";
    }

}
