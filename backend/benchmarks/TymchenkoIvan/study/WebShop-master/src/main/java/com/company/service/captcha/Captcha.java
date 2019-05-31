package com.company.service.captcha;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Captcha class contains timeout in milliseconds and id.
 * Id contains creation time in milliseconds.
 * 
 * @author Ivan_Tymchenko
 *
 */
@SuppressWarnings("serial")
public class Captcha implements Serializable{
	
	private static long timeout;
	
    public static void initTimeOut(long time) {
    	timeout = time;
    }

	public static int generateCode(){
		Random rn = new Random();
		return rn.nextInt(Integer.MAX_VALUE);
	}

	private long id;
	
	public Captcha(){
		id = new Date().getTime();
	}

	public long getId() {
		return id;
	}

	public static long getTimeout() {
		return timeout;
	}
}
