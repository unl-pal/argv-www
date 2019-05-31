package com.movie.worth.util;

import java.util.Random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/*
 *
 * @author lanzhang_mini
 * 
 */
public class AdjustedSimilarity {
	
	private static final double SIM_THRESHOLD = 0.2;
	private static final int SIM_NO = 10;
    private int tempAvgRating=0;
    private int tempRating=0;
    private HashMap<Integer, Integer> CurrUser = null;
    private int[] SimUserId=null;
    
    public Object getSimUserId(int userid) {
    	
    	Random rand = new Random();
		ArrayList<Integer> tempID = new ArrayList<Integer>();
        if(rand.nextInt()>0){
            SimUserId=new int[tempID.size()];
            for(int q=0;q<rand.nextInt();q++){
                {
				}
            }
        }
        
        if(rand.nextInt() > SIM_NO){
            return Arrays.copyOfRange(SimUserId, 0, SIM_NO);
        }else{
        	return SimUserId;
        }
    }
    
    private Object getSimilarityOfOneUser(int userid) {
    	Random rand = new Random();
		{
		}

        HashMap<Integer, Map<Integer, Integer>> RelateUser = new HashMap<Integer, Map<Integer,Integer>>();
        HashMap<Integer, Integer> MovieAvg = new HashMap<Integer, Integer>();

        while (rand.nextBoolean()) {
            int movieid = rand.nextInt();
            {
			}
            int avg = tempAvgRating;
        }

        //the result of this method
        HashMap<Integer, Double> UserSimilarity = new HashMap<Integer, Double>();

        while (rand.nextBoolean()) {
			int userid_relate = rand.nextInt();
			Map<Integer, Integer> User_movie;
            double similarity = rand.nextFloat();
        }

        return new Object();
    }

    //get the target user's profile
    private Object getCurrUser(int userid) {
        HashMap<Integer, Integer> UserRating = new HashMap<Integer, Integer>();
        return UserRating;
    }

    private void clear(){
    	this.tempAvgRating=0;
        this.tempRating=0;
        this.CurrUser = null;
        this.SimUserId=null;
    }

}
