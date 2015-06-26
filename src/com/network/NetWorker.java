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
	String tag = "��ʾ~~";
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
			Log.i(tag, "���ӵ���������");
			System.out.println("���ӵ�����������");
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
		Log.i(tag, "һֱ�ڵȴ����ܷ��������ص���Ϣ��");
		System.out.println("һֱ�ڵȴ����ܷ��������ص���Ϣ��");
		try {
			String msg = in.readLine();
			Log.i(tag, "�ӷ��������ص���Ϣ�ǣ�" + msg);
			System.out.println("�ӷ��������ص���Ϣ�ǣ�" + msg);
			jsonObject = new JSONObject(msg);
			dataType = jsonObject.getInt("requestType");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ��¼
		if (dataType == Config.REQUEST_LOGIN) {
			handLogin();

		}
		// ���ٵ�¼
		else if (dataType == Config.REQUEST_QUICK_LOGIN) {
			handregisterq();
		}
		// ע��
		else if (dataType == Config.REQUEST_REGISTER) {
			handRegister();
		}
		// �˳�
		else if (dataType == Config.REQUEST_EXIT) {
			Message msg = new Message();
			int num = 7;
			msg.what = num;
			BaseActivity.sendMessage(msg);
		}
		// ��ȡ�����̳�
		else if (dataType == Config.REQUEST_GET_PROP) {
			handGetshop();
		}
		// ��ȡ�����޸�
		else if (dataType == Config.REQUEST_MODIFY_PROP) {
			handChangshop();
		}

		// �жϻ�ȡ�������
		else if (dataType == Config.REQUEST_GET_USERS_ONLINE) {
			handPlayerList();
		}
		// �ж�����Ϊ��ȡ���ѣ��ڽ��д���
		else if (dataType == Config.REQUEST_ADD_FRIEND) {
			handAddFriend();

		}
		//�ж������Ƿ�Ϊ��ս����
		else if(dataType == Config.REQUEST_SEND_INVITE){
			
			handYaoZhan();
		}
		//�ж϶Է����������
		else if(dataType == Config.REQUEST_INVITE_RESULT){
			
			handInviteResult();
		}
		// �жϻ�ȡ�����б�
		else if (dataType == Config.REQUEST_GET_FRIEND) {
			handFriendList();
		}
		//��ȡ����
		else if(dataType ==  Config.REQUEST_GET_QUESTION){
			handGetQuestion();
		}
		//��ȡ���ѵĻ���
		else if(dataType ==Config.REQUEST_ADD_PLAYERSCORE){
			handAddPlayerScore();
		}
		//��ȡ����
		else if(dataType == Config.REQUEST_GET_SCORES){
			handGetSocre();
		}
		//��ȡPK���
		else if(dataType == Config.REQUEST_PK_RESULT){
			handPKResult();
		}
		//������Ϸ���˳���Ϸ������
		else if(dataType == Config.REQUEST_EXIT_GAME){
			handExitGameActivity();
		}
	}

	// ��¼
	public void login(String userName, String password) {

		System.out.println("���͵�¼������ddd");
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
		Log.i(tag, "���͵�¼������Ϊ��" + jo.toString());

		out.println(jo.toString());
	}

	// ���ݵ�¼
	public void handLogin() {
		Log.i(tag, "���ݴӷ������˷��صĵ�¼������");
		System.out.println("���ݴӷ������˷��صĵ�¼������");
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

	// ���ٵ�¼
	public void registerq() {

		System.out.println("����--���ٵ�¼--������");
		JSONObject jo = new JSONObject();
		try {
			jo.put("requestType", Config.REQUEST_QUICK_LOGIN);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "���Ϳ��ٵ�¼����Ϊ��" + jo.toString());

	}

	// ���ݿ��ٵ�¼
	private void handregisterq() {
		Log.i(tag, "���ݴӷ������˷��صĿ��ٵ�¼������");
		System.out.println("���ݴӷ������˷��صĿ��ٵ�¼������");

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

	// ע��
	public void register(String userName, String password) {
		Log.i(tag, "����ע�������dd");
		System.out.println("����ע�������dd");
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
		Log.i(tag, "����ע�������Ϊ��" + jo.toString());
		System.out.println("����ע�������Ϊ��" + jo.toString());
	}

	// ����ע��
	private void handRegister() {
		Log.i(tag, "���ݴӷ������˷��ص�~ע��~������");
		System.out.println("���ݴӷ������˷��ص�~ע��~������");
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

	// �˳���Ϸ
	public void exitGame() {
		Log.i(tag, "�����˳���Ϸ������");
		System.out.println("�����˳���Ϸ������");
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("requestType", Config.REQUEST_EXIT);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "�����˳���Ϸ������Ϊ��" + jo.toString());
		System.out.println("�����˳���Ϸ������Ϊ��" + jo.toString());
	}

	// ��Ʒ�Ĺ���
	public void getshop(String username) {
		Log.i(tag, "���ͻ�ȡ���ߵ�����");
		System.out.println("���ͻ�ȡ���ߵ�����");

		JSONObject jo = new JSONObject();
		try {
			jo.put("username", username);
			jo.put("requestType", Config.REQUEST_GET_PROP);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "���ͻ�ȡ���ߵ�����Ϊ��" + jo.toString());
		System.out.println("���ͻ�ȡ���ߵ�����Ϊ��" + jo.toString());
	}

	// �̳��̴߳���
	public void handGetshop() {
		Log.i(tag, "���ݴӷ������˷��ص�~��ȡ����~������");
		System.out.println("���ݴӷ������˷��ص�~��ȡ����~������");
		
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

	// ���ߵ�����
	public void changshop(String prpoName, int num) {
		Log.i(tag, "���͸��ĵ��ߵ�����");
		System.out.println("���͸��ĵ��ߵ�����");

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
		Log.i(tag, "���͸��ĵ��ߵ�����Ϊ��" + jo.toString());
		System.out.println("���͸��ĵ��ߵ�����Ϊ��" + jo.toString());
	}

	// �̳ǵ����̴߳���
	public void handChangshop() {
		Log.i(tag, "���ݴӷ������˷��ص�~���ĵ���~������");
		System.out.println("���ݴӷ������˷��ص�~���ĵ���~������");
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

	// ���ͻ�ȡ�������
	public void getPlayerList() {
		System.out.println("���ͻ�ȡ������ҵ�����");
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
		Log.i(tag, "���ͻ�ȡ������ҵ�����Ϊ��" + jo.toString());
	}
	
	// �̴߳����������
	public void handPlayerList() {
		Log.i(tag, "���ݴӷ������˷��ص�~��ȡ�������~");
		System.out.println("���ݴӷ������˷��ص�~��ȡ�������");
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

	// �������������Ӻ�������
	public void addFriend(String friendname) {
		
		System.out.println("������Ӻ��ѵ�����");
		JSONObject jo = new JSONObject();
		try {
			/*jo.put("selfName", Constant.userName);
			jo.put("friendName", friendname);
			jo.put("requestType", Config.REQUEST_ADD_FRIEND);*/
			jo.put(Config.USERNAME, Constant.userName);
			jo.put("playername", friendname);
			jo.put(Config.REQUEST_TYPE, Config.REQUEST_ADD_FRIEND);
			out.println(jo.toString());
			System.out.println("��Ӻ��ѵ�����Ϊ��"+jo.toString());
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.println(jo.toString());
		Log.i(tag, "������Ӻ��ѵ�����Ϊ��"+jo.toString());
		System.out.println("������Ӻ��ѵ�����Ϊ��"+jo.toString());
	}

	// ������������غ���
	private void handAddFriend() {
		Log.i(tag, "���ݴӷ������˷��ص�~��Ӻ����б�~������");
		
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

	// ����������ͻ�ȡ��������
	public void getFriendList() {
		
		System.out.println("���ͺ����б������");
		JSONObject jo = new JSONObject();
		try {
			jo.put("username", Constant.userName);
			jo.put("requestType", Config.REQUEST_GET_FRIEND);
			System.out.println("���ͻ�ȡ�����б������Ϊ��"+jo.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println(jo.toString());
		Log.i(tag, "���ͺ����б������Ϊ��"+jo.toString());
		
	}

	// ��������������б�
	private void handFriendList() {

		Log.i(tag, "���ݴӷ������˷��ص�~��ȡ�����б�~������");
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
	
    //��������������������ս����
	public void yaoZhan(String playerName, String userName, int model) {
		Log.i(tag, "������ս������");
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
		Log.i(tag, "������ս������Ϊ��"+jo.toString());
	}
	
	//������������ս������
	public void handYaoZhan(){
		Log.i(tag, "���ݴӷ������˷��ص�~��ս~������");
		System.out.println("���ݴӷ������˷��ص�~��ս~������");
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
	
	//������������Ƿ����������Ϣ
	public void inviteResult(String playername, int result) {

		Log.i(tag, "�����Ƿ�������������");
		System.out.println("�����Ƿ�������������");
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
		Log.i(tag, "�����Ƿ�������������Ϊ��"+jo.toString());
		System.out.println("�����Ƿ�������������Ϊ��"+jo.toString());	
	}
	
	//�����������Ƿ��������
	public void handInviteResult(){
		Log.i(tag, "���ݴӷ������˷��ص�~~�Ƿ��������");
		System.out.println("���ݴӷ������˷��ص�~�Ƿ��������~������");
		
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
	
	//����������ͳ�������
	public void getQuestion(int typeMain,int typeSub) {
		System.out.println("���ͻ�ȡ������");
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
		Log.i(tag, "���ͻ�ȡ��Ŀ������Ϊ��"+jo.toString());
	
	}
	
	//���������ݳ��������
	public void handGetQuestion(){
		
		Log.i(tag, "���ݴӷ������˷��ص�~��ȡ��Ŀ�б�~������");
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
	
	//������һ�������
	public void getScore(String name){
		Log.i(tag, "���ͻ�ȡ��һ��ֵ�����");
		
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
		Log.i(tag, "���ͻ�ȡ��һ��ֵ�����Ϊ��"+jo.toString());
		System.out.println("���ͻ�ȡ��һ��ֵ�����Ϊ��"+jo.toString());
	}
	
	
	public void handGetSocre(){
		Log.i(tag, "���ݴӷ������˷��ص�~��һ���~������");
		System.out.println("���ݴӷ������˷��ص�~��һ���~������");
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
	
	//������ӻ��ֵ�����
	public void addScore(int num){
		Log.i(tag, "������ӻ��ֵ�����");
		System.out.println("������ӻ��ֵ�����");
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
		Log.i(tag, "������ӻ��ֵ�����Ϊ��"+jo.toString());
		System.out.println("������ӻ��ֵ�����Ϊ��"+jo.toString());
	}
	
	//�������������ս��������
	public void addPlayerScore(String playername,int num){
		
		System.out.println("������սʱ��ӻ��ֵ�����");
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
		Log.i(tag, "������սʱ��ӻ��ֵ�����Ϊ��"+jo.toString());
	}
	
	//�ӷ��������ض�ս��ҵĻ���
    public void handAddPlayerScore(){
	
    	System.out.println("���ݴӷ������˷��ص�~��ս��һ�������~������");
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
    
    //����pK��������������÷������ж�˭ʤ��
    public void sendPKResult(String playername){
    	Log.i(tag, "����pk���������");
	
    	JSONObject jo = new JSONObject();
    	try {
    		jo.put("requestType", Config.REQUEST_PK_RESULT);
    		jo.put("playername", playername);
	    } catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
    	out.println(jo.toString());
    	System.out.println("����pk���������Ϊ��"+jo.toString());
    }

     //�������ķ��ؽ��
    public void handPKResult(){
    	  
	    System.out.println("���ݴӷ������˷��ص�~pk���~������");
		Message msg = new Message();
		msg.what=Config.REQUEST_PK_RESULT;
		BaseActivity.sendMessage(msg);
    }
      
    public void exitGameAcitvity(String playername,String username){
  		Log.i(tag, "�����˳���Ϸ���������");
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
  		System.out.println("�����˳���Ϸ���������Ϊ��"+jo.toString());
  	}
      
  	public void handExitGameActivity(){
  		
  		Log.i(tag, "���ݴӷ������˷��ص�~~�˳���Ϸ����");
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
