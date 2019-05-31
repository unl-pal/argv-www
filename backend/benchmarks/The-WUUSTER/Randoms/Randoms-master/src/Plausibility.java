import java.io.*;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.Scanner;

public class Plausibility 
{
	private int[] alphabet;
	private double[][] matrix;
	private int[][] mapping;
	private double plaus;
	
	public Plausibility(int alphabet_size)
	{
		alphabet = new int[alphabet_size];
		matrix = new double[alphabet_size][alphabet_size];
		mapping = new int[2][alphabet_size];
		plaus = 0;
	}
	
	/**
	 * Returns an appropriate index for the matrix
	 */
	public static int indexLookup(int ch)
	{
		char character = (char)ch;
		if (Character.isLetter(character))
		{
			character = Character.toLowerCase(character);
			int matrix_val = (int)character - 97;
			return matrix_val;
		}
		else
		{
			if (character == ' ')
				return 26;
			else if (character == '.')
				return 27;
			else if (character == ',')
				return 28;
			else if (character == '?')
				return 29;
			else if (character == '!')
				return 30;
			else if (character == '-')
				return 31;
			else
				return 32;
		}
	}
	
	/**
	 * Creates a matrix out of a file
	 */
	public void createMatrix(String file) throws IOException
	{
		@SuppressWarnings("resource")
		BufferedReader stream = new BufferedReader(new FileReader(file));
		int ch = 0;
		Plausibility function = new Plausibility(33);
		if(stream.ready()) 
		{
			ch = stream.read();
		}
		while(stream.ready()) 
		{
			int next = stream.read();
			if((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || ch == 32 || ch == 33 || ch == 44 || ch == 46 || ch == 63) 
			{
				if((next >= 65 && next <= 90) || (next >= 97 && next <= 122) || next == 32 || next == 33 || next == 44 || next == 46 || next == 63) 
				{
					function.matrix[indexLookup(ch)][indexLookup(next)]++;
					ch = next;
					next = stream.read();
				}
				else 
					ch = stream.read();
			}
			else 
				ch = next;
		}
		for (ch = 0; ch < function.matrix.length; ch++)
		{
			int sum = 0;
			for (int i = 0; i < function.matrix.length; i++)
			{
				sum += function.matrix[ch][i];
			}
			for (int j = 0; j < function.matrix.length; j++)
			{
				function.matrix[ch][j] /= sum;
			}
		}
	}
	
	public double calculatePlaus(int[] mapping)
	{
		return 0;
	}
	
	public double calculatePlaus(String line)
	{
		double p = 1.0;
		for (int i = 0; i < line.length() - 1; i++)
		{
			p *= matrix[indexLookup(line.charAt(i))][indexLookup(line.charAt(i + 1))];
		}
		return p;
	}
	
	public void nextMapping()
	{
		Random rng = new Random();
		int i = rng.nextInt(mapping[0].length), j = rng.nextInt(mapping[0].length);
		int temp = mapping[1][i];
		mapping[1][i] = mapping[1][j];
		mapping[1][j] = temp;
		double current_plausibility = calculatePlaus(mapping[0]);
		double transposed_plausibility = calculatePlaus(mapping[1]);
		if (transposed_plausibility > current_plausibility)
		{
			for (int x = 0; x < mapping[0].length; x++)
			{
				mapping[0][x] = mapping[1][x];
			}
		}
		else
		{
			double x = rng.nextDouble();
		}
	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException
	{
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter file name: ");
		String filename = sc.next();
		Plausibility function = new Plausibility(31);
		function.createMatrix(filename);
	}
}
