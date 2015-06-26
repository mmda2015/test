package com.example.mmda;

import com.network.Communication;
import com.until.Constant;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class OnlineMainActivity extends BaseActivity {
	private ImageButton backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// û�б�����
		setContentView(R.layout.activity_online_main);
		
		//���ӵ�������
		con = Communication.newInstance();
		backBtn = (ImageButton) findViewById(R.id.exit);
		
		
		backBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//isToNextAct = true;
				Intent intent = new Intent();
				intent.setClass(OnlineMainActivity.this, LoginActivity.class);
				startActivity(intent);
				Toast.makeText(OnlineMainActivity.this, "���ص���Ϸ����  ! !", 1).show();
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
	
	//����
	public void onlineMusic(View view) {
		Constant.onlineMainChoice = 1;
		Intent intent = new Intent();
		intent.setClass(OnlineMainActivity.this, OnlineSubActivity.class);
		startActivity(intent);
		//finish();
	}
	
	//����
		public void onlineSport(View view) {
			Constant.onlineMainChoice = 5;
			Intent intent = new Intent();
			intent.setClass(OnlineMainActivity.this, OnlineSubActivity.class);
			startActivity(intent);
			//finish();
		}
		
		//��Ϸ
		public void onlineGame(View view) {
			Constant.onlineMainChoice = 3;
			Intent intent = new Intent();
			intent.setClass(OnlineMainActivity.this, OnlineSubActivity.class);
			startActivity(intent);
			//finish();
		}
		
		//����
		public void onlineEducation(View view) {
			Constant.onlineMainChoice = 4;
			Intent intent = new Intent();
			intent.setClass(OnlineMainActivity.this, OnlineSubActivity.class);
			startActivity(intent);
			//finish();
		}
		
		//��Ӱ
		public void onlineMovie(View view) {
			Constant.onlineMainChoice = 2;
			Intent intent = new Intent();
			intent.setClass(OnlineMainActivity.this, OnlineSubActivity.class);
			startActivity(intent);
			//finish();
		}
		//����
		public void onlineSetting(View view){
			Intent intent = new Intent();
			intent.setClass(OnlineMainActivity.this, SettingActivity.class);
			startActivity(intent);
			finish();
		}
		//�̳�
		public void onlineShop(View view){
			Intent intent = new Intent();
			intent.setClass(OnlineMainActivity.this, ShopActivity.class);
			startActivity(intent);
			finish();
		}
		//�̳�
				public void onlineFriends(View view){
					Intent intent = new Intent();
					intent.setClass(OnlineMainActivity.this, PKFriendActivity.class);
					startActivity(intent);
					finish();
				}
	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		
	}
}
