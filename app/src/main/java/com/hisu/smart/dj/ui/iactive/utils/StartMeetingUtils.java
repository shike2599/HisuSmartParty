package com.hisu.smart.dj.ui.iactive.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.ui.iactive.activity.IactiveLoginActivity;
import com.wdliveuc.android.ActiveMeeting7.ActiveMeeting7Activity;

import java.net.URLEncoder;

import cn.com.iactive.utils.Constant;
import cn.com.iactive.utils.HttpUtil;
import cn.com.iactive.utils.ThreadPoolManager;
import cn.com.iactive.vo.MeetingInfo;

/**
 *
 * @author FQ
 * @date 14-3-21
 */
public class StartMeetingUtils {
	protected final String TAG = "StartMeeting";
	 private ThreadPoolManager threadPoolManager;
    private final static String serverudpport = "8000"; // 服务器端口
    private final static String servertcpport = "11010"; // 服务器端口

    private static final int START_OK = 1;
    private ProgressDialog progressDialog;
    public StartMeetingUtils(){
        this.threadPoolManager = ThreadPoolManager.getInstance();
    }
    Context m_context;
  	MeetingInfo m_info;
    public void start(Context context,MeetingInfo info){
    	if(!HttpUtil.hasNetwork(context)){
			return;
		}
    	m_info = info;
    	getMeetingUrl(m_info);
    	m_context = context;
    	handler.removeCallbacks(slowlyStartMeetingStatusRunnable);
    	handler.postDelayed(slowlyStartMeetingStatusRunnable, 1000);
        showProgressDialog(context);
    	
//        showProgressDialog(context);
//        String head = info.head;
//        String srvIP = info.srvIP;
//        String enterpriseName = info.enterprisename;
//        String nickname = info.nickname;
//        String username = info.username;
//       try{
//            nickname = URLEncoder.encode(info.nickname,"utf-8");
//            if(!"".equals(username)){
//                username = URLEncoder.encode(info.username,"utf-8");
//            }else{
//                username = nickname;
//            }
//        }catch (Exception e){
//        }
//        StringBuilder url = new StringBuilder(head+":");
//        url.append("SrvIP="+srvIP+"&SrvUDPPort="+serverudpport+"&SrvTCPPort="+servertcpport);
//        url.append("&Password="+ info.userpass+"&RoomID="+info.roomId+"&UserType="+info.userType);
//        url.append("&AnonymousUser="+info.isAnonymous);
//        url.append("&ClassPwd="+info.roompass+"&ServerUTF8="+info.serverUTF8+"&NameUTF8=2");
//        url.append("&nickname="+nickname);
//        url.append("&EnterpriseName="+enterpriseName);
//        url.append("&LiveUCSrvIP="+srvIP);
//        url.append("&AppVer=2");//app类型,2=会议
//        url.append("&UserName="+username);
//        if(head.equals(Constant.HEAD_LIVEUC))
//        {
//		    url.append("&master_ip0="+info.master_ip0);
//		    url.append("&master_ip1="+info.master_ip1);
//		    url.append("&master_ip2="+info.master_ip2);
//        }
//        StartTask task = new StartTask(context,url.toString());
//        threadPoolManager.addTask(task);
    }
    public static StringBuilder url;
    public static String getMeetingUrl(MeetingInfo info){
	      String head = info.head;
	      String srvIP = info.srvIP;
	      String enterpriseName = info.enterprisename;
	      String nickname = info.nickname;
	      String username = info.username;
	     try{
	          nickname = URLEncoder.encode(info.nickname,"utf-8");
	          if(!"".equals(username)){
	              username = URLEncoder.encode(info.username,"utf-8");
	          }else{
	              username = nickname;
	          }
	      }catch (Exception e){
	      }
	      url = new StringBuilder(head+":");
	      url.append("SrvIP="+srvIP+"&SrvUDPPort="+serverudpport+"&SrvTCPPort="+servertcpport);
	      url.append("&Password="+ info.userpass+"&RoomID="+info.roomId+"&UserType="+info.userType);
	      url.append("&AnonymousUser="+info.isAnonymous);
	      url.append("&ClassPwd="+info.roompass+"&ServerUTF8="+info.serverUTF8+"&NameUTF8=2");
	      url.append("&nickname="+nickname);
	      url.append("&EnterpriseName="+enterpriseName);
	      url.append("&LiveUCSrvIP="+srvIP);
	      url.append("&AppVer=2");//app类型,2=会议
	      url.append("&UserName="+username);
	      if(head.equals(Constant.HEAD_LIVEUC))
	      {
			    url.append("&master_ip0="+info.master_ip0);
			    url.append("&master_ip1="+info.master_ip1);
			    url.append("&master_ip2="+info.master_ip2);
	      }

    	 return url.toString();
    }
    
//    public static String getMeetingUrl1(Context context,String address,String username,String password){
//    	ConfigEntity configEntity = ConfigService.LoadConfig(context);
//    	String params="&srvip=";
//
//		if(ActiveMeeting7Activity.isAnonymous)
//		{// 閿熸枻鎷烽敓鏂ゆ嫹閿熼摪锟�				
//			params += address;
//			params += "&username=";
//			params += username;
//			params += "&classpwd=";
//			params += password;
//			params += "&usertype=0";
//			params += "&anonymoususer=1";
//			params += "&roomid=0";
//			params += "&password=";
//			params += password;
//		}
//		else
//		{
//			params += address;
//			params += "&username=";
//			params += username;
//			params += "&roomid=0";
//			params += "&password=";
//			params += password;
//		}
//
//		if (configEntity.applyType == ConfigEntity.AT_MEETING ) params += "&appver=2";
//		else if (configEntity.applyType == ConfigEntity.AT_EDUCATION) params += "&appver=1";
//		else params += "&appver=2";
//
//		ActiveMeeting7Activity.mbServerUtf8 = false;
//		params += "&serverutf8=0";
//		
//		params += "&srvtcpport=";
//		params += servertcpport;
//		params += "&rcvprotocol=";
//		params += configEntity.protocol;
//		params += "&sendprotocol=";
//		params += configEntity.protocolsend;// == 2?1:configEntity.protocol;
//		return params;
//    	
//    }
    
//    public static void  SetCOnnectServer(String m_params)
//	{
//		if (!ActiveMeeting7Activity.mbServerUtf8)
//		{
//			try { 
//				//ActiveMeeting7Activity.nativeConnectServerWithBuffer (params.getBytes("GBK"), ActiveMeeting7Activity.mPixelVideo, ActiveMeeting7Activity.mPixelVideo1, ActiveMeeting7Activity.mPixelScreen, 2);
//				ActiveMeeting7Activity.nativeConnectServer (m_params.getBytes("GBK"), 3, UDPServer.PORT);
//			} catch ( Exception e) {
//				e.printStackTrace();
//			}
//		}
//		else
//		{
//			//ActiveMeeting7Activity.nativeConnectServerWithBuffer (params.getBytes(), ActiveMeeting7Activity.mPixelVideo, ActiveMeeting7Activity.mPixelVideo1, ActiveMeeting7Activity.mPixelScreen, 2);
//			ActiveMeeting7Activity.nativeConnectServer (m_params.getBytes(), 3, UDPServer.PORT);
//		}
//		ActiveMeeting7Activity.nativeSetIPVersion(ActiveMeeting7Activity.m_StrIP.getBytes(),ActiveMeeting7Activity.strVersion.getBytes());
//	}

