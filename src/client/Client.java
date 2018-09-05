package client;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;

public class Client {
	public Client() {
		init();
	}
	
	private void init() {
		connectSocket();
	}
	
	private void connectSocket() {
		try {
			
				
			Socket s = new Socket(InetAddress.getByName("localhost"), 8080);
			
			
			while(!s.isConnected()) {
				s.connect(null);
			}
			
			System.out.println("Client Connection Successful \\(`-´)/");
			System.out.println(s.toString());
			
			
			while(true) {
				
				InputStream stream = s.getInputStream();
				byte[] data = new byte[1];
				
				stream.read(data);
				
//				if(data[0] != 0){
					System.out.println(data[0]);
//				}
				
			}
			
			
		} catch (Exception e) {System.out.println(e);}
	}
	
	
}
