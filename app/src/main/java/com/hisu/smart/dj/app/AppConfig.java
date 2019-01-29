package com.hisu.smart.dj.app;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author lichee
 * @date 2019/1/29
 */

public class AppConfig  {

    private static Context sContext;
    private static AppConfig sInstance;
    private SharedPreferences mPreferences;

    public static void init(Context context) {
        sContext = context;
        sInstance = new AppConfig(sContext);
    }

    public static synchronized AppConfig getInstance() {
        if(sInstance == null) {
            sInstance = new AppConfig(sContext);
        }

        return sInstance;
    }

    private AppConfig(Context context) {
        this.mPreferences = context.getSharedPreferences("settings", 0);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.mPreferences.getBoolean(key, defValue);
    }

    public void setBoolean(String key, boolean value) {
        this.mPreferences.edit().putBoolean(key, value).apply();
    }

    public long getLong(String key, long defValue) {
        return this.mPreferences.getLong(key, defValue);
    }

    public void setLong(String key, long defValue) {
        this.mPreferences.edit().putLong(key, defValue).apply();
    }

    public int getInt(String key, int defValue) {
        return this.mPreferences.getInt(key, defValue);
    }

    public void setInt(String key, int value) {
        this.mPreferences.edit().putInt(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return this.mPreferences.getString(key, defValue);
    }

    public void setString(String key, String value) {
        this.mPreferences.edit().putString(key, value).apply();
    }

}
