package com.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Message;
import android.util.Log;

import com.example.mmda.BaseActivity;
import com.until.Config;
import com.until.Constant;

public class NetWorker extends Thread {
	// Context context;
	String tag = "提示~~";
	private static final String IP = "172.24.30.76";
	private static final int PORT = 8898;

	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;

	int dataType;
	int flag = 0;

	private Boolean onWork = true;
	protected final byte connect = 1;
	protected final byte running = 2;
	protected byte state = connect;

	JSONObject jsonObject;
	JSONArray jsonArray;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		System.out.println("zhixingrunfangfa ne ");

		while (onWork) {

			switch (state) {
			case connect:
				System.out.println("lianjie houtai !");
				connect();
				break;
			case running:
				receiveMsg();
				break;
			}

		}
	}

	private void connect() {
		try {
			System.out.println("ganma ne ");
			socket = new Socket(IP, PORT);
			Log.i(tag, "连接到服务器啦");
			System.out.println("连接到服务器啦！");
			state = running;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream(), "UTF-8"));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void receiveMsg() {
		Log.i(tag, "一直在等待接受服务器返回的信息！");
		System.out.println("一直在等待接受服务器返回的信息！");
		try {
			String msg = in.readLine();
			Log.i(tag, "从服务器返回的消息是：" + msg);
			System.out.println("从服务器返回的消息是：" + msg);
			jsonObject = new JSONObject(msg);
			dataType = jsonObject.getInt("requestType");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 登录
		if (dataType == Config.REQUEST_LOGIN) {
			handLogin();

		}
		// 快速登录
		else if (dataType == Config.REQUEST_QUICK_LOGIN) {
			handregisterq();
		}
		// 注册
		else if (dataType == Config.REQUEST_REGISTER) {
			handRegister();
		}
		// 退出
		else if (dataType == Config.REQUEST_EXIT) {
			Message msg = new Message();
			int num = 7;
			msg.what = num;
			BaseActivity.sendMessage(msg);
		}
		// 获取道具商城
		else if (dataType == Config.REQUEST_GET_PROP) {
			handGetshop();
		}
		// 获取道具修改
		else if (dataType == Config.REQUEST_MODIFY_PROP) {
			handChangshop();
		}

		// 判断获取在线玩家
		else if (dataType == Config.REQUEST_GET_USERS_ONLINE) {
			handPlayerList();
		}
		// 判断类型为获取好友，在进行处理
		else if (dataType == Config.REQUEST_ADD_FRIEND) {
			handAddFriend();

		}
		//判断类型是否为邀战请求
		else if(dataType == Config.REQUEST_SEND_INVITE){
			
			handYaoZhan();
		}
		//判断对方否接收请求
		else if(dataType == Config.REQUEST_INVITE_RESULT){
			
			handInviteResult();
		}
		// 判断获取好友列表
		else if (dataType == Config.REQUEST_GET_FRIEND) {
			handFriendList();
		}
		//获取成语
		else if(dataType ==  Config.REQUEST_GET_QUESTION){
			handGetQuestion();
		}
		//获取好友的积分
		else if(dataType ==Config.REQUEST_ADD_PLAYERSCORE){
			handAddPlayerScore();
		}
		//获取积分
		else if(dataType == Config.REQUEST_GET_SCORES){
			handGetSocre();
		}
		//获取PK结果
		else if(dataType == Config.REQUEST_PK_RESULT){
			handPKResult();
		}
		//返回游戏中退出游戏的请求
		else if(dataType == Config.REQUEST_EXIT_GAME){
			handExitGameActivity();
		}
	}

	// 登录
	public void login(String userName, String password) {

		System.out.println("发送登录的请求ddd");
		// JSOn
		JSONObject jo = new JSONObject();
		try {
			jo.put("requestType", Config.REQUEST_LOGIN);
			jo.put("username", userName);
			jo.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i(tag, "发送登录的请求为：" + jo.toString());

		out.println(jo.toString());
	}

	// 传递登录
	public void handLogin() {
		Log.i(tag, "传递从服务器端返回的登录的请求");
		System.out.println("传递从服务器端返回的登录的请求");
		int result = 0;
		try {
			result = jsonObject.getInt("result");
			Message msg = new Message();
			msg.arg1 = result;
			msg.what = Config.REQUEST_LOGIN;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 快速登录
	public void registerq() {

		System.out.println("发送--快速登录--的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("requestType", Config.REQUEST_QUICK_LOGIN);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送快速登录请求为：" + jo.toString());

	}

	// 传递快速登录
	private void handregisterq() {
		Log.i(tag, "传递从服务器端返回的快速登录的请求");
		System.out.println("传递从服务器端返回的快速登录的请求");

		try {
			// int result = jsonObject.getInt("result");
			// if (result == Config.SUCCESS) {
			Constant.userName = jsonObject.getString("username");
			Constant.userPassword = jsonObject.getString("password");
			System.out.println(Constant.userName + "!!!!!!!!!!!!"
					+ Constant.userPassword);
			Message msg = new Message();
			msg.what = Config.REQUEST_QUICK_LOGIN;
			BaseActivity.sendMessage(msg);
			// } else if(result == Config.FAIl){
			// registerq();
			// }

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 注册
	public void register(String userName, String password) {
		Log.i(tag, "发送注册的请求dd");
		System.out.println("发送注册的请求dd");
		JSONObject jo = new JSONObject();
		try {
			jo.put("requestType", Config.REQUEST_REGISTER);
			jo.put("username", userName);
			jo.put("password", password);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送注册的请求为：" + jo.toString());
		System.out.println("发送注册的请求为：" + jo.toString());
	}

	// 传递注册
	private void handRegister() {
		Log.i(tag, "传递从服务器端返回的~注册~的请求");
		System.out.println("传递从服务器端返回的~注册~的请求");
		int result = 0;
		try {
			result = jsonObject.getInt("result");
			Message msg = new Message();
			msg.arg1 = result;
			msg.what = Config.REQUEST_REGISTER;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 退出游戏
	public void exitGame() {
		Log.i(tag, "发送退出游戏的请求");
		System.out.println("发送退出游戏的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("requestType", Config.REQUEST_EXIT);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送退出游戏的请求为：" + jo.toString());
		System.out.println("发送退出游戏的请求为：" + jo.toString());
	}

	// 商品的购买
	public void getshop(String username) {
		Log.i(tag, "发送获取道具的请求");
		System.out.println("发送获取道具的请求");

		JSONObject jo = new JSONObject();
		try {
			jo.put("username", username);
			jo.put("requestType", Config.REQUEST_GET_PROP);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送获取道具的请求为：" + jo.toString());
		System.out.println("发送获取道具的请求为：" + jo.toString());
	}

	// 商城线程处理
	public void handGetshop() {
		Log.i(tag, "传递从服务器端返回的~获取道具~的请求");
		System.out.println("传递从服务器端返回的~获取道具~的请求");
		
		try {
			int xmd = jsonObject.getInt("tip");
			int tsk = jsonObject.getInt("heart");
			//Log.i(tag, "~~~~~~~~~~~~~~~~heart"+String.valueOf(tsk));
			int score = jsonObject.getInt("score");
			// Constant.playerTskNum = tsk;
			// Constant.playerXmdNum = xmd;
			Message msg = new Message();
			msg.arg1 = tsk;
			msg.arg2 = xmd;
			Log.i(tag, "~~~~~~~~~~~~~~~~tip"+msg.arg1);
			Log.i(tag, "~~~~~~~~~~~~~~~~heart"+msg.arg2);
			Constant.playerXmdNum = msg.arg2;
			Constant.playerTskNum = msg.arg1;
			Constant.score = score;
			msg.what = Config.REQUEST_GET_PROP;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 道具的请求
	public void changshop(String prpoName, int num) {
		Log.i(tag, "发送更改道具的请求");
		System.out.println("发送更改道具的请求");

		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("propName", prpoName);
			jo.put("num", num);
			jo.put("requestType", Config.REQUEST_MODIFY_PROP);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送更改道具的请求为：" + jo.toString());
		System.out.println("发送更改道具的请求为：" + jo.toString());
	}

	// 商城道具线程处理
	public void handChangshop() {
		Log.i(tag, "传递从服务器端返回的~更改道具~的请求");
		System.out.println("传递从服务器端返回的~更改道具~的请求");
		try {
			int result = jsonObject.getInt("result");
			Message msg = new Message();
			msg.arg1 = result;
			msg.what = Config.REQUEST_MODIFY_PROP;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 发送获取玩家请求
	public void getPlayerList() {
		System.out.println("发送获取在线玩家的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("requestType", Config.REQUEST_GET_USERS_ONLINE);
			System.out.println(Constant.userName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送获取在线玩家的请求为：" + jo.toString());
	}
	
	// 线程处理在线玩家
	public void handPlayerList() {
		Log.i(tag, "传递从服务器端返回的~获取在线玩家~");
		System.out.println("传递从服务器端返回的~获取在线玩家");
		try {
			JSONArray ja = jsonObject.getJSONArray("list");
			System.out.println(ja.toString());
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			for(int i = 0; i<ja.length();i++ ){
				if (!ja.getJSONObject(i).optString("username")
						.equals(Constant.userName)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("score", ja.getJSONObject(i).optInt("score"));
					map.put("username",ja.getJSONObject(i).optString("username"));
					list.add(map);
					System.out.println(list);
				}
			}
			Message msg = new Message();
			msg.obj = list;
			msg.what = Config.REQUEST_GET_USERS_ONLINE;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {

			e.printStackTrace();
		}
	
	}

	// 向服务器发送添加好友请求
	public void addFriend(String friendname) {
		
		System.out.println("发送添加好友的请求");
		JSONObject jo = new JSONObject();
		try {
			/*jo.put("selfName", Constant.userName);
			jo.put("friendName", friendname);
			jo.put("requestType", Config.REQUEST_ADD_FRIEND);*/
			jo.put(Config.USERNAME, Constant.userName);
			jo.put("playername", friendname);
			jo.put(Config.REQUEST_TYPE, Config.REQUEST_ADD_FRIEND);
			out.println(jo.toString());
			System.out.println("添加好友的请求为："+jo.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.println(jo.toString());
		Log.i(tag, "发送添加好友的请求为："+jo.toString());
		System.out.println("发送添加好友的请求为："+jo.toString());
	}

	// 处理服务器返回好友
	private void handAddFriend() {
		Log.i(tag, "传递从服务器端返回的~添加好友列表~的请求");
		
		Message msg = new Message();
		try {
			msg.arg1=jsonObject.getInt("result");
			msg.what=Config.REQUEST_ADD_FRIEND;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 向服务器发送获取好友请求
	public void getFriendList() {
		
		System.out.println("发送好友列表的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("requestType", Config.REQUEST_GET_FRIEND);
			System.out.println("发送获取好友列表的请求为："+jo.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送好友列表的请求为："+jo.toString());
		
	}

	// 处理服务器好友列表
	private void handFriendList() {

		Log.i(tag, "传递从服务器端返回的~获取好友列表~的请求");
		JSONArray ja;
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		try {
			ja = jsonObject.optJSONArray("list");
			System.out.println(ja.toString());
			for(int i = 0; i<ja.length();i++){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("score", ja.getJSONObject(i).getInt("score"));
				map.put("friendName", ja.getJSONObject(i).getString("friendName"));
				list.add(map);
			}
		
		  } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		Message msg = new Message();
		msg.obj = list;
		msg.what=Config.REQUEST_GET_FRIEND;
	    BaseActivity.sendMessage(msg);
	}
	
    //向服务器发送邀请玩家挑战请求
	public void yaoZhan(String playerName, String userName, int model) {
		Log.i(tag, "发送邀战的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("playername", playerName);
			jo.put("username", userName);
			jo.put("model", model);
			jo.put("requestType", Config.REQUEST_SEND_INVITE);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送邀战的请求为："+jo.toString());
	}
	
	//服务器返回邀战的请求
	public void handYaoZhan(){
		Log.i(tag, "传递从服务器端返回的~邀战~的请求");
		System.out.println("传递从服务器端返回的~邀战~的请求");
		Message msg = new Message();
		try {
			msg.arg1=jsonObject.getInt("model");
			msg.obj=jsonObject.getString("username");
			msg.what=Config.REQUEST_SEND_INVITE;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//向服务器发送是否接受请求信息
	public void inviteResult(String playername, int result) {

		Log.i(tag, "发送是否接受邀请的请求");
		System.out.println("发送是否接受邀请的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("playername", playername);
			jo.put("requestType", Config.REQUEST_INVITE_RESULT);
			jo.put("result", result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送是否接受邀请的请求为："+jo.toString());
		System.out.println("发送是否接受邀请的请求为："+jo.toString());	
	}
	
	//服务器返回是否接收请求
	public void handInviteResult(){
		Log.i(tag, "传递从服务器端返回的~~是否接受邀请");
		System.out.println("传递从服务器端返回的~是否接受邀请~的请求");
		
		Message msg = new Message();
		try {
			msg.arg1=jsonObject.getInt("result");
			msg.what=Config.REQUEST_INVITE_RESULT;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//向服务器发送成语请求
	public void getQuestion(int typeMain,int typeSub) {
		System.out.println("发送获取的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("requestType", Config.REQUEST_GET_QUESTION);
			jo.put("typeMain", typeMain);
			jo.put("typeSub", typeSub);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送获取题目的请求为："+jo.toString());
	
	}
	
	//服务器传递成语的请求
	public void handGetQuestion(){
		
		Log.i(tag, "传递从服务器端返回的~获取题目列表~的请求");
		JSONArray ja;
		List<Map<String,Object>> list1= new ArrayList<Map<String,Object>>();
		try {
			ja = jsonObject.optJSONArray("list1");
			System.out.println("11"+ja.toString());
			int i=0;
			for(; i<ja.length();){
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("question", ja.getJSONObject(i).get("question"));
				map.put("answer1", ja.getJSONObject(i).get("answer1"));
				map.put("answer2", ja.getJSONObject(i).get("answer2"));
				map.put("answer3", ja.getJSONObject(i).get("answer3"));
				map.put("answer4", ja.getJSONObject(i).get("answer4"));
				map.put("right", ja.getJSONObject(i).get("right"));
				//map.put("friendName", ja.getJSONObject(i).getString("friendName"));
				list1.add(map);
				i=i+1;
			}
		
		  } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
		System.out.println("12"+list1.toString());
		Message msg = new Message();
		msg.obj = list1;
		msg.what=Config.REQUEST_GET_QUESTION;
	    BaseActivity.sendMessage(msg);
	}
	
	//发送玩家积分请求
	public void getScore(String name){
		Log.i(tag, "发送获取玩家积分的请求");
		
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", name);
			System.out.println(Constant.userName);
			jo.put("requestType", Config.REQUEST_GET_SCORES);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送获取玩家积分的请求为："+jo.toString());
		System.out.println("发送获取玩家积分的请求为："+jo.toString());
	}
	
	
	public void handGetSocre(){
		Log.i(tag, "传递从服务器端返回的~玩家积分~的请求");
		System.out.println("传递从服务器端返回的~玩家积分~的请求");
		try {
			 
			int score=jsonObject.getInt("score");
			Message msg = new Message();
			msg.arg1=score;
			msg.what=Config.REQUEST_GET_SCORES;
			BaseActivity.sendMessage(msg);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//发送添加积分的请求
	public void addScore(int num){
		Log.i(tag, "发送添加积分的请求");
		System.out.println("发送添加积分的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("requestType", Config.REQUEST_ADD_SCORES);
			jo.put("propName", "score");
			jo.put("num", num);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送添加积分的请求为："+jo.toString());
		System.out.println("发送添加积分的请求为："+jo.toString());
	}
	
	//向服务器发送挑战积分请求
	public void addPlayerScore(String playername,int num){
		
		System.out.println("发送挑战时添加积分的请求");
		JSONObject jo = new JSONObject();
		try {
			jo.put("playername", playername);
			jo.put("requestType", Config.REQUEST_ADD_PLAYERSCORE);
			jo.put("num", num);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "发送挑战时添加积分的请求为："+jo.toString());
	}
	
	//从服务器返回对战玩家的积分
    public void handAddPlayerScore(){
	
    	System.out.println("传递从服务器端返回的~对战玩家积分增加~的请求");
    	try {
    		int num=jsonObject.getInt("num");
    		Message msg = new Message();
    		msg.arg1=num;
    		msg.what=Config.REQUEST_ADD_PLAYERSCORE;
    		BaseActivity.sendMessage(msg);
    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    }
    
    //发送pK结果给服务器，让服务器判断谁胜利
    public void sendPKResult(String playername){
    	Log.i(tag, "发送pk结果的请求");
	
    	JSONObject jo = new JSONObject();
    	try {
    		jo.put("requestType", Config.REQUEST_PK_RESULT);
    		jo.put("playername", playername);
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
    	out.println(jo.toString());
    	System.out.println("发送pk结果的请求为："+jo.toString());
    }

     //服务器的返回结果
    public void handPKResult(){
    	  
	    System.out.println("传递从服务器端返回的~pk结果~的请求");
		Message msg = new Message();
		msg.what=Config.REQUEST_PK_RESULT;
		BaseActivity.sendMessage(msg);
    }
      
    public void exitGameAcitvity(String playername,String username){
  		Log.i(tag, "发送退出游戏界面的请求");
  		JSONObject jo = new JSONObject();
  		try {
  			jo.put("username", username);
  			jo.put("playername", playername);
  			jo.put("requestType", Config.REQUEST_EXIT_GAME);
  		} catch (JSONException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  		out.println(jo.toString());
  		System.out.println("发送退出游戏界面的请求为："+jo.toString());
  	}
      
  	public void handExitGameActivity(){
  		
  		Log.i(tag, "传递从服务器端返回的~~退出游戏界面");
  		Message msg = new Message();
  		try {
  			msg.obj = jsonObject.getString("username");
  			msg.what=Config.REQUEST_EXIT_GAME;
  			BaseActivity.sendMessage(msg);
  		} catch (JSONException e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
  		}
  	}
  	
	public void setOnWork(Boolean onWork) {
		this.onWork = onWork;
	}
	
}
