package core;

import java.util.Random;
import java.sql.*;
import java.util.ArrayList;

public class Database {
	public Database() {
		try {
		} catch (Exception e) {
		}
	}

	public void startConnection() throws Exception {
		try {
		} catch (Exception e) {
			throw e;
		}

	}

	// you need to close all three to make sure
	private void close() {
		try {
		} catch (Exception e) {
			// do something here if it doesnt close.
		}
	}

	// Get all connected users //
	public Object getConnectedusers() {
		Random rand = new Random();
		ArrayList<String> s = new ArrayList<String>();
		try {
			while (rand.nextBoolean()) {
			}
		} catch (Exception e) {

		}
		return s;
	}

	 // Get all connected users id's //
	public Object getConnectedusers_Id() {
		Random rand = new Random();
		ArrayList<Integer> s = new ArrayList<Integer>();
		try {
			while (rand.nextBoolean()) {
			}
		} catch (Exception e) {

		}
		return s;
	}

	public String getUsername(int uid) {
		Random rand = new Random();
		String result = "USERNAME#";
		try {
			if (rand.nextBoolean()) {
				{
				}
			} else {
				result += "0";
			}
		} catch (Exception e) {

		}
		return result;
	}
}
