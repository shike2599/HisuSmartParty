package com.hisu.smart.dj.ui.main.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hisu.smart.dj.BuildConfig;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.ui.login.activity.LoginActivity;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private Banner welBanner;
    private List<Integer> welBannerImages;
    private int[] Images = {
            R.mipmap.weleocme_img_3,R.mipmap.weleocme_img_2,
            R.mipmap.weleocme_img_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
//                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);
        welBanner = findViewById(R.id.welcome_banner);
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);

        //初始化sp存储
        AppConfig.init(this);

        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        initData();
        welBanner.setImageLoader(new GlideImageLoader());
        welBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        welBanner.setImages(welBannerImages);
        welBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
//        homeBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        welBanner.isAutoPlay(true);
        welBanner.setDelayTime(1500);
        welBanner.setIndicatorGravity(BannerConfig.CENTER);
        welBanner.start();
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(),  cb);
        new Thread(){
            public void run(){
                try {
                    Thread.sleep(3000);
                    welBanner.stopAutoPlay();
                    Intent intent = new Intent();
                    LoginActivity.startAction(WelcomeActivity.this);
                    WelcomeActivity.this.finish();
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void initData() {
        welBannerImages = new ArrayList<>();
        for(int i = 0; i < Images.length; i++){
            welBannerImages.add(Images[i]);
        }
    }

    //Banner  重写图片加载器 网络图片需要重写
    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //Glide 加载图片简单用法
//            Glide.with(context).load(path).into(imageView);
//            //用fresco加载图片简单用法，记得要写下面的createImageView方法
//            Uri uri = Uri.parse((String) path);
//            imageView.setImageURI(uri);

            Glide.with(context.getApplicationContext())
                    .load(path)
                    .into(imageView);
        }
    }
}
