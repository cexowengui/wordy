package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import core.model.ClientSocket;
import core.model.User;
import core.util.ConfigRead;
import core.util.DBHelper;

public class TestCase {

	public static ClientSocket generateSocket() throws NumberFormatException, UnknownHostException, IOException {
		// 每次都会产生一个新的socket连接
		Socket socket = new Socket("127.0.0.1",
				Integer.valueOf(ConfigRead.getConfigProperties("server_port")).intValue());
		ClientSocket mySocket = new ClientSocket();
		mySocket.setSocket(socket);
		mySocket.setInput(new DataInputStream(socket.getInputStream()));
		mySocket.setOutput(new DataOutputStream(socket.getOutputStream()));		
		return mySocket;
	}
	
	public static void closeResource(ClientSocket mySocket) throws IOException{
		/*
		 * 关闭相关资源,不关闭的话也看不出来什么异常，但是会造成内存泄露，测试用例运行几万次，发现就不行了
		 */
		mySocket.getInput().close();
		mySocket.getOutput().close();
		mySocket.getSocket().close();
	}

	public static void cleanDatabase() throws IOException, SQLException {
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql1 = "delete from users";
		PreparedStatement pstmt1 = (PreparedStatement) conn.prepareStatement(sql1);
		pstmt1.executeUpdate();
		pstmt1.close();

		String sql2 = "delete from groups";
		PreparedStatement pstmt2 = (PreparedStatement) conn.prepareStatement(sql2);
		pstmt2.executeUpdate();
		pstmt2.close();
		dbHelper.close();
	}

	public static int userRegistry(ClientSocket mySocket, String userName, String passwd)
			throws IOException, InterruptedException {
		// 注册协议：0+username_passwd 详细参考MessageConstant.java文件注释说明
		mySocket.getOutput().writeUTF("0+" + userName + "+" + passwd);
		while (true) {
			String msg = mySocket.getInput().readUTF();
			if (msg != null) {
				// 服务器回复消息格式为：1+OK+123456或者FAIL+……
				// 详细参考MessageConstant.java文件注释说明
				String[] msgStrings = msg.split("\\+");
				System.out.println("userRegistry:接收到服务端的消息：" + msg.toString());
				return Integer.parseInt(msgStrings[2]);
			}
		}

	}

	public static boolean userLogin(ClientSocket mySocket, int userNum, String passwd) throws IOException {
		// 登录协议：1+123456_passwd 详细参考MessageConstant.java文件注释说明
		mySocket.getOutput().writeUTF("1+" + String.valueOf(userNum) + "+" + passwd);

		/*
		 * 这里其实不用接受服务器的回复消息，但是服务端确实往这个socket里面写了数据的，如果不接收的话
		 * 那么下次再接受的话会把属于这个登录请求的回复一起接收出来，那样的话就没办法区分了；这就是消息黏连问题
		 * 属于比较复杂的一个问题。解决的办法多种多样，比如我们可以这样改进：客户端开启两个socket，一个用于和服务器交互，
		 * 这种socket是同步的，就是发送一个请求就等待消息回复，取出来，比如添加还有申请注册加群等请求；
		 * 另外一个就是用来聊天的socket，这种的消息不要求太准确，即使有黏连什么的，消息直接显示，用户也看得懂； 两种消息通道就分开了
		 */
		while (true) {
			String msg = mySocket.getInput().readUTF();
			if (msg != null) {
				System.out.println("userLogin:接收到服务端的消息：" + msg.toString());
				// 服务器回复消息格式为：1+OK+null或者1+FAIL+……
				// 详细参考MessageConstant.java文件注释说明
				if (msg.split("\\+")[1].equals("OK")) {
					//登录正确，那么启动一个线程定时更新这个socket的时间，当做心跳给服务器，否则会被服务器清理掉的
					//new Thread(new UpdateHeartBeatThread(mySocket, userNum)).start();
					return true;
				}
				return false;
			}
		}
	}

	public static boolean addFriend(ClientSocket mySocket, int userNum, int friendNum) throws IOException {
		// 2+123456+654321 QQ号码为123456的用户申请添加654321为好友
		mySocket.getOutput().writeUTF("2+" + String.valueOf(userNum) + "+" + String.valueOf(friendNum));
		while (true) {
			String msg = mySocket.getInput().readUTF();
			if (msg != null) {
				System.out.println("addFriend:接收到服务端的消息：" + msg.toString());
				// 服务器回复消息格式为：1+OK+null或者1+FAIL+……
				// 详细参考MessageConstant.java文件注释说明
				if (msg.split("\\+")[1].equals("OK")) {
					return true;
				}
				return false;
			}
		}
	}
	
	/*public static void talKWithFriend(ClientSocket mySocket, User user, User friend){
		//5+123456+654321+123+234 用户123456给用户号码或者群号码为654321发送内容为123+456的消息
		//和好友聊天，本质上还是和服务器聊天，因为所有的消息都要经过服务器转发的
		
		//user用户开始给friend发送消息并接受消息
		ReadWriteThread rw = new ReadWriteThread(mySocket, user);
		rw.writeThread(friend);//开启发送消息线程,发送给对方的内容就是当前时间
		rw.readThread();//开启接受消息线程				
		
	}*/
}
