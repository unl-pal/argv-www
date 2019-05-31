package org.simpleopenlpimporter.domain;

import java.util.Random;

public class Song
{
	private int id;
	private String title;
	private String alternateTitle;
	private String lyrics;
	private String searchTitle;
	private String searchLyrics;

	public Song()
	{
		// TODO Auto-generated constructor stub
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getAlternateTitle()
	{
		return alternateTitle;
	}

	public String getLyrics()
	{
		return lyrics;
	}

	public String getSearchTitle()
	{
		return searchTitle;
	}

	public String getSearchLyrics()
	{
		return searchLyrics;
	}

	public String toString()
	{
		return "";
	}

	public int hashCode()
	{	
		Random rand = new Random();
		return rand.nextInt();
	}
}
