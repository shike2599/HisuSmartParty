package com.hisu.smart.dj.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hisu.smart.dj.R;

/**
 *
 * @author lichee
 * @date 2018/3/13
 */

public class ProfileEdit extends LinearLayout {

    private ImageView mIconView;
    private TextView mKeyView;
    private ImageView mRightArrowView;

    public ProfileEdit(Context context) {
        super(context);
        init();
    }

    public ProfileEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_profile_edit, this
                , true);
        findAllViews();
    }

    private void findAllViews() {
        mIconView = (ImageView) findViewById(R.id.profile_icon);
        mKeyView = (TextView) findViewById(R.id.profile_key);
        mRightArrowView = (ImageView) findViewById(R.id.right_arrow);
    }

    public void set(int iconResId, String key) {
        mIconView.setImageResource(iconResId);
        mKeyView.setText(key);
    }

    public void updateKey(String value) {
        mKeyView.setText(value);
    }

    public String getValue() {
        return mKeyView.getText().toString();
    }

    protected void disableEdit() {
        mRightArrowView.setVisibility(GONE);
    }
}
