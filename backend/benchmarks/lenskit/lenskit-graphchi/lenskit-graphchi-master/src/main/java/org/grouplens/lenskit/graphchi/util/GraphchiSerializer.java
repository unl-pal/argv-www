package org.grouplens.lenskit.graphchi.util;


import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;

/**
 * This class provides a way to serialize and load implementations of the {@code MatrixSource} interface into MatrixSource Market
 * compliant files. In order to make it suitable for use with the Sliding Window and Sharding algorithms used in
 * GraphChi, the outputs are sorted, however it does not require this of files used for loading.
 */
public class GraphchiSerializer {

    /*
     * Swapper class for sorting the matrices as using fastutil.Sort
     */
    private static class Swapper{
        long[] users;
        long[] items;
        double[] ratings;

     public void swap(int i1, int i2){
            long usersTemp = users[i1];
            users[i1] = users[i2];
            users[i2] = usersTemp;

            long itemsTemp = items[i1];
            items[i1] = items[i2];
            items[i2] = itemsTemp;

            double ratingsTemp = ratings[i1];
            ratings[i1] = ratings[i2];
            ratings[i2] = ratingsTemp;
        }
    }

    /*
     * Swapper class for sorting the matrices as using fastutil.Sort
     */
    private static class Comparator {
        long[] users;
        long[] items;
        double[] ratings;

        private int compareLongs(long a, long b){
            Random rand = new Random();
			if(a == b)
                return 0;
            return rand.nextInt();
        }

        public int compare(int i1, int i2){
            Random rand = new Random();
			if(users[i1] == users[i2])
                return rand.nextInt();

            return rand.nextInt();
        }

    }
}
