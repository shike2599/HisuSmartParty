package com.hisu.smart.dj.ui.web.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CookieEntity;
import com.hisu.smart.dj.entity.NewsInfoResponse;
import com.hisu.smart.dj.ui.news.contract.NewsInfoContract;
import com.hisu.smart.dj.ui.news.model.NewsInfoModel;
import com.hisu.smart.dj.ui.news.presenter.NewInfoPresenter;
import com.hisu.smart.dj.utils.X5WebView;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonwidget.LoadingDialog;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

import java.util.List;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class WebActivity extends BaseActivity<NewInfoPresenter,NewsInfoModel>
        implements View.OnClickListener,NewsInfoContract.View{
    private String TAG = "WebActivity";
    @Bind(R.id.title_TextView)
    TextView title_textView;
    @Bind(R.id.back_imageView)
    ImageView back_img;

    @Bind(R.id.webView_FrameLayout)
    ViewGroup mViewParent;
    @Bind(R.id.show_news_title_layout)
    LinearLayout show_news_layout;
    @Bind(R.id.news_title_TextView)
    TextView show_news_title;
    @Bind(R.id.news_time_textView)
    TextView show_news_time;
    @Bind(R.id.news_collection_imageView)
    ImageView collection_img;
    @Bind(R.id.collection_TextView)
    TextView news_collection_textView;
    private X5WebView x5WebView;
    private String title_str;
    private String webUrl;
    private Integer newsID;
    private boolean isNeedSign = false;
    private String jump_tag;
    @Override
    public int getLayoutId() {
        return R.layout.activity_web;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
        x5WebView = new X5WebView(this,null);
        newsID = getIntent().getIntExtra("NEWSID",-1);
        Log.d("WebActivity","newsID==="+newsID);
        if(newsID == -1){
            title_str = getIntent().getStringExtra("TITLE");
            webUrl = getIntent().getStringExtra("URL");
        }else{
            jump_tag = getIntent().getStringExtra("TAG");
        }

    }

    @Override
    public void initView() {
       initWebView();
       back_img.setOnClickListener(this);
       news_collection_textView.setOnClickListener(this);

       if(newsID!=-1){
           title_textView.setVisibility(View.INVISIBLE);
           if(jump_tag!=null){
               if(jump_tag.equals("践行")){
                   mPresenter.getFollowInfoDataRequest(newsID);
               }else if(jump_tag.equals("三会一课")){
                   mPresenter.getTopicInfoDataRequest(newsID);
               }
           }else{
               mPresenter.getNewsInfoDataRequest(newsID);
           }


       }else{
           show_news_layout.setVisibility(View.GONE);
           title_textView.setText(title_str);
           startLoad(false,webUrl);
       }
    }

    private void initWebView() {
        setCookie(null);
        mViewParent.addView(x5WebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        x5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG,"----onPageFinished----");
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
//                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
//                if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
//                    changGoForwardButton(view);
                /* mWebView.showLog("test Log"); */
            }
        });

        x5WebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view,
                                         IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }
        });
        x5WebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                TbsLog.d(TAG, "url: " + arg0);
                new AlertDialog.Builder(WebActivity.this)
                        .setTitle("allow to download？")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                WebActivity.this,
                                                "fake message: i'll download...",
                                                1000).show();
                                    }
                                })
                        .setNegativeButton("no",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                WebActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                WebActivity.this,
                                                "fake message: refuse download...",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });

        WebSettings webSetting = x5WebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
