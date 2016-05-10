package PLAYER;

import java.awt.Color;
import java.awt.Graphics;

import MAP.TileMap;

public class Player {
	private int size = 32;
	private double x;
	private double y;
	private double targetX;
	private double targetY;
	
	/*
		moveState:
		0 = not moving
		1 = right
		2 = up
		3 = left
		4 = down
	*/
	private int moveState = 0; 
	private double moveSpeed = 10;
	
	private TileMap tileMap;
	
	public Player(TileMap tm) {
		tileMap = tm;
	}
	
	public void setX(int i) { x = i; };
	public void setY(int i) { y = i; };
	
	public void moveRight() { 
		if( moveState == 0) {
			targetX = x + 32;
			moveState = 1;
			System.out.println("Moving right");
		}
	};
	public void moveUp() { 
		if( moveState == 0) {
			targetY = y - 32;
			moveState = 2;
			System.out.println("Moving up");
		}
	};
	public void moveLeft() { 
		if( moveState == 0) {
			targetX = x - 32;
			moveState = 3;
			System.out.println("Moving left");
		}
	};
	public void moveDown() { 
		if( moveState == 0) {
			targetY = y + 32;
			moveState = 4;
			System.out.println("Moving down");
		}
	};

	
	public void update() {
		switch (moveState) {
			case 1:	// RIGHT
				x += moveSpeed;
				if( x >= targetX ) {
					x = targetX;
					moveState = 0;
				}
				break;	
			case 2:	// UP
				y -= moveSpeed;
				if( y <= targetY ) {
					y = targetY;
					moveState = 0;
				}
				break;
			case 3:	// LEFT
				x -= moveSpeed;
				if( x <= targetX ) {
					x = targetX;
					moveState = 0;
				}
				break;
			case 4:	// DOWN
				y += moveSpeed;
				if( y >= targetY ) {
					y = targetY;
					moveState = 0;
				}
				break;
		}
	}
	
	public void draw(Graphics g) {
		int tx = tileMap.getX();
		int ty = tileMap.getY();
		g.setColor(Color.RED);
		g.fillRect( (int) (tx + x - size / 2), (int) (ty + y - size / 2), size, size);	
	}
}
