package lab;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	public static void main(String[] args) {
		new Server();
	}

	int PORT = 8080;
	String loc = "localhost";
	ServerSocket serversocket;
	int numusers;
	
	ArrayList<ClientTuple> clientList;

	public Server() {
		init();
		run();
	}

	private void init() {
		clientList = new ArrayList<ClientTuple>();
		serversocket = null;
		numusers = 0;

		try {
			serversocket = new ServerSocket(PORT);
			System.out.println(serversocket);
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + PORT);
			System.exit(-1);
		}
	}

	private void run() {
		while (true) {

			Socket socket = null;
			try {
				socket = serversocket.accept();
				
				clientList.add(new ClientTuple(socket, ++numusers));

				Thread t = new Thread(new ClientScanner(this, socket, numusers));
				t.start();

			} catch (IOException e) {
				System.exit(-1);
			}

		}
	}
	
	public void close() {
		System.exit(-1);
	}

	private class ClientScanner implements Runnable {

		Server server;
		Socket socket;
		int id;

		public ClientScanner(Server server, Socket socket, int id) {
			this.server = server;
			this.socket = socket;
			this.id = id;
		}

		@Override
		public void run() {

			while (true) {
				try {

					InputStream stream = socket.getInputStream();
					byte[] data = new byte[156];

					if (stream.available() > 0) {
						stream.read(data);
						server.blast(new String(data), id);
						System.out.println(new String(data));
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void blast(String string, int id) {
		if(string.contains("]~close"))
			close();
		
		for(int i = 0; i < clientList.size();i++) {
			if( !clientList.get(i).checkId(id) ) {
				clientList.get(i).sendString(string);
			}
		}
	}
	
	private class ClientTuple{
		Socket socket;
		int id;
		
		public ClientTuple(Socket socket, int id) {
			this.socket = socket;
			this.id = id;
		}
		
		public int getID() {
			return id;
		}
		
		public boolean checkId(int id) {
			return this.id == id;
		}
		
		public void sendString(String s) {
			try {
				OutputStream outputStream = socket.getOutputStream();
				outputStream.write(s.getBytes());
			} catch (IOException e) {e.printStackTrace();}
		}
	}
}
