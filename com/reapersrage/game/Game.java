package com.reapersrage.game;

import com.reapersrage.entities.mobs.Mob;
import com.reapersrage.entities.mobs.Player;
import com.reapersrage.gfx.GameTile;
import com.reapersrage.gfx.Screen;
import com.reapersrage.input.Buttons;
import com.reapersrage.input.Keyboard;
import com.reapersrage.world.level.Level;
import com.reapersrage.world.level.RandomLevel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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

	private int dir;

	JFrame container;
        //JFrame debugPanel; //for debuging purposes
	JPanel panel;
	Canvas canvas;
	BufferStrategy bufferStrategy;
        
        //Mobs on Screen. Should move to level later or some sort of mob generator
        //private Mob spikeMob;
        //private Mob foutainMob;
        //List to store the mobs
        //private ArrayList<Mob> MobList;
        //private Mob spikeMob2;
        
        //Strings for debug
        private String collisionDebug;
        
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

        //Objects to create 
        //NOTE: TO MAKE THEM SHOW UP THEY MUST ALSO BE SET TO RENDER!
	public void init() throws IOException {
		level = new RandomLevel(MAP_WIDTH, MAP_HEIGHT);
		screen = new Screen(WIDTH, HEIGHT);
		player = new Player(0, 0, WIDTH/MAP_WIDTH, HEIGHT/MAP_HEIGHT);
                //spikeMob = new Mob(100, 100,80 , 80, 1, 1, "spike");
                //foutainMob = new Mob(300,150,80,80,-1,1,"foutain");
                

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
        
        //update routine
	public void update() {
		ButtonPressed();
		player.update(playerDirs);
                level.update(player);
//<<<<<<< HEAD
//                //spikeMob.update(player);
//                //foutainMob.update(player);
//                
//                //collisionDebug = "<html>Mobs";
//                //Iterator<Mob> mobIterator = MobList.iterator();
//                //while(mobIterator.hasNext()){
//                    //Mob currentMob = mobIterator.next();
//                    //currentMob.update(player);
//                    //collisionDebug = collisionDebug +"<br>"+ currentMob.getName() + " (" + currentMob.getX() + ", " + currentMob.getY() + ")"+ " collision: " + currentMob.isCollided(player); 
//               // }
//               
//               // collisionDebug = collisionDebug+"</html>";
//                debugPanel.setLabel(1,collisionDebug);
//                //debugPanel.setLabel(0,"<html>Player<br> Position: ("+player.getX()+","+player.getY()+")"+"<br>"+"Health: "+player.getHealth()+"</html>");
//                
//                
//=======
//                level.update(player);
//>>>>>>> origin/newTile
	}

        //Renders everything
	public void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

		screen.drawBackground(g);
                //what goes last stays on top
                //spikeMob.drawMob(g);
		//foutainMob.drawMob(g);
                level.renderMobs(g);
                
                player.drawPlayer(g);
                
               
		g.dispose();
		bufferStrategy.show();
                
        }
	
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

	public boolean[] getPlayerDir() {
		return playerDirs;
	}
        
        public static void setDebugText(int Loc, String text){
                debugPanel.setLabel(Loc,text);
        }
        
        
}

