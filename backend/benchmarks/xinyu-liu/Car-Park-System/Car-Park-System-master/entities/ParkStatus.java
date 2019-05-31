package carparksystem.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/** 
 * This is an entity class storing the 
 * information of park status.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class ParkStatus {
	private int ticketNum = 100;
	private double coinNum = 0;
	private int spaceNum = 30;
	private File parkStatusFile = new File("park_status.txt");
	
	/**
	 * Constructor to initialize a ParkStatus
	 */
	public ParkStatus() {
		if (parkStatusFile.exists()) 
			try {
				//Open the file
				FileReader fr = new FileReader(parkStatusFile);
				BufferedReader br = new BufferedReader(fr);
				ticketNum = Integer.parseInt(br.readLine());
				coinNum = Double.parseDouble(br.readLine());
				spaceNum = Integer.parseInt(br.readLine());
				// Close the file
				br.close();
				fr.close();
			} catch (Exception e) {
				System.out.printf("Can't read the file!\n");
				e.printStackTrace();
				System.exit(1);
			}
		else
			synchronize();
	}
	
	/**
	 * This method is used to make the data is displaying on 
	 * the screen is the same as the data storing in the file.
	 */
	private void synchronize() {
		try {
			// Open the file
			FileWriter fw = new FileWriter(parkStatusFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.write(ticketNum + "\r\n");
			pw.write(coinNum + "\r\n");
			pw.write(spaceNum + "\r\n");
			// Close the file
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			System.out.printf("Can't write the config file!\n");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	// Get and set methods
	public int getTicketNum() {
		return ticketNum;
	}
	
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
		synchronize();
	}
	
	public double getCoinNum() {
		return coinNum;
	}
	
	public void setCoinNum(double coinNum) {
		this.coinNum = coinNum;
		synchronize();
	}
	
	public int getSpaceNum() {
		return spaceNum;
	}
	
	public void setSpaceNum(int spaceNum) {
		this.spaceNum = spaceNum;
		synchronize();
	}
}
