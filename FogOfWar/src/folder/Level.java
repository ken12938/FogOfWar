package folder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import units.Fort;
import units.Grenadier;
import units.Assassin;
import units.Marine;
import units.Rocketeer;
import units.MachineGunner;
import units.Medic;
import units.Scout;
import units.Tank;
import units.Sniper;
import units.Unit;
import units.VisionTower;
import units.Zombie;
import folder.tile.Tile;
import folder.tile.WallTile;
import projectiles.Projectile;

public class Level { // will contain all level subclasses and tiles, and anything related to the level. very big class.

	protected int width, height; // dimension of the LEVEL, not the screen
	protected int[] tilesInt; // contains integers, each one representing a type of tile (wood, stone, etc.) that relates to its position
	protected int[] tiles; // contains the colors for each tile in the map
	protected Player player;
	protected final Random random = new Random();

	protected Mouse mouse;
	private int mouseClickCounter;
	protected Keyboard input; // key variable from Game class

	protected Game game;
	protected Screen screen;
	private int xScroll;
	private int yScroll;

	private static int time; // game time, dawg. in (seconds * 60)
	private int currentTime; // the time exactly when you hit "end turn"
	private int turnTime; // turn time * 60
	private int turn; // 0 means no one's turn, 1 means red's turn, 2 means green's turn
	private int lastTurn; // keeps track of last turn

	public boolean[] redPixels; // an array of which pixels are visible or invisible during red's turn
	public boolean[] greenPixels;

	public int redMarine = 0; // counts how many of each respective unit there are
	public int redTank = 0;
	public int redGrenadier = 0;
	public int redSniper = 0;
	public int redAssassin = 0;
	public int redRocketeer = 0;
	public int redScout = 0;
	public int redMachineGunner = 0;
	public int redMedic = 0;
	
	public int redZombie = 0;
	public int redFort = 0;
	
	public int greenMarine = 0;
	public int greenTank = 0;
	public int greenGrenadier = 0;
	public int greenSniper = 0;
	public int greenAssassin = 0;
	public int greenRocketeer = 0;
	public int greenScout = 0;
	public int greenMachineGunner = 0;
	public int greenMedic = 0;
	
	public int greenZombie = 0;
	public int greenFort = 0;
	
	public int redFortX = 1; //this will determine the x and y values of the forts
	public int redFortY = 10;
	public int greenFortX = 29;
	public int greenFortY = 10;

	public int unitSelector = 0; // detects which unit you are adding during placement phase
	public int unitCounter = 0;

	public int marineCost = 150;
	public int tankCost = 300;
	public int grenadierCost = 250;
	public int sniperCost = 200;
	public int assassinCost = 250;
	public int rocketeerCost = 200;
	public int scoutCost = 150;
	public int machineGunnerCost = 200;
	public int medicCost = 150;

	private LinkedList<Entity> entities = new LinkedList<Entity>(); // contains all the entities in a level. This allows simple rendering and updating for entities to be streamlined into the LEvel class
	private LinkedList<Projectile> projectiles = new LinkedList<Projectile>(); // separate arraylist exclusively for projectiles.
	private LinkedList<Unit> redTeam = new LinkedList<Unit>(); // red Units
	private LinkedList<Unit> greenTeam = new LinkedList<Unit>(); // green Units, also double as the zombie class
	private LinkedList<Unit> towers = new LinkedList<Unit>(); // all towers, regardless of color or race
	private LinkedList<Unit> redMedics = new LinkedList<Unit>(); // red medics
	private LinkedList<Unit> greenMedics = new LinkedList<Unit>();

	public Level(Game mainGame, String path, Player player, Screen screen, Keyboard input) { // this second constructor. Its used for generating a map from a .png file
		this.input = input;
		loadLevel(path);
		System.out.println(path);
		generateLevel();

		turnTime = 600;

		this.player = player;
		this.screen = screen;
		this.game = mainGame;
		xScroll = 0;
		yScroll = 0;

		time = currentTime = 0;
		turn = 1;
		lastTurn = 0;
		redPixels = new boolean[16 * 16 * width * height]; // 704 * 256 pixels
		greenPixels = new boolean[16 * 16 * width * height];

	}

	protected void generateLevel() {
	}

	protected void loadLevel(String path) {
	}

	// updates the level. Anything AI related gets updated. 60 ups still
	public void update() {
		// checks if the cursor is off the screen, and scrolls if necessary
		scroll();
		if (lastTurn != turn) {
			teamVision();
			fogOfWar();
		}
		//if (time % 60 == 0) teamVision();
		//if (time % 60 == 0) fogOfWar(); // checks visibility and makes sure that fog covers the right stuff.

		if (turn == 1 || turn == 2) { // if it is either red or green's turn, selection is possible
			select();// selection method made by Andy

			// tjese two if-loops animate the units' idle animations
			if (turn == 1) {
				for (int i = 0; i < redTeam.size(); i++) {
					redTeam.get(i).updateAnimation(); // updates its animation
				}
				for (int i = 0; i < towers.size(); i++) {
					towers.get(i).unitCollision(); //does collision here
					towers.get(i).updateAnimation(); // updates its animation
				}
			} else if (turn == 2) {
				for (int i = 0; i < greenTeam.size(); i++) {
					greenTeam.get(i).updateAnimation(); // updates its animation
				}
				for (int i = 0; i < towers.size(); i++) {
					towers.get(i).unitCollision();
					towers.get(i).updateAnimation();
				}
			}
		}

		
		//zombie mode AI. Works only for green team
		if (turn == 2 && lastTurn == 1 && game.mode.equals("zombie") && game.selecting == false) {
			zombieAI();
			turn = 0;
			lastTurn = 2;
			clearPixels();
		}

		// turn execution
		if (turn == 0) {
			for (int i = 0; i < entities.size(); i++) { // updates all entities in arraylist
				entities.get(i).update();
			}
		}

		for (int i = 0; i < projectiles.size(); i++) { // updates all projectiles
			projectiles.get(i).update();
		}

		// checks and actually removes if projectiles are "removed"
		clearProjectiles();
		// checks and actually units are "removed" or have less than zero health
		removeUnits();
		
		if (turn == 0 && lastTurn == 2 && game.selecting == false) {
			
			//Sound.turn_theme.stop();
			//Sound.map_theme.loop();
		}

		if (turn == 1 && lastTurn == 0 && game.selecting == false) {
			resetUnits(); // only resets units when turn goes from green team to no one.
			//Sound.map_theme.stop();
			//Sound.turn_theme.loop();
		}

		/*if (turn != lastTurn) { // every time it switches turns, clear both arrays
			clearPixels(); // No need for the (time % 60) thing since its only doing clearPixels() once every turn anyway
		}*/
		
		//it will do clearPixels() in the time() method now

		lastTurn = turn;
		// this method manages time and switching turns.
		time();
	}

