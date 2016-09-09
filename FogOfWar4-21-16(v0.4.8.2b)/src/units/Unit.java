package units;

import java.util.List;

import folder.Entity;
import folder.Level;
import folder.MathMachine;
import folder.Mob;
import projectiles.Projectile;
import folder.Screen;
import folder.Sprite;

//KEY:
//1 = move
//2 = shoot
//3 = shoot & move

public abstract class Unit extends Mob {

	protected double xa = 0; // xa and ya are the increments that the unit uses to move each update.
	protected double ya = 0;
	protected double xD, yD; // this is where the unit wants to go.
	protected double xs;
	protected double ys;
	protected double xOrigin; // where the units starts from at each turn
	protected double yOrigin;
	private int dist; // distance traveled
	protected int time; // not consistently used. Its only updated when unit is shooting

	protected double speed;
	protected double health;
	protected double maxHealth;
	protected int reload; // keeps track of reloading NOTE: burstfire and reload are different than BURSTFIRE and RELOAD in the subclasses
	protected int burstFire; // keeps track of # of bullets in each burst.
	protected double move_range;
	protected double projectile_range;
	protected double vision_range;
	protected double vision_width;

	public boolean UNIT_R; // team type. public currently...
	public boolean UNIT_G; // not a final variable because of VisionTower
	public String unitType;
	public String team;

	public int action = 0; // stores the type of action it will execute into an array.
	public boolean selected;
	public boolean visibleToEnemy; // is this unit visible to the other team?
	public boolean visibleInNeutral;

	protected boolean shotFired; //variable that keeps track if a shot is fired
	
	public double dir2; // temporary storage of dir for 1 turn.
	protected int anim;// affects which animation position is currently shown.

	public Unit(int x, int y, boolean playerR, boolean playerB) {
		this.x = x << 4; // converts to pixel coordinates by multiplying by 16
		this.y = y << 4;

		this.UNIT_R = playerR;
		this.UNIT_G = playerB;
		assignTeam(this.UNIT_R, this.UNIT_G); // put team-specific properties in this method!
		visibleToEnemy = false;

		selected = false;
		action = 0;
		shotFired = false;
		
		if (UNIT_R == true) {
			dir = 0;
		} else if (UNIT_G == true) {
			dir = 3.14;
		}
		dir2 = dir;
	}

	// add more team specific properties. (i.e. sprite)
	protected abstract void assignTeam(boolean playerR, boolean playerB);

	public void act(double x, double y, int action) {
		this.action = action;

		xOrigin = this.x; // current coordinates of unit
		yOrigin = this.y;
		// 8 offsets the position which accounts for the fact that this.x is defined as the top left corner of a tile.
		xD = x - 8;
		yD = y - 8;

		xs = xD - this.x; // xs and ys are two sides of a triangle that we use to calculate the projection angle for a unit.
		ys = yD - this.y; // we calculate xs and ys by finding the units desired location minus initial location
		dir = Math.atan2(ys, xs); // atan2 is a method that returns the arctan. Don't worry about dividing by zero. it deals with it.
		//System.out.println("unit direction: " + dir);
		xa = Math.round(speed * Math.cos(dir) * 100.0) / 100.0; // rounds speed * direction to two decimal places.
		ya = Math.round(speed * Math.sin(dir) * 100.0) / 100.0;
	}

	protected abstract void shoot(double x, double y, double dir);
	
	// FOLLOWING CODE HAS 4 major if-statements. They are for moving, shooting, moving-shooting, passive shooting!
	public void update() {
		// shooting and moving! This results in a slower movement, and unit self-targeting.
		if (action == 3 && !(this instanceof Grenadier) && !(this instanceof Sniper)) { // can't be a tank
			if (stop()) { // the method stop detects if we have arrived at correct position or reached max distance
				xa = 0;
				ya = 0;
			} else move(xa - xa / 8 * 5, ya - ya / 8 * 5); // SLOWS DOWN MOVEMENT

			Unit u = target(); // this is the enemy unit it is targeting.
			if (u != null) {
				dir = Math.atan2(u.getY() - y, u.getX() - x);
				updateShooting();
			}
		}

		// shooting only
		else if (action == 2) {
			updateShooting();
		}
		
		// moving only
		else if (action == 1) {
			if (unitType.equals("Assassin") || unitType.equals("Zombie")) { // these are units that deal with collisions!
				unitCollision(); // these three units have  methods that specialize in collision
			}

			if (stop()) { // the method stop detects if we have arrived at correct position!
				xa = 0;
				ya = 0;
				action = 0;
			} else move(xa, ya); // YOU MUST ROUND TO A CERTAIN NUMBER OF DECIMAL PLACES IN ORDER TO REDUCE CHOPPINESS OF MOVEMENT
		}
		updateAnimation();

	}

