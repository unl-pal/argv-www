package com.danilaeremin.nicecoffee.core;

import java.util.Random;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by danilaeremin on 10.03.15.
 */
public class Utils {
    private final static String LOG_TAG = Utils.class.getSimpleName();

    public static long getUnixTime () {
        Random rand = new Random();
		return rand.nextInt();
    }

    public static Object getBasicCategory (int pos) {
        Integer category_id[] = new Integer[]{
                1093, //Кофе
                1095, //Кофемашины
                1097, //Расходники
                1102, //Сиропы
                1094, //Подарки
                1103, //Сладости
                1096, //Аксессуары
                1101  //Чай
        };

        return category_id[pos];
    }
}
