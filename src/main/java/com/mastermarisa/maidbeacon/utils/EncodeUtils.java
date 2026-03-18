package com.mastermarisa.maidbeacon.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EncodeUtils {
    private static final Gson GSON;
    private static final String[] THOUSANDS;
    private static final String[] HUNDREDS;
    private static final String[] TENS;
    private static final String[] UNITS;

    public static String toRoman(int num) {
        if (num < 1 || num > 3999) {
            return "Invalid";
        }

        return THOUSANDS[num / 1000] +
                HUNDREDS[(num % 1000) / 100] +
                TENS[(num % 100) / 10] +
                UNITS[num % 10];
    }

    public static <T> String toJson(T obj){
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<? extends T> type){
        return GSON.fromJson(json, type);
    }

    static {
        GSON = new GsonBuilder().setPrettyPrinting().create();
        THOUSANDS = new String[]{"", "M", "MM", "MMM"};
        HUNDREDS = new String[]{"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        TENS = new String[]{"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        UNITS = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
    }
}
