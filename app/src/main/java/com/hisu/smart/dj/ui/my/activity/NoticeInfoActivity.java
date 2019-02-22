package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.NoticeInfoEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.ui.my.contract.NoticeReadContract;
import com.hisu.smart.dj.ui.my.model.NoticeReadModel;
import com.hisu.smart.dj.ui.my.presenter.NoticeReadPresenter;
import com.jaydenxiao.common.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.Bind;

public class NoticeInfoActivity extends BaseActivity<NoticeReadPresenter,NoticeReadModel>
        implements View.OnClickListener,NoticeReadContract.View{
    @Bind(R.id.title_TextView)
    TextView title_Tv;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.notice_info_title_textView)
    TextView notice_info_title_Tv;
    @Bind(R.id.notice_info_time_textView)
    TextView notice_info_time_Tv;
    @Bind(R.id.notice_info_content_textView)
    TextView notice_info_content_Tv;
    private NoticeInfoEntity.DataListBean noticeBean;
    private int userId;
    private int partyMemberId;
    @Override
    public int getLayoutId() {
        return R.layout.activity_notice_info;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
        noticeBean = (NoticeInfoEntity.DataListBean)getIntent().getSerializableExtra("NOTICEBEAN");
        userId = AppConfig.getInstance().getInt(AppConstant.USER_ID,-1);
        partyMemberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
    }

    @Override
    public void initView() {
       title_Tv.setText("消息详情");
       back_img.setOnClickListener(this);
       if(noticeBean!=null){
           notice_info_title_Tv.setText(noticeBean.getName());
           notice_info_time_Tv.setText(noticeBean.getPublishTime());
           notice_info_content_Tv.setText(noticeBean.getContent());
           if(!noticeBean.isHasRead()){
               //标记未读为已读
               mPresenter.readNoticeNumRequest(userId,partyMemberId,noticeBean.getId());
           }
       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:
                NoticeInfoActivity.this.finish();
                break;
        }

    }
    public static void startAction(Activity activity, NoticeInfoEntity.DataListBean noticeBean){
        Intent intent = new Intent(activity, NoticeInfoActivity.class);
        intent.putExtra("NOTICEBEAN",noticeBean);
        activity.startActivity(intent);
    }

    @Override
    public void returnReadNoticeNum(NotingResponse notingResponse, String tag) {
         if(notingResponse.getResultCode() == 200){
             Log.d("NoticeInfoActivity","该消息已经成功阅读～");
         }
    }

    @Override
    public void showLoading(String tag) {

    }

    @Override
    public void stopLoading(String tag) {

    }

    @Override
    public void showErrorTip(String msg, String tag) {

    }
}
