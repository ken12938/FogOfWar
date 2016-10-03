package folder.tile;

import folder.Screen;
import folder.Sprite;


public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y,  Screen screen) {
		screen.renderTile(x << 4, y << 4, this);//x and y are currently referring to pixel positions NOT tiles!
	}
	
	public boolean isSolid(){ //voidtile is the map boundary.
		return true;
	}
}
