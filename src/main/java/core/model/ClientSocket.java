package core.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class ClientSocket{
	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public DataInputStream getInput() {
		return input;
	}
	public void setInput(DataInputStream input) {
		this.input = input;
	}
	public DataOutputStream getOutput() {
		return output;
	}
	public void setOutput(DataOutputStream output) {
		this.output = output;
	}
	
	//input = new DataInputStream(clientSocket.getInputStream());
	//output = new DataOutputStream(clientSocket.getOutputStream());
}