	public void reset() { // resets units at the end of turn
		xD = x;
		yD = y;
		action = 0;
		dir2 = dir;
		visibleToEnemy = false;
		selected = false;
		
		if (UNIT_R == true) {
			// dir = 0;
		} else if (UNIT_G == true) {
			// dir = 180 / 2 / 3.14;
		}
	}

	// some units such as tower, assassin and zombie need this collision method
	public void unitCollision() {
	}

	// returns closest enemy unit to shoot at.
	protected Unit target() {
		Unit closest = null; // this variable will contain the closest Unit
		Double min = (double) projectile_range; // this will contain the lowest distance
		List<Entity> entities = level.getEntities();

		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			Unit u = (Unit) e; // casts e to Unit
			if (u.equals(this)) continue; // discounts self
			if (u.UNIT_R != UNIT_G && u.UNIT_G != UNIT_R) continue; // discounts Units from own team
			if (!(e instanceof Unit)) continue; // discounts non-Unit entities

			double distance = MathMachine.distance((u.getX() - x), (u.getY() - y));
			/*
			 * int r = 0; int g = 0; for (int y = 0; y < level.getHeight() * 16; y++) { for (int x = 0; x < level.getWidth() * 16; x++) { if (level.redPixels[x + y * level.getWidth() * 16] == true) r++; if (level.greenPixels[x + y * level.getWidth() * 16] == true )g++; System.out.println("r: " + r + ", " + g); } }
			 */
			if (UNIT_R) { // if it is a red unit
				if (distance < min) {
					int xt = (int) Math.round(u.getX());
					int yt = (int) Math.round(u.getY());
					if (level.redPixels[(int) Math.round(xt + level.getWidth() * 16.0 * yt)]) {
						closest = u;
						min = distance;
					}
				}
			}
			if (UNIT_G) { // if it is a green unit
				if (distance < min) {
					System.out.println(level.greenPixels[(int) Math.round(u.getX() + level.getWidth() * 16.0 * u.getY())]);

					if (level.greenPixels[(int) Math.round(u.getX() + level.getWidth() * 16.0 * u.getY())]) {
						closest = u;
						min = distance;
					}
				}
			}

		}

