package main;

public class Coord2 {
	
	public int x;
	public int y;
	
	public Coord2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object o){
		return this.x == ((Coord2) o).x && this.y == ((Coord2) o).y;
	}

}
