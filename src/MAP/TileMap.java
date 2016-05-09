package MAP;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javax.imageio.ImageIO;

public class TileMap {
	private int x;
	private int y;
	
	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	
	private BufferedImage tileSet;
	private Tile[][] tiles;
	
	
	public TileMap(String s, int tileSize) {
		this.tileSize = tileSize;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(s));
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];
			String delimiters = "\\s+";
			for(int row = 0; row < mapHeight; row++) {
				String line = br.readLine();
				String[] tokens = line.split(delimiters);
				for( int col = 0; col < mapWidth; col++) {
					map[row][col] = Integer.parseInt(tokens[col]);
				}
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadTiles(String s) {
		try {
			tileSet = ImageIO.read(new File(s));
			int numTilesX = tileSet.getWidth()  / tileSize;
			int numTilesY = tileSet.getHeight() / tileSize;
			tiles = new Tile[numTilesY][numTilesX];
			
			BufferedImage subImage;
			for(int row = 0; row < numTilesY; row++) {
				for(int col = 0; col < numTilesX; col++) {
					subImage = tileSet.getSubimage(col*tileSize, row*tileSize, tileSize, tileSize);
					tiles[row][col] = new Tile(subImage);
				}
			}
				
				
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void update() {
		
	}
	
	public void draw(Graphics2D g) {
		for(int row = 0; row < mapHeight; row++) {
			for(int col = 0; col < mapWidth; col++) {
				int rc = map[row][col];
				
				if( rc == 1 ) {
					g.setColor(Color.BLACK);
				}
				if( rc == 0 ) {
					g.setColor(Color.WHITE);
				}
				g.fillRect(x + col * tileSize,  y + row * tileSize, tileSize, tileSize);		
			}
		}
	}
}
