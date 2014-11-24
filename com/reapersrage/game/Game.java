package com.reapersrage.game;

import com.reapersrage.things.Player;
import com.reapersrage.things.AllThings;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.level.Level;
import com.reapersrage.world.level.RandomLevel;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

	// width and height of game screen
	static final int HEIGHT = (240 / 16) * 16;
	// Make sure we have a 16:9 aspect ratio
	static final int WIDTH = ((HEIGHT * 16 / 9) / 16) * 16;
	// Scale the pixels by 3
	static final int SCALE = 3;
	// Name of the game to display on windows
	static final String NAME = "DOODLE ARENA WARS 2015";
	// Keyboard class for input
	private static Keyboard key;
	// Image buffer to display
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	// Array of pixels
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();
	// Is the game running
	private boolean running = false;
	// Number of ticks for something
	private int tickCount;
	private Screen screen;
	private Level level;
	//Player, add all object here
	//private Player player;
	private AllThings allThings;
	private Thread gameThread;
	//Frame rate (FPS)
	static final int FRAMERATE = 50;

	public static void main(String[] args) {
		Game game = new Game();

		// not being used
		// game.setMinimumSize(new Dimension((WIDTH - WIDTH%16)*SCALE, HEIGHT *
		// SCALE));
		// game.setMaximumSize(new Dimension((WIDTH - WIDTH%16)*SCALE, HEIGHT *
		// SCALE));
		// Set the dimensions of the screen
		game.setPreferredSize(new Dimension((WIDTH - WIDTH % 16) * SCALE,
				HEIGHT * SCALE));
		// New
		JFrame frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// I don't know what this does
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		// Makes it render correctly
		frame.pack();
		frame.setResizable(false);
		// Make the windows open in the center of the screen
		frame.setLocationRelativeTo(null);
		// Make the screen actually appear
		frame.setVisible(true);

		game.start();

		key = new Keyboard();

		game.addKeyListener(key);
	}

	// Starts the game
	public synchronized void start() {
		// The game is running
		running = true;
		// New thread to run the game
		gameThread = new Thread(this, NAME);
		// Start the game thread
		gameThread.start();

	}

	public synchronized void stop() {
		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		screen = new Screen(WIDTH, HEIGHT);
		level = new RandomLevel(64, 64);
		//player = new Player(key);
		allThings = new AllThings();
		allThings.createDefaultPlayer(key);
		/*
		 * try { screen = new Screen(WIDTH, HEIGHT, new
		 * SpriteSheet(ImageIO.read(
		 * Game.class.getResourceAsStream("/icons.png")))); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
	}

	public void run() {
		init();
		while (running) {
			long frameStart = System.currentTimeMillis();
			tick();
			render();
			long frameLength = System.currentTimeMillis() - frameStart;
			if (frameLength < FRAMERATE) {
				try {
					gameThread.sleep(FRAMERATE - frameLength);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void tick() {
		tickCount++;
		key.update();
		//player.update();
		allThings.update();
	}

	public void render() {
		Player player=allThings.getPlayerAt(0);
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			requestFocus();
			return;
		}
		screen.clear();
		// screen.render(xScroll, yScroll);
		// level.render(player.getX(), player.getY(), screen); This will move
		// the entire level with directional button
		level.render(0, 0, screen); // Render the level
		player.render(screen); // Render the Player

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.getPixel(i);
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", 0, 50));
		// Display the player's position on the screen, bottom-right -- scales
		// to size
		g.drawString("X: " + player.getX() + " Y: " + player.getY() + " Dir: "
				+ player.getDir(), (int) (WIDTH - 150) * SCALE, (int) (HEIGHT)
				* SCALE); // DEBUG
		g.dispose();
		bs.show();
	}
}