//        long time = System.currentTimeMillis();
//        if (webUrl == null) {
//            x5WebView.loadUrl(webUrl);
//        } else {
//            x5WebView.loadUrl(webUrl.toString());
//        }
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:
                if (x5WebView != null && x5WebView.canGoBack()){
                    x5WebView.goBack();
                }else{
                    WebActivity.this.finish();
                }
                break;
                //收藏
            case R.id.collection_TextView:
                if(isNeedSign){
                    collection_img.setBackgroundResource(R.mipmap.links_icon);
                    news_collection_textView.setText("收藏");
                    isNeedSign = false;
                }else{
                    collection_img.setBackgroundResource(R.mipmap.pre_likes);
                    news_collection_textView.setText("取消收藏");
                    isNeedSign = true;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
//        if (mTestHandler != null)
//            mTestHandler.removeCallbacksAndMessages(null);
        if (x5WebView != null)
            x5WebView.destroy();
        super.onDestroy();
    }

    public static void startAction(Activity activity,String title,String url){
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("TITLE",title);
        intent.putExtra("URL",url);
        activity.startActivity(intent);
    }
    public static void startAction(Activity activity,int id){
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("NEWSID",id);
        activity.startActivity(intent);
    }

    public static void startAction(Activity activity,int id,String tag){
        Intent intent = new Intent(activity, WebActivity.class);
        intent.putExtra("NEWSID",id);
        intent.putExtra("TAG",tag);
        activity.startActivity(intent);
    }
    /**
     * 监听全屏视频时返回键
     */
    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    /**
     * 监听返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(x5WebView != null && x5WebView.canGoBack()){
                x5WebView.goBack();
             }else{
                WebActivity.this.finish();
            }
             return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void returnNewsInfoData(NewsInfoResponse newsInfoResponse) {
        Log.d("WebActivity","newsInfoResponse---=="+newsInfoResponse);
        if(newsInfoResponse.getData()!=null){
            NewsInfoResponse.DataBean dataBean = newsInfoResponse.getData();
            String news_time = dataBean.getPublishTime();
            show_news_time.setText(news_time);
            String title = dataBean.getName();
            show_news_title.setText(title);
            isNeedSign = dataBean.isIsNeedSign();
            if(isNeedSign){
                collection_img.setBackgroundResource(R.mipmap.pre_likes);
                news_collection_textView.setText("取消收藏");
            }else{
                collection_img.setBackgroundResource(R.mipmap.links_icon);
                news_collection_textView.setText("收藏");
            }
            String webData = dataBean.getContent();
            startLoad(true,webData);
        }
    }

    @Override
    public void showLoading(String tag) {
        LoadingDialog.showDialogForLoading(this,"请稍候！",false);
    }

    @Override
    public void stopLoading(String tag) {
        Log.d("WebActivity","---stopLoading---==");
      LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        Log.d("WebActivity","---showErrorTip---=="+msg);
       LoadingDialog.cancelDialogForLoading();
       Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private void startLoad(boolean isNews,String webUrl){
        if(isNews){
            String webhtml = getHtmlData(webUrl);
            x5WebView.loadData(webhtml,"text/html;charset=utf-8","utf-8");
        }else{
            x5WebView.loadUrl(webUrl);
        }
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
    }

    private String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{background-color: #f2f1ef;"+
                " padding: 0.7rem;" +
                "font-size: 14px;" +
                " margin-top: 0;" +
                " margin-bottom: 10px;" +
                " font-family:'微软雅黑';" +
                " color: #666666}"+
                "p{padding:0px;margin:0px;margin-bottom:10px;font-size:18px;color:#222222;line-height:1.5rem;text-indent:1.2rem} img{padding:0px;max-width:100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
    //cookie保存用户信息
    void setCookie(List<CookieEntity> list) {
        StringBuilder sb = new StringBuilder();
        AppConfig appConfig = AppConfig.getInstance();
        sb.append("phone="+ appConfig.getString(AppConstant.MEMBER_PHONE,"")+";");
        sb.append("isPartyMember="+ appConfig.getBoolean(AppConstant.IS_PARTY_MEMBER,false)+";");
        sb.append("isPartyBranch="+ appConfig.getBoolean(AppConstant.IS_PARTY_BRANCH,false)+";");
        sb.append("nickname="+ appConfig.getString(AppConstant.NICK_NAME,"")+";");
        sb.append("userId="+ appConfig.getInt(AppConstant.USER_ID,-1)+";");
        sb.append("isPartyCommittee="+ appConfig.getBoolean(AppConstant.IS_PARTY_COMMITTEE,false)+";");
        sb.append("userName="+ appConfig.getString(AppConstant.USER_NAME,"")+";");

        sb.append("id="+ appConfig.getInt(AppConstant.MEMBER_ID,-1)+";");
        sb.append("name="+ appConfig.getString(AppConstant.MEMBER_NAME,"")+";");
        sb.append("code="+ appConfig.getString(AppConstant.MEMBER_CODE,"")+";");
        sb.append("idCard="+ appConfig.getString(AppConstant.MEMBER_IDCARD,"")+";");
        sb.append("sex="+ appConfig.getInt(AppConstant.MEMBER_SEX,-1)+";");
        sb.append("partyBranchId="+ appConfig.getInt(AppConstant.MEMBER_PARTYBRANCH_ID,-1)+";");
        sb.append("status="+ appConfig.getInt(AppConstant.MEMBER_STATUS,-1)+";");
        sb.append("integral="+ appConfig.getInt(AppConstant.MEMBER_INTEGRAL,-1)+";");
        if(list!=null&&list.size()>0){
            for(int i = 0;i < list.size(); i++){
                CookieEntity cookieEntity = list.get(i);
                sb.append(cookieEntity.getCookieKey()+"="+cookieEntity.getCookieValue()+";");
            }
        }
        String stringCookie = (sb.deleteCharAt(sb.length()-1)).toString();
        Log.d("WebActivity","stringCookie----"+stringCookie);
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.removeSessionCookies(null);
            cookieManager.flush();
        } else {
            cookieManager.removeSessionCookie();
            CookieSyncManager.getInstance().sync();
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(webUrl, stringCookie);
    }

}
