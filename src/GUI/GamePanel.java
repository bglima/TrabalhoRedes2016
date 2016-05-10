package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import MAP.TileMap;
import PLAYER.Player;

public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 416; // 13 tiles * 32 pixels = 416
	public static final int HEIGHT = 416;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	
	private TileMap tileMap;
	private Player player;
	
	GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		requestFocus();
	}
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
		registerKeys();
	}
	
	public void registerKeys() {
		// Pressed KEY_RIGHT
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "KEY_RIGHT");
		this.getActionMap().put("KEY_RIGHT",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveRight();
			}
		});
		// Pressed KEY_UP
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "KEY_UP");
		this.getActionMap().put("KEY_UP",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveUp();
			}
		});
		// Pressed KEY_LEFT
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "KEY_LEFT");
		this.getActionMap().put("KEY_LEFT",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveLeft();
			}
		});
		// Pressed KEY_DOWN
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "KEY_DOWN");
		this.getActionMap().put("KEY_DOWN",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveDown();
			}
		});
		this.requestFocus();
		
	}
	
	public void tell() {
		System.out.println("Hello");
	}
	
	@Override
	public void run() {
		init();
		long startTime;
		long urdTime;
		long waitTime;
		
		while(running) {
			startTime = System.nanoTime();
			
			update();
			render();
			draw();
			
			urdTime = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - urdTime;
			
			try {
				Thread.sleep(waitTime);
			} catch (Exception e){
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void init() {
		running = true;
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		tileMap = new TileMap("E:/ThreeJSWorkspace/TrabalhoRedes/src/MAP/testMap.txt", 32);
		tileMap.loadTiles("E:/ThreeJSWorkspace/TrabalhoRedes/src/MAP/tileSet.png");
		player = new Player(tileMap);
		player.setX(16);
		player.setY(16);
	}

	private void update() {
		tileMap.update();
		player.update();
	}
	
	private void render() {
		tileMap.draw(g);
		player.draw(g);
	}
	
	private void draw() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
}
