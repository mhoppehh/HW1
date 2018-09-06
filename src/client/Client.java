package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Scanner;

public class Client {
	
	Socket socket;
	
	Scanner scanner;
	String name;
	int socketPort;
	
	boolean connected;
	
	public Client() throws UnknownHostException, IOException {
		init();
		
		run();
		
//		scanInput();
		
		scanner.close();
	}
	
	private void init() throws UnknownHostException, IOException {
		scanner = new Scanner(System.in);
		
		connected = false;
		
		initUser();
	}
	
	private void initUser() {
		System.out.print("[Enter your name]");
		Scanner scanner = new Scanner(System.in);
		name = scanner.nextLine();
	}
	
	private int getSocketPort() {
		System.out.print("[Enter the socket number]");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}
	
	private void run() throws IOException {
		while(true) {
			if(!checkConnection())
				establishConnection();
			sendMessage();
		}
	}
	
	private boolean checkConnection() {
		if(socket == null || !socket.isConnected() || socket.isClosed()) {
			if(connected)System.out.println("Connection Lost");
			return false;
		}
		return true;
	}
	
	private void establishConnection() {
		boolean connectSucc = false;
		
		while(!connectSucc) {
			socketPort = getSocketPort();
			connectSucc = connectSocket(socketPort);
		}
	}
	
	private void sendMessage(){
		try {
			System.out.print("[" + name + "]");
			Scanner scanner = new Scanner(System.in);
			String message = scanner.nextLine();
			
			OutputStream outputStream = socket.getOutputStream();
			String total = "[" + name + "]" + message;
	
			outputStream.write(total.getBytes());
		} catch (IOException e) {if(!checkConnection())return;}
	}
	
	private boolean connectSocket(int socketNum){
		try {
			socket = new Socket();
			
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName("localhost"), socketNum);
		
			socket.connect(socketAddress);
			if(socket != null) {
				System.out.println("Client Connection Successful \\(`-´)/");
				System.out.println(socket.toString());
				connected = true;
				return true;
			} else {
				System.out.println("Connection Timed Out");
				return false;
			}
		
		} catch (UnknownHostException e) {System.out.println("Connection Unsuccessful");return false;
		} catch (IOException e) {System.out.println("Connection Unsuccessful");return false;}
		
	}
	
	private void scanInput() throws IOException {
		while(true) {
			
			InputStream stream = socket.getInputStream();
			byte[] data = new byte[1];
			
			stream.read(data);
			
			if(data[0] != 0){
				System.out.println(data[0]);
			}
			
		}
	}
	
	
}
