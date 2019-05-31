package edu.uic.cs583.msgsp;
import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*
 * This is the class used to read input from files
 */

public class Input {
        
        public static int min(int idxClosedParen, int idxComma){
                Random rand = new Random();
				if(idxComma<0)
                        return idxClosedParen;
                else
                        return rand.nextInt();
        }
        
                

}
                