		return closest; // the program is fucked if null happens. just a headsup. hopefully you will remember if you get a null pointer exception.
	}

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite);
		animate(anim);
		
		muzzleFlash(screen); //renders the muzzle flash if the time is right
		
		if (!visibleToEnemy) { // this single if statement makes sure that even if the unit is spotted, its commands will not be visible to the opposing player
			// screen.renderVision((int) x + 8,(int) y + 8, getVisionRange(), dir); //adding 8 to the x and y coordinates centers the vision on the unit.

			// highlights selected units
			if (action == 1 || action == 2 || action == 3) {
				screen.renderMob((int) x, (int) y, Sprite.commandCursor);
				screen.renderMob((int) xD, (int) yD, Sprite.wayPoint);
			}
			if (action == 1 && selected) {
				screen.renderRange((int) x, (int) y, getMoveRange(), 0xff00FF3F); // renders the MOVEMENT range in light green
			}
			if (action == 2 && selected) {
				screen.renderRange((int) x, (int) y, getProjectileRange(), 0xff00FF3F);// renders the SHOOTING range in light green
			}
			if (action == 3 && selected) {
				screen.renderRange((int) x, (int) y, getMoveRange(), 0xff00FF3F);// renders the MOVEMENT range in light green
			}
			// highlights commanded units
			if (selected) {
				screen.renderMob((int) x, (int) y, Sprite.selectCursor);
			}

			renderHealth(screen); // renders all the health bars based on amount of unit health

		}
	}

	//muzzleFlash
	public void muzzleFlash(Screen screen){
		if (shotFired && (reload <= 0)) screen.renderFlash((int) x, (int) y, sprite.getWidth());
	}
	
	//updates anim which method animate uses to animate the units
	public void updateAnimation() {
		if (anim < 1000) {
			anim++;
		} else anim = 0;
	}

	// responsible for changing the sprite for the appropriate direction and animation. Every Unit must implement this method!
	protected abstract void animate(int anim);

	// renders the appropiate health bar. Talk to Callan for how he organized this.
	protected void renderHealth(Screen screen) {
		if (health >= 250) screen.renderMob((int) x, (int) y - 1, Sprite.health_10);
		else if (health >= 225) screen.renderMob((int) x, (int) y - 1, Sprite.health_9);
		else if (health >= 200) screen.renderMob((int) x, (int) y - 1, Sprite.health_8);
		else if (health >= 175) screen.renderMob((int) x, (int) y - 1, Sprite.health_7);
		else if (health >= 150) screen.renderMob((int) x, (int) y - 1, Sprite.health_6);
		else if (health >= 125) screen.renderMob((int) x, (int) y - 1, Sprite.health_5);
		else if (health >= 100) screen.renderMob((int) x, (int) y - 1, Sprite.health_4);
		else if (health >= 75) screen.renderMob((int) x, (int) y - 1, Sprite.health_3);
		else if (health >= 50) screen.renderMob((int) x, (int) y - 1, Sprite.health_2);
		else if (health >= 25) screen.renderMob((int) x, (int) y - 1, Sprite.health_1);
	}

	protected abstract void updateShooting();

	// stops the unit once it reaches its location or reaches its max distance
	private boolean stop() {
		boolean collide = false;

		dist = (int) MathMachine.distance((xOrigin - this.x), (yOrigin - this.y)); // distance traveled
		if (dist >= move_range) collide = true;

		if (Math.abs(x - xD) <= 2 && Math.abs(y - yD) <= 2) { // adding 8 shifts the collision box from the top left corner to the bottom right.
			collide = true; // this is the important statement for collision detection. It works by looking at the tile you want to go to and seeing if it is solid.
		}

		return collide;
	}

	// collision for the unit sprites andrew ton made
	protected boolean tileCollision(double xa, double ya) { // returns true if there is a collision
		boolean solid = false;

		for (int c = 0; c < 4; c++) { // this checks all 4 corners for collision
			double xt = ((x - 6 + xa) - c % 2 * 4) / 16; // i honestly don't know hoW this works. It just does. Episode 65.
			double yt = ((y - 4 + ya) - c / 2 * 7) / 16;

			int ix = (int) Math.ceil(xt); // returns the smallest integer greater than or equal to a number
			int iy = (int) Math.ceil(yt);

			if (level.getTile(ix, iy).solid()) solid = true; // this is the important statement for collision detection. It works by looking at the tile you want to go to and seeing if it is solid.
		}

		return solid;
	}

	public void changeHealth(double dHealth, String damageType) { // changes health
		health -= dHealth;
	}

	public void select(int action) { // deselects or selects a unit. After each turn it must be deselected.
		selected = true;
		this.action = action;
	}

	public void deselect() {
		selected = false;
	}

	public boolean getSelected() {
		return selected;
	}

	public void setMoving() {
		moving = true;
	}

	public void setShooting() {
		moving = true;
	}

	public double getHealth() {
		return health;
	}

	public double getMaxHealth() {
		return maxHealth;
	}

	public double getSpeed() {
		return speed;
	}

	public int getReload() {
		return reload;
	}

	public double getVisionWidth() {
		return vision_width;
	}
	
	public double getHealRange() {
		return 0; //doesn't do anything for units besides the medic
	}
	
	public double getXD(){
		return xD;
	}
	
	public double getYD(){
		return yD;
	}

	abstract public double getMoveRange();

	abstract public double getProjectileRange();

	abstract public double getVisionRange();

	abstract public double getDamage();

}
