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
				clientList.add(socket);
				// clientName = socket.getInetAddress().toString();
				// output = new DataOutputStream(socket.getOutputStream());
				// input = new DataInputStream(socket.getInputStream());
				new readClient(socket).start();
				// new writeClient(socket, null).start();
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			try {

			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {

			} catch (Exception e2) {
				// TODO: handle exception
			}
			try {

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
	}

	public class readClient extends Thread {
		private Socket socket;

		public readClient(Socket client) {
			socket = client;
		}

		public void run() {
			String msg;
			try {
				String clientName = socket.getInetAddress().toString();
				// DataOutputStream output = new
				// DataOutputStream(socket.getOutputStream());
				DataInputStream input = new DataInputStream(socket.getInputStream());
				while ((msg = input.readUTF()) != null) {
					System.out.println("收到消息：【" + clientName + "】 " + msg);

				}
				socket.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}finally {
				try {

				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {

				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {

				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

	public class writeClient extends Thread {
		private Socket socket;
		private String responseMessage;

		public writeClient(Socket client, String respMessage) {
			socket = client;
			responseMessage = respMessage;
		}

		public void run() {
			try {

				String clientName = socket.getInetAddress().toString() + socket.getPort();
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				while (true) {
					output.writeUTF(responseMessage);
					output.flush();
					System.out.println("已发送消息给【" + clientName + "】" + responseMessage);
				}

			} catch (Exception e) {
				System.out.println(e.toString());
			}finally {
				try {

				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {

				} catch (Exception e2) {
					// TODO: handle exception
				}
				try {

				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
	}

}
