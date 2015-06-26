package com.example.mmda;

import com.until.Constant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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

public class SingleSubActivity extends ActionBarActivity {

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
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// û�б�����
		setContentView(R.layout.activity_single_sub);
		
		playerlist = (ListView) findViewById(R.id.listViewSingleSub);
		inflater = LayoutInflater.from(this);
		
		adapter = new Myadapter();
		playerlist.setAdapter(adapter);
		//adapter.notifyDataSetChanged();
		freshBtn = (ImageButton) findViewById(R.id.fresh);
		backBtn = (ImageButton) findViewById(R.id.back);
	
	
	backBtn.setOnClickListener(new OnClickListener() {

		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			//isToNextAct = true;
			Intent intent = new Intent();
			intent.setClass(SingleSubActivity.this, SingleMainActivity.class);
			startActivity(intent);
			Toast.makeText(SingleSubActivity.this, "���ص���Ϸ����  ! !", 1).show();
			finish();
		}
	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_sub, menu);
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
	
	// ����һ��������������б��е�����
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

//			if (convertView == null) {
//				// �б���
				convertView = inflater.inflate(R.layout.topic_list, null);
//			}
			topic = (TextView) convertView.findViewById(R.id.topicName);
			inviteBtn = (ImageButton) convertView.findViewById(R.id.imgbtnyaozhan);
			switch (Constant.singleChoice) {
			case 1:
				if (position == 1){
					topic.setText("��½����");
				}else {
					topic.setText("��̨����");
				}
				break;
			case 2:
				if (position == 1){
					topic.setText("��������");
				}else {
					topic.setText("�ٶ��뼤��7");
				}
				break;
			case 3:
				if (position == 1){
					topic.setText("DOTA");
				}else {
					topic.setText("Ӣ������");
				}
				break;
			case 4:
				if (position == 1){
					topic.setText("GRE����");
				}else {
					topic.setText("�����ԭ��");
				}
				break;
			case 5:
				if (position == 1){
					topic.setText("NBA");
				}else {
					topic.setText("���籭");
				}
				break;
			default:
					break;
			}
			inviteBtn.setVisibility(View.VISIBLE);
			//System.out.println((String) mList.get(position).get("friendName"));
			//playername.setText((String) mList.get(position).get("friendName"));
			
			// �Է����Ķ���������ҵĵȼ�
			//int score = (Integer) mList.get(position).get("score");
			//level.setText(Playerlevel.getLevel(score));
			// ������Ϊ���Ѱ�ť����
			inviteBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (Constant.singleChoice) {
					case 1:
						if (position == 1){
							Constant.singleSubChoice = 1;
						}else {
							Constant.singleSubChoice = 2;
						}
						break;
					case 2:
						if (position == 1){
							Constant.singleSubChoice = 3;
						}else {
							Constant.singleSubChoice = 4;
						}
						break;
					case 3:
						if (position == 1){
							Constant.singleSubChoice = 5;
						}else {
							Constant.singleSubChoice = 6;
						}
						break;
					case 4:
						if (position == 1){
							Constant.singleSubChoice = 7;
						}else {
							Constant.singleSubChoice = 8;
						}
						break;
					case 5:
						if (position == 1){
							Constant.singleSubChoice = 9;
						}else {
							Constant.singleSubChoice = 10;
						}
						break;
					default:
							break;
					}
					Intent intent = new Intent();
					intent.setClass(SingleSubActivity.this, SingleGameActivity.class);
					startActivity(intent);
					finish();
				}
			});
			return convertView;
		}
	}
}
