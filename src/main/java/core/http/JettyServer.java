package core.http;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.jetty.FirstServlet;

import core.util.ConfigRead;

public class JettyServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(Integer.parseInt(ConfigRead.getConfigProperties("jetty_port")));

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		// 正确的访问方式为http://127.0.0.1:8080/hello1?name=xww&password=ubuntu
		// context.setContextPath("/");

		// 正确的访问方式为http://127.0.0.1:8080/message/hello1?name=xww&password=ubuntu
		context.setContextPath("/message");

		server.setHandler(context);
		
		context.addServlet(new ServletHolder(new TestServlet()), "/test");		
		context.addServlet(new ServletHolder(new UserRegistryServlet()), "/userRegistry");		
		context.addServlet(new ServletHolder(new UserLoginServlet()), "/login");
		context.addServlet(new ServletHolder(new UserAddFriendServlet()), "/addFriend");
		context.addServlet(new ServletHolder(new UserCreateGroupServlet()), "/groupRegistry");
		context.addServlet(new ServletHolder(new UserJoinGroupServlet()), "/addGroup");
		context.addServlet(new ServletHolder(new UserSendMessageServlet()), "/sendMessage");
		context.addServlet(new ServletHolder(new UserHeartBeatServlet()), "/heartBeat");
		
		context.addServlet(new ServletHolder(new GetUserDetailServlet()), "/user");
		context.addServlet(new ServletHolder(new GetGroupDetailServlet()), "/group");
		context.addServlet(new ServletHolder(new GetUserSocketMapServlet()), "/socket");
		

		server.start();
		server.join();
	}

}
