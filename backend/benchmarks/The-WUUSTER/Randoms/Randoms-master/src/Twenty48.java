
public class Twenty48 
{
	private int rowLength;
	private int colLength;
	private int[][] board;
	
	public Twenty48(int r, int c)
	{
		rowLength = r;
		colLength = c;
		board = new int[r][c];
		reset();
	}
	
	public void reset()
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[0].length; j++)
			{
				board[i][j] = 0;
			}
		}
	}
	
	public int getRowLength()
	{
		return rowLength;
	}
	
	public int getColLength()
	{
		return colLength;
	}
	
	public int getTile(int r, int c)
	{
		return board[r][c];
	}
	
	public void setTile(int r, int c, int value)
	{
		board[r][c] = value;
	}
	
	public static int[] merge(int[] line)
	{
		int[] result = new int[line.length];
		boolean[] merged = new boolean[line.length];
		
		for (int x = 0; x < result.length; x++)
		{
			result[x] = 0;
			merged[x] = false;
		}
		
		for (int i = 0; i < line.length; i++)
		{
			if (line[i] != 0)
			{
				for (int j = 0; j < result.length; j++)
				{
					if (result[j] == 0)
					{
						result[j] = line[i];
						break;
					}
				}
			}
		}
		
		for (int shit = 0; shit < result.length - 1; shit++)
		{
			if (result[shit] == result[shit + 1] && !merged[shit])
			{
				result[shit] *= 2;
				merged[shit] = true;
				for (int nipple = shit + 1; nipple < result.length - 1; nipple++)
				{
					result[nipple] = result[nipple + 1];
				}
				result[result.length - 1] = 0;
			}
		}
		
		return result;
	}
	
	public void newTile()
	{
		int randomRow = (int)(Math.random() * rowLength);
		int randomCol = (int)(Math.random() * colLength);
		while (board[randomRow][randomCol] != 0)
		{
			randomRow = (int)(Math.random() * rowLength);
			randomCol = (int)(Math.random() * colLength);
		}
		int chance = 1 + (int)(Math.random() * 10);
		if (chance == 1)
		{
			board[randomRow][randomCol] = 4;
		}
		else
		{
			board[randomRow][randomCol] = 2;
		}
	}
	
	public static void main(String[] args)
	{
		int[] a = {8, 16, 16, 8}, b = {2, 0, 2, 4}, c = {0, 0, 2, 2}, d = {2, 2, 2, 2};
		a = merge(a);
		b = merge(b);
		c = merge(c);
		d = merge(d);
		
		for (int i = 0; i < b.length; i++)
		{
			System.out.print(b[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < c.length; i++)
		{
			System.out.print(c[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < d.length; i++)
		{
			System.out.print(d[i] + " ");
		}
		System.out.println();
		for (int i = 0; i < a.length; i++)
		{
			System.out.print(a[i] + " ");
		}
	}
}
