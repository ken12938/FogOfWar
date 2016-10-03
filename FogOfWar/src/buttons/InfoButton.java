package buttons;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;

public class InfoButton extends Inter {

	public InfoButton(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		super(mainGame, input, x, y, width, height, type);
		group = 7;
	}
	
	public void updateButton() {
		if(type == 0) {
			if(getGame().selectionbuttons.get(2).mouseOver == true) {
				visible = true;
			} else {
				visible = false;
			}
		} else if(type == 1) {
			if(getGame().selectionbuttons.get(3).mouseOver == true) {
				visible = true;
			} else {
				visible = false;
			}
		} else if(type == 2) {
			if(getGame().selectionbuttons.get(4).mouseOver == true) {
				visible = true;
			} else {
				visible = false;
			}
		} else if(type == 3) {
			if(getGame().selectionbuttons.get(5).mouseOver == true) {
				visible = true;
			} else {
				visible = false;
			}
		} else if(type == 4) {
			if(getGame().selectionbuttons.get(6).mouseOver == true) {
				visible = true;
			} else {
				visible = false;
			}
		} else if(type == 5) {
			if(getGame().selectionbuttons.get(8).mouseOver == true) {
				visible = true;
			} else {
				visible = false;
			}
		}
		
		//if(getGame().selecting == false) {
		//	visible = false;
		//}
	}
}
