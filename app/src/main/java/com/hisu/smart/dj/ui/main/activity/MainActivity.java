package com.hisu.smart.dj.ui.main.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.TabEntity;
import com.hisu.smart.dj.ui.main.fragment.FollowFragment;
import com.hisu.smart.dj.ui.main.fragment.HomeFragment;
import com.hisu.smart.dj.ui.main.fragment.MyFragment;
import com.hisu.smart.dj.ui.main.fragment.PartyBuildFragment;
import com.hisu.smart.dj.ui.main.fragment.StudyFragment;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.commonutils.LogUtils;

import java.util.ArrayList;

import butterknife.Bind;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**
 * @author lichee
 */
public class MainActivity extends BaseActivity {

    @Bind(R.id.tab_layout)
    CommonTabLayout tabLayout;
    private String[] mTitles = {"首页", "学习","党建","践行","我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.home_no_selerted,R.mipmap.study_no_seleter,
            R.mipmap.dang_no_selected,R.mipmap.doit_no_selecter,R.mipmap.prosen_no_selecter};
    private int[] mIconSelectIds = {
            R.mipmap.home_selected_ico,R.mipmap.study_selected_icon,
            R.mipmap.dang_selected_icon,R.mipmap.doit_selected_icon,R.mipmap.porsen_selected_icon};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private HomeFragment homeFragment;
    private PartyBuildFragment partyBuildFragment;
    private StudyFragment studyFragment;
    private FollowFragment followFragment;
    private MyFragment myFragment;

    private static int currentTabPosition;

    /**
     * 入口
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.jaydenxiao.common.R.anim.fade_out);
    }

    public static void startAction(Activity activity,int currentTabPosition){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(AppConstant.HOME_CURRENT_TAB_POSITION,currentTabPosition);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in,
                com.jaydenxiao.common.R.anim.fade_out);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化frament
        initFragment(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        //初始化菜单
        initTab();
    }

    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        currentTabPosition = getIntent().getIntExtra(AppConstant.HOME_CURRENT_TAB_POSITION,0);
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            partyBuildFragment = (PartyBuildFragment) getSupportFragmentManager().findFragmentByTag("partyBuildFragment");
            studyFragment = (StudyFragment) getSupportFragmentManager().findFragmentByTag("studyFragment");
            followFragment = (FollowFragment) getSupportFragmentManager().findFragmentByTag("followFragment");
            myFragment = (MyFragment) getSupportFragmentManager().findFragmentByTag("myFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            homeFragment = new HomeFragment();
            partyBuildFragment = new PartyBuildFragment();
            studyFragment = new StudyFragment();
            followFragment = new FollowFragment();
            myFragment = new MyFragment();

            transaction.add(R.id.fl_body, homeFragment, "homeFragment");
            transaction.add(R.id.fl_body, partyBuildFragment, "partyBuildFragment");
            transaction.add(R.id.fl_body, studyFragment, "studyFragment");
            transaction.add(R.id.fl_body, followFragment, "followFragment");
            transaction.add(R.id.fl_body, myFragment, "myFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);
        tabLayout.setCurrentTab(currentTabPosition);
    }



    /**
     * 初始化tab
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tabLayout.setTabData(mTabEntities);
        //点击监听
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                SwitchTo(position);
            }
            @Override
            public void onTabReselect(int position) {
            }
        });
    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        LogUtils.logd("主页菜单position" + position);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            //首页
            case 0:
                transaction.hide(partyBuildFragment);
                transaction.hide(studyFragment);
                transaction.hide(followFragment);
                transaction.hide(myFragment);
                transaction.show(homeFragment);
                transaction.commitAllowingStateLoss();
                break;
            //学习
            case 1:
                transaction.hide(partyBuildFragment);
                transaction.hide(followFragment);
                transaction.hide(myFragment);
                transaction.hide(homeFragment);
                transaction.show(studyFragment);
                transaction.commitAllowingStateLoss();
                break;
            //党建
            case 2:
                transaction.hide(followFragment);
                transaction.hide(myFragment);
                transaction.hide(homeFragment);
                transaction.hide(studyFragment);
                transaction.show(partyBuildFragment);
                transaction.commitAllowingStateLoss();
                break;
            //践行
            case 3:
                transaction.hide(myFragment);
                transaction.hide(homeFragment);
                transaction.hide(studyFragment);
                transaction.hide(partyBuildFragment);
                transaction.show(followFragment);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 4:
                transaction.hide(homeFragment);
                transaction.hide(studyFragment);
                transaction.hide(partyBuildFragment);
                transaction.hide(followFragment);
                transaction.show(myFragment);
                transaction.commitAllowingStateLoss();
                break;
            default:
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
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //奔溃前保存位置
        LogUtils.logi("MAIN","onSaveInstanceState进来了1");
        if (tabLayout != null) {
            LogUtils.logi("MAIN","onSaveInstanceState进来了2");
            outState.putInt(AppConstant.HOME_CURRENT_TAB_POSITION, tabLayout.getCurrentTab());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppConstant.QR_CODE&&resultCode==RESULT_OK) {

        }
    }

}
