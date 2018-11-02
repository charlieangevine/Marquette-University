import java.awt.Point;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameGroup extends Thread {

	List<GameClientThread> clients = new ArrayList<>();
	final int MIN = 2;
	final int MAX = 30;

	int[][] grid;
	String board;
	public static final int WORLD_WIDTH = 100;
	public static final int WORLD_HEIGHT = 100;

	List<Player> players = new ArrayList<>();

	public GameGroup(Socket s) {
		addClient(s);
	}

	public void addClient(Socket s) {
		GameClientThread client = new GameClientThread(s, this);
		client.start();
		clients.add(client);

		if (GameServer.started) {
			String id = new BigInteger("" + ((int) (10000 * Math.random()))).toString(16);
			Point pnt = emptySpot();

			Player p = new Player(pnt.x, pnt.y, (int) (4 * Math.random()));
			p.my_id = id;
			grid[p.x][p.y] = 3;
			client.message("who," + id);
			addPlayer(p);
			update(p);
			client.message("start," + board);
		}
	}

	private void addPlayer(Player p) {
		players = players.stream().filter(player -> {
			return player != null;
		}).collect(Collectors.toList());

		players.add(p);
	}

	public void run() {
		System.out.println("GameGroup begun");
		board = fillGrid();

		for (GameClientThread client : clients) {
			String id = new BigInteger("" + ((int) (10000 * Math.random()))).toString(16);
			Point pnt = emptySpot();

			Player p = new Player(pnt.x, pnt.y, (int) (4 * Math.random()));
			p.my_id = id;
			grid[p.x][p.y] = 3;
			client.message("who," + id);
			addPlayer(p);
			update(p);
		}
		output("start," + board);
	}

	public String fillGrid() {
		grid = new int[WORLD_WIDTH][WORLD_HEIGHT];

		int SIZE = WORLD_WIDTH * WORLD_HEIGHT;

		for (int x = 0; x < WORLD_WIDTH; x++)
			for (int y = 0; y < WORLD_HEIGHT; y++)
				grid[x][y] = 0;
		for (int i = 0; i < (SIZE * 0.4); i++) {
			Point p = emptySpot();
			grid[p.x][p.y] = 1;
		}
		for (int i = 0; i < (SIZE * 0.08); i++) {
			Point p = emptySpot();
			grid[p.x][p.y] = 2;
		}
		for (int i = 0; i < (SIZE * 0.02); i++) {
			Point p = emptySpot();
			grid[p.x][p.y] = 4;
		}

		StringBuffer sb = new StringBuffer(SIZE);
		for (int y = 0; y < WORLD_HEIGHT; y++)
			for (int x = 0; x < WORLD_WIDTH; x++)
				sb.append(grid[x][y]);
		return new String(sb);
	}

	public Point emptySpot() {
		int x, y;
		do {
			x = (int) (WORLD_WIDTH * Math.random());
			y = (int) (WORLD_HEIGHT * Math.random());
		} while (grid[x][y] != 0);
		return new Point(x, y);
	}

	public synchronized void processMessage(String msg) {
		String[] words = msg.split(",");
		String cmd = words[0];

		switch (cmd) {
			case "grab":
				grab(words);
				break;
			case "blast":
				blast(words);
				break;
			case "turnLeft":
				Player p1 = lookup(words[1]);
				p1.turnLeft();
				update(p1);
				break;
			case "turnRight":
				Player p2 = lookup(words[1]);
				p2.turnRight();
				update(p2);
				break;
			case "step":
				step(words);
				break;
		}
	}

	private Player lookup(String id) {
		for (Player p : players)
			if (p.my_id.equals(id))
				return p;
		return null;
	}

	private void grab(String[] args) {
		Player p = lookup(args[1]);
		int x = p.dx();
		int y = p.dy();
		if (grid[x][y] == 2) {
			p.score++;
			grid[x][y] = 0;
			output("grab," + p.my_id + "," + x + "," + y + ",2");
		} else if (grid[x][y] == 4) {
			p.dynamite++;
			grid[x][y] = 0;
			output("grab," + p.my_id + "," + x + "," + y + ",4");
		}

		checkGameOver();
	}

	private void blast(String[] args) {
		Player p = lookup(args[1]);
		int x = p.dx();
		int y = p.dy();

		if (p.dynamite > 0 && grid[x][y] == 1) {
			grid[x][y] = 0;
			p.dynamite--;
			output("blast," + p.my_id + "," + x + "," + y);
		}
	}

	private void step(String[] args) {
		Player p = lookup(args[1]);
		int x = p.dx();
		int y = p.dy();

		if (x < 0 || x >= GameGroup.WORLD_WIDTH)
			return;
		if (y < 0 || y >= GameGroup.WORLD_HEIGHT)
			return;

		if (grid[x][y] != 0)
			return;

		grid[p.x][p.y] = 0;

		p.x = x;
		p.y = y;

		grid[p.x][p.y] = 3;

		update(p);
	}

	public void checkGameOver() {
		for (int y = 0; y < WORLD_HEIGHT; y++)
			for (int x = 0; x < WORLD_WIDTH; x++)
				if (grid[x][y] == 2)
					return;
		output("gameover");
	}

	private void update(Player p) {
		output("update," + p.my_id + "," + p.x + "," + p.y + "," + p.dir + "," + p.color.getRed() + ","
				+ p.color.getGreen() + "," + p.color.getBlue());
	}

	public void output(String str) {
		for (GameClientThread client : clients)
			client.message(str);
	}

	public boolean full() {
		return clients.size() >= MIN;
	}
}
