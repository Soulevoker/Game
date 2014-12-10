package com.reapersrage.game;

//import com.reapersrage.things.Player;
//import com.reapersrage.things.AllThings;
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

    static final int SCALE = 3;

    // Starts the game
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

    /**
     * Starts the game thread
     */
    public synchronized void start() {
        // The game is running
        running = true;
        // New thread to run the game
        gameThread = new Thread(this, NAME);
        // Start the game thread
        gameThread.start();

    }

    /**
     * Stops the game thread
     */
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
        player = new Player((WIDTH / 2) - ((WIDTH / MAP_WIDTH) / 2), (HEIGHT / 2) - ((HEIGHT / MAP_HEIGHT) / 2), WIDTH / MAP_WIDTH, HEIGHT / MAP_HEIGHT);
        gameover = new GameOverScreen(WIDTH, HEIGHT);

    }

    /**
     * Runs the game
     */
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

    /**
     * Updates the game
     */
    public void update() {
        long updateStart = System.currentTimeMillis();

        //debugPanel.setLabel(3, ""+(int)Math.sqrt((double)Game.ticks));
        //debugPanel.addLabel(0, ""+ticks % 50);
        //gamestate = 1 when the game is being played
        if (gameState == 1) {

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
        if (gameState == 2) {
            gameover.Update();
        }
        //game state becomes 55 before it become 1 so that we can reset the game
        if (gameState == 55) {
            try {
                resetGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameState = 1;
        }
        long timePassed = System.currentTimeMillis() - updateStart;
        debugPanel.setLabel(3, "<html> Ticks: " + ticks % 50
                + "<br>ms: " + timePassed
                + "<br>locX: " + screen.getX()
                + "<br>locY: " + screen.getY()
                + "</html>");
        
        System.out.println("Jim");

        
    }

    /**
     * Renders the game
     */
    public void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        // Draw the background, mobs, and player; last is on top
        if (gameState == 1) {
            screen.drawBackground(g);
            level.drawEntities(g);
            player.draw(g);
        }
        if (gameState == 2) {
            gameover.drawBackground(g);
        }

        g.dispose();
        bufferStrategy.show();

    }

    /**
     * Resets the game
     *
     * @throws IOException
     */
    private void resetGame() throws IOException {
        buttonsPressed = new Buttons();
        buttonPressed = "";
        for (int i = 0; i < playerDirs.length; i++) {
            playerDirs[i] = false;
        }

        init();
    }

    /**
     * Sets an array of button presses
     * <p>
     * I doubt we need this
     */
    public void ButtonPressed() {
        playerDirs[0] = Buttons.up;
        playerDirs[1] = Buttons.down;
        playerDirs[2] = Buttons.left;
        playerDirs[3] = Buttons.right;
        playerDirs[4] = Buttons.space;
        playerDirs[5] = Buttons.projUp;
        playerDirs[6] = Buttons.projDown;
        playerDirs[7] = Buttons.projLeft;
        playerDirs[8] = Buttons.projRight;
    }

    /**
     * Returns the object containing the current level
     *
     * @return
     */
    public static Level getLevel() {
        return level;
    }

    /**
     * Sets the level on which we are
     *
     * @param level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Map width. How is this different from width?
     *
     * @return width
     */
    public static int getMapWidth() {
        return MAP_WIDTH;
    }

    /**
     * Map height. How is this different from height?
     *
     * @return height
     */
    public static int getMapHeight() {
        return MAP_HEIGHT;
    }

    /**
     * Get width. Probably local?
     *
     * @return width
     */
    public int getWidth() {
        return WIDTH;
    }

    /**
     * Get height. Probably local?
     *
     * @return height
     */
    public int getHeight() {
        return HEIGHT;
    }

    /**
     * Statically return the map's width (width of the screen, local)
     *
     * @return width
     */
    public static int getStaticWidth() {
        return WIDTH;
    }

    /**
     * Statically returns the local height
     *
     * @return height
     */
    public static int getStaticHeight() {
        return HEIGHT;
    }

    /**
     * Sets debug text
     *
     * @param Loc location to place the text
     * @param text text
     */
    public static void setDebugText(int Loc, String text) {
        debugPanel.setLabel(Loc, text);
    }

    /**
     * Number of frames that have been rendered since start
     *
     * @return ticks
     */
    public static int getTicks() {
        return ticks;
    }

    /**
     * The state of the game
     * <p>
     * TODO: list game states here
     *
     * @return game state
     */
    public static int getGameState() {
        return gameState;
    }

    /**
     * Sets the game state
     *
     * @param gameState
     */
    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

    /**
     * Returns the player object
     *
     * @return player
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Returns the absolute width. Width of the map in tiles, the entire field
     *
     * @return width
     */
    public static int getAbsolute_MapWidth() {
        return absolute_MapWidth;
    }

    /**
     * Returns absolute height. Entire field, in tile
     *
     * @return height
     */
    public static int getAbsolute_MapHeight() {
        return absolute_MapHeight;
    }

    /**
     * Gets the screen object
     *
     * @return the screen
     */
    public static Screen getScreen() {
        return screen;
    }

    /**
     * Returns an array of the buttons which have been pressed.
     * <p>
     * I don't think we need this because buttons are public static anyway
     *
     * @return the buttons object
     */
    public static Buttons getButtonsPressed() {
        return buttonsPressed;
    }

}
