package folder;


import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import buttons.Button;
import buttons.HelpButton;
import buttons.InfoButton;
import buttons.Inter;
import buttons.MenuButton;
import buttons.NextButton;
import buttons.SelectionButton;
import buttons.SettingsButton;

public class Game extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//changing width greatly affects FPS! 300 is optimal
	private static int width = 400; // IF YOU WANT TO ACCESS WIDTH, HEIGHT OR SCALE. Use getWindowWidth, getWindowHeight, getWindowScale!
	private static int height = width * 9 / 16;
	public static int scale = 3; //modifying the scale variable in order to change zoom does not work.. Maybe adjust the screen class?
	//scale variable made public by kenny
	
	private Thread thread; //threads are a new concept. They allow multiple tasks to run simultaneously which is important for games
	private JFrame frame;
	private Keyboard key;
	private Level level; //only need 1 level at a time
	private Player player; //kenny changed to public for interface
	
	private Inter inter;
	public List<Inter> buttons;
	private Inter button1;
	private Inter button2;
	private Inter button3;
	private Inter button4;
	private Inter button5;
	public List<Inter> helpbuttons;
	private Inter helpbutton1;
	private Inter helpinterface1;
	private Inter helpbutton2;
	private Inter helpbutton3;
	private Inter helpbutton4;
	public List<Inter> selectionbuttons;
	private Inter selectioninterface1; //refers to buttons that appear in the placement phase
	private Inter selectioninterface2;
	private Inter selectionbutton3;
	private Inter selectionbutton4;
	private Inter selectionbutton5;
	private Inter selectionbutton6;
	private Inter selectionbutton7;
	private Inter selectionbutton8;
	private Inter selectionbutton9;
	public List<Inter> settingsbuttons;
	private Inter settingsbutton1;
	private Inter settingsinterface1;
	private Inter settingsbutton2;
	public List<Inter> menubuttons;
	private Inter menuinterface1;
	private Inter menubutton1;
	private Inter menubutton2;
	private Inter menubutton3;
	private Inter menutitlebutton;
	private Inter menutopbutton;
	private Inter menubottombutton;
	public List<Inter> nextbuttons;
	private Inter nextbutton1;
	private Inter nextbutton2;
	public List<Inter> infobuttons;
	private Inter infobutton1;
	private Inter infobutton2;
	private Inter infobutton3;
	private Inter infobutton4;
	private Inter infobutton5;
	private Inter infobutton6;
	
	public boolean wButtonPressed; //detects if s is pressed, and so on for the other 2 booleans
	public boolean qButtonPressed;
	public boolean eButtonPressed;
	public boolean rButtonPressed;
	
	private Boolean running = false;
	public Boolean menu = true; //determines whether the main menu is displayed
	public String mode;
	public Boolean selecting = false; //whether the units are currently being selected or not
	public int selectingTurn = 1; //who's turn it is to select units
	public int unitCountSequence = 0; //determines which set of units is displayed in the unit count
	public int unitPlacementSequence = 0; //determines which set of units is displayed in the top interface
	
	public int redMoney = 1600;
	public int greenMoney = 1600;
	public boolean endTurnClicked = false;
	
	private Screen screen;
	
	private int xScroll = 0;
	private int yScroll = 0;
	
	private BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); //BufferedImage class very useful
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData(); //allows you to convert BufferedImage into an integer array that you can actually modify
	
	
	//levels
		//public static Level map_ian; //best map
		public static Level map_1;
		public static Level map_zombie;
		//public static Level map_kevin;
		public static Level map_test;
		//public static Level map_GPI;
	
	public Game() {
		Dimension size = new Dimension(width*scale, height*scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height); //multiply these by scale in order to zoom?
		frame = new JFrame();
		key = new Keyboard(); //variable that records keystrokes
		addKeyListener(key); //this is important. easy to forget, cuz I didn't actually code for this method. It is from the subclass Component
		
		Mouse mouse = new Mouse();
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		
		TileCoordinate playerSpawn = new TileCoordinate(10, 10); //stores the playerspawning position inside a TileCoordinate object. This is useful. The coordinates -2, -2 put the player off the map.

		/*The next set of objects are the buttons that make up the interface
		 * They are then added to an array list of buttons so that iterating through the buttons is easier
		 * 
		 */
		
		//Inter = interfaces
		//Button = buttons on the bottom section of interface
		//NextButton = buttons that have the word next on them and cause sequences
		//HelpButton = buttons that are on the help interface
		//SelectionButton = buttons that are on the top section of the interface
		//SettingsButton = buttons that are on the settings interface
		//MenuButton = buttons that are on the menu
		
		inter = new Inter(this, key, -16, height - 50, width + 32, 66, 0);
		button1 = new Button(this, key, 85, height - 42, 50, 15, 0);
		button2 = new Button(this, key, 145, height - 42, 50, 15, 0);
		button3 = new Button(this, key, 85, height - 22, 50, 15, 0);
		button4 = new Button(this, key, 145, height - 22, 50, 15, 1);
		button5 = new Button(this, key, 326, height - 14, 75, 15, 2);
		nextbutton1 = new NextButton(this, key, 288, height - 11, 30, 9, 0);
		helpbutton1 = new HelpButton(this, key, 73, height - 50, 7, 7, 0);
		helpinterface1 = new Inter(this, key, 73, height - 199, 249, 150, 1);
		helpbutton2 = new HelpButton(this, key, 73, height - 169, 55, 15, 1);
		helpbutton3 = new HelpButton(this, key, 73, height - 155, 55, 15, 1);
		helpbutton4 = new HelpButton(this, key, 73, height - 141, 55, 15, 1);
		selectioninterface1 = new Inter(this, key, 326, -1, 75, 22, 2);
		selectioninterface2 = new Inter(this, key, -1, -1, 238, 30, 2);
		selectionbutton3 = new SelectionButton(this, key, 8, 2, 20, 19, 0); //selection buttons 3-7 are the squares on the top right
		selectionbutton4 = new SelectionButton(this, key, 36, 2, 20, 19, 0);
		selectionbutton5 = new SelectionButton(this, key, 64, 2, 20, 19, 0);
		selectionbutton6 = new SelectionButton(this, key, 92, 2, 20, 19, 0);
		selectionbutton7 = new SelectionButton(this, key, 120, 2, 20, 19, 0);
		selectionbutton8 = new SelectionButton(this, key, 186, 6, 42, 15, 1);
		selectionbutton9 = new SelectionButton(this, key, 8, 2, 20, 19, 2);
		nextbutton2 = new NextButton(this, key, 148, 7, 30, 13, 1);
		settingsbutton1 = new SettingsButton(this, key, 321, height - 50, 7, 7, 0);
		settingsinterface1 = new Inter(this, key, 321, height - 149, 65, 100, 3);
		settingsbutton2 = new SettingsButton(this, key, 330, height - 128, 46, 13, 1);
		infobutton1 = new InfoButton(this, key, 8, 28, 105, 140, 0);
		infobutton2 = new InfoButton(this, key, 36, 28, 105, 140, 1);
		infobutton3 = new InfoButton(this, key, 64, 28, 105, 140, 2);
		infobutton4 = new InfoButton(this, key, 92, 28, 105, 140, 3);
		infobutton5 = new InfoButton(this, key, 120, 28, 105, 140, 4);
		infobutton6 = new InfoButton(this, key, 8, 28, 105, 140, 5);
		
		menuinterface1 = new Inter(this, key, -16, -16, width + 32, height + 32, 4);
		menubutton1 = new MenuButton(this, key, 5*width/12, 2*height/3, width/6, height/12, 0);
		menubutton2 = new MenuButton(this, key, 3*width/12, 2*height/3, width/6, height/12, 1);
		menubutton3 = new MenuButton(this, key, 7*width/12, 2*height/3, width/6, height/12, 2);
		menutitlebutton = new Inter(this, key, width/4, height/4, width/2, height/4, 5);
		menutopbutton = new Inter(this, key, -1, 0, width + 32, height/12 + 2, 5);
		menubottombutton = new Inter(this, key, -1, 11*height/12, width + 32, height/12 + 2, 5);
		
		buttons = new ArrayList<Inter>();
		helpbuttons = new ArrayList<Inter>();
		selectionbuttons = new ArrayList<Inter>();
		settingsbuttons = new ArrayList<Inter>();
		nextbuttons = new ArrayList<Inter>();
		infobuttons = new ArrayList<Inter>();
		buttons.add(button1);
		buttons.add(button2);
		buttons.add(button3);
		buttons.add(button4);
		buttons.add(button5);
		nextbuttons.add(nextbutton1);
		nextbuttons.add(nextbutton2);
		helpbuttons.add(helpbutton1);
		helpbuttons.add(helpinterface1);
		helpbuttons.add(helpbutton2);
		helpbuttons.add(helpbutton3);
		helpbuttons.add(helpbutton4);
		selectionbuttons.add(selectioninterface1);
		selectionbuttons.add(selectioninterface2);
		selectionbuttons.add(selectionbutton3);
		selectionbuttons.add(selectionbutton4);
		selectionbuttons.add(selectionbutton5);
		selectionbuttons.add(selectionbutton6);
		selectionbuttons.add(selectionbutton7);
		selectionbuttons.add(selectionbutton8);
		selectionbuttons.add(selectionbutton9);
		settingsbuttons.add(settingsbutton1);
		settingsbuttons.add(settingsinterface1);
		settingsbuttons.add(settingsbutton2);
		infobuttons.add(infobutton1);
		infobuttons.add(infobutton2);
		infobuttons.add(infobutton3);
		infobuttons.add(infobutton4);
		infobuttons.add(infobutton5);
		infobuttons.add(infobutton6);
		
		menubuttons = new ArrayList<Inter>();
		menubuttons.add(menuinterface1);
		menubuttons.add(menubutton1);
		menubuttons.add(menubutton2);
		menubuttons.add(menubutton3);
		menubuttons.add(menutitlebutton);
		menubuttons.add(menutopbutton);
		menubuttons.add(menubottombutton);
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).visible = true;
		}
		helpbutton1.visible = true;
		menubutton1.visible = true; //makes the play button visible from the start since not all menu buttons are visible
		settingsbutton1.visible = true;
		settingsbutton2.clicked = true;
		
		player = new Player(playerSpawn.x(), playerSpawn.y());
		player.setScale(scale);
		
		//maps
		//map_GPI = new SpawnLevel(this, "/textures/map_GPI.png", player, screen, key); //initialized all the levels for some reason...idk...could be a problem at some point. for now tho, it just uses a bit of CPU
		//map_kevin = new SpawnLevel(this, "/textures/mappie.png", player, screen, key);
		map_test = new SpawnLevel(this, "/textures/map_test.png", player, screen, key);
		map_1 = new SpawnLevel(this, "/textures/map_1.png", player, screen, key);
		//map_ian = new SpawnLevel(this, "/textures/map_ian.png", player, screen, key); //best map
		map_zombie = new SpawnLevel(this, "/textures/map_zombie.png", player, screen, key);

		
		level = map_test;//loads the level file from the folder res. Level.map1 is a Level instantiated in the Level class.		
		
		
		//Sound.menu_theme.loop(); //plays background music
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
		
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();                      //exits out of program
		}
	}
	
	public void run() { //this is the code that is constantly running during the game until running==false. It automatically runs cuz its linked to "implements runnable"
		long lastTime = System.nanoTime(); // tells the time at which the game started
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0; // # of nanoseconds in  second divided by frames per second we want (nanoseconds per frame)
		double delta = 0;
		int frames = 0; //measure frames/second
		int updates = 0; //measure updates/second
		
		requestFocus(); //when the window opens, the keys are already focused into it.
		while (running) {
			long now = System.nanoTime(); // this value is different than lastTime, cuz time passes in 4 lines of code
			delta += (now - lastTime) / ns; //adds the time 
			lastTime = now;
			while (delta >= 1) { // whenever delta equals 1, update and subtract 1
				update();
				updates++;
				delta--;
			}
			if(frames % 500000 == 0) render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) { //forces the inside of the if loop to occur once per second
				timer+= 1000;
				System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle("Fog Of War  ||   " + updates + " ups, " + frames + " fps");
				
				updates = 0;
				frames = 0;
			}
		}
	}
	
	//Updates all the objects in the game
	public void update(){

		if(menubuttons.get(1).mouseClicked == true) { //if you click the play button, then the zombie and pvp buttons will show up
			menubuttons.get(1).visible = false;
			menubuttons.get(2).visible = true;
			menubuttons.get(3).visible = true;
		} else if(menubuttons.get(2).mouseClicked == true) {
			menubuttons.get(2).visible = false;
			menubuttons.get(3).visible = false;
			menu = false;
			selecting = true;
			
			level = map_1;
			player.init(level);
			mode = "pvp";
			
			resetInterface();
			//Sound.menu_theme.stop();
			//Sound.selection_theme.loop(); 
			/*for(int i = 0; i < buttons.size(); i++) { //this method is being worked so that we don't have to activate updateInterface() during the menu phase
				buttons.get(i).x += level.getXScroll();
				buttons.get(i).y += level.getYScroll();
			} */
		} else if(menubuttons.get(3).mouseClicked == true) {
			menubuttons.get(2).visible = false;
			menubuttons.get(3).visible = false;
			menu = false;
			selecting = true;
			
			level = map_zombie;
			mode = "zombie";
			player.init(level);
			
			resetInterface();
			//Sound.menu_theme.stop();
			//Sound.selection_theme.loop();
		}
		
		key.update(); // constantly checks if any keys are being pressed
		if(menu == false) level.update(); //handles AI, projectiles, Tiles, etc.
		if(menu == true) level.scroll();
		player.update(); //calls update method of player class. basically outsources the keyboard tracking.
		updateMenu();
		updateInterface();
		
		if(menu == false) {
			if(selecting == false) {
				//Sound.map_theme.stop();
				//Sound.death.loop(); 
				gameOver(gameCondition());
			}
		
			if((button5.clicked == true) && endTurnClicked == false && selecting == true) { //determines if the end turn button is clicked during placement phase
				if(selectingTurn == 1) {
					selectingTurn = 2; //if it's red's selecting phase, then change to green's selecting phase
					level.switchTurn();
				} else if(selectingTurn == 2) {
					selecting = false; //if it's green's selecting phase, then change it to the regular game phase
					level.switchTurn();
					level.setCurrentTime(); //sets the currentTime to the time in the level class, just to make sure you don't accidentally switch the turn when you press end turn
				
					//if there are no units placed on the map when the game starts, place default units
					level.addDefaultUnits();
					//Sound.selection_theme.stop(); 
					//Sound.turn_theme.loop();
				}
				endTurnClicked = true;
			} else if((button5.clicked == false) && selecting == true) {
				endTurnClicked = false;
			}
			
			if(mode.equals("zombie") && selecting == true && selectingTurn == 2) { //makes sure that if it is zombie mode, the selection phase immediately ends when end turn is clicked
				selecting = false;
				level.switchTurn();
				level.setCurrentTime();
				
				level.addDefaultUnits();
				//Sound.selection_theme.stop();
				//Sound.turn_theme.loop();
			}
		}
	}
	
	//Updates all the interface buttons based on whether they were clicked or not
	public void updateMenu() {
		for(Inter button: menubuttons) {
			button.update();
		}
	}
	
	public void updateInterface() {
		
		updateKeyboardInteractions();


		inter.update();
		for(Inter button: buttons) {
			button.update();
		}
		for(Inter button: selectionbuttons) {
			button.update();
		}
		for(Inter button: helpbuttons) {
			button.update();
		}
		for(Inter button: settingsbuttons) {
			button.update();
		}
		for(Inter button: nextbuttons) {
			button.update();
		}
		for(Inter button: infobuttons) {
			button.update();
		}
		
	}
	
	//detects keyboard presses and updates the buttons based on them
	public void updateKeyboardInteractions() { 
		
		boolean wButtonPressed = buttons.get(1).getKeyboard().lastKey == 87; //detects if s is pressed, and so on for the other 2 booleans
		boolean qButtonPressed = buttons.get(1).getKeyboard().lastKey == 81;
		boolean eButtonPressed = buttons.get(1).getKeyboard().lastKey == 69;
		boolean rButtonPressed = buttons.get(1).getKeyboard().lastKey == 82;

		if(wButtonPressed == true) {
			buttons.get(0).clicked = false; //to make the other buttons's clicked variable false
			buttons.get(1).clicked = true;
			buttons.get(2).clicked = false;
			buttons.get(1).getKeyboard().lastKey = 0;
		}
		
		if(qButtonPressed == true) {
			buttons.get(0).clicked = true; //to make the other buttons's clicked variable false
			buttons.get(1).clicked = false;
			buttons.get(2).clicked = false;
			buttons.get(1).getKeyboard().lastKey = 0;
		}
		
		if(eButtonPressed == true) {
			buttons.get(0).clicked = false; //to make the other buttons's clicked variable false
			buttons.get(1).clicked = false;
			buttons.get(2).clicked = true;
			buttons.get(1).getKeyboard().lastKey = 0;
		}
		
		if(rButtonPressed == true) { //cancel button deselects all the other buttons
			buttons.get(0).clicked = false;
			buttons.get(1).clicked = false;
			buttons.get(2).clicked = false;
			buttons.get(1).getKeyboard().lastKey = 0;
			
			if(level.getTurn() == 1) {
				for(int i = 0; i < level.getRedTeam().size(); i++) {
					level.getRedTeam().get(i).selected = false;
					level.getRedTeam().get(i).action = 0;
				}
			} else if(level.getTurn() == 2) {
				for(int i = 0; i < level.getGreenTeam().size(); i++) {
					level.getGreenTeam().get(i).selected = false;
					level.getGreenTeam().get(i).action = 0;
				}
			}
		}
	}
	
	//detects whether red wins, green wins, tie, or game still is going
	public int gameCondition() {
		
		boolean redTeamDown = level.redMarine + level.redTank + level.redGrenadier + level.redSniper + level.redAssassin + level.redRocketeer + level.redScout + level.redMachineGunner + level.redMedic == 0;
		boolean greenTeamDown = level.greenMarine + level.greenTank + level.greenGrenadier + level.greenSniper + level.greenAssassin + level.greenRocketeer + level.greenScout + level.greenMachineGunner + level.greenMedic == 0;
		
		if(mode.equals("pvp")) {
			if(redTeamDown || level.redFort == 0) {
				if(greenTeamDown || level.greenFort == 0) {
					return 3; //tie game
				} else {
					return 1; //green wins
				}
			} else if(greenTeamDown || level.greenFort == 0) {
				return 2; //red wins
			}
		} else if(mode.equals("zombie")) {
			if(redTeamDown) { //only requires redTeamDown to be true since there will be 0 forts in zombie mode
				if(level.greenZombie == 0) {
					return 3; //tie game
				} else {
					return 1; //green wins
				}
			} else if(level.greenZombie == 0) {
				return 2; //red wins
			}
		}
		
		return 0; //if nothing happens, then game keeps going
	}
	
	//This method is called when the game detects that someone has won
	public void gameOver(int gameCondition) {
		if(gameCondition == 1) {
			if(mode.equals("pvp")) {
				JOptionPane.showMessageDialog(this, "Green Team Wins!", "Game Over", JOptionPane.YES_NO_OPTION);
			} else if(mode.equals("zombie")) {
				JOptionPane.showMessageDialog(this, "Zombies Win!", "Game Over", JOptionPane.YES_NO_OPTION);
			}
			//Sound.map_theme.stop();
			//Sound.death.loop();
			System.exit(ABORT);
		} else if(gameCondition == 2) {
			JOptionPane.showMessageDialog(this, "Red Team Wins!", "Game Over", JOptionPane.YES_NO_OPTION);
			//Sound.map_theme.stop();
			//Sound.death.loop();
			System.exit(ABORT);
		} else if(gameCondition == 3) {
			JOptionPane.showMessageDialog(this, "Tie Game!", "Game Over", JOptionPane.YES_NO_OPTION);
			//Sound.map_theme.stop();
			//Sound.death.loop();
			System.exit(ABORT);
		}
	
	}
	
	
	public void resetInterface() {
	
		inter.x = inter.xFinal;
		inter.y = inter.yFinal;
		
		for(int i = 0; i < buttons.size(); i++) {
			buttons.get(i).x = buttons.get(i).xFinal;
			buttons.get(i).y = buttons.get(i).yFinal;
		}
		for(int i = 0; i < settingsbuttons.size(); i++) {
			settingsbuttons.get(i).x = settingsbuttons.get(i).xFinal;
			settingsbuttons.get(i).y = settingsbuttons.get(i).yFinal;
		}
		for(int i = 0; i < selectionbuttons.size(); i++) {
			selectionbuttons.get(i).x = selectionbuttons.get(i).xFinal;
			selectionbuttons.get(i).y = selectionbuttons.get(i).yFinal;
		}
		for(int i = 0; i < helpbuttons.size(); i++) {
			helpbuttons.get(i).x = helpbuttons.get(i).xFinal;
			helpbuttons.get(i).y = helpbuttons.get(i).yFinal;
		}
		for(int i = 0; i < nextbuttons.size(); i++) {
			nextbuttons.get(i).x = nextbuttons.get(i).xFinal;
			nextbuttons.get(i).y = nextbuttons.get(i).yFinal;
		}
		for(int i = 0; i< infobuttons.size(); i++) {
			infobuttons.get(i).x = infobuttons.get(i).xFinal;
			infobuttons.get(i).y = infobuttons.get(i).yFinal;
		}
	}
	
	/*public void scroll() {
		if (player.x < -4 && xScroll >= -100) {
			xScroll -= 3;
			menubuttons.get(0).xVelocity = -3;
			menubuttons.get(1).xVelocity = -3;
		} else if (player.x + 28 > screen.width && xScroll <= width * 16 - 100) { // 32 is for the size of the reticle
			xScroll += 3;
			menubuttons.get(0).xVelocity = 3;
			menubuttons.get(1).xVelocity = 3;
		} else {
			menubuttons.get(0).xVelocity = 0;
			menubuttons.get(1).xVelocity = 0;
		}
		if (player.y < -4 && yScroll >= -100) {
			yScroll -= 3;
			menubuttons.get(0).yVelocity = -3;
			menubuttons.get(1).yVelocity = -3;
		} else if (player.y + 20 > screen.height && yScroll <= height * 16 - 100) { // accounts for the additional offset because of the GUI
			yScroll += 3;
			menubuttons.get(0).yVelocity = 3;
			menubuttons.get(1).yVelocity = 3;
		} else {
			menubuttons.get(0).xVelocity = 0;
			menubuttons.get(1).xVelocity = 0;
		}

		screen.setOffset(xScroll, yScroll);
	}*/
				
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.clear();
		
		Graphics g = bs.getDrawGraphics();
		Color darkYellow = new Color(122, 99, 48);
		Color darkGreen = new Color(16, 176, 16);
		Color darkGrey = new Color(92, 89, 85);
		
		if(menu == true) { //menu text
			renderMenu();
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
			for (int i = 0; i < pixels.length ; i++) {  //equates the pixels from screen to the buffered image which is what can display the image 
				pixels[i] = screen.pixels[i];
			}
			
			g.setFont(new Font("Lucida Console", 0, 72));
			g.setColor(Color.RED);
			g.drawString("Fog Of War", 381, 250);
			
			g.setFont(new Font("Lucida Console", 0, 36));
			g.setColor(Color.WHITE);
			g.drawString("Made by GPI", 480, 310);
			if(menubuttons.get(1).visible == true) {
				g.drawString("Play", 555, 489);
			} else if(menubuttons.get(2).visible == true) {
				g.drawString("PvP", 367, 489);
				g.drawString("Zombies", 723, 489);
			}
			
		} else if(menu == false) { //happens when you leave the menu
			level.render(screen); //by inputing xScroll and yScroll instead of player.x and player.y, we put our screen center on the character. 
			player.render(screen);
			renderInterface();
		
			//test UI. Interestingly enough, rendering in this way results in a 2-300 decrease in fps
			//Sprite sprite = new Sprite (40, height - 10, 0xff000000); //test. Interestingly enough, rendering a sprite in this way results in a 300 decrease in fps
			//screen.renderSprite(10, 10, sprite, false);
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null); //this is what actually draws the image.
		
			boolean unitSelected = false; //whether the unit is selected or not
			int unitIndex = 0; //index of the unit in the arraylist
			String team = ""; //the team of the unit that is selected
			//if(level.getTurn() == 1) {
				for(int i = 0; i < level.getRedTeam().size(); i++) { //checks if each unit in redTeam is selected or not
					if(level.getRedTeam().get(i).getSelected() == true && (level.getTurn() == 1 || level.getRedTeam().get(i).unitType == "Fort")) {
						screen.renderMob(inter.x - inter.xVelocity + 22, inter.y - inter.yVelocity + 7, level.getRedTeam().get(i).sprite); //renders the selected mob in the lower right part of the screen
						unitSelected = true;
						unitIndex = i; //identifying which unit it exactly is
						team = "Red";
					}
				}
			//} else if(level.getTurn() == 2) {
				for(int i = 0; i < level.getGreenTeam().size(); i++) {
					if(level.getGreenTeam().get(i).getSelected() == true && (level.getTurn() == 2 || level.getGreenTeam().get(i).unitType == "Fort")) {
						screen.renderMob(inter.x - inter.xVelocity + 22, inter.y - inter.yVelocity + 7, level.getGreenTeam().get(i).sprite);
						unitSelected = true;
						unitIndex = i;
						team = "Green";
					}
				}
			//}
		
			if(selecting == true) { //this stuff only appears during the placement phase of the game
				g.setFont(new Font("Lucida Console", 0, 20));
				g.setColor(darkYellow);
				if(selectingTurn == 1) {
					g.drawString("Money: $" + redMoney, 1000, 25);
				} else if(selectingTurn == 2) {
					g.drawString("Money: $" + greenMoney, 1000, 25);
				}
				g.drawString("Placement Phase", 1000, 50);

				g.setFont(new Font("Times New Roman", 0, 12));
				if(unitPlacementSequence == 0) {
					g.drawString("1", 74, 58); //displays 1, 2, 3, 4, 5
					g.drawString("2", 157, 58);
					g.drawString("3", 241, 58);
					g.drawString("4", 325, 58);
					g.drawString("5", 409, 58);
			
					g.drawString("$150", 41, 78);
					g.drawString("$300", 124, 78);
					g.drawString("$250", 208, 78);
					g.drawString("$200", 292, 78);
					g.drawString("$250", 376, 78);
				} else if(unitPlacementSequence == 1) {
					g.drawString("6", 74, 58);
					
					g.drawString("$200", 41, 78);
				}
			
				g.setFont(new Font("Lucida Console", 0, 26));
				g.drawString("Reset", 582, 49);
				
				g.setFont(new Font("Lucida Console", 0, 24));
				g.drawString("Next", 461, 48);
			
				if(selectingTurn == 1) {
					if(unitPlacementSequence == 0) {
						screen.renderMob(selectionbutton3.x - selectionbutton3.xVelocity + 2, selectionbutton3.y - selectionbutton3.yVelocity + 1, Sprite.marine_r_front_right);
						screen.renderMob(selectionbutton4.x - selectionbutton4.xVelocity + 2, selectionbutton4.y - selectionbutton4.yVelocity, Sprite.tank_r_right_frame_1);
						screen.renderMob(selectionbutton5.x - selectionbutton5.xVelocity + 2, selectionbutton5.y - selectionbutton5.yVelocity + 1, Sprite.grenadier_r_front_right);
						screen.renderMob(selectionbutton6.x - selectionbutton6.xVelocity + 2, selectionbutton6.y - selectionbutton6.yVelocity + 1, Sprite.sniper_r_front_right);
						screen.renderMob(selectionbutton7.x - selectionbutton7.xVelocity + 2, selectionbutton7.y - selectionbutton7.yVelocity + 1, Sprite.assassin_r_front_right);
					} else if(unitPlacementSequence == 1) {
						screen.renderMob(selectionbutton9.x - selectionbutton9.xVelocity + 2, selectionbutton9.y - selectionbutton9.yVelocity + 1, Sprite.rocketeer_r_front_right);
					}
				} else if(selectingTurn == 2) {
					if(unitPlacementSequence == 0) {
						screen.renderMob(selectionbutton3.x - selectionbutton3.xVelocity + 2, selectionbutton3.y - selectionbutton3.yVelocity + 1, Sprite.marine_g_front_right);
						screen.renderMob(selectionbutton4.x - selectionbutton4.xVelocity + 2, selectionbutton4.y - selectionbutton4.yVelocity, Sprite.tank_g_right_frame_1);
						screen.renderMob(selectionbutton5.x - selectionbutton5.xVelocity + 2, selectionbutton5.y - selectionbutton5.yVelocity + 1, Sprite.grenadier_g_front_right);
						screen.renderMob(selectionbutton6.x - selectionbutton6.xVelocity + 2, selectionbutton6.y - selectionbutton6.yVelocity + 1, Sprite.sniper_g_front_right);
						screen.renderMob(selectionbutton7.x - selectionbutton7.xVelocity + 2, selectionbutton7.y - selectionbutton7.yVelocity + 1, Sprite.assassin_g_front_right);
					} else if(unitPlacementSequence == 1) {
						screen.renderMob(selectionbutton9.x - selectionbutton9.xVelocity + 2, selectionbutton9.y - selectionbutton9.yVelocity + 1, Sprite.rocketeer_g_front_right);
					}
				}
				
				//Mouse over descriptions
				if(infobutton1.visible == true) { //Marine description
					g.setFont(new Font("Times New Roman", 0, 32));
					g.drawString("Marine", 135, 135);
					g.setFont(new Font("Times New Roman", 0, 16));
					g.drawString("The marine is your go-to man if you need", 75, 175);
					g.drawString("a unit who is decent at everything. It has good", 45, 195);
					g.drawString("damage, good mobility, good vision and a", 45, 215);
					g.drawString("respectable amount of health. Most of the time,", 45, 235);
					g.drawString("you can't go wrong with your trusty marine.", 45, 255);
					
					g.drawString("Health: 100", 45, 335);
					g.drawString("Fire Rate: 100", 180, 335);
					g.drawString("Damage: 15", 45, 360);
					g.drawString("Damage Type: Bullet", 180, 360);
					g.drawString("Move Speed: 0.5", 45, 385);
					g.drawString("Move Range: 150", 180, 385);
					g.drawString("Vision Range: 120", 45, 410);
					g.drawString("Vision Width: 1", 180, 410);
					g.drawString("Attack Range: 200", 45, 435);
				}
				
				else if(infobutton2.visible == true) { //Tank description
					g.setFont(new Font("Times New Roman", 0, 32));
					g.drawString("Tank", 230, 135);
					g.setFont(new Font("Times New Roman", 0, 16));
					g.drawString("The tank is an absolute powerhouse that", 160, 175);
					g.drawString("can heavily dent the enemy lineup if used", 130, 195);
					g.drawString("properly. It boasts high damage and extremely", 130, 215);
					g.drawString("high tankiness. However, tanks make up for", 130, 235);
					g.drawString("this with their very low vision and mobility.", 130, 255);
					g.drawString("Still, tanks are a force to be reckoned with.", 130, 275);
					
					g.drawString("Health: 300", 130, 335);
					g.drawString("Fire Rate: 100", 265, 335);
					g.drawString("Damage: 80", 130, 360);
					g.drawString("Damage Type: Shell", 265, 360);
					g.drawString("Move Speed: 0.2", 130, 385);
					g.drawString("Move Range: 100", 265, 385);
					g.drawString("Vision Range: 60", 130, 410);
					g.drawString("Vision Width: 0.8", 265, 410);
					g.drawString("Attack Range: 240", 130, 435);
				}
				
				else if(infobutton3.visible == true) { //Grenadier description
					g.setFont(new Font("Times New Roman", 0, 32));
					g.drawString("Grenadier", 290, 135);
					g.setFont(new Font("Times New Roman", 0, 16));
					g.drawString("The grenadier is an extremely potent", 245, 175);
					g.drawString("offensive threat that loves clumps of enemies.", 215, 195);
					g.drawString("He has above average health and great splash", 215, 215);
					g.drawString("damage, especially against tanks, but is", 215, 235);
					g.drawString("limited by accuracy and vision issues. Don't", 215, 255);
					g.drawString("underestimate the grenadier unless you want", 215, 275);
					g.drawString("your army wiped out before you even realize.", 215, 295);
					
					g.drawString("Health: 120", 215, 335);
					g.drawString("Fire Rate: 90", 350, 335);
					g.drawString("Splash Damage: 30", 215, 360);
					g.drawString("Splash Radius: 5", 350, 360);
					g.drawString("Move Speed: 0.5", 215, 385);
					g.drawString("Move Range: 150", 350, 385);
					g.drawString("Vision Range: 80", 215, 410);
					g.drawString("Vision Width: 0.8", 350, 410);
					g.drawString("Attack Range: 175", 215, 435);
					g.drawString("Damage Type: Explosives", 215, 460);
				}
				
				else if(infobutton4.visible == true) { //Sniper description
					g.setFont(new Font("Times New Roman", 0, 32));
					g.drawString("Sniper", 390, 135);
					g.setFont(new Font("Times New Roman", 0, 16));
					g.drawString("The sniper has the ability to wreck", 330, 175);
					g.drawString("havoc if you know where the enemy units are.", 300, 195);
					g.drawString("The sniper possesses very high damage and", 300, 215);
					g.drawString("attack range, though he is weak in many ways.", 300, 235);
					g.drawString("For example, he has low mobility, vision, and", 300, 255);
					g.drawString("health, but these are only an issue if the", 300, 275);
					g.drawString("enemy can even find this elusive sharpshooter.", 300, 295);
					
					g.drawString("Health: 75", 300, 335);
					g.drawString("Fire Rate: 600", 440, 335);
					g.drawString("Damage: 150", 300, 360);
					g.drawString("Damage Type: Bullet", 440, 360);
					g.drawString("Move Speed: 0.4", 300, 385);
					g.drawString("Move Range: 100", 440, 385);
					g.drawString("Vision Range: 100", 300, 410);
					g.drawString("Vision Width: 0.5", 440, 410);
					g.drawString("Attack Range: 300", 300, 435);
				}
				
				else if(infobutton5.visible == true) { //Assassin description
					g.setFont(new Font("Times New Roman", 0, 32));
					g.drawString("Assassin", 465, 135);
					g.setFont(new Font("Times New Roman", 0, 16));
					g.drawString("The assassin is the guy who loves to", 415, 175);
					g.drawString("shred through the enemy back lines. The", 385, 195);
					g.drawString("assassin deals extremely high damage and", 385, 215);
					g.drawString("possesses incredible mobility. However, the", 385, 235);
					g.drawString("assassin suffers from having a melee-only", 385, 255);
					g.drawString("range and extremely low health, making the", 385, 275);
					g.drawString("assassin a frail but still deadly killer.", 385, 295);
					
					g.drawString("Health: 1", 385, 335);
					g.drawString("Attack Range: 0", 525, 335);
					g.drawString("Damage: 1000", 385, 360);
					g.drawString("Damage Type: Melee", 525, 360);
					g.drawString("Move Speed: 2", 385, 385);
					g.drawString("Move Range: 250", 525, 385);
					g.drawString("Vision Range: 100", 385, 410);
					g.drawString("Vision Width: 1.3", 525, 410);
				}
				
				else if(infobutton6.visible == true) { //Rocketeer description
					g.setFont(new Font("Times New Roman", 0, 32));
					g.drawString("Rocketeer", 125, 135);
					g.setFont(new Font("Times New Roman", 0, 16));
					g.drawString("The rocketeer can pack a huge punch", 75, 175);
					g.drawString("when used effectively. It has both great single", 45, 195);
					g.drawString("target and splash damage that works very well", 45, 215);
					g.drawString("against tanks, as well as a high amount of", 45, 235);
					g.drawString("health. Though the rocketeer lacks attack range", 45, 255);
					g.drawString("and attack speed, he has truly an unparalleled", 45, 275);
					g.drawString("amount of attack power.", 45, 295);
					
					g.drawString("Health: 150", 45, 335);
					g.drawString("Fire Rate: 300", 180, 335);
					g.drawString("Damage: 60", 45, 360);
					g.drawString("Splash Damage: 30 (7.5)", 180, 360);
					g.drawString("Attack Range: 125", 45, 385);
					g.drawString("Splash Radius: 5 (10)", 180, 385);
					g.drawString("Move Speed: 0.4", 45, 410);
					g.drawString("Move Range: 120", 180, 410);
					g.drawString("Vision Range: 90", 45, 435);
					g.drawString("Vision Width: 1", 180, 435);
					g.drawString("Damage Type: Explosives", 45, 460);
				}
			}
		
			for (int i = 0; i < pixels.length ; i++) {  //equates the pixels from screen to the buffered image which is what can display the image 
				pixels[i] = screen.pixels[i];
			} // for some reason, putting this before the above if loops will cause the images not to show up when selected

			g.setFont(new Font("Times New Roman", 0, 18));
			g.setColor(Color.WHITE);
			g.drawString("?", 226, 541);
			g.drawString("!", 971, 541);
		
			if(unitSelected == true) { //if unit is selected, then display these stats for the unit
				g.setFont(new Font("Times New Roman", 0, 22));
				g.setColor(darkYellow);
				if(team.equals("Red")) {
					g.drawString(team + " " + level.getRedTeam().get(unitIndex).unitType, 80, 570); // says the team and the type of unit
				
					g.setFont(new Font("Times New Roman", 0, 14));
					g.setColor(Color.WHITE);
				
					g.drawString("HP: " + (int)level.getRedTeam().get(unitIndex).getHealth() + "/" + (int)level.getRedTeam().get(unitIndex).getMaxHealth(), 15, 615); // says the unit's health and max health
					g.drawString("Reload: " + (int)level.getRedTeam().get(unitIndex).getReload(), 130, 615); //says the reload time for the unit
					g.drawString("Speed: " + level.getRedTeam().get(unitIndex).getSpeed(), 15, 635);
					g.drawString("Damage: " + (int)level.getRedTeam().get(unitIndex).getDamage(), 130, 635);
					g.drawString("Range: " + (int)level.getRedTeam().get(unitIndex).getProjectileRange(), 15, 655);
				} else if(team.equals("Green")) {
					g.drawString(team + " " + level.getGreenTeam().get(unitIndex).unitType, 80, 570);
				
					g.setFont(new Font("Times New Roman", 0, 14));
					g.setColor(Color.WHITE);
				
					g.drawString("HP: " + (int)level.getGreenTeam().get(unitIndex).getHealth() + "/" + (int)level.getGreenTeam().get(unitIndex).getMaxHealth(), 15, 615);
					g.drawString("Reload: " + (int)level.getGreenTeam().get(unitIndex).getReload(), 130, 615);
					g.drawString("Speed: " + level.getGreenTeam().get(unitIndex).getSpeed(), 15, 635);
					g.drawString("Damage: " + (int)level.getGreenTeam().get(unitIndex).getDamage(), 130, 635);
					g.drawString("Range: " + (int)level.getGreenTeam().get(unitIndex).getProjectileRange(), 15, 655);
				}
			}
		
			//test UI. Interestingly enough, rendering in this way results in a 2-300 decrease in fps
			//Sprite sprite = new Sprite (40, height - 10, 0xff000000); //test. Interestingly enough, rendering a sprite in this way results in a 300 decrease in fps
			//screen.renderSprite(10, 10, sprite, false);
		
			for (int i = 0; i < pixels.length ; i++) {  //equates the pixels from screen to the buffered image which is what can display the image 
				pixels[i] = screen.pixels[i];
			}
		
		
			g.setFont(new Font("Lucida Console", 0, 26));
			g.setColor(Color.WHITE); //next 3 lines draw the font and color of the coordinate location thing.
			if(level.getTurn() == 1) {
				g.setColor(Color.RED);
				g.drawString("Turn: Red", 982, 607);
			} else if(level.getTurn() == 2) {
				g.setColor(darkGreen);
				g.drawString("Turn: Green", 982, 607);
			} else {
				g.drawString("Please wait.", 982, 607);
			}
			//if (!level.getTurn()) g.setColor(Color.RED); 
		
			g.setColor(Color.WHITE); //next 3 lines draw the font and color of the coordinate location thing.
			String minuteAdder = "";
			String secondAdder = "";
			if(level.getTime() / 3600 < 10) minuteAdder = "0"; //adds a zero if the minute value is less than 0
			if((level.getTime() % 3600) / 60 < 10) secondAdder = "0"; // same thing as the minuteAdder except for seconds
			g.drawString("Time  " + minuteAdder + (level.getTime() / 3600) + ":" + secondAdder + ((level.getTime() % 3600) / 60), 982, 576);
		
			g.setColor(darkYellow);
			g.setFont(new Font("Lucida Console", 0, 26));
			g.drawString("Move", 299, 581);
			g.drawString("Attack", 464, 581);
			g.drawString("End Turn", 1027, 666);
			g.setFont(new Font("Lucida Console", 0, 18));
			g.drawString("Move/Attack", 271, 638);
			g.drawString("Reset Turn", 456, 638);
			g.setFont(new Font("Comic Sans MS", 0, 12));
			g.drawString("Q", 391, 590);
			g.drawString("W", 570, 590);
			g.drawString("E", 392, 650);
			g.drawString("R", 573, 650);
			//g.fillRect(Mouse.getX() - 32, Mouse.getY() -32, 64, 64); //draws the stuff on the mouse
			//if (Mouse.getButton() != -1) g.drawString("Mousebutton:" + Mouse.getButton(), 80, 80); //shows if the mouse is visible and which button is being pressed..
			if(selecting == false) { //not visible during placement phase to make sure that enemy team doesn't see what units you have
				g.setFont(new Font("Lucida Console", 0, 16));
				g.drawString("Next", 889, 661);
				g.setFont(new Font("Lucida Console", 0, 26));
				g.setColor(Color.RED);
				g.drawString("Red Team", 618, 560);
				g.setFont(new Font("Lucida Console", 0, 18));
				if(unitCountSequence == 0) {
					g.drawString("Marines: " + level.redMarine, 618, 587);
					g.drawString("Tanks: " + level.redTank, 618, 604);
					g.drawString("Grenadiers: " + level.redGrenadier, 618, 621);
					g.drawString("Snipers: " + level.redSniper, 618, 638);
				} else if(unitCountSequence == 1) {
					g.drawString("Assassins: " + level.redAssassin, 618, 587);
					g.drawString("Rocketeers: " + level.redRocketeer, 618, 604);
					//g.drawString("Scouts: " + level.redScout, 618, 621);
					//g.drawString("MGers: " + level.redMachineGunner, 618, 638);
				} else if(unitCountSequence == 2) {
					//g.drawString("Medics: " + level.redMedic, 618, 587);
				}
		
				if(mode == "pvp") {
					g.setFont(new Font("Lucida Console", 0, 26));
					g.setColor(darkGreen);
					g.drawString("Green Team", 790, 560);
					g.setFont(new Font("Lucida Console", 0, 18));
					if(unitCountSequence == 0) {
						g.drawString("Marines: " + level.greenMarine, 790, 587);
						g.drawString("Tanks: " + level.greenTank, 790, 604);
						g.drawString("Grenadiers: " + level.greenGrenadier, 790, 621);
						g.drawString("Snipers: " + level.greenSniper, 790, 638);				
					} else if(unitCountSequence == 1) {
						g.drawString("Assassins: " + level.greenAssassin, 790, 587);
						g.drawString("Rocketeers: " + level.greenRocketeer, 790, 604);
						//g.drawString("Scouts: " + level.greenScout, 790, 621);
						//g.drawString("MGers: " + level.greenMachineGunner, 790, 638);
					} else if(unitCountSequence == 2) {
						//g.drawString("Medics: " + level.greenMedic, 790, 587);
					}
				} else if(mode == "zombie") {
					g.setFont(new Font("Lucida Console", 0, 26));
					g.setColor(darkGrey);
					g.drawString("Zombies", 790, 560);
					g.setFont(new Font("Lucida Console", 0, 18));
					g.drawString("Zombies: " + level.greenZombie, 790, 587);
				}
			}
		
			if(helpbuttons.get(1).visible == true) { //if the help interface is open
				g.setFont(new Font("Lucida Console", 0, 32));
				g.setColor(darkGrey);
				g.drawString("Help Menu", 515, 130);
			
				g.setFont(new Font("Lucida Console", 0, 20));
				g.setColor(Color.WHITE);
				g.drawString("How to Play", 236, 198);
				g.drawString("Controls", 253, 240);
				g.drawString("Units", 272, 282);
			}
		
			if(settingsbuttons.get(1).visible == true) { //if the settings interface is open
				g.setFont(new Font("Lucida Console", 0, 28));
				g.setColor(Color.WHITE);
				g.drawString("Settings", 993, 262);
			
				g.setFont(new Font("Lucida Console", 0, 18));
				g.drawString("Neutral Fog", 999, 316);
			}
		}
		
		g.dispose();
		bs.show();
	
	}
	
	public void renderMenu() {
		for(Inter button: menubuttons) {
			button.render(screen);
		}
		/*menuinterface1.render(screen);
		menubutton1.render(screen);
		menubutton2.render(screen);
		menubutton3.render(screen);
		menutitlebutton.render(screen);
		menutopbutton.render(screen);
		menubottombutton.render(screen);*/
	}
	
	public void renderInterface() {

		inter.render(screen);
		for(Inter button: buttons) {
			button.render(screen);
		}
		for(Inter button: selectionbuttons) {
			button.render(screen);
		}
		for(Inter button: helpbuttons) {
			button.render(screen);
		}
		for(Inter button: settingsbuttons) {
			button.render(screen);
		}
		for(Inter button: nextbuttons) {
			button.render(screen);
		}
		for(Inter button: infobuttons) {
			button.render(screen);
		}
		/*button1.render(screen);
		button2.render(screen);
		button3.render(screen);
		button4.render(screen);
		button5.render(screen);
		nextbutton1.render(screen);
		selectioninterface1.render(screen);
		selectioninterface2.render(screen);
		selectionbutton3.render(screen);
		selectionbutton4.render(screen);
		selectionbutton5.render(screen);
		selectionbutton6.render(screen);
		selectionbutton7.render(screen);
		selectionbutton9.render(screen);
		selectionbutton8.render(screen);
		infobutton1.render(screen);
		infobutton2.render(screen);
		infobutton3.render(screen);
		infobutton4.render(screen);
		infobutton5.render(screen);
		infobutton6.render(screen);
		helpbutton1.render(screen);
		helpinterface1.render(screen);
		helpbutton2.render(screen);
		helpbutton3.render(screen);
		helpbutton4.render(screen);
		settingsbutton1.render(screen);
		settingsinterface1.render(screen);
		settingsbutton2.render(screen);
		nextbutton2.render(screen);*/
	}
	
	//currently not functional. Don't know where to put it.
	public void playMusic() {
		if (level.getTurn() == 1 && level.getLastTurn() == 0){
			//Sound.turn_theme.loop(); 
			//Sound.map_theme.loop(); 
		}
		if (level.getTurn() == 0 && level.getLastTurn() == 1){
			//Sound.map_theme.loop(); 
			//Sound.turn_theme.loop();
		}
	}
	
	public static void main (String[] args){
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle("Fog Of War");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //stops the game when the Jframe closes
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start(); //starts the game, lol
	}
	
	public Screen getScreen() {
		return screen;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getScale() {
		return scale;
	}

	public Level getLevel() {
		return level;
	}
	
	public Keyboard getKeyboard() {
		return key;
	}
	
}
