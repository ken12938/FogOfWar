package folder;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet { //constructs a SpriteSheet object which keeps track of types of images.
	
	private String path;
	public final int SIZE;
	public int[] pixels;
	

	public static SpriteSheet tiles = new SpriteSheet("/textures/spritesheet.png", 256); //tiles refers to a SPECIFIC spritesheet. it aint generic.
	public static SpriteSheet projectiles = new SpriteSheet("/textures/projectilesheet.png", 128); //stores projectile sprites
	public static SpriteSheet units = new SpriteSheet("/textures/unitsheet.png", 256);
	public static SpriteSheet interactives = new SpriteSheet("/textures/interactives.png", 256);
	public static SpriteSheet zombies = new SpriteSheet("/textures/unitsheet_zombies.png", 256);
	public static SpriteSheet largesprites = new SpriteSheet("/textures/largesprites.png", 256);
	public static SpriteSheet castles = new SpriteSheet("/textures/castles.png", 256);
	
	public SpriteSheet(String path, int size) {
		this.path = path; //filepath
		SIZE = size; //size of the spritesheet
		pixels = new int[SIZE*SIZE]; //contains rgb values for each position in the spritesheet
		load();
	}
	
	
	private void load() { //loading the SpriteSheet from paint.net. Spritesheets are grids with pictures inside each grid. Each grid is the size of a tile
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w); //converts the paint image into a pixel array that we can use
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	
	}
}