	private void teamVision() {
		if (turn == 1 || turn == 0) { // executes during both red turn and turn execution
			for (int i = 0; i < redTeam.size(); i++) {
				int xOrigin = (int) Math.round(redTeam.get(i).getX()); // x and y coordinates of unit
				int yOrigin = (int) Math.round(redTeam.get(i).getY());
				int range = (int) redTeam.get(i).getVisionRange();
				double direction = redTeam.get(i).dir2; // dir2 is used instead of dir since you want to update the vision only after every turn
				double visionWidth = redTeam.get(i).getVisionWidth();

				for (int y = -range; y < range; y++) {
					for (int x = -range; x < range; x++) {
						double xDiff = x + xOrigin + 8; // normalizes the coordinates so that they are relative to the Level
						double yDiff = y + yOrigin + 8; // 8 is added so that the center of vision is in the center of the unit
						double dist = MathMachine.distance(x, y);
						double tempDir = MathMachine.atan2(y, x);

						if (xDiff <= 0 || xDiff >= width * 16 || yDiff <= 0 || yDiff >= height * 16) continue;
						if (dist <= range && ((direction - tempDir >= -visionWidth / 2 && direction - tempDir <= visionWidth / 2) || direction - tempDir >= 6.28 - visionWidth / 2 || direction - tempDir <= -6.28 + visionWidth / 2)) {
							redPixels[(int) (xDiff + (yDiff) * (width * 16))] = true;
						}
					}
				}
			}

		}
		if (turn == 2 || turn == 0) { // only executes during its turn

			for (int i = 0; i < greenTeam.size(); i++) {
				int xOrigin = (int) Math.round(greenTeam.get(i).getX()); // x and y coordinates of unit
				int yOrigin = (int) Math.round(greenTeam.get(i).getY());
				int range = (int) greenTeam.get(i).getVisionRange();
				double direction = greenTeam.get(i).dir2;
				double visionWidth = greenTeam.get(i).getVisionWidth();

				for (int y = (-1 * range); y < range; y++) {
					for (int x = (-1 * range); x < range; x++) {
						double xDiff = x + xOrigin + 8;
						double yDiff = y + yOrigin + 8;
						double dist = MathMachine.distance(x, y);
						double tempDir = MathMachine.atan2(y, x);

						if (xDiff <= 0 || xDiff >= width * 16 || yDiff <= 0 || yDiff >= height * 16) continue;
						if (dist <= range && ((direction - tempDir >= -visionWidth / 2 && direction - tempDir <= visionWidth / 2) || direction - tempDir >= 6.28 - visionWidth / 2 || direction - tempDir <= -6.28 + visionWidth / 2)) {
							greenPixels[(int) (xDiff + (yDiff) * (width * 16))] = true;
						}
					}
				}
			}

		}
	}

	// checks if units can see each other! Only runs after a turn has occurred.
	private void fogOfWar() {
		// checks to see if the RED units can see the green ones. happens only once per turn.
		if (turn == 1) {
			for (int i = 0; i < greenTeam.size(); i++) {
				greenTeam.get(i).visibleToEnemy = false;
				int greenX = (int) Math.round(greenTeam.get(i).getX());
				int greenY = (int) Math.round(greenTeam.get(i).getY());
				if (redPixels[(int) Math.round(greenX + greenY * (width * 16))] == true || greenTeam.get(i).unitType == "Fort") {
					greenTeam.get(i).visibleToEnemy = true;
				}
				/*
				 * for (int j = 0; j < redTeam.size(); j++) { redTeam.get(j).visible = false;//resets all green units so that we can render their interactive sprites (i.e. health bars, selection boxes, etc.)
				 * 
				 * 
				 * double xDiff = greenTeam.get(i).getX() - redTeam.get(j).getX(); double yDiff = greenTeam.get(i).getY() - redTeam.get(j).getY(); double dist = MathMachine.distance(xDiff, yDiff); double direction = MathMachine.atan2((int)yDiff, (int)xDiff); if( dist < redTeam.get(j).getVisionRange() && direction - redTeam.get(j).dir2 >= -0.5 && direction - redTeam.get(j).dir2 <= 0.5) greenTeam.get(i).visible = true; }
				 */
			}
		}

		// checks to see if the GREEN units can see the red ones. Happens only once per turn.
		if (turn == 2) {
			for (int i = 0; i < redTeam.size(); i++) {
				redTeam.get(i).visibleToEnemy = false;
				int redX = (int) Math.round(redTeam.get(i).getX());
				int redY = (int) Math.round(redTeam.get(i).getY());
				if (greenPixels[(int) Math.round((redX + redY * (width * 16)))] == true || redTeam.get(i).unitType == "Fort") {
					redTeam.get(i).visibleToEnemy = true;
				}
				/*
				 * for (int j = 0; j < greenTeam.size(); j++) { greenTeam.get(j).visible = false; //resets all green units so that we can render their interactive sprites (i.e. health bars, selection boxes, etc.) double xDiff = redTeam.get(i).getX() - greenTeam.get(j).getX(); double yDiff = redTeam.get(i).getY() - greenTeam.get(j).getY(); double dist = MathMachine.distance(xDiff, yDiff); if(dist < greenTeam.get(j).getVisionRange()) redTeam.get(i).visible = true; }
				 */
			}
		}

		if (turn == 0 && game.settingsbuttons.get(2).clicked == true) {
			for (int i = 0; i < redTeam.size(); i++) {
				redTeam.get(i).visibleToEnemy = false;
			}
			for (int i = 0; i < greenTeam.size(); i++) {
				greenTeam.get(i).visibleToEnemy = false;
			}
		}
	}

	// sets the unit's action variable back to zero (nothing). Also makes it invisible again happens every turn.
	private void resetUnits() {
		for (int i = 0; i < entities.size(); i++) {
			Entity ent = entities.get(i);
			if (ent instanceof Unit) {
				Unit p = (Unit) ent;
				p.reset();
			}
		}

		for (int i = 0; i < redMedics.size(); i++) {
			for (int j = 0; j < redTeam.size(); j++) {
				double xDiff = redTeam.get(j).getX() - redMedics.get(i).getX();
				double yDiff = redTeam.get(j).getY() - redMedics.get(i).getY();
				double healRange = redMedics.get(i).getHealRange();

				if (MathMachine.distance(xDiff, yDiff) <= healRange) {
					redTeam.get(j).changeHealth(-0.2 * redTeam.get(j).getMaxHealth(), "neutral");
				}
			}
		}

		for (int i = 0; i < greenMedics.size(); i++) {
			for (int j = 0; j < greenTeam.size(); j++) {
				double xDiff = greenTeam.get(j).getX() - greenMedics.get(i).getX();
				double yDiff = greenTeam.get(j).getY() - greenMedics.get(i).getY();
				double healRange = greenMedics.get(i).getHealRange();

				if (MathMachine.distance(xDiff, yDiff) <= healRange) {
					greenTeam.get(j).changeHealth(-0.2 * greenTeam.get(j).getMaxHealth(), "neutral");
				}
			}
		}
	}

