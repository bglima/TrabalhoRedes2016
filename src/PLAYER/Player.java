package PLAYER;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import MAP.TileMap;

public class Player {
	private int size;
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
		4 = down    */
	private int moveState; 
	private int moveDirection;
	private double moveSpeed;
	private boolean keepMoving;
	private int animationDelay;
	
	private TileMap tileMap;
	private Animation animation;
	private BufferedImage[] walkingRight;
	private BufferedImage[] walkingUp;
	private BufferedImage[] walkingLeft;
	private BufferedImage[] walkingDown;
	private BufferedImage[][] idle;
	
	public Player(TileMap tm) {
		size = 32;
		tileMap = tm;
		keepMoving = false;
		moveState = 0;
		moveDirection = 1;
		moveSpeed = 5;
		animationDelay = 200;
		
		try {
			walkingRight = new BufferedImage[3];
			walkingUp    = new BufferedImage[3];
			walkingLeft  = new BufferedImage[3];
			walkingDown  = new BufferedImage[3];
			idle = new BufferedImage[4][];
			idle[0] = new BufferedImage[1];
			idle[1] = new BufferedImage[1];
			idle[2] = new BufferedImage[1];
			idle[3] = new BufferedImage[1];		
			BufferedImage sprite = ImageIO.read(new File("src/PLAYER/char1.png"));
			for ( int i = 0; i < 3; i++ ) { walkingRight[i] = sprite.getSubimage( i * size, 2 * size, size, size); };
			for ( int i = 0; i < 3; i++ ) { walkingUp[i]    = sprite.getSubimage( i * size, 3 * size, size, size); };
			for ( int i = 0; i < 3; i++ ) { walkingLeft[i]  = sprite.getSubimage( i * size, 1 * size, size, size); };
			for ( int i = 0; i < 3; i++ ) { walkingDown[i]  = sprite.getSubimage( i * size, 0 * size, size, size); };
			idle[0][0] = sprite.getSubimage( size, 2 * size, size, size);
			idle[1][0] = sprite.getSubimage( size, 3 * size, size, size);
			idle[2][0] = sprite.getSubimage( size, 1 * size, size, size);
			idle[3][0] = sprite.getSubimage( size, 0 * size, size, size);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// Instatiating a player aimation;
		animation = new Animation();
	}
	
	public void setX(int i) { x = i; };
	public void setY(int i) { y = i; };
	
	public void moveRight() { 
		if( moveState == 0) {
			targetX = x + size;
			moveState = 1;
			moveDirection = 1;
		} else if ( moveState == 1 ) {
			keepMoving = true;
		}
	};
	public void moveUp() { 
		if( moveState == 0) {
			targetY = y - size;
			moveState = 2;
			moveDirection = 2;
		} else if ( moveState == 2 ) {
			keepMoving = true;
		}
	};
	public void moveLeft() { 
		if( moveState == 0) {
			targetX = x - size;
			moveState = 3;
			moveDirection = 3;
		} else if ( moveState == 3) {
			keepMoving = true;
		}
	};
	public void moveDown() { 
		if( moveState == 0) {
			targetY = y + size;
			moveState = 4;
			moveDirection = 4;
		} else if ( moveState == 4 ) {
			keepMoving = true;
		}
	};

	
	public void update() {
		switch (moveState) {
			case 0:
				animation.setFrames(idle[moveDirection - 1]);
				animation.setDelay(-1);
				break;
			case 1:	// RIGHT
				animation.setFrames(walkingRight);
				animation.setDelay(animationDelay);
				x += moveSpeed;
				if( x >= targetX ) {
					if(keepMoving == false) {
						x = targetX;
						moveState = 0;
					} else {
						targetX += size;
						keepMoving = false;
					}
				}
				break;	
			case 2:	// UP
				animation.setFrames(walkingUp);
				animation.setDelay(animationDelay);
				y -= moveSpeed;
				if( y <= targetY ) {
					if(keepMoving == false) {
						y = targetY;
						moveState = 0;
					} else {
						targetY -= size;
						keepMoving = false;
					}
				}
				break;
			case 3:	// LEFT
				animation.setFrames(walkingLeft);
				animation.setDelay(animationDelay);
				x -= moveSpeed;
				if( x <= targetX ) {
					if(keepMoving == false) {
						x = targetX;
						moveState = 0;
					} else {
						targetX -= size;
						keepMoving = false;
					}
				}
				break;
			case 4:	// DOWN
				animation.setFrames(walkingDown);
				animation.setDelay(animationDelay);
				y += moveSpeed;
				if( y >= targetY ) {
					if(keepMoving == false) {
						y = targetY;
						moveState = 0;
					} else {
						targetY += size;
						keepMoving = false;
					}
				}
				break;
		}
		animation.update();
	}
	
	public void draw(Graphics g) {
		int tx = tileMap.getX();
		int ty = tileMap.getY();
		g.drawImage( animation.getImage(), (int) (tx + x - size / 2), (int) (ty + y - size / 2), null);
	}
}
