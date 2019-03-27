package com.hisu.smart.dj.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hisu.smart.dj.R;


public class CommomDialog extends Dialog implements View.OnClickListener{
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private View centen_line;
    private boolean isShowCancel;
    public CommomDialog(Context context) {
        super(context);
        this.mContext = context;
    }
    public CommomDialog(Context context, String content) {
        super(context, R.style.dialog);
        this.mContext = context;
        this.content = content;
    }

    public CommomDialog(Context context, int themeResId, String content) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
    }

    public CommomDialog(Context context, int themeResId, String content, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.content = content;
        this.listener = listener;
    }

    protected CommomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public CommomDialog setTitle(String title){
        this.title = title;
        if(titleTxt!=null){
            titleTxt.setText(title);
        }
        return this;
    }
    public CommomDialog setContent(String content){
        this.content = content;
        if(contentTxt!=null){
            contentTxt.setText(content);
        }
        return this;
    }
    public CommomDialog setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public CommomDialog setNegativeButton(String name){
        this.negativeName = name;
        if(cancelTxt!=null){
            cancelTxt.setText(name);
        }
        return this;
    }
   public CommomDialog isShowCancelBtn(boolean isshow){
        this.isShowCancel = isshow;
        if(cancelTxt!=null){
            if(isshow){
                cancelTxt.setVisibility(View.VISIBLE);
                centen_line.setVisibility(View.VISIBLE);
            }else{
                cancelTxt.setVisibility(View.GONE);
                centen_line.setVisibility(View.GONE);
            }
        }
        return this;
   }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_commom);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView(){
        centen_line = findViewById(R.id.common_segmentation_line);
        contentTxt = (TextView)findViewById(R.id.content);
        titleTxt = (TextView)findViewById(R.id.title);
        submitTxt = (TextView)findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        cancelTxt = (TextView)findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
        }

        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }

        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }
        if(isShowCancel){
            cancelTxt.setVisibility(View.VISIBLE);
            centen_line.setVisibility(View.VISIBLE);
        }else{
            cancelTxt.setVisibility(View.GONE);
            centen_line.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel:
                if(listener != null){
                    listener.onClick(this, false);
                }
                this.dismiss();
                break;
            case R.id.submit:
                if(listener != null){
                    listener.onClick(this, true);
                }
                break;
        }
    }

    public interface OnCloseListener{
        void onClick(Dialog dialog, boolean confirm);
    }


}
