package folder;

import java.util.Random;

public abstract class Entity { //abstract means it can't be instantiated.
	

	public double x, y; //only used if there is a sprite
	protected boolean removed = false; //if it is removed or nah
	protected Level level; //keeps track of what level you are on
	protected final Random random = new Random(); //for AI in the future
	
	
	protected void update () { //linked to update method in Game. still 60 ups
	}
	
	protected void render (Screen screen) { // we have a screen parameter because these entities CAN move unlike tiles
	}
	
	public void remove() {
		//Remove from level. note this doesnt actually remove it, we have to have other methods do the dirty work.
		removed = true;
	}
	
	public boolean isRemoved() { //returns if the entity is removed or not.
		return removed;
	}
	
	public void init(Level level) { //since we reference the Level class when checking for collisions in Mob, we must initialize Level somewhere.
		this.level = level;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}

	protected void reset(){ //entities dont really use this. only units!	
	}
	
	protected double  abs(double value) { //returns either 1 or -1 based on if a value is positive or not.
		if (value < 0) return -1.0;
		if (value > 0) return 1.0;
		else return 0;
	}
}
