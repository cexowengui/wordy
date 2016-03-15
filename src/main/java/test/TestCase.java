package test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import core.util.DBHelper;

public class TestCase {
	
	public static void CleanDatabase() throws IOException, SQLException{
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql1 = "delete from users";		
		PreparedStatement pstmt1 = (PreparedStatement)conn.prepareStatement(sql1);
		pstmt1.executeUpdate();
		pstmt1.close();
		
		String sql2 = "delete from groups";
		PreparedStatement pstmt2 = (PreparedStatement)conn.prepareStatement(sql2);
		pstmt2.executeUpdate();		
		pstmt2.close();
		dbHelper.close();
	}
	public static int UserRegistry(Socket socket, String userName, String passwd) throws IOException, InterruptedException {
		//注册协议：0+username_passwd 详细参考MessageConstant.java文件注释说明
		new DataOutputStream(socket.getOutputStream()).writeUTF("0+" + userName + "+" + passwd);
		while(true){
			String msg = new DataInputStream(socket.getInputStream()).readUTF();
			if (msg != null) {
				//服务器回复消息格式为：1+OK+123456或者FAIL+…… 详细参考MessageConstant.java文件注释说明
				String[] msgStrings = msg.split("\\+");
				System.out.println("接收到服务端的消息：" + msg.toString());
				Thread.sleep(2000);
				return Integer.parseInt(msgStrings[2]);
			}
		}
		
	}
	
	public static boolean UserLogin(Socket socket, int userNum, String passwd) throws IOException{
		//登录协议：1+123456_passwd 详细参考MessageConstant.java文件注释说明
		new DataOutputStream(socket.getOutputStream()).writeUTF("1+" + String.valueOf(userNum) + "+" + passwd);
		
		/*这里其实不用接受服务器的回复消息，但是服务端确实往这个socket里面写了数据的，如果不接收的话
		那么下次再接受的话会把属于这个登录请求的回复一起接收出来，那样的话就没办法区分了；这就是消息黏连问题
		属于比较复杂的一个问题。解决的办法多种多样，比如我们可以这样改进：客户端开启两个socket，一个用于和服务器交互，
		这种socket是同步的，就是发送一个请求就等待消息回复，取出来，比如添加还有申请注册加群等请求；
		另外一个就是用来聊天的socket，这种的消息不要求太准确，即使有黏连什么的，消息直接显示，用户也看得懂；
		两种消息通道就分开了
		*/
		while(true){
			String msg = new DataInputStream(socket.getInputStream()).readUTF();
			if (msg != null) {
				//服务器回复消息格式为：1+OK+null或者1+FAIL+…… 详细参考MessageConstant.java文件注释说明				
				if(msg.split("\\+")[1].equals("OK")){
					return true;
				}
				return false;
			}
		}
	}
	
	public static boolean AddFriend(Socket socket, int userNum, int friendNum) throws IOException{
		//2+123456+654321 QQ号码为123456的用户申请添加654321为好友
		new DataOutputStream(socket.getOutputStream()).writeUTF("2+"+String.valueOf(userNum)+"+"+String.valueOf(friendNum));
		while(true){
			String msg = new DataInputStream(socket.getInputStream()).readUTF();
			if (msg != null) {
				//服务器回复消息格式为：1+OK+null或者1+FAIL+…… 详细参考MessageConstant.java文件注释说明				
				if(msg.split("\\+")[1].equals("OK")){
					return true;
				}
				return false;
			}
		}
	}
}
