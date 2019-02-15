package com.hisu.smart.dj.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author lichee
 * @date 2019/1/29
 */

public class AppConfig  {

    private static Context sContext;
    private static AppConfig sInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences mPreferences_user; //保存多组密码的SharedPreferences

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
        this.mPreferences_user = context.getSharedPreferences("session", 0);
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
    //取账号密码
    public String getUserNameAndPassWordString(String key, String defValue) {
        return this.mPreferences_user.getString(key, defValue);
    }
    //保存多组账号和密码
    public void setUserNameAndPassWordString(String key, String value) {
        this.mPreferences_user.edit().putString(key, value).apply();
    }
    public void clearAll() {

        SharedPreferences.Editor editor = this.mPreferences.edit();
        editor.clear();
        editor.apply();
    }
    //获取所有的账号密码
    public List<String> getAllData() {
        Map<String,String> map = (Map<String, String>)this.mPreferences_user.getAll();
        Log.d("AppConfig","map---size()==="+map.size());
        Log.d("AppConfig","map---value)==="+map.toString());

        List<String> list = new ArrayList<>();

        if(map!=null && map.size()>0){
            //去掉两边{}
            String name_pass = map.toString().substring(1,map.toString().length()-1);
            String[] userArr = null;
            //是否是多个数据
            if(name_pass.contains(", ")){
                userArr = name_pass.split(", ");
            }else{
                userArr = new String[1];
                userArr[0] = name_pass;
            }
            for (int i = 0; i < userArr.length; i++) {
                String username[] = userArr[i].split("=");
                Log.d("AppConfig","username[0]==="+username[0]);
                list.add(username[0]);
            }
            return  list;
        }else{
            return null;
        }
    }

    public void deleteThreadData(String key) {
        this.mPreferences_user.edit().remove(key).apply();
    }
}
