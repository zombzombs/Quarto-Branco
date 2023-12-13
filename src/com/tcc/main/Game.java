package com.tcc.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import com.tcc.entities.*;
import com.tcc.graficos.Doorg;
import com.tcc.graficos.Spritesheet;
import com.tcc.graficos.UI;
import com.tcc.graficos.dialog;
import com.tcc.world.Camera;
import com.tcc.world.World;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{


	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread; 
	private boolean isRunning = true;
	public static final int WIDTH = 240;
	public static final int HEIGHT = 160;
	public static final int SCALE = 3;
	
	public static int CUR_LEVEL = 1;
	public int MAX_LEVEL=2;
	private BufferedImage image;
	public static List<Enemy> enemies;
	public static List<Entity> entities;
	public static List<Bullet> bullets;
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public UI ui;
	public static String gameState = "MENU";
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	private boolean restartGame = false;
	public dialog Dialog;
	public static Doorg door;
	public Menu menu;

	public Game() {
		Sound.musicBackground.loop();
		rand = new Random();
		addKeyListener(this); 
		addMouseListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE,HEIGHT*SCALE));
		initFrame();
		//Inicializando Objetos.
		ui = new UI();
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		
		
		spritesheet = new Spritesheet("/Spritesheet.png");
		player = new Player(0,0,32,32,spritesheet.getSprite(0,64, 32, 32));
		entities.add(player);
		world = new World("/level1.png");
		
		door = new Doorg();
		Dialog = new dialog();
		menu = new Menu();
	}
	
	public void initFrame() {
		frame = new JFrame("Game #1");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		//frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
        isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		 try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		
		if(gameState == "NORMAL") {
			this.restartGame = false;
			for(int i = 0; i< entities.size(); i++) {
				Entity e = entities.get(i);
				e.tick();
			}
			for(int i = 0; i<bullets.size(); i++) {
				bullets.get(i).tick();
			}
			
			if(enemies.size() == 0) {
				//avanca para o proximo level
				CUR_LEVEL++;
				if(CUR_LEVEL > MAX_LEVEL) {
					CUR_LEVEL = 1;
				}
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			}
		}else if(gameState=="GAME_OVER") {
			//System.out.println("Game Over!");
			this.framesGameOver++;
			if(this.framesGameOver==30) {
				this.framesGameOver = 0;
				if(this.showMessageGameOver) 
					this.showMessageGameOver = false;
					else 
						this.showMessageGameOver = true;
			}
			if(restartGame) {
				this.restartGame = false;
				this.gameState = "NORMAL";
				CUR_LEVEL=1;
				String newWorld = "level"+CUR_LEVEL+".png";
				World.restartGame(newWorld);
			}
		}else if(gameState=="MENU") {
			menu.tick();
		}else if(gameState=="TEXT") {
			Dialog.tick();
		}else if(gameState=="DOOR") {
			door.tick();
		}
		
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		/*     Renderizacao do jogo     */
		//Renderiza o Mundo
		world.render(g);
		//Rendereiza as Entidades
		for(int i = 0; i< entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i<bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		
		
		/***/
		ui.render(g);
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		g.setColor(Color.white);	
		g.setFont(new Font("arial", Font.BOLD,17));
		g.drawString("AMMO: "+Game.player.ammo, 8, 450);
		if(gameState=="GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0,0,0,100));
			g2.fillRect(0, 0, WIDTH*SCALE, HEIGHT*SCALE);
			g2.setFont(new Font("arial", Font.BOLD,30));
			g2.setColor(new Color(255,255,255));
			g2.drawString("GAME OVER", 290, (HEIGHT*SCALE)/2);
			g2.setFont(new Font("arial", Font.BOLD,20));
			if(showMessageGameOver) {
				g2.drawString(">Precione Enter para reiniciar<", 240, (HEIGHT*SCALE)/2 +30);
			}
			
		}else if(gameState=="MENU") {
			menu.render(g);
		}else if(gameState=="TEXT") {
			Dialog.render(g);
		}else if(gameState=="DOOR") {
			door.render(g);
		}
		bs.show();
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		requestFocus();
		while(isRunning) {
		   long now = System.nanoTime();
		   delta+= (now - lastTime) / ns;
		   lastTime = now;
		    if (delta >= 1) {
			   tick();
			   render();
			   frames++;
			   delta--;
		   }
		    if(System.currentTimeMillis() - timer >= 1000) {
		    	System.out.println("FPS: "+ frames);
		    	frames = 0;
		    	timer += 1000;
		    	
		    }
		    
		}
		stop();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT
				||e.getKeyCode() == KeyEvent.VK_D){
			player.right = true;
			
			if(gameState=="DOOR") {
				door.right = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT
				||e.getKeyCode() == KeyEvent.VK_A){
			player.left = true;
			
			if(gameState=="DOOR") {
				door.left = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP
				||e.getKeyCode() == KeyEvent.VK_W){
			player.up = true;
			
			if(gameState=="MENU") {
				menu.up = true;
			}
			
			if(gameState=="DOOR") {
				door.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN
				||e.getKeyCode() == KeyEvent.VK_S){
			player.down = true;
			
			if(gameState=="MENU") {
				menu.down = true;
			}
			
			if(gameState=="DOOR") {
				door.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_J) {
			player.shoot = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
			player.speed = 3.0;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(gameState =="GAME_OVER") {
			restartGame = true;
			}
			if(gameState=="MENU") {
				menu.enter = true;
			}
			if(gameState=="TEXT") {
				Dialog.enter = true;
			}
			if(gameState=="DOOR") {
				door.enter = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gameState ="MENU";
			menu.pause = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_E) {
			if(player.isCollindingText) {
				gameState ="TEXT";
			}	
			if(player.isCollindingDoor) {
				gameState ="DOOR";
			}	
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT
				||e.getKeyCode() == KeyEvent.VK_D){
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT
				||e.getKeyCode() == KeyEvent.VK_A){
			player.left = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP
				||e.getKeyCode() == KeyEvent.VK_W){
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN
				||e.getKeyCode() == KeyEvent.VK_S){
			player.down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_J) {
			player.shoot = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
			player.speed = 1.4;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		player.mouseShoot = true;
		player.mx = (e.getX()/3);
		player.my = (e.getY()/3);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}