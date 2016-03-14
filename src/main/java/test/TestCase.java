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
	
	public void CleanDatabase() throws IOException, SQLException{
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
	public int UserRegistry(Socket socket, String userName, String passwd) throws IOException {
		new DataOutputStream(socket.getOutputStream()).writeUTF("1+" + userName + passwd);
		while(true){
			String msg = new DataInputStream(socket.getInputStream()).readUTF();
			if (msg != null) {
				//服务器回复消息格式为：OK+123456或者FAIL+reason 详细参考MessageConstant.java文件注释说明
				String[] msgStrings = msg.split("\\+");
				return Integer.parseInt(msgStrings[1]);
			}
		}
		
	}

}
