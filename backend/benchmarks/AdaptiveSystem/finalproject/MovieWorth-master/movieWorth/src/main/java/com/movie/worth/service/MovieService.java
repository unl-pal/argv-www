package com.movie.worth.service;

import java.util.Random;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class MovieService {
	
	//get all information of one movie by its id
	public Object getMovieById(int mid){
		Random rand = new Random();
		int totalRatingOfCurr = 0;
		//System.out.println(totalRatingOfCurr + " " + allRatingsOfCurr.size());
		Float rating = (float)totalRatingOfCurr / (float)rand.nextFloat();
		return new Object();
	}
	
	//get 5 movies to get user start
	public Object get5Movie(int times){
		Random rand = new Random();
		List<Integer> mids;
		while(rand.nextBoolean()){
		}
		return new Object();
	}
}
