package com.network;

import android.content.Context;

public class Communication {
	
	Context context;
	private NetWorker netWorker;
	public static Communication instance;
	
	//����Communicationͨ�����ʵ��
	public static Communication newInstance() {
		if (instance == null) {
			instance = new Communication();
			System.out.println("���ӵ�������");
		}
		return instance;
	}
	
	//�����캯��˽�л���ʹ�䲻�����ɶ��ʵ������ֹ������ӷ�����
	private Communication() {
		netWorker = new NetWorker();
		netWorker.start();
	}
	
	//��½
	public void login(String userName, String password) {
		netWorker.login(userName, password);
	}
	//ע��
	public void register(String userName, String password) {
		netWorker.register(userName, password);
	}
	//�����˳���Ϸ����
	public void exitGame() {
		netWorker.exitGame();
	}
	//���ٵ�¼
	public void registerq() {
		netWorker.registerq();
	}
	//��Ʒ
	public void getshop(String name) {
		netWorker.getshop(name);
	}
	//����
	public void changshop(String prpoName, int num) {
		netWorker.changshop(prpoName, num);
	}
	//����б�
	public void getPlayerList() {
		netWorker.getPlayerList();
	}
	//�����б�
	public void getFriendList() {
		netWorker.getFriendList();
	}
	//������Ϊ����
	public void addFriend(String friendname) {
		netWorker.addFriend(friendname);
	}
	//���������ս
	public void yaoZhan(String playername, String username, int zfdmModel) {
		netWorker.yaoZhan(playername, username, zfdmModel);
	}
	//�Ƿ��������
	public void inviteResult(String playername, int result) {
		netWorker.inviteResult(playername, result);
	}
	//���ͳ�������
	public void getQuestion(int typeMain,int typeSub) {
		netWorker.getQuestion(typeMain,typeSub);
	}
	//pk�з��͵ķ���
	public void addPlayerScore(String username, int flag1) {
		netWorker.addPlayerScore(username, flag1);
	}
	//���ͻ�ȡ��һ���
	public void getScore(String name) {
		netWorker.getScore(name);
	}
	//��ӻ�������
	public void addScore(int num) {
		netWorker.addScore(num);
	}
	//����pkֵ
	public void sendPKResult(String playername) {
		netWorker.sendPKResult(playername);
	}
	//�����˳���Ϸ����
	public void exitGameActivity(String playername, String username) {
		netWorker.exitGameAcitvity(playername, username);
	}
	
	//�˳����ӣ������Դ
	public void clear() {
		netWorker.setOnWork(false);
		instance = null;
	}
	
}

