package com.reapersrage.game;

import com.reapersrage.entities.Player;
import com.reapersrage.gfx.GameOverScreen;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Buttons;
import com.reapersrage.input.Keyboard;
import com.reapersrage.input.Mouse;
import com.reapersrage.world.level.Level;
import com.reapersrage.world.level.RandomLevel;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Game extends Canvas implements Runnable {

	static final int MAP_WIDTH = 10;
	static final int MAP_HEIGHT = 10;
	
	private static int absolute_MapWidth = 20;
	private static int absolute_MapHeight = 20;

	// Make sure we have a 16:9 aspect ratio
	static final int WIDTH = 800;
	// width and height of game screen
	static final int HEIGHT = 600;
	// Name of the game to display on windows
	static final String NAME = "DOODLE ARENA WARS 2015";
	// Keyboard class for input
	private Keyboard key;
	private Mouse mouse;
	// Is the game running
	private boolean running = false;
	private static Buttons buttonsPressed;
	private static String buttonPressed;
	private BufferedImage OImage;
	private GameOverScreen gameover;
	

	private int playerDir;
        
	// value which will decide if the game is main menu/game/ or game over
	// screen
	private static int gameState;

	// Directions the player can move in: {Up, Down, Left, Right}
	private boolean[] playerDirs;
	private boolean[] projDirs;
	private static Screen screen;
	private static Level level;
	private static Player player;

	private Thread gameThread;
	// Length of frame (ms)
	static final int FRAMERATE = 50;

	JFrame container;
	// JFrame debugPanel; //for debuging purposes
	JPanel panel;
	Canvas canvas;
	BufferStrategy bufferStrategy;

	// Strings for debug
	public static int ticks;

	public static Debug debugPanel = new Debug();

	public Game() {
		buttonPressed = "";
		buttonsPressed = new Buttons();
		// Directions sent to the player
		playerDirs = new boolean[9];
		// initializes jframe
		container = new JFrame(NAME);

		// initialized jpane;
		panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		panel.setLayout(null);

		// sets boundaries of panel and adds canvas to panel
		setBounds(0, 0, WIDTH, HEIGHT);
		panel.add(this);

		setIgnoreRepaint(true);

		// more jframe stuff
		container.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		container.setResizable(false);
		container.pack();
		container.setVisible(true);

		try {
			OImage = ImageIO
					.read(GameTile.class
							.getResourceAsStream("/com/reapersrage/res/textures/JimIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		container.setIconImage(OImage);

		// adds key listener
		key = new Keyboard();
		addKeyListener(new Keyboard());
		
		mouse = new Mouse();
		addMouseListener(mouse);
		
		// requests focus for our keylistener
		requestFocus();

		// does stuff
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		this.ticks = 0;
				
		//sets current state of the game
		gameState = 1;
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

	// Objects to create
	// NOTE: TO MAKE THEM SHOW UP THEY MUST ALSO BE SET TO RENDER!
	public void init() throws IOException {
		level = new RandomLevel(absolute_MapWidth, absolute_MapHeight);
		screen = new Screen(WIDTH, HEIGHT);
		player = new Player((WIDTH/2)-((WIDTH/MAP_WIDTH)/2), (HEIGHT/2)-((HEIGHT/MAP_HEIGHT)/2), WIDTH/MAP_WIDTH, HEIGHT/MAP_HEIGHT);
		gameover = new GameOverScreen(WIDTH, HEIGHT);
		
	}

	// Run the game
	public void run() {
		try {
			init();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (running) {
			// Time where the grame started
			long frameStart = System.currentTimeMillis();
			// Update all the objects
			update();
			// Render all the objects
			render();
			// Determine how long it took the frame to update/render
			long frameLength = System.currentTimeMillis() - frameStart;
			
			if (frameLength < FRAMERATE) {
				// If that time is less than the desired frame length, sleep the
				// remaining time
				try {
					gameThread.sleep(FRAMERATE - frameLength);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	// Update routine
	public void update() {
                long updateStart = System.currentTimeMillis();
               

                //debugPanel.setLabel(3, ""+(int)Math.sqrt((double)Game.ticks));

                //debugPanel.addLabel(0, ""+ticks % 50);


		//gamestate = 1 when the game is being played
		if(gameState == 1){
		
                    // Update the player, passing the buttons pressed
                    player.update(null);
                    ticks++;
                    // Update the level
                    level.update(player);
                    screen.Update(player);

                    //sets gamestate to gameover if player is destroyed
                    if (player.isDestroyed()) {
			gameState = 2;
                    }
		
              
                  
                }
		//gamestate = 2 when the game is over
		if(gameState == 2){
			gameover.Update();
		}
		//game state becomes 55 before it become 1 so that we can reset the game
		if(gameState == 55){
			try {
				resetGame();
			} catch (IOException e) {
				e.printStackTrace();
			}
			gameState = 1;
		}
                long timePassed = System.currentTimeMillis() - updateStart;
                debugPanel.setLabel(3, "<html>" + ticks % 50 + "<br>" + timePassed);

	}


	// Renders everything
	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		// Draw the background, mobs, and player; last is on top
		if(gameState == 1){
		screen.drawBackground(g);
		level.drawEntities(g);
		player.draw(g);
		}
		if(gameState == 2){
			gameover.drawBackground(g);
		}

		g.dispose();
		bufferStrategy.show();


	}

	//used to reset game will be fancier in future
	private void resetGame() throws IOException {
		buttonsPressed = new Buttons();
		buttonPressed = "";
		for(int i = 0; i < playerDirs.length; i++){
			playerDirs[i] = false;
		}
		
		init();		
	}


	// Determine which buttons are currently being pressed
	public void ButtonPressed() {
		playerDirs[0] = buttonsPressed.up;
		playerDirs[1] = buttonsPressed.down;
		playerDirs[2] = buttonsPressed.left;
		playerDirs[3] = buttonsPressed.right;
		playerDirs[4] = buttonsPressed.space;

		playerDirs[5] = buttonsPressed.projUp;
		playerDirs[6] = buttonsPressed.projDown;
		playerDirs[7] = buttonsPressed.projLeft;
		playerDirs[8] = buttonsPressed.projRight;
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

	// I needed static methods for width and height and didn't want to get rid
	// of the old ones
	public static int getStaticWidth() {
		return WIDTH;
	}

	public static int getStaticHeight() {
		return HEIGHT;
	}
        
        //Text for the debug console
        public static void setDebugText(int Loc, String text){
                debugPanel.setLabel(Loc,text);
        }
        public static int getTicks(){
            return ticks;
        }
        
	public static int getGameState() {
		return gameState;
	}

	public static void setGameState(int gameState) {
		Game.gameState = gameState;
	}
        
        public static Player getPlayer(){
            return player;
        }

		public static int getAbsolute_MapWidth() {
			return absolute_MapWidth;
		}

		public static void setAbsolute_MapWidth(int absolute_MapWidth) {
			Game.absolute_MapWidth = absolute_MapWidth;
		}

		public static int getAbsolute_MapHeight() {
			return absolute_MapHeight;
		}

		public static void setAbsolute_MapHeight(int absolute_MapHeight) {
			Game.absolute_MapHeight = absolute_MapHeight;
		}

		public static Screen getScreen() {
			return screen;
		}



		public static Buttons getButtonsPressed() {
			return buttonsPressed;
		}
        
		
        
    
}
