package com.jaydenxiao.common.base;

/**
 * des:baseview
 * Created by xsf
 * on 2016.07.11:53
 */
public interface BaseView {
    /*******内嵌加载*******/
    void showLoading(String tag);
    void stopLoading(String tag);
    void showErrorTip(String msg,String tag);
}
