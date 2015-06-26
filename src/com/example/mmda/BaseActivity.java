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
	//�����ɵ�Activity���ŵ�LinkList������
	protected static LinkedList<BaseActivity> queue= new LinkedList<BaseActivity>();
	public static Communication con;
	public Intent m_intent = new Intent("com.angel.Android.MUSIC");
	public static int choose_music =1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// û�б�����
//		initSoundPoolGame();
		//�жϸ�Activity�Ƿ���LinkedList�У�û���ڵĻ��������
		if(!queue.contains(this)){
			queue.add(this);
			System.out.println("��"+queue.getLast()+"��ӵ�list��ȥ");
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
					build.setTitle("��ʾ");
					build.setMessage(playername+"���㷢����ս�����룡�Ƿ�������룿");
					build.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							con.inviteResult(playername, Config.SUCCESS);
							Constant.playerName = playername;
							Toast.makeText(queue.getLast(),"ͬ��Է�����������������Ϸ�С�����", 1000).show();	
							Intent in = new Intent (queue.getLast(),OnlineGameActivity.class);
							queue.getLast().startActivity(in);
							queue.getLast().finish();
						}
					});
					build.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {						
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
				System.out.println("ִ�е��˿�����Ϣ����handle");
				if(!queue.isEmpty()){
					Log.i("��ʾ","ֵ="+msg.arg1+"!!!!!!"+"����="+msg.what);
					queue.getLast().processMessage(msg);
				}
				break;
			}
			
		};
	};
	
	//������Ϣ����������
	public static void sendMessage(Message msg){
		handler.sendMessage(msg);
	}

}
