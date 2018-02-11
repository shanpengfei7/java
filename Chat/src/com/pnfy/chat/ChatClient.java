package com.pnfy.chat;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatClient extends Frame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Socket socket = null;
	DataOutputStream dataOutputStream  = null;
	DataInputStream dataInputStream = null;
	private boolean bCounnected = false;
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	
	public static void main(String[] args) {
		new ChatClient().launchFrame();
	}
	
	
	public void launchFrame() {
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				disconnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TfListener());
		setVisible(true);
		
		connect();
		
		new Thread(new RecvThread()).start();
	}
	
		
	public void connect() {
		try {
			socket = new Socket("192.168.1.102", 8801);
			dataOutputStream = new DataOutputStream(socket.getOutputStream());
			dataInputStream = new DataInputStream(socket.getInputStream());
			bCounnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	public void disconnect() {
		try {
			dataOutputStream.close();
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	private class TfListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String string = tfTxt.getText().trim();
			//taContent.setText(string);
			tfTxt.setText("");
			
			try {
				//DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				dataOutputStream.writeUTF(string);
				dataOutputStream.flush();
				//dataOutputStream.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			
		}
				
	}
	
	
	private class RecvThread implements Runnable {

		public void run() {
			try {
				while(bCounnected) {					 
					String string = dataInputStream.readUTF();
					//System.out.print(string);
					taContent.setText(taContent.getText() + string + '\n');
				}
			}catch (SocketException e) {
				System.out.print("退出了，bye!");
			} catch (EOFException e) {
				System.out.print("退出了，bey--bye!");
			
			} catch (IOException e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	}
		
	
}



















