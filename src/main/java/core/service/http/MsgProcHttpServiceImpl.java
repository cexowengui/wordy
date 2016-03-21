package core.service.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

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

public class MsgProcHttpServiceImpl implements MsgProcHttpService {

	@Override
	public void procMessage(HttpServletResponse response, String msg) throws SQLException, IOException {

		MsgParseService msgParse = new MsgParseServiceImpl();
		RequestDetail requestDetail = msgParse.parseMessage(msg);
		switch (requestDetail.getAction()) {
		case MessageConstant.REGISTRY:
			this.Registry(requestDetail, response);
			break;
		case MessageConstant.LOGIN:
			this.Login(requestDetail, response);
			break;
		case MessageConstant.ADD_FRIEND:
			this.addFriend(requestDetail, response);
			break;
		case MessageConstant.CREATE_GROUP:
			this.createGroup(requestDetail, response);
			break;
		case MessageConstant.JOIN_GROUP:
			this.joinGroup(requestDetail, response);
			break;
		case MessageConstant.SEND_MESSAGE:
			this.sendMessage(requestDetail, response);
			break;
		case MessageConstant.HEART_BEAT:
			this.updateHeartBeat(requestDetail);
			break;
		default:
			break;
		}
	}

	public void Registry(RequestDetail requestDetail, HttpServletResponse response) throws SQLException, IOException {
		// 将重心移到dao层去写了，如果是正式开发,判断逻辑应该落在service层，dao层不应该有太多的业务代码
		try {
			Dao dao = new Dao();
			ResponseDetail res = dao.userRegistry(requestDetail);
			response.getWriter().println(res.toString());
		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}

	public void Login(RequestDetail requestDetail, HttpServletResponse response) throws SQLException, IOException {
		// 这个功能也没办法实现，原理同聊天一样，参考如下：
		// http协议是无状态的，请求一次就响应一次，服务端没法主动向http客户端发送消息，所以使用http协议不能聊天
		// 除非前端配合，在网页使用ajax技术产生一个跟socket一样的东西，定时和服务器保持联系，网页版QQ也必须这样做
		// 并且网页版的功能非常少，有些功能单纯在网页上实现是很困难的，这也就是通信类的软件基本上都要有客户端，而不能
		// 像网站一样用网页代替
		// 这里就不做这个功能了

	}

	public void addFriend(RequestDetail requestDetail, HttpServletResponse response) throws SQLException, IOException {
		// 将重心移到dao层去写了，如果是正式开发,判断逻辑应该落在service层，dao层不应该有太多的业务代码
		ResponseDetail responseDetail = new Dao().userAddFriend(requestDetail);
		response.getWriter().println(responseDetail.toString());
	}

	public void createGroup(RequestDetail requestDetail, HttpServletResponse response) {
		// return null;
	}

	public void joinGroup(RequestDetail requestDetail, HttpServletResponse response) {
		// return null;
	}

	public void updateHeartBeat(RequestDetail requestDetail) throws IOException {
		//因为没有登录功能，就没办法更新心跳了，所以采用http协议这个功能也没办法实现，http的局限性由此可见

	}

	public void sendMessage(RequestDetail requestDetail, HttpServletResponse response) throws IOException {
		// http协议是无状态的，请求一次就响应一次，服务端没法主动向http客户端发送消息，所以使用http协议不能聊天
		// 除非前端配合，在网页使用ajax技术产生一个跟socket一样的东西，定时和服务器保持联系，网页版QQ也必须这样做
		// 并且网页版的功能非常少，有些功能单纯在网页上实现是很困难的，这也就是通信类的软件基本上都要有客户端，而不能
		// 像网站一样用网页代替
		// 这里就不做这个功能了

	}

}
