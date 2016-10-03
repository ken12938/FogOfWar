package buttons;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;

public class SelectionButton extends Inter {

	public SelectionButton(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		super(mainGame, input, x, y, width, height, type);
		group = 4;
	}
	
	public void updateButton() {
		if(type == 0) { //selection panel's 5 buttons that correspond to the first 5 units
			if(getGame().unitPlacementSequence == 0 && getGame().selecting == true) {
				visible = true;
				
				if(mouseClicked == true && clickCounter == 0) {
					if(clicked == false) {
						getGame().selectionbuttons.get(2).clicked = false;
						getGame().selectionbuttons.get(3).clicked = false;
						getGame().selectionbuttons.get(4).clicked = false;
						getGame().selectionbuttons.get(5).clicked = false;
						getGame().selectionbuttons.get(6).clicked = false;
						getGame().selectionbuttons.get(8).clicked = false;
						clicked = true;
					} else if(clicked == true) {
						clicked = false;
					}
					clickCounter = 1; // again, to prevent the button from being clicked multiple times in one mouse press
				} else if(mouseClicked == false) {
					clickCounter = 0;
				}
			} else {
				visible = false;
			}
		} else if(type == 1) { //selection panel's reset button
			if(mouseClicked == true && clickCounter == 0) {
				getGame().selectionbuttons.get(2).clicked = false; //cancel button deselects all other buttons
				getGame().selectionbuttons.get(3).clicked = false;
				getGame().selectionbuttons.get(4).clicked = false;
				getGame().selectionbuttons.get(5).clicked = false;
				getGame().selectionbuttons.get(6).clicked = false;
				getGame().selectionbuttons.get(8).clicked = false;
					
				if(getGame().selectingTurn == 1) { //removes all units on the team
					for(int i = 0; i < getGame().getLevel().getRedTeam().size(); i++) {
						getGame().getLevel().getRedTeam().get(i).changeHealth(1000, "shell");
						getGame().redMoney = 1600; //resets the money
					}
				} else if(getGame().selectingTurn == 2) {
					for(int i = 0; i < getGame().getLevel().getGreenTeam().size(); i++) {
						getGame().getLevel().getGreenTeam().get(i).changeHealth(1000, "shell");
						getGame().greenMoney = 1600;
					}
				}
				getGame().getLevel().removeUnits(); //removes the units on that team
				getGame().getLevel().clearPixels(); //removes any vision those units had
				
				clicked = true;
			} else if(mouseClicked == false) {
				clicked = false; 
			}
			
			if(getGame().selecting == true) {
				visible = true;
			} else if(getGame().selecting == false) {
				visible = false;
			}
		} else if(type == 2) { //selection panel's 5 buttons that correspond to the next 5 units
			if(getGame().unitPlacementSequence == 1 && getGame().selecting == true) {
				visible = true;
				
				if(mouseClicked == true && clickCounter == 0) {
					if(clicked == false) {
						getGame().selectionbuttons.get(2).clicked = false;
						getGame().selectionbuttons.get(3).clicked = false;
						getGame().selectionbuttons.get(4).clicked = false;
						getGame().selectionbuttons.get(5).clicked = false;
						getGame().selectionbuttons.get(6).clicked = false;
						getGame().selectionbuttons.get(8).clicked = false;
						clicked = true;
					} else if(clicked == true) {
						clicked = false;
					}
					clickCounter = 1; // again, to prevent the button from being clicked multiple times in one mouse press
				} else if(mouseClicked == false) {
					clickCounter = 0;
				}
			} else {
				visible = false;
			}
		}
		
		//if(getGame().selecting == false) {
		//	visible = false;
		//}
	}
}
