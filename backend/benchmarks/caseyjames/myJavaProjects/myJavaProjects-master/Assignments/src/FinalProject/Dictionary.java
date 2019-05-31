package FinalProject;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 *
 *
 *
 *
 */
@SuppressWarnings("ConstantConditions")
public class Dictionary {
    private Node[] dictionary;

    /**
     * Constructor for Dictionary to hold all correctly spelled words and their frequencies.
     * @param inputFile - file to read words from
     * @param tableSize - initial table size
     */
    public Dictionary(File inputFile, int tableSize) {
        dictionary = new Node[nextPrime(tableSize)];
        Scanner inputStats = new Scanner("");
        //try reading file
        try {
            inputStats = new Scanner(inputFile);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        Scanner lineParse;
        String nextName;
        int nextFreq;

        //read data from file and add to dictionary
        while (inputStats.hasNextLine()) {
            lineParse = new Scanner(inputStats.nextLine());
            nextName = lineParse.next().toLowerCase();
            nextFreq = Integer.parseInt(lineParse.next());
            add(nextName, nextFreq);
        }
    }

    /**
     * Creates alternate spellings for the passed in word and returns the most likely alternate,
     * optionally writes all of those alternates to a text file named '<word>.txt'
     * @param word - String, word to generate alternates for
     * @param fileWrite - Boolean, whether to create a verbose text file containing all of the
     *                  alternates
     * @return - String, most likely alternate or empty String if unknown word
     */
    public String spellCheck(String word, boolean fileWrite) {
        if (word == null)
            return "";
        word = word.toLowerCase();
        //if known word, return
        if (this.contains(word))
            return word;

        int alternateCount = 0;
        String maxFreq = "";
        int i, j = 0;


        //setup PrintWriter
        PrintWriter writeFile = null;
        if (fileWrite) {
            try {
                writeFile = new PrintWriter("" + word + ".txt");
                writeFile.print("User string: " + word);
                writeFile.println();
                writeFile.println();
            } catch (Exception e) {
                System.err.println("Unable to write file!");
                return "";
            }
        }

        StringBuilder temp;
        // for loop for deletion alternative
        for (i = 0; i < word.length(); i++) {
            temp = new StringBuilder(word);
            //delete char at index and check for validity
            temp.deleteCharAt(i);
            String newWord = temp.toString();
            if (fileWrite)
                writeFile.println("Deletion string: " + newWord);
            if (this.frequency(newWord) > this.frequency(maxFreq))
                maxFreq = newWord;
            alternateCount++;
        }
        if (fileWrite) {
            writeFile.println("Created " + i + " deletion alternatives");
            writeFile.println();
        }

        // for loop for transposition alternatives
        for (i = 0; i < word.length() - 1; i++) {
            temp = new StringBuilder(word);
            //swap characters going left to right and check for validity
            char tempChar = temp.charAt(i);
            temp.setCharAt(i, temp.charAt(i + 1));
            temp.setCharAt(i + 1, tempChar);
            String newWord = temp.toString();
            if (fileWrite)
                writeFile.println("Transposition string: " + newWord);
            if (this.frequency(newWord) > this.frequency(maxFreq))
                maxFreq = newWord;
            alternateCount++;
        }
        if (fileWrite) {
            writeFile.println("Created " + i + " transposition alternatives");
            writeFile.println();
        }
        // for loop for substitution alternatives
        for (i = 0; i < word.length(); i++) {
            //replace char at specified index with current ascii char and check for validity
            for (j = 0; j < 26; j++) {
                temp = new StringBuilder(word);
                if (temp.charAt(i) == (char)(j+97))
                    continue;
                temp.setCharAt(i, (char) (j + 97));
                String newWord = temp.toString();
                if (fileWrite) {
                    writeFile.println("Substitution string: " + newWord);
                }
                if (this.frequency(newWord) > this.frequency(maxFreq))
                    maxFreq = newWord;
                alternateCount++;
            }
        }
        if (fileWrite) {
            writeFile.println("Created " + (i * (j - 1)) + " substitution alternatives");
            writeFile.println();
        }
        // for loops for insertion alternatives
        for (i = 0; i <= word.length(); i++) {
            //insert ascii character at specified index and check for validity
            for (j = 0; j < 26; j++) {
                temp = new StringBuilder(word);
                temp.insert(i, (char) (j + 97));
                String newWord = temp.toString();
                if (fileWrite)
                    writeFile.println("Insertion string: " + newWord);
                if (this.frequency(newWord) > this.frequency(maxFreq))
                    maxFreq = newWord;
                alternateCount++;
            }
        }
        //finish file and close writer
        if (fileWrite) {
            writeFile.println("Created " + (i * j) + " insertion alternatives");
            writeFile.println();
            writeFile.println("TOTAL: generated " + alternateCount + " alternative spellings!");
            writeFile.close();
        }
        return maxFreq;
    }

    /**
     * Hashes new words into the dictionary
     * @param newName - new word
     * @param newFreq - frequency of new word
     */
    private void add(String newName, int newFreq) {
        //no duplicates
        if(contains(newName))
            return;
        //hash new word into chaining hash table
        int listIndex = Math.abs(newName.hashCode() % dictionary.length);
        if (dictionary[listIndex] == null)
            dictionary[listIndex] = new Node(newName, newFreq);
        else {
            Node current = dictionary[listIndex];
            while (current.next != null)
                current = current.next;
            current.next = new Node(newName, newFreq);
        }
    }

    /**
     * Gets the frequency of the passed word
     * @param word - word to check
     * @return - frequency of word
     */
    private int frequency(String word) {
        int listIndex = Math.abs(word.hashCode() % dictionary.length);
        Node current = dictionary[listIndex];

        while (current != null) {
            if (current.getName().equals(word))
                return current.getFrequency();
            current = current.next;
        }
        return 0;
    }

    /**
     * Checks if the passed word is contained in the dictionary.
     * @param word - word to check
     * @return - boolean, whether the word is present or not
     */
    private boolean contains(String word) {
        int listIndex = Math.abs(word.hashCode() % dictionary.length);
        Node current = dictionary[listIndex];

        //While there are words to check, compare current word against passed
        while (current != null) {
            //if equal, it exists, exit; else false
            if (current.getName().equals(word))
                return true;
            current = current.next;
        }
        return false;
    }

    /**
     * Wrapper class for dictionary entries, consisting of the word and its frequency
     */
    private class Node {
        String name;
        int frequency;
        Node next;

        /**
         * Constructor for Node
         * @param _name - name of entry
         * @param _frequency - frequency of entry
         */
        public Node(String _name, int _frequency) {
            name = _name;
            frequency = _frequency;
        }

        /**
         *
         * @return - name of this word
         */
        public String getName() {
            return name;
        }

        /**
         *
         * @return - frequency of this word
         */
        public int getFrequency() {
            return frequency;
        }
    }

    /**
     * Returns the first prime integer greater than or equal to the passed integer
     */
    public static int nextPrime(int number) {
        // copy the number so we don't affect the parameter
        int n = number;

        // make n odd if it's not
        if (n % 2 == 0)
            n++;

        // increase n until a prime number is found
        while (! isPrime(n))
            n += 2;

        return n;
    }

    /**
     * Returns true iff the passed int is prime
     */
    public static boolean isPrime(int number) {
        // test each odd integer smaller than sqrt(number) to see if it's a factor
        for (int test = 3; test < Math.sqrt(number) + 1; test += 2)
            if (number % test == 0)
                return false;

        // if no factor was found the number is prime
        return true;
    }
}
