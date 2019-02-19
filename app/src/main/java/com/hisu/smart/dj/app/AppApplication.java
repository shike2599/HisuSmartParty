package com.hisu.smart.dj.app;

import android.util.Log;

import com.hisu.smart.dj.BuildConfig;
import com.jaydenxiao.common.baseapp.BaseApplication;
import com.jaydenxiao.common.commonutils.LogUtils;
import com.jaydenxiao.common.commonutils.NetWorkUtils;
import com.tencent.smtt.sdk.QbSdk;

/**
 *
 * @author lichee
 * @date 2019/1/21
 */

public class AppApplication  extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        AppConfig.init(this);
    }

}