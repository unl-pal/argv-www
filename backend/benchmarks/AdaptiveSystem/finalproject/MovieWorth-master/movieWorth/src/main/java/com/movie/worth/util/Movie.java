package com.movie.worth.util;

import java.util.ArrayList;

public class Movie {
	private int mid;
	private String title;
	private String releaseDate;
	private String imdbURL;
	private ArrayList<String> genre;
	private Float rating;
	
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getImdbURL() {
		return imdbURL;
	}
	public void setImdbURL(String imdbURL) {
		this.imdbURL = imdbURL;
	}
	public ArrayList<String> getGenre() {
		return genre;
	}
	public void setGenre(ArrayList<String> genre) {
		this.genre = genre;
	}
	public Float getRating() {
		return rating;
	}
	public void setRating(Float rating) {
		this.rating = rating;
	}
}
