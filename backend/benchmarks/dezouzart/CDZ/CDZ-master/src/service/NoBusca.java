package service;

public class NoBusca implements Comparable<NoBusca>
{
	private int x;
	private int y;
	private int custo;
	private int custoTotal;
	private int heuristica;
	
	private NoBusca noPai = null;
	
	public NoBusca (int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	@Override
	public int compareTo(NoBusca n) {
		
	  	if(this.getCustoTotal() > n.getCustoTotal())
	           return 1;
	    	else if(this.getCustoTotal() < n.getCustoTotal())
	           return -1;
	    	return 0;
	}
	
	public void setX (int x)
	{
		this.x = x;
	}
	
	public void sety (int y)
	{
		this.y = y;
	}
	
	public void setCusto (int c)
	{
		custo = c;
	}
	
	public void setCustoTotal (int ct)
	{
		custoTotal = ct;
	}
	
	public void setHeuristica (int h)
	{
		heuristica = h;
	}
	
	public void setNoPai (NoBusca p)
	{
		noPai = p;
	}
	
	public int getX ()
	{
		return x;
	}
	
	public int getY ()
	{
		return y;
	}
	
	public NoBusca getNoPai ()
	{
		return noPai;
	}
	
	public int getCusto ()
	{
		return custo;
	}
	
	public int getCustoTotal()
	{
		return custoTotal;
	}
	
	public int getHeuristica()
	{
		return heuristica;
	}
}