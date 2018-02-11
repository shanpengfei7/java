package com.pnfy.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class ChatServer {
	boolean started = false;
	ServerSocket serverSocket = null;
	
	List<Client> clients = new ArrayList<Client>();
	
	
	public static void main(String[] args) {
		new ChatServer().start();
		
	}
	public void start() {
		/*boolean started = false;
		ServerSocket serverSocket = null;
		Socket socket =null;
		DataInputStream dataInputStream = null;*/
		
		try {
			serverSocket = new ServerSocket(8801);
			started = true;
		} catch (BindException e) {
			System.out.print("端口使用中……");
			System.out.print("请关闭程序并重新运行服务器！");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			
			while (started) {
				Socket socket = serverSocket.accept();
				Client client = new Client(socket);
				new Thread(client).start();
				//dataInputStream.close();
				clients.add(client);
			}
						
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					serverSocket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			}
		
		}
	}

	
	class Client implements Runnable {
		private Socket socket;
		private DataInputStream dataInputStream = null;
		private DataOutputStream dataOutputStream = null;
		private boolean bConnected = false;
		public Client (Socket socket) {
			this.socket = socket;
			try {
				dataInputStream = new DataInputStream(socket.getInputStream());
				dataOutputStream = new DataOutputStream(socket .getOutputStream());
				bConnected = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		public void send (String string) {
		
				try {
					dataOutputStream.writeUTF(string);
				} catch (IOException e) {
					clients.remove(this);
					System.out.print("对方退出了！");
				}
			
		}
			
		public void run() {
			Client client = null;
			
			try {
			while (bConnected)  {
					String string = dataInputStream.readUTF();
System.out.println(string);
					for(int i=0; i<clients.size(); i++) {
						client = clients.get(i);
						client.send(string);
					}
					
					/*
					for(Iterator <Client> iterator = clients.iterator(); iterator.hasNext();) {
						Client client = iterator.next();
						client.send(string);
					}*/
					
					/*Iterator<Client> iterator = clients.iterator();
					while(iterator.hasNext()) {
						Client client = iterator.next();
						client.send(string);
					}*/
				} 	
			} catch (SocketException e) {
				clients.remove(this);
			} catch (EOFException e) {
				System.out.print("Client closed");
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(dataInputStream != null) dataInputStream.close();
					if(dataOutputStream != null) dataOutputStream.close();
					if(socket != null) {
						socket.close();
						socket = null;
					}
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(client != null) clients.remove(client);
			}
		}
	}
}
