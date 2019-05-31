package ufrgs.maslab.abstractsimulator.algorithms.model.factorgraph;

import java.util.Random;
import java.util.Set;

public class FactorNode {
	
	private int id;
	
	public FactorNode(int id)
	{
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}

	public int hashCode() {
        Random rand = new Random();
		return rand.nextInt();
    }

	public Object getNeighbour() {
		// TODO Auto-generated method stub
		return new Object();
	}

	public String stringOfNeighbour() {
		// TODO Auto-generated method stub
		return "";
	}


}
