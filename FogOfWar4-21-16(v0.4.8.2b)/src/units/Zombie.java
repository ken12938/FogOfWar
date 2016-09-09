package units;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import folder.Entity;
import folder.MathMachine;
import folder.Screen;
import folder.Sprite;

public class Zombie extends Assassin {

	private int swarm; // swarm #
	public double swarmX;
	public double swarmY;
	private double swarm_range; // range of the swarm

	private double wander_speed;
	private double charge_speed;
	
	public boolean targeting; // keeps track if the unit is in random mode or targeting mode
	private Unit closest; // keeps track of the unit it is following
	public boolean charging; //is it charging with the horde?
	public boolean returning; // is it returning to swarm?
	public boolean wandering;//is it wandering?
	
	public Zombie(int x, int y, boolean playerR, boolean playerB, int swarm) {
		super(x, y, playerR, playerB);
		this.swarm = swarm;
		
		targeting = false;
		charging = false;
		returning = false;
		wandering = false;
		
		
		move_range = 70;
		speed = wander_speed = .25;
		charge_speed = .65;
		swarm_range = 70;
		vision_width = 7;
		vision_range = 100;
	}

	// override
	protected void assignTeam(boolean playerR, boolean playerB) { // add more team specific properties
		if (playerR == true) sprite = Sprite.zombie_front_left;
		else if (playerB == true) sprite = Sprite.zombie_front_left;
	}

	// responsible for hivemind behavior of zombie
	protected void groupThink() {

	}

	// Same thing as target() but for units
		public boolean sense() {
			closest = null; // this variable will contain the closest Unit
			Double min = (double) vision_range; // this will contain the lowest distance
			List<Entity> entities = level.getEntities();
			targeting = false; // careful with this line of code dawg. this only works right now cuz th ecode only runs once

			for (int i = 0; i < entities.size(); i++) {
				Entity e = entities.get(i);
				Unit u = (Unit) e; // casts e to Unit
				if (u.equals(this) || u.equals(closest)) continue; // discounts self and unit currently targeted
				if (u.UNIT_R == UNIT_R && u.UNIT_G == UNIT_G) continue; // discounts Units from own team
				if (!(e instanceof Unit)) continue; // discounts non-Unit entities
				
				double distance = MathMachine.distance((u.getX() - x), (u.getY() - y));

				/*
				 * int r = 0; int g = 0; for (int y = 0; y < level.getHeight() * 16; y++) { for (int x = 0; x < level.getWidth() * 16; x++) { if (level.redPixels[x + y * level.getWidth() * 16] == true) r++; if (level.greenPixels[x + y * level.getWidth() * 16] == true )g++; System.out.println("r: " + r + ", " + g); } }
				 */
				if (UNIT_R) { // if it is a red unit

				}
				if (UNIT_G) { // if it is a green unit
					if (distance < min) {
						
						if (level.greenPixels[(int) Math.round(u.getX() + level.getWidth() * 16.0 * u.getY())]) {
							
							targeting = true;
							closest = u;
							min = distance;
							speed = charge_speed;
						}
					}
				}

			}

			return targeting; // the program is fucked if null happens. just a headsup. hopefully you will remember if you get a null pointer exception.
		}
	
	//charges with the swarrrmmmmmm!!
	public boolean charge() {
		charging = false;
		if (targeting == true) return charging;
		
		List <Unit> zombies = level.getGreenTeam();
		for (int i = 0; i < zombies.size(); i++){
			if (zombies.get(i) instanceof Zombie){
				Zombie z = (Zombie) zombies.get(i);
				if (z.closest != null && z.swarm == swarm) {
					this.closest = z.closest;
					charging = true;
				}
			}
		}
		
		return charging;
	}
	
	// returns a zombie to the swarm if necessary
	public boolean reCenter() {
		returning = false;
		List<Unit> zombies = level.getGreenTeam();
		double xt = 0; // calculates swarm center
		double yt = 0;
		double swarmSize = 0;

		for (int i = 0; i < level.getGreenTeam().size(); i++) {
			if (zombies.get(i) instanceof Zombie) {
				Zombie z = (Zombie) zombies.get(i);
				if (z.getSwarm() == swarm) {
					swarmSize++;
					MathMachine.distance(zombies.get(i).getX() - this.x, zombies.get(i).getY() - this.y);
					xt += zombies.get(i).getX();
					yt += zombies.get(i).getY();
				}
			}
		}
		swarmX = xt / swarmSize;
		swarmY = yt / swarmSize;

		if (MathMachine.distance(this.x - swarmX, this.y - swarmY) > swarm_range) returning = true;
		return returning;
	}

	public void wander(){
		Random rand = new Random();
		wandering = true;
		 xD = rand.nextInt((int) (move_range)) + (int) (this.x - move_range / 2);
		 yD = rand.nextInt((int) (move_range)) + (int) (this.y - move_range / 2);
		while (xD < 8 || xD > level.getWidth() * 16 || yD > level.getHeight() * 16 || yD < 8) {
			xD = rand.nextInt((int) (move_range)) + (int) (this.x - move_range / 2);
			yD = rand.nextInt((int) (move_range)) + (int) (this.y - move_range / 2);
		}
	}
	//only detects for collision with units. Overrides assasin.
	public void unitCollision(){	
		
		if(UNIT_R == true) {
			for(int i = 0; i < level.getGreenTeam().size(); i++) {
				Unit u = level.getGreenTeam().get(i);
				if(Math.abs(u.getX() - getX()) <= size * 5 && Math.abs(u.getY() - getY()) <= size*5) {
					u.changeHealth(100213, "bullet");
					if(u.unitType.equals("Assassin")) {
						this.changeHealth(100213, "assassinate");
					}
				}
			}
		} else if(UNIT_G == true) {
			for(int i = 0; i < level.getRedTeam().size(); i++) {
				Unit u = level.getRedTeam().get(i);
				if(Math.abs(u.getX() - x) <= size * 5 && Math.abs(u.getY() - y) <= size*5) {
					u.changeHealth(100213, "bullet");
					if(u.unitType.equals("Assassin")) {
						this.changeHealth(100213, "assassinate");
					}
				}
			}
		}
	}

