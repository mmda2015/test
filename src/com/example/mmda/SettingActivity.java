package com.example.mmda;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.network.Communication;
public class SettingActivity extends BaseActivity{
	private ImageView sound;
	private ImageView music;
	private ImageButton backBtn, freshBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_setting);
		
		//连接到服务器
		con = Communication.newInstance();
		sound=(ImageView) findViewById(R.id.sound);
		music=(ImageView) findViewById(R.id.music);
		if(Flag.getFlag1()==0){
			sound.setBackgroundResource(R.drawable.sound);
		}
		else if(Flag.getFlag1()==1){
			sound.setBackgroundResource(R.drawable.sound_ban);
		}
		if(Flag.getFlag2()==0){
			music.setBackgroundResource(R.drawable.music_);
		}
		else if(Flag.getFlag2()==1){
			music.setBackgroundResource(R.drawable.music_ban);
		}
		init();
		freshBtn = (ImageButton) findViewById(R.id.fresh);
		backBtn = (ImageButton) findViewById(R.id.back);
	
	
	backBtn.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//isToNextAct = true;
			Intent intent = new Intent();
			intent.setClass(SettingActivity.this, OnlineMainActivity.class);
			startActivity(intent);
			Toast.makeText(SettingActivity.this, "返回到游戏大厅  ! !", 1).show();
			finish();
		}
	});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.online_main, menu);
		return true;
	}
	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
	}
	public void init(){
		sound.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				if(Flag.getFlag1()==0){
					sound.setBackgroundResource(R.drawable.sound);
					Flag.setFlag1(1);
				}
				else if(Flag.getFlag1()==1){
					sound.setBackgroundResource(R.drawable.sound_ban);
					Flag.setFlag1(0);
				}
			}
		});
		
		music.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {				
				if(Flag.getFlag2()==0){
					startService(m_intent);
					music.setBackgroundResource(R.drawable.music_);
					Flag.setFlag2(1);
				}
				else if(Flag.getFlag2()==1){
					stopService(m_intent);
					music.setBackgroundResource(R.drawable.music_ban);
					Flag.setFlag2(0);
				}
			}
		});
	}
}
