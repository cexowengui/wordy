package core.model;

/*
 *  
 * 
 */

 public class RequestDetail {
	 public class RegistryRequest {
			private String userName;//注册请求必须提供注册的名字和密码，号码系统会自动分配
			private String userPasswd;

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
		}
	 
	 public class LoginRequest{
		 private int userNum;
		 private String passwd;
		public int getUserNum() {
			return userNum;
		}
		public void setUserNum(int userNum) {
			this.userNum = userNum;
		}
		public String getPasswd() {
			return passwd;
		}
		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}
		 
	 }

	 public class AddFriendRequest {
			private int userNum;//用户号码
			private int friendNum;//想添加的好友号码
			public int getUserNum() {
				return userNum;
			}
			public void setUserNum(int userNum) {
				this.userNum = userNum;
			}
			public int getFriendNum() {
				return friendNum;
			}
			public void setFriendNum(int friendNum) {
				this.friendNum = friendNum;
			}

			
		}

	 public class CreateGroupRequest {
			private String groupName;//想要创建的组名

			public String getGroupName() {
				return groupName;
			}

			public void setGroupName(String groupName) {
				this.groupName = groupName;
			}
		}

	 public class AddGroupRequest {
			private int userNum;//用户号码
			private int groupNum;//群号
			public int getUserNum() {
				return userNum;
			}
			public void setUserNum(int userNum) {
				this.userNum = userNum;
			}
			public int getGroupNum() {
				return groupNum;
			}
			public void setGroupNum(int groupNum) {
				this.groupNum = groupNum;
			}

			
		}
	 
	 public class UpdateHeartBeatRequest{
		 private int userNum;
		 private long updateTime;
		 
		public int getUserNum() {
			return userNum;
		}
		public void setUserNum(int userNum) {
			this.userNum = userNum;
		}
		public long getUpdateTime() {
			return updateTime;
		}
		public void setUpdateTime(long updateTime) {
			this.updateTime = updateTime;
		}
		 
	 }

	 public class SendMessageRequest {
			
			private Message message;//消息体
			public Message getMessage() {
				return message;
			}

			public void setMessage(Message message) {
				this.message = message;
			}

		}
		
	private int action;
	private RegistryRequest registryRequest;
	private LoginRequest loginRequest;
	private AddFriendRequest addFriendRequest;
	private CreateGroupRequest createGroupRequest;
	private AddGroupRequest addGroupRequest;
	private SendMessageRequest sendMessageRequest;
	private UpdateHeartBeatRequest updateHeartBeatRequest;
	
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public RegistryRequest getRegistryRequest() {
		return registryRequest;
	}
	public void setRegistryRequest(RegistryRequest registryRequest) {
		this.registryRequest = registryRequest;
	}
	public AddFriendRequest getAddFriendRequest() {
		return addFriendRequest;
	}
	public void setAddFriendRequest(AddFriendRequest addFriendRequest) {
		this.addFriendRequest = addFriendRequest;
	}
	public CreateGroupRequest getCreateGroupRequest() {
		return createGroupRequest;
	}
	public void setCreateGroupRequest(CreateGroupRequest createGroupRequest) {
		this.createGroupRequest = createGroupRequest;
	}
	public AddGroupRequest getAddGroupRequest() {
		return addGroupRequest;
	}
	public void setAddGroupRequest(AddGroupRequest addGroupRequest) {
		this.addGroupRequest = addGroupRequest;
	}
	public SendMessageRequest getSendMessageRequest() {
		return sendMessageRequest;
	}
	public void setSendMessageRequest(SendMessageRequest sendMessageRequest) {
		this.sendMessageRequest = sendMessageRequest;
	}
	public LoginRequest getLoginRequest() {
		return loginRequest;
	}
	public void setLoginRequest(LoginRequest loginRequest) {
		this.loginRequest = loginRequest;
	}
	public UpdateHeartBeatRequest getUpdateHeartBeatRequest() {
		return updateHeartBeatRequest;
	}
	public void setUpdateHeartBeatRequest(UpdateHeartBeatRequest updateHeartBeatRequest) {
		this.updateHeartBeatRequest = updateHeartBeatRequest;
	}

}
