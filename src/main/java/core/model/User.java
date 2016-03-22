package core.model;

import java.io.Serializable;

/*
 * 按照数据库的表结构定义对象model
 */
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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

	public String getUserPasswd() {
		return userPasswd;
	}

	public void setUserPasswd(String userPasswd) {
		this.userPasswd = userPasswd;
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

	public String toString() {
		return String.valueOf(id) + " " + String.valueOf(userNum) + " " + userName + " " + userFriends + " "
				+ userGroups + " " + description;
	}

	// 需要重写equals方法比较两个对象
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		final User u = (User) o;
		if (this.getUserName().equals(u.getUserName()) 
				&& this.getUserPasswd().equals(u.getUserPasswd())
				&& (this.getUserNum() == u.getUserNum())) {
			return true;
		} else {
			return false;
		}

	}

}
