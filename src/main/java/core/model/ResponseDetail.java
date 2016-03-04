package core.model;

public class ResponseDetail {
	
	private String result;//请求的结果，OK或者FAIL
	private String msg;//有些请求需要返回一些信息，比如注册需要返回注册好的QQ号码
	
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
}
