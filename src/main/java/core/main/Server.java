package core.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

	ArrayList clientList = new ArrayList();

	public static void main(String[] args) {
		// 在main函数中，启动服务器的socket
		new Server().OpenServer();
	}

	public void OpenServer() {
		try {
			ServerSocket server = new ServerSocket(10001);
			Socket socket;
			while ((socket = server.accept()) != null) {
				System.out.println("接收到客户端连接");
				new ClientHandler(socket).start();				
			}

			if (!socket.isClosed()) {
				socket.close();
			}
			if (!server.isClosed()) {
				server.close();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {

		}
	}

	

}
