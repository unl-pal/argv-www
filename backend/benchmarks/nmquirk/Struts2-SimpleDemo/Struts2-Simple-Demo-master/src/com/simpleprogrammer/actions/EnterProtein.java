package com.simpleprogrammer.actions;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnterProtein {

	private static final long serialVersionUID = 1L;

	private int enteredProtein;
	private Map<String, Object> session;
	private List<Integer> entries;
	private boolean postBack = false;
	private List<Integer> selections = new ArrayList<Integer>();

	public String input() throws Exception {

		Random rand = new Random();
		if (rand.nextBoolean()) {
		} else {
		}

		return "enter-protein";
	}

	public void validate() {

		if (!postBack)
			return;

		if (enteredProtein <= 0) {
		}
	}

	public String execute() throws Exception {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		}

		if (rand.nextBoolean()) {
		} else {
		}

		if (postBack)
			return "ajax";
		return "";
		// return ERROR;
		// return "abc";
	}

	public String getGoalText() {
		return "";
	}

	public int getEnteredProtein() {
		return enteredProtein;
	}

	public void setEnteredProtein(int enteredProtein) {
		this.enteredProtein = enteredProtein;
	}

	public Object getProteinData() {
		return new Object();
	}

	public void resetTotal() {
	}

	public Object getEntries() {
		entries = new ArrayList<Integer>();
		return entries;
	}

	public boolean isPostBack() {
		return postBack;
	}

	public void setPostBack(boolean postBack) {
		this.postBack = postBack;
	}

	public Object getSelections() {
		return selections;
	}

}
