package core.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;



public class ClientHandler extends Thread {

	private Socket clientSocket;

	public ClientHandler(Socket client) {
		this.clientSocket = client;
	}

	public void run() {
			String msg;
			DataInputStream input = null;
			
			try {
				String clientName = clientSocket.getInetAddress().toString();
				// DataOutputStream output = new
				// DataOutputStream(socket.getOutputStream());
				input = new DataInputStream(clientSocket.getInputStream());
				while ((msg = input.readUTF()) != null) {
					System.out.println("收到消息：【" + clientName + "】 " + msg);
				}				
			} catch (Exception e) {
				System.out.println(e.toString());
			} finally {
				System.out.println("客户端断开，退出");
				try {
					this.clientSocket.close();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		}
}

class ReplyClient extends Thread {
	private Socket socket;
	private String responseMessage;

	public ReplyClient(Socket client, String respMessage) {
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
}
