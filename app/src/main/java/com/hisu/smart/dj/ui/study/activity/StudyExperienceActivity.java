package com.hisu.smart.dj.ui.study.activity;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.imageselector.utils.ImageSelector;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.ui.adapter.PicSelectorAdapter;
import com.hisu.smart.dj.ui.adapter.StudyTopicAdapter;
import com.hisu.smart.dj.ui.study.contract.UpLoadFileContract;
import com.hisu.smart.dj.ui.study.model.UpLoadFileModel;
import com.hisu.smart.dj.ui.study.presenter.UpLoadFilePresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;
import com.jaydenxiao.common.commonwidget.LoadingDialog;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 学习心得页面（可能会复用）
 */
public class StudyExperienceActivity extends BaseActivity<UpLoadFilePresenter,UpLoadFileModel> implements
        View.OnClickListener,UpLoadFileContract.View{
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
    private String title;
    private Integer user_id;
    private Integer partyMemberId;
    private Integer mediaType;
    private AppConfig appConfig;
    private Map<String, RequestBody> bodyMap;
    @Override
    public int getLayoutId() {
        return R.layout.activity_study_experience;
    }

    @Override
    public void initPresenter() {
      title = getIntent().getStringExtra(AppConstant.UPLOAD_TITLE);
      appConfig = AppConfig.getInstance();
      user_id = appConfig.getInt(AppConstant.USER_ID,-1);
      partyMemberId = appConfig.getInt(AppConstant.MEMBER_PARTYBRANCH_ID,-1);
      mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        back_img.setOnClickListener(this);
        add_img.setOnClickListener(this);
        start_upload.setOnClickListener(this);

        show_title.setText(title);
        show_selector_img.setLayoutManager(new GridLayoutManager(this, 3));
        picSelectorAdapter = new PicSelectorAdapter(this);
        show_selector_img.setAdapter(picSelectorAdapter);
    }

    public static void startAction(Activity activity,String title){
        Intent intent = new Intent(activity, StudyExperienceActivity.class);
        intent.putExtra(AppConstant.UPLOAD_TITLE,title);
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
                 String title = title_edit.getText().toString();
                 String content = content_edit.getText().toString();
                 if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(content)){
                     if(images!=null&&images.size()>0){
                         mediaType = 1;
                         bodyMap = new HashMap<>();
                         for(int i = 0; i < images.size();i++){
                             File file = new File(images.get(i));
                             bodyMap.put("imgPaths",RequestBody.create(MediaType.parse("image/jpeg"),file));
                         }
                     }else{
                         mediaType = 2;
                     }
                     mPresenter.submitActionContentRequest(user_id,partyMemberId,null,
                             null,title,null,bodyMap,mediaType,content,null,getNowTime(),false);
                 }else{
                     Toast.makeText(StudyExperienceActivity.this,
                             "标题和内容不能为空!",Toast.LENGTH_LONG).show();
                 }
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

    @Override
    public void returnSubmitResponse(BaseResponse baseResponse) {

    }

    @Override
    public void showLoading(String tag) {
        LoadingDialog.showDialogForLoading(StudyExperienceActivity.this,tag,false);
    }

    @Override
    public void stopLoading(String tag) {
       LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg, String tag) {
      LoadingDialog.cancelDialogForLoading();
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    private String getNowTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Log.d("Time",dateString);
        return dateString;
    }
}
