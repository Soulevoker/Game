package com.reapersrage.game;

import com.reapersrage.entities.mobs.Player;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Buttons;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.level.Level;
import com.reapersrage.world.level.RandomLevel;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

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
        private BufferedImage OImage;

	
        private int playerDir;
        

        //Directions the player can move in: {Up, Down, Left, Right}
        private boolean[] playerDirs;
	private Screen screen;
	private static Level level;
	private Player player;
        
	private Thread gameThread;
	// Frame rate (FPS)
	static final int FRAMERATE = 50;


	JFrame container;
        //JFrame debugPanel; //for debuging purposes
	JPanel panel;
	Canvas canvas;
	BufferStrategy bufferStrategy;
        
        //Strings for debug
        
        
        public static Debug debugPanel = new Debug();
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
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		container.setResizable(false);
		container.pack();
		container.setVisible(true);
                
                try {
			OImage = ImageIO
			.read(GameTile.class
			.getResourceAsStream("/com/reapersrage/res/textures/JimIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();}
                container.setIconImage(OImage);
                
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

        //Objects to create 
        //NOTE: TO MAKE THEM SHOW UP THEY MUST ALSO BE SET TO RENDER!
	public void init() throws IOException {
		level = new RandomLevel(MAP_WIDTH, MAP_HEIGHT);
		screen = new Screen(WIDTH, HEIGHT);
		player = new Player(0, 0, WIDTH/MAP_WIDTH, HEIGHT/MAP_HEIGHT);
	}
        //Run the game
	public void run() {
		try {
			init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (running) {
                        //Time where the grame started
			long frameStart = System.currentTimeMillis();
			//Update all the objects
                        update();
                        //Render all the objects
			render();
                        //Determine how long it took the frame to update/render
			long frameLength = System.currentTimeMillis() - frameStart;
			if (frameLength < FRAMERATE) {
                            //If that time is less than the desired frame length, sleep the remaining time
				try {
					gameThread.sleep(FRAMERATE - frameLength);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
        
        //Update routine
	public void update() {
                //Check which buttons are pressed
		ButtonPressed();
                //Update the player, passing the buttons pressed
		player.update(playerDirs);
                //Update the level
                level.update(player);
	}

        //Renders everything
	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
                //Draw the background, mobs, and player; last is on top
		screen.drawBackground(g);
                level.renderMobs(g);                
                player.drawPlayer(g);
                //Actuall render it               
		g.dispose();
		bufferStrategy.show();
                
        }
	//Determine which buttons are currently being pressed
	public void ButtonPressed(){
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
        
        //I needed static methods for width and height and didn't want to get rid of the old ones
        public static int getStaticWidth() {
		return WIDTH;
	}

	public static int getStaticHeight() {
		return HEIGHT;
	}
        //When a button is pressed, set to true
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
        //When a button is released, set to false
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
        //Text for the debug console
        public static void setDebugText(int Loc, String text){
                debugPanel.setLabel(Loc,text);
        }
        
        
}

