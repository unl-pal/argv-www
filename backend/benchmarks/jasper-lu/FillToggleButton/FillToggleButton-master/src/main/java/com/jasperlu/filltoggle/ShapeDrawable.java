package com.jasperlu.filltoggle;

/**
 * Created by Jasper on 3/31/2015.
 */
public abstract class ShapeDrawable{
    public static final int RECT = 0;
    public static final int CIRCLE = 0;

    protected int fillColor;
    protected int borderColor;

    protected int mShape;

    public ShapeDrawable(int shape, int fillColor) {
        mShape = shape;
        this.fillColor = fillColor;
        this.borderColor = fillColor;
    }

    public ShapeDrawable(int shape, int fillColor, int borderColor) {
        mShape = shape;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }


    public void setBorderColor(int color) {
        borderColor = color;
    }

    public void setFillColor(int color) {
        fillColor = color;
    }

    public void setAlpha(int alpha) {

    }

    public int getOpacity() {
        return 0;
    }
}
