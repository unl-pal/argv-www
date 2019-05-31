/*
 * The core logic of 2048
 */
package com.xer.fx2048;

/**
 *
 * @author lifeng liu 
 */
public class Core {
	private int [][] board;
	private int size;
	private int list[][];
	private int score;
	/**
	 * The constructor of Core class
	 * @param width the number of tiles in x direction
	 * @param height the number of tiles in y direction 
	 */	
	public Core(int size)
	{
		this.size=size;
		this.score=0;
		board=new int[size][size];
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				board[i][j]=0;
			}
		}
		list=new int[size*size][2];
		addRandom();
		addRandom();
	}
	/**
	 * slide up
	 * @return slide success or not
	 */
	public boolean slideUp()
	{
		boolean success=false;
		for(int i=0;i<size;i++)
		{
			if(slideColumn(board[i]))
			{
				success=true;
			}
		}
		if(success)
		{
			addRandom();
		}
		return success;
	}
	/**
	 * slide right
	 * @return slide success or not
	 */
	public boolean slideRight()
	{
		boolean success=false;
		rotate();
		rotate();
		rotate();
		success=slideUp();
		rotate();
		return success;
	}
	/**
	 * slide down
	 * @return slide success or not
	 */
	public boolean slideDown()
	{
		boolean success=false;
		rotate();
		rotate();
		success=slideUp();
		rotate();
		rotate();
		return success;
	}
	/**
	 * slide left
	 * @return slide success or not
	 */
	public boolean slideLeft()
	{
		boolean success=false;
		rotate();
		success=slideUp();
		rotate();
		rotate();
		rotate();
		return success;
	}
	/**
	 * Randomly add a tile in empty slot.
	 */
	public void addRandom()
	{
		int len=0;
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				if(board[i][j]==0)
				{
					list[len][0]=i;
					list[len][1]=j;
					len++;
				}
			}
		}
		if(len>0)
		{
			int r=(int)(Math.random()*len);	
			int x=list[r][0];
			int y=list[r][1];
			int n=(((int)(Math.random()*10))/9+1)*2;	
			board[x][y]=n;	
		}
		
	}
	/**
	 * Rotate the board to right by 90 degrees
	 */
	public void rotate()
	{
		int temp=0;
		for(int i=0;i<size/2;i++)
		{
			for(int j=i;j<size-i-1;j++)
			{
				temp=board[i][j];
				board[i][j]=board[j][size-i-1];
				board[j][size-i-1]=board[size-i-1][size-j-1];
				board[size-i-1][size-j-1]=board[size-j-1][i];
				board[size-j-1][i]=temp;
			}
		}
	}
	/**
	 * slide a column 
	 */
	public boolean slideColumn(int [] line)
	{
		int stop=0;
		boolean success=false;
		for(int i=0;i<line.length;i++)
		{
			if(line[i]!=0)
			{
				int t=findTarget(line,i,stop);
				if(t!=i)
				{
					if(line[t]!=0)
					{
						score+=line[t]+line[i];
						stop=t+1;
					}
					line[t]+=line[i];
					line[i]=0;
					success=true;
				}
			}
		}
		return success;
	}
	/**
	 * Find merge target for location x
	 */
	public int findTarget(int [] line, int x,int stop)
	{
		if(x==0)
		{
			return 0;
		}
		for(int i=x-1;i>=0;i--)
		{
			if(line[i]!=0)
			{
				if(line[i]!=line[x])
				{
					return i+1;	
				}
				else
				{
					return i;
				}
			}
			else
			{
				if(i==stop)
				{
					return i;
				}
			}
		}
		return x;
	}
	/**
	 * Get the board matrix from core object;
	 * @return the reference of board matrix 
	 */
	public int [][] getBoard()
	{
		return board;
	}
	/**
	 * Set the content of board matrix .
	 * For test purpose. 
	 * @param board  The board going to set. 
	 */
	public void setBoard(int [][] board)
	{
		for(int i=0;i<size;i++)
		{
			for(int j=0;j<size;j++)
			{
				this.board[i][j]=board[i][j];
			}
		}
	}
}
