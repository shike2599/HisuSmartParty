package com.hisu.smart.dj.ui.iactive.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hisu.smart.dj.R;
import com.hisu.smart.dj.ui.iactive.adapter.JoinRoomAdapter;
import com.hisu.smart.dj.ui.iactive.view.TitleBarView;
import com.wdliveuc.android.ActiveMeeting7.ActiveMeeting7Activity;
import com.wdliveuc.android.ActiveMeeting7.BaseActivity;
import com.wdliveuc.android.ActiveMeeting7.Login1Activity;

import java.util.ArrayList;
import java.util.List;

import cn.com.iactive.vo.Room;


public class JoinRoomListActivity extends BaseActivity {
	private ListView mCustomListView;
	private JoinRoomAdapter joinRoomAdapter;
	private TitleBarView mTitleBarView;
	
	private View joinRoomEdit;
	private LinearLayout join_list_item_footer_layout;
	
	private List<Room> roomList = new ArrayList<Room>();
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void findViewById() {
		// TODO Auto-generated method stub
		LayoutInflater inflater=getLayoutInflater();
		joinRoomEdit = inflater.inflate(R.layout.join_list_item_footer_other_room, null);
		//join_list_item_textviewfooter = inflater.inflate(R.layout.join_list_item_textviewfooter, null);
		join_list_item_footer_layout=(LinearLayout)joinRoomEdit.findViewById(R.id.join_list_item_footer_layout);
		mCustomListView = (ListView)findViewById(R.id.lv_join_roomList);
		mCustomListView.setSelector(getResources().getDrawable(R.drawable.custome_listview_item_seletor));
		mTitleBarView = (TitleBarView)findViewById(R.id.title_bar);
	}

	@Override
	protected void loadViewLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.fragment_join_list); 
	}

	@Override
	protected void processLogic() {
		// TODO Auto-generated method stub
		
		Intent intent=this.getIntent();
		try {
			roomList=(List<Room>) intent.getSerializableExtra("roomList");
			joinRoomAdapter = new JoinRoomAdapter(this, roomList);
			mCustomListView.setAdapter(joinRoomAdapter);
			initTitle();
			addFooter();
		}catch(Exception e) {
			
		}
		
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // android.util.Log.d("ActiveMeeting7","Key Event=" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
        	new AlertDialog.Builder(this).setTitle(getString(R.string.imm_prompt)).setMessage(getString(R.string.imm_exit_not1))
            .setPositiveButton(getString(R.string.imm_ok), new DialogInterface.OnClickListener() {
                @Override
				public void onClick(DialogInterface dialogInterface, int i) {
                	finish();
                	Login1Activity.m_LoginActivity.finish();
                }
            }).setNegativeButton(getString(R.string.imm_cancel1), new DialogInterface.OnClickListener() 
            {
                @Override
				public void onClick(DialogInterface dialogInterface, int i)
                {
                    // finish();
                }
            }).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void setListener() {
		// TODO Auto-generated method stub
		mCustomListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if(id != -1){
					final Room room = roomList.get((int) id);
					if(room.roomPass!=null)
					{
						// 閿熸枻鎷烽敓鏂ゆ嫹涓�敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鏇伴敓鏂ゆ嫹閿燂拷閿熸枻鎷烽敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹
						final EditText editPwd = new EditText(JoinRoomListActivity.this);
						new AlertDialog.Builder(JoinRoomListActivity.this).setTitle(getString(R.string.imm_meeting_password_title))
						.setView(editPwd)
						.setPositiveButton(getString(R.string.imm_ok), new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							    try {
							        ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editPwd.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
							    } catch (Exception e) {
							        e.printStackTrace();
							    }
							    
							    String password = editPwd.getText().toString();
							    if ("".equals(password)) {
							        Toast.makeText(getApplicationContext(), getResources().getString(R.string.imm_password_no_null), Toast.LENGTH_SHORT).show(); return;
							    }
							    
								if (ActiveMeeting7Activity.nativeLoginRoom (room.roomId, password.getBytes(), password.length()) == 0) {
								    //showMeeting = false;

								    Intent data = new Intent();
								    data.putExtra("roomName", room.roomName);
								    ActiveMeeting7Activity.mRoomName=room.roomName+"";
								    ActiveMeeting7Activity.m_roomid=room.roomId+"";
								    setResult(RESULT_OK, data);
								    finish();
								}
								else {
								    Toast.makeText(getApplicationContext(), getResources().getString(R.string.imm_room_password_error), Toast.LENGTH_SHORT).show();
								}
							}
						})
						.setNegativeButton(getString(R.string.imm_cancel1), new DialogInterface.OnClickListener() {
	                        @Override
							public void onClick(DialogInterface dialog, int which) {
	                            try {
	                                ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(editPwd.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	                            } catch (Exception e) {
	                                e.printStackTrace();
	                            }
	                        }
	                    })
						.show();
					}else{
						startMeeting(room);
					}
				}
				
			}
		});
		mTitleBarView.setComeBackOnclickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				exitLogin();
			}
		});
	}
	private void initTitle() {
		mTitleBarView.setCommonTitle(View.VISIBLE);
		mTitleBarView.setTitleText(R.string.join_room_btn_title);
		mTitleBarView.setTitleComeBack(View.VISIBLE);
	}
	private void addFooter(){
//		mCustomListView.addFooterView(joinRoomEdit);
	}
	
	private void startMeeting(Room room){
		ActiveMeeting7Activity.nativeLoginRoom (room.roomId, null, 0);
//		showMeeting = false;
		ActiveMeeting7Activity.m_roomid=room.roomId+"";
		ActiveMeeting7Activity.mRoomName=room.roomName+"";
		Intent data = new Intent();
		data.putExtra("roomName", room.roomName);
		setResult(RESULT_OK, data);
		/*RESULT_OK*/
		finish();
//        Intent data = new Intent();
//        data.putExtra("roomName", room.roomName);
//        setResult(RESULT_OK, data);
//        finish();
       }
	private void exitLogin(){
		finish();
    	Login1Activity.m_LoginActivity.finish();
	}
}
