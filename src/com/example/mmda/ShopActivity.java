package com.example.mmda;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.until.Constant;
import com.until.Config;
public class ShopActivity extends BaseActivity {
private TextView question;
private TextView tip1;
private TextView heart1;
private String name;
private ImageButton backBtn, freshBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop);
		init();
		freshBtn = (ImageButton) findViewById(R.id.fresh);
		backBtn = (ImageButton) findViewById(R.id.back);
	
	
	backBtn.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//isToNextAct = true;
			Intent intent = new Intent();
			intent.setClass(ShopActivity.this, OnlineMainActivity.class);
			startActivity(intent);
			Toast.makeText(ShopActivity.this, "返回到游戏大厅  ! !", 1).show();
			finish();
		}
	});
	}

	private void init() {
		ImageButton buy_tip = (ImageButton) findViewById(R.id.paicuo);
		ImageButton buy_heart = (ImageButton) findViewById(R.id.zhengque);
		question = (TextView) findViewById(R.id.question);
		tip1 = (TextView) findViewById(R.id.paicuo1);
		heart1 = (TextView) findViewById(R.id.zhengque1);
		name = Constant.userName;;
		Timer time = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				con.getshop(name);
				con.getScore(name);
			}
		};
		time.schedule(task, 1000);
		buy_tip.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final  Timer time = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						con.changshop("tip", 1);
						con.getshop(name);
						time.cancel();
					}
				};
				time.schedule(task, 300);
				
			}
		});
		buy_heart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				final  Timer time = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						con.changshop("heart", 1);
						con.getshop(name);
						time.cancel();
					}
				};
				time.schedule(task, 300);
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop, menu);
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

	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		switch (message.what) {
		case Config.REQUEST_GET_PROP:
			//String tip=String.valueOf(message.arg1);
			System.out.println("获取道具请求。。。。。！~！@~！@。");
			tip1.setText(""+message.arg2);
			heart1.setText(""+message.arg1);
			System.out.println("tip=" + message.arg1 + "heart=" + message.arg2);
			System.out.println();
			break;
		case Config.REQUEST_MODIFY_PROP:
			if (message.arg1 == Config.SUCCESS) {
				Toast.makeText(this, "购买成功", 1).show();
				con.getshop(Constant.userName);
			} else if (message.arg1 == Config.FAIl) {
				Toast.makeText(this, "购买失败!", 1).show();
			}
		case Config.REQUEST_GET_SCORES:
			
			System.out.println("处理获取积分请求。。。");
			question.setText(""+message.arg1);
		default:
			break;
		}
	}
}
