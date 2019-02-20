package com.hisu.smart.dj.ui.study.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.CateEntity;
import com.hisu.smart.dj.ui.study.contract.StudyCommonMainContract;
import com.hisu.smart.dj.ui.study.fragment.StudyCommonFragment;
import com.hisu.smart.dj.ui.study.model.StudyCommonMainModel;
import com.hisu.smart.dj.ui.study.presenter.StudyCommonMainPresenter;
import com.hisu.smart.dj.utils.MyUtils;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.base.BaseFragmentAdapter;

import com.jaydenxiao.common.commonutils.ToastUitl;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author lichee
 */
public class StudyCommonActivity extends BaseActivity<StudyCommonMainPresenter,StudyCommonMainModel>
        implements StudyCommonMainContract.View {
    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView backImage;
    @Bind(R.id.tabs_common_cate)
    TabLayout tabs;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    private BaseFragmentAdapter fragmentAdapter;
    private String[] commonStudyTitle = {"党史研究","党章党纪","理论武装","思想理论"};


    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, StudyCommonActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_study_common;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        mTitle.setText("常规学习");
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.listCommonCateRequest(null,"","");
    }

    @Override
    public void showLoading(String tag) {
    }

    @Override
    public void stopLoading(String msg) {
    }

    @Override
    public void showErrorTip(String msg, String tag) {
        ToastUitl.show(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void returnListCommonCate(List<CateEntity> cateEntitys) {
        if(cateEntitys != null) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> commonFragmentList = new ArrayList<>();

            //拿到对应的标题
            for (int i = 0; i < cateEntitys.size(); i++) {
                String StrA = cateEntitys.get(i).getName();
                for (int j = 0; j < commonStudyTitle.length; j++) {
                    if (StrA.equals(commonStudyTitle[j])) {
                        //取到共同元素，写逻辑
                        String StrB = commonStudyTitle[j];
//                        Log.d("PartyNewsActivity","channelNames==="+channelNames);
                        channelNames.add(StrB);
                        commonFragmentList.add(createListFragments(cateEntitys.get(i)));
                        break;
                    }
                }
            }
            //暂时注释
//            for (int i = 0; i < cateEntitys.size(); i++) {
//                channelNames.add(cateEntitys.get(i).getName());
//                commonFragmentList.add(createListFragments(cateEntitys.get(i)));
//            }
            if(fragmentAdapter==null) {
                fragmentAdapter = new BaseFragmentAdapter(getSupportFragmentManager(), commonFragmentList, channelNames);
            }else{
                //刷新fragment
                fragmentAdapter.setFragments(getSupportFragmentManager(),commonFragmentList,channelNames);
            }
            viewPager.setAdapter(fragmentAdapter);
            tabs.setupWithViewPager(viewPager);
            MyUtils.dynamicSetTabLayoutMode(tabs);
            setPageChangeListener();
        }
    }

    private StudyCommonFragment createListFragments(CateEntity cateEntity) {
        StudyCommonFragment fragment = new StudyCommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstant.COMMON_CATE_CODE, cateEntity.getCode());
        fragment.setArguments(bundle);
        return fragment;
    }
    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
