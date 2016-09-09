package units;

import projectiles.LaserProjectile;
import folder.Sprite;

public class VisionTower extends Unit {

	int size = 4; // size of collision box

	public VisionTower(int x, int y, boolean playerR, boolean playerB) {
		super(x, y, playerR, playerB);

		action = 1;
		move_range = 0;
		projectile_range = 0;
		vision_range = 100;
		vision_width = 10;
		// TODO Auto-generated constructor stub
		health = 1;
		maxHealth = health;
		visibleToEnemy = true; // makes sure it is always visible
	}

	@Override
	protected void assignTeam(boolean playerR, boolean playerB) {
		sprite = Sprite.tower_n_frame_6;
	}

	public void update() {
		removed = false;
		//unitCollision(); 
		
		//currently, towers don't have a health system.
		/*
		if(health == 0) {
			if(UNIT_G) {
				level.getGreenTeam().remove(this);
			} else if(UNIT_R) {
				level.getRedTeam().remove(this);
			}
			level.getEntities().remove(this);
		}
		*/
		updateAnimation();
	}
	// only detects for collision with units. Responsible for capturing towers
	public void unitCollision() { 
		for (int i = 0; i < level.getGreenTeam().size(); i++) {
			if (UNIT_G) continue; //prevents infinite loop of adding unit
			Unit u = level.getGreenTeam().get(i);
			if (Math.abs(u.getX() - getX()) <= size * 5 && Math.abs(u.getY() - getY()) <= size * 5) {
				UNIT_R = false;
				UNIT_G = true;
				level.addGreenTower(this);
				level.removeRedTower(this);
			}
		}

		for (int i = 0; i < level.getRedTeam().size(); i++) {
			if (UNIT_R) continue;//prevents infinite loop of adding unit

			Unit u = level.getRedTeam().get(i);
			if (Math.abs(u.getX() - x) <= size * 5 && Math.abs(u.getY() - y) <= size * 5) {
				UNIT_G = false;
				UNIT_R = true;
				level.addRedTower(this);
				level.removeGreenTower(this);
			}
		}

	}

	@Override
	protected void animate(int anim) {
		// TODO Auto-generated method stub
		if(health > 0) {
			if (UNIT_R) {
				if (anim % 72 > 60) sprite = Sprite.tower_r_frame_6;
				else if (anim % 72 > 48) sprite = Sprite.tower_r_frame_5;
				else if (anim % 72 > 36) sprite = Sprite.tower_r_frame_4;
				else if (anim % 72 > 24) sprite = Sprite.tower_r_frame_3;
				else if (anim % 72 > 12) sprite = Sprite.tower_r_frame_2;
				else sprite = Sprite.tower_r_frame_1;
			}
			else if (UNIT_G) {
				if (anim % 72 > 60) sprite = Sprite.tower_g_frame_6;
				else if (anim % 72 > 48) sprite = Sprite.tower_g_frame_5;
				else if (anim % 72 > 36) sprite = Sprite.tower_g_frame_4;
				else if (anim % 72 > 24) sprite = Sprite.tower_g_frame_3;
				else if (anim % 72 > 12) sprite = Sprite.tower_g_frame_2;
				else sprite = Sprite.tower_g_frame_1;
			}
			else {
				if (anim % 72 > 60) sprite = Sprite.tower_n_frame_6;
				else if (anim % 72 > 48) sprite = Sprite.tower_n_frame_5;
				else if (anim % 72 > 36) sprite = Sprite.tower_n_frame_4;
				else if (anim % 72 > 24) sprite = Sprite.tower_n_frame_3;
				else if (anim % 72 > 12) sprite = Sprite.tower_n_frame_2;
				else sprite = Sprite.tower_n_frame_1;
			}
		}
	}

	@Override
	protected void shoot(double x, double y, double dir) {
	}

	@Override
	protected void updateShooting() {
	}

	@Override
	public double getMoveRange() {
		return move_range;
	}

	@Override
	public double getProjectileRange() {
		return projectile_range;
	}

	@Override
	public double getVisionRange() {
		// TODO Auto-generated method stub
		return vision_range;
	}

	@Override
	public double getDamage() {
		return 0;
	}
	
	//does nothing cuz it is invincible
	public void changeHealth(double dHealth, String damageType) { 
	}

}
