package com.example.mmda;

import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;



import com.network.Communication;
import com.until.Config;
import com.until.Constant;

public class LoginActivity extends BaseActivity {

	EditText username,password;
	String name, psw;
	MyMusic mm = new MyMusic();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_login);
		if(Flag.getFlag2()==0){
		startService(m_intent);//开始音乐
		}
		
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		
		//连接到服务器
		con = Communication.newInstance();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
	
	//登陆
	public void onLogin(View view) {

		name = username.getText().toString().trim();
		psw = password.getText().toString().trim();
			
//		Intent intent = new Intent();
//		intent.setClass(LoginActivity.this, OnlineMainActivity.class);
//		startActivity(intent);
//		//finish();

		Toast.makeText(LoginActivity.this,"name:"+name+"password"+psw,3000).show();
		Timer time = new Timer();
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
			// TODO Auto-generated method stub
			con.login(name,psw);
			}
		};
		time.schedule(task,3000);
	}

	//快速登陆
	public void onRegisterq(View view) {

//		name = username.getText().toString().trim();
//		psw = password.getText().toString().trim();
//			
//		Intent intent = new Intent();
//		intent.setClass(LoginActivity.this, OnlineMainActivity.class);
//		startActivity(intent);
//		//finish();
		Toast.makeText(LoginActivity.this,"快速登录！！！",3000).show();
		Timer time = new Timer();
		TimerTask task=new TimerTask(){
			@Override
			public void run() {
			// TODO Auto-generated method stub
			con.registerq();
			}
		};
		time.schedule(task,3000);
	}

	//新手注册
	public void onRegister(View view) {
		
		//ms.p(1);
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
		//finish();
	}
		
	//单机界面
	public void onSingle(View view) {
		
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this, SingleMainActivity.class);
		startActivity(intent);
		//finish();
	}

	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("执行到处理信息了！！！");
		switch(message.what){
		case Config.REQUEST_LOGIN:
			int result = message.arg1;
			if(result == Config.SUCCESS){
				Toast.makeText(this, "登陆成功", 3000).show();
				Constant.userName = name;
				Constant.userPassword = psw;
				isToNextAct = true;
				//ms.p(1);
				Intent intent = new Intent(LoginActivity.this,OnlineMainActivity.class);
				startActivity(intent);
				finish();
				message = null;
				
			}else {
				Toast.makeText(this, "登录失败", 3000).show();
			}
		break;
		case Config.REQUEST_QUICK_LOGIN:
			Builder builder = new Builder(this);
			builder.setTitle("提示");
			builder.setMessage("系统为你分配的随即账号为："+Constant.userName+"密码为："+Constant.userPassword+"请妥善保存！");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface dialog,int which) {
					// TODO Auto-generated method stub
					//ms.p(1);
					System.out.println("快速登录成功！");
				//	isToNextAct = true;
					Intent intent = new Intent(LoginActivity.this,OnlineMainActivity.class);
					startActivity(intent);
					finish();
				}
			});
			
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog,int which) {
					// TODO Auto-generated method stub
					//System.out.println("快速登录成功！");
					//intent=new Intent(Login.this,Register.class);
				}
			});
		builder.create().show();
		break;
		
		}
		
	}
	
}
