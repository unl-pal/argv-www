package dungeonDigger.contentGeneration;

public class Room {
	private String name;
	private int roomID;
	private boolean[][] layout;
	private int height, width, row, column;

	public Room copy() {
		Room result = new Room();
		result.column = column;
		result.height = height;
		result.layout = layout;
		result.name = name;
		result.roomID = roomID;
		result.row = row;
		result.width = width;

		return result;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *        the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the roomID
	 */
	public int getRoomID() {
		return roomID;
	}

	/**
	 * @param roomID
	 *        the roomID to set
	 */
	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}

	/**
	 * @return the layout
	 */
	public boolean[][] getLayout() {
		return layout;
	}

	/**
	 * @param layout
	 *        the layout to set
	 */
	public void setLayout(boolean[][] layout) {
		this.layout = layout;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *        the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *        the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getColumn() {
		return column;
	}
}
