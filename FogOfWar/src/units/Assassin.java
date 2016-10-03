package units;

import folder.Level;
import projectiles.Projectile;
import folder.Sprite;

public class Assassin extends Unit{

	protected final int RELOAD; //time it takes between attack.
	protected final int BURSTFIRE; //# of bullets in each burst
	
	int size = 3;

	public Assassin(int x, int y, boolean playerR, boolean playerB) {
		super(x, y, playerR, playerB);
		move_range = 250;
		vision_range = 100;
		vision_width = 1.3;
		
		health = 1;
		maxHealth = health;
		speed = 2;
		reload = RELOAD = 0;
		burstFire = BURSTFIRE = 0;
		time = level.getTime();
	}
	
	protected void assignTeam(boolean playerR, boolean playerB){ //add more team specific properties
		if (playerR == true) sprite = Sprite.assassin_r_front_left;
		else if (playerB == true) sprite = Sprite.assassin_g_front_left;
	}
	
	protected void updateShooting() { //updates if the assassin has killed anyone, doesn't shoot despite the name of the method
	}

	//does nothing
	protected void shoot (double x, double y, double dir) {
	}
	
	@Override
	protected void animate(int anim) {

		if (UNIT_R) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_r_back_right_idle_1;

					else
						sprite = Sprite.assassin_r_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_r_front_right_idle_1;
					else
						sprite = Sprite.assassin_r_front_right;
				}

				else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_r_front_left_idle_1;
					else
						sprite = Sprite.assassin_r_front_left;
				}

				else {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_r_back_left_idle_1;
					else
						sprite = Sprite.assassin_r_back_left;
				}
			}

			else if (action == 1) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_r_back_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_r_back_right_frame_2;
					else
						sprite = Sprite.assassin_r_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_r_front_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_r_front_right_frame_2;
					else
						sprite = Sprite.assassin_r_front_right;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_r_front_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_r_front_left_frame_2;
					else
						sprite = Sprite.assassin_r_front_left;
				} else {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_r_back_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_r_back_left_frame_2;
					else
						sprite = Sprite.assassin_r_back_left;
				}
			}
			else if (action == 2) {
				if (dir >= -3.14 / 2 && dir < 0) {
					sprite = Sprite.assassin_r_back_right_attack;
				} else if (dir >= 0 && dir < 3.14 / 2) {
					sprite = Sprite.assassin_r_front_right_attack;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					sprite = Sprite.assassin_r_front_left_attack;
				} else {
					sprite = Sprite.assassin_r_back_left_attack;
				}
			}

		} else if (UNIT_G) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_g_back_right_idle_1;

					else
						sprite = Sprite.assassin_g_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_g_front_right_idle_1;
					else
						sprite = Sprite.assassin_g_front_right;
				}

				else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_g_front_left_idle_1;
					else
						sprite = Sprite.assassin_g_front_left;
				}

				else {
					if (anim % 48 > 24)
						sprite = Sprite.assassin_g_back_left_idle_1;
					else
						sprite = Sprite.assassin_g_back_left;
				}
			} else if (action == 1) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_g_back_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_g_back_right_frame_2;
					else
						sprite = Sprite.assassin_g_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_g_front_right_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_g_front_right_frame_2;
					else
						sprite = Sprite.assassin_g_front_right;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_g_front_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_g_front_left_frame_2;
					else
						sprite = Sprite.assassin_g_front_left;
				} else {
					if (anim % 12 > 8)
						sprite = Sprite.assassin_g_back_left_frame_3;
					else if (anim % 12 > 4)
						sprite = Sprite.assassin_g_back_left_frame_2;
					else
						sprite = Sprite.assassin_g_back_left;
				}
			}
			else if (action == 2) {
				if (dir >= -3.14 / 2 && dir < 0) {
					sprite = Sprite.assassin_g_back_right_attack;
				} else if (dir >= 0 && dir < 3.14 / 2) {
					sprite = Sprite.assassin_g_front_right_attack;
				} else if (dir >= 3.14 / 2 && dir <= 3.14) {
					sprite = Sprite.assassin_g_front_left_attack;
				} else {
					sprite = Sprite.assassin_g_back_left_attack;
				}
			}
		}

		/*
		 * if (UNIT_G) { if (anim % 12 > 8) sprite =
		 * Sprite.assassin_g_front_left_frame_1; else if (anim % 12 > 4) sprite =
		 * Sprite.assassin_g_front_left_frame_2; else sprite =
		 * Sprite.assassin_g_front_left_frame_3; }
		 */

	}
	
	public void unitCollision(){	//only detects for collision with units.
		
		if(UNIT_R == true) {
			for(int i = 0; i < level.getGreenTeam().size(); i++) {
				Unit u = level.getGreenTeam().get(i);
				if(Math.abs(u.getX() - getX()) <= size * 5 && Math.abs(u.getY() - getY()) <= size*5) {
					u.changeHealth(100213, "assassinate");
					if(u.unitType.equals("Assassin")) {
						this.changeHealth(100213, "assassinate");
					}
				}
			}
		} else if(UNIT_G == true) {
			for(int i = 0; i < level.getRedTeam().size(); i++) {
				Unit u = level.getRedTeam().get(i);
				if(Math.abs(u.getX() - x) <= size * 5 && Math.abs(u.getY() - y) <= size*5) {
					u.changeHealth(100213, "assassinate");
					if(u.unitType.equals("Assassin")) {
						this.changeHealth(100213, "assassinate");
					}
				}
			}
		}
	}
	
	public void changeHealth(double dHealth, String damageType) {
		super.changeHealth(dHealth, damageType);
	}
	
	public double getProjectileRange() {
		return 0;
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
		return 1000;
	}


	
}
