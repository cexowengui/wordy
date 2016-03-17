package core.http;

import javax.servlet.Servlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.example.jetty.FirstServlet;

public class JettyServer {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

		// 正确的访问方式为http://127.0.0.1:8080/hello1?name=xww&password=ubuntu
		// context.setContextPath("/");

		// 正确的访问方式为http://127.0.0.1:8080/message/hello1?name=xww&password=ubuntu
		context.setContextPath("/message");

		server.setHandler(context);
		TestServlet a = new TestServlet();
		context.addServlet(new ServletHolder(new TestServlet()), "/hello");

		server.start();
		server.join();
	}

}
