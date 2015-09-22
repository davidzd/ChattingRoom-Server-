package unimelb.daz1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Created by davidzd on 15/9/20.
 */
public class Server extends Thread {
	private Integer port;
	public static Set<Room> roomlist = new HashSet<Room>();

	public Server(Integer port) {
		// TODO Auto-generated constructor stub
		this.port = port;
		roomlist.add(Room.MainHall);

	}

	@Override
	public void run() {
		while (!isInterrupted()) {
			Server server = new Server(port);
			try {
				server.startServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void startServer() throws IOException {
		ServerSocket serverSocket = null;
		try {
			// unimelb.daz1.Server is listening on port 4444
			serverSocket = new ServerSocket(port);
			System.out.println("unimelb.daz1.Server is listening...");

			while (true) {
				// unimelb.daz1.Server waits for a new connection
				Socket socket = serverSocket.accept();
				// A new thread is created dealing with each client
				Thread client = new Thread(new ClientThread(socket));
				// It starts running the thread by calling run() method
				client.start();
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null)
				serverSocket.close();
		}
	}
	public static Room checkRoom(String roomId){
		Room room = null;
		for(Room r : roomlist){
			if(r.getroomid().equals(roomId)){
				room = r;
				break;
			}
		}
		return room;
	}

}
