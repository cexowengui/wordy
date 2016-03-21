package core.service.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import org.hamcrest.core.Is;

import core.dao.Dao;
import core.model.ClientSocket;
import core.model.RequestDetail;
import core.model.RequestDetail.SendMessageRequest;
import core.service.MsgParseService;
import core.service.MsgParseServiceImpl;
import core.service.SocketMap;
import core.model.ResponseDetail;
import core.model.User;
import core.util.MessageConstant;

public class MsgProcSocketServiceImpl implements MsgProcSocketService {

	@Override
	public void procMessage(ClientSocket clientSocket, String msg) throws SQLException, IOException {
		
		MsgParseService msgParse = new MsgParseServiceImpl();
		RequestDetail requestDetail = msgParse.parseMessage(msg);		
		switch (requestDetail.getAction()) {
		case MessageConstant.REGISTRY:this.Registry(requestDetail, clientSocket);break;
		case MessageConstant.LOGIN:this.Login(requestDetail, clientSocket);break;
		case MessageConstant.ADD_FRIEND:this.addFriend(requestDetail, clientSocket);break;
		case MessageConstant.CREATE_GROUP:this.createGroup(requestDetail, clientSocket);break;
		case MessageConstant.JOIN_GROUP:this.joinGroup(requestDetail, clientSocket);break;
		case MessageConstant.SEND_MESSAGE:this.sendMessage(requestDetail);break;
		case MessageConstant.HEART_BEAT:this.updateHeartBeat(requestDetail);break;
		default: break;
					
		}
	}
	
	public void Registry(RequestDetail requestDetail, ClientSocket clientSocket) throws SQLException, IOException{	
		//将重心移到dao层去写了，如果是正式开发,判断逻辑应该落在service层，dao层不应该有太多的业务代码
		try {
			Dao dao = new Dao();		
			ResponseDetail res = dao.userRegistry(requestDetail);		
			clientSocket.getOutput().writeUTF(res.toString());		
			clientSocket.getOutput().flush();			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
	}
	
	public void Login(RequestDetail requestDetail, ClientSocket clientSocket) throws SQLException, IOException{
		ResponseDetail res = new ResponseDetail();
		RequestDetail.LoginRequest loginRequest = requestDetail.getLoginRequest();
		res.setResult("OK");
		res.setType(MessageConstant.RESPONSE_S2C);
		Dao dao = new Dao();
		User user = dao.getUserByUserNum(loginRequest.getUserNum());
		if(user == null){
			res.setResult("FAIL");
			res.setMsg("用户不存在");
			
		}
		if(!user.getUserPasswd().equals(loginRequest.getPasswd())){
			res.setResult("FAIL");
			String reason = user.getUserName() + "的密码不正确。请求的密码是:"+loginRequest.getPasswd() + 
					";数据库的密码是:"+user.getUserPasswd();
			res.setMsg(reason);			
		}
		
		//将登陆成功的socket存放入全局的静态变量中，后续其它线程会用的，必须放在一个所有线程都可以取到的地方；
		//客户端A必须执行这个方法才会把连接放入全局变量中，也只有这样才可以接受他人消息，否则B给A发送消息，无法
		//从该全局变量中找到对应的socket，就无法把消息发送给A，如果后续增加了消息持久化功能后，那么这个消息会
		//被持久化保存起来，直到A下一次上线后才会推送过去。
		SocketMap.userNumSocketMap.put(requestDetail.getLoginRequest().getUserNum(), clientSocket);	
		
		clientSocket.getOutput().writeUTF(res.toString());
		clientSocket.getOutput().flush();			
	}
	
	public void addFriend(RequestDetail requestDetail, ClientSocket clientSocket) throws SQLException, IOException{
		//将重心移到dao层去写了，如果是正式开发,判断逻辑应该落在service层，dao层不应该有太多的业务代码
		ResponseDetail responseDetail = new Dao().userAddFriend(requestDetail);
		clientSocket.getOutput().writeUTF(responseDetail.toString());
		clientSocket.getOutput().flush();
	}
	
	public void createGroup(RequestDetail requestDetail, ClientSocket clientSocket){	
		//return null;		
	}
	
	public void joinGroup(RequestDetail requestDetail, ClientSocket clientSocket){	
		//return null;		
	}
	
	public void updateHeartBeat (RequestDetail requestDetail) throws IOException{
		ClientSocket clientSocket = SocketMap.userNumSocketMap.get(
				requestDetail.getUpdateHeartBeatRequest().getUserNum());
		//不用客户端请求发过来的时间数据，直接获取服务器本地的时间
		clientSocket.setUpdateTime(System.currentTimeMillis());
		SocketMap.userNumSocketMap.put(requestDetail.getUpdateHeartBeatRequest().getUserNum(), 
				clientSocket);
		
		//不需要回复消息给客户端		
		
	}
	
	public void sendMessage(RequestDetail requestDetail) throws IOException{	
		SendMessageRequest sendMsgReq = requestDetail.getSendMessageRequest();
		ClientSocket recvClient = SocketMap.userNumSocketMap.get(sendMsgReq.getMessage().getTo());
		if(recvClient == null){
			//查找不到接收方的socket，则认为接收方没连接上来（其实有可能已经连接到了服务器，但是没有login，因为把socket存储到
			//全局变量中是在Login方法中做的），那么就需要将这个消息持久化
			/*
			 * 持久化的代码逻辑
			 */
		}else{
			//直接发送给对方
			ResponseDetail responseDetail = new ResponseDetail();
			responseDetail.setType(MessageConstant.RESPONSE_C2C);
			responseDetail.setResult("OK");//这个在客户端那边没有用
			responseDetail.setMsg(requestDetail.getSendMessageRequest().getMessage().getContent());
			recvClient.getOutput().writeUTF(responseDetail.toString());
			recvClient.getOutput().flush();
		}
		
				
	} 


}
