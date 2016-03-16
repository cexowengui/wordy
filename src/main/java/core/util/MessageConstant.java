package core.util;
/*
 * 通讯协议定义：
 * 服务器接收到客户端的消息协议：
 * 0+user_name+passwd 申请注册用户名为user_name密码为passwd的QQ号码 回复：OK+1+123456或者FAIL+reason
 * 
 * 1+123456+passwd 用户123456用密码passwd登录
 * 
 * 2+123456+654321 QQ号码为123456的用户申请添加654321为好友 回复：1+OK+或者1+FAIL+reason
 * 
 * 3+123456+group_name 用户123456申请创建群组名为group_name的群 回复：OK+234567或者FAIL+reason
 * 
 * 4+123456+234567 QQ号码为123456的用户申请加入群234567 回复：OK或者FAIL+reason
 * 
 * 5+123456+654321+123+234 用户123456给用户号码或者群号码为654321发送内容为123+456的消息
 * 回复：这种不需要回复，否则太慢。 发送消息的协议中，第三部分的数字的意义可能表示用户号码，也可能表示群号，为了避免在
 * 消息中再添加别的信息进去，直接在业务开始之初就规定：1-8开头的为用户号码，9开头的为群号
 */
/*
 * 6+123456+1457602965193 用户保活心跳，避免服务器清理socket线程把自己清理掉，最后是发送该心跳的时间，自1970年1月1日0时起的毫秒数
 */

public class MessageConstant {
	public static final int REGISTRY = 0;//用户注册
	public static final int LOGIN = 1;//用户登录
	public static final int ADD_FRIEND = 2;//添加好友	
	public static final int CREATE_GROUP = 3;//创建群组
	public static final int JOIN_GROUP = 4;//加群
	public static final int SEND_MESSAGE = 5;//发送消息
	public static final int HEART_BEAT = 6;//心跳
	
	public static final int POINT_MESSAGE_TYPE = 1;//点对点消息
	public static final int GOURP_MESSAGE_TYPE = 2;//群发消息
	
	//参考ResponseDetail.java的解释
	public static final int RESPONSE_S2C = 1;//server to client回复消息
	public static final int RESPONSE_C2C = 2;//client to client回复消息
	
	
	
}
