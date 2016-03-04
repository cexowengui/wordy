package core.dao;

import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import core.model.RequestDetail;
import core.model.ResponseDetail;
import core.model.User;
import core.util.DBHelper;

public class Dao {
	/*
	 * 产生用户的唯一号码，类似QQ号码，因为我们这里不会涉及到数据库分表，所以不用采用时间+随机数组成，
	      一般生产环境为了冷热数据区分，比如聊天记录，一般都是查找最近的，那么就可以根据记录的产生时间直接把
	      数据库的表分为多部分，老数据存储到廉价存储设备中去，最近的热数据存储到快速设备上。
	      我们这里直接从10000开始递增分配
	 */
	public int generateUserNum() throws SQLException, IOException{
		/*
		 * 为了节约时间，懒得封装sql这部分了，每个函数都有重复代码，封装这部分没啥技术含量
		 * 后续要有时间，dao层可以全部换成ibatis来管理，会简单很多，但是这都是企业做法，学生没必要
		 * 反而直接用sql语句把基础搞扎实点更有好处
		 */
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
	
	public ResponseDetail userRegistry(RequestDetail requestDetail) throws SQLException, IOException{
		String userName = requestDetail.getRegistryRequest().getUserName();		
		int userNum = this.generateUserNum();		
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql = "insert into users (user_num, user_name) values(?,?)";
		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
		pstmt.setInt(1, userNum);
		pstmt.setString(2, userName);
		pstmt.executeUpdate();	
		
		ResponseDetail res = new ResponseDetail();
		res.setResult("OK");
		res.setMsg(String.valueOf(userNum));
		return res;	
	}
	
	public ResponseDetail userAddFriend(RequestDetail requestDetail) throws SQLException, IOException{
		int userNum = requestDetail.getAddFriendRequest().getUserNum();
		int friendNum = requestDetail.getAddFriendRequest().getFriendNum();	
		User user = this.getUserByUUID(userNum);
		/*
		 *这里需要做一些校验，为了省事，我们不考虑非常规流程，比如添加不存在的用户作为好友等		 
		User user = this.getUserByUUID(userNum);//是否存在这个用户
		User friend = this.getUserByUUID(friendNum);//是否存在这个用户
		if(userNum==friendNum){}//自己不能添加自己为好友
		*/
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql;
		if (user.getUserFriends() == null) {
			//避免存进去一个多余的逗号，比如",123456,234567"
			sql = "update users set user_friends="  + friendNum + " where user_num=" 
			       + user.getUserNum();
		} else {
			sql = "update users set user_friends=" + user.getUserFriends() + "," 
			           + friendNum + " where user_num=" + user.getUserNum();
		}		
		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);		
		pstmt.executeLargeUpdate();
		
		ResponseDetail res = new ResponseDetail();
		res.setResult("OK");		
		return res;	
	}
	
	public ResponseDetail sendMessage(RequestDetail requestDetail, DataOutputStream output){
		return null;		
	} 
	

	public User getUserByUUID(int user_num) throws SQLException, IOException {
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql = "select * from users where user_num=" + user_num;
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
