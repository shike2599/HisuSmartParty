package com.hisu.smart.dj.ui.iactive.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.app.AppConfig;
import com.hisu.smart.dj.ui.iactive.utils.ConfigService;
import com.hisu.smart.dj.ui.iactive.utils.StartMeetingUtils;
import com.hisu.smart.dj.ui.iactive.utils.StringUtils;
import com.hisu.smart.dj.ui.iactive.view.TitleBarView;
import com.wdliveuc.android.ActiveMeeting7.ActiveMeeting7Activity;
import com.wdliveuc.android.ActiveMeeting7.BaseActivity;
import com.wdliveuc.android.ActiveMeeting7.ClassList.ClassInfo;
import com.wdliveuc.android.ActiveMeeting7.Login1Activity;
import com.wdliveuc.android.domain.ConfigEntity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.com.iactive.utils.CommonUtil;
import cn.com.iactive.utils.Constant;
import cn.com.iactive.utils.MeetingListBack;
import cn.com.iactive.vo.MeetingInfo;
import cn.com.iactive.vo.Room;


/**
 * @author lichee
 */
public class IactiveLoginActivity extends BaseActivity {

	private Button mLogin;
	private EditText et_firm_name,et_username, et_password;
	private SharedPreferences sp;
	private TitleBarView mTitleBarView;
	private TextView tv_login_version;
	private ConfigEntity configEntity;
	public static String iac_ser_ip="";
	public static String iac_username="";
	public static String iac_password="";

	public static void startAction(Activity activity){
		Intent intent = new Intent(activity, IactiveLoginActivity.class);
		activity.startActivity(intent);
	}

			
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.firm_login	:
			String firmName = et_firm_name.getText().toString().trim();
			String username = et_username.getText().toString().trim();
			String password = et_password.getText().toString().trim();

			if (StringUtils.isBlank(firmName)) {
				CommonUtil.showToast(this,
						getString(R.string.login_firm_name_isnull),
						Toast.LENGTH_LONG);
			}
			if (username == null || "".equals(username)) {
				CommonUtil.showToast(this,
						getString(R.string.login_firm_username_hint),
						Toast.LENGTH_LONG);
				return;
			}
			if (password == null || "".equals(password)) {
				CommonUtil.showToast(this,
						getString(R.string.login_pass_isnull),
						Toast.LENGTH_LONG);
				return;
			}
			if(!StringUtils.isNumberOrChar(password)){
				CommonUtil.showToast(this,getString(R.string.login_pass_is_only_number_or_char), Toast.LENGTH_LONG);
				return;
			}
			if(!ActiveMeeting7Activity.GetClearGCFinish()){
				CommonUtil.showToast(this,"没有清理完全内存", Toast.LENGTH_LONG);
				return;
			}

