package server;

import java.net.*;

public class Server {
	
	ServerSocket ss;
	
	public Server() {
		init();
		
		openSockets();
	}

	private void init() {
		try {
			ss = new ServerSocket(8080, 0, InetAddress.getByName("localhost"));
		}catch(Exception e) {System.out.println(e.toString());}
	}
	
	private void openSockets() {
		try {
			Socket s = null;
			while(s == null) {
				s = ss.accept();
			}
			
			System.out.println("Server Connection Successful \\(`-´)/");
			System.out.println(s.toString());
			
			
			
			
		}catch(Exception e) {System.out.println(e.toString());}
		
		
	}
}
