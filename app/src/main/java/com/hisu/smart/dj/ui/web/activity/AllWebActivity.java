package com.hisu.smart.dj.ui.web.activity;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.web.SystemScript;
import com.jaydenxiao.common.base.BaseActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class AllWebActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.title_TextView)
    TextView title_textView;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.title_LinearLayout)
    LinearLayout title_layout;

    @Bind(R.id.webView_FrameLayout)
    WebView centWebView;

    private String title_str;
    private String webUrl;
    @Override
    public int getLayoutId() {
        return R.layout.activity_all_web;
    }

    @Override
    public void initPresenter() {
        title_str = getIntent().getStringExtra("TITLE");
        webUrl = getIntent().getStringExtra("URL");
    }

    @Override
    public void initView() {
        back_img.setOnClickListener(this);
        title_textView.setText(title_str);
        centWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        centWebView.addJavascriptInterface(new SystemScript(AllWebActivity.this,centWebView), "System");
        WebSettings webSetting = centWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setLoadsImagesAutomatically(true);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);

        startLoad(webUrl);//开始加载网页
        }

    public static void startAction(Activity activity, String title, String url) {
        Intent intent = new Intent(activity, AllWebActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("URL", url);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_imageView:
                if (centWebView != null && centWebView.canGoBack()) {
                    centWebView.goBack();
                } else {
                    AllWebActivity.this.finish();
                }
                break;
        }
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

    private void startLoad(String webUrl){
        String onlineShoping = AppConstant.BASE_URL_LOAD+"convenient/fresh.html";
        String targeturl = AppConstant.BASE_URL_LOAD + "partyBuild/targetOrganazition.html";
        if(webUrl.equals(onlineShoping) || webUrl.equals(targeturl)){
            title_layout.setVisibility(View.GONE);
        }
        centWebView.loadUrl(webUrl);
    }
}
