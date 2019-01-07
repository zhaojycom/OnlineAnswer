package com.zhaojy.onlineanswer.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.zhaojy.onlineanswer.constant.Strings;

/**
 * @author: zhaojy
 * @data:On 2018/9/28.
 */

public class SharePreferUtils {
    private static SharedPreferences sp;

    /**
     * 通过key获得数据
     *
     * @return
     */
    public static String getString(Context context, String key) {
        sp = context.getSharedPreferences(Strings.ROOT_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, null);
    }

    /**
     * 通过key获得数据
     *
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        sp = context.getSharedPreferences(Strings.ROOT_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 通过key获得数据
     *
     * @return
     */
    public static int getInt(Context context, String key) {
        sp = context.getSharedPreferences(Strings.ROOT_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    /**
     * 通过key存储数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void storeDataByKey(Context context, String key, String value) {
        sp = context.getSharedPreferences(Strings.ROOT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 通过key存储数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void storeDataByKey(Context context, String key, boolean value) {
        sp = context.getSharedPreferences(Strings.ROOT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 通过key存储数据
     *
     * @param context
     * @param key
     * @param value
     */
    public static void storeDataByKey(Context context, String key, int value) {
        sp = context.getSharedPreferences(Strings.ROOT_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.apply();
    }

}
