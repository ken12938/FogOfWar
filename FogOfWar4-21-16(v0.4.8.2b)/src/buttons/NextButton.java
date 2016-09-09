package buttons;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;

public class NextButton extends Inter {

	public NextButton(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		super(mainGame, input, x, y, width, height, type);
		group = 2;
	}
	
	public void updateButton() {
		if(type == 0) { //"Next" button on the unit count part of the interface
			if(mouseClicked == true) {
				clicked = true;
				if(getGame().unitCountSequence == 0 && clickCounter == 0) {
					getGame().unitCountSequence = 1;
				} else if(getGame().unitCountSequence == 1 && clickCounter == 0) {
					getGame().unitCountSequence = 2;
				} else if(getGame().unitCountSequence == 2 && clickCounter == 0) {
					getGame().unitCountSequence = 0;
				}
				clickCounter = 1;
			} else if(mouseClicked == false) {
				clicked = false;
				clickCounter = 0;
			}
			
			if(getGame().selecting == false) {
				visible = true;
			} else if(getGame().selecting == true) {
				visible = false;
			}
		} else if(type == 1) { //"Next" button on the unit count part of the interface
			if(mouseClicked == true) {
				clicked = true;
				if(getGame().unitPlacementSequence == 0 && clickCounter == 0) {
					getGame().unitPlacementSequence = 1;
				} else if(getGame().unitPlacementSequence == 1 && clickCounter == 0) {
					getGame().unitPlacementSequence = 0;
				}
				clickCounter = 1;
			} else if(mouseClicked == false) {
				clicked = false;
				clickCounter = 0;
			}
			
			if(getGame().selecting == false) {
				visible = false;
			} else if(getGame().selecting == true) {
				visible = true;
			}
		}
	}
}
