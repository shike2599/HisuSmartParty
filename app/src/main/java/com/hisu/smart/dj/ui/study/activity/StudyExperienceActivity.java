package com.hisu.smart.dj.ui.study.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donkingliang.imageselector.adapter.ImageAdapter;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.ui.adapter.PicSelectorAdapter;
import com.jaydenxiao.common.base.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * 学习心得页面（可能会复用）
 */
public class StudyExperienceActivity extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.title_TextView)
    TextView show_title;
    @Bind(R.id.title_EditText)
    EditText title_edit;
    @Bind(R.id.content_editText)
    EditText content_edit;
    @Bind(R.id.start_upload_relativeLayout)
    RelativeLayout start_upload;
    @Bind(R.id.add_img_ImageView)
    ImageView add_img;
    @Bind(R.id.selector_img_RecyclerView)
    RecyclerView show_selector_img;

    private static final int REQUEST_CODE = 0x00000011;
    private ArrayList<String> images;
    private PicSelectorAdapter picSelectorAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_study_experience;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        back_img.setOnClickListener(this);
        add_img.setOnClickListener(this);
        start_upload.setOnClickListener(this);

        show_title.setText("学习心得");
        show_selector_img.setLayoutManager(new GridLayoutManager(this, 3));
        picSelectorAdapter = new PicSelectorAdapter(this);
        show_selector_img.setAdapter(picSelectorAdapter);
    }

    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, StudyExperienceActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.back_imageView:
                 StudyExperienceActivity.this.finish();
                 break;
             case R.id.add_img_ImageView:
                 ImageSelector.builder()
                         .useCamera(true) // 设置是否使用拍照
                         .setSingle(false)  //设置是否单选
                         .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                         .start(StudyExperienceActivity.this, REQUEST_CODE); // 打开相册
                 break;
             case R.id.start_upload_relativeLayout:
                 title_edit.getText();
                 content_edit.getText();
                 break;
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {

        if (requestCode == REQUEST_CODE && intent != null) {
            images = intent.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            picSelectorAdapter.refresh(images);
        }

    }
}
