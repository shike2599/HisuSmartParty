package com.hisu.smart.dj.app;

import com.hisu.smart.dj.BuildConfig;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.NetWorkUtils;

/**
 *
 * @author lichee
 * @date 2019/1/21
 */

public class AppApplication  extends BaseApplication {
    public static boolean isNet;
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化logger
        LogUtils.logInit(BuildConfig.LOG_DEBUG);
        //判断网络
        isNet = NetWorkUtils.isNetConnected(this);
        //初始化sp存储
        AppConfig.init(this);
    }
}