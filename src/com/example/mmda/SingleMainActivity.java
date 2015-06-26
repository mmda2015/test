package com.example.mmda;

import com.until.Constant;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class SingleMainActivity extends ActionBarActivity {

	private ImageButton backBtn, freshBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_single_main);
		freshBtn = (ImageButton) findViewById(R.id.fresh);
		backBtn = (ImageButton) findViewById(R.id.back);
	
	
	backBtn.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//isToNextAct = true;
			Intent intent = new Intent();
			intent.setClass(SingleMainActivity.this, LoginActivity.class);
			startActivity(intent);
			Toast.makeText(SingleMainActivity.this, "返回到游戏大厅  ! !", 1).show();
			finish();
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_main, menu);
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
	
	//音乐
	public void onMusic(View view) {
		Constant.singleChoice = 1;
		Intent intent = new Intent();
		intent.setClass(SingleMainActivity.this, SingleSubActivity.class);
		startActivity(intent);
		//finish();
	}
	
	//电影
	public void onMovie(View view) {
		Constant.singleChoice = 2;
		Intent intent = new Intent();
		intent.setClass(SingleMainActivity.this, SingleSubActivity.class);
		startActivity(intent);
		//finish();
	}
	
	//游戏
	public void onGame(View view) {
		Constant.singleChoice = 3;
		Intent intent = new Intent();
		intent.setClass(SingleMainActivity.this, SingleSubActivity.class);
		startActivity(intent);
		//finish();
	}
	
	//教育
	public void onEdu(View view) {
		Constant.singleChoice = 4;
		Intent intent = new Intent();
		intent.setClass(SingleMainActivity.this, SingleSubActivity.class);
		startActivity(intent);
		//finish();
	}
	
	//体育
	public void onSport(View view) {
		Constant.singleChoice = 5;
		Intent intent = new Intent();
		intent.setClass(SingleMainActivity.this, SingleSubActivity.class);
		startActivity(intent);
		//finish();
	}
	
}
