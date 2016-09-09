package buttons;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;

public class Button extends Inter {

	public Button(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		super(mainGame, input, x, y, width, height, type);
		group = 1;
	}
	
	public void updateButton() {
		if(type == 0) {
			if(mouseClicked == true && clickCounter == 0 && getGame().selecting == false) {
				if(clicked == false) {
					getGame().buttons.get(0).clicked = false; //to make the other buttons's clicked variable false
					getGame().buttons.get(1).clicked = false;
					getGame().buttons.get(2).clicked = false;
					clicked = true;
				} else if(clicked == true) {
					clicked = false;
				}
				clickCounter = 1; // again, to prevent the button from being clicked multiple times in one mouse press
			} else if(mouseClicked == false) {
				clickCounter = 0;
			}
		} else if(type == 1 || type == 2) { //button type 2 is the cancel button, button type 3 is the end turn button
			if(mouseClicked == true) {
				if(type == 1 && getGame().selecting == false) {
					getGame().buttons.get(0).clicked = false; //cancel button deselects all other buttons
					getGame().buttons.get(1).clicked = false;
					getGame().buttons.get(2).clicked = false;
					
					if(getGame().getLevel().getTurn() == 1) {
						for(int i = 0; i < getGame().getLevel().getRedTeam().size(); i++) {
							getGame().getLevel().getRedTeam().get(i).selected = false;
							getGame().getLevel().getRedTeam().get(i).action = 0;
						}
					} else if(getGame().getLevel().getTurn() == 2) {
						for(int i = 0; i < getGame().getLevel().getGreenTeam().size(); i++) {
							getGame().getLevel().getGreenTeam().get(i).selected = false;
							getGame().getLevel().getGreenTeam().get(i).action = 0;
						}
					}
					clicked = true;
				} else if(type == 1) { //resets lastKey even if it is still in placement phase
					getKeyboard().lastKey = 0;
				} else if(type == 2) {
					clicked = true;
				}
			} else if(mouseClicked == false) {
				clicked = false;
			}
		}
	}
}
