package com.hisu.smart.dj.ui.news.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConstant;
import com.hisu.smart.dj.entity.InformationEntity;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class MediaPlayerActivity extends AppCompatActivity {

    private JCVideoPlayerStandard player;
    private InformationEntity videoData;
    private final static String TAG = "MediaPlayerActivity";

    public static void startAction(Activity activity, InformationEntity data){
        Intent intent = new Intent(activity, MediaPlayerActivity.class);
        intent.putExtra(AppConstant.VIDEO,data);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        player = findViewById(R.id.new_video_player);
        player.fullscreenButton.setVisibility(View.GONE);
        player.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        videoData = (InformationEntity) getIntent().getSerializableExtra(AppConstant.VIDEO);
        Log.i(TAG,"url========"+videoData.getUrl());
        boolean setUp = player.setUp(
               TextUtils.isEmpty(videoData.getUrl())?"":videoData.getUrl() ,
                JCVideoPlayer.SCREEN_WINDOW_FULLSCREEN,
                TextUtils.isEmpty(videoData.getName())?"":videoData.getName());
        if (setUp) {
            Glide.with(this)
                    .load(videoData.getIcon())
                    .apply(new RequestOptions()
                            .error(com.jaydenxiao.common.R.drawable.no_content_tip)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(com.jaydenxiao.common.R.drawable.no_content_tip))
                    .into(player.thumbImageView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
}
