import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * This public class is a collection of useful methods for making console games
 * 
 * List of methods:
 * makeSpace()
 * promptInt()
 * swapDim()
 * 
 * @author Will Brown (AKA ClockM)
 */

public abstract class ConsoleGame
{
	private Scanner userInput;
	public double version;
	public String title;
	
	abstract void runGame();
	
	public ConsoleGame()
	{
		this.userInput = null;
		this.version = 0.0;
		this.title = "default";
	}
	
	
	
	// Creates a break between messages and an additional n line breaks
	public void makeSpace(int n)
	{
		System.out.print("----------------------------");
		for(int i=0;i<=n;i++)
		{
			System.out.println();
		}
	}
		
	// Prints the string s and prompts recursively for an integer value
	public int promptInt(String s)
	{
		int n = 0;
		while(true)
		{
			try
			{
				System.out.println(s);
				userInput = new Scanner(System.in);
				n = userInput.nextInt();
				break;
			}
			catch(InputMismatchException e)
			{
				makeSpace(0);
				System.out.println("*Please input an integer*");
			}
		}
		return n;
	}
	
	// Prints the string s and prompts recursively for an integer value
	// Also checks to make sure the integer is between the min. and the max. values specified
	public int promptInt(String s, int min, int max)
	{
		int n = 0;
		while(true)
		{
			try
			{
				System.out.println(s);
				userInput = new Scanner(System.in);
				n = userInput.nextInt();
				if(n<min || n>max)
				{
					throw new InputMismatchException();
				}
				break;
			}
			catch(InputMismatchException e)
			{
				makeSpace(0);
				System.out.println("*Please input an integer between " + min + " and " + max + "*");
			}
		}
		return n;
	}
		
	//Returns a 2D boolean array such that b[i][j] becomes b[j][i] for all values of i and j
	public boolean[][] SwapDim(boolean[][] b)
	{
		boolean[][] bInv = new boolean[b[0].length][b.length];
		
		for(int j=b[0].length-1;j>=0;j--)
		{
			for(int i=0;i<b.length;i++)
			{
				bInv[j][i] = b[i][j];
			}
		}
		return bInv;
	}
}
