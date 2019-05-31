package markov;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class monteCarlo {
	private int[] alphabet;
	private double[][] matrix;
	private int[][] mapping;
	private double plaus;
	
	/*
	 * Constructor takes alphabet 
	 * and number of significant preceding letters 
	 * as arguments
	 */
	public monteCarlo(String filename, int order) {
		//needs multi-dimension fix
	}
	/*
	 * Default constructor
	 */
	public monteCarlo(String filename) throws IOException {
		createAlphabet(filename);
		int as = alphabet.length;
		matrix = new double[as][as];
		for (int i = 0; i < as; i++)
		{
			for (int j = 0; j < as; j++)
			{
				matrix[i][j] = 1;
			}
		}
		createMatrix(filename);
		mapping = new int[2][as];
		for(int i = 0; i < as; i++) {
			mapping[0][i] = alphabet[i]; //will be used to hold current function
			mapping[1][i] = alphabet[i]; //will be used to hold transposition
		}
		plaus = calcPlaus(mapping[0]);
	}
	
	/*
	 * Builds alphabet for a given file by
	 * Creating an index for each unique character
	 * Then move to global alphabet and sort by ASCII value
	 */
	private void createAlphabet(String filename) throws IOException {
		ArrayList<Integer> alfa = new ArrayList<Integer>();
		@SuppressWarnings("resource")
		BufferedReader stream = new BufferedReader(new FileReader(filename));
		int ch = 0;
		while(stream.ready()) {
			ch = stream.read();
			if(!alfa.contains(ch)) {
				alfa.add(ch);
			}
		}
		alphabet = new int[alfa.size()];
		for(int i = 0; i < alfa.size(); i++) {
			alphabet[i] = alfa.get(i);
		}
		Arrays.sort(alphabet);
	}
	/*
	 * Read in a file and count the occurences of letter pairs
	 * Normalize values with logarithms to avoid underflow
	 */
	private void createMatrix(String filename) throws IOException {
		//count occurences that char x follows y and store in matrix[y][x]
		@SuppressWarnings("resource")
		BufferedReader stream = new BufferedReader(new FileReader(filename));
		int ch=0;
		if(stream.ready()) {
			ch = stream.read();
		}
		while(stream.ready()) {
			int next = stream.read();
			matrix[indexLookup(ch)][indexLookup(next)]++;
			ch = next;
			next = stream.read();
		}
		//change all values to probabilities by dividing each entry in a row by the sum of the row
		//reusing ch as row counter
		for(ch = 0; ch < matrix.length; ch++) {
			double sum = alphabet.length;
			for(int i = 0; i < matrix.length; i++) {
				sum += matrix[ch][i];
			}
			sum = Math.log(sum);
			for(int i = 0; i < matrix.length; i++) {
				matrix[ch][i] = Math.log(matrix[ch][i]) - sum;
			}
		}
	}
	/* 
	 * Create next mapping from current mapping by 
	 * Change to f* by making a random transposition of
	 * the values f assigns to two symbols.
	 * Compute Pl(f*); if this is larger than Pl(f), accept f*.
	 * If not, flip a Pl(f*)/Pl(f) coin
	 */
	private void nextMapping() {
		//randomly transpose two values in f
		Random rg = new Random();
		int i=rg.nextInt(mapping[0].length), j=rg.nextInt(mapping[0].length);
		int temp = mapping[1][i];
		mapping[1][i]=mapping[1][j];
		mapping[1][j]=temp;
		//check if more plausible
		double currentPl = calcPlaus(mapping[0]);
		double transposedPl = calcPlaus(mapping[1]);
		if(currentPl < transposedPl) {
			//if yes set current function to transposed function
			for(i = 0; i < mapping[0].length; i++) {
				mapping[0][i] = mapping[1][i];
			}
		}
		else {
			//else flip Bernoulli Coin of plausibilities and accept result
			double p = rg.nextDouble();
			if(p < transposedPl/(transposedPl+currentPl)) {
				for(i = 0; i < mapping[0].length; i++) {
					mapping[0][i] = mapping[1][i];
				}
			}
		}
	}
	private double calcPlaus(int[] func) {
		double p = 0;
		//figure out
		return p;
	}
	public double calcPlaus(String phrase) {
		double p = 0;
		for(int i = 0; i < phrase.length()-1; i++) {
			p += matrix[indexLookup(phrase.charAt(i))][indexLookup(phrase.charAt(i+1))];
		}
		return p;
	}
	private int indexLookup(int ch) {
		for(int i = 0; i < alphabet.length; i++) {
			if(alphabet[i]==ch) {
				return i;
			}
		}
		return -1;
	}
	public String toString() {
		String result = "Rows are first character, Columns are second character\n";
		for(int i = 0; i < matrix.length; i++) {
			if(i==0) {
				result += "  ";
				for(; i < alphabet.length; i++) {
					result += "          " + (char)alphabet[i] + "          ";
				}
				result += "\n";
				i=0;
			}
			for(int j = 0; j < matrix.length; j++) {
				if(j==0) result += (char)alphabet[i] + " ";
				result += matrix[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
	
	public static void main(String[] args) throws IOException {
		monteCarlo test = new monteCarlo("src/warandpeace.txt");
		for(int i = 0; i < test.alphabet.length; i++) {
			System.out.print((char)test.alphabet[i] + " ");
		} System.out.print("\n");
		System.out.println(test);
		System.out.println("The plausibility of \"Xanthomata\" is: " + test.calcPlaus("xanthomata"));
		System.out.println("The plausibility of a paragraph from War and Peace is: " + test.calcPlaus("Prince Vasili kept the promise he had given to Princess Drubetskaya who had spoken to him on behalf of her only son Boris on the evening of Anna Pavlovnas soiree. The matter was mentioned to the Emperor, an exception made, and Boris transferred into the regiment of Semenov Guards with the rank of cornet. He received, however, no appointment to Kutuzovs staff despite all Anna Mikhaylovnas endeavors and entreaties. Soon after Anna Pavlovnas reception Anna Mikhaylovna returned to Moscow and went straight to her rich relations, the Rostovs, with whom she stayed when in the town and where her darling Bory, who had only just entered a regiment of the line and was being at once transferred to the Guards as a cornet, had been educated from childhood and lived for years at a time. The Guards had already left Petersburg on the tenth of August, and her son, who had remained in Moscow for his equipment, was to join them on the march to Radzivilov. It was St. Natalias day and the name day of two of the Rostovs the mother and the youngest daughter both named Nataly. Ever since the morning, carriages with six horses had been coming and going continually, bringing visitors to the Countess Rostovas big house on the Povarskaya, so well known to all Moscow. The countess herself and her handsome eldest daughter were in the drawing room with the visitors who came to congratulate, and who constantly succeeded one another in relays. The countess was a woman of about forty five, with a thin Oriental type of face, evidently worn out with childbearing she had had twelve. A languor of motion and speech, resulting from weakness, gave her a distinguished air which inspired respect. Princess Anna Mikhaylovna Drubetskaya, who as a member of the household was also seated in the drawing room, helped to receive and entertain the visitors. The young people were in one of the inner rooms, not considering it necessary to take part in receiving the visitors. The count met the guests and saw them off, inviting them all to dinner."));
		System.out.println("The plausibility of the string \"The\" is: " + test.calcPlaus("The"));
		System.out.println("The plausibility of the string \"The quick brown fox jumped over the lazy dog.\" is: " + test.calcPlaus("The quick brown fox jumped over the lazy dog"));
	}
}