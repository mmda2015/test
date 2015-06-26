package com.example.mmda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;




import com.until.Constant;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;


public class SingleGameActivity extends ActionBarActivity {

	
	private int quesNumber = 0;
	private int rightNumber = 0;
	private Button answer1, answer2, answer3, answer4;
	private TextView question;
	private int []questionNo ;
	private String[] qaString;
	private String path = "";
	private TextView timeView;
	private int timeUpdate;
	private int timeLeft;
	private ProgressBar bar;
	private Intent m_intent = new Intent("com.angel.Android.MUSIC");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_single_game);
		stopService(m_intent);
		Constant.singleScore = 0;
		question = (TextView) findViewById(R.id.question);
		answer1 = (Button) findViewById(R.id.answer1);
		answer2 = (Button) findViewById(R.id.answer2);
		answer3 = (Button) findViewById(R.id.answer3);
		answer4 = (Button) findViewById(R.id.answer4);
		
		
		answer1.setOnClickListener(myListener);
		answer2.setOnClickListener(myListener);
		answer3.setOnClickListener(myListener);
		answer4.setOnClickListener(myListener);
		try {
			createQuestion();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timeUpdate = 20;
		timeLeft = 20;
		timeView  = (TextView) findViewById(R.id.chronometer1); 
		bar = (ProgressBar)findViewById(R.id.progressbar1);
		handler.post(time_runnable);	
	}
	Handler handler = new Handler();
	
	Runnable time_runnable = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			
			timeView.setText(""+timeLeft);
			bar.setProgress(timeLeft);
			
			handler.postDelayed(time_runnable, 1000);
			timeLeft--;
			
			if(timeLeft==0){
				switch(rightNumber){
				case 1:
					answer1.setBackgroundResource(R.drawable.choice_right);
					break;
				case 2:
					answer2.setBackgroundResource(R.drawable.choice_right);
					break;
				case 3:
					answer3.setBackgroundResource(R.drawable.choice_right);			
					break;
				case 4:
					answer4.setBackgroundResource(R.drawable.choice_right);
				}
				
				
			}
			else if(timeLeft == -1){				
				quesNumber++;				
				if(quesNumber==6){
					handler.removeCallbacks(time_runnable);
					Intent intent = new Intent();
					intent.setClass(SingleGameActivity.this, SingleScoreActivity.class);
					startActivity(intent);	    				
					finish();
				}
				else
				QARefresh();				
			}
			
		}
	};

	private void createQuestion() throws IOException {
		// TODO Auto-generated method stub
		
		questionNo = randomCommon(1,30,6);
		Log.i("0",""+questionNo[0]);
		Log.i("1",""+questionNo[1]);
		Log.i("2",""+questionNo[2]);
		Log.i("3",""+questionNo[3]);
		Log.i("4",""+questionNo[4]);
		Log.i("5",""+questionNo[5]);
		qaString = new String[6];
		for(int i=0;i<6;i++){
			path = Constant.singleSubChoice + ".txt";
			qaString[i] =read(path,(questionNo[0]-1)*6+i+1);
		}
		question.setText(qaString[0]);
		answer1.setText(qaString[1]);
		answer2.setText(qaString[2]);
		answer3.setText(qaString[3]);
		answer4.setText(qaString[4]);
		rightNumber = Integer.valueOf(qaString[5]);
		//String temp = read("lol.txt",5);
		//Log.i("tttt",""+temp);
		Log.i("0",""+qaString[0]);
		Log.i("1",""+qaString[1]);
		Log.i("2",""+qaString[2]);
		Log.i("3",""+qaString[3]);
		Log.i("4",""+qaString[4]);
		
	}
	
		private String read(String p, int i) throws IOException {
		// TODO Auto-generated method stub
		//从文件中读取指定行
			//InputStream in = new FileInputStream(p);
			InputStreamReader in = new InputStreamReader(getResources().getAssets().open(p));
		    BufferedReader reader=new BufferedReader(in);
		    String line=readLine(i,reader);//读取第i行
		    reader.close();
		    return line;
	}
		private String readLine(int lineNumber, BufferedReader reader) throws IOException {
			// TODO Auto-generated method stub
			String line="";
		       int i=0;
		       while(i<lineNumber){
		           line=reader.readLine();
		           i++;
		           //Log.i("tmp", line);
		       }
		       return line;	
		}
		// TODO Auto-generated method stub
		 /**
		 * 随机指定范围内N个不重复的数
		 * 最简单最基本的方法
		 * @param min 指定范围最小值
		 * @param max 指定范围最大值
		 * @param n 随机数个数
		 */
		public static int[] randomCommon(int min, int max, int n){
			if (n > (max - min + 1) || max < min) {
	            return null;
	        }
			int[] result = new int[n];
			int count = 0;
			while(count < n) {
				int num = (int) (Math.random() * (max - min)) + min;
				boolean flag = true;
				for (int j = 0; j < n; j++) {
					if(num == result[j]){
						flag = false;
						break;
					}
				}
				if(flag){
					result[count] = num;
					count++;
				}
			}
			return result;
		}
	
	View.OnClickListener myListener = new View.OnClickListener() {

		@Override
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int answerNo = 0;
			switch(v.getId()){
			case R.id.answer1: 
				answerNo = 1;
				break;
			case R.id.answer2: 
				answerNo = 2;
				
				break;
			case R.id.answer3: 
				answerNo = 3;
				
				break;
			case R.id.answer4: 
				answerNo = 4;
				
				break;
			default:
				return ;
			}
			
			if(answerNo == rightNumber){
				Constant.singleScore += timeLeft;////////////////////////先假设都是加20
				findViewById(v.getId()).setBackgroundResource(R.drawable.choice_right);
			}
			else{
				
				findViewById(v.getId()).setBackgroundResource(R.drawable.choice_fault);
				switch(rightNumber){
				case 1:
					answer1.setBackgroundResource(R.drawable.choice_right);
					break;
				case 2:
					answer2.setBackgroundResource(R.drawable.choice_right);
					break;
				case 3:
					answer3.setBackgroundResource(R.drawable.choice_right);
					break;
				case 4:
					answer4.setBackgroundResource(R.drawable.choice_right);
				}
			}
				answer1.setClickable(false);
				answer2.setClickable(false);
				answer3.setClickable(false);
				answer4.setClickable(false);
				timeLeft = 0;		
		}		
	};
	private   void  QARefresh() {
		// TODO Auto-generated method stub
		timeLeft=timeUpdate;
		timeView.setText(""+timeLeft);
		bar.setProgress(timeLeft);
		answer1.setBackgroundResource(R.drawable.choice_normal1);
		answer2.setBackgroundResource(R.drawable.choice_normal2);
		answer3.setBackgroundResource(R.drawable.choice_normal);
		answer4.setBackgroundResource(R.drawable.choice_normal4);
		question.setText("");
		for(int i=0;i<6;i++){
			try {
				qaString[i] =read(path,(questionNo[quesNumber]-1)*6+i+1);
				////System.out.println("读取："+qaString[i]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		question.setText(qaString[0]);
		answer1.setText(qaString[1]);
		answer2.setText(qaString[2]);
		answer3.setText(qaString[3]);
		answer4.setText(qaString[4]);
		rightNumber = Integer.valueOf(qaString[5]);
		answer1.setClickable(true);
		answer2.setClickable(true);
		answer3.setClickable(true);
		answer4.setClickable(true);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_game, menu);
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
}
