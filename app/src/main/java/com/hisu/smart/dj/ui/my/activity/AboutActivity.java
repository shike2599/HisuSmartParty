package com.hisu.smart.dj.ui.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.jaydenxiao.common.base.BaseActivity;

import butterknife.Bind;

/**
 * @author lichee
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.title_TextView)
    TextView mTitle;
    @Bind(R.id.back_imageView)
    ImageView back_img;//返回按钮

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        mTitle.setText("关于我们");
        back_img.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_imageView:
                AboutActivity.this.finish();
                break;
            default:
                return;
        }
    }
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }
}
