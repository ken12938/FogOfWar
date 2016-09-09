package units;

import projectiles.LaserProjectile;
import projectiles.Projectile;
import folder.Sprite;
import projectiles.TankProjectile;

public class Tank extends Unit {

	protected final int RELOAD; // time it takes between firing.

	public Tank(int x, int y, boolean playerR, boolean playerB) {
		super(x, y, playerR, playerB);
		move_range = 100;
		projectile_range = TankProjectile.getRange();
		vision_range = 60;
		vision_width = 0.8;

		speed = .2;
		health = 300;
		maxHealth = health;
		burstFire = 1;
		reload = 0;
		RELOAD = TankProjectile.FIRE_RATE;
	}

	protected void assignTeam(boolean playerR, boolean playerB) { // add more team specific properties
		if (playerR == true) sprite = Sprite.tank_r_right_frame_1;
		else if (playerB == true) sprite = Sprite.tank_g_right_frame_1;
	}

	protected void updateShooting() {
		if (reload <= 0) { // if you shoot, shoot in the direction of mouse. Also, firerate must be zero
			shoot(x, y, dir);
			reload = RELOAD;
		} else reload--;
	}

	protected void shoot(double x, double y, double dir) {
		shotFired = true;
		Projectile p = new TankProjectile(x - 8, y - 5, dir, UNIT_R, UNIT_G); // LaserProjectile is generic btw! Eventually we are gonna override this for different units
		level.addProjectile(p); // note: when this method is called, it adds projectile p BUT IT ALSO INITIALIZES LEVEL for the projectile class! "p.init(this)"
	}

	protected void animate(int anim) {
		// TODO Auto-generated method stub
		/*
		 * if (UNIT_R) { if (anim % 12 > 8) sprite = Sprite.drone_frame_1; else if (anim % 12 > 4) sprite = Sprite.drone_frame_2; else sprite = Sprite.drone_frame_3; }
		 */
		if (UNIT_R) {
			if (action == 0) {
				if (dir >= -3.14 * 3 / 4 && dir < -3.14 / 4) 
					sprite = Sprite.tank_r_up_frame_1;
				else if (dir >= -3.14 / 4 && dir < 3.14 / 4) 
					sprite = Sprite.tank_r_right_frame_1;
				else if (dir >= 3.14 / 4 && dir < 3.14 * 3 / 4) 
					sprite = Sprite.tank_r_down_frame_1;
				else
					sprite = Sprite.tank_r_left_frame_1;
				}
			
			else if (action == 1) {
				if (dir >= -3.14 * 3 / 4 && dir < -3.14 / 4) {
					if (anim % 12 > 8) sprite = Sprite.tank_r_up_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.tank_r_up_frame_2;
					else sprite = Sprite.tank_r_up_frame_1;
				} else if (dir >= -3.14 / 4 && dir < 3.14 / 4) {
					if (anim % 12 > 8) sprite = Sprite.tank_r_right_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.tank_r_right_frame_2;
					else sprite = Sprite.tank_r_right_frame_1;
				} else if (dir >= 3.14 / 4 && dir < 3.14 * 3 / 4) {
					if (anim % 12 > 8) sprite = Sprite.tank_r_down_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.tank_r_down_frame_2;
					else sprite = Sprite.tank_r_down_frame_1;
				} else {
					if (anim % 12 > 8) sprite = Sprite.tank_r_left_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.tank_r_left_frame_2;
					else sprite = Sprite.tank_r_left_frame_1;
				}
			}
		}

		/*
		 * if (UNIT_G) { if (anim % 12 > 8) sprite = Sprite.drone_frame_1; else if (anim % 12 > 4) sprite = Sprite.drone_frame_2; else sprite = Sprite.drone_frame_3; }
		 */
		else if (UNIT_G) {
			if (dir >= -3.14 * 3 / 4 && dir < -3.14 / 4) {
				if (anim % 12 > 8) sprite = Sprite.tank_g_up_frame_3;
				else if (anim % 12 > 4) sprite = Sprite.tank_g_up_frame_2;
				else sprite = Sprite.tank_g_up_frame_1;
			} else if (dir >= -3.14 / 4 && dir < 3.14 / 4) {
				if (anim % 12 > 8) sprite = Sprite.tank_g_right_frame_3;
				else if (anim % 12 > 4) sprite = Sprite.tank_g_right_frame_2;
				else sprite = Sprite.tank_g_right_frame_1;
			} else if (dir >= 3.14 / 4 && dir < 3.14 * 3 / 4) {
				if (anim % 12 > 8) sprite = Sprite.tank_g_down_frame_3;
				else if (anim % 12 > 4) sprite = Sprite.tank_g_down_frame_2;
				else sprite = Sprite.tank_g_down_frame_1;
			} else {
				if (anim % 12 > 8) sprite = Sprite.tank_g_left_frame_3;
				else if (anim % 12 > 4) sprite = Sprite.tank_g_left_frame_2;
				else sprite = Sprite.tank_g_left_frame_1;
			}
		}

	}

	public void changeHealth(double dHealth, String damageType) {
		if (damageType.equals("bullet")) {
			super.changeHealth(0.8 * dHealth, damageType);
		} else if (damageType.equals("shell")) {
			super.changeHealth(1.2 * dHealth, damageType);
		} else if (damageType.equals("explosive")) {
			super.changeHealth(1.5 * dHealth, damageType);
		}

		// assassin does no damage
	}

	public double getProjectileRange() {
		return projectile_range;
	}

	public double getMoveRange() {
		return move_range;
	}

	public double getVisionRange() {
		return vision_range;
	}

	public double getDamage() {
		return burstFire * 80;
	}

}
