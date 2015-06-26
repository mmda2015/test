package com.example.mmda;

import com.example.mmda.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
  
public class MyMusic extends Service {  
  
    private MediaPlayer mediaPlayer;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId){
		super.onStart(intent, startId);
		if(mediaPlayer==null&&BaseActivity.choose_music==1){
			mediaPlayer = MediaPlayer.create(this, R.raw.bg_music);
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		}
		else if(mediaPlayer!=null&&BaseActivity.choose_music==2){
			mediaPlayer = MediaPlayer.create(this, R.raw.huosheng);
			mediaPlayer.setLooping(false);
			mediaPlayer.start();
		}
		else if(BaseActivity.choose_music==3){
			mediaPlayer = MediaPlayer.create(this, R.raw.shibai);
     		mediaPlayer.setLooping(false);
			mediaPlayer.start();
		}
		
	}
	
	
	@Override
	public void onDestroy(){
		super.onDestroy();
		mediaPlayer.stop();
	}
}  
