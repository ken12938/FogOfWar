package projectiles;

import units.Unit;
import units.VisionTower;
import folder.MathMachine;
import folder.Sprite;
import folder.Entity;

public abstract class Projectile extends Entity{ //this is a superclass for projectiles
	
	protected final double xOrigin, yOrigin; //final variable.
	protected double angle;
	protected double x, y; //overrides the int variables in Entity, because we need the precision of double variables to create accurate firing angles.
	protected Sprite sprite;
	protected double nx, ny; //stands for newx, newy. Changes after each update as projectile moves
	
	protected static double speed, range, damage;
	protected static String dmgType;
	protected int size; //SIZE is used for collision detection
	
	public final boolean PROJECTILE_B;
	public final boolean PROJECTILE_R;
	
	protected boolean flash = true; //responsible for muzzle flare
	
	public Projectile(double x, double y, double dir, boolean playerR, boolean playerB) {
		PROJECTILE_B = playerB;
		PROJECTILE_R = playerR;
		
		size = 0; //this must be changed each for each sprite, just
		xOrigin = x;
		yOrigin = y;
		angle = dir;
		this.x = x; //dont forget this. This makes sure the x and y from the entity superclass are actually correct.
		this.y = y;
	}
	
	
	//special move method. Similar to mob, but not exactly. Not gonna lie, its pretty dope.
	protected void move(double xa, double ya) { 
		//xa and ya is each "jump" for a mob.

		
		while (xa != 0){ 

			if (Math.abs(xa) > 1) { // if the xa is more than onetile... (in negative OR positive direction)
				if (!tileCollision(abs(xa), abs(ya), size)) { //"abs()" is different than "Math.abs()" !!
					this.x += abs(xa);
				}
				else remove();
				
				xa -= abs(xa);
			} else {
				if (!tileCollision(abs(xa), abs(ya), size))
					this.x += xa;	
				else remove();
				
				xa = 0;
			}
			
		}
			
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!tileCollision(abs(xa), abs(ya), size)){
					this.y += abs(ya);
				}
				else remove();
				
				ya -= abs(ya);
			} else {
				if (!tileCollision(abs(xa), abs(ya), size))
					this.y += ya;
				else remove();
				
				ya = 0;
			}
		}	
		
	}
	
	public boolean tileCollision (double xa, double ya, int size) { //this method is used by projectiles for tile collision
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = ((int)( x + xa) + c % 2 * size  + 13) / 16;
			int yt = ((int)( y + ya) + c / 2 * size + 13) / 16;
			
			if (level.getTile(xt, yt).solid()) solid = true;
		}
		
		return solid;
	}
		
	protected boolean unitCollision(){	//only detects for collision with units.
		boolean collision = false;
		for(int i = 0; i < level.getEntities().size(); i++){ // all entities in Level class
			if(level.getEntities().get(i) instanceof Unit || !(level.getEntities().get(i) instanceof VisionTower)) { //towers don't take fire
				Unit u = (Unit) level.getEntities().get(i);
				if (PROJECTILE_R == u.UNIT_G || PROJECTILE_B == u.UNIT_R){ //TURNS OFF FRIENDLY FIRE
					if (Math.abs(u.getX() - x ) <= size * 5 && Math.abs(u.getY() - y) <= size * 5){  //size refers to area of collision box.
						u.changeHealth(damage, dmgType);
						collision = true; //if the coordinates are equal, WOOHOO!
						System.out.println("HIT");
					}
				}
				//friendly fire only occurs at longer ranges.
				if ((PROJECTILE_R != u.UNIT_G || PROJECTILE_B != u.UNIT_R) && distance() >= 16* 3){ //prevents you from shooting through allies
					if (Math.abs(u.getX() - x ) <= size * 3 && Math.abs(u.getY() - y) <= size * 3){  //size refers to area of collision box.
						remove(); //no friendly fire dawg.
					}
				}
			}
		} 		
		
		return collision;
	}
	
	protected void move() {
	}
	
	protected double distance() {
		double dist = MathMachine.distance((xOrigin - x), (yOrigin -y)); //pythagorean theorem
		return dist;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	abstract double getDamage();
	

}
