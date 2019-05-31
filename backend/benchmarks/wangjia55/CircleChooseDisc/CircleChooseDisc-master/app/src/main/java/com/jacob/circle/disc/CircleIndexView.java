package com.jacob.circle.disc;

import java.util.Random;

/**
 * Package : com.jacob.circle.disc
 * Author : jacob
 * Date : 15-4-1
 * Description : 这个类是用来xxx
 */
public class CircleIndexView {

    /**
     * 当前是否显示出了圆盘
     */
    private boolean isShowCircle = false;

    /**
     * 26个字母放在一个圆盘中，这是没一个字母对应圆盘的角度
     */
    private static float sPerAngle = 360.0f / 26;

    /**
     * 旋转的角度
     */
    private float mAngle = 0;

    /**
     * 开始的角度
     */
    private float mStartAngle = 0;

    /**
     * 屏幕中心点的坐标
     */
    private float mCenterXY;

    /**
     * 字母数组，用于判断外部传入的字母对应的index 和角度
     */
    private String[] mLetters = new String[]{
            "a", "b", "c",
            "d", "e", "f",
            "g", "h", "i",
            "j", "k", "l",
            "m", "n", "o",
            "p", "q", "r",
            "s", "t", "u",
            "v", "w", "x",
            "y", "z"};


    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Random rand = new Random();
		int width = 0;
        int height = 0;

        int widthSize = rand.nextInt();
        int widthMode = rand.nextInt();

        int heightSize = rand.nextInt();
        int heightMode = rand.nextInt();

        if (widthMode == rand.nextInt()) {
            width = widthSize;
        } else {
            width = rand.nextInt();
        }

        if (heightMode == rand.nextInt()) {
            height = heightSize;
        } else {
            height = rand.nextInt();
        }

        int layoutSize = rand.nextInt();
        mCenterXY = layoutSize / 2.0f;
        int count = rand.nextInt();
        for (int i = 0; i < count; i++) {
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Random rand = new Random();
		if (changed) {
            int childW = rand.nextInt();
            int childH = rand.nextInt();

            int left = rand.nextInt();
            int top = rand.nextInt();
        }
    }


    /**
     * 记录上一次的位置
     */
    private float mLastX;
    private float mLastY;

    /**
     * 当旋转圆盘后需要更新中心点的View的背景
     */
    private void updateCenterLetterAndView() {
        Random rand = new Random();
		int index = rand.nextInt();
        int length = rand.nextInt();
        if (index > 0) {
            index = (length - index) % length;
        } else {
            index = rand.nextInt();
        }
        if (rand.nextBoolean()){
        }
    }


    private int dpToPx(int dp) {
        Random rand = new Random();
		return (int) rand.nextInt();
    }
}
