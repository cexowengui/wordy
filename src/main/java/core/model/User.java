package core.model;
/*
 * 按照数据库的表结构定义对象model
 */
public class User {
	private int id;
	private String userNum;
	private String userName;
	private String userFriends;
	private String userGroups;
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
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

}
