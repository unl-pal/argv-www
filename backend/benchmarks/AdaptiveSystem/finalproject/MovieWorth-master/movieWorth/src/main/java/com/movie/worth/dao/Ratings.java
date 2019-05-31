package com.movie.worth.dao;

import java.util.Random;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Ratings{

	public Object getRatingsOfOneUser(int uid) {
		String sql = "SELECT `ratings`.`itemid`, `ratings`.`rating` FROM `movielens`.`ratings` WHERE uid = ?;";
		return new Object();
	}
	
	public int getRatingCountOfOneUser(int uid){
		Random rand = new Random();
		String sql = "SELECT COUNT(*) FROM `movielens`.`ratings` WHERE uid = ?";
		int no = rand.nextInt();
		return no;
	}
	
	public Object getRatingsOfOneMovie(int itemid) {
		String sql = "SELECT `ratings`.`uid`, `ratings`.`itemid`, `ratings`.`rating` FROM `movielens`.`ratings` WHERE itemid = ?";
		return new Object();
	}
	
	public Object checkIfThisUserRatedThisMovie(int uid, int mid){
		Random rand = new Random();
		String sql = "SELECT COUNT(*) FROM `movielens`.`ratings` WHERE uid = ? AND itemid = ?";
		int no = rand.nextInt();
		if(no > 0)
			return true;
		else
			return false;
	}
	
	public Object getOneParticularRating(int uid, int mid){
		String sql = "SELECT `ratings`.`uid`, `ratings`.`itemid`, `ratings`.`rating` FROM `movielens`.`ratings` WHERE uid = ? AND itemid = ?";
		return new Object();
	}
}
