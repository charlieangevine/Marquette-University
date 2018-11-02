import java.net.ServerSocket;
import java.net.Socket;

public class GameServer {

	static ServerSocket socket = null;
	static boolean started = false;

	public static void main(String[] args) {
		int port;
		GameGroup parent = null;

		try {
			port = Integer.parseInt(args[0]);
		} catch (Exception e) {
			port = 2018;
		}

		try {
			socket = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Could not initialize. Exiting.");
			System.exit(1);
		}
		System.out.println("Server successfully initialized. Waiting for connection on port " + port);

		while (socket != null) {
			Socket tempSocket;
			try {
				tempSocket = socket.accept();
				System.out.println("Received new connection");
				if (parent != null) {
					parent.addClient(tempSocket);
					if (parent.full() && !started) {
						parent.start();
						started = true;
					}

				} else
					parent = new GameGroup(tempSocket);

			} catch (Exception e) {
				System.out.println("New connection failure. Exiting." + e);
				System.exit(1);
			}

			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}

	public void finalize() {
		try {
			socket.close();
		} catch (Exception e) {
		}
		socket = null;
	}
}
