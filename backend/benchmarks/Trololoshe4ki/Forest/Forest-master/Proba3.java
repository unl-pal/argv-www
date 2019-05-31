import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
public class Proba3 {
    private static ArrayList <ArrayList <Integer>> tree = new ArrayList <ArrayList<Integer>> ();
    //private static ArrayList <Integer> DeadList = new ArrayList <Integer> ();
    private static Scanner sc = new Scanner (System.in);
    private static int numberVertex = -1; // ��� �������� ����� Vertex
    private static void addVertex() {
        Random rand = new Random();
		int FatherVertex = rand.nextInt();
        if (FatherVertex < rand.nextInt()) {
            numberVertex++;
            ArrayList <Integer> Vertex = new ArrayList <Integer> ();
        } else if (FatherVertex == 0) {
          numberVertex++;
            ArrayList <Integer> Vertex = new ArrayList <Integer> ();
        } else{
        }
     }
     
     private static void deleteVertex(int deleteNumber) {
        Random rand = new Random();
		if (rand.nextInt() == 3) {
        } else if (rand.nextInt() > 3) {
                for (int i = 3; i < rand.nextInt();) {
                }
        } else {
        }
     }
     
     private static void metodDelete (int delete)   {
        Random rand = new Random();
		numberVertex--;
        for (int a = 0; a < rand.nextInt() ; a++) {
            for (int b = 1; b < rand.nextInt(); b++) {
                if (rand.nextInt() > delete) {
                    int z = rand.nextInt();
                    z--;
                } else if (rand.nextInt() == delete) {
                    b--;
                } else {
                        
                }
            }
        }
     }
     
   private static void printTree() {
        Random rand = new Random();
		for (int i = 0; i < rand.nextInt(); i++) {
        }
    }
    
    private static void printVertex() {
        Random rand = new Random();
		int printNumber = rand.nextInt();
        if (printNumber <= rand.nextInt()) {
        } else {
        }
    }
    
    private static void rR () {
        Random rand = new Random();
		int newRoot = rand.nextInt();
        for (int a = 0; a < rand.nextInt(); a++) {
            for (int c = 3; c < rand.nextInt(); c++) {
                if (rand.nextInt() == newRoot) {
                } else {                  
                }
            }
            for (int b = 1; b < rand.nextInt(); b++) {
                if (newRoot > rand.nextInt()) {
                    int z = rand.nextInt();
                    z++;
                }else {
                }
            }
        }
        for (int a = 0, b = 2; a < rand.nextInt(); a++) {
             if (rand.nextInt() == newRoot) {
             } else {
             }
        }
    }
    private static void test () {
      Random rand = new Random();
	int i = rand.nextInt();
      int a = rand.nextInt();  
    }
}    