	public void clearPixels() {
		for (int y = 0; y < height * 16; y++) {
			for (int x = 0; x < width * 16; x++) {
				redPixels[x + y * width * 16] = false;
				greenPixels[x + y * width * 16] = false;
			}
		}
	}

	private void clearProjectiles() {
		for (int i = 0; i < projectiles.size(); i++) { // cycles through all "removed" projectiles and actually removes them.
			Projectile p = projectiles.get(i);
			if (p.isRemoved()) projectiles.remove(i);
		}
	}

	private void select() { // andy's code

		if (game.selecting == true) { // if the game is in placement phase
			if (game.getKeyboard().lastKey == 49) {
				// unitSelector = 1; //Marine selected (button 1)
				game.selectionbuttons.get(2).clicked = true; // pressing the button 1 selects the first button and deselects the other buttons
				game.selectionbuttons.get(3).clicked = false;
				game.selectionbuttons.get(4).clicked = false;
				game.selectionbuttons.get(5).clicked = false;
				game.selectionbuttons.get(6).clicked = false;
				game.selectionbuttons.get(8).clicked = false;
				game.unitPlacementSequence = 0;
			}
			if (game.getKeyboard().lastKey == 50) {
				// unitSelector = 2; //Tank selected (button 2)
				game.selectionbuttons.get(2).clicked = false;
				game.selectionbuttons.get(3).clicked = true;
				game.selectionbuttons.get(4).clicked = false;
				game.selectionbuttons.get(5).clicked = false;
				game.selectionbuttons.get(6).clicked = false;
				game.selectionbuttons.get(8).clicked = false;
				game.unitPlacementSequence = 0;
			}
			if (game.getKeyboard().lastKey == 51) {
				// unitSelector = 3; //Grenadier selected (button 3)
				game.selectionbuttons.get(2).clicked = false;
				game.selectionbuttons.get(3).clicked = false;
				game.selectionbuttons.get(4).clicked = true;
				game.selectionbuttons.get(5).clicked = false;
				game.selectionbuttons.get(6).clicked = false;
				game.selectionbuttons.get(8).clicked = false;
				game.unitPlacementSequence = 0;
			}
			if (game.getKeyboard().lastKey == 52) {
				// unitSelector = 4; //Sniper selected (button 4)
				game.selectionbuttons.get(2).clicked = false;
				game.selectionbuttons.get(3).clicked = false;
				game.selectionbuttons.get(4).clicked = false;
				game.selectionbuttons.get(5).clicked = true;
				game.selectionbuttons.get(6).clicked = false;
				game.selectionbuttons.get(8).clicked = false;
				game.unitPlacementSequence = 0;
			}
			if (game.getKeyboard().lastKey == 53) {
				// unitSelector = 5; //Assassin selected (button 5)
				game.selectionbuttons.get(2).clicked = false;
				game.selectionbuttons.get(3).clicked = false;
				game.selectionbuttons.get(4).clicked = false;
				game.selectionbuttons.get(5).clicked = false;
				game.selectionbuttons.get(6).clicked = true;
				game.selectionbuttons.get(8).clicked = false;
				game.unitPlacementSequence = 0;
			}
			if (game.getKeyboard().lastKey == 54) {
				// unitSelector = 6; //Rocketeer selected (button 6)
				game.selectionbuttons.get(2).clicked = false;
				game.selectionbuttons.get(3).clicked = false;
				game.selectionbuttons.get(4).clicked = false;
				game.selectionbuttons.get(5).clicked = false;
				game.selectionbuttons.get(6).clicked = false;
				game.selectionbuttons.get(8).clicked = true;
				game.unitPlacementSequence = 1;
			}
			if (game.getKeyboard().lastKey == 67) {
				// unitSelector = 0;//c = cancel button

				game.selectionbuttons.get(2).clicked = false;
				game.selectionbuttons.get(3).clicked = false;
				game.selectionbuttons.get(4).clicked = false;
				game.selectionbuttons.get(5).clicked = false;
				game.selectionbuttons.get(6).clicked = false;
			}
			game.getKeyboard().lastKey = 0; // makes sure that lastKey does not register anything after the key is pressed

			if (Mouse.getButton() == 1 && (time - currentTime) > 25) {
				currentTime = time;
				int mouseX = ((int) player.x + (int) screen.xOffset + 8) >> 4; // X value of where the unit will be placed
				int mouseY = ((int) player.y + (int) screen.yOffset + 8) >> 4; // Y value of where the unit will be placed

				boolean inRedBase = mouseX > 0 && mouseX < width / 4 && mouseY > height / 4 && mouseY < 3 * height / 4; // makes sure that the unit is added in the red base
				boolean inGreenBase = mouseX >= 3 * width / 4 && mouseX <= width && mouseY >= height / 4 && mouseY <= 3 * height / 4;
				boolean tileEqualsWall = getTile(mouseX, mouseY).getClass().toString().equals("class folder.tile.WallTile"); // prevents adding a unit on a wall
				boolean tileEqualsFort = ((mouseX == redFortX || mouseX == redFortX + 1) && (mouseY == redFortY || mouseY == redFortY + 1)) || ((mouseX == greenFortX || mouseX == greenFortX + 1) && (mouseY == greenFortY || mouseY == greenFortY + 1));
				boolean offInterface = player.y < game.buttons.get(0).yFinal - 24; // makes sure you can't click on the interface

				if (game.selectingTurn == 1 && inRedBase && !tileEqualsWall && !tileEqualsFort && offInterface) { // red's placement phase //the mouseX and mouseY conditions prevent the unit from being placed too far from the base
					if (game.selectionbuttons.get(2).clicked == true && game.redMoney - marineCost >= 0) { // mouseX < 8 is supposed to prevent you from placing a unit too far into the middle
						add(new Marine(mouseX, mouseY, true, false));
						game.redMoney -= marineCost;
					} else if (game.selectionbuttons.get(3).clicked == true && game.redMoney - tankCost >= 0) { // game.redMoney - 250 >= 0 checks if you have enough money to buy the tank
						add(new Tank(mouseX, mouseY, true, false));
						game.redMoney -= tankCost;
					} else if (game.selectionbuttons.get(4).clicked == true && game.redMoney - grenadierCost >= 0) {
						add(new Grenadier(mouseX, mouseY, true, false));
						game.redMoney -= grenadierCost;
					} else if (game.selectionbuttons.get(5).clicked == true && game.redMoney - sniperCost >= 0) {
						add(new Sniper(mouseX, mouseY, true, false));
						game.redMoney -= sniperCost;
					} else if (game.selectionbuttons.get(6).clicked == true && game.redMoney - assassinCost >= 0) {
						add(new Assassin(mouseX, mouseY, true, false));
						game.redMoney -= assassinCost;
					} else if (game.selectionbuttons.get(8).clicked == true && game.redMoney - rocketeerCost >= 0) {
						add(new Rocketeer(mouseX, mouseY, true, false));
						game.redMoney -= rocketeerCost;
					}
				} else if (game.selectingTurn == 2 && inGreenBase && !tileEqualsWall && !tileEqualsFort && offInterface) { // blue's placement phase //mouseX < 8 is supposed to prevent you from placing a unit too far into the middle
					if (game.selectionbuttons.get(2).clicked == true && game.greenMoney - marineCost >= 0) { // the first condition detects if the certain button was clicked or not
						add(new Marine(mouseX, mouseY, false, true));
						game.greenMoney -= marineCost;
					} else if (game.selectionbuttons.get(3).clicked == true && game.greenMoney - tankCost >= 0) { // game.redMoney - 250 >= 0 checks if you have enough money to buy the tank
						add(new Tank(mouseX, mouseY, false, true));
						game.greenMoney -= tankCost;
					} else if (game.selectionbuttons.get(4).clicked == true && game.greenMoney - grenadierCost >= 0) {
						add(new Grenadier(mouseX, mouseY, false, true));
						game.greenMoney -= grenadierCost;
					} else if (game.selectionbuttons.get(5).clicked == true && game.greenMoney - sniperCost >= 0) {
						add(new Sniper(mouseX, mouseY, false, true));
						game.greenMoney -= sniperCost;
					} else if (game.selectionbuttons.get(6).clicked == true && game.greenMoney - assassinCost >= 0) {
						add(new Assassin(mouseX, mouseY, false, true));
						game.greenMoney -= assassinCost;
					} else if (game.selectionbuttons.get(8).clicked == true && game.greenMoney - rocketeerCost >= 0) {
						add(new Rocketeer(mouseX, mouseY, false, true));
						game.greenMoney -= rocketeerCost;
					}
				}
			}
		}

		if (Mouse.getClicked() && game.selecting == false) { // in normal game phase
			int action = 0; // 0 = nothing; 1 = moving; 2 = shooting; 3 = moving and shooting

			if (game.buttons.get(0).clicked == true) action = 1; // q key; moving
			if (game.buttons.get(1).clicked == true) action = 2; // w key; shooting
			if (game.buttons.get(2).clicked == true) action = 3; // e key; moving and shooting

			mouseClickCounter++;
			Mouse.resetClick(); // resets it to zero.
			if (mouseClickCounter > 1) {
				mouseClickCounter = 0;
			}
			if (mouseClickCounter == 1) {
				System.out.println(screen.xOffset + " , " + screen.yOffset);
				boolean selected = false;
				for (int i = 0; i < entities.size(); i++) {
					Entity ent = entities.get(i);
					if (ent instanceof Unit) { // insures that only units are picked
						Unit unit = (Unit) ent;
						// Andrew Ton optimized this, so see me if you have any questions about the values.
						if (Math.abs((int) unit.getX() - ((int) player.x + (int) screen.xOffset + 8)) < 10 && Math.abs((int) unit.getY() - (((int) player.y + (int) screen.yOffset + 8))) < 10) { // selection code. note the xOffset and yOffset because player.x and player.y are relative to the actual position.
							if ((turn == 1 && unit.UNIT_R == true) || (turn == 2 && unit.UNIT_G == true) || unit.unitType == "Fort") { // makes sure that you only can select red units on red's turn and same for green
								selected = true;
								unit.select(action);
								player.switchSprite();
								System.out.println("selected");
								break;
							}
						}
					}
				}
				if (!selected) {
					mouseClickCounter--;
				}
			}

			if (mouseClickCounter == 0) {
				for (int i = 0; i < entities.size(); i++) {
					Entity ent = entities.get(i);
					if (ent instanceof Unit) { // insures that only units are picked
						Unit unit = (Unit) ent;
						if (unit.getSelected()) {
							if (player.y < 158) { // prevents action if the mouse clicks on part of the interface
								unit.act((player.x + screen.xOffset + 16), (player.y + screen.yOffset + 16), action); // moves the player directly to the wanted position. Coordinates are in terms of the entire map (pixel precision)
							}
							player.switchSprite();
							unit.deselect(); // resets selection to false.
						}
					}
				}
			}

		}
	}

