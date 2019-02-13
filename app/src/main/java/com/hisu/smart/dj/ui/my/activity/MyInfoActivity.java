package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;

/**
 * @author lichee
 */
public class MyInfoActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮
    @Bind(R.id.user_info_nickname)
    TextView nickName;
    @Bind(R.id.user_info_name)
    TextView name;
    @Bind(R.id.user_info_sex)
    TextView sex;
    @Bind(R.id.user_info_idcard)
    TextView idCard;
    @Bind(R.id.user_info_phone)
    TextView phone;
    @Bind(R.id.user_info_xueLi)
    TextView xueLi;
    @Bind(R.id.user_info_joinPartyTime)
    TextView joinPartyTime;
    @Bind(R.id.user_info_MemberTime)
    TextView memberTime;
    @Bind(R.id.user_info_partyYear)
    TextView partyYear;
    private AppConfig appConfig;
    @Override
    public int getLayoutId() {
        return R.layout.activity_my_info;
    }

    @Override
    public void initPresenter() {
        appConfig = AppConfig.getInstance();
    }

    @Override
    public void initView() {
        mTitle.setText("个人信息");
        back_img.setOnClickListener(this);
        nickName.setText(appConfig.getString(AppConstant.NICK_NAME,""));
        name.setText(appConfig.getString(AppConstant.USER_NAME,""));
        int sex_type = appConfig.getInt(AppConstant.MEMBER_SEX,-1);
        if(sex_type == 1){
            sex.setText("男");
        }else if(sex_type == 0){
            sex.setText("n女");
        }
        idCard.setText(appConfig.getString(AppConstant.MEMBER_IDCARD,""));
        phone.setText(appConfig.getString(AppConstant.USER_PHONE,""));
        xueLi.setText(appConfig.getString(AppConstant.USER_PHONE,""));
        joinPartyTime.setText("2012年8月10日");
        memberTime.setText("2015年8月10日");
        partyYear.setText("8年");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_imageView:
                MyInfoActivity.this.finish();
                break;
            default:
                return;
        }
    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MyInfoActivity.class);
        activity.startActivity(intent);
    }
}
