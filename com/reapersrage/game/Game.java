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
	
	//test update
	//test
	//test

    //width and height of game screen
	static final int HEIGHT = 240;
    static final int WIDTH = HEIGHT * 16 / 9;
    static final int SCALE = 3;
    
    static final String NAME = "DOODLE ARENA WARS 2015";
    private static Keyboard key;
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
    private boolean running = false;
    private int tickCount;
    private Screen screen;
    private Level level;
    private Player player;
    private Thread gameThread;
    private int xScroll, yScroll;

    public static void main(String[] args) {
        Game game = new Game();
        
        //not being used
        game.setMinimumSize(new Dimension((WIDTH - WIDTH%16)*SCALE, HEIGHT * SCALE));
        game.setMaximumSize(new Dimension((WIDTH - WIDTH%16)*SCALE, HEIGHT * SCALE));
        game.setPreferredSize(new Dimension((WIDTH - WIDTH%16)*SCALE, HEIGHT * SCALE));

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
        //new Thread(this).start();
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
       /* try {
            screen = new Screen(WIDTH, HEIGHT,
                    new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    
    public void run() {
    	int frames = 0;
        int ticks = 0;
        int frameRate = 50;
        
        init();
    	
    	 while (running) {
    		 long frameStart = System.currentTimeMillis();
    		 ticks++;
             tick();
             frames++;
             render();
             
             long frameLength = System.currentTimeMillis() - frameStart;
             
             if(frameLength < frameRate){
            	 try {
					gameThread.sleep(frameRate-frameLength);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
             }
             
    		 
    	 }
    	
    }

    /*public void run() {
        long lastTime = System.nanoTime();
        double unprocessed = 0D;
        double nsPerTick = 5000000000D / 60D;
        int frames = 0;
        int ticks = 0;
        long lastTimer1 = System.currentTimeMillis();
        
        

        init();

        while (running) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;
            while (unprocessed > 1) {
                ticks++;
                tick();
                unprocessed -= 1;
                shouldRender = true;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if (shouldRender) {
                frames++;
                render();
            }
            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                System.out.println(ticks + " Ticks " + frames + " Frames");
                frames = 0;
                ticks = 0;
            }
        }
    }*/

    public void tick() {
        tickCount++;
        key.update();
        player.update();
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            requestFocus();
            return;
        }
        screen.clear();
        //screen.render(xScroll, yScroll);
        //level.render(player.getX(), player.getY(), screen); This will move the entire level with directional button
        level.render(0, 0, screen); // Render the level
        player.render(screen); //Render the Player

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = screen.getPixel(i);
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Verdana", 0, 50));
        g.drawString("X: " + player.getX() + " Y: " + player.getY() + " Dir: " + player.getDir(), 700, 650); //DEBUG
        g.dispose();
        bs.show();
    }
}
