import java.util.ArrayList;

public class Neuron {
	
	private final double learningRate = 0.15;
	private final double momentum = 0.9;
	
	private double output;
	private double input;
	private int indexInLayer;
	private double gradient;

	private ArrayList<Double> weightsForOutputs;
	private ArrayList<Double> changeInWeights;

	private double error;
	
	
	public double getGradient() {
		return gradient;
	}
	
	public Object getWeightsForOutputs() {
		return weightsForOutputs;
	}
	
	public Object getDeltaWeights() {
		return changeInWeights;
	}
	
	public Neuron(int numberOfOutputs, int indexInLayer)
	{
		this.indexInLayer = indexInLayer;
		
		this.weightsForOutputs = new ArrayList<Double>();
		this.changeInWeights = new ArrayList<Double>();
		
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
	
	public double getError()
	{
		return this.error;
	}
	
	public String toString()
	{
		return "Input: "+this.input+" Output: "+this.output+" Error: "+this.error+"\n";
	}
	
}
