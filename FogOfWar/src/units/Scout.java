package units;

import projectiles.LaserProjectile;
import projectiles.ScoutProjectile;
import folder.Level;
import projectiles.Projectile;
import folder.Sprite;

public class Scout extends Unit {

	protected final int RELOAD; // time it takes between firing.
	protected final int BURSTFIRE; // # of bullets in each burst

	public Scout(int x, int y, boolean playerR, boolean playerB) {
		super(x, y, playerR, playerB);
		move_range = 250;
		projectile_range = ScoutProjectile.getRange();
		vision_range = 200;
		vision_width = 0.8;
		
		health = 100;
		maxHealth = health;
		speed = 1;
		reload = 0;
		RELOAD = ScoutProjectile.FIRE_RATE;
		burstFire = BURSTFIRE = 1;
		time = level.getTime();
	}

	protected void assignTeam(boolean playerR, boolean playerB) { // add more
																	// team
																	// specific
																	// properties
		if (playerR == true)
			sprite = Sprite.marine_r_front_left;
		else if (playerB == true)
			sprite = Sprite.marine_g_front_left;
	}

	protected void updateShooting() {

		if (Level.getTime() - time > 2 && reload <= 0) {
			shoot(x, y, dir);
			time = level.getTime();
			burstFire--;
			if (burstFire <= 0) {
				reload = RELOAD;
				burstFire = BURSTFIRE;
			}
		} else
			reload--;
	}

	protected void shoot(double x, double y, double dir) {
		Projectile p = new ScoutProjectile(x - 8, y - 5, dir, UNIT_R, UNIT_G); 
		level.addProjectile(p); // note: when this method is called, it adds
								// projectile p BUT IT ALSO INITIALIZES LEVEL
								// for the projectile class! "p.init(this)"
	}
	
	public void changeHealth(double dHealth, String damageType) {
		super.changeHealth(dHealth, damageType);
	}

	public double getProjectileRange() {
		return projectile_range;
	}

	@Override
	public double getMoveRange() {
		return move_range;
	}

	@Override
	public double getVisionRange() {
		return vision_range;
	}

	public double getDamage() {
		return burstFire * 15;
	}

	@Override
	// responsible for changing the sprite for the appropriate direction and
	// animation. Every Unit must implement this method!
	protected void animate(int anim) {

		if (UNIT_R) {
			//if unit is idle..
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24)
						sprite = Sprite.marine_r_back_right_idle_1;
					else
						sprite = Sprite.marine_r_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24)
						sprite = Sprite.marine_r_front_right_idle_1;
					else
						sprite = Sprite.marine_r_front_right;
				}

				else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 48 > 24)
						sprite = Sprite.marine_r_front_left_idle_1;
					else
						sprite = Sprite.marine_r_front_left;
				}

				else {
					if (anim % 48 > 24)
						sprite = Sprite.marine_r_back_left_idle_1;
					else
						sprite = Sprite.marine_r_back_left;
				}
			}
			//if unit is either moving or shooting
			else if (action == 1 || action == 3) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8)
						sprite = Sprite.marine_r_back_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_r_back_right_frame_2;
					else
						sprite = Sprite.marine_r_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8)
						sprite = Sprite.marine_r_front_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_r_front_right_frame_2;
					else
						sprite = Sprite.marine_r_front_right;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 12 > 8)
						sprite = Sprite.marine_r_front_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_r_front_left_frame_2;
					else
						sprite = Sprite.marine_r_front_left;
				} else {
					if (anim % 12 > 8)
						sprite = Sprite.marine_r_back_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_r_back_left_frame_2;
					else
						sprite = Sprite.marine_r_back_left;
				}
			}

		} else if (UNIT_G) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24)
						sprite = Sprite.marine_g_back_right_idle_1;

					else
						sprite = Sprite.marine_g_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24)
						sprite = Sprite.marine_g_front_right_idle_1;
					else
						sprite = Sprite.marine_g_front_right;
				}

				else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 48 > 24)
						sprite = Sprite.marine_g_front_left_idle_1;
					else
						sprite = Sprite.marine_g_front_left;
				}

				else {
					if (anim % 48 > 24)
						sprite = Sprite.marine_g_back_left_idle_1;
					else
						sprite = Sprite.marine_g_back_left;
				}
			} else if (action == 1 || action == 3) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8)
						sprite = Sprite.marine_g_back_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_g_back_right_frame_2;
					else
						sprite = Sprite.marine_g_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8)
						sprite = Sprite.marine_g_front_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_g_front_right_frame_2;
					else
						sprite = Sprite.marine_g_front_right;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 12 > 8)
						sprite = Sprite.marine_g_front_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_g_front_left_frame_2;
					else
						sprite = Sprite.marine_g_front_left;
				} else {
					if (anim % 12 > 8)
						sprite = Sprite.marine_g_back_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.marine_g_back_left_frame_2;
					else
						sprite = Sprite.marine_g_back_left;
				}
			}
		}

		/*
		 * if (UNIT_G) { if (anim % 12 > 8) sprite =
		 * Sprite.marine_g_front_left_frame_1; else if (anim % 12 > 4) sprite =
		 * Sprite.marine_g_front_left_frame_2; else sprite =
		 * Sprite.marine_g_front_left_frame_3; }
		 */

	}

}
