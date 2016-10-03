package projectiles;

import units.Unit;
import folder.Sprite;
import folder.Entity;
import folder.Screen;

public class LaserProjectile extends Projectile{

	public static final int FIRE_RATE = 100; //5 seems optimal. adjust for burst fireee
	private static final double RANGE = 200;
	
	public LaserProjectile (double x, double y, double dir, boolean playerR,boolean playerB) {
		super(x, y, dir, playerR, playerB);
		//arbitrary values
		range = RANGE; 
		damage = 5;
		dmgType = "bullet";
		sprite = Sprite.projectile_laser; //sprite.grass is arbitrary
		speed = 4; //4 seems optimal 
		size = 2;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update () {
		if (tileCollision(nx, ny, size)) remove();//2 is an arbitrary number that refers to the size of the pixel and affects its collision detection
		if (unitCollision()) remove();
		if (distance() > range) remove();
			
		move(nx, ny);
	}
	
	public static double getRange() {
		return RANGE;
	}
	
	
	public void render(Screen screen) { //the 8 and 5 will eventually need to be changed. They are responsible for making the thing shoot from the center.
		screen.renderProjectile((int)x + 8, (int) y + 5, this); //must cast to int from double. doesnt affect the angle precision :D
	}
	
	public double getDamage() {
		return damage;
	}


}
