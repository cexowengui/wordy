package core.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBHelper {
	
	//用下面两个的任意一个url连接数据库，如果都不行，就把mysq的配置文件 /etc/mysql/my.cnf里面的bind_address改成0.0.0.0	
	//public static final String url = "jdbc:mysql://127.0.0.1/cloud_message";	
	//public static final String url = "jdbc:mysql://localhost/cloud_message";
	
	public static final String url = "jdbc:mysql://10.166.224.207/cloud_message";
	public static final String driver = "com.mysql.jdbc.Driver";
	public static final String user = "root";
	public static final String password = "ubuntu";

	public Connection conn = null;

	public Connection getConn() {
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
