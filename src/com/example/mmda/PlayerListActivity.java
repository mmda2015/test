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
import android.view.KeyEvent;
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

public class PlayerListActivity extends  BaseActivity{
	private ListView playerlist;
	List<Map<String, Object>> mList;
	private ImageButton backBtn, freshBtn;
	TextView player, level;
	ImageButton tjBtn, lookbtn;
	// �ж�����һ��Ǻ��ѵ�״̬
	//int flag = 0;
	Myadapter adapter;
	LayoutInflater inflater3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player_list);
		
		init();
	}
	
private void init() {
		
		playerlist = (ListView) findViewById(R.id.listView3);
		inflater3 = LayoutInflater.from(this);		
		
		final Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				con.getPlayerList();
				timer.cancel();
			}
		};
		timer.schedule(timerTask, 1000);

		/**
		 * ���ذ�ť����
		 * */
		

		/**
		 * ˢ�°�ť����
		 * */
		
		/**
		 * ����б�ť����
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
		 * �����б�ť����
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
		getMenuInflater().inflate(R.menu.player_list, menu);
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
//				// �б���
				convertView = inflater3.inflate(R.layout.player_list, null);
//			}
			player = (TextView) convertView.findViewById(R.id.playerName);
			tjBtn = (ImageButton) convertView.findViewById(R.id.tjhaoyou);
			//pkBtn = (ImageButton) convertView.findViewById(R.id.imgbtnhaoyou);
		
				/*if (position == 1){
					friend.setText("��½����");
				}else {
					friend.setText("��̨����");
				}*/
				
		    tjBtn.setVisibility(View.VISIBLE);
			System.out.println((String) mList.get(position).get("username"));
			player.setText((String) mList.get(position).get("username"));
			
			// �Է����Ķ���������ҵĵȼ�
			//int score = (Integer) mList.get(position).get("score");
			//level.setText(Playerlevel.getLevel(score));
			// ������Ϊ���Ѱ�ť����
			
			//System.out.println((String) mList.get(position).get("playerName"));
			tjBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//playSoundButton1();
					
					//���PK,�õ�ѡ�к��ѵ�����
					String playerName1 = (String)
					mList.get(position).get("username");
					//con.yaoZhan(playerName,Constant.userName,1);
					//Toast.makeText(FriendListActivity.this, "check info", 1).show();
					//con.getshop((String) mList.get(position).get("friendName"));
//					con.addFriend(playerName);
					Toast.makeText(PlayerListActivity.this, "���Ϊ����"+playerName1, 1).show();
					con.addFriend((String) mList.get(position).get("username"));
				}
			});
			return convertView;
		}
	}
	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				System.out.println("ִ�е���Ϣ������");
				//String dengji = Playerlevel.getLevel(Constant.score);
				switch (message.what) {
				// �����Ϣ����
				case Config.REQUEST_GET_USERS_ONLINE:
					mList = (List<Map<String, Object>>) message.obj;
					adapter = new Myadapter();
					playerlist.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					System.out.println("��Ϣ������������б�Ӵ������");
					Toast.makeText(PlayerListActivity.this, "��ȡ����б�ɹ�", 1000).show();
					break;

				case Config.REQUEST_ADD_FRIEND:
					if (message.arg1 == Config.SUCCESS) {
						Toast.makeText(PlayerListActivity.this, "��Ӻ��ѳɹ�����", 2000).show();
					} else {
						Toast.makeText(PlayerListActivity.this, "��Ӻ���ʧ�ܡ���", 2000).show();
					}
					break;	
					
				// ������Ϣ����
				case Config.REQUEST_GET_FRIEND:
					mList = (List<Map<String, Object>>) message.obj;
					adapter = new Myadapter();
					playerlist.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					System.out.println("��Ϣ�������˺����б�Ӵ������");
					Toast.makeText(PlayerListActivity.this, "��ȡ�����б�ɹ�", 1000).show();
					break;
				case Config.REQUEST_INVITE_RESULT:
					int result_2 = message.arg1;
					if(result_2 == Config.SUCCESS){
						//XLXF_KuaisuMainActivity.flagNo = 1;
						Intent intent = new Intent(PlayerListActivity.this,OnlineGameActivity.class);
						startActivity(intent);
						finish();
					}
					else
					{
						Toast.makeText(PlayerListActivity.this, "��ս���ѣ�ʧ��", Toast.LENGTH_SHORT).show();	
					}
				break;
				}
			}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			isToNextAct = true;
			Intent intent = new Intent();
			intent.setClass(PlayerListActivity.this, OnlineMainActivity.class);
			startActivity(intent);
			Toast.makeText(PlayerListActivity.this, "���ص���Ϸ����!!", 1000).show();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	}

