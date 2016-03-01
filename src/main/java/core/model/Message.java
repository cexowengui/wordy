package core.model;
/*
 * 消息对象
 */
public class Message {
	private String from;//消息发送者
	private String to;//消息接受者，可能是一个用户，也可能是一个群组
	private int type;//消息类型，1为单发消息  2为群发消息
	private String content;//消息内容
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
