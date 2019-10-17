package com.gw.webSocket;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * websocket
 * 
 * @author Administrator
 *
 */
@ServerEndpoint(value = "/websocket/{userId}/{loginTime}")
@Component
public class AlarmWebSocket {

	private Logger log = LoggerFactory.getLogger(AlarmWebSocket.class);

	// 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	// concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
	// private static CopyOnWriteArraySet<AlarmWebSocket> webSocketSet = new
	// CopyOnWriteArraySet<AlarmWebSocket>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	public static Map<AlarmWebSocket, String> map = new ConcurrentHashMap<>();

	/**
	 * 连接建立成功调用的方法
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId, @PathParam("loginTime") String loginTime) {
		this.session = session;
		// webSocketSet.add(this); //加入set中
		map.put(this, userId + "," + loginTime);
		addOnlineCount(); // 在线数加1
		log.info("连接建立成功数为" + getOnlineCount() + "userId:" + userId);
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {
		// webSocketSet.remove(this); //从set中删除
		map.remove(this);
		subOnlineCount(); // 在线数减1
		log.info("有一连接关闭！" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 *
	 * @param message
	 *            客户端发送过来的消息
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		log.info("来自客户端的消息:" + message);
		// 群发消息
		// for (AlarmWebSocket item : webSocketSet) {
		// try {
		// item.sendMessage(message);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		log.info("发生错误");
		error.printStackTrace();
	}

	public synchronized void sendMessage(String message) throws IOException {
		this.session.getBasicRemote().sendText(message);
	}

	/**
	 * 群发自定义消息
	 */
	public static void sendInfo(String message) throws IOException {
		// for (AlarmWebSocket item : webSocketSet) {
		// try {
		// item.sendMessage(message);
		// } catch (IOException e) {
		// continue;
		// }
		// }
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		AlarmWebSocket.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		AlarmWebSocket.onlineCount--;
	}
}