	// manages the switching turn and time. and switching music
	public void time() {
		// manually inputting the turn
		if (game.selecting == false) {
			if ((input.enter == true || game.buttons.get(4).clicked == true) && turn == 1 && (time - currentTime) > 25) { // if the enter key is pressed and it's red's turn, it will now be green's turn
				turn = 2;
				currentTime = time;
			}

			if ((input.enter == true || game.buttons.get(4).clicked == true) && turn == 2 && (time - currentTime) > 25) { // if the enter key is pressed and it's green's turn, it will now be no one's turn
				// Sound.menu_theme.stop(); //stop all music, jus because
				// Sound.map_theme.stop();
				// Sound.selection_theme.stop();
				// Sound.turn_theme.stop();
				// Sound.map_theme.loop();
				turn = 0;
				currentTime = time;
			}

			if (time - turnTime == currentTime && time != 0 && turn == 0) {
				// Sound.map_theme.stop();
				// Sound.turn_theme.loop();
				clearPixels();
				turn = 1;
			}
		}

		time++;
	}

	public void scroll() {
		if (player.x < -4 && xScroll >= -100) {
			xScroll -= 3;
		} else if (player.x + 28 > screen.width && xScroll <= width * 16 - 100) { // 32 is for the size of the reticle
			xScroll += 3;
		}
		if (player.y < -4 && yScroll >= -100) {
			yScroll -= 3;
		} else if (player.y + 20 > screen.height && yScroll <= height * 16 - 100) { // accounts for the additional offset because of the GUI
			yScroll += 3;
		}

		screen.setOffset(xScroll, yScroll);
	}

