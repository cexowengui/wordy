package core.http;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import core.dao.Dao;
import core.model.User;
import core.service.SocketMap;
import core.service.http.MsgProcHttpService;
import core.service.http.MsgProcHttpServiceImpl;
import core.service.socket.MsgProcSocketService;
import core.service.socket.MsgProcSocketServiceImpl;
import core.util.JsonUtil;

/*
 * 定义几个http api：
 * 测试api：http://127.0.0.1:8080/message/test?name=xww&password=ubuntu
 * 
 * 注册用户：http://127.0.0.1:8080/message/userRegistry?name=xww&password=ubuntu
 * 用户登录：http://127.0.0.1:8080/message/login?userNum=123456&password=ubuntu
 * 用户添加好友：http://127.0.0.1:8080/message/addFriend?userNum=123456&friendNum=654321
 * 用户创建群组：http://127.0.0.1:8080/message/groupRegistry?userNum=123456&groupName=aaa
 * 用户加群：http://127.0.0.1:8080/message/addGroup?userNum=123456&groupNum=8123456
 * 用户给好友发送消息：http://127.0.0.1:8080/message/sendMessage?userNum=123456&friendNum=8123456&message=content
 * 心跳：http://127.0.0.1:8080/message/heartBeat?userNum=123456
 * 
 * 再提供一些接口：这些接口在socket那边是没有的，那边太麻烦了
 * 查询用户信息api：http://127.0.0.1:8080/message/user?userNum=123456  查询123456这个用户的信息
 * 查询群组信息api: http://127.0.0.1:8080/message/group?groupNum=123456  查询123456这个组的信息 * 
 * 查询服务器当前socket列表SocketMap.userNumSocketMap是否保存了某用户的socket：
 * http://127.0.0.1:8080/message/socket?userNum=123456  查询123456这个用户是否在服务器所保存的socket中
 * 
 * 
 */

class UserRegistryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
		// 构造出字符串的请求message，因为MsgParseService接口接受的就是一个String字符串,
		// 具体的字符串格式请参考MessageConstant.java里面的注释
		String reqMsg = "0" + "+" + request.getParameter("name") + "+" + request.getParameter("password");
		MsgProcHttpService msgProcHttpService = new MsgProcHttpServiceImpl();
		try {
			msgProcHttpService.procMessage(res, reqMsg);// 使用core.service.http下面的处理接口
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

class UserLoginServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
		// 这个功能没办法实现，原理同聊天一样，参考如下：
		// 登录的时候必须要把客户端的信息保存到服务器的某个全局变量里面，但是这里是http请求，根本不可能保存客户端的什
		// 么信息，如果非要保存，那也只能保存cookie或者session，但是这两个玩意是无法发送接收消息的，
		// 也就是说http服务器无法主动向http客户端发送请求，所以http性质的服务职能是C/S架构，而不适合迅雷QQ等这样的项目

		// http协议是无状态的，请求一次就响应一次，服务端没法主动向http客户端发送消息，所以使用http协议不能聊天
		// 除非前端配合，在网页使用ajax技术产生一个跟socket一样的东西，定时和服务器保持联系，网页版QQ也必须这样做
		// 并且网页版的功能非常少，有些功能单纯在网页上实现是很困难的，这也就是通信类的软件基本上都要有客户端，而不能
		// 像网站一样用网页代替
		//

		// 这里就不做这个功能了
		res.getWriter().println("Not Implement!");

	}
}

class UserAddFriendServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);

		// 2+123456+654321 QQ号码为123456的用户申请添加654321为好友 回复：1+OK+或者1+FAIL+reason
		String reqMsg = "2" + "+" + request.getParameter("userNum") + "+" + request.getParameter("friendNum");
		MsgProcHttpService msgProcHttpService = new MsgProcHttpServiceImpl();
		try {
			msgProcHttpService.procMessage(res, reqMsg);// 使用core.service.http下面的处理接口
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

class UserCreateGroupServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);

	}
}

class UserJoinGroupServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);

	}
}

class UserSendMessageServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
		// 这个功能没办法实现，原理同聊天一样，参考如下：
		// 登录的时候必须要把客户端的信息保存到服务器的某个全局变量里面，但是这里是http请求，根本不可能保存客户端的什
		// 么信息，如果非要保存，那也只能保存cookie或者session，但是这两个玩意是无法发送接收消息的，
		// 也就是说http服务器无法主动向http客户端发送请求，所以http性质的服务职能是C/S架构，而不适合迅雷QQ等这样的项目

		// http协议是无状态的，请求一次就响应一次，服务端没法主动向http客户端发送消息，所以使用http协议不能聊天
		// 除非前端配合，在网页使用ajax技术产生一个跟socket一样的东西，定时和服务器保持联系，网页版QQ也必须这样做
		// 并且网页版的功能非常少，有些功能单纯在网页上实现是很困难的，这也就是通信类的软件基本上都要有客户端，而不能
		// 像网站一样用网页代替
		//

		// 这里就不做这个功能了
		res.getWriter().println("Not Implement!");

	}
}

class UserHeartBeatServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
		// 这个功能没办法实现，原理同聊天一样，参考如下：
		// 登录的时候必须要把客户端的信息保存到服务器的某个全局变量里面，但是这里是http请求，根本不可能保存客户端的什
		// 么信息，如果非要保存，那也只能保存cookie或者session，但是这两个玩意是无法发送接收消息的，
		// 也就是说http服务器无法主动向http客户端发送请求，所以http性质的服务职能是C/S架构，而不适合迅雷QQ等这样的项目

		// http协议是无状态的，请求一次就响应一次，服务端没法主动向http客户端发送消息，所以使用http协议不能聊天
		// 除非前端配合，在网页使用ajax技术产生一个跟socket一样的东西，定时和服务器保持联系，网页版QQ也必须这样做
		// 并且网页版的功能非常少，有些功能单纯在网页上实现是很困难的，这也就是通信类的软件基本上都要有客户端，而不能
		// 像网站一样用网页代替
		//

		// 这里就不做这个功能了
		res.getWriter().println("Not Implement!");

	}
}

class GetUserDetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
		int userNum = Integer.parseInt(request.getParameter("userNum"));
		try {
			User user = new Dao().getUserByUserNum(userNum);
			//这里有两种方法，一种是对象序列化然后远程传输，到那边再反序列化，
			//第二种是以json字符串的格式传输，到那边再把json转换成对象
			//这里使用第二种
			response.getWriter().print(JsonUtil.ObjectToJson(user));
			
		} catch (SQLException e) {			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class GetGroupDetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
	}
}

class GetUserSocketMapServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpServletResponse res = ResponseWrapper.getWrapResponse(response);
		int userNum = Integer.parseInt(request.getParameter("userNum"));
		if (SocketMap.userNumSocketMap.get(userNum) != null) {
			response.getWriter().println("OK");
		} else {
			response.getWriter().println("FAIL");
		}
	}
}

class TestServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String path = request.getRequestURI();
		path = request.getScheme();
		path = request.getQueryString();
		path = request.getServletPath();
		path = request.getServletPath();

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);

		// request.getSession(true).getId());//获取session

		JSONObject studentJSONObject = new JSONObject();
		studentJSONObject.put("name", "Jason");
		studentJSONObject.put("id", 20130001);
		studentJSONObject.put("phone", "13579246810");
		studentJSONObject.put("phone2", "13579246810");

		if (name != null) {
			studentJSONObject.put("名字", name);
		}

		if (password != null) {
			studentJSONObject.put("密码", password);
		}
		//response.getWriter().println(studentJSONObject.toString());
		User user = new User();
		user.setId(123);
		try {
			response.getWriter().print(JsonUtil.ObjectToJson(user));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

class ResponseWrapper {
	public static HttpServletResponse getWrapResponse(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_OK);
		return response;
	}
}
