package com.example.mmda;

import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.network.Communication;
import com.until.Config;
import com.until.Constant;

public abstract class BaseActivity  extends Activity{
	
	public boolean isToNextAct;
	//将生成的Activity都放到LinkList集合中
	protected static LinkedList<BaseActivity> queue= new LinkedList<BaseActivity>();
	public static Communication con;
	public Intent m_intent = new Intent("com.angel.Android.MUSIC");
	public static int choose_music =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
//		initSoundPoolGame();
		//判断该Activity是否在LinkedList中，没有在的话就添加上
		if(!queue.contains(this)){
			queue.add(this);
			System.out.println("将"+queue.getLast()+"添加到list中去");
		}
	}
	
	
	public abstract void processMessage(Message message);
	
	private static Handler handler = new Handler(){
		
		public void handleMessage(Message msg) {
			
			switch (msg.what) {
			
			case Config.REQUEST_SEND_INVITE:	
				final String playername=(String) msg.obj;
				
				int model = msg.arg1;
				Constant.gameModel = model;
				System.out.println(queue.getLast().getClass().toString());
				if(!(queue.getLast().getClass().toString()).equals("class com.ambo.Pkgames")){
					
					Builder build = new Builder(queue.getLast());
					build.setTitle("提示");
					build.setMessage(playername+"向你发出挑战的邀请！是否接受邀请？");
					build.setPositiveButton("确认", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							con.inviteResult(playername, Config.SUCCESS);
							Constant.playerName = playername;
							Toast.makeText(queue.getLast(),"同意对方的请求啦，进入游戏中。。。", 1000).show();	
							Intent in = new Intent (queue.getLast(),OnlineGameActivity.class);
							queue.getLast().startActivity(in);
							queue.getLast().finish();
						}
					});
					build.setNegativeButton("取消", new DialogInterface.OnClickListener() {						
						public void onClick(DialogInterface dialog, int which) {
							con.inviteResult(playername, Config.FAIl);
						}
					});
					build.create().show();
					
				}
				else{
					con.inviteResult(playername, Config.FAIl);
				}
				break;

			default:
				System.out.println("执行到了控制信息处理handle");
				if(!queue.isEmpty()){
					Log.i("提示","值="+msg.arg1+"!!!!!!"+"类型="+msg.what);
					queue.getLast().processMessage(msg);
				}
				break;
			}
			
		};
	};
	
	//发送消息（、、、）
	public static void sendMessage(Message msg){
		handler.sendMessage(msg);
	}

}
