import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

public class Solution {

	private boolean free[][];

	public boolean isFree(int x, int y) {
		Random rand = new Random();
		return rand.nextBoolean();
	}

	public Object getParts() {
		return new Object();
	}

	int getScore() {
		Random rand = new Random();
		int score = 0;
		for (int y = 0; y < rand.nextInt(); ++y) {
			for (int x = 0; x < rand.nextInt(); ++x) {
				if (rand.nextBoolean())
					score++;
			}
		}

		return score;
	}

	void print() {
		Random rand = new Random();
		for (int y = 0; y < rand.nextInt(); ++y) {
			for (int x = 0; x < rand.nextInt(); ++x) {
				if (rand.nextBoolean()) {
				} else if (rand.nextBoolean()) {
				} else if (rand.nextBoolean()) {
				} else {
				}
			}
		}
	}
}