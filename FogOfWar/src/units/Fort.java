package units;

import folder.Sprite;

public class Fort extends Unit {
	
	public Fort(int x, int y, boolean playerR, boolean playerB) {
		super(x, y, playerR, playerB);
		
		move_range = 0;
		projectile_range = 0;
		vision_range = 0;
		vision_width = 0;
		
		health = 500;
		maxHealth = health;
		visibleToEnemy = true;
	}

	@Override
	protected void assignTeam(boolean playerR, boolean playerB) {
		if(playerR == true) {
			sprite = Sprite.castle_r;
		} else if(playerB == true) {
			sprite = Sprite.castle_g;
		}
	}
	
	protected void animate(int anim) {
		
	}
	
	public void update() {
		removed = false;
		updateAnimation();
	}
	
	public void changeHealth(double dHealth, String damageType) {
		if (damageType.equals("bullet")) {
			super.changeHealth(0.8 * dHealth, damageType);
		} else if (damageType.equals("shell")) {
			super.changeHealth(1.2 * dHealth, damageType);
		} else if (damageType.equals("explosive")) {
			super.changeHealth(1.5 * dHealth, damageType);
		}
		
		//assassin does no damage to the fort
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

}
