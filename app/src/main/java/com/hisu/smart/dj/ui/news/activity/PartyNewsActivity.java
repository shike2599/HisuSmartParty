package com.hisu.smart.dj.ui.news.activity;

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
import com.hisu.smart.dj.ui.news.contract.PartyNewsMainContract;
import com.hisu.smart.dj.ui.news.fragment.PartyNewsFragment;
import com.hisu.smart.dj.ui.news.model.PartyNewsMainModel;
import com.hisu.smart.dj.ui.news.presenter.PartyNewsMainPresenter;

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
public class PartyNewsActivity extends BaseActivity<PartyNewsMainPresenter,PartyNewsMainModel> implements PartyNewsMainContract.View {

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView backImage;
    @Bind(R.id.tabs_news_cate)
    TabLayout tabs;
    @Bind(R.id.view_news_pager)
    ViewPager viewPager;
    private BaseFragmentAdapter fragmentAdapter;

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, PartyNewsActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_party_news;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        mTitle.setText("党建咨讯");
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter.listInfoCateRequest(null,"","");
    }

    @Override
    public void showLoading(String tag) {

    }

    @Override
    public void stopLoading(String tag) {

    }

    @Override
    public void showErrorTip(String msg, String tag) {
            ToastUitl.show(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void returnListInfoCate(List<CateEntity> cateEntitys) {
        if(cateEntitys != null) {
            List<String> channelNames = new ArrayList<>();
            List<Fragment> commonFragmentList = new ArrayList<>();
            for (int i = 0; i < cateEntitys.size(); i++) {
                channelNames.add(cateEntitys.get(i).getName());
                commonFragmentList.add(createListFragments(cateEntitys.get(i)));
            }
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

    private PartyNewsFragment createListFragments(CateEntity cateEntity) {
        PartyNewsFragment fragment = new PartyNewsFragment();
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
