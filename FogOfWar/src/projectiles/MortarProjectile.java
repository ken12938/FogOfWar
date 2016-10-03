package projectiles;

import units.Unit;
import folder.Sprite;
import folder.Entity;
import folder.Screen;
import folder.Level;

public class MortarProjectile extends Projectile{
public static final int FIRE_RATE = 90;
private static int time;
private static final int LIFETIME = 15; // time that explosion lasts
private static final int EXPLODETIME = 8; //time it takes to explode
private static final double RANGE = 175;

	public MortarProjectile(double x, double y, double xD, double yD, double dir, boolean playerR, boolean playerB) {
		super(x, y, dir, playerR, playerB);
		//arbitrary values
		
		this.x = 12 * random.nextGaussian()+ xD;
		this.y = 12 * random.nextGaussian()+ yD;
		
		range = RANGE; 
		damage = 2;
		dmgType = "explosive";
		sprite = Sprite.projectile_mortar_1; //mortars and tanks have same projectile
		size = 3;
		time = Level.getTime();
	}
	
	public void update () {
		if (tileCollision(0, 0, size)) remove();//zeros are input for x and y because it isnt moving.
		if (unitCollision() && (Level.getTime() - time) > LIFETIME) remove();
		if (distance() > range) remove();
		if(Level.getTime() - time > EXPLODETIME) sprite = Sprite.projectile_mortar_2; //makes it look like the mortar explodes
		if(Level.getTime() - time > LIFETIME) remove();
	}
	
	public static double getRange() {
		return RANGE;
	}
	
	public void render(Screen screen) { //the 8 and 5 will eventually need to be changed.
		screen.renderProjectile((int)x, (int) y, this); //must cast to int from double. doesnt affect the angle precision :D
	}
	
	public double getDamage() {
		return damage;
	}
}
