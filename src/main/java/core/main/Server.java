package core.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import core.model.ClientSocket;


/*
 * 2016-03-02 16:45
 * 目前还是有点问题，server.java启动，然后启动这个包里面的SocketClient.java，偶尔会出现
 * 客户端的console无法输入第二行数据，关闭重启就会好，暂时不知道什么问题
 */





public class Server {	

	public static void main(String[] args) {		
		new Server().OpenServer();
	}

	public void OpenServer() {
		try {
			ServerSocket server = new ServerSocket(10001);
			Socket socket;
			while ((socket = server.accept()) != null) {
				System.out.println("接收到客户端连接");
				ClientSocket clientSocket = new ClientSocket();
				clientSocket.setSocket(socket);
				clientSocket.setInput(new DataInputStream(socket.getInputStream()));
				clientSocket.setOutput(new DataOutputStream(socket.getOutputStream()));
				new ClientHandler(clientSocket).start();
				//new DataInputStream(socket.getInputStream());
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