	protected void animate(int anim) {

		if (UNIT_R) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24) sprite = Sprite.zombie_back_right_idle_1;

					else sprite = Sprite.zombie_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24) sprite = Sprite.zombie_front_right_idle_1;
					else sprite = Sprite.zombie_front_right;
				}

				else if (dir >= 3.14 / 2 && dir < 3.14) {
					if (anim % 48 > 24) sprite = Sprite.zombie_front_left_idle_1;
					else sprite = Sprite.zombie_front_left;
				}

				else {
					if (anim % 48 > 24) sprite = Sprite.zombie_back_left_idle_1;
					else sprite = Sprite.zombie_back_left;
				}
			}

			else if (action == 1) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8) sprite = Sprite.zombie_back_right_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_back_right_frame_2;
					else sprite = Sprite.zombie_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8) sprite = Sprite.zombie_front_right_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_front_right_frame_2;
					else sprite = Sprite.zombie_front_right;
				} else if (dir >= 3.14 / 2 && dir < 3.14) {
					if (anim % 12 > 8) sprite = Sprite.zombie_front_left_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_front_left_frame_2;
					else sprite = Sprite.zombie_front_left;
				} else {
					if (anim % 12 > 8) sprite = Sprite.zombie_back_left_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_back_left_frame_2;
					else sprite = Sprite.zombie_back_left;
				}
			}

		} else if (UNIT_G) {
			if (action == 0) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 48 > 24) sprite = Sprite.zombie_back_right_idle_1;

					else sprite = Sprite.zombie_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 48 > 24) sprite = Sprite.zombie_front_right_idle_1;
					else sprite = Sprite.zombie_front_right;
				}

				else if (dir >= 3.14 / 2 && dir < 3.14) {
					if (anim % 48 > 24) sprite = Sprite.zombie_front_left_idle_1;
					else sprite = Sprite.zombie_front_left;
				}

				else {
					if (anim % 48 > 24) sprite = Sprite.zombie_back_left_idle_1;
					else sprite = Sprite.zombie_back_left;
				}
			} else if (action == 1) {
				if (dir >= -3.14 / 2 && dir < 0) {
					if (anim % 12 > 8) sprite = Sprite.zombie_back_right_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_back_right_frame_2;
					else sprite = Sprite.zombie_back_right;
				}

				else if (dir >= 0 && dir < 3.14 / 2) {
					if (anim % 12 > 8) sprite = Sprite.zombie_front_right_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_front_right_frame_2;
					else sprite = Sprite.zombie_front_right;
				} else if (dir >= 3.14 / 2 && dir < 3.14) {
					if (anim % 12 > 8) sprite = Sprite.zombie_front_left_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_front_left_frame_2;
					else sprite = Sprite.zombie_front_left;
				} else {
					if (anim % 12 > 8) sprite = Sprite.zombie_back_left_frame_3;
					else if (anim % 12 > 4) sprite = Sprite.zombie_back_left_frame_2;
					else sprite = Sprite.zombie_back_left;
				}
			}

		}

		/*
		 * if (UNIT_G) { if (anim % 12 > 8) sprite = Sprite.zombie_front_left_frame_1; else if (anim % 12 > 4) sprite = Sprite.zombie_front_left_frame_2; else sprite = Sprite.zombie_front_left_frame_3; }
		 */

	}

	// override
	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite);
		animate(anim);
		if (!visibleToEnemy) { // this single if statement makes sure that even if the unit is spotted, its commands will not be visible to the opposing player
			// screen.renderVision((int) x + 8,(int) y + 8, getVisionRange(), dir); //adding 8 to the x and y coordinates centers the vision on the unit.

			// highlights selected units
			if (action == 1 || action == 2 || action == 3) {
				screen.renderMob((int) x, (int) y, Sprite.commandCursor);
				screen.renderMob((int) xD, (int) yD, Sprite.wayPoint);
			}
			if (action == 1 && selected) {
				screen.renderRange((int) x, (int) y, getMoveRange(), 0xff00FF3F); // renders the MOVEMENT range in light green
			}
			if (action == 2 && selected) {
				screen.renderRange((int) x, (int) y, getProjectileRange(), 0xff00FF3F);// renders the SHOOTING range in light green
			}
			if (action == 3 && selected) {
				screen.renderRange((int) x, (int) y, getMoveRange(), 0xff00FF3F);// renders the MOVEMENT range in light green
			}
			// highlights commanded units
			if (selected) {
				screen.renderMob((int) x, (int) y, Sprite.selectCursor);
			}

			renderHealth(screen); // renders all the health bars based on amount of unit health

		}
	}

	public void changeHealth(double dHealth, String damageType) {
		super.changeHealth(dHealth, damageType);
	}

	public Unit getClosest() {
		return closest;
	}

	public void reset() { // resets units at the end of turn
		super.reset();

		closest = null;
		speed = wander_speed;
		charging = false;
		targeting = false;
		returning = false;
		wandering = false;
	}

	public int getSwarm() {
		// TODO Auto-generated method stub
		return swarm;
	}
}
