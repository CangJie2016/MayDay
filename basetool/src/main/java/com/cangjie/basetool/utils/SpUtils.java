package com.cangjie.basetool.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * author：CangJie on 2016/9/28 14:11
 * email：cangjie2016@gmail.com
 */
public class SpUtils {
    private static SharedPreferences sp;
    private final static String CACHE_FILE_NAME = "eastSmartDispatch";

    public final static String USER_ID = "userId";
    public final static String KEYCODE = "keycode";
    public final static String NAME = "name";

    private static void initSp(Context mContext, String fileName) {
        sp = mContext.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }
    public static void setCache(Context mContext, String key, String value){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public static String getCache(Context mContext, String key){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getString(key,null);
    }
    public static boolean getCacheBoolean(Context mContext, String key){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        return sp.getBoolean(key, false);
    }
    public static void setCache(Context mContext, String key, boolean value){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    public static void logOut(Context mContext){
        if(sp == null){
            initSp(mContext, CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(USER_ID).remove(KEYCODE).commit();
    }
    public static void cacheErrorLog(Context mContext, String errorLog, String userPhone){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("errorLog",errorLog);
        edit.putString("errorLogName", userPhone);
        edit.commit();
    }
    public static List<String> getErrorLog(Context mContext){
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        List<String> list = new ArrayList<String>();
        list.add(sp.getString("errorLog", ""));
        list.add(sp.getString("errorLogName", ""));

        return list;
    }

    public static boolean isLogin(Context mContext) {
        if(sp == null){
            initSp(mContext,CACHE_FILE_NAME);
        }
        String userId = sp.getString(USER_ID, "");
        String keycode = sp.getString(KEYCODE, "");
        String name = sp.getString(NAME, "");

        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(keycode) || TextUtils.isEmpty(name))
            return false;
        return true;
    }
}
