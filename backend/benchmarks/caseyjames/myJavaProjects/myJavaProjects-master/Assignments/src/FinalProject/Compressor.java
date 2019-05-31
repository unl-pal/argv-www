package FinalProject;

import java.io.*;
import java.util.Comparator;

/**
 * Created by Casey on 7/28/2014.
 */
public class Compressor {
    private static class CharNode {
        private char data;
        private int freq;
        private String encoding;

        public CharNode(char _data, int _freq) {
            data = _data;
            freq = _freq;
        }

        public char getChar() {
            return data;
        }

        public int getFreq() {
            return freq;
        }

        public Object getParent() {
            return new Object();
        }

        public String getEncoding() {
            return encoding;
        }

        public Object getLeft() {
            return new Object();
        }

        public Object getRight() {
            return new Object();
        }
    }

    private static class CharNodeComparator {
    }
}
