import java.util.ArrayList;

/**
 * Created by arpit on 27/3/15.
 */
public class RBFNeuron {

    private double betaVal;
    private ArrayList<Double> centroid;
    private ArrayList<Double> weightVector;
    private double outputVal;

    RBFNeuron(int numberOfClasses) {
        weightVector = new ArrayList<Double>();
        for (int i = 0; i < numberOfClasses; i++)
        {
            weightVector.add(Math.random() - Math.random());
        }

    }

//    public ArrayList<Double> getDeltaWeights() {
//        return changeInWeights;
//    }

    public double getBetaVal()
    {
        return betaVal;
    }

    public void setBetaVal(ArrayList<ArrayList<Double>> coordinateSet)
    {
        double sum = 0;
        double sigma;
        double temp;

        for(ArrayList<Double> pt : coordinateSet)
        {
            sum = sum + getDistance(pt);
        }

        sigma =  sum/(coordinateSet.size());
        temp = Math.pow(sigma,-1*2);
        this.betaVal = 0.5*temp;

    }

    public ArrayList<Double> getMean()
    {
        return centroid;
    }

    public void setMean(ArrayList<Double> centroid)
    {
        this.centroid = centroid;
    }

    public double rbfActivation(ArrayList<Double> pointOfConcern)
    {
        double product = -1*betaVal*Math.pow(getDistance(pointOfConcern),2);
        this.outputVal = Math.exp(product);
        return outputVal;
    }

    public double getDistance(ArrayList<Double> point)
    {
        double distance = Math.sqrt(Math.pow(point.get(0)-centroid.get(0),2)+Math.pow(point.get(1)-centroid.get(1),2));
        return  distance;
    }

    public void setOutputVal(double val)
    {
        this.outputVal = val;
    }

    public double getOutputVal()
    {
        return this.outputVal;
    }

    public ArrayList<Double> getWeightVector()
    {
        return weightVector;
    }

}
