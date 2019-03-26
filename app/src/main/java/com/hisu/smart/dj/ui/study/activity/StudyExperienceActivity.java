package com.hisu.smart.dj.ui.study.activity;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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

import com.donkingliang.imageselector.utils.ImageSelector;
import com.hisu.smart.dj.R;

import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.UpLoadFileResponse;
import com.hisu.smart.dj.ui.adapter.PicSelectorAdapter;

import com.hisu.smart.dj.ui.study.contract.UpLoadFileContract;
import com.hisu.smart.dj.ui.study.model.UpLoadFileModel;
import com.hisu.smart.dj.ui.study.presenter.UpLoadFilePresenter;

import com.hisu.smart.dj.ui.widget.CommomDialog;
import com.hisu.smart.dj.ui.zone.activity.CircleZoneActivity;
import com.jaydenxiao.common.base.BaseActivity;

import com.jaydenxiao.common.commonwidget.LoadingDialog;

import java.io.File;

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
        View.OnClickListener,UpLoadFileContract.View,PicSelectorAdapter.OnImgItemClickListener{
    private String TAG = "StudyExperienceActivity";
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
    private Integer memberId;
    private Integer mediaType;
    private AppConfig appConfig;
    private Map<String, RequestBody> bodyMap;
    private CommomDialog commomDialog;
    private int follow_id;
    private boolean isUploadSuccess;
    private String content;
    private String upTitle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_study_experience;
    }

    @Override
    public void initPresenter() {
      images = new ArrayList<>();
      title = getIntent().getStringExtra(AppConstant.UPLOAD_TITLE);
      follow_id = getIntent().getIntExtra(AppConstant.FOLLOW_ID,-1);
      Log.d(TAG,"follow_id==="+follow_id);
      if(follow_id == -1){
          follow_id = 5008; //学习心得
      }
      appConfig = AppConfig.getInstance();
      user_id = appConfig.getInt(AppConstant.USER_ID,-1);
      memberId = appConfig.getInt(AppConstant.MEMBER_ID,-1);
      mPresenter.setVM(this,mModel);
      commomDialog = new CommomDialog(this,R.style.dialog,"",new CommomDialog.OnCloseListener(){
          @Override
          public void onClick(Dialog dialog, boolean confirm) {
                 if(isUploadSuccess){
                     if(confirm){
                         dialog.dismiss();
                         AppConstant.isUpLoad = true;
                         StudyExperienceActivity.this.finish();
                         if(title.endsWith("党员圈")){
                           finish();
                         }

                     }else{
                         dialog.dismiss();
                     }
                 }else{
                     dialog.dismiss();
                 }
          }
      });


    }

    @Override
    public void initView() {
        back_img.setOnClickListener(this);
        add_img.setOnClickListener(this);
        start_upload.setOnClickListener(this);

        show_title.setText(title);
        show_selector_img.setLayoutManager(new GridLayoutManager(this, 3));
        picSelectorAdapter = new PicSelectorAdapter(this);
        picSelectorAdapter.setOnItemClickListener(this);
        show_selector_img.setAdapter(picSelectorAdapter);
    }

    public static void startAction(Activity activity,String title){
        Intent intent = new Intent(activity, StudyExperienceActivity.class);
        intent.putExtra(AppConstant.UPLOAD_TITLE,title);
        activity.startActivity(intent);
    }

//    public static void startAction(Activity activity,String title,int follow_id){
//        Intent intent = new Intent(activity, StudyExperienceActivity.class);
//        intent.putExtra(AppConstant.UPLOAD_TITLE,title);
//        intent.putExtra(AppConstant.FOLLOW_ID,follow_id);
//        activity.startActivity(intent);
//    }
    public static void startAction(Context activity, String title, int follow_id){
        Intent intent = new Intent(activity, StudyExperienceActivity.class);
        intent.putExtra(AppConstant.UPLOAD_TITLE,title);
        intent.putExtra(AppConstant.FOLLOW_ID,follow_id);
        activity.startActivity(intent);
    }
    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.back_imageView:
                 StudyExperienceActivity.this.finish();
                 break;
             case R.id.add_img_ImageView:
