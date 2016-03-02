package core.service;

import core.util.MessageConstant;

public class MessageServiceImpl implements MessageService {
	/*
	 * 通讯协议定义： 1+user_name 申请注册用户名为user_name的QQ号码 回复：OK+123456或者FAIL+reason
	 * 
	 * 2+123456+654321 QQ号码为123456的用户申请添加654321为好友 回复：OK或者FAIL+reason
	 * 
	 * 3+group_name 申请创建群组名为group_name的群 回复：OK+234567或者FAIL+reason
	 * 
	 * 4+123456+234567 QQ号码为123456的用户申请加入群234567 回复：OK或者FAIL+reason
	 * 
	 * 5+123456+654321+123+234 用户123456给用户号码或者群号码为654321发送内容为123+456的消息
	 * 回复：这种不需要回复，否则太慢 发送消息的协议中，第三部分的数字的意义可能表示用户号码，也可能表示群号，为了避免在
	 * 消息中再添加别的信息进去，直接在业务开始之初就规定：1-8开头的为用户号码，9开头的为群号
	 */

	public void parseMessage(String message) {
		String[] messStrings = message.split("+");

		switch (Integer.valueOf(messStrings[0]).intValue()) {
		case MessageConstant.REGISTRY:
			break;
		case MessageConstant.ADD_FRIEND:

			break;
		case MessageConstant.CREATE_GROUP:

			break;
		case MessageConstant.ADD_GROUP:

			break;
		case MessageConstant.SEND_MESSAGE:

			break;

		default:
			break;
		}
	}

	public void procUserRegistryReq(String message) {

	}

	public void procAddFriendReq(String message) {

	}

	public void procCreateGroupReq(String message) {

	}

	public void procAddGroupReq(String message) {

	}

	public void procSendMessageReq(String message) {

	}

}
