package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Server {
	
	int STARTING_PORT 	= 8080;
	int PORT_INCREMENT 	= 10; 
	InetAddress DEFAULT_ADDRESS = InetAddress.getByName("localhost");
	
	int numberConnected;
	
	ServerSocket nextSocket;
	
	ArrayList<Socket> openSockets;
	int nextPort;
	
	public Server() throws IOException {
		init();
		
		openSocket();
		
		run();
		
		close();
	}

	private void init() {
		nextPort = STARTING_PORT;
		
		openSockets = new ArrayList<Socket>();
	}
	
	private void run() throws IOException {
		while(true) {
			scanMessages();
		}
	}
	
	private void scanMessages() throws IOException {
		for(Socket s : openSockets) {
			InputStream stream = s.getInputStream();
			byte[] data = new byte[156];
		
			stream.read(data);
		
			if(sum(data) > 0){
				System.out.println(new String(data));
			}	
		}
	}
	
	private void openSocket() throws IOException {
			nextSocket = new ServerSocket(nextPort, 0, DEFAULT_ADDRESS);
		
			Socket s = null;
			
			s = nextSocket.accept();
			
			if(s != null) {
			
				System.out.println("Server Connection Successful \\(`-´)/");
				System.out.println(s.toString());
			
				openSockets.add(s);
				numberConnected++;
				nextPort += PORT_INCREMENT;
				nextSocket = null;
			}
			
	}
	
	private void close() throws IOException {
		nextSocket.close();
		
		for(int i = 0; i < openSockets.size();i++) {
			openSockets.get(i).close();
		}
	}
	
	private int sum(byte[] a) {
		int sum = 0;
		for(byte b : a) sum += b;
		return sum;
	}
}
