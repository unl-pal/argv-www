package com.simpleprogrammer.actions;

import java.util.Random;
import java.util.Map;

public class EnterGoal {

	private static final long serialVersionUID = 1L;

	private int goal;
	private Map<String, Object> session;

	public String execute() throws Exception {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		} else {
		}

		goal = rand.nextInt();

		return "";
	}

	public String save() throws Exception {
		Random rand = new Random();
		if (rand.nextBoolean()) {
		} else {
		}

		return "enter-goal";
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}
}
