package folder.tile;

import folder.Screen;
import folder.Sprite;


public class GrassTile extends Tile {
	
	public GrassTile (Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y,  Screen screen) {
		screen.renderTile(x << 4, y << 4,  this); //x and y are currently referring to pixel positions NOT tiles!
	}

}