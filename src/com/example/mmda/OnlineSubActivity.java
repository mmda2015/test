package com.example.mmda;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mmda.R;

import com.until.Constant;


public class OnlineSubActivity extends ActionBarActivity {

	private ListView playerlist;
	LayoutInflater inflater;
	TextView topic;
	ImageButton inviteBtn;
	Myadapter adapter;
	private ImageButton backBtn, freshBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_online_sub);
		
		playerlist = (ListView) findViewById(R.id.listViewOnlilne);
		
		freshBtn = (ImageButton) findViewById(R.id.fresh);
		backBtn = (ImageButton) findViewById(R.id.back);
		inflater = LayoutInflater.from(this);
		
		adapter = new Myadapter();
		playerlist.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.online_sub, menu);
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
	
	public void onClick(View view) {
		choiceDialog();
	}
	
	private void choiceDialog(){
		Dialog dialog = new AlertDialog.Builder(OnlineSubActivity.this)
			.setTitle("竞争者选择")		// 创建标题
			//.setMessage("") // 表示对话框中的内容
			//.setIcon(R.drawable.img12) // 设置LOGO
			.setPositiveButton("随机匹配", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent(OnlineSubActivity.this,RandomMatchActivity.class);
					startActivity(intent);
					finish() ;	// 操作结束
				}
			}).setNegativeButton("挑战好友", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.setClass(OnlineSubActivity.this, PKFriendActivity.class);
					startActivity(intent);
					finish();
				}
			}).create(); // 创建了一个对话框
		dialog.show() ;	// 显示对话框
		//Intent intent = new Intent();
		//intent.setClass(OnlineSubActivity.this, RandomMatchActivity.class);
		//startActivity(intent);
		//finish();
		
	}
	
	// 创建一个适配器，存放列表中的内容
		class Myadapter extends BaseAdapter {

			public Myadapter() {

			}

			public int getCount() {

				return 2;
			}

			public Object getItem(int position) {

				return position;
			}

			public long getItemId(int position) {

				return position;
			}

			public View getView(final int position, View convertView,
					ViewGroup parent) {

//				if (convertView == null) {
//					// 列表布局
					convertView = inflater.inflate(R.layout.topic_list, null);
//				}
				topic = (TextView) convertView.findViewById(R.id.topicName);
				inviteBtn = (ImageButton) convertView.findViewById(R.id.imgbtnyaozhan);
				//Constant.onlineMainChoice = position;
				switch (Constant.onlineMainChoice) {
				case 1:
					if (position == 0){
						topic.setText("大陆音乐");
					}else {
						topic.setText("港台音乐");
					}
					break;
				case 2:
					if (position == 0){
						topic.setText("哈利波特");
					}else {
						topic.setText("速度与激情7");
					}
					break;
				case 3:
					if (position == 0){
						topic.setText("DOTA");
					}else {
						topic.setText("英雄联盟");
					}
					break;
				case 4:
					if (position == 0){
						topic.setText("GRE单词");
					}else {
						topic.setText("计算机原理");
					}
					break;
				case 5:
					if (position == 0){
						topic.setText("NBA");
					}else {
						topic.setText("世界杯");
					}
					break;
				default:
						break;
				}
				inviteBtn.setVisibility(View.VISIBLE);
				//System.out.println((String) mList.get(position).get("friendName"));
				//playername.setText((String) mList.get(position).get("friendName"));
				
				// 以分数的多少设置玩家的等级
				//int score = (Integer) mList.get(position).get("score");
				//level.setText(Playerlevel.getLevel(score));
				// 添加玩家为好友按钮监听
				inviteBtn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Constant.onlineSubChoice = position+1;
						choiceDialog();
					}
				});
				
				backBtn.setOnClickListener(new OnClickListener() {

					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						//isToNextAct = true;
						Intent intent = new Intent();
						intent.setClass(OnlineSubActivity.this, LoginActivity.class);
						startActivity(intent);
						Toast.makeText(OnlineSubActivity.this, "返回到游戏大厅  ! !", 1).show();
						finish();
					}
				});
				return convertView;
			}
		}
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {

			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
				//isToNextAct = true;
				Intent intent = new Intent();
				intent.setClass(OnlineSubActivity.this, LoginActivity.class);
				startActivity(intent);
				Toast.makeText(OnlineSubActivity.this, "返回!!", 1000).show();
				finish();
			}
			return super.onKeyDown(keyCode, event);
		}
}
