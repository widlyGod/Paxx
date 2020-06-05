package com.paxx.app.ui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Sharepreference 使用工具类
 */
public class ShareprefUtils {
    private static SharedPreferences sp;
    private static String SP_NAME = "config";

    public static void saveString(Context context, String key, String value) {
        if (null == sp) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }

        Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static void saveBoolea(Context context, String key, boolean value) {
        if (null == sp) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }

        Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.apply();
    }

    /**
     * 保存long型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putLong(key, value).apply();
    }

    /**
     * 保存int型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 保存float型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveFloat(Context context, String key, float value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putFloat(key, value).apply();
    }

    /**
     * 存储List<String>
     *
     * @param context
     * @param key     List<String>对应的key
     * @param strList 对应需要存储的List<String>
     */
    public static void putStrListValue(Context context, String key, List<String> strList) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        Gson gson = new Gson();
        //转换成json数据，再保存
        String strJson = gson.toJson(strList);
        sp.edit().putString(key, strJson).apply();
    }


    /**
     * 取出List<String>
     *
     * @param context
     * @param key     List<String> 对应的key
     * @return List<String>
     */
    public static List<String> getStrListValue(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        List<String> datalist = new ArrayList<>();
        String strJson = sp.getString(key, null);
        if (null == strJson) {
            return datalist;
        }
        Gson gson = new Gson();
        datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
        }.getType());
        return datalist;
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取long值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取boolean值
     *
     * @param context
     * @param key
     * @return
     */
    public static Boolean getBoolean(Context context, String key) {
        if (null == sp) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }

        return sp.getBoolean(key, false);
    }

    public static Boolean getBoolean(Context context, String key, boolean defBoolean) {
        if (null == sp) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }

        return sp.getBoolean(key, defBoolean);
    }


    /**
     * 获取String值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, defValue);
    }

    public static String getString(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getString(key, "");
    }

    /**
     * delete_icon all infos in SharedPreferenced file
     *
     * @param context
     */
    public static void clear(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().clear().apply();
    }

    public static Boolean saveMap(Context context, Map<String, String> map, String key) {

        boolean result;
        if (sp == null) {

            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        Editor editor = sp.edit();
        try {

            Gson gson = new Gson();
            String json = gson.toJson(map);
            editor.putString(key, json);
            result = true;
        } catch (Exception e) {
            result = false;
            e.printStackTrace();
        }
        editor.apply();
        return result;
    }

    public static Map<String, Object> getMap(Context context, String key) {

        if (sp == null) {

            sp = context.getSharedPreferences(SP_NAME, 0);
        }
        String json = sp.getString(key, "");
        Map<String,Object> mapNew = new Gson().fromJson(json, new TypeToken<HashMap<String,Object>>(){}.getType());
        if (mapNew == null) {

            return new HashMap<String,Object>();
        }
        return mapNew;
    }
}

//    public static void saveLocalGuideIconHeight(Context mContext, int heihgt) {
//        saveInt(mContext, AppKey.LOCALGUIDEICONHEIGHT, heihgt);
//    }
//
//    public static int getLocalGuideIconHeight(Context mContext) {
//        return getInt(mContext, AppKey.LOCALGUIDEICONHEIGHT, DensityUtil.dip2px(mContext, 1));
//    }
//
//    public static void savePoiMapIconHeight(Context mContext, int heihgt) {
//        saveInt(mContext, AppKey.POIMAPICONHEIGHT, heihgt);
//    }
//
//    public static int getPoiMapIconHeight(Context mContext) {
//        return getInt(mContext, AppKey.POIMAPICONHEIGHT, DensityUtil.dip2px(mContext, 1));
//    }
