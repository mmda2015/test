package com.example.mmda;

import java.util.Timer;
import java.util.TimerTask;
import com.network.Communication;
import com.until.Config;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends BaseActivity {

	TextView username, password, repassword;
	String name, pwd, repwd;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȫ��
		requestWindowFeature(Window.FEATURE_NO_TITLE);// û�б�����
		setContentView(R.layout.activity_register);
		con = Communication.newInstance();
		username = (TextView) findViewById(R.id.user);
		password = (TextView) findViewById(R.id.pwd);
		repassword = (TextView) findViewById(R.id.repwd);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
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
	
	//��½
	public void ok(View view) {

		name = username.getText().toString().trim();
		pwd = password.getText().toString().trim();
		repwd = repassword.getText().toString().trim();
		
		/*Intent intent = new Intent();
		intent.setClass(RegisterActivity.this, OnlineMainActivity.class);
		startActivity(intent);
		finish();*/
		if(!"".equals(name) && !"".equals(pwd)){
		if(!pwd.equals(repwd)){
			Toast.makeText(RegisterActivity.this, "������������벻һ�£����������룡", Toast.LENGTH_LONG).show();
			((EditText)findViewById(R.id.pwd)).setText("");	//�������༭��
			((EditText)findViewById(R.id.repwd)).setText("");	//���ȷ������༭��
			((EditText)findViewById(R.id.pwd)).requestFocus();	//������༭���ý���
		}
		else
			con.register(name, pwd);
		}
		Timer time = new Timer();
		TimerTask task = new TimerTask() {				
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//con.login(name, psw);
			}
		};
		time.schedule(task, 3000);
	}
	
	//��½
	public void cancel(View view) {		
		Intent intent = new Intent();
		intent.setClass(RegisterActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void processMessage(Message message) {
		// TODO Auto-generated method stub
		System.out.println("����ע����Ϣ������");
		if(message.what==Config.REQUEST_REGISTER) {
			int result = message.arg1;
			if(result==Config.SUCCESS) {
				Toast.makeText(this,"ע��ɹ�",3000).show();
				Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
				isToNextAct = true;
				startActivity(intent);	//�����µ�Activity
				finish();
				message=null;
			}
			else
			{
				Toast.makeText(this,"ע��ʧ��",3000).show();
			}
		}
	}
	
}
