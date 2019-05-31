package com.company.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Generates image for captcha with special code.
 * 
 * @author Ivan_Tymchenko
 *
 */
public class ImageGenerator {
	
	public static BufferedImage createImage(int number){
		BufferedImage image = new BufferedImage(200, 40, BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D graphics = image.createGraphics();
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 200, 40);
 
        GradientPaint gradientPaint = new GradientPaint(10, 5, Color.GREEN, 20, 10, Color.LIGHT_GRAY, true);
        graphics.setPaint(gradientPaint);
        Font font = new Font("Comic Sans MS", Font.BOLD, 30);
        graphics.setFont(font);
 
        graphics.drawString(String.valueOf(number), 5, 30);
        graphics.dispose();
        
        return image;
	}

}
