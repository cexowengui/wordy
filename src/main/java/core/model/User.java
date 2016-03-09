package core.model;
/*
 * 按照数据库的表结构定义对象model
 */
public class User {
	private int id;
	private int userNum;
	private String userName;
	private String userPasswd;
	private String userFriends;
	private String userGroups;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserFriends() {
		return userFriends;
	}
	public void setUserFriends(String userFriends) {
		this.userFriends = userFriends;
	}
	public String getUserGroups() {
		return userGroups;
	}
	public void setUserGroups(String userGroups) {
		this.userGroups = userGroups;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String toString(){
		return String.valueOf(id) + " " + String.valueOf(userNum) 
		+  " " + userName + " " + userFriends + " " + userGroups+ " " + description;
	}
	public String getUserPasswd() {
		return userPasswd;
	}
	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
	}

}
