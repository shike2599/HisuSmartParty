package com.hisu.smart.dj.ui.iactive.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hisu.smart.dj.R;


public class TitleBarView extends RelativeLayout {

	private static final String TAG = "TitleBarView";
	private Context mContext;

	private TextView tv_center;
	
	private LinearLayout ll_come_back,title_btn_ll;
	
	private Button title_cfm_btn,title_left_btn,title_right_btn;
	
	private ImageView title_memu;
	private ImageView title_edit;//个人中心编辑
	
	public TitleBarView(Context context){
		super(context);
		mContext=context;
		initView();
	}
	
	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext=context;
		initView();
	}
	
	private void initView(){
		LayoutInflater.from(mContext).inflate(R.layout.common_title_bar, this);
		tv_center=(TextView) findViewById(R.id.title_txt);
		ll_come_back = (LinearLayout)findViewById(R.id.title_come_back);
		title_btn_ll = (LinearLayout)findViewById(R.id.title_btn_ll);
		title_cfm_btn = (Button)findViewById(R.id.title_cfm_btn);
		title_left_btn = (Button)findViewById(R.id.title_left_btn);
		title_right_btn = (Button)findViewById(R.id.title_right_btn);
		title_memu = (ImageView)findViewById(R.id.title_memu);
		title_edit = (ImageView)findViewById(R.id.title_edit);
	}
	
	public void setCommonTitle(int centerVisibility){
		tv_center.setVisibility(centerVisibility);
		
	}
	
	public void setTitleComeBack(int visibility){
		ll_come_back.setVisibility(visibility);
	}
	public void setTitleEditvVisibility(int visibility){
		title_edit.setVisibility(visibility);
	}

	public void setTitleText(String txtRes){
		tv_center.setText(txtRes);
	}
	
	public void setTitleText(int txtRes){
		tv_center.setText(txtRes);
	}
	
	public void setCfmBtn(int centerVisibility){
		title_cfm_btn.setVisibility(centerVisibility);
	}
	
	public void setCfmBtnText(int txtRes){
		title_cfm_btn.setText(txtRes);
	}
	
	public void setTitleMemu(int centerVisibility){
		title_memu.setVisibility(centerVisibility);
	}
	
	public void setTitleMemuImage(int resId){
		title_memu.setImageResource(resId);
	}
	
	public void destoryView(){
		tv_center.setText(null);
	}

	public void setComeBackOnclickListener(OnClickListener listener){
		ll_come_back.setOnClickListener(listener);
	}
	public void setComeBackOnclickEnable(boolean result){
		ll_come_back.setClickable(result);
	}
	
	public void setTitleMemuOnclickListener(OnClickListener listener){
		title_memu.setOnClickListener(listener);
	}
	
	public void setCenterBtn(int centerVisibility){
		title_btn_ll.setVisibility(centerVisibility);
	}
	
	public void setCenterLeftBtnText(int text){
		title_left_btn.setText(text);
	}
	
	public void setCenterRightBtnText(int text){
		title_right_btn.setText(text);
	}
	
	public void setCenterLeftBtnOnclickListener(OnClickListener listener){
		title_left_btn.setOnClickListener(listener);
	}
	
	public void setCenterRightBtnOnclickListener(OnClickListener listener){
		title_right_btn.setOnClickListener(listener);
	}
	public void setTitle_EditOnclickListener(OnClickListener listener){
		title_edit.setOnClickListener(listener);
	}
	
	public void setCenterBtnSelected(int pos){
		if(pos == 1){
			title_right_btn.setActivated(true);
			title_left_btn.setActivated(false);
		}else{
			title_left_btn.setActivated(true);
			title_right_btn.setActivated(false);
		}
	}
	public void setTitleEditvClickable(boolean clickable){
		if(clickable) {
			title_edit.setImageResource(R.mipmap.ic_list_title_edit);
		}else {
			title_edit.setImageResource(R.mipmap.ic_list_title_edit_disable);
		}
		title_edit.setClickable(clickable);
	}

	
	public Button getCenterBtnLeft(){
		return title_left_btn;
	}
	
	public Button getCenterBtnRight(){
		return title_right_btn;
	}
}
