package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.CORBA.PUBLIC_MEMBER;

import core.dao.Dao;
import core.model.ClientSocket;
import core.model.User;
import core.util.ConfigRead;

public class TestMain {

	private DataOutputStream output;
	private DataInputStream input;
	private String clientName;

	public static void main(String[] args) {
		try {
			new TestMain().Test();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// new TestMain().ConnectServer();
	}

	public void Test() throws IOException {

		try {
			// this.TestRegistry();
			// this.TestLogin();
			// this.TestAddFriend();
			this.TestTalk();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void TestRegistry() throws IOException, SQLException, InterruptedException {
		TestCase.cleanDatabase();
		ClientSocket mySocket = TestCase.generateSocket();
		int userNum = TestCase.userRegistry(mySocket, "xww", "passwd");
		// 调用服务端的http接口(顺便测试一下http服务)，获取刚才创建的用户，然后对比验证
		// 代码to do
		TestCase.closeResource(mySocket);
	}

	public void TestLogin() throws IOException, InterruptedException, SQLException {
		TestCase.cleanDatabase();
		// 同一个客户端的请求，共用一个socket
		ClientSocket mySocket = TestCase.generateSocket();
		int userNum = TestCase.userRegistry(mySocket, "xww", "passwd");
		TestCase.userLogin(mySocket, userNum, "passwd");
		// 调用服务端的http接口(顺便测试一下http服务)查询SocketMap里面有没有这个socket
		// 代码to do
		TestCase.closeResource(mySocket);
	}

	public void TestAddFriend() throws IOException, SQLException, InterruptedException {
		TestCase.cleanDatabase();
		ClientSocket mySocket = TestCase.generateSocket();
		int userNum1 = TestCase.userRegistry(mySocket, "xww1", "passwd1");
		int userNum2 = TestCase.userRegistry(mySocket, "xww2", "passwd2");
		int userNum3 = TestCase.userRegistry(mySocket, "xww3", "passwd3");
		//TestCase.userLogin(mySocket, userNum1, "passwd");跟服务器打交道的请求不需要登录，只有两个客户端之间聊天需要登录
		TestCase.addFriend(mySocket, userNum1, userNum2);// xww1添加xww2作为好友
		TestCase.addFriend(mySocket, userNum1, userNum3);// xww1添加xww3作为好友
		// 调用服务端的http接口(顺便测试一下http服务)请求这三个人的详细信息，看user_friends里面是不是有正确信息
		TestCase.closeResource(mySocket);
	}

	public void TestTalk()  {
		try {
			TestCase.cleanDatabase();
			// 我们模拟这里是两个客户端分别发起的聊天，所以每个客户端一个独立的socket
			ClientSocket mySocket1 = TestCase.generateSocket();
			ClientSocket mySocket2 = TestCase.generateSocket();
			int userNum1 = TestCase.userRegistry(mySocket1, "xww1", "passwd1");
			int userNum2 = TestCase.userRegistry(mySocket2, "xww2", "passwd2");
			TestCase.addFriend(mySocket1, userNum1, userNum2);// 这里只需要xww1添加xww2就可以了
			
			//必须要登录，否则服务器没办法将消息转发给对方，因为只有登录的用户才会把socket保存在全局变量里面供服务器查找
			TestCase.userLogin(mySocket1, userNum1, "passwd1");
			TestCase.userLogin(mySocket2, userNum2, "passwd2");
			
			User userxww1 = new Dao().getUserByUserNum(userNum1);
			User userxww2 = new Dao().getUserByUserNum(userNum2);
			
			//user xww1用户开始给friend xww2发送消息并接受消息
			writeService write1 =  new writeService(mySocket1, userxww1, userxww2) ;
			write1.start();
			readService read1 = new readService(mySocket1, userxww1, userxww2);
			read1.start();			
			
			//user xww2用户开始给friend xww1发送消息并接受消息
			writeService write2 =  new writeService(mySocket2, userxww2, userxww1) ;
			write2.start();
			readService read2 = new readService(mySocket2, userxww2, userxww1);
			read2.start();
			
			//不能在这里直接关闭资源，否则上面的双方读写线程刚启动就关闭了，要使用join等到双方的两个子线程结束后才可以关闭资源
			//上面的几个线程都会在一定时间内结束的
			//TestCase.closeResource(mySocket1);
			//TestCase.closeResource(mySocket2);
			//如下：			
			write1.join();
			read1.join();
			write2.join();
			read2.join();			
			System.out.println("两个用户的读写线程都结束了,关闭相关资源");
			TestCase.closeResource(mySocket1);
			TestCase.closeResource(mySocket2);			
			System.out.println("main end");
			
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
}
