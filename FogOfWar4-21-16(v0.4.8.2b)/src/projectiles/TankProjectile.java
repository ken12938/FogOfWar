package projectiles;

import units.Unit;
import folder.Sprite;
import folder.Entity;
import folder.Screen;

public class TankProjectile extends Projectile{

	public static final int FIRE_RATE = 100;
	private static final double RANGE = 240;
	private double DAMAGE;

	public TankProjectile(double x, double y, double dir, boolean playerR, boolean playerB) {
		super(x, y, dir, playerR, playerB);
		//arbitrary values
		range = RANGE; 
		DAMAGE = damage = 80;
		dmgType = "shell";
		sprite = Sprite.projectile_tank; //sprite.grass is arbitrary
		speed = 2; //4 seems optimal 
		size = 3;
		
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

	public void render(Screen screen) { //the 8 and 5 will eventually need to be changed.
		screen.renderProjectile((int)x + 8, (int) y + 5, this); //must cast to int from double. doesnt affect the angle precision :D
	}
	
	public double getDamage() {
		return DAMAGE;
	}
}
