package core.util;




import java.awt.image.DataBufferDouble;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import core.model.User;

public class DBTest {

	static String sql = null;
	static DBHelper db1 = null;
	static ResultSet ret = null;

	public static void main(String[] args) {		
		DBHelper dbHelper =  new DBHelper();
		Connection conn = (Connection) dbHelper.getConn();
		
		try {
			PreparedStatement pstmt;
			
			//插入
			sql = "insert into users (user_num, user_name,user_friends,user_groups,description) values(?,?,?,?,?)";
			pstmt = (PreparedStatement) conn.prepareStatement(sql);			
			pstmt.setInt(1, 125);
			pstmt.setString(2, "xww");
			pstmt.setString(3, "12345,23456");
			pstmt.setString(4, "123,345");
			pstmt.setString(5, "desc");
			int i = pstmt.executeUpdate();
			System.out.println(i);
			
			//查询
			String sql = "select * from users where user_num=" + 123;
			PreparedStatement pstmt2;
			pstmt = (PreparedStatement) conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();			
			int colnums = rs.getMetaData().getColumnCount();
			User user = new User();
			while (rs.next()) {
				// for (int i = 1; i <= colnums; i++) {
				user.setId(rs.getInt(1));
				user.setUserNum(rs.getInt(2));
				user.setUserName(rs.getString(3));
				user.setUserFriends(rs.getString(4));
				user.setUserGroups(rs.getString(5));
				user.setDescription(rs.getString(6));
				System.out.println(user.toString());
				// }
			}
			
			//查询2
			String sql2 = "select max(user_num) from users";
			pstmt = (PreparedStatement) conn.prepareStatement(sql2);
			ResultSet rs2 = pstmt.executeQuery();
			//rs2.last();
			//System.out.println(rs2.getRow());
			rs2.next();
			System.out.println("max user_num is:" + rs2.getInt(1));
			
			
			pstmt.close();
			dbHelper.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

/*
 * private static Integer select() {
    Connection conn = getConn();
    String sql = "select * from students";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement)conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();
        int col = rs.getMetaData().getColumnCount();
        System.out.println("============================");
        while (rs.next()) {
            for (int i = 1; i <= col; i++) {
                System.out.print(rs.getString(i) + "\t");
                if ((i == 2) && (rs.getString(i).length() < 8)) {
                    System.out.print("\t");
                }
             }
            System.out.println("");
        }
            System.out.println("============================");
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
  private static int update(Student student) {
    Connection conn = getConn();
    int i = 0;
    String sql = "update students set Age='" + student.getAge() + "' where Name='" + student.getName() + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
        System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}
private static int delete(String name) {
    Connection conn = getConn();
    int i = 0;
    String sql = "delete from students where Name='" + name + "'";
    PreparedStatement pstmt;
    try {
        pstmt = (PreparedStatement) conn.prepareStatement(sql);
        i = pstmt.executeUpdate();
        System.out.println("resutl: " + i);
        pstmt.close();
        conn.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return i;
}*/

