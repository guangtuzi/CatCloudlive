package com.catlivevideo.living.catlivevideo.utlis;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pan on 2016/10/7.
 */

public class PreferenceUtil {
    public static void putString(String pkgName,String key,String param,Context context){
        SharedPreferences account = context.getSharedPreferences(pkgName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = account.edit();
        editor.putString(key,param);
        editor.commit();
    }
    public static String getString(String pkgName,String key,String defaultParam,Context context){
        SharedPreferences account = context.getSharedPreferences(pkgName, Context.MODE_PRIVATE);
        return account.getString(key,defaultParam);
    }
    public static void putBoolean(String pkgName,String key,Boolean param,Context context){
        SharedPreferences account = context.getSharedPreferences(pkgName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = account.edit();
        editor.putBoolean(key,param);
        editor.commit();
    }
    public static Boolean getBoolean(String pkgName,String key,Boolean defaultParam,Context context){
        SharedPreferences account = context.getSharedPreferences(pkgName, Context.MODE_PRIVATE);
        return account.getBoolean(key,defaultParam);
    }
}