//                 isUploadSuccess = false;
//                 commomDialog.isShowCancelBtn(false);
//                 commomDialog.setTitle("提示");
//                 commomDialog.setContent("暂未开通图片上传！敬请期待！");
//                 commomDialog.show();
                 ImageSelector.selectedImgNum = images.size();
                 ImageSelector.builder()
                         .useCamera(true) // 设置是否使用拍照
                         .setSingle(false)  //设置是否单选
                         .setMaxSelectCount(9) // 图片的最大选择数量，小于等于0时，不限数量。
                         .start(StudyExperienceActivity.this, REQUEST_CODE); // 打开相册
                 break;
             case R.id.start_upload_relativeLayout:
                 upTitle = title_edit.getText().toString();
                 content = content_edit.getText().toString();
                 if(!TextUtils.isEmpty(title)&&!TextUtils.isEmpty(content)){
                     if(images!=null&&images.size()>0){
                         mediaType = 1;
                         bodyMap = new HashMap<>();
                         for(int i = 0; i < images.size();i++){
                             File file = new File(images.get(i));
                             bodyMap.put("file"+i+"\"; filename=\""+file.getName(),
                                     RequestBody.create(MediaType.parse("image/*"),file));
                         }
                         LoadingDialog.showDialogForLoading(StudyExperienceActivity.this,"正在上传",false);
                         mPresenter.upLoadFileRequest(bodyMap);
                     }else{
                         mediaType = 2;
                         //上传纯文字信息
                         LoadingDialog.showDialogForLoading(StudyExperienceActivity.this,"正在上传",false);
                         startUpLoad(null);
                     }

                 }else{
                     isUploadSuccess = false;
                     commomDialog.isShowCancelBtn(false);
                     commomDialog.setTitle("提示");
                     commomDialog.setContent("请将标题和内容填写完整!");
                     commomDialog.show();
                 }
                 break;
         }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent intent) {

        if (requestCode == REQUEST_CODE && intent != null) {
            List<String> selectedImages = intent.getStringArrayListExtra(ImageSelector.SELECT_RESULT);
            images.addAll(selectedImages);
            picSelectorAdapter.refresh(images);
        }

    }
    //上传所有内容
    @Override
    public void returnSubmitResponse(NotingResponse notingResponse) {
        LoadingDialog.cancelDialogForLoading();
        if(notingResponse.getResultCode() == 200){
            isUploadSuccess = true;
            commomDialog.isShowCancelBtn(true);
            commomDialog.setTitle("提示");
            commomDialog.setContent("上传成功！");
            commomDialog.setNegativeButton("返回");
        }else{
            LoadingDialog.cancelDialogForLoading();
            commomDialog.isShowCancelBtn(false);
            commomDialog.setTitle("提示");
            commomDialog.setContent("上传失败请重试！！！");
            commomDialog.setNegativeButton("返回");
        }
        commomDialog.show();
    }
    //图片上传返回
    @Override
    public void returnUpLoadFile(UpLoadFileResponse upLoadFileResponse) {
          int responseCode = upLoadFileResponse.getResultCode();
          List<UpLoadFileResponse.FileListBean> fileBeas = upLoadFileResponse.getDataList();
          Log.d(TAG,"----fileBeas--==size=="+fileBeas.size());
          if(responseCode == 200 && fileBeas!=null && fileBeas.size()>0){
              isUploadSuccess = true;
              StringBuilder imgPathsSb = new StringBuilder();
              for(UpLoadFileResponse.FileListBean fileListBean : fileBeas){
                  imgPathsSb.append(fileListBean.getPath());
                  imgPathsSb.append(",");
              }
              String imagePath = imgPathsSb.toString();
              imagePath = imagePath.substring(0,imagePath.length()-1);
              Log.d(TAG,"imagePath======"+imagePath);
              //开始上传
              startUpLoad(imagePath);
          }else{
              LoadingDialog.cancelDialogForLoading();
              commomDialog.isShowCancelBtn(false);
              commomDialog.setTitle("提示");
              commomDialog.setContent("上传失败请重试！！！");
              commomDialog.setNegativeButton("返回");
              commomDialog.show();
          }

    }

    @Override
    public void showLoading(String tag) {
//        LoadingDialog.showDialogForLoading(StudyExperienceActivity.this,tag,false);
    }

    @Override
    public void stopLoading(String tag) {
//       LoadingDialog.cancelDialogForLoading();
    }

    @Override
    public void showErrorTip(String msg, String tag) {
      LoadingDialog.cancelDialogForLoading();
      isUploadSuccess = false;
      commomDialog.setTitle("提示");
      commomDialog.setContent(msg);
      commomDialog.show();
    }

    private String getNowTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        Log.d("Time",dateString);
        return dateString;
    }

    /**
     * 开始上传内容
     * @param imagePaths
     */
    private void startUpLoad(String imagePaths){
        if(follow_id == 5008 || follow_id == 5007){
            mPresenter.submitActionContentRequest(user_id,memberId,null,
                    String.valueOf(follow_id),upTitle,"",imagePaths,mediaType,content,null,getNowTime(),false);
        }else{
            mPresenter.submitActionContentRequest(user_id,memberId,follow_id,
                    null,upTitle,"",imagePaths,mediaType,content,null,getNowTime(),false);
        }
    }

    @Override
    public void onImgItemClick(int position) {
        images.remove(position);
        picSelectorAdapter.refresh(images);
    }
}
