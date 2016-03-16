package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import core.model.ClientSocket;
import core.model.User;

class writeService extends Thread {
	private static int count = 0;
	private ClientSocket mySocket;
	private User user;
	private User friend;
	public writeService(ClientSocket mySocket, User user, User friend){
		this.mySocket = mySocket;
		this.user = user;
		this.friend = friend;			
	}		
	public void run() {
		while(true){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			//5+123456+654321+123+234 用户123456给用户号码或者群号码为654321发送内容为123+456的消息
			//和好友聊天，本质上还是和服务器聊天，因为所有的消息都要经过服务器转发的
			String msg = "5+" + user.getUserNum() + "+" + friend.getUserNum() +
					"+" + df.format(new Date());
			try {
				mySocket.getOutput().writeUTF(msg);
				System.out.println("我是" + user.getUserName() + ",已发送消息:" + msg);
				Thread.sleep((long)(Math.random()*1000+1000));//随机休眠时间1000ms到2000毫秒，模拟真实的用户发送消息
				if (count > 10) {//读写线程都对count进行自增，存在竞争，所以两者的输出加起来才是count次，这里只是测试，
					System.out.println("我是" + user.getUserName() + "count已经等于" + count);
					break;       //目的只是为了这个线程能够退出，所以就无所谓了
				} else {
					count++;
					System.out.println("我是" + user.getUserName() + "count=" + count);
				}
				
			} catch (Exception e) {
				System.out.println("write e "+ e.toString());
			}			
			
		}
		
	}

}


class readService extends Thread {
	private static int count = 0;
	private ClientSocket mySocket;
	private User user;
	private User friend;
	public readService(ClientSocket mySocket, User user, User friend){
		this.mySocket = mySocket;
		this.user = user;
		this.friend = friend;			
	}		

	public void run() {
		String msg;
		try {
			while (true) {
				msg = mySocket.getInput().readUTF();
				if (msg != null)
					System.out.println("我是" + user.getUserName() + ",收到消息:" + msg);
				if (count > 10) {//读写线程都对count进行自增，存在竞争，所以两者的输出加起来才是count次，这里只是测试，目的只是为了这个线程能够退出，所以就无所谓了
					System.out.println("我是" + user.getUserName() + "count已经等于" + count);
					break;       
				} else {
					count++;
					System.out.println("我是" + user.getUserName() + "count=" + count);
				}
			}
		} catch (Exception e) {
			System.out.println("read e "+ e.toString());
		}
	}
}

/*public class ReadWriteThread {
	private static int count = 0;
	private ClientSocket clientSocket;
	private User user;

	public ReadWriteThread(ClientSocket clientSocket, User user) {
		this.clientSocket = clientSocket;
		this.user = user;
	}
	
	public void readThread(){
		new readService().start();
	}
	
    public void writeThread(User friend){
		new writeService(friend).start();
	}
	

	public class readService extends Thread {

		public void run() {
			String msg;
			try {
				while (true) {
					msg = clientSocket.getInput().readUTF();
					if (msg != null)
						System.out.println("我是" + user.getUserName() + ",收到消息:" + msg);
					if (count > 10) {//读写线程都对count进行自增，存在竞争，所以两者的输出加起来才是count次，这里只是测试，目的只是为了这个线程能够退出，所以就无所谓了
						System.out.println("我是" + user.getUserName() + "count已经等于" + count);
						break;       
					} else {
						count++;
						System.out.println("我是" + user.getUserName() + "count=" + count);
					}
				}
			} catch (Exception e) {
				System.out.println("read e "+ e.toString());
			}finally {
				System.out.println("我是" + user.getUserName() + "退出读线程" );
				try {//包含早try里面，就算是重复关闭也无所谓，否则需要用if判断是否已经关闭
					clientSocket.getInput().close();
					clientSocket.getOutput().close();
					clientSocket.getSocket().close();
					
				} catch (Exception e2) {
					System.out.println("read e2 "+ e2.toString());
				}
			}	
		}
	}

	public class writeService extends Thread {		
		private User friend;
		public writeService(User friend){
			this.friend = friend;			
		}
			
		public void run() {
			while(true){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				//5+123456+654321+123+234 用户123456给用户号码或者群号码为654321发送内容为123+456的消息
				//和好友聊天，本质上还是和服务器聊天，因为所有的消息都要经过服务器转发的
				String msg = "5+" + user.getUserNum() + "+" + friend.getUserNum() +
						"+" + df.format(new Date());
				try {
					clientSocket.getOutput().writeUTF(msg);
					System.out.println("我是" + user.getUserName() + ",已发送消息:" + msg);
					Thread.sleep((long)(Math.random()*1000+1000));//随机休眠时间1000ms到2000毫秒，模拟真实的用户发送消息
					if (count > 10) {//读写线程都对count进行自增，存在竞争，所以两者的输出加起来才是count次，这里只是测试，
						System.out.println("我是" + user.getUserName() + "count已经等于" + count);
						break;       //目的只是为了这个线程能够退出，所以就无所谓了
					} else {
						count++;
						System.out.println("我是" + user.getUserName() + "count=" + count);
					}
					
				} catch (Exception e) {
					System.out.println("write e "+ e.toString());
				}finally {
					System.out.println("我是" + user.getUserName() + "退出写线程" );
					try {//包含早try里面，就算是重复关闭也无所谓，否则需要用if判断是否已经关闭
						clientSocket.getInput().close();
						clientSocket.getOutput().close();
						clientSocket.getSocket().close();
						
					} catch (Exception e2) {
						System.out.println("write e2 "+ e2.toString());
					}
				}				
				
			}
			
		}

	}
}*/

class UpdateHeartBeatThread implements Runnable{
	private ClientSocket socket;
	private int userNum;
	public UpdateHeartBeatThread(ClientSocket socket, int userNum){
		this.socket = socket;
		this.userNum = userNum;
	}

	@Override
	public void run() {		
		while (true) { 
			try {
				//6+123456+1457602965193 用户保活心跳，避免服务器清理socket线程把自己清理掉，
				socket.getOutput().writeUTF("6+" + String.valueOf(userNum) + "+1234567895621");//后面的时间用不上，服务器自己取它本地时间
				Thread.sleep(10000);
			} catch (IOException e) {				
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
	

