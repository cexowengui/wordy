package core.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import core.model.ClientSocket;

/*
 * 这个类的作用是作为全局变量，每一个socket连接到服务器上来，必须扔到这个hashmap里面来，
 * 因为客户端A发送了一个消息给客户端B，那么这个时候服务器必须在处理A请求的线程中把消息发送给B客户端，
 * 那么就必须拿到B客户端的连接，否则消息发送不出去。最大的问题就是如何把各个socket存储到这个hashmap，
 * 我们这里用QQ号作为key，整个ClientSocket作为value;
 * 客户端处理线程结束的时候，必须将这个全局变量对应的socket也删除掉，否则会误认为这个客户端还是在线
 */

public class SocketMap {

public static ConcurrentMap<Integer, ClientSocket> userNumMap = new ConcurrentHashMap<>();

}
