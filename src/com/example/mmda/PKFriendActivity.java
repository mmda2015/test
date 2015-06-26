package com.example.mmda;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;




import com.example.mmda.SingleSubActivity.Myadapter;
import com.until.*;
import com.example.mmda.BaseActivity;




import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PKFriendActivity extends BaseActivity {
	
	private ListView playerlist;
	LayoutInflater inflater1;
	TextView friend;
	ImageButton pkBtn;
	Myadapter adapter1;
	List<Map<String, Object>> mList;
	private ImageButton backBtn, freshBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_pkfriend);
		init();
		freshBtn = (ImageButton) findViewById(R.id.fresh);
		backBtn = (ImageButton) findViewById(R.id.back);
	
	
	backBtn.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//isToNextAct = true;
			Intent intent = new Intent();
			intent.setClass(PKFriendActivity.this, OnlineMainActivity.class);
			startActivity(intent);
			Toast.makeText(PKFriendActivity.this, "返回到游戏大厅  ! !", 1).show();
			finish();
		}
	});
		 }
	private void init() {
		
		playerlist = (ListView) findViewById(R.id.listView2);
		inflater1 = LayoutInflater.from(this);		
		
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				con.getFriendList();
				timer.cancel();
			}
		};
		timer.schedule(timerTask, 1000);

		/**
		 * 返回按钮监听
		 * */
		

		/**
		 * 刷新按钮监听
		 * */
		
		/**
		 * 玩家列表按钮监听
		 * */
//		playerBtn.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				playerBtn.setBackgroundResource(R.drawable.playerlist_btn1);
//				friendBtn.setBackgroundResource(R.drawable.friend_list_btn0);
//				flag = 0;
//				con.getPlayerList();
////				adapter = new Myadapter();
////				playerlist.setAdapter(adapter);
//			}
//		});
		/**
		 * 好友列表按钮监听
		 * */
		/*friendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				playSoundButton1();
				playerBtn.setBackgroundResource(R.drawable.playerlist_btn0);
				friendBtn.setBackgroundResource(R.drawable.friend_list_btn1);
				flag = 1;
				con.getFriendList();
//				 adapter = new Myadapter();
//				 playerlist.setAdapter(adapter);
			}
		});*/

	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pkfriend, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	class Myadapter extends BaseAdapter {

		public Myadapter() {

		}

		public int getCount() {

			return mList.size();
		}

		public Object getItem(int position) {

			return position;
		}

		public long getItemId(int position) {

			return position;
		}
		@SuppressLint("ViewHolder")
		public View getView(final int position, View convertView,
				ViewGroup parent) {

//			if (convertView == null) {
//				// 列表布局
				convertView = inflater1.inflate(R.layout.friend_list, null);
//			}
			friend = (TextView) convertView.findViewById(R.id.friendName);
			pkBtn = (ImageButton) convertView.findViewById(R.id.imgbtnhaoyou);
		
				/*if (position == 1){
					friend.setText("大陆音乐");
				}else {
					friend.setText("港台音乐");
				}*/
				
			pkBtn.setVisibility(View.VISIBLE);
			System.out.println((String) mList.get(position).get("friendName"));
			friend.setText((String) mList.get(position).get("friendName"));
			
			// 以分数的多少设置玩家的等级
			//int score = (Integer) mList.get(position).get("score");
			//level.setText(Playerlevel.getLevel(score));
			// 添加玩家为好友按钮监听
			pkBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//playSoundButton1();
					
					//点击PK,得到选中好友的名字
					String playerName = (String)
					mList.get(position).get("friendName");
					con.yaoZhan(playerName,Constant.userName,1);
					//Toast.makeText(FriendListActivity.this, "check info", 1).show();
					//con.getshop((String) mList.get(position).get("friendName"));
//					con.addFriend(playerName);
					
				}
			});
			return convertView;
		}
	}
	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("执行到消息处理啦");
		//String dengji = Playerlevel.getLevel(Constant.score);
		switch (message.what) {
		// 玩家信息处理
		case Config.REQUEST_GET_USERS_ONLINE:
			mList = (List<Map<String, Object>>) message.obj;
			adapter1 = new Myadapter();
			playerlist.setAdapter(adapter1);
			adapter1.notifyDataSetChanged();
			System.out.println("消息处理完了玩家列表哟。。。");
			Toast.makeText(PKFriendActivity.this, "获取玩家列表成功", 1000).show();
			break;

		// 好友信息处理
		case Config.REQUEST_GET_FRIEND:
			mList = (List<Map<String, Object>>) message.obj;
			adapter1 = new Myadapter();
			playerlist.setAdapter(adapter1);
			adapter1.notifyDataSetChanged();
			System.out.println("消息处理完了好友列表哟。。。");
			Toast.makeText(PKFriendActivity.this, "获取好友列表成功", 1000).show();
			break;
		case Config.REQUEST_INVITE_RESULT:
			int result_2 = message.arg1;
			if(result_2 == Config.SUCCESS){
				//XLXF_KuaisuMainActivity.flagNo = 1;
				Intent intent = new Intent(PKFriendActivity.this,OnlineGameActivity.class);
				startActivity(intent);
				finish();
			}
			else
			{
				Toast.makeText(PKFriendActivity.this, "邀战好友，失败", Toast.LENGTH_SHORT).show();	
			}
		break;
		}
	}
}
