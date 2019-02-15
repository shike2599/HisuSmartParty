package com.hisu.smart.dj.ui.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;

public class SystemScript {
    String TAG = "SystemScript";
    private Activity webAc;
    AppConfig appConfig;
    public SystemScript(Activity activity){
        appConfig = AppConfig.getInstance();
        webAc = activity;
    }
    //获取本地存储信息 int
    @JavascriptInterface
    public int getUserInfoInt(String key) {
        Log.d(TAG,"---key---"+key);
        int obj = 0;
        obj =  appConfig.getInt(key,-1);
        Log.d(TAG,"---obj---"+obj);
        return obj;
    }

    //获取本地存储信息 String
    @JavascriptInterface
    public String getUserInfoStr(String key) {
        Log.d(TAG,"---key---"+key);
        String obj = null;
        obj = appConfig.getString(key,"");
        Log.d(TAG,"---obj---"+obj);
        return obj;
    }

    //获取本地存储信息 boolean
    @JavascriptInterface
    public boolean getUserInfoBoolean(String key) {
        Log.d(TAG,"---key---"+key);
        boolean obj = false;
        obj = appConfig.getBoolean(key,false);
        Log.d(TAG,"---obj---"+obj);
        return obj;
    }

    //退出WebActivity
    @JavascriptInterface
    public void finshWebView() {
        webAc.finish();
    }
}

