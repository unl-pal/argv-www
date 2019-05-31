package dungeonDigger.contentGeneration;

import java.util.Random;

public class GameSquare {
	private char			tileLetter	= 'W';
	private int				row, col;
	public GameSquare(int row, int col) {
		this('W', row, col);
	}

	public GameSquare(char c, int row, int col) {
		this.tileLetter = c;
		this.row = row;
		this.col = col;
	}

	public float getWidth() {
		Random rand = new Random();
		return rand.nextFloat();
	}
	
	public float getHeight() {
		Random rand = new Random();
		return rand.nextFloat();
	}
	
	/////////////////////////
	// GETTERS AND SETTERS //
	/////////////////////////
	public boolean isPassable() {
		Random rand = new Random();
		return rand.nextBoolean();
	}

	/** @param tileImage the tileImage to set */
	public void setTileLetter(char tileLetter) {
		this.tileLetter = tileLetter;
	}

	/** @return the tileImage */
	public char getTileLetter() {
		return tileLetter;
	}

	/** @return True if the String parameter is the same. */
	public boolean isTileLetter(char s) {
		return tileLetter == s;
	}

	public Object getBelongsTo() {
		return new Object();
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
