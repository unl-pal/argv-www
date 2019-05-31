package ufrgs.maslab.abstractsimulator.variables;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

public class FireStation{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2877720837521565204L;

	public FireStation(){
		super();
	}
	
	public void act(int time){
		
		Random rand = new Random();
		//normalization of the input data
		if(rand.nextInt() != 0) {
		}
		
		/**
		 * in the first timestep fire station receives the input data by the messages
		 * from the fire fighters
		 * 
		 * then it mounts the dataset of the instances and from the second timestep it starts
		 * to find the clusters
		 */
		if(time > 1){
		}
	}
	
	
	private final String FILEKMEANS = "kmeans_info";
	
	/**
	 * main of the clustering phase
	 */
	public void clusteringMain(int time){
		
		Random rand = new Random();
		if(time == 2){
        	String header = "";
            header = "x;y;difficulty";
        	ArrayList<Double> weights = new ArrayList<Double>(Arrays.asList(0.15, 0.15, 0.7));
        	//show2DMap(this.gsom.getNeuralNetwork());
			for(int p = 0; p < rand.nextInt(); p++)
			{
				for(int k = 0; k < rand.nextInt(); k++)
				{
				}
			}
			//show3DMap(this.gsom.getNeuralNetwork());
        	
        }
		
	}
	
	/**
	 * internal class to comparate values and to provide ordering of tasks
	 * @author abel
	 *
	 */
	public static class ValueComparator {
	}
	
}
