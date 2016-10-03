package buttons;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;

public class HelpButton extends Inter {

	public HelpButton(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		super(mainGame, input, x, y, width, height, type);
		group = 3;
	}
	
	public void updateButton() {
		if(type == 0) { //if the help button with the question mark is clicked, then open up the help interface and the side buttons
			if(mouseClicked == true && clickCounter == 0) {
				if(getGame().helpbuttons.get(1).visible == true) {
					getGame().helpbuttons.get(1).visible = false;
					getGame().helpbuttons.get(2).visible = false;
					getGame().helpbuttons.get(3).visible = false;
					getGame().helpbuttons.get(4).visible = false;
				} else if(getGame().helpbuttons.get(1).clicked == false) {
					getGame().helpbuttons.get(1).visible = true;
					getGame().helpbuttons.get(2).visible = true;
					getGame().helpbuttons.get(3).visible = true;
					getGame().helpbuttons.get(4).visible = true;
				}
				clickCounter = 1;
			} else if(mouseClicked == false) {
				clickCounter = 0;
			}
		} else if(type == 1) { //if one of the blue buttons on the help interface is pressed
			if(mouseClicked == true && clickCounter == 0) {
				if(clicked == false) {
					getGame().helpbuttons.get(2).clicked = false; //to make the other buttons's clicked variable false
					getGame().helpbuttons.get(3).clicked = false;
					getGame().helpbuttons.get(4).clicked = false;
					clicked = true;
				} else if(clicked == true) {
					clicked = false;
				}
				clickCounter = 1; // again, to prevent the button from being clicked multiple times in one mouse press
			} else if(mouseClicked == false) {
				clickCounter = 0;
			}
		}
	}
}
