package core.service;

public interface NetService {
	public void recvMessageFromClient();//接收客户端消息
    public void sendMessageToClient(String message);//发送消息给客户端
}
