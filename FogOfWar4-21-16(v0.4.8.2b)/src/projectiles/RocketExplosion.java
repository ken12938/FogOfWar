package projectiles;

import units.Unit;
import folder.Sprite;
import folder.Entity;
import folder.Screen;
import folder.Level;

public class RocketExplosion extends Projectile{
public static final int FIRE_RATE = 70;
private static int time;
private static final int LIFETIME = 12; // time that explosion lasts
private static final int EXPLODETIME = 0; //time it takes to explode
private static final double RANGE = 200;

	public RocketExplosion(double x, double y, double xD, double yD, double dir, boolean playerR, boolean playerB) {
		super(x, y, dir, playerR, playerB);
		//arbitrary values
		System.out.println("We made it!");
		System.out.println(x + ", " + y);
		
		range = RANGE; 
		damage = 3;
		dmgType = "explosive";
		sprite = Sprite.projectile_mortar_1; //mortars and tanks have same projectile
		size = 5;
		time = Level.getTime();
	}
	
	public void update () {
		if (tileCollision(0, 0, size)) remove();//zeros are input for x and y because it isnt moving.
		if (unitCollision() && (Level.getTime() - time) > LIFETIME) remove();
		if (distance() > range) remove();
		if(Level.getTime() - time > EXPLODETIME) sprite = Sprite.projectile_mortar_2; //makes it look like the mortar explodes
		if(Level.getTime() - time > LIFETIME) remove();
	}
	
	public boolean unitCollision(){	//only detects for collision with units.
		boolean collision = false;
		for(int i = 0; i < level.getEntities().size(); i++){ // all entities in Level class
			if(level.getEntities().get(i) instanceof Unit) {
				Unit u = (Unit) level.getEntities().get(i);
				if (PROJECTILE_R == u.UNIT_G || PROJECTILE_B == u.UNIT_R){ //TURNS OFF FRIENDLY FIRE
					if (Math.abs(u.getX() - x ) <= size * 5 && Math.abs(u.getY() - y) <= size * 5){  //size refers to area of collision box.
						u.changeHealth(damage, dmgType);
						collision = true; //if the coordinates are equal, WOOHOO!
						System.out.println("HIT");
					} else if(Math.abs(u.getX() - x ) <= size * 10 && Math.abs(u.getY() - y) <= size * 10) { //if a unit is on the outer radius, take reduced damage
						u.changeHealth(damage/4, dmgType);
						collision = true;
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
	
	public static double getRange() {
		return RANGE;
	}
	
	public void render(Screen screen) { //the 8 and 5 will eventually need to be changed.
		screen.renderProjectile((int)x, (int) y, this); //must cast to int from double. doesnt affect the angle precision :D
		System.out.println("Rendering...");
	}
	
	public double getDamage() {
		return damage;
	}
}
