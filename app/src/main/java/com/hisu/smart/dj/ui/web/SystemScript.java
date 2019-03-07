package com.hisu.smart.dj.ui.web;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.study.activity.StudyExperienceActivity;
import com.hisu.smart.dj.utils.X5WebView;

public class SystemScript {
    String TAG = "SystemScript";
    private Activity webAc;
    AppConfig appConfig;
    private X5WebView x5;
    public SystemScript(Activity activity){
        appConfig = AppConfig.getInstance();
        webAc = activity;
    }
    public SystemScript(Activity activity,X5WebView webView){
        appConfig = AppConfig.getInstance();
        webAc = activity;
        x5 = webView;
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

    //存储支部ID int
    @JavascriptInterface
    public void setPartyBranchId(int partyBranchId) {
        Log.d(TAG,"---setPartyBranchId--id==="+partyBranchId);
        AppConfig.getInstance().setInt(AppConstant.MEMBER_PARTYBRANCH_ID,partyBranchId);
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

    /**
     * 调用拨号界面
     * @param phone 电话号码
     */
    @JavascriptInterface
    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        webAc.startActivity(intent);
    }

    /**
     * 跳转到学习心得上传页面
     * @param cateCode 分类ID
     */
    @JavascriptInterface
    public void toUpLoadActivity(int cateCode) {
        Log.d(TAG,"---toUpLoadActivity---cateCode==="+cateCode);
        StudyExperienceActivity.startAction(webAc,"党员圈",cateCode);
    }


    @JavascriptInterface
    public void resize(final float height) {
        webAc.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                Toast.makeText(webAc, height + "", Toast.LENGTH_LONG).show();
                Log.i(TAG,"resize=================="+height);
                //此处的 layoutParmas 需要根据父控件类型进行区分，这里为了简单就不这么做了
                x5.setLayoutParams(new FrameLayout.LayoutParams(webAc.getResources().getDisplayMetrics().widthPixels, (int) (height * webAc.getResources().getDisplayMetrics().density)));
            }
        });
    }

}

