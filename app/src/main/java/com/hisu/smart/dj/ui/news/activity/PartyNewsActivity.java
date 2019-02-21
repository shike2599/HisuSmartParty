package com.hisu.smart.dj.ui.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
    private String[] partyNewsTitle = {"时政新闻","热点新闻","党建要闻","党员风采","时代先锋","制度文件","基层动态","警钟长鸣"};
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
        mTitle.setText("党建资讯");
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
            //拿到对应的标题
            for (int i = 0; i < cateEntitys.size(); i++) {
                String StrA = cateEntitys.get(i).getName();
                for (int j = 0; j < partyNewsTitle.length; j++) {
                    if (StrA.equals(partyNewsTitle[j])) {
                        //取到共同元素，写逻辑
                        String StrB = partyNewsTitle[j];
//                        Log.d("PartyNewsActivity","channelNames==="+channelNames);
                        channelNames.add(StrB);
                        commonFragmentList.add(createListFragments(cateEntitys.get(i)));
                        break;
                    }
                }
            }
            //暂时先注释
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

    private PartyNewsFragment createListFragments(CateEntity cateEntity) {
        PartyNewsFragment fragment = new PartyNewsFragment();
        Bundle bundle = new Bundle();
        if(cateEntity.getName().equals("制度文件")){
            bundle.putBoolean("ISFILE",true);
        }
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
