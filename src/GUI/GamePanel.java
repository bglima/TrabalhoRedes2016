package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import MAP.TileMap;

public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 400;
	public static final int HEIGHT = 400;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	
	private TileMap tileMap;
	
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
		
		tileMap = new TileMap("E:/ThreeJSWorkspace/TrabalhoRedes/src/MAP/testMap.txt", 20);
	}

	private void update() {
		tileMap.update();
	}
	
	private void render() {
		tileMap.draw(g);
	}
	
	private void draw() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
}
