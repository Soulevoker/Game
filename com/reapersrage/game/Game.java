package com.reapersrage.game;

import com.reapersrage.entities.mobs.Player;
import com.reapersrage.entities.mobs.Mob;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.level.Level;
import com.reapersrage.world.level.RandomLevel;
import com.reapersrage.input.Buttons;

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
	private static Buttons buttonsPressed;
	private static String buttonPressed;
	
        private int playerDir;
        
        //Directions the player can move in: {Up, Down, Left, Right}
        private boolean[] playerDirs;
	private Screen screen;
	private static Level level;
	private Player player;
        private Mob spikeMob;
	private Thread gameThread;
	// Frame rate (FPS)
	static final int FRAMERATE = 50;

	private int dir;

	JFrame container;
	JPanel panel;
	Canvas canvas;
	BufferStrategy bufferStrategy;

	public Game() {
		buttonPressed = "";
                buttonsPressed = new Buttons();
		//playerDir = 0;
                playerDirs = new boolean[4];
		//initializes jframe
		container = new JFrame(NAME);

		//initialized jpane;
		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		//sets boundaries of panel and adds canvas to panel
		setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(this);

		setIgnoreRepaint(true);

		//more jframe stuff
		container.setResizable(false);
		container.pack();
		container.setVisible(true);

		//adds key listener
		addKeyListener(new Keyboard());

		//requests focus for our keylistener
		requestFocus();

		//does stuff
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();

	}

	public static void main(String[] args) {
		Game game = new Game();

		/*
		 * key = new Keyboard(); game.addKeyListener(key);
		 */

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
		player = new Player(0, 0, WIDTH/MAP_WIDTH, HEIGHT/MAP_HEIGHT);
                spikeMob = new Mob(100, 100,80 , 80, 10);

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
		ButtonPressed();
		player.update(playerDirs);
                if (player.getX() == spikeMob.getX() && player.getY() == spikeMob.getX()){
                    spikeMob.dealDamage(player);
                }
                
                
	}

        //Renders everything
	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		screen.drawBackground(g);
                //what goes last stays on top
                spikeMob.drawMob(g);
		player.drawPlayer(g);
               
		g.dispose();
		bufferStrategy.show();
                System.out.println("Health: "+player.getHealth()+" PlayerPos: ("+ player.getX()+","+player.getY()+") SpikePos: ("+spikeMob.getX()+","+spikeMob.getY()+")");

	}
	
	public void ButtonPressed(){
		/*switch (buttonPressed) {
		case "down":
			playerDir = 1;
			break;
		case "up":
			playerDir = 2;
			break;
		case "left":
			playerDir = 3;
			break;
		case "right":
			playerDir = 4;
			break;
		default:
			playerDir = 0;
			break;
		}*/
                playerDirs[0] = buttonsPressed.up;
                playerDirs[1] = buttonsPressed.down;
                playerDirs[2] = buttonsPressed.left;
                playerDirs[3] = buttonsPressed.right;
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

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public String getButtonPressed() {
		return buttonPressed;
	}

	public static void setButtonPressed(String b) {
		buttonPressed = b;
                if(b.equals("up")){
                    buttonsPressed.up = true;
                }
                if(b.equals("down")){
                    buttonsPressed.down = true;
                }
                if(b.equals("left")){
                    buttonsPressed.left = true;
                }
                if(b.equals("right")){
                    buttonsPressed.right = true;
                }
	}
        
        public static void setButtonReleased(String b){
                if(b.equals("up")){
                    buttonsPressed.up = false;
                }
                if(b.equals("down")){
                    buttonsPressed.down = false;
                }
                if(b.equals("left")){
                    buttonsPressed.left = false;
                }
                if(b.equals("right")){
                    buttonsPressed.right = false;
                }
        }

	public int getPlayerDir() {
		return playerDir;
	}

	public void setPlayerDir(int playerDir) {
		this.playerDir = playerDir;
	}
	
	

}

