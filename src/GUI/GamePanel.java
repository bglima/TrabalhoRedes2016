package GUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import APP.Client;
import MAP.TileMap;
import PLAYER.Player;

public class GamePanel extends JPanel implements Runnable {
	public static final int WIDTH = 416; // 13 tiles * 32 pixels = 416
	public static final int HEIGHT = 416;
	
	private Client client;
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private int FPS = 30;
	private int targetTime = 1000/FPS;
	
	private TileMap tileMap;
	private Player player;
	private Map <String, Player> playersMap = new HashMap<String, Player>();
	
	private String playerName;
	private String charSet;
	
	public TileMap getTileMap() { return tileMap; }
	
	GamePanel( String playerName, String charSet, Client client) {
		super();
		this.playerName = playerName;
		this.charSet = charSet;
		this.client = client;
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
				client.sendControlMessage("moveRight,"+
										  player.getPlayerName()+","+
										  player.getTargetX()+","+
										  player.getTargetY());
			}
		});
		// Pressed KEY_UP
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "KEY_UP");
		this.getActionMap().put("KEY_UP",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveUp();
				client.sendControlMessage("moveUp,"+
						  player.getPlayerName()+","+
						  player.getTargetX()+","+
						  player.getTargetY());
			}
		});
		// Pressed KEY_LEFT
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "KEY_LEFT");
		this.getActionMap().put("KEY_LEFT",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveLeft();
				client.sendControlMessage("moveLeft,"+
						  player.getPlayerName()+","+
						  player.getTargetX()+","+
						  player.getTargetY());
			}
		});
		// Pressed KEY_DOWN
		this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "KEY_DOWN");
		this.getActionMap().put("KEY_DOWN",	new AbstractAction() {
			public void actionPerformed(ActionEvent e) {	
				player.moveDown();
				client.sendControlMessage("moveDown,"+
						  player.getPlayerName()+","+
						  player.getTargetX()+","+
						  player.getTargetY());
			}
		});
		this.requestFocus();
	}
	
	// Function that adds a new player to the game
	public void addPlayer(Player player) {
		// Don't add itself again
		if( player.getPlayerName().equals(playerName)) return;
		playersMap.put(player.getPlayerName(), player);
	}
	
	// Function that moves an existing player that already exists in the game
	public void movePlayer(String playerName, String direction) {
		if( player.getPlayerName().equals(playerName)) return;
		Player playerToMove = playersMap.get(playerName);
		switch(direction) {
			case "moveRight":
				playerToMove.moveRight();
				break;
			case "moveUp":
				playerToMove.moveUp();
				break;
			case "moveLeft":
				playerToMove.moveLeft();
				break;
			case "moveDown":
				playerToMove.moveDown();
				break;
		}
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
		
		tileMap = new TileMap("src/MAP/testMap.txt", 32);
		tileMap.loadTiles("src/MAP/tileSet.png");
		player = new Player(tileMap, playerName, charSet, 16 + 64, 16 + 64, 3);
		playersMap.put(playerName, player);
		
		client.setPlayer(player);
		client.sendControlMessage(	"addPlayer"+","+
									player.getPlayerName()+","+
									player.getCharSet()+","+
									player.getX()+","+
									player.getY()+","+
									player.getMoveDirection());
		/*
		out.println("control:newPlayer ");
		out.println("control:removePlayer ");
		out.println("control:playerName,moveRight")
		out.println("control:playerName,moveUp")
		out.println("control:playerName,moveLeft")
		out.println("control:playerName,moveDown")
		*/
	}
	
	private void update() {
		tileMap.update();
		for( String name : playersMap.keySet() ) {
			(playersMap.get(name)).update();
		}
	}
	
	private void render() {
		tileMap.draw(g);
		for( String name : playersMap.keySet() ) {
			(playersMap.get(name)).draw(g);
		}
	}
	
	private void draw() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
}
