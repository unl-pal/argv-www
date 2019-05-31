package carparksystem.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * This is an entity class storing the 
 * information parking records.
 * 
 * @author Group 31
 * @version Last modified 20/04/2012
 * 
*/

public class Record {
	private int id;
	private Date enterTime;
	private Date departTime;
	private double payment;
	
	/**
	 * This methods is used to convert time from
	 * String format to Date format.
	 *  
	 * @param time The String representation of time.
	 * @return Date The Date representation of time.
	 */
	public Date stringToTime(String time) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = null;
		try {
			date = bartDateFormat.parse(time);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return date;
	}
	
	/**
	 * This methods is used to convert time from
	 * Date format to String format.
	 * @param time The Date representation of time.
	 * @return String The String representation of time.
	 */
	public String timeToString(Date time) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		return bartDateFormat.format(time);
	}
	
	//Get and set method
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Date getEnterTime() {
		return enterTime;
	}

	public void setEnterTime(Date enterTime) {
		this.enterTime = enterTime;
	}

	public Date getDepartTime() {
		return departTime;
	}

	public void setDepartTime(Date departTime) {
		this.departTime = departTime;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}
}
