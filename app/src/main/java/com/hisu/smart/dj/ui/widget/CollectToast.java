package com.hisu.smart.dj.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hisu.smart.dj.R;


public class CollectToast {
    private Context context;
    private Dialog dialog;
    private View view;
    private LinearLayout layout;
    private ImageView image;
    private String content;
    private boolean isCollect;
    private TextView contentText;
    public CollectToast(Context context) {
        this.context = context;
    }
    public void setContext(String content){
        this.content = content;
    }
    public void setIsCollect(Boolean isCollect){
        this.isCollect = isCollect;
    }
    public CollectToast builder() {
        view = LayoutInflater.from(context).inflate(R.layout.collection_anim_dialog, null);
        dialog = new Dialog(context, R.style.CollectionAnimDialogStyle);
        layout = (LinearLayout) view.findViewById(R.id.layout_ToastCollection);
        layout.getBackground().setAlpha(200);//0~255透明度值
        image = (ImageView) view.findViewById(R.id.image_ToastCollection);
        contentText = view.findViewById(R.id.tip_ToastCollection);
        contentText.setText(content);
        if(isCollect){
            image.setBackgroundResource(R.mipmap.links_icon);
        }else{
            image.setBackgroundResource(R.mipmap.pre_likes);
        }

        dialog.setContentView(view);
        return this;
    }

    public void show() {
        dialog.show();
        collectionAnim();
    }

    /**
     * 收藏动画
     */
    private void collectionAnim() {
        AlphaAnimation alpha = new AlphaAnimation(1, 0);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);
        alpha.setRepeatCount(0);
        layout.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dialog.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
