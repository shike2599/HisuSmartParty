package com.hisu.smart.dj.ui.news.activity;

import android.app.Activity;
import android.content.Intent;

import android.content.pm.ActivityInfo;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.app.AppConstant;

import com.hisu.smart.dj.entity.MediaParamEntity;
import com.hisu.smart.dj.entity.StudiedDetailEntity;
import com.hisu.smart.dj.entity.StudyLogParam;
import com.hisu.smart.dj.ui.news.contract.MediaPlayerContract;
import com.hisu.smart.dj.ui.news.model.MediaPlayerModel;
import com.hisu.smart.dj.ui.news.presenter.MediaPlayerPresenter;
import com.jaydenxiao.common.base.BaseActivity;
import com.jaydenxiao.common.basebean.BaseResponse;

import fm.jiecao.jcvideoplayer_lib.JCBuriedPoint;
import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class MediaPlayerActivity extends BaseActivity<MediaPlayerPresenter, MediaPlayerModel> implements MediaPlayerContract.View {

    private JCVideoPlayerStandard player;
    private MediaParamEntity videoData;
    private boolean isParyBranch = false;
    private int resType = 0;
    private StudiedDetailEntity studiedDetail;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        player = findViewById(R.id.new_video_player);
        player.fullscreenButton.setVisibility(View.GONE);
        player.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        videoData = (MediaParamEntity) getIntent().getSerializableExtra(AppConstant.VIDEO);
        Log.i(TAG, "url:" + videoData.getUrl() + ",resId:" + videoData.getResId() + "resType:" + videoData.getResType());
        boolean setUp = player.setUp(
                TextUtils.isEmpty(videoData.getUrl()) ? "" : videoData.getUrl(),
                JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN,
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

        resType = videoData.getResType();
        isParyBranch = AppConfig.getInstance().getBoolean(AppConstant.IS_PARTY_BRANCH, false);
        Log.i(TAG, "isParyBranch========================" + isParyBranch);
        if (resType > 0) {
            if (isParyBranch) {
                mPresenter.getBranchResStudiedDetailRequest(videoData.getUserId(), resType, videoData.getResId());
            } else {
                mPresenter.getMemberResStudiedDetailRequest(videoData.getUserId(), resType, videoData.getResId());
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        int position = player.getCurrentPositionWhenPlaying();
        if (JCVideoPlayer.backPress()) {
            if (resType > 0) {
                StudyLogParam param = new StudyLogParam();
                param.setUserId(videoData.getUserId());
                param.setResName(videoData.getTitle());
                param.setResId(videoData.getResId());
                param.setResType(videoData.getResType());
                if (studiedDetail != null) {
                    param.setLogId(studiedDetail.getId());
                    param.setPartyBranchId(studiedDetail.getPartyBranchId());
                    param.setStudiedHours(studiedDetail.getHours());
                    param.setResTotalHours(studiedDetail.getTotalHours());
                }
                param.setDuration(Long.parseLong(position + ""));
                param.setPagePath("com.hisu.smart.dj.ui.news.activity.MediaPlayerActivity");
                param.setRemark("v1.0");
                Log.i(TAG, "progress:" + position);
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
            int mSeekTimePosition = studiedDetail.getDuration();
            player.seekToInAdvance = mSeekTimePosition;
        }
    }

    @Override
    public void returnMemberResStudiedDetail(StudiedDetailEntity studiedDetailEntity) {
        studiedDetail = studiedDetailEntity;
        if (studiedDetailEntity != null) {
            Log.i(TAG, "returnMemberResStudiedDetail========" + studiedDetail.toString());
            int mSeekTimePosition = studiedDetail.getDuration();
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
}
