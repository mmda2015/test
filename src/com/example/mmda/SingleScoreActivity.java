package com.example.mmda;

import com.until.Constant;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.mmda.BaseActivity;

public class SingleScoreActivity extends BaseActivity {
	private TextView scoreText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 没有标题栏
		setContentView(R.layout.activity_single_score);
		scoreText = (TextView) findViewById(R.id.scoretext);
		scoreText.setText(""+Constant.singleScore);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single_score, menu);
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
		
	}
}
