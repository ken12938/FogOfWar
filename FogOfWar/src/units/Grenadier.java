package units;

import projectiles.LaserProjectile;
import projectiles.MortarProjectile;
import projectiles.Projectile;
import folder.Sprite;
import projectiles.TankProjectile;

public class Grenadier extends Unit{
	
	protected final int RELOAD; //time it takes between firing.

	public Grenadier(int x, int y, boolean playerR, boolean playerB) {
		super(x, y, playerR, playerB);
		move_range = 150;
		projectile_range = MortarProjectile.getRange();
		vision_range = 80;
		vision_width = 0.8;
		
		speed = .5;
		health = 120;
		maxHealth = health;
		reload = 0;
		RELOAD = MortarProjectile.FIRE_RATE;
	}
	
	protected void assignTeam(boolean playerR, boolean playerB) {
		if (playerR == true)
			sprite = Sprite.grenadier_r_front_left;
		else if (playerB == true)
			sprite = Sprite.grenadier_g_front_left;
	}
	
	protected void shoot (double x, double y, double dir) {
		shotFired = true;
		Projectile p = new MortarProjectile(x, y, xD, yD, dir, UNIT_R, UNIT_G); //LaserProjectile is generic btw! Eventually we are gonna override this for different units
		level.addProjectile(p); //note: when this method is called, it adds projectile p BUT IT ALSO INITIALIZES LEVEL for the projectile class! "p.init(this)"
	}
	
	protected void updateShooting() {
		if(reload <= 0) { // if you shoot, shoot in the direction of mouse. Also, firerate must be zero
			shoot(x, y, dir); //this is different than all other units cuz it is a mortar
			reload = RELOAD;
		}
		else reload--;
	}

	@Override
	protected void animate(int anim) {

		if (UNIT_R) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_r_back_right_idle_1;

					else
						sprite = Sprite.grenadier_r_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_r_front_right_idle_1;
					else
						sprite = Sprite.grenadier_r_front_right;
				}

				else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_r_front_left_idle_1;
					else
						sprite = Sprite.grenadier_r_front_left;
				}

				else {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_r_back_left_idle_1;
					else
						sprite = Sprite.grenadier_r_back_left;
				}
			}

			else if (action == 1 || action == 3) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_r_back_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_r_back_right_frame_2;
					else
						sprite = Sprite.grenadier_r_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_r_front_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_r_front_right_frame_2;
					else
						sprite = Sprite.grenadier_r_front_right;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_r_front_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_r_front_left_frame_2;
					else
						sprite = Sprite.grenadier_r_front_left;
				} else {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_r_back_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_r_back_left_frame_2;
					else
						sprite = Sprite.grenadier_r_back_left;
				}
			}
			else if (action == 2) {
				if (dir >= -3.14 / 2 && dir < 0) {
					sprite = Sprite.grenadier_r_back_right_firing;
				} else if (dir >= 0 && dir < 3.14 / 2) {
					sprite = Sprite.grenadier_r_front_right_firing;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					sprite = Sprite.grenadier_r_front_left_firing;
				} else {
					sprite = Sprite.grenadier_r_back_left_firing;
				}
			}

		} else if (UNIT_G) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_g_back_right_idle_1;

					else
						sprite = Sprite.grenadier_g_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_g_front_right_idle_1;
					else
						sprite = Sprite.grenadier_g_front_right;
				}

				else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_g_front_left_idle_1;
					else
						sprite = Sprite.grenadier_g_front_left;
				}

				else {
					if (anim % 48 > 24)
						sprite = Sprite.grenadier_g_back_left_idle_1;
					else
						sprite = Sprite.grenadier_g_back_left;
				}
			} else if (action == 1 || action == 3) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_g_back_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_g_back_right_frame_2;
					else
						sprite = Sprite.grenadier_g_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_g_front_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_g_front_right_frame_2;
					else
						sprite = Sprite.grenadier_g_front_right;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_g_front_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_g_front_left_frame_2;
					else
						sprite = Sprite.grenadier_g_front_left;
				} else {
					if (anim % 12 > 8)
						sprite = Sprite.grenadier_g_back_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.grenadier_g_back_left_frame_2;
					else
						sprite = Sprite.grenadier_g_back_left;
				}
			}
			else if (action == 2) {
				if (dir >= -3.14 / 2 && dir < 0) {
					sprite = Sprite.grenadier_g_back_right_firing;
				} else if (dir >= 0 && dir < 3.14 / 2) {
					sprite = Sprite.grenadier_g_front_right_firing;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					sprite = Sprite.grenadier_g_front_left_firing;
				} else {
					sprite = Sprite.grenadier_g_back_left_firing;
				}
			}
		}

		/*
		 * if (UNIT_G) { if (anim % 12 > 8) sprite =
		 * Sprite.grenadier_g_front_left_frame_1; else if (anim % 12 > 4) sprite =
		 * Sprite.grenadier_g_front_left_frame_2; else sprite =
		 * Sprite.grenadier_g_front_left_frame_3; }
		 */

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
	
	public double getVisionRange() {
		return vision_range;
	}
	
	public double getDamage() {
		return 0;
	}


}
