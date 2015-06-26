package com.example.mmda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.network.Communication;
import com.until.Config;
import com.until.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class RandomMatchActivity extends BaseActivity {
	
	ImageView imageView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_random_match);
		con = Communication.newInstance();
		
		//final Intent it = new Intent(this, OnlineGameActivity.class); //你要转向的Activity   
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {  

		    @Override  

		    public void run() {   

		   // startActivity(it); //执行  
		    con.getPlayerList();
		     } 

		 };

		timer.schedule(task, 3000); 
	

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.random_match, menu);
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
	public void processMessage(Message msg) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				System.out.println("执行到处理信息了");
				switch(msg.what){
				case Config.REQUEST_GET_USERS_ONLINE:
					List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
					list = (List<Map<String, Object>>) msg.obj;
					int number = list.size();
					if(number == 0){
						Toast.makeText(RandomMatchActivity.this, "当前没有其他在线玩家", Toast.LENGTH_SHORT).show();
						Intent intent = new Intent();
						intent.setClass(RandomMatchActivity.this, OnlineSubActivity.class);
						startActivity(intent);
						finish();
					}else {
						Map map = null;
						String[] userName = new String [number]; 
						//String []scoreString = new String [number]		
						for(int i=0;i<number;i++){
							map = (HashMap)list.get(i);
							userName[i]=(String)map.get("username");
						}
						Random r = new Random();
						int random = Math.abs(r.nextInt())%number;
						//OnlineGameActivity.rivalXLXFName_1 = userName[random];
						//OnlineGameActivity.rivalXLXFName_2 = Constant.userName;
					    con.yaoZhan(userName[random], Constant.userName,1); 
					}
				break;
				case Config.REQUEST_INVITE_RESULT:
					int result_2 = msg.arg1;
					if(result_2 == Config.SUCCESS){
						//XLXF_KuaisuMainActivity.flagNo = 1;
						Intent intent = new Intent(RandomMatchActivity.this,OnlineGameActivity.class);
						startActivity(intent);
					}
					else
					{
						Toast.makeText(RandomMatchActivity.this, "邀战好友，失败", Toast.LENGTH_SHORT).show();	
					}
				break;
				default:break;
			}
	}
}
