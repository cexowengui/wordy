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
import core.util.MessageConstant;

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
		
		rs.next();
		int result = rs.getInt(1)+1;
		if (result < 10000) {
			result = 10000;//初始数据库没有数据，rs.getInt(1)结果是0
		}
		
		pstmt.close();
		dbHelper.close();
		return result;
	}
	
	public ResponseDetail userRegistry(RequestDetail requestDetail) throws SQLException, IOException{		
		int userNum = this.generateUserNum();		
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql = "insert into users (user_name, user_passwd, user_num) values(?,?,?)";
		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);
		pstmt.setString(1, requestDetail.getRegistryRequest().getUserName());
		pstmt.setString(2, requestDetail.getRegistryRequest().getUserPasswd());
		pstmt.setInt(3, userNum);		
		pstmt.executeUpdate();	
		pstmt.close();
		dbHelper.close();
		
		ResponseDetail res = new ResponseDetail();
		res.setResult("OK");
		res.setType(MessageConstant.RESPONSE_S2C);
		res.setMsg(String.valueOf(userNum));
		return res;	
	}
	
	private void addFriend(User user, User friend) throws IOException, SQLException{
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql;
		if (user.getUserFriends() == null) {
			//避免存进去一个多余的逗号，比如",123456,234567"
			sql = "update users set user_friends="  + friend.getUserNum() + " where user_num=" 
			       + user.getUserNum();
		} else {
			sql = "update users set user_friends=" + "'"+ user.getUserFriends() + "," 
			           + friend.getUserNum() + "'" + " where user_num=" + user.getUserNum();
		}		
		PreparedStatement pstmt = (PreparedStatement)conn.prepareStatement(sql);		
		pstmt.executeLargeUpdate();
		pstmt.close();
		dbHelper.close();
	}
	
	public ResponseDetail userAddFriend(RequestDetail requestDetail) throws SQLException, IOException{
		int userNum = requestDetail.getAddFriendRequest().getUserNum();
		int friendNum = requestDetail.getAddFriendRequest().getFriendNum();	
		User user = this.getUserByUserNum(userNum);
		User friend = this.getUserByUserNum(friendNum);
		/*
		 *这里需要做一些校验，为了省事，我们不考虑非常规流程，比如添加不存在的用户作为好友，好友拒绝接受申请，已经是好友了等
		 *另外这里是这样的：A添加B为好友，数据库中A的好友里面会有B的号码，同时我们也应该在B的还有里面添加A的号码		 
		User user = this.getUserByUUID(userNum);//是否存在这个用户
		User friend = this.getUserByUUID(friendNum);//是否存在这个用户
		if(userNum==friendNum){}//自己不能添加自己为好友
		*/
		addFriend(user, friend);
		addFriend(friend, user);
		
		ResponseDetail res = new ResponseDetail();
		res.setResult("OK");
		res.setType(MessageConstant.RESPONSE_S2C);
		return res;	
	}

	public User getUserByUserNum(int user_num) throws SQLException, IOException {
		DBHelper dbHelper = new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		String sql = "select * from users where user_num=" + user_num;
		PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		User user = new User();
		rs.next();
		//while (rs.next()) {	有且只会有一个，不需要循环，在外面执行rs.next()就行了		
			user.setId(rs.getInt(1));
			user.setUserName(rs.getString(2));
			user.setUserPasswd(rs.getString(3));
			user.setUserNum(rs.getInt(4));			
			user.setUserFriends(rs.getString(5));
			user.setUserGroups(rs.getString(6));
			user.setDescription(rs.getString(7));			
		//}
		pstmt.close();
		dbHelper.close();
		return user;
	}
}
