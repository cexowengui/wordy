package core.service.http;

import core.model.Message;
import core.model.RequestDetail;
import core.service.MsgParseService;
import core.util.MessageConstant;

public class MsgParseServiceHttpImpl implements MsgParseService {
	/*
	 * 通讯协议定义：详细参见 core.util.MessageConstant.java内解释
	 
	 */

	public RequestDetail parseMessage(String message) {
		String[] msgStrings = message.split("\\+");//因为+号在正则表达式中有特殊意义，此处需要转义
		switch (Integer.valueOf(msgStrings[0]).intValue()) {		
		case MessageConstant.REGISTRY:return parseUserRegistryReq(message);
		case MessageConstant.LOGIN:return parseUserLoginReq(message);
		case MessageConstant.ADD_FRIEND:return parseAddFriendReq(message);
		case MessageConstant.CREATE_GROUP:return parseCreateGroupReq(message);
		case MessageConstant.JOIN_GROUP:return parseJoinGroupReq(message);
		case MessageConstant.SEND_MESSAGE:return parseSendMessageReq(message);
		case MessageConstant.HEART_BEAT:return parseUPdateHeartbeatReq(message);
		default:return null;
		}		
	}
	public RequestDetail parseUserLoginReq(String message) {
		RequestDetail.LoginRequest loginRequest = new RequestDetail().new LoginRequest();
		String[] msgStrings = message.split("\\+");
		loginRequest.setUserNum(Integer.valueOf(msgStrings[1]).intValue());
		loginRequest.setPasswd(msgStrings[2]);
		
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setLoginRequest(loginRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
		
	}
	
	public RequestDetail parseUserRegistryReq(String message) {
		System.out.println("收到用户注册的请求，message is:" + message);
		RequestDetail.RegistryRequest registryRequest = 
				new RequestDetail().new RegistryRequest();	
		String[] msgStrings = message.split("\\+");
		registryRequest.setUserName(msgStrings[1]);
		registryRequest.setUserPasswd(msgStrings[2]);
		
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setRegistryRequest(registryRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
	}

	public RequestDetail parseAddFriendReq(String message) {
		RequestDetail.AddFriendRequest  addFriendRequest = 
				new RequestDetail().new AddFriendRequest();	
		String[] msgStrings = message.split("\\+");
		addFriendRequest.setUserNum(Integer.valueOf(msgStrings[1]).intValue());;
		addFriendRequest.setFriendNum(Integer.valueOf(msgStrings[2]).intValue());
		
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

	public RequestDetail parseJoinGroupReq(String message) {
		RequestDetail.AddGroupRequest addGroupRequest  = 
				new RequestDetail().new AddGroupRequest();
		String[] msgStrings = message.split("\\+");
		addGroupRequest.setUserNum(Integer.valueOf(msgStrings[1]).intValue());
		addGroupRequest.setGroupNum(Integer.valueOf(msgStrings[2]).intValue());
		
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setAddGroupRequest(addGroupRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;
	}
	
	public RequestDetail parseUPdateHeartbeatReq (String message) {
		RequestDetail.UpdateHeartBeatRequest updateHeartBeatRequest = 
				new RequestDetail().new UpdateHeartBeatRequest();
		String[] msgStrings = message.split("\\+");
		updateHeartBeatRequest.setUserNum(Integer.valueOf(msgStrings[1]).intValue());
		
		
		RequestDetail requestDetail = new RequestDetail();
		requestDetail.setUpdateHeartBeatRequest(updateHeartBeatRequest);
		requestDetail.setAction(Integer.valueOf(msgStrings[0]).intValue());
		return requestDetail;		
	}

	public RequestDetail parseSendMessageReq(String message) {
		RequestDetail.SendMessageRequest sendMessageRequest = 
				new RequestDetail().new SendMessageRequest();
		String[] msgStrings = message.split("\\+");
		
		Message msg = new Message();
		msg.setFrom(Integer.valueOf(msgStrings[1]).intValue());
		msg.setTo(Integer.valueOf(msgStrings[2]).intValue());
		if(msg.getTo() >= 90000){//群消息，因为群都是9开头的,90000为第一个群号
			msg.setType(MessageConstant.GOURP_MESSAGE_TYPE);
		}else{
			msg.setType(MessageConstant.POINT_MESSAGE_TYPE);
		}
		
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
