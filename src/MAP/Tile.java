package MAP;

import java.awt.image.BufferedImage;

public class Tile {
	private BufferedImage image;
	
	Tile(BufferedImage subImage) {
		this.image = subImage;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
