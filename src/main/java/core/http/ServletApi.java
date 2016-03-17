package core.http;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/*
 * 定义几个http api：
 * 测试api：http://127.0.0.1:8080/message/hello1?name=xww&password=ubuntu
 * 
 * 注册用户：http://127.0.0.1:8080/message/userRegistry?name=xww&password=ubuntu
 * 用户登录：http://127.0.0.1:8080/message/login?userNum=123456&password=ubuntu
 * 用户添加好友：http://127.0.0.1:8080/message/addFriend?userNum=123456&friendNum=654321
 * 用户创建群组：http://127.0.0.1:8080/message/groupRegistry?userNum=123456&groupName=aaa
 * 用户加群：http://127.0.0.1:8080/message/addGroup?userNum=123456&groupNum=8123456
 * 用户给好友发送消息：http://127.0.0.1:8080/message/sendMessage?userNum=123456&friendNum=8123456&message=content
 * 心跳：http://127.0.0.1:8080/message/heartBeat?userNum=123456
 * 
 * 在提供一些接口：这些接口在socket那边是没有的，那边太麻烦了
 * 查询用户信息api：http://127.0.0.1:8080/message/user/123456  查询123456这个用户的信息
 * 查询群组信息api: http://127.0.0.1:8080/message/group/123456  查询123456这个组的信息 * 
 * 查询服务器当前socket列表SocketMap.userNumSocketMap是否保存了某用户的socket：
 * http://127.0.0.1:8080/message/socket/123456  查询123456这个用户是否在服务器所保存的socket中
 * 
 * 
 */

class TestServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");

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
		response.getWriter().println(studentJSONObject.toString());

	}
}
