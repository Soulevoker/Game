package com.reapersrage.game;

import com.reapersrage.entities.mobs.Player;
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
	static final int HEIGHT = 240;
	static final int WIDTH = HEIGHT * 16 / 9;
	static final int SCALE = 3;

	private final int FRAME_RATE = 20;

	static final String NAME = "DOODLE ARENA WARS 2015";
	private static Keyboard key;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT,
			BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
			.getData();
	private boolean running = false;
	private int tickCount;
	private Screen screen;
	private Level level;
	private Player player;
	private Thread gameThread;
	private int xScroll, yScroll;

	public static void main(String[] args) {
		Game game = new Game();

		// not being used
		game.setMinimumSize(new Dimension((WIDTH - WIDTH % 16) * SCALE, HEIGHT
				* SCALE));
		game.setMaximumSize(new Dimension((WIDTH - WIDTH % 16) * SCALE, HEIGHT
				* SCALE));
		game.setPreferredSize(new Dimension((WIDTH - WIDTH % 16) * SCALE,
				HEIGHT * SCALE));

		JFrame frame = new JFrame(NAME);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();

		key = new Keyboard();

		game.addKeyListener(key);
	}

	public synchronized void start() {
		running = true;
		gameThread = new Thread(this, NAME);
		gameThread.start();
		// new Thread(this).start();
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
		player = new Player(key);
		/*
		 * try { screen = new Screen(WIDTH, HEIGHT, new
		 * SpriteSheet(ImageIO.read(
		 * Game.class.getResourceAsStream("/icons.png")))); } catch (IOException
		 * e) { e.printStackTrace(); }
		 */
	}

	public void run() {

		// Initializes game.
		init();

		// Game Loop.
		while (running) {

			// sets start if frame.
			long frameStart = System.currentTimeMillis();

			// gameUpdate updates the game, gameRender renders update to screen.
			gameUpdate();
			gameRender();

			// frameLength is set to the time it take to update and render to
			// screen.
			long frameLength = System.currentTimeMillis() - frameStart;

			// checks to see that we haven't exceeded our framerate.
			if (frameLength < (1000 / FRAME_RATE)) {
				try {
					// sleeps for correct amount of time to keep consistent
					// framerate.
					gameThread.sleep((1000 / FRAME_RATE) - frameLength);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}

	}

	public void gameUpdate() {
		key.update();
		player.update();
	}

	public void gameRender() {
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
		g.drawString("X: " + player.getX() + " Y: " + player.getY() + " Dir: "
				+ player.getDir(), 700, 650); // DEBUG
		g.dispose();
		bs.show();
	}
}
