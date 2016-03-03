package core.service;

import core.model.Message;
import core.model.RequestDetail;
import core.util.MessageConstant;

public class MsgParseServiceImpl implements MsgParseService {
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

	public RequestDetail parseMessage(String message) {
		String[] msgStrings = message.split("\\+");//因为+号在正则表达式中有特殊意义，此处需要转义
		switch (Integer.valueOf(msgStrings[0]).intValue()) {
		case MessageConstant.REGISTRY:return parseUserRegistryReq(message);
		case MessageConstant.ADD_FRIEND:return parseAddFriendReq(message);
		case MessageConstant.CREATE_GROUP:return parseCreateGroupReq(message);
		case MessageConstant.ADD_GROUP:return parseAddGroupReq(message);
		case MessageConstant.SEND_MESSAGE:return parseSendMessageReq(message);
		default:return null;
		}		
	}

	public RequestDetail parseUserRegistryReq(String message) {
		RequestDetail.RegistryRequest registryRequest = 
				new RequestDetail().new RegistryRequest();	
		String[] msgStrings = message.split("\\+");
		registryRequest.setUserName(msgStrings[1]);
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setRegistryRequest(registryRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
	}

	public RequestDetail parseAddFriendReq(String message) {
		RequestDetail.AddFriendRequest  addFriendRequest = 
				new RequestDetail().new AddFriendRequest();	
		String[] msgStrings = message.split("\\+");
		addFriendRequest.setUserNum(msgStrings[1]);;
		addFriendRequest.setFriendNum(msgStrings[2]);
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setAddFriendRequest(addFriendRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
	}

	public RequestDetail parseCreateGroupReq(String message) {
		RequestDetail.CreateGroupRequest createGroupRequest = 
				new RequestDetail().new CreateGroupRequest();
		String[] msgStrings = message.split("\\+");
		createGroupRequest.setGroupName(msgStrings[1]);
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setCreateGroupRequest(createGroupRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
	}

	public RequestDetail parseAddGroupReq(String message) {
		RequestDetail.AddGroupRequest addGroupRequest  = 
				new RequestDetail().new AddGroupRequest();
		String[] msgStrings = message.split("\\+");
		addGroupRequest.setUserNum(msgStrings[1]);
		addGroupRequest.setGroupNum(msgStrings[2]);
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setAddGroupRequest(addGroupRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
	}

	public RequestDetail parseSendMessageReq(String message) {
		RequestDetail.SendMessageRequest sendMessageRequest = 
				new RequestDetail().new SendMessageRequest();
		String[] msgStrings = message.split("\\+");
		
		Message msg = new Message();
		msg.setFrom(msgStrings[1]);
		msg.setTo(msgStrings[2]);
		
		String content = "";//从第三部分开始全部作为消息内容看待，不管内容中是否有+号
		for(int i=3;i < msgStrings.length;i++){
			content = content + msgStrings[i];
		}
		msg.setContent(content);
		sendMessageRequest.setMessage(msg);
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setSendMessageRequest(sendMessageRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;

	}

}