    /**
     *  显示提示框
     */
    protected void showProgressDialog(Context context) {
        if ((this.progressDialog == null)) {
            this.progressDialog = new ProgressDialog(context);
        }
        this.progressDialog.setMessage(context.getString(R.string.imm_meeting_is_starting));
        Window window =  this.progressDialog.getWindow(); 
        WindowManager.LayoutParams lp = window.getAttributes();  
     // 设置透明度 
        lp.alpha = 0.6f; 
        window.setAttributes(lp); 
        this.progressDialog.show();
    }
    
    class StartTask implements Runnable {
        private Context context;
        private String meetingUrl;
        public StartTask(Context context,String meetingUrl){
            this.context = context;
            this.meetingUrl = meetingUrl;
        }
        @Override
        public void run(){
            Message mess =new Message();
            //集成一个APP的调用方式
            Intent intent = new Intent();
            intent.setClass(context, ActiveMeeting7Activity.class);
            Uri uri = Uri.parse(meetingUrl);
            intent.setData(uri);
            try {
            	((IactiveLoginActivity)context).startActivityForResult(intent, 144);
			} catch (Exception e) {
				Log.e(TAG,e.getLocalizedMessage());
			}finally{
				mess.what = START_OK;
	            handler.sendMessage(mess);
			}
        }
    }
    public void SetStartMeeting(){
//    	String url=getURL();
        StartTask task = new StartTask(m_context,url.toString());
        threadPoolManager.addTask(task);
    }
    public final int handler_slowlyStartMeeting = 35;
  	public  Runnable	slowlyStartMeetingStatusRunnable = new Runnable() {
  		@Override
        public void run() {
  			Message message = new Message();
  			message.what = handler_slowlyStartMeeting;
  			handler.sendMessage(message);
  		}
  	};
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_OK:
                    if(progressDialog!=null){
                        progressDialog.dismiss();
                    }
                    break;
                case handler_slowlyStartMeeting:
                	handler.removeCallbacks(slowlyStartMeetingStatusRunnable);
                	SetStartMeeting();
                	break;
                default:
                    break;
            }
        }
    };
    
}
