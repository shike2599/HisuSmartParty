package com.hisu.smart.dj.ui.iactive.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.ui.iactive.utils.StringUtils;

import java.util.List;

import cn.com.iactive.utils.CommonUtil;
import cn.com.iactive.utils.Constant;
import cn.com.iactive.utils.SpUtil;
import cn.com.iactive.utils.StartMeeting;
import cn.com.iactive.vo.MeetingInfo;
import cn.com.iactive.vo.Room;

public class JoinRoomAdapter extends BaseAdapter{
	protected static final String TAG = "JoinRoomAdapter";
	private Context mContext;
	private List<Room> lists;
	//private int demoRoomId = 1275;
	private SharedPreferences sp;
	
	private String username;
	private String userpass;
	private String enterprisename = "";
	
	public JoinRoomAdapter(Context context, List<Room> lists)
	{
		this.mContext = context;
		this.lists = lists;
		//demoRoomId = Integer.parseInt(mContext.getString(R.string.demo_room_id));
		sp = SpUtil.getSharePerference(context);
		
		username = sp.getString("loginname","");
		userpass = sp.getString("password","");
		enterprisename = sp.getString("enterprisename","");
	}

	@Override
	public int getCount() {
		if (lists != null) {
			return lists.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return lists.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder holder;
		final Room room = lists.get(position);
		if(convertView == null){
			//convertView = View.inflate(mContext, R.layout.fragment_join_list_item, null);
			convertView = LayoutInflater.from(mContext).inflate(R.layout.join_list_item, null);
			holder = new Holder();
			holder.join_item_room_name = (TextView)convertView.findViewById(R.id.join_item_room_name);
			holder.join_item_room_id = (TextView)convertView.findViewById(R.id.join_item_room_id);
			holder.join_item_room_time = (TextView)convertView.findViewById(R.id.join_item_room_time);
			holder.room_info_title_icon = (ImageView)convertView.findViewById(R.id.room_info_title_icon);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.join_item_room_name.setText(room.roomName);
		holder.join_item_room_id.setText(room.roomId+"");
		//holder.join_item_room_time.setText(room.startTime);
		String strTOnline = room.nTOnline > 0 ? mContext.getResources().getString(R.string.imm_m_host_online) : mContext.getResources().getString(R.string.imm_m_host_outline);
		holder.join_item_room_time.setText("(" + room.onlineUser + "/" + room.nTotalUser + ")" + strTOnline);
		
		if(room.roomId==1275){
			holder.room_info_title_icon.setImageResource(R.mipmap.join_list_icon_title_sd);
		} else if(room.roomId==1276){
			holder.room_info_title_icon.setImageResource(R.mipmap.join_list_icon_title_hd);
		} else if(room.roomType==2){
			holder.room_info_title_icon.setImageResource(R.mipmap.join_list_icon_title_anyone);
		} else{
			holder.room_info_title_icon.setImageResource(R.mipmap.join_list_icon_title_anyone);
		}
		/*holder.join_list_item_layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startMeeting(room);
			}
		});*/
		return convertView;
	}
	
	private void startMeeting(Room room){
		StartMeeting startMeeting = new StartMeeting();
        MeetingInfo meetingInfo = new MeetingInfo();
        meetingInfo.roomId = room.roomId;
        meetingInfo.roompass = room.roomPass;
        String nickname = sp.getString("nickname","");
        if(StringUtils.isNotBlank(enterprisename)){
        	meetingInfo.enterprisename = enterprisename;
        	username = sp.getString("orgloginname","");
        }
        if(room.isPermRoom == 1 || room.userRole == Constant.ROOM_USER_TYPE_HOST){
        	meetingInfo.isAnonymous = 0;
        	//meetingInfo.username = room.hostName;
        	//meetingInfo.userpass = room.hostPass;
        	meetingInfo.username = username;
        	meetingInfo.userpass = userpass;
        	if(nickname == null || "".equals(nickname)){
        		nickname = room.hostName;
        	}
        }else{
        	meetingInfo.isAnonymous = 1;
        	if(nickname == null || "".equals(nickname)){
        		nickname = "guest"+ CommonUtil.getRandomCode(4);
        	}
        }
        meetingInfo.nickname = nickname;
        meetingInfo.userType = room.userRole;
        startMeeting.start(mContext,meetingInfo);
	}
	
	class Holder{
		TextView join_item_room_name;
		TextView join_item_room_id;
		TextView join_item_room_pass;
		TextView join_item_room_time;
		ImageView room_info_title_icon;
	}
	

	
}
