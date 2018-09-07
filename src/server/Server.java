package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Server {
	
	int STARTING_PORT 	= 10;
	int PORT_INCREMENT 	= 10; 
	InetAddress DEFAULT_ADDRESS = InetAddress.getByName("localhost");
	
	int numberConnected;
	
	ServerSocket nextSocket;
	
	ArrayList<Socket> openSockets;
	int nextPort;
	
	public Server() throws IOException, InterruptedException {
		init();
		
		run();
		
		close();
	}

	private void init() {
		nextPort = STARTING_PORT;
		
		openSockets = new ArrayList<Socket>();
	}
	
	private void run() throws IOException, InterruptedException {
		while(true) {
			openSocket();
			
			scanMessages();
		}
	}
	
	private void scanMessages() throws IOException {
		for(Socket s : openSockets) {
			InputStream stream = s.getInputStream();
			byte[] data = new byte[156];
		
			if(stream.available() > 0) {
				stream.read(data);
				System.out.println(new String(data));
			}
		}
	}
	
	private void openSocket() throws InterruptedException{
		Socket s = null;
		try {
		
			nextSocket = new ServerSocket(nextPort, 0, DEFAULT_ADDRESS);
			nextSocket.setSoTimeout(50); 
			
			s = nextSocket.accept();
			
			socketConnected(s);
			} catch (java.net.BindException b) {
				nextPort += PORT_INCREMENT;
			} catch (SocketException se) {
			} catch (IOException e) {
				if(s != null)
					socketConnected(s);
				Thread.sleep(50);
			}
		
			
	}
	
	private void socketConnected(Socket s) {
		System.out.println("Server Connection Successful \\(`-´)/");
		System.out.println(s.toString());
		
		openSockets.add(s);
		numberConnected++;
		nextPort += PORT_INCREMENT;
		nextSocket = null;
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
