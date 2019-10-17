package com.gw.webSocket.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClsMainClient {

	private static Logger log = LoggerFactory.getLogger(ClsMainClient.class);

	public static boolean lookup(String ip, int port, String ownercode, String userName, String useroc, byte slavecmd) {
		TcpClient c1 = new TcpClient() {

			@Override
			public void onReceive(SocketTransceiver st, String s) {
				log.info("Client1 Receive: " + s);
			}

			@Override
			public void onDisconnect(SocketTransceiver st) {
				log.info("Client1 Disconnect");
			}

			@Override
			public void onConnect(SocketTransceiver transceiver) {
				log.info("Client1 Connect success!");
			}

			@Override
			public void onConnectFailed() {
				log.info("Client1 Connect Failed");
			}
		};
		c1.connect(ip, port);

		delay();
		if (!c1.isConnected()) {
			return false;
		}
		String datacontent = String.format("{0};{1};{2}", 30, userName, ownercode);
		byte[] btContent = datacontent.getBytes();
		byte[] btOC = useroc.getBytes();
		int buflength = 15 + btOC.length + 2 + btContent.length + 2;
		byte[] buf = new byte[buflength];
		buf[0] = 0x7b;
		buf[1] = 0;
		buf[2] = 0;
		buf[3] = 0;
		buf[4] = 0;
		buf[5] = 1;
		buf[6] = (byte) 2;
		buf[7] = slavecmd;
		buf[8] = 0;
		buf[9] = 0;
		buf[10] = 0;
		buf[11] = 0;
		buf[12] = (byte) 1;
		buf[13] = 0;
		buf[14] = 0;

		for (int i = 0; i < 12; i++) {
			buf[15 + i] = btOC[i];
			buf[27] = (byte) ((btContent.length >> 8) & 0x0ff);
			buf[28] = (byte) ((btContent.length >> 0) & 0x0ff);
		}
		for (int i = 0; i < btContent.length; i++) {
			buf[29 + i] = btContent[i];
		}
		int sum = 0;
		for (int i = 1; i < buf.length - 2; i++) {
			sum += buf[i];
			buf[buf.length - 2] = (byte) (sum & 0x0ff);
			buf[buf.length - 1] = 0x7d;
		}

		long startTime = System.currentTimeMillis();
		while (true) {
			boolean send = c1.getTransceiver().send(buf, 0, buf.length);
			if (send) {
				c1.onDisconnect(c1.getTransceiver());
				return true;
			}
			if (System.currentTimeMillis() - startTime > 5000) {
				return false;
			}
		}
	}

	static void delay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		ClsMainClient.lookup("101.37.22.223", 5002,"059200000090","admin","888888888888",(byte)0x82);
	}
}
