package buttons;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;

public class SettingsButton extends Inter {

	public SettingsButton(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		super(mainGame, input, x, y, width, height, type);
		group = 5;
	}
	
	public void updateButton() {
		if(type == 0) {
			if(mouseClicked == true && clickCounter == 0) {
				if(getGame().settingsbuttons.get(1).visible == false) {
					getGame().settingsbuttons.get(1).visible = true;
					getGame().settingsbuttons.get(2).visible = true;
				} else if(getGame().settingsbuttons.get(1).visible == true) {
					getGame().settingsbuttons.get(1).visible = false;
					getGame().settingsbuttons.get(2).visible = false;
				}
				clickCounter = 1;
			} else if(mouseClicked == false) {
				clickCounter = 0;
			}
		} else if(type == 1) {
			if(mouseClicked == true && clickCounter == 0) {
				if(clicked == false) {
					clicked = true;
				} else if(clicked == true) {
					clicked = false;
				}
				clickCounter = 1;
			} else if(mouseClicked == false) {
				clickCounter = 0;
			}
		}
	}
}
