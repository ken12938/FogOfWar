package buttons;
import java.awt.Rectangle;

import folder.Game;
import folder.Keyboard;
import folder.Mouse;
import folder.Screen;
import units.Unit;

public class Inter extends Rectangle {
	
	private Keyboard input;
	private Game game;
	public int x;
	public int y;
	public int xFinal; //the original values of x and y, used for determining the position of the interface in terms of the mouse coordinates
	public int yFinal;
	public int width;
	public int height;
	public int clickCounter; //simply prevents a button from being clicked multiple times in one mouse click
	
	public boolean clicked; //determines whether the button is clicked or not
	public boolean visible; //determines whether the button is visible or not (only applies to buttons that can be turned on and off)
	public boolean mouseClicked;
	public boolean mouseOver;

	public int group; //determines which group it is in (menubuttons, selectionbuttons, interfaces)
	public int type; //determines what type of button it is
	
	public int xVelocity = 0; //Velocity variables compensate for the scrolling of the interface when edge panning
	public int yVelocity = 0;
	
	protected int dir = 0;
	
	public Inter(Game mainGame, Keyboard input, int x, int y, int width, int height, int type) {
		game = mainGame;
		this.input = input;
		this.x = x;
		this.y = y;
		this.xFinal = x;
		this.yFinal = y;
		this.width = width;
		this.height = height;
		this.type = type;
		this.clickCounter = 0;
		
		clicked = false; //whether the interface is clicked or not
		visible = false;	
		group = 0;
		
	}
	
	public void move(int xa, int ya) { //xa and ya track the change in position
		
		
		if ( xa != 0 && ya != 0) { //this says if we are moving in two axis' at once, this separates it into two different commands.
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		x += xa;
		y += ya;
	}
	
	public void update() { // green icon on the left indicates it is overriding an update method.
		int xa = 0, ya = 0; 
		if(game.getPlayer().x < -4 && game.getLevel().getXScroll() >= -100){ //you can only scroll 100 pixels past the side of the level
			xa -= 3;
			xVelocity = -3;
		}else if(game.getPlayer().x + 28 > game.getScreen().width && game.getLevel().getXScroll() <= game.getLevel().getWidth()*16 - 100){
			xa += 3;
			xVelocity = 3;
		} else {
			xVelocity = 0;
		}
		if(game.getPlayer().y < -4 && game.getLevel().getYScroll() >= -100){
			ya -= 3;
			yVelocity = -3;
		}else if(game.getPlayer().y + 20 > game.getScreen().height && game.getLevel().getYScroll() <= game.getLevel().getHeight()*16 - 100){
			ya += 3;
			yVelocity = 3;
		} else {
			yVelocity = 0;
		}
		
		if (xa != 0 || ya!= 0) move(xa, ya);
		
		mouseClicked = Mouse.getButton() == 1 && Mouse.getX() >= Game.scale*(xFinal) && Mouse.getX() < Game.scale*(xFinal+width) && Mouse.getY() >= Game.scale*(yFinal) && Mouse.getY() <= Game.scale*(yFinal + height) && visible == true;
		mouseOver = Mouse.getX() >= Game.scale*(xFinal) && Mouse.getX() < Game.scale*(xFinal+width) && Mouse.getY() >= Game.scale*(yFinal) && Mouse.getY() <= Game.scale*(yFinal + height) && visible == true;
		
		
		updateButton();
		
		return;
	}
	
	public void updateButton() { //updates the specific button stuff depending on what button it is, this method is different for each group of buttons
		
				//detects if the mouse is clicking on the interface
				/*boolean wButtonPressed = input.lastKey == 87; //detects if s is pressed, and so on for the other 2 booleans
				boolean qButtonPressed = input.lastKey == 81;
				boolean eButtonPressed = input.lastKey == 69;
				boolean rButtonPressed = input.lastKey == 82;*/
				
			if(type == 2) { //the small rectangle at the top right during selection phase
				if(game.selecting == true) {
					visible = true;
					/*game.selectionbuttons.get(2).visible = true;
					game.selectionbuttons.get(3).visible = true;
					game.selectionbuttons.get(4).visible = true;
					game.selectionbuttons.get(5).visible = true;
					game.selectionbuttons.get(6).visible = true;
					game.selectionbuttons.get(7).visible = true;*/
				} if(game.selecting == false) {
					visible = false;
					/*game.selectionbuttons.get(2).visible = false;
					game.selectionbuttons.get(3).visible = false;
					game.selectionbuttons.get(4).visible = false;
					game.selectionbuttons.get(5).visible = false;
					game.selectionbuttons.get(6).visible = false;
					game.selectionbuttons.get(7).visible = false;*/
				}
			} else if(type == 4 || type == 5) { //if the button value is in the 1000's, it is a menu type of button
				if(game.menu == true) {
					visible = true;
				} else if(game.menu == false) {
					visible = false;
				}
			} 
			
			//the line input.lastkey = 0; is used to prevent sButtonPressed and the other booleans from repeatedly activating since lastKey only takes into account the last key you pressed
			
			/*if(wButtonPressed == true) {
				game.buttons.get(0).clicked = false; //to make the other buttons's clicked variable false
				game.buttons.get(1).clicked = true;
				game.buttons.get(2).clicked = false;
				input.lastKey = 0;
			}
			
			if(qButtonPressed == true) {
				game.buttons.get(0).clicked = true; //to make the other buttons's clicked variable false
				game.buttons.get(1).clicked = false;
				game.buttons.get(2).clicked = false;
				input.lastKey = 0;
			}
			
			if(eButtonPressed == true) {
				game.buttons.get(0).clicked = false; //to make the other buttons's clicked variable false
				game.buttons.get(1).clicked = false;
				game.buttons.get(2).clicked = true;
				input.lastKey = 0;
			}
			
			if(rButtonPressed == true) { //cancel button deselects all the other buttons
				game.buttons.get(0).clicked = false;
				game.buttons.get(1).clicked = false;
				game.buttons.get(2).clicked = false;
				input.lastKey = 0;
				
				if(game.getLevel().getTurn() == 1) {
					for(int i = 0; i < game.getLevel().getRedTeam().size(); i++) {
						game.getLevel().getRedTeam().get(i).selected = false;
						game.getLevel().getRedTeam().get(i).action = 0;
					}
				} else if(game.getLevel().getTurn() == 2) {
					for(int i = 0; i < game.getLevel().getGreenTeam().size(); i++) {
						game.getLevel().getGreenTeam().get(i).selected = false;
						game.getLevel().getGreenTeam().get(i).action = 0;
					}
				}
			}*/
	}
	
	public void render(Screen screen) {
		//The "-16" helps center character by shifting it over to the left. 
		screen.renderInterface(x - xVelocity, y - yVelocity, width, height, group, type, clicked, visible); //renders all 4 pieces of the 32x32 sprite. Modifying the x or y will change where the screen is centered.
	}
	
	public Game getGame() {
		return game;
	}
	
	public Keyboard getKeyboard() {
		return input;
	}
	
}
