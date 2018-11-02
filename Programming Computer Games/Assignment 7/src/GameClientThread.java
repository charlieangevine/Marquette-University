import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClientThread extends Thread {

	GameGroup parent;
	Socket socket;
	BufferedReader br;
	PrintWriter pw;

	GameClientThread(Socket socket, GameGroup parent) {
		this.socket = socket;
		this.parent = parent;
	}

	public void run() {
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream());
		} catch (Exception e) {
		}

		while (socket != null) {
			String input = null;
			try {
				input = br.readLine().trim();
				if (input != null)
					parent.processMessage(input);
			} catch (Exception e) {
				socket = null;
			}
			try {
				sleep(100);
			} catch (Exception e) {
			}
		}
	}

	public boolean message(String str) {
		boolean flag = false;
		System.out.println("Sending mssage: " + str);
		while (!flag)
			try {
				pw.println(str);
				pw.flush();
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		return true;
	}

	public void finalize() {
		try {
			pw.close();
			br.close();
			socket.close();
		} catch (Exception e) {
		}
		socket = null;
	}
}
