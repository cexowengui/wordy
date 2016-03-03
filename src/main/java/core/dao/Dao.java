package core.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.example.socket.SocketServer.readClient;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import core.model.RequestDetail;
import core.model.User;
import core.util.DBHelper;

public class Dao {
	/*
	 * 产生用户的唯一号码，类似QQ号码，因为我们这里不会涉及到数据库分表，所以不用采用时间+随机数组成，
	      一般生产环境为了冷热数据区分，比如聊天记录，一般都是查找最近的，那么就可以根据记录的产生时间直接把
	      数据库的表分为多部分，老数据存储到廉价存储设备中去，最近的热数据存储到快速设备上。
	      我们这里直接从10000开始递增分配
	 */
	public int generateUserNum() throws SQLException{
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql = "select max(user_num) from users";
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		int result;
		if(rs.next()){
			result = rs.getInt(1)+1;
		}else{
			result = 10000;
		}		
		pstmt.close();
		dbHelper.close();
		return result;
	}
	
	public boolean userRegistry(RequestDetail requestDetail) throws SQLException{
		String userName = requestDetail.getRegistryRequest().getUserName();
		int userNum = this.generateUserNum();
		
		return true;
		
	}

	public User getUserByUUID(String uuid) throws SQLException {
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql = "select * from users where user_num=" + uuid;
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		User user = new User();
		while (rs.next()) {			
			user.setId(rs.getInt(1));
			user.setUserNum(rs.getInt(2));
			user.setUserName(rs.getString(3));
			user.setUserFriends(rs.getString(4));
			user.setUserGroups(rs.getString(5));
			user.setDescription(rs.getString(6));			
		}
		pstmt.close();
		dbHelper.close();
		return user;
	}
}
