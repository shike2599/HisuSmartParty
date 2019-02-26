package com.hisu.smart.dj.ui.news.activity;

import android.app.Activity;

import android.content.Intent;

import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.NotingResponse;
import com.hisu.smart.dj.entity.StudiedDetailEntity;
import com.hisu.smart.dj.entity.StudyLogParam;
import com.hisu.smart.dj.entity.UserCollectionEntity;
import com.hisu.smart.dj.ui.news.contract.MediaPlayerContract;
import com.hisu.smart.dj.ui.news.model.MediaPlayerModel;
import com.hisu.smart.dj.ui.news.presenter.MediaPlayerPresenter;
import com.hisu.smart.dj.ui.widget.CollectToast;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;

import java.text.NumberFormat;

import butterknife.Bind;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


public class MediaPlayerActivity extends BaseActivity<MediaPlayerPresenter, MediaPlayerModel> implements MediaPlayerContract.View, View.OnClickListener, JCVideoPlayerStandard.OnCompletionListener, JCVideoPlayer.OnPrepareListener {


    @Bind(R.id.title_TextView)
    TextView title_textView;
    @Bind(R.id.back_imageView)
    ImageView back_img;
    @Bind(R.id.video_collection_imageView)
    ImageView collection_img;
    @Bind(R.id.video_collection_textView)
    TextView collection_textView;
    @Bind(R.id.new_video_player)
    JCVideoPlayerStandard player;
    @Bind(R.id.video_title)
    TextView video_title;
    @Bind(R.id.video_time)
    TextView video_time;
    private MediaParamEntity videoData;
    private boolean isParyBranch = false;
    private int resType = 0;
    private StudiedDetailEntity studiedDetail;
    private int mSeekTimePosition = 0;
    private int collectSeri = 0; //收藏序号
    private CollectToast collectToast;
    private int partyMemberId;
    private int totalTime = 0;