	// if a zombie is not following a target, this automatically chooses a random location to run to. If it is targeting, it sets the location to that position.
	// currently only works for green team
	public void zombieAI() {
		for (int i = 0; i < greenTeam.size(); i++) {
			Entity ent = greenTeam.get(i);
			if (ent instanceof Zombie) { // insures that only units are picked
				Zombie zombie = (Zombie) ent;

				// NEXT 3 LINES ARE THE 3 basic actions of Zombie AI!
				boolean targeting = zombie.sense(); // is it targeting?
				boolean charging = zombie.charge(); // is it charging with the horde?
				boolean returning = zombie.reCenter(); // is it returning to the horde?

				Random spread = new Random(); // makes there charges and targeting a little more random so that they arent uniform.

				if (targeting) {
					System.out.println("targeting");
					zombie.act(zombie.getClosest().getX() + spread.nextInt(10) - 5, zombie.getClosest().getY() + spread.nextInt(10) - 5, 1);
				} else if (charging) {
					System.out.println("charging");
					zombie.act(zombie.getClosest().getX() + spread.nextInt(10) - 5, zombie.getClosest().getY() + spread.nextInt(10) - 5, 1);
				} else if (returning) {
					System.out.println("Returning");
					zombie.act(zombie.swarmX, zombie.swarmY, 1);
				} else {
					zombie.wander();
					zombie.act(zombie.getXD(), zombie.getYD(), 1); // moves the zombie to a random position. Coordinates are in terms of the entire map (pixel precision)
				}
			}
		}
	}

