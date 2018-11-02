import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Grab extends Application implements Runnable {
	final String appName = "Grab";

	GraphicsContext gc;

	Thread kicker;

	int grid[][] = new int[GameGroup.WORLD_WIDTH][GameGroup.WORLD_HEIGHT];
	public static final int CELLSIZE = 64;

	public static final int WIDTH = 12 * CELLSIZE;
	public static final int HEIGHT = 10 * CELLSIZE;
	boolean setup = false;

	List<Player> players = new ArrayList<>();
	String my_id;

	PrintWriter pw;
	Socket s = null;
	BufferedReader br = null;
	String name, host = "localhost";
	int port = 2018;

	Image img = null;;

	void initialize() {
		try {
			BufferedImage bimg = ImageIO
					.read(new URL("https://phaser.io/images/examples/2/thumb/tile-sprites/tiling-sprite.jpg"));
			img = SwingFXUtils.toFXImage(bimg, null);
			makeContact();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SoundFX.initialize();

		kicker = new Thread(this);
		kicker.setPriority(Thread.MIN_PRIORITY);
		kicker.setDaemon(true);
		kicker.start();
		render(gc);
	}

	private void makeContact() {
		while (s == null)
			try {
				System.out.println("Attempting to make connection: " + host + ":" + port);
				s = new Socket(host, port);
				br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				pw = new PrintWriter(s.getOutputStream());
			} catch (Exception e) {
				try {
					Thread.sleep(7500);
				} catch (Exception ex) {
				}
			}
		System.out.println("Connection established.");
	}

	public Player lookup(String id) {
		for (Player p : players)
			if (p.my_id.equals(id))
				return p;
		return null;
	}

	public Player getPlayerAt(int x, int y) {
		for (Player p : players)
			if (p.x == x && p.y == y)
				return p;
		return null;
	}

	public void run() {
		while (kicker != null) {
			String input = null;
			while (input == null)
				try {
					Thread.sleep(100);
					input = br.readLine();
				} catch (Exception e) {
					input = null;
				}

			System.out.println("Got input: " + input);

			String[] words = input.split(",");
			String cmd = words[0];

			switch (cmd) {
				case "grab":
					grab(words);
					break;
				case "blast":
					blast(words);
					break;
				case "start":
					fillGrid(words[1]);
					setup = true;
					render(gc);
					break;
				case "who":
					my_id = words[1];
					break;
				case "update":
					update(words);
					break;
			}
		}
	}

	private void grab(String[] args) {
		int x = Integer.parseInt(args[2]);
		int y = Integer.parseInt(args[3]);
		int d = Integer.parseInt(args[4]);
		Player p = lookup(args[1]);
		Player me = lookup(my_id);
		double volume = 1.0 - (distance(p, me) / (CELLSIZE * 10));
		if (d == 2) {
			SoundFX.COIN.play(volume);
			p.score++;
		} else if (d == 4) {
			SoundFX.RELOAD.play(volume);
			p.dynamite++;
		}
		grid[x][y] = 0;
		render(gc);
	}

	private double distance(Player p1, Player p2) {
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	private void blast(String[] args) {
		Player p = lookup(args[1]);
		Player me = lookup(my_id);
		int x = Integer.parseInt(args[2]);
		int y = Integer.parseInt(args[3]);
		SoundFX.BOOM.play(1.0 - (distance(p, me) / (CELLSIZE * 10)));
		p.dynamite--;
		grid[x][y] = 0;
		render(gc);
	}

	private void update(String[] args) {
		Player p = lookup(args[1]);
		if (p == null) {
			System.out.println("Could not find " + args[1] + ". Creating new.");
			p = new Player(0, 0, 0);
			p.my_id = args[1];
			players.add(p);
		} else {
			grid[p.x][p.y] = 0;
		}

		p.x = Integer.parseInt(args[2]);
		p.y = Integer.parseInt(args[3]);
		p.dir = Integer.parseInt(args[4]);

		double r = Double.parseDouble(args[5]);
		double g = Double.parseDouble(args[6]);
		double b = Double.parseDouble(args[7]);

		p.color = new Color(r, g, b, 1);

		grid[p.x][p.y] = 3;

		render(gc);
	}

	void fillGrid(String board) {
		int i = 0;
		char c;
		for (int y = 0; y < GameGroup.WORLD_HEIGHT; y++)
			for (int x = 0; x < GameGroup.WORLD_WIDTH; x++) {
				c = board.charAt(i++);
				switch (c) {
					case '0':
						grid[x][y] = 0;
						break;
					case '1':
						grid[x][y] = 1;
						break;
					case '2':
						grid[x][y] = 2;
						break;
					case '3':
						grid[x][y] = 3;
						break;
					case '4':
						grid[x][y] = 4;
						break;
				}
			}
	}

	public void finalize() {
		try {
			br.close();
			pw.close();
			s.close();
		} catch (Exception e) {
		}
	}

	public void render(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, WIDTH, HEIGHT);
		gc.setFill(Color.BLACK);

		if (img != null)
			gc.drawImage(img, 0, 0, WIDTH, HEIGHT);

		int W = WIDTH / CELLSIZE;
		int H = HEIGHT / CELLSIZE;

		if (!setup) {
			gc.fillText("Waiting...", 50, 50);
			return;
		}

		Player p = lookup(my_id);
		if (p == null) {
			gc.fillText("Waiting for player information...", 50, 50);
			return;
		}
		for (int x = 0; x < W; x++)
			for (int y = 0; y < H; y++) {
				int x1 = p.x - (W / 2) + x;
				int y1 = p.y - (H / 2) + y;
				if (x1 >= 0 && x1 < GameGroup.WORLD_WIDTH && y1 >= 0 && y1 < GameGroup.WORLD_HEIGHT) {
					int v = grid[x1][y1];
					gc.setFill(Color.WHITE);
					gc.fillRect(CELLSIZE * x, CELLSIZE * y, CELLSIZE, CELLSIZE);
					switch (v) {
						case 1:
							gc.setFill(Color.GRAY);
							gc.fillRect(CELLSIZE * x, CELLSIZE * y, CELLSIZE - 1, CELLSIZE - 1);
							break;
						case 2:
							gc.setFill(Color.ORANGE);
							gc.fillOval(CELLSIZE * x + 2, CELLSIZE * y + 2, CELLSIZE - 4, CELLSIZE - 4);
							break;
						case 4:
							gc.setFill(Color.RED);
							gc.fillRect(CELLSIZE * x + 14, CELLSIZE * y + 18, CELLSIZE - 28, CELLSIZE - 36);
							break;
						case 3:
							Player player = getPlayerAt(p.x + x - (W / 2), p.y + y - (H / 2));
							if (!p.my_id.equals(player.my_id) && player != null)
								player.render(gc, CELLSIZE * x + 1, CELLSIZE * y + 1);
							break;
						default:
							break;
					}
				}
			}
		p.render(gc, WIDTH / 2, HEIGHT / 2);

		int y = 8;
		Collections.sort(players, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				if (o1.score == o2.score)
					return o1.my_id.compareTo(o2.my_id);
				if (o1.score > o2.score)
					return -1;
				else
					return 1;
			}
		});
		for (Player player : players) {
			gc.setFill(player.color);
			gc.setTextAlign(TextAlignment.RIGHT);
			gc.setTextBaseline(VPos.CENTER);
			gc.fillText(player.my_id + ": " + player.score, gc.getCanvas().getWidth() - 8, y);
			y += 16;
		}
		gc.setFill(Color.RED);
		gc.setTextAlign(TextAlignment.LEFT);
		gc.fillText("Dynamite: " + p.dynamite, 8, 8);
	}

	public void tellServer(String msg) {
		System.out.println("to server: " + msg);
		boolean flag = false;
		while (!flag)
			try {
				pw.println(msg);
				pw.flush();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				flag = false;
			}
	}

	void setHandlers(Scene scene) {
		scene.setOnKeyPressed(e -> {
			KeyCode c = e.getCode();
			switch (c) {
				case B:
					tellServer("blast," + my_id);
					break;
				case G:
					tellServer("grab," + my_id);
					break;
				case J:
				case LEFT:
					tellServer("turnLeft," + my_id);
					break;
				case L:
				case RIGHT:
					tellServer("turnRight," + my_id);
					break;
				case K:
				case UP:
					tellServer("step," + my_id);
					break;
				default:
					break;
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle(appName);

		Group root = new Group();
		Scene scene = new Scene(root);
		stage.setScene(scene);

		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();

		initialize();

		setHandlers(scene);

		stage.show();
	}
}
