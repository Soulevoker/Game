package com.reapersrage.game;

import com.reapersrage.entities.mobs.Player;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.level.Level;
import com.reapersrage.world.level.RandomLevel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Game extends Canvas implements Runnable {

	// Make sure we have a 16:9 aspect ratio
	static final int WIDTH = 800;
	// width and height of game screen
	static final int HEIGHT = 600;
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
	private Player player;
	private Thread gameThread;
	// Frame rate (FPS)
	static final int FRAMERATE = 50;

	JFrame frame;
	Canvas canvas;
	BufferStrategy bufferStrategy;

	public Game() {
		JFrame frame = new JFrame(NAME);

		// Sets up Jpanel
		JPanel panel = (JPanel) frame.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		// sets up the canvas we will be rendering to
		canvas = new Canvas();
		canvas.setBounds(0, 0, WIDTH, HEIGHT);
		canvas.setIgnoreRepaint(true);

		// adds the canvas to jpanel
		panel.add(canvas);

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Makes it render correctly
		frame.pack();
		frame.setResizable(false);
		// Make the windows open in the center of the screen
		frame.setLocationRelativeTo(null);
		// Make the screen actually appear
		frame.setVisible(true);

		// Does magic
		canvas.createBufferStrategy(2);
		bufferStrategy = canvas.getBufferStrategy();
		canvas.requestFocus();

	}

	public static void main(String[] args) {
		Game game = new Game();

		key = new Keyboard();
		game.addKeyListener(key);

		game.start();

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
		player = new Player(key);
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
		player.update();
	}

	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);
		test(g);
		g.dispose();
		bufferStrategy.show();
		
		
		screen.clear();

		player.render(screen); // Render the Player

	}
	
	protected void test(Graphics2D g){
	      g.fillRect(0, 0, 200, 200);
	   }
	
}
