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
import java.io.IOException;

public class Game extends Canvas implements Runnable {
	
     static final int MAP_WIDTH = 20;
     static final int MAP_HEIGHT = 20;

	// Make sure we have a 16:9 aspect ratio
	static final int WIDTH = 800;
	// width and height of game screen
	static final int HEIGHT = 600;
	// Name of the game to display on windows
	static final String NAME = "DOODLE ARENA WARS 2015";
	// Keyboard class for input
	private static Keyboard key;
	// Is the game running
	private boolean running = false;
	
	private Screen screen;
	private static Level level;
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

		frame.setResizable(false);
		// Make the windows open in the center of the screen
		frame.setLocationRelativeTo(null);
		// Makes it render correctly
		frame.pack();
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

	public void init() throws IOException {
		level = new RandomLevel(MAP_WIDTH, MAP_HEIGHT);
		screen = new Screen(WIDTH, HEIGHT);
		player = new Player(0,0,key);
	
	}

	public void run() {
		try {
			init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (running) {
			long frameStart = System.currentTimeMillis();
			update();
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

	public void update() {
		key.update();
		player.update();
	}

	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
	
		screen.drawBackground(g);
		
		player.drawPlayer(g);
		
		g.dispose();
		bufferStrategy.show();
		
		

	}

	public static Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public static int getMapWidth() {
		return MAP_WIDTH;
	}

	public static int getMapHeight() {
		return MAP_HEIGHT;
	}

	public  int getWidth() {
		return WIDTH;
	}

	public  int getHeight() {
		return HEIGHT;
	}
	
	
	
	
	
}