	// xscroll and yscroll keep track of the position of the top left corner of the screen... in pixels i believe.
	public void render(Screen screen) { // x1, x0, y0, y1 are called "corner pins". They represent the coordinates of the 4 corners of the screen.
		// scroll code

		int x0 = xScroll >> 4; // ">>" shifts the value over by 4, making it equivalent to dividing by 16. This is more efficient than using the "/"!
		int x1 = (xScroll + screen.width + 16) >> 4; // the reason why we are dividing by 16 is to convert the number of pixels to tiles which is a significantly lower number. We are adding sixteen to add 1 tile to the number of tiles being rendered. This gets ride of the black edging problem.
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16 - 50) >> 4;

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen); // allows us to have a map out of bounds error
			}
		}

		// this block of code renders the lighter pixels representing vision.
		if (turn == 1) {
			screen.renderVision(redPixels, height, width); // uses either the redPixels or greenPixels array depending on who's turn it is
		} else if (turn == 2) {
			screen.renderVision(greenPixels, height, width);
		} else if (turn == 0) {
			// screen.renderVision(redPixels, height, width); // uses either the redPixels or greenPixels array depending on who's turn it is
			// screen.renderVision(greenPixels, height, width);
		}

		// for RED TURN. only renders RED units and the visible green units
		if (turn == 1) {
			for (int i = 0; i < redTeam.size(); i++) {
				redTeam.get(i).render(screen);
				redTeam.get(i).visibleToEnemy = false; // resets its visibility
				for (int j = 0; j < greenTeam.size(); j++) {
					if (greenTeam.get(j).visibleToEnemy) greenTeam.get(j).render(screen);
				}
			}
			for (int i = 0; i < towers.size(); i++) { // renders towers
				if(towers.get(i).getHealth() > 0) {
					towers.get(i).render(screen);
				}
			}
		}
		// for green TURN. Only renders green units, and the visible red units
		if (turn == 2 && game.mode.equals("pvp")) {
			for (int i = 0; i < greenTeam.size(); i++) {
				greenTeam.get(i).render(screen);
				greenTeam.get(i).visibleToEnemy = false; // resets its visiblity
				for (int j = 0; j < redTeam.size(); j++) {
					if (redTeam.get(j).visibleToEnemy) redTeam.get(j).render(screen);
				}
			}
			for (int i = 0; i < towers.size(); i++) {// renders towers
				if(towers.get(i).getHealth() > 0) {
					towers.get(i).render(screen);
				}
			}
		}

		if (turn == 0) { // during action phase, renders the correct units
			if (game.settingsbuttons.get(2).clicked == false) { // button for turning on/off fog
				for (int i = 0; i < entities.size(); i++) { // for each entity, render it!
					entities.get(i).render(screen);
				}
			} else { // always renders the muzzle flash
				for (int j = 0; j < greenTeam.size(); j++) {
					greenTeam.get(j).muzzleFlash(screen);
				}
				for (int j = 0; j < redTeam.size(); j++) {
					redTeam.get(j).muzzleFlash(screen);
				}
			}

			for (int i = 0; i < towers.size(); i++) {// renders towers
				if(towers.get(i).getHealth() > 0) {
					towers.get(i).render(screen);
				}
			}
		}

		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
	}

	// adds entity to level
	public void add(Entity e) {
		e.init(this);

		if (e instanceof Unit) {
			Unit u = (Unit) e;
			entities.add(u);

			// RED TEAM!
			if (u.UNIT_R == true) {
				redTeam.add(u);
				if (u.getClass().toString().equals("class units.Marine")) { // gets the class, converts it into a string, and then compares it with what it should say if it is a marine class
					redMarine++;
					u.unitType = "Marine";
				} else if (u.getClass().toString().equals("class units.Tank")) {
					redTank++;
					u.unitType = "Tank";
				} else if (u.getClass().toString().equals("class units.Grenadier")) {
					redGrenadier++;
					u.unitType = "Grenadier";
				} else if (u.getClass().toString().equals("class units.Sniper")) {
					redSniper++;
					u.unitType = "Sniper";
				} else if (u.getClass().toString().equals("class units.Assassin")) {
					redAssassin++;
					u.unitType = "Assassin";
				} else if (u.getClass().toString().equals("class units.Rocketeer")) {
					redRocketeer++;
					u.unitType = "Rocketeer";
				} else if (u.getClass().toString().equals("class units.Scout")) {
					redScout++;
					u.unitType = "Scout";
				} else if (u.getClass().toString().equals("class units.MachineGunner")) {
					redMachineGunner++;
					u.unitType = "Machine Gunner";
				} else if (u.getClass().toString().equals("class units.Medic")) {
					redMedic++;
					u.unitType = "Medic";
					redMedics.add(u); // adds to the medic arraylist if the unit is a medic
				} else if (u.getClass().toString().equals("class units.Zombie")) {
					redZombie++;
					u.unitType = "Zombie";
				} else if (u.getClass().toString().equals("class units.Fort")) {
					redFort++;
					u.unitType = "Fort";
				}
				u.team = "Red";
			}

			// GREEN TEAM!
			if (u.UNIT_G == true) {
				greenTeam.add(u);
				if (u.getClass().toString().equals("class units.Marine")) { // gets the class, converts it into a string, and then compares it with what it should say if it is a marine class
					greenMarine++;
					u.unitType = "Marine";
				} else if (u.getClass().toString().equals("class units.Tank")) {
					greenTank++;
					u.unitType = "Tank";
				} else if (u.getClass().toString().equals("class units.Grenadier")) {
					greenGrenadier++;
					u.unitType = "Grenadier";
				} else if (u.getClass().toString().equals("class units.Sniper")) {
					greenSniper++;
					u.unitType = "Sniper";
				} else if (u.getClass().toString().equals("class units.Assassin")) {
					greenAssassin++;
					u.unitType = "Assassin";
				} else if (u.getClass().toString().equals("class units.Rocketeer")) {
					greenRocketeer++;
					u.unitType = "Rocketeer";
				} else if (u.getClass().toString().equals("class units.Scout")) {
					greenScout++;
					u.unitType = "Scout";
				} else if (u.getClass().toString().equals("class units.MachineGunner")) {
					greenMachineGunner++;
					u.unitType = "Machine Gunner";
				} else if (u.getClass().toString().equals("class units.Medic")) {
					greenMedic++;
					u.unitType = "Medic";
					greenMedics.add(u);
				} else if (u.getClass().toString().equals("class units.Zombie")) {
					greenZombie++;
					u.unitType = "Zombie";
				} else if (u.getClass().toString().equals("class units.Fort")) {
					greenFort++;
					u.unitType = "Fort";
				}
				u.team = "green";
			}

			// TOWERS!
			if (e instanceof VisionTower) {
				VisionTower t = (VisionTower) e;
				towers.add(t);
				u.unitType = "VisionTower";
			}
		}

	}

	public void addProjectile(Projectile p) {
		p.init(this);
		projectiles.add(p);
	}

	public List<Projectile> getProjectiles() {
		return projectiles;
	}

	// color KEY for pixel-size map. See the variables in the Tile method
	// 0xff7F6A00 = graintile
	// 0xffFFD800 = flowertile
	// 0xff000000 = rocktile
	// 0xff267F00 = bush
	// 0xffFFFF00 = flower
	// THIS method looks at the pixel size map and returns the corresponding tile
	public Tile getTile(int x, int y) { // x and y are in terms of tile position, NOT pixels
		if (x < 0 || x >= width || y < 0 || y >= height) {
			return Tile.voidTile;
		}

		if (tiles[x + y * width] == Tile.col_grass) return Tile.grass_vis; // this means a zero indicates a grass tile.
		if (tiles[x + y * width] == Tile.col_wall) return Tile.wall_vis;
		if (tiles[x + y * width] == Tile.col_flower) return Tile.flower_vis;
		if (tiles[x + y * width] == Tile.col_bush) return Tile.bush_vis;
		if (tiles[x + y * width] == Tile.col_grain) return Tile.grain_vis;
		if (tiles[x + y * width] == Tile.col_rock) return Tile.rock_vis;
		if (tiles[x + y * width] == Tile.col_wall2) return Tile.wall2_vis;

		if (tiles[x + y * width] == Tile.col_ceilingstdalone) return Tile.ceilingstdalon;
		if (tiles[x + y * width] == Tile.col_ceilingupperleft) return Tile.ceilingupperleft;
		if (tiles[x + y * width] == Tile.col_ceilingupper) return Tile.ceilingupper;
		if (tiles[x + y * width] == Tile.col_ceilingupperright) return Tile.ceilingupperright;
		if (tiles[x + y * width] == Tile.col_ceilingcenterleft) return Tile.ceilingcenterleft;
		if (tiles[x + y * width] == Tile.col_ceilingcenter) return Tile.ceilingcenter;
		if (tiles[x + y * width] == Tile.col_ceilingcenterright) return Tile.ceilingcenterright;
		if (tiles[x + y * width] == Tile.col_ceilinglowerleft) return Tile.ceilinglowerleft;
		if (tiles[x + y * width] == Tile.col_ceilinglower) return Tile.ceilinglower;
		if (tiles[x + y * width] == Tile.col_ceilinglowerright) return Tile.ceilinglowerright;
		if (tiles[x + y * width] == Tile.col_wallstandalone) return Tile.wallstandalone;
		if (tiles[x + y * width] == Tile.col_wallleft) return Tile.wallleft;

		if (tiles[x + y * width] == Tile.col_wallcenter) return Tile.wallcenter;
		if (tiles[x + y * width] == Tile.col_wallright) return Tile.wallright;
		if (tiles[x + y * width] == Tile.col_singlewallupper) return Tile.singlewallupper;
		if (tiles[x + y * width] == Tile.col_singlewalluppercenter) return Tile.singlewalluppercenter;
		if (tiles[x + y * width] == Tile.col_singlewallcenter) return Tile.singlewallcenter;
		if (tiles[x + y * width] == Tile.col_singlewalllowercenter) return Tile.singlewalllowercenter;
		if (tiles[x + y * width] == Tile.col_singlewalllower) return Tile.singlewalllower;
		if (tiles[x + y * width] == Tile.col_singlewallright) return Tile.singlewallright;
		if (tiles[x + y * width] == Tile.col_singlewallrightcenter) return Tile.singlewallrightcenter;
		if (tiles[x + y * width] == Tile.col_singlewallleftcenter) return Tile.singlewallleftcenter;
		if (tiles[x + y * width] == Tile.col_singlewallleft) return Tile.singlewallleft;

		if (tiles[x + y * width] == Tile.col_brickceilingcrack1) return Tile.brickceilingcrack1;
		if (tiles[x + y * width] == Tile.col_brickceilingcrack2) return Tile.brickceilingcrack2;
		if (tiles[x + y * width] == Tile.col_brickceilingcrack3) return Tile.brickceilingcrack3;
		if (tiles[x + y * width] == Tile.col_roadconcreteupperleft) return Tile.roadconcreteupperleft;
		if (tiles[x + y * width] == Tile.col_roadconcreteupper) return Tile.roadconcreteupper;
		if (tiles[x + y * width] == Tile.col_roadconcreteupperright) return Tile.roadconcreteupperright;
		if (tiles[x + y * width] == Tile.col_roadconcretecenterleft) return Tile.roadconcretecenterleft;
		if (tiles[x + y * width] == Tile.col_roadconcretecenter) return Tile.roadconcretecenter;
		if (tiles[x + y * width] == Tile.col_roadconcretecenterright) return Tile.roadconcretecenterright;
		if (tiles[x + y * width] == Tile.col_roadconcretelowerleft) return Tile.roadconcretelowerleft;
		if (tiles[x + y * width] == Tile.col_roadconcretelowercenter) return Tile.roadconcretelowercenter;
		if (tiles[x + y * width] == Tile.col_roadconcretelowerright) return Tile.roadconcretelowerright;
		if (tiles[x + y * width] == Tile.col_roadhorizontal) return Tile.roadhorizontal;
		if (tiles[x + y * width] == Tile.col_roadverticle) return Tile.roadverticle;
		if (tiles[x + y * width] == Tile.col_brickceilingstandalone) return Tile.brickceilingstandalone;
		if (tiles[x + y * width] == Tile.col_brickceilingupperleft) return Tile.brickceilingupperleft;
		if (tiles[x + y * width] == Tile.col_brickceilingupper) return Tile.brickceilingupper;
		if (tiles[x + y * width] == Tile.col_brickceilingupperright) return Tile.brickceilingupperright;
		if (tiles[x + y * width] == Tile.col_brickceilingcenterleft) return Tile.brickceilingcenterleft;
		if (tiles[x + y * width] == Tile.col_brickceilingcenter) return Tile.brickceilingcenter;
		if (tiles[x + y * width] == Tile.col_brickceilingcenterright) return Tile.brickceilingcenterright;
		if (tiles[x + y * width] == Tile.col_brickceilinglowerleft) return Tile.brickceilinglowerleft;
		if (tiles[x + y * width] == Tile.col_brickceilinglower) return Tile.brickceilinglower;
		if (tiles[x + y * width] == Tile.col_brickceilinglowerright) return Tile.brickceilinglowerright;
		if (tiles[x + y * width] == Tile.col_brickceiling) return Tile.brickceiling;
		if (tiles[x + y * width] == Tile.col_bricksinglewallhorizontal) return Tile.bricksinglewallhorizontal;
		if (tiles[x + y * width] == Tile.col_bricksinglewallvertical) return Tile.bricksinglewallvertical;
		if (tiles[x + y * width] == Tile.col_bricksinglewallupper) return Tile.bricksinglewallupper;
		if (tiles[x + y * width] == Tile.col_bricksinglewallcenter) return Tile.bricksinglewallcenter;
		if (tiles[x + y * width] == Tile.col_bricksinglewalllower) return Tile.bricksinglewalllower;
		if (tiles[x + y * width] == Tile.col_bricksinglewallleft) return Tile.bricksinglewallleft;
		if (tiles[x + y * width] == Tile.col_bricksinglewallright) return Tile.bricksinglewallright;
		if (tiles[x + y * width] == Tile.col_bricksinglewalltright) return Tile.bricksinglewalltright;
		if (tiles[x + y * width] == Tile.col_bricksinglewalltlower) return Tile.bricksinglewalltlower;
		if (tiles[x + y * width] == Tile.col_bricksinglewalltleft) return Tile.bricksinglewalltleft;
		if (tiles[x + y * width] == Tile.col_bricksinglewalltupper) return Tile.bricksinglewalltupper;
		if (tiles[x + y * width] == Tile.col_concretemoss) return Tile.concretemoss;
		if (tiles[x + y * width] == Tile.col_concretecrack1) return Tile.concretecrack1;
		if (tiles[x + y * width] == Tile.col_concretecrack2) return Tile.concretecrack2;
		if (tiles[x + y * width] == Tile.col_concretefirehydrant) return Tile.concretefirehydrant;
		if (tiles[x + y * width] == Tile.col_brickwallstandalone) return Tile.brickwallstandalone;
		if (tiles[x + y * width] == Tile.col_brickwallleft) return Tile.brickwallleft;
		if (tiles[x + y * width] == Tile.col_brickwallcenter) return Tile.brickwallcenter;
		if (tiles[x + y * width] == Tile.col_brickwallright) return Tile.brickwallright;
		if (tiles[x + y * width] == Tile.col_concrete) return Tile.concrete;

		if (tiles[x + y * width] == 0) return Tile.voidTile; // temporary for the vision class

		return Tile.voidTile; // default
	}

	// whatever is inside here is arbitrary. This method just spawns units
	public void addUnits(int numunits) {

		// spawns green units

		// add(new Grenadier(27, 13, false, true)); //constructor for unit (x, y, playerR?, playerB?)
		// add(new Marine(26, 10, false, true));
		// add(new Marine(26, 12, false, true));
		// add(new Marine(26, 14, false, true));
		// add(new Zombie(26, 10, false, true));
		// add(new Zombie(26, 12, false, true));
		// add(new Zombie(26, 14, false, true));
		// add(new Zombie(26, 10, false, true));

		// add(new Tank(25, 11, false, true));
		// add(new Tank(25, 13, false, true));
		// add(new Tank(25, 15, false, true));
		// add(new Sniper(27, 15, false, true));
		// add(new Assassin(27, 11, false, true));

		// spawns red units
		// add(new Tank(5, 11, true, false)); // constructor for unit (x, y, playerR?, playerB?)
		// add(new Tank(5, 9, true, false));
		// add(new Tank(5, 7, true, false));
		// add(new Grenadier(3, 9, true, false));
		// add(new Marine(4, 6, true, false));
		// add(new Marine(4, 8, true, false));
		// add(new Marine(4, 10, true, false));
		// add(new Marine(4, 12, true, false));
		// add(new Sniper(3, 11, true, false));
		// add(new Assassin(3, 7, true, false));

	}

	public void addDefaultUnits() { // add default placed units, only happens if neither player adds any units

		boolean redTeamDown = redMarine + redTank + redGrenadier + redSniper + redAssassin + redRocketeer + redScout + redMachineGunner + redMedic == 0;
		boolean greenTeamDown = greenMarine + greenTank + greenGrenadier + greenSniper + greenAssassin + greenRocketeer + greenScout + greenMachineGunner + greenMedic == 0;
		addVisionTowerDefaultUnits();

		if (redTeamDown) {
			addRedDefaultUnits();
		}
		if (game.mode.equals("pvp") && greenTeamDown) {
			addGreenDefaultUnits();
		} else if (game.mode.equals("zombie")) {
			addZombieDefaultUnits();
		}

	}

	public void addRedDefaultUnits() {
		// constructor for unit (x, y, playerR?, playerB?)
		add(new Tank(5, 11, true, false));
		add(new Tank(5, 9, true, false));
		add(new Grenadier(4, 10, true, false));
		add(new Marine(4, 8, true, false));
		add(new Marine(4, 12, true, false));
		add(new Sniper(3, 9, true, false));
		add(new Assassin(3, 11, true, false));
	}

	public void addGreenDefaultUnits() {
		add(new Grenadier(26, 12, false, true)); // constructor for unit (x, y, playerR?, playerB?)
		add(new Marine(26, 10, false, true));
		add(new Marine(26, 14, false, true));
		add(new Tank(25, 11, false, true));
		add(new Tank(25, 13, false, true));
		add(new Sniper(27, 13, false, true));
		add(new Assassin(27, 11, false, true));
	}

	public void addZombieDefaultUnits() {
		
		int numZombies = 15; //number of zombies spawned
		
		for(int i = 0; i < numZombies; i++) {
			int zombieX = 18 + random.nextInt(10);
			int zombieY = 2 + random.nextInt(16);
			int zombieSwarm = 1 + random.nextInt(1);
			add(new Zombie(zombieX, zombieY, false, true, zombieSwarm));
		}
		
		/*add(new Zombie(12, 10, false, true, 1));
		add(new Zombie(12, 14, false, true, 1));
		add(new Zombie(12, 10, false, true, 1));
		add(new Zombie(12, 14, false, true, 1));
		add(new Zombie(12, 12, false, true, 1));
		add(new Zombie(17, 10, false, true, 1));
		add(new Zombie(17, 14, false, true, 2));
		add(new Zombie(15, 10, false, true, 2));
		add(new Zombie(15, 14, false, true, 2));
		add(new Zombie(16, 12, false, true, 2));
		add(new Zombie(12, 10, false, true, 2));
		add(new Zombie(12, 14, false, true, 2));
		add(new Zombie(12, 12, false, true, 2));*/
	}

	public void addVisionTowerDefaultUnits() {
		if(game.mode.equals("pvp")) {
			add(new VisionTower(15, 18, false, false));
			add(new VisionTower(15, 10, false, false));
			add(new VisionTower(15, 2, false, false));
			add(new VisionTower(5, 3, false, false));
			add(new VisionTower(5, 16, false, false));
			add(new VisionTower(26, 16, false, false));
			add(new VisionTower(26, 3, false, false));
			add(new Fort(redFortX, redFortY, true, false));
			add(new Fort(greenFortX, greenFortY, false, true));
		} else if(game.mode.equals("zombie")) {
			add(new VisionTower(15, 20, false, false));
			add(new VisionTower(15, 10, false, false));
			add(new VisionTower(18, 2, false, false));
			add(new VisionTower(4, 3, false, false));
			add(new VisionTower(4, 28, false, false));
			add(new VisionTower(26, 13, false, false));
			add(new VisionTower(31, 6, false, false));	
			add(new VisionTower(35, 18, false, false));		
			add(new VisionTower(35, 26, false, false));		
			add(new VisionTower(29, 34, false, false));		
			add(new VisionTower(12, 34, false, false));	
		}
	}

	// removes units from the arraylists! Very important to do this! Basically insures that they will never be rendered/updated again.
	public void removeUnits() {

		for (int i = 0; i < greenTeam.size(); i++) { // cycles through all "removed" units and actually removes them from array.
			Unit p = (Unit) greenTeam.get(i);
			if (p.isRemoved() || p.getHealth() <= 0) {
				greenTeam.remove(i);
				if (p.getClass().toString().equals("class units.Tank")) {
					greenTank--;
				} else if (p.getClass().toString().equals("class units.Marine")) {
					greenMarine--;
				} else if (p.getClass().toString().equals("class units.Grenadier")) {
					greenGrenadier--;
				} else if (p.getClass().toString().equals("class units.Sniper")) {
					greenSniper--;
				} else if (p.getClass().toString().equals("class units.Assassin")) {
					greenAssassin--;
				} else if (p.getClass().toString().equals("class units.Rocketeer")) {
					greenRocketeer--;
				} else if (p.getClass().toString().equals("class units.Scout")) {
					greenScout--;
				} else if (p.getClass().toString().equals("class units.MachineGunner")) {
					greenMachineGunner--;
				} else if (p.getClass().toString().equals("class units.Medic")) {
					greenMedic--;
				} else if (p.getClass().toString().equals("class units.Zombie")) {
					greenZombie--;
				} else if (p.getClass().toString().equals("class units.Fort")) {
					greenFort--;
				}
			}
		}

		for (int i = 0; i < redTeam.size(); i++) { // cycles through all "removed" units and actually removes them from array.
			Unit p = (Unit) redTeam.get(i);
			if (p.isRemoved() || p.getHealth() <= 0) {
				redTeam.remove(i);
				if (p.getClass().toString().equals("class units.Tank")) {
					redTank--;
				} else if (p.getClass().toString().equals("class units.Marine")) {
					redMarine--;
				} else if (p.getClass().toString().equals("class units.Grenadier")) {
					redGrenadier--;
				} else if (p.getClass().toString().equals("class units.Sniper")) {
					redSniper--;
				} else if (p.getClass().toString().equals("class units.Assassin")) {
					redAssassin--;
				} else if (p.getClass().toString().equals("class units.Rocketeer")) {
					redRocketeer--;
				} else if (p.getClass().toString().equals("class units.Scout")) {
					redScout--;
				} else if (p.getClass().toString().equals("class units.MachineGunner")) {
					redMachineGunner--;
				} else if (p.getClass().toString().equals("class units.Medic")) {
					redMedic--;
				} else if (p.getClass().toString().equals("class units.Zombie")) {
					redZombie--;
				} else if (p.getClass().toString().equals("class units.Fort")) {
					redFort--;
				}
			}
		}

		for (int i = 0; i < entities.size(); i++) { // cycles through all "removed" units and actually removes them from array.
			Unit p = (Unit) entities.get(i);
			if (p.isRemoved() || p.getHealth() <= 0) {
				entities.remove(i);
			}
		}
	}

	public void addRedTower(VisionTower tower) {
		redTeam.add(tower);
	}

	public void addGreenTower(VisionTower tower) {
		greenTeam.add(tower);
	}

	public void removeRedTower(VisionTower tower) {
		redTeam.remove(tower);
		entities.remove(tower);
	}

	public void removeGreenTower(VisionTower tower) {
		greenTeam.remove(tower);
		entities.remove(tower);
	}

	public static int getTime() {
		return time;
	}

	public int getTurn() {
		return turn;
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Unit> getRedTeam() {
		return redTeam;
	}

	public List<Unit> getGreenTeam() {
		return greenTeam;
	}

	public int getXScroll() {
		return xScroll;
	}

	public int getYScroll() {
		return yScroll;
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	public void switchTurn() { // switches from red to green's turn, or green to red's turn
		if (turn == 1) {
			turn = 2;
		} else if (turn == 2) {
			turn = 1;
		}
	}

	public void setCurrentTime() { // sets currentTime equal to time
		currentTime = time;
	}

	public int getLastTurn() {
		// TODO Auto-generated method stub
		return lastTurn;
	}

}