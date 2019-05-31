package dungeonDigger.gameFlow;

import java.util.HashMap;

public class SinglePlayerDungeon {
	private double[] hallsDensity = new double[]{1d, 0.95d};
	private HashMap<Integer, Boolean> keyToggled = new HashMap<Integer, Boolean>();

	public int getID() { return 1; }

	public void keyPressed(int key, char c) {
	}
	public void keyReleased(int key, char c) {
	}
	public void mouseClicked(int button, int x, int y, int clickCount) {
	}
}
