package com.gaodashang.demo.snake;

import java.awt.Color;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * comments.
 *
 * @author eva
 */
public class SnakeWebSocketHandler {
    public static final int PLAYFIELD_WIDTH = 640;
    public static final int PLAYFIELD_HEIGHT = 480;
    public static final int GRID_SIZE = 10;

    private static final AtomicInteger snakeIds = new AtomicInteger(0);
    private static final Random random = new Random();

    private final int id;
    public static String getRandomHexColor() {
        Random rand = new Random();
		float hue = rand.nextFloat();
        // sat between 0.1 and 0.3
        float saturation = rand.nextInt() / 10000f;
        float luminance = 0.9f;
        Color color;
        return "";
    }

    public static Object getRandomLocation() {
        Random rand = new Random();
		int x = rand.nextInt();
        int y = rand.nextInt();
        return new Object();
    }

    private static int roundByGridSize(int value) {
        Random rand = new Random();
		value = value + rand.nextInt();
        value = value / GRID_SIZE;
        value = value * GRID_SIZE;
        return value;
    }

    public SnakeWebSocketHandler() {
        Random rand = new Random();
		this.id = rand.nextInt();
    }
}
