package folder;

import projectiles.LaserProjectile;

public class Player extends Mob{

	private Keyboard input;
	
	private int fireRate = 0;
	private int scale;
			
	public Player(Keyboard input) { //constructor spawns anywhere
		this.input = input;
	}
	
	public Player (int x, int y) { //constructor that SPAWNS you in a specific location
		this.x = x; //x and y aren't initialized specifically in this subclass. But its instantiated in the superclass Entity!
		this.y = y; //x and y are keeping track of player position.
		fireRate = LaserProjectile.FIRE_RATE;
		sprite = Sprite.player;
	}
	
	public void update() { // green icon on the left indicates it is overriding an update method.
		this.x = Mouse.getX()/scale - 16; //centers the reticle of the 32x32 sprite onto the mouse. BUT NOTE: the x and y are still defined as top left corner!
		this.y = Mouse.getY()/scale - 16;
		
		//if (fireRate > 0) fireRate--; 
		/*
		double xa = 0, ya = 0; 
		if (input.up) ya -= 3; //asks keyboard if any up keys are being hit. so on for next rows. Using this method allows us to move in two directions at once. E.g. if up and left are pressed, screen will move diagonally to the left.
		if (input.down) ya += 3; //xa and ya can only equal -1, 0, or 1.
		if (input.left) xa -= 3;
		if (input.right) xa += 3;

		if (xa != 0 || ya!= 0) move(xa, ya);
		*/
		//clear();
		//updateShooting();
	}
	
	/*
	protected void clear() { //clears projectiles! Not used in player though. 

		for (int i= 0; i < level.getProjectiles().size(); i++) { //cycles through all "removed" projectiles and actually removes them.
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
		
	}

*/


	//runs during every update cycle. shoots if there is a mouse click.
	/*
	private void updateShooting() {
		if(Mouse.getButton() == 1 && fireRate <= 0) { // if you shoot, shoot in the direction of mouse. Also, firerate must be zero
			double dx = Mouse.getX() - Game.getWindowWidth() / 2; //dx and dy are two sides of a triangle that we use to caclulate the projection angle for a projectile.
			double dy = Mouse.getY() - Game.getWindowHeight() / 2; // we calculate dx and dy by finding the mob's position on the WINDOW not the Screen!
			double dir = Math.atan2(dy, dx); //atan2 is a method that returns the arctan. Don't worry about dividing by zero. it deals with it.
			
			shoot(x, y, dir);
			fireRate = LaserProjectile.FIRE_RATE;
		}
	}
	*/
	
	
	
	public void render(Screen screen) {
		//The "-16" helps center character by shifting it over to the left. 
		screen.renderPlayer((int) x, (int) y, sprite); //renders all 4 pieces of the 32x32 sprite. Modifying the x or y will change where the screen is centered.
	}
	
	protected boolean tileCollision(double xa, double ya) { //returns true if there is a collision
		boolean solid = false;
		//for (int c = 0; c < 4; c++) { //this checks all 4 corners for collision
			//int xt = ((x + xa) + c % 2 * 18 + 7) / 16; // i honestly don't know hoW this works. It just does. Episode 65.
			//int yt = ((y + ya) + c / 2  * 14 + 8) / 16;
			//if (level.getTile(xt, yt).solid()) solid = true; //this is the important statement for collision detection. It works by looking at the tile you want to go to and seeing if it is solid.
		//}
		
		
		return solid;
	}
	public void setScale(int scale){
		this.scale = scale;
	}

	public void switchSprite() { //switches the skin of sprite.
		if (sprite.equals(Sprite.player)) sprite = Sprite.playerSelecting;
		else if (sprite.equals(Sprite.playerSelecting)) sprite = Sprite.player;
	}
}
