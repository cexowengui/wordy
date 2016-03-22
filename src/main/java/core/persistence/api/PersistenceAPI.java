package core.persistence.api;

import java.io.IOException;
import java.util.List;

import core.model.Message;

public interface PersistenceAPI {
	/*file类型的消息存储格式
	 * 点对点的消息保存在文件怕p2p_20160322.message中,每天一个文件，适合小规模
	 * 发送者QQ号      接受者QQ号    消息发送时间（毫秒表示）    消息内容
	 *  123456    234567    1458640706395                 您好啊
	 *  123457    234568    1558440706395                 您好啊
	 *  
	 *  点对群的消息保存在文件怕p2g_20160322.message中,每天一个文件，适合小规模
	 *  发送者QQ号      接受群号       消息发送时间（毫秒表示）    消息内容    已经接收该消息的群用户列表（所有用户都接收完或者达到最大时间才可以删除）
	 *  123456    234567    1458640706395                 您好啊      123456,234567
	 *  123457    234568    1558440706395                 您好啊      123456,234567
	 *  
	 */
	
	/*
	 *redis类型的消息存储格式 :
	 *
	 *点对点消息存储：
	 *以接收者的QQ号加年月日时间作为hash的key，内容为一个列表，里面存放的都是别人发给
	 *这个接收者的消息，格式是发送者的num加内容，这样的只要这个接收者一登录，立即将这个列表的内容全部发过去
	 *下面表示456789给123456发送了hello，234567给123456发送了hello2
	 *redisclient.hset("123456_20160322",["456789,hello","234567,hello2"])
	 *
	 *群消息：
	 *以群号作为key，方便查找，列表中的内容是发送者加消息内容
	 *下面表示用户456789向群号为823456发送了hello消息，用户234567向群发送了hello2消息
	 *redisclient.hset("823456_20160322",["456789,hello","234567,hello2"])
	 *
	 */
	
	
	//对方用户不在西安，将消息保存起来
	public void saveMessage(Message message);
	
	//用户上线，首先获取它不在线的时候别人发送给他的消息
	public List<Message> getMessage(int userNum);
	
	//用户上线后，服务器将保存的消息发送给他，然后删除
	public void deleteMessage(int userNum);
}
