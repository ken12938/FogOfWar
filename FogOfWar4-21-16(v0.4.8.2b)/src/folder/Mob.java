package folder;




public abstract class Mob extends Entity{
	
	protected Sprite sprite;
	protected double dir = 0;
	protected boolean moving = false;
	
	
	
	public void move(double xa, double ya) { //xa and ya track the change in position
		
		//Implementing this if-statement causes units to slide along walls indefinitely, if they can't reach their destination.
		if ( xa != 0 && ya != 0) { //this says if we are moving in two axis' at once, this separates it into two different commands.
			move(xa, 0);
			move(0, ya);
			return;
		}
		

		
		//this while loop allows us to use collision detection for faster moving units. It checks collision in INCREMENTS. However. Currently it causes too many collision problems with the units.

		while (xa != 0){ 

			if (Math.abs(xa) > 1) { // if the xa is more than onetile... (in negative OR positive direction)
				if (!tileCollision(abs(xa), abs(ya))) { //"abs()" is different than "Math.abs()" !!
					this.x += abs(xa);
				}
				xa -= abs(xa);
			} else {
				if (!tileCollision(abs(xa), abs(ya)))
					this.x += xa;	
				xa = 0;
			}
			
		}
			
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!tileCollision(abs(xa), abs(ya))){
					this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!tileCollision(abs(xa), abs(ya)))
					this.y += ya;
				ya = 0;
			}
		}	
		return;
	}
	
	

	
	public abstract void update();  //abstract means that subclasses must be define this method.
	
	public abstract void render(Screen screen);
	
	protected void shoot (double x, double y, double dir) { //dont actually use this lol.
	}
	
	protected boolean tileCollision(double xa, double ya) { //returns true if there is a collision
		boolean solid = false;
		
		for (int c = 0; c < 4; c++) { //this checks all 4 corners for collision
			double xt = ((x + xa) - c % 2 * 8 ) / 16; // i honestly don't know hoW this works. It just does. Episode 65.
			double yt = ((y + ya) - c / 2  * 8 - 2) / 16;
			
			int ix = (int) Math.ceil(xt); //returns the smallest integer greater than or equal to a number
			int iy = (int) Math.ceil(yt);
			
			if (level.getTile(ix, iy).solid()) solid = true; //this is the important statement for collision detection. It works by looking at the tile you want to go to and seeing if it is solid.
		}
		
		return solid;
	}
		
	
}
