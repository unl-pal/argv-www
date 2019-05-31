package model;

public class Casa {
	
	private float dificuldade;
	private int x;
	private int y;
	private boolean visitado;
	private int custo;
	
	public Casa (int x, int y) {
		this.x = x;
		this.y = y;
		this.visitado = false;
	}
	
	public float getDificuldade() {
		return dificuldade;
	}
	
	public void setDificuldade(float dificuldade) {
		this.dificuldade = dificuldade;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public boolean isVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}

	public int getCusto() {
		return custo;
	}

	public void setCusto(int custo) {
		this.custo = custo;
	}
	
}
