package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import org.omg.CORBA.PUBLIC_MEMBER;

import core.util.ConfigRead;

public class TestMain {

	private DataOutputStream output;
	private DataInputStream input;
	private String clientName;

	public static void main(String[] args) {
		new TestMain().Test();
		// new TestMain().ConnectServer();
	}
	public void Test() {
		Socket socket;
		try {
			socket = new Socket("127.0.0.1", Integer.valueOf(ConfigRead.getConfigProperties("server_port")).intValue());
			
			//this.TestRegistry(socket);
			//this.TestLogin(socket);
			this.TestAddFriend(socket);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void TestRegistry(Socket socket) throws IOException, SQLException, InterruptedException {
		TestCase.CleanDatabase();
		int userNum = TestCase.UserRegistry(socket, "xww", "passwd");
		//调用服务端的http接口(顺便测试一下http服务)，获取刚才创建的用户，然后对比验证
		//代码to do
	}

	public void TestLogin(Socket socket) throws IOException, InterruptedException, SQLException {
		TestCase.CleanDatabase();
		int userNum = TestCase.UserRegistry(socket, "xww", "passwd");
		TestCase.UserLogin(socket, userNum, "passwd");
		//调用服务端的http接口(顺便测试一下http服务)查询SocketMap里面有没有这个socket
		//代码to do
	}

	public void TestAddFriend(Socket socket) throws IOException, SQLException, InterruptedException {
		TestCase.CleanDatabase();
		int userNum1 = TestCase.UserRegistry(socket, "xww1", "passwd1");
		int userNum2 = TestCase.UserRegistry(socket, "xww2", "passwd2");
		int userNum3 = TestCase.UserRegistry(socket, "xww3", "passwd3");
		TestCase.AddFriend(socket, userNum1, userNum2);//xww1添加xww2作为好友
		TestCase.AddFriend(socket, userNum1, userNum3);//xww1添加xww3作为好友
		//调用服务端的http接口(顺便测试一下http服务)请求这三个人的详细信息，看user_friends里面是不是有正确信息
	}
	
	public void TestTalk(Socket socket) throws IOException, SQLException, InterruptedException{
		TestCase.CleanDatabase();
		int userNum1 = TestCase.UserRegistry(socket, "xww1", "passwd1");
		int userNum2 = TestCase.UserRegistry(socket, "xww2", "passwd2");
		
	}

	

	public void ConnectServer() {
		try {
			Socket socket = new Socket("127.0.0.1",
					Integer.valueOf(ConfigRead.getConfigProperties("server_port")).intValue());

			clientName = socket.getInetAddress().toString();
			input = new DataInputStream(socket.getInputStream());
			output = new DataOutputStream(socket.getOutputStream());
			new readServer().start();
			new writeServer().start();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public class readServer extends Thread {
		private Socket client;

		public void run() {
			String msg;
			try {
				while (true) {
					msg = input.readUTF();
					if (msg != null)
						System.out.println("收到消息：【" + clientName + "】 " + msg);
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

	public class writeServer extends Thread {
		private Socket client;

		public void run() {
			try {
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				String userInput;
				while (true) {
					if (stdIn.ready()) {
						userInput = stdIn.readLine();
						if (!userInput.equals("exit")) {
							output.writeUTF(userInput);
							System.out.println("已发送消息给【" + clientName + "】" + userInput);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}
}
