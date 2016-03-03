package core.model;

/*
 *  
 * 
 */




 public class RequestDetail {
	 public class RegistryRequest {
			private String userName;//注册请求必须提供注册的名字，号码系统会自动分配

			public String getUserName() {
				return userName;
			}

			public void setUserName(String userName) {
				this.userName = userName;
			}
		}

	 public class AddFriendRequest {
			private String userNum;//用户号码
			private String friendNum;//想添加的好友号码

			public String getUserNum() {
				return userNum;
			}

			public void setUserNum(String userNum) {
				this.userNum = userNum;
			}

			public String getFriendNum() {
				return friendNum;
			}

			public void setFriendNum(String friendNum) {
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
			private String userNum;//用户号码
			private String groupNum;//群号

			public String getUserNum() {
				return userNum;
			}

			public void setUserNum(String userNum) {
				this.userNum = userNum;
			}

			public String getGroupNum() {
				return groupNum;
			}

			public void setGroupNum(String groupNum) {
				this.groupNum = groupNum;
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
	private AddFriendRequest addFriendRequest;
	private CreateGroupRequest createGroupRequest;
	private AddGroupRequest addGroupRequest;
	private SendMessageRequest sendMessageRequest;
	
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

}
