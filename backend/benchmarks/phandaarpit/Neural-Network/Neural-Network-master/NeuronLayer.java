import java.util.ArrayList;


/**
 * @author Arpit Phanda
 */
public class NeuronLayer {
	
	private int numberOfNeurons;
	
	public int getNumberOfNeurons() {
		return numberOfNeurons;
	}

	public Object getNeuronVector() {
		return new Object();
	}

	
	public NeuronLayer(int numberOfNeurons, int numberOfNeuronsInNextLayer) {
		
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



/**
  @Override
	public String toString()
	{
		String str = "";
		for(int i=0; i<this.numberOfNeurons; i++)
		{
			System.out.println("Neuron "+i);
			System.out.println("Weights: "+this.neuronVector.get(i).getWeightsForOutputs());
			System.out.println("Delta weights: "+this.neuronVector.get(i).getDeltaWeights());
			System.out.println("Input: "+this.neuronVector.get(i).getInput());
			System.out.println("Output: "+this.neuronVector.get(i).getOutputVal());
			System.out.println("------------------------------------------");
		}
		
		return str;
	}
 * **/
