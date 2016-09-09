package folder;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpawnLevel extends Level {
		
	public SpawnLevel (Game mainGame, String path, Player player, Screen screen, Keyboard input){
		super(mainGame, path, player, screen, input);
	}
	

	protected void loadLevel (String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w); //writes the image from the path into into an integer array levelPixels.	
		} catch (IOException e) {
			e.printStackTrace(); // prints the other errors
			System.out.println("Exception! Could not load level file!");
		}
	
		addUnits(6);
	}
	

	protected void generateLevel() {

	}
	


}
