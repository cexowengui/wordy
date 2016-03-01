package core.model;

/*
 * 因为请求种类不多，所以都写在一起，这个类包含了所有请求解析出来的信息，比如添加好友，注册，发送信息都在这里面
 * 都用内部类，有点麻烦，后期重构可以找更合适的方法
 * 
 */

public class RequestDetail {
	private int action;//所有请求都必须包含这个

	class RegistryRequest {
		private String userName;//注册请求必须提供注册的名字，号码系统会自动分配

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
	}

	class AddFriendRequest {
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

	class CreateGroupRequest {
		private String groupName;//想要创建的组名

		public String getGroupName() {
			return groupName;
		}

		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
	}

	class AddGroupRequest {
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

	class SendMessageRequest {
		
		private Message message;//消息体
		public Message getMessage() {
			return message;
		}

		public void setMessage(Message message) {
			this.message = message;
		}

	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

}
