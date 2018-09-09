
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		new Client();
	}

	int PORT 	= 8080;
	String loc 	= "localhost";
	
	Socket socket;
	Scanner scanner;
	String name;

	public Client() {
		if(init())
			run();
	}

	private boolean init() {
		try {
			
			socket = new Socket();
			
			SocketAddress socketAddress = new InetSocketAddress(InetAddress.getByName(loc), PORT);
		
			socket.connect(socketAddress);
			if(socket != null) {
				System.out.println(socket.toString());
				initUser();
				return true;
			}else return false;
		
		} catch (IOException e) {System.out.println("Connection Unsuccessful");return false;}
	}
	
	private void initUser() {
		System.out.print("[Enter your name]");
		scanner = new Scanner(System.in);
		name = scanner.nextLine();
	}
	
	private void run() {
		Thread t = new Thread(new ServerScanner(socket));
		t.start();
		
		while(true) {
			try {
				System.out.println("[" + name + "]");
				String message = scanner.nextLine();
				
				OutputStream outputStream = socket.getOutputStream();
				String total = "[" + name + "]" + message;
		
				outputStream.write(total.getBytes());
				
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	private class ServerScanner implements Runnable {

		Socket socket;

		public ServerScanner(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				while (true) {

					InputStream stream = socket.getInputStream();
					byte[] data = new byte[156];
				
					if(stream.available() > 0) {
						stream.read(data);
						System.out.println(new String(data));
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