			try {
				String enterprisename = firmName;
				String strname = username;
			} catch (Exception e) {
			}
			iac_ser_ip = firmName;
			iac_username = username;
			iac_password = password;
			AppConfig.getInstance().setString("enterprisename", "");
			AppConfig.getInstance().setString("join_et_server1", iac_ser_ip);
			AppConfig.getInstance().setString("join_et_nickname", iac_username);
			AppConfig.getInstance().setString("join_et_roompass", iac_password);
			StartMeetingUtils startMeeting = new StartMeetingUtils();
			MeetingInfo info = new MeetingInfo();
			info.head = Constant.HEAD_ACTIVEMEETING;
			info.srvIP = firmName;
			info.enterprisename = "";
			info.username = username;
			info.userpass = password;
			info.isAnonymous = 0;
			info.serverUTF8 = 0;
			startMeeting.start(IactiveLoginActivity.this, info);
//			String meetingUrl=startMeeting.getMeetingUrl(info); 
//			Intent intent = new Intent();
//	        intent.setClass(IactiveLoginActivity.this, ActiveMeeting7Activity.class);
//	        Uri uri = Uri.parse(meetingUrl);
//	        intent.setData(uri);
//	        startActivityForResult(intent, 144);
			break;
		}
	}
	private ProgressDialog m_progressDialog;
	protected void showprogressDialog(Context context) {
      if ((this.m_progressDialog == null)) {
          this.m_progressDialog = new ProgressDialog(this);
      }
      this.m_progressDialog.setMessage(context.getString(R.string.imm_meeting_is_starting));
      Window window =  this.m_progressDialog.getWindow(); 
      WindowManager.LayoutParams lp = window.getAttributes();  
   // 设置透明度 
      lp.alpha = 0.6f; 
      window.setAttributes(lp); 
      this.m_progressDialog.show();
  }

	@Override
	protected void findViewById() {
		mLogin = (Button) findViewById(R.id.firm_login);
		et_firm_name = (EditText) findViewById(R.id.firmName);
		et_username = (EditText) findViewById(R.id.firmUsername);
		et_password = (EditText) findViewById(R.id.firmPassword);
		mTitleBarView = (TitleBarView)findViewById(R.id.title_bar);
		tv_login_version=(TextView) findViewById(R.id.tv_login_version);
		initTitle();
		configEntity = ConfigService.LoadConfig(this);
		configEntity.isLiveUC = false;
		ConfigService.SaveConfig(this, configEntity);
		Login1Activity.m_MeetingListBack = new MeetingListBack() {
			@Override
			public void finishMeetingListBack() {
				// TODO Auto-generated method stub
				
				List<Room> roomList=new ArrayList<Room>();
    			List<ClassInfo> classList = null;
    			if(ActiveMeeting7Activity.mClassList.roomList.size()>0){
    				for(ClassInfo info: ActiveMeeting7Activity.mClassList.roomList){
    						Room room=new Room();
    						room.roomId=info.roomid;
    						room.roomName=info.roomName;
    						room.roomPass=info.roomPass;
    						room.nTOnline=info.nTOnline;
    						room.onlineUser=info.nOnlineUser;
    						room.nTotalUser=info.nTotalUser;
    						roomList.add(room);
    				}
    			}
    			Intent intent=new Intent(Login1Activity.m_LoginActivity,JoinRoomListActivity.class);
    			intent.putExtra("roomList", (Serializable)roomList);
    			startActivityForResult(intent, Login1Activity.REQ_LOGIN_list);
			}
			//点击上传图片之后的响应事件
			@Override
			public void ClickUploadPhoto() {
				// TODO Auto-generated method stub
				Log.i("", "interface ClickUploadPhoto 点击");
				//判断这个activity是否为控，如果不为空，可以通过这个Activity跳转：ActiveMeeting7Activity.m_ActiveMeeting7Activity
			}
			//点击上传图片之后的响应事件
			@Override
			public void Medicaladvice() {
				// TODO Auto-generated method stub
				Log.i("", "interface inMedicaladvice 点击");
				//判断这个activity是否为控，如果不为空，可以通过这个Activity跳转：ActiveMeeting7Activity.m_ActiveMeeting7Activity
			}
			@Override
			public void Meetinglistback() {
				// TODO Auto-generated method stub
				//如果没有传会议室id。则显示会议室列表。否则。直接入会
			}
			//用户退出会议室
			@Override
			public void MeetingExit() {
				// TODO Auto-generated method stub
				Log.i("", "interface MeetingExit 退会");
			}
		};
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_org_login);
	}

	@Override
	protected void processLogic() {
		tv_login_version.setText(this.getString(R.string.setting_version)+ CommonUtil.getAppVersionName(this));
		iac_ser_ip =  AppConfig.getInstance().getString("join_et_server1", "mcu.iactive.com.cn");
		iac_username = AppConfig.getInstance().getString("join_et_nickname", "");
		iac_password = AppConfig.getInstance().getString("join_et_roompass", "");
		et_firm_name.setText(iac_ser_ip);
		et_username.setText(iac_username);
		et_password.setText(iac_password);
	}

	@Override
	protected void setListener() {

		mLogin.setOnClickListener(this);
			
	}


	
	private void initTitle(){
		mTitleBarView.setVisibility(View.VISIBLE);
		mTitleBarView.setCommonTitle(View.VISIBLE);
		mTitleBarView.setTitleText(R.string.login_memu_title);
		mTitleBarView.setComeBackOnclickListener(comeBackClickListener);
	}
	
	private OnClickListener comeBackClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			comeback();
		}
	};

	
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	 comeback();
        }
        return true;
    }
    
    private void comeback(){
    	finish();
    }
    private void hiddenInputMode() {
		// 隐藏输入法
		InputMethodManager imm = (InputMethodManager) this.getApplicationContext().getSystemService(
						Context.INPUT_METHOD_SERVICE);
		// 显示或者隐藏输入法
		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    }
}
