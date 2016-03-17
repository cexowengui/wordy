package core.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import core.model.ClientSocket;
import core.util.ConfigRead;


/*
 * 2016-03-02 16:45
 * 目前还是有点问题，server.java启动，然后启动这个包里面的SocketClient.java，偶尔会出现
 * 客户端的console无法输入第二行数据，关闭重启就会好，暂时不知道什么问题
 */

public class Server {	

	public static void main(String[] args) {		
		new Server().OpenServer();
		
		/*启动定时任务线程清理断开的客户端连接，虽然客户端退出会在ClientHandler的finally里面把socket关闭，但是全局变量
		 * SocketMap里面还保留了client的socket，必须清理掉对于三分钟以上没有心跳更新的socket都清除掉
		*/
		new Thread( new CleanInactivateClientThread()).start();
	}
	

	public void OpenServer() {
		try {
			ServerSocket server = new ServerSocket(Integer.valueOf(ConfigRead.getConfigProperties("server_port")).intValue());
			Socket socket;
			while ((socket = server.accept()) != null) {
				System.out.println("接收到客户端连接");
				ClientSocket clientSocket = new ClientSocket();
				clientSocket.setSocket(socket);
				clientSocket.setInput(new DataInputStream(socket.getInputStream()));
				clientSocket.setOutput(new DataOutputStream(socket.getOutputStream()));
				clientSocket.setUpdateTime(System.currentTimeMillis());
				new ClientHandler(clientSocket).start();
				//new DataInputStream(socket.getInputStream());
			}
			
			System.out.println("服务端退出");
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
