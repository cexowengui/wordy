package core.model;

public class ResponseDetail {	
	//1为客户端和服务器的交互，2为客户端之间发送信息，客户端接收到好多消息，有的是服务器返回给他的，有的是其他用户发送给他的消息，需要区分
	private int response_type;
	private String result;//请求的结果，OK或者FAIL
	private String msg;//有些请求需要返回一些信息，比如注册需要返回注册好的QQ号码，另外客户端之间互发的消息也放在这里面
	
	public int getType() {
		return response_type;
	}
	public void setType(int response_type) {
		this.response_type = response_type;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String toString(){
		return String.valueOf(response_type) + "+" + result + "+" + msg;
	}
}
