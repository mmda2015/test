package com.example.mmda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mmda.PKFriendActivity.Myadapter;
import com.network.Communication;
import com.until.Config;
import com.until.Constant;

public class OnlineGameActivity extends BaseActivity {

	private int quesNum = 0;
	private int rightNum = 1;///////////////////////应该是0 ，测试用1
	private TextView ques;
	public int num=1;
	private TextView t1,t2,t3,t4;
	private ImageButton ans1, ans2, ans3, ans4;
	private ImageView star, card;
	private ImageView cmptImage1, cmptImage2;
	private TextView timeView;
	AlertDialog.Builder builder;
	private long timeLeft = 0;
	private int timeUpdate = 0;
	private Handler mHandler;		//声明一个用于处理消息的Handler类的对象
	Myadapter adapter2;
	LayoutInflater inflater1;
	List<String> rList = new ArrayList<String>();
	List<Map<String, Object>> mList;
	
	private Intent m_intent = new Intent("com.angel.Android.MUSIC");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_online_game);
        
		con = Communication.newInstance();
		ans1 = (ImageButton) findViewById(R.id.ans1);
		ans2 = (ImageButton) findViewById(R.id.ans2);
		ans3 = (ImageButton) findViewById(R.id.ans3);
		ans4 = (ImageButton) findViewById(R.id.ans4);
		ques =(TextView)findViewById(R.id.question);
		t1 = (TextView) findViewById(R.id.text1);
		t2 = (TextView) findViewById(R.id.text2);
		t3 = (TextView) findViewById(R.id.text3);
		t4 = (TextView) findViewById(R.id.text4);
		
		star = (ImageView) findViewById(R.id.rightStar);
		card = (ImageView) findViewById(R.id.faultCard);
		
		cmptImage1 = (ImageView) findViewById(R.id.competitor1);
		cmptImage2 = (ImageView) findViewById(R.id.competitor2);
		
		Constant.cmptScore1 = 0;
		
		timeView = (TextView) findViewById(R.id.chronometer);
		
		timeLeft = 5;
		
		Message message = handler.obtainMessage(1);
	    handler.sendMessageDelayed(message, 1000);
		con.getQuestion(1, 1);
		init(0);
		
        
        
	}
private void init(int num) {
	
		//answerlist = (ListView) findViewById(R.id.listView2);
		inflater1 = LayoutInflater.from(this);
		
			
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.online_game, menu);
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
	
	//倒计时处理
    @SuppressLint("HandlerLeak")
	final Handler handler = new Handler(){
    	
        public void handleMessage(Message msg){       // handle message
        	if (timeUpdate == 1){
        		//Constant.cmptScore1 += timeLeft;
        		timeLeft = 5;
        		timeUpdate = 0;
        		
        	}
        	timeView.setText("" + timeLeft);
        	if (timeLeft-- > 0){
        		
        		Message message = handler.obtainMessage(1);
                handler.sendMessageDelayed(message, 1000);      // send message
        	} else {
        		//timeUp();
        		onAns();
        		timeLeft = 5;
        		timeUpdate = 0;
        		Message message = handler.obtainMessage(1);
                handler.sendMessageDelayed(message, 1000);      // send message
                
        	}
            super.handleMessage(msg);
        }
    };
	
	public void onAns(){
		switch (rightNum){
		case 1:
			ans1.setBackgroundResource(R.drawable.choice_right);
			break;
		case 2:
			ans2.setBackgroundResource(R.drawable.choice_right);
			break;
		case 3:
			ans3.setBackgroundResource(R.drawable.choice_right);
			break;
		case 4:
			ans4.setBackgroundResource(R.drawable.choice_right);
			break;
		default:
				break;
		}
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				convert();
			}
		}, 1000);//1s延迟刷新
