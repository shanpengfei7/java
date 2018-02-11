package com.pnfy.tankwar.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TankServer {
	
	public static final int TCP_PORT = 8888;
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(TCP_PORT);
			while (true) {
				Socket s = ss.accept();
System.out.print("dfjsdlfjs" + s.getInetAddress() + "fds" + s.getPort());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