    private final static String TAG = "MediaPlayerActivity";
    public static void startAction(Activity activity, MediaParamEntity data) {
        Intent intent = new Intent(activity, MediaPlayerActivity.class);
        intent.putExtra(AppConstant.VIDEO, data);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_player;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView() {
        collectToast = new CollectToast(this);
        title_textView.setText("");
        back_img.setOnClickListener(this);
        collection_textView.setOnClickListener(this);
        videoData = (MediaParamEntity) getIntent().getSerializableExtra(AppConstant.VIDEO);
        partyMemberId = AppConfig.getInstance().getInt(AppConstant.MEMBER_ID,-1);
        video_title.setText(videoData.getTitle());
        video_time.setText(videoData.getCreateTime());
        Log.i(TAG, "url:" + videoData.getUrl() + ",resId:" + videoData.getResId() + "resType:" + videoData.getResType());
        boolean setUp = player.setUp(
                TextUtils.isEmpty(videoData.getUrl()) ? "" : videoData.getUrl(),
                JCVideoPlayer.SCREEN_LAYOUT_LIST,
                TextUtils.isEmpty(videoData.getTitle()) ? "" : videoData.getTitle());
        if (setUp) {
            Glide.with(this)
                    .load(videoData.getCover())
                    .apply(new RequestOptions()
                            .error(com.jaydenxiao.common.R.drawable.no_content_tip)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(com.jaydenxiao.common.R.drawable.no_content_tip))
                    .into(player.thumbImageView);
        }
        player.setOnCompletionListener(this);
        player.setOnPrepareListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        resType = videoData.getResType();
//        isParyBranch = AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_BRANCH, false);
        isParyBranch =  AppConstant.IS_STUDY_BRANCH;
        Log.i(TAG, "isParyBranch========================" + isParyBranch);
            if (resType > 0) {
                JCVideoPlayer.isStudy = true;
                player.progressBar.setClickable(false);
                player.progressBar.setEnabled(false);
                player.progressBar.setSelected(false);
                player.progressBar.setFocusable(false);
                if (isParyBranch) {
                    mPresenter.getBranchResStudiedDetailRequest(videoData.getUserId(), resType, videoData.getResId());
                } else {
                    mPresenter.getMemberResStudiedDetailRequest(videoData.getUserId(), resType, videoData.getResId());
                }
            }else{
                JCVideoPlayer.isStudy = false;
            }
        mPresenter.getUserCollectionDataRequest(videoData.getUserId(),partyMemberId,resType, videoData.getResId());
        player.startPlayLogic();
    }


    @Override
    public void onPause() {
        int position = player.getCurrentPositionWhenPlaying();
        addStudyLogs(position);
        JCVideoPlayer.releaseAllVideos();
        super.onPause();
    }

    public void addStudyLogs(long position) {
        Log.i(TAG, "addStudyLogs==============" +String.valueOf(position)  + "===========mSeekTimePosition=============" + mSeekTimePosition);

        if (mSeekTimePosition >= position) {
            return;
        }
        if (resType > 0) {
            StudyLogParam param = new StudyLogParam();
            param.setUserId(videoData.getUserId());
            param.setResName(videoData.getTitle());
            param.setResId(videoData.getResId());
            param.setResType(videoData.getResType());
            if (studiedDetail != null) {
                param.setLogId(studiedDetail.getId());
                param.setPartyBranchId(studiedDetail.getPartyBranchId());
                Log.i(TAG,"position:"+String.valueOf(position) +",totalTime:"+totalTime);
                float studiedHours = videoData.getTotalHours()*((float) position/totalTime);
                NumberFormat nf = NumberFormat.getNumberInstance();
                nf.setMaximumFractionDigits(2);
                param.setStudiedHours(Float.valueOf(nf.format(studiedHours)));
                param.setResTotalHours(videoData.getTotalHours());
            }
            param.setDuration(Long.parseLong(position + ""));
            param.setPagePath("com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity");
            param.setRemark("v1.0");
            Log.i(TAG, "param:" + param.toString());
            if (isParyBranch) {
                mPresenter.addPartyBranchStudyLogsRequest(param.getUserId(),
                        param.getLogId(),
                        param.getPartyBranchId(),
                        param.getResType(),
                        param.getResId(),
                        param.getResName(),
                        param.getDuration(),
                        param.getStudiedHours(),
                        param.getResTotalHours(),
                        param.getPagePath(),
                        param.getRemark());
            } else {
                mPresenter.addPartyMemberStudyLogsRequest(param.getUserId(),
                        param.getLogId(),
                        param.getPartyBranchId(),
                        param.getResType(),
                        param.getResId(),
                        param.getResName(),
                        param.getDuration(),
                        param.getStudiedHours(),
                        param.getResTotalHours(),
                        param.getPagePath(),
                        param.getRemark());
            }
        }

    }


    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
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

    @Override
    public void returnBranchResStudiedDetail(StudiedDetailEntity studiedDetailEntity) {
        studiedDetail = studiedDetailEntity;
        if (studiedDetailEntity != null) {
            Log.i(TAG, "returnBranchResStudiedDetail========" + studiedDetail.toString());
            mSeekTimePosition = studiedDetail.getDuration();
            player.seekToInAdvance = mSeekTimePosition;
        }
    }

    @Override
    public void returnMemberResStudiedDetail(StudiedDetailEntity studiedDetailEntity) {
        studiedDetail = studiedDetailEntity;
        if (studiedDetailEntity != null) {
            Log.i(TAG, "returnMemberResStudiedDetail========" + studiedDetail.toString());
            mSeekTimePosition = studiedDetail.getDuration();
            player.seekToInAdvance = mSeekTimePosition;
        }
    }

    @Override
    public void returnAddPartyBranchStudyLogs(BaseResponse baseResponse) {
        Log.i(TAG, "returnAddPartyBranchStudyLogs========" + baseResponse.toString());
    }

    @Override
    public void returnAddPartyMemberStudyLogs(BaseResponse baseResponse) {
        Log.i(TAG, "returnAddPartyMemberStudyLogs========" + baseResponse.toString());
    }

    //添加收藏/查询序号
    @Override
    public void returnCollectionData(UserCollectionEntity userCollectionEntity, String tag) {
        Log.d(TAG,"===returnCollectionData=tag="+tag);
        Log.d(TAG,"===userCollectionEntity==DATA=="+userCollectionEntity.getData());
        //查询收藏接口
        if(tag.equals(AppConstant.QUERY_COLLECTION_TAG)){
            if(userCollectionEntity.getResultCode() == 200){
                collectSeri = userCollectionEntity.getData();
                if(collectSeri != 0){
                    collection_img.setBackgroundResource(R.mipmap.pre_likes);
                    collection_textView.setText("取消收藏");
                }else{
                    collection_img.setBackgroundResource(R.mipmap.links_icon);
                    collection_textView.setText("收藏");
                }
            }
        }
        //添加收藏
        if(tag.equals(AppConstant.ADD_COLLECTION_TAG)){
            if(userCollectionEntity.getResultCode() == 200){
                collectSeri = userCollectionEntity.getData();
                collectToast.setContext("收藏成功!");
                collectToast.setIsCollect(true);
                collectToast.builder().show();
                collection_img.setBackgroundResource(R.mipmap.pre_likes);
                collection_textView.setText("取消收藏");
            }else if(userCollectionEntity.getResultCode() == 1001){
                collectToast.setContext("您已收藏该新闻");
                collectToast.setIsCollect(false);
                collectToast.builder().show();
            }
        }


    }
    //取消收藏返回
    @Override
    public void returnCancelCollectionData(NotingResponse notingResponse) {
        if(notingResponse.getResultCode() == 200){
            collectToast.setContext("您已取消该收藏!");
            collectToast.setIsCollect(true);
            collectToast.builder().show();
            collection_img.setBackgroundResource(R.mipmap.links_icon);
            collection_textView.setText("收藏");
            collectSeri = 0;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_imageView:
                finish();
                break;
            //收藏
            case R.id.video_collection_textView:
                if(collectSeri != 0){
                    mPresenter.cancelCollectionRequest(collectSeri);
                    Log.d(TAG,"===已经收藏，取消收藏==");
                }else{
                    //未收藏准备收藏
                    Log.d(TAG,"===未收藏准备收藏==");
                    mPresenter.addCollectionDataRequest(videoData.getUserId(),partyMemberId,resType,videoData.getResId());
                }
                break;
        }
    }

    @Override
    public void onCompletion() {
        int position = player.getDuration();
        Log.i(TAG,"onCompletion======================"+position);
        addStudyLogs(position);
    }

    @Override
    public void onPrepare() {
        totalTime = player.getDuration();
        Log.i(TAG,"onPrepare================="+totalTime);
    }
}
