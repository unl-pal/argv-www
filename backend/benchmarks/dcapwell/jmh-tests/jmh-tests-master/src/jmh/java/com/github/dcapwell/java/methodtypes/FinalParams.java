package com.github.dcapwell.java.methodtypes;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Shows that final params have no effect on runtime performance
 */
public class FinalParams {
    private static final int left = ThreadLocalRandom.current().nextInt();
    private static final int right = ThreadLocalRandom.current().nextInt();

    public int doNothing() {
        // if below tests equal this one, then test bug
        return 4;
    }

    public int nonFinal() {
        Random rand = new Random();
		return rand.nextInt();
    }

    public int withFinal() {
        Random rand = new Random();
		return rand.nextInt();
    }

    private static int add(int left, int right) {
        return left + right;
    }

    private static int addFinal(final int left, final int right) {
        return left + right;
    }
}
