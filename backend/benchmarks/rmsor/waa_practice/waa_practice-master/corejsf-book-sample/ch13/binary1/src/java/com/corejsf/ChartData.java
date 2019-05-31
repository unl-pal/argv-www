package com.corejsf;

import java.io.IOException;
import java.io.OutputStream;

public class ChartData {   
   private int width, height;
   private String title;
   private String[] names;
   private double[] values;

   private static final int DEFAULT_WIDTH = 200;
   private static final int DEFAULT_HEIGHT = 200;
 
   public ChartData() {
      width = DEFAULT_WIDTH;
      height = DEFAULT_HEIGHT;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public String getContentType() {
      return "image/png";
   }
}