//		convert();
	}
	
	public void convert(){
		
		quesNum++;
		if (quesNum == 5){
			//比较结果
			if (Constant.cmptScore1 > Constant.cmptScore2){
				Intent intent = new Intent();
				intent.setClass(OnlineGameActivity.this, OnlineWinActivity.class);
				startActivity(intent);
				stopService(m_intent);
				finish();
			} else if (Constant.cmptScore1 < Constant.cmptScore2){
				Intent intent = new Intent();
				intent.setClass(OnlineGameActivity.this, OnlineLoseActivity.class);
				startActivity(intent);
				finish();
			} else if (Constant.cmptScore1 == Constant.cmptScore2){
				Intent intent = new Intent();
				intent.setClass(OnlineGameActivity.this, OnlineLoseActivity.class);//////平局
				startActivity(intent);
				finish();
			}	
		} else {
			//取下一道题
			ans1.setBackgroundResource(R.drawable.choice_normal1);
			ans2.setBackgroundResource(R.drawable.choice_normal2);
			ans3.setBackgroundResource(R.drawable.choice_normal);
			ans4.setBackgroundResource(R.drawable.choice_normal4);
			Map<String, Object> str = mList.get(num);
			num=num+1;
			ques.setText(""+str.get("question"));
			t1.setText(""+str.get("answer1"));
			t2.setText(""+str.get("answer2"));
			t3.setText(""+str.get("answer3"));
			t4.setText(""+str.get("answer4"));
			String r = ""+str.get("right");
			int right;
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//前方高能前方高能前方高能前方高能前方高能前方高能前方高能前方高能前方高能前方高能
			right = Integer.parseInt(r);//这是刘琦要的正确的值~~~~~~~~~~~~~~~~~~~~~~
			rightNum = right;
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			
		}
	}
	
	//点击第一个答案
	public void onAns1(View view) {
		timeUpdate = 1;
		if (rightNum != 1){
			ans1.setBackgroundResource(R.drawable.choice_fault);
		} else {
			//计算得分
			Constant.cmptScore1 += timeLeft;
		}
		onAns();	
	}	
	
	//点击第一个答案
		public void onAns2(View view) {
			timeUpdate = 1;
			if (rightNum != 2){
				ans2.setBackgroundResource(R.drawable.choice_fault);
			} else {
				//计算得分
				Constant.cmptScore1 += timeLeft;
			}
			onAns();	
		}
		
		//点击第一个答案
		public void onAns3(View view) {
			timeUpdate = 1;
			if (rightNum != 3){
				ans3.setBackgroundResource(R.drawable.choice_fault);
			} else {
				//计算得分
				Constant.cmptScore1 += timeLeft;
			}
			onAns();	
		}
		
		//点击第一个答案
		public void onAns4(View view) {
			timeUpdate = 1;
			if (rightNum != 4){
				ans4.setBackgroundResource(R.drawable.choice_fault);
			} else {
				//计算得分
				Constant.cmptScore1 += timeLeft;
			}
			onAns();	
		}
		
		public void onRightStar(View view) {
			
		}
		
		public void faultCard(View view) {
			
		}
		

		//@Override
		public void processMessage(Message message) {
			// TODO Auto-generated method stub
			//初始化题

			// TODO Auto-generated method stub
			System.out.println("执行到消息处理啦");
			//String dengji = Playerlevel.getLevel(Constant.score);
			if(message.what == Config.REQUEST_GET_QUESTION){
			////JSONObject ja = (JSONObject) message.obj;
			//System.out.println("88888"+ja.toString());
			mList = (List<Map<String, Object>>) message.obj;
			System.out.println("hhhhhh"+mList);
			
			Map<String, Object> str = mList.get(num);
			num=num+1;
			ques.setText(""+str.get("question"));
			t1.setText(""+str.get("answer1"));
			t2.setText(""+str.get("answer2"));
			t3.setText(""+str.get("answer3"));
			t4.setText(""+str.get("answer4"));
			String r = ""+str.get("right");
			int right;
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			//前方高能前方高能前方高能前方高能前方高能前方高能前方高能前方高能前方高能前方高能
			right = Integer.parseInt(r);//这是刘琦要的正确的值~~~~~~~~~~~~~~~~~~~~~~
			rightNum = right;
			//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			
			
			}
		}
		
}
