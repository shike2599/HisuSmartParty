package com.hisu.smart.dj.ui.iactive.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.ui.iactive.view.TitleBarView;
import com.jaydenxiao.common.commonutils.StatusBarUtil;
import com.wdliveuc.android.ActiveMeeting7.ActiveMeeting7Activity;
import com.wdliveuc.android.ActiveMeeting7.BaseActivity;
import com.wdliveuc.android.domain.ConfigEntity;
import com.wdliveuc.android.domain.ConfigService;

import cn.com.iactive.utils.CommonUtil;
import cn.com.iactive.utils.Constant;
import cn.com.iactive.utils.StartMeeting;
import cn.com.iactive.utils.immStringUtils;
import cn.com.iactive.vo.MeetingInfo;

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

    private ImageView back_img;
    private TextView title_text;

	public static boolean m_ShowJoinRoomList=false;



	public static void startAction(Activity activity){
		Intent intent = new Intent(activity, IactiveLoginActivity.class);
		activity.startActivity(intent);
	}




	@Override
	public void onClick(View v) {
		m_ShowJoinRoomList=false;
		switch (v.getId()) {
		case R.id.firm_login	:
			m_ShowJoinRoomList=false;
			String firmName = et_firm_name.getText().toString().trim();
			String username = et_username.getText().toString().trim();
			String password = et_password.getText().toString().trim();
			
			if (immStringUtils.isBlank(firmName)) {
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
//			if(!immStringUtils.isNumberOrChar(password)){
//				CommonUtil.showToast(this,getString(R.string.login_pass_is_only_number_or_char), Toast.LENGTH_LONG);
//				return;
//			}
			if(!ActiveMeeting7Activity.GetClearGCFinish()){
				CommonUtil.showToast(this,"没有清理完全内存", Toast.LENGTH_LONG);
				return;
			}

			SharedPreferences.Editor ed = sp.edit();
			iac_ser_ip=firmName;
			iac_username=username;
			iac_password=password;
			ed.putString("enterprisename", "");
			ed.putString("join_et_server1", iac_ser_ip);
			ed.putString("join_et_nickname", iac_username);
			ed.putString("join_et_roompass", iac_password);
			ed.commit();
			StartMeeting startMeeting = new StartMeeting();
	        MeetingInfo meetingInfo = new MeetingInfo();
	        if(iac_ser_ip.equals("master.liveuc.net")) {
				meetingInfo.head = Constant.HEAD_LIVEUC;
				meetingInfo.serverUTF8 = 1;
			}else {
				meetingInfo.head = Constant.HEAD_ACTIVEMEETING;
				meetingInfo.serverUTF8 = 0;
			}
	        
//	        meetingInfo.roomId = room.roomId;
	        String nickname = username;
	    	//meetingInfo.username = room.hostName;
	    	//meetingInfo.userpass = room.hostPass;
	    	meetingInfo.username = username;
	    	meetingInfo.userpass = password;
//	        meetingInfo.nickname = nickname;
	        meetingInfo.isAnonymous=0;
	        meetingInfo.srvIP=firmName;
	        if(nickname.contains("@")){
				String[] strserver = ActiveMeeting7Activity.getNode(nickname, "@");
				if (strserver != null&&strserver.length==2) {
					nickname=strserver[1]+"|"+strserver[0];
				}
			}
            meetingInfo.nickname = nickname;
	        startMeeting.start(IactiveLoginActivity.this,meetingInfo);
			
//			StartMeetingUtils startMeeting = new StartMeetingUtils();
//			MeetingInfo info=new MeetingInfo();
//			info.head=Constant.HEAD_ACTIVEMEETING;
//			info.srvIP=firmName;
//			info.enterprisename="";
//			info.username=username;
//			info.userpass=password;
//			info.isAnonymous=0;
//			info.serverUTF8=0;
//			info.roomId=3;
//			startMeeting.start(IactiveLoginActivity.this, info);
//			String meetingUrl=startMeeting.getMeetingUrl(info); 
//			Intent intent = new Intent();
//	        intent.setClass(IactiveLoginActivity.this, ActiveMeeting7Activity.class);
//	        Uri uri = Uri.parse(meetingUrl);
//	        intent.setData(uri);
//	        startActivityForResult(intent, 144);
			break;
		case R.id.back_imageView:
			 IactiveLoginActivity.this.finish();
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
		StatusBarUtil.setStatusBarMode(this, true, com.jaydenxiao.common.R.color.white);
		mLogin = (Button) findViewById(R.id.firm_login);
		et_firm_name = (EditText) findViewById(R.id.firmName);
		et_username = (EditText) findViewById(R.id.firmUsername);
		et_password = (EditText) findViewById(R.id.firmPassword);
		mTitleBarView = (TitleBarView)findViewById(R.id.title_bar);
		tv_login_version=(TextView) findViewById(R.id.tv_login_version);
		back_img = findViewById(R.id.back_imageView);
		title_text = findViewById(R.id.title_TextView);
		initTitle();
		configEntity = ConfigService.LoadConfig(this);
		configEntity.isLiveUC=false;
		ConfigService.SaveConfig(this, configEntity);
		//如果没有传会议室id。则会返回会议室列表界面。
//		Login1Activity.m_MeetingListBack=new MeetingListBack() {
//			
//			@Override
//			public void finishMeetingListBack() {
//				// TODO Auto-generated method stub
//			}
//			//暂时没用
//			@Override
//			public void ClickUploadPhoto() {
//				// TODO Auto-generated method stub
//			}
//			//暂时没用
//			@Override
//			public void Medicaladvice() {
//				// TODO Auto-generated method stub
//			}
//			@Override
//			public void Meetinglistback() {
//				// TODO Auto-generated method stub
//				//如果没有传会议室id。则显示会议室列表。否则。直接入会
//			}
//			//用户退出会议室
//			@Override
//			public void MeetingExit() {
//				// TODO Auto-generated method stub
//			}
//		};
	}

	@Override
	protected void loadViewLayout() {
		setContentView(R.layout.activity_org_login);
	}

	@Override
	protected void processLogic() {
		sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
		tv_login_version.setText(this.getString(R.string.setting_version)+ CommonUtil.getAppVersionName(this));
		iac_ser_ip = sp.getString("join_et_server1", "mcu.iactive.com.cn");
		iac_username = sp.getString("join_et_nickname", "");
		iac_password = sp.getString("join_et_roompass", "");
		et_firm_name.setText(iac_ser_ip);
		et_username.setText(iac_username);
		et_password.setText(iac_password);
	}

	@Override
	protected void setListener() {

		mLogin.setOnClickListener(this);
		back_img.setOnClickListener(this);
			
	}


	
	private void initTitle(){
		title_text.setText("视频会议系统");
//		mTitleBarView.setVisibility(View.VISIBLE);
//		mTitleBarView.setCommonTitle(View.VISIBLE);
		mTitleBarView.setTitleText(R.string.login_memu_title);
//		mTitleBarView.setComeBackOnclickListener(comeBackClickListener);
	}
	
	private OnClickListener comeBackClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//Intent intent = new Intent(this,MainActivity.class);
			//startActivity(intent);
			comeback();
		}
	};

	
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	finish();
//        	System.exit(0);
        }
        return true;
    }
    
    
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
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
