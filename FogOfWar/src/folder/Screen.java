package folder;

import java.util.Random;

import folder.Sprite;
import projectiles.Projectile;
import folder.tile.Tile;

public class Screen {
	
	public int width; // dimensions of the SCREEN!
	public int height;
	public int[] pixels;
	public double xOffset, yOffset; //stores offset values that help account for taking into account player movement during rendering
	private Random random = new Random();
	private boolean[] pixelVisible;
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width*height]; //90,000
		pixelVisible = new boolean[width*height];
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}

		for(int i = 0; i < pixelVisible.length; i++) {
			pixelVisible[i] = false;
		}
	}
	
	public void updateOldPixels() {
		
	}

	
	public void renderSprite (int xp, int yp, Sprite sprite, boolean fixed) { //this is a render method that will render any square or rectangular sprite!
		if (fixed) { //differentiates between fixed and unfixed objects!
			xp -= xOffset;
			yp -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				
				if (xa < 0 || xa >= width || ya < 0 || ya > height) continue;
				pixels[xa + ya * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
		
	}
	
	public void renderTile(int xp, int yp,Tile tile) { //since the constructor is Tile but not Sprite, we are able to change the type of sprite it is. i.e. grass to rock
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see andrew ton.
				//"xa < -tile.sprite.SIZE" insures that we get an extra tile on the far left so rendering is smooth.
				if (xa < 0) xa = 0; //we set xa equal to zero if "xa < 0" in case we are on the border. It ONLY solves the arrayindexoutofbounds error. doesnt have affect rendering
				pixels[xa + ya * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];	//30ish fps drop cuz of darkening
			}
		}
	}

	//same as renderTile basically.
	public void renderProjectile(int xp, int yp, Projectile p) { //since the constructor is Tile but not Sprite, we are able to change the type of sprite it is. i.e. grass to rock
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see andrew ton.
				if (xa < 0) xa = 0;
				
				int col = p.getSprite().pixels[x + y * p.getSprite().SIZE];
				if (col != 0xffffffff)
				pixels[xa + ya * width] = col;
			}
		}
	}
	
	
	
	public void renderMob(int xp, int yp, Sprite sprite) { 
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -sprite.getWidth() || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
				if (xa < 0) xa = 0;
				
				int col = sprite.pixels[x + y * sprite.getWidth()]; //col is exactly the same as sprite.pixels[], but we use it because it helps us use the following if-statement...
				if (col != 0xffffffff) {//this if-statement tells the computer to not render pixels of a certain color. This is important, because when you draw stuff in paint, there are background colors you dont want. NOTE: Add an extra ff at the beginning, because...idk. If this doesn't work for you, see Andrew Ton!
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	public void renderPlayer(int xp, int yp, Sprite sprite){
		for(int y = 0; y < sprite.getHeight(); y++){
			for(int x = 0; x < sprite.getWidth(); x++) {
				int xs = x + xp;
				int ys = y + yp;
				
				if (xs >= width || xs < 0) {
					continue;
				}
				
				if (ys >= height || ys < 0) {
					continue;
				}
				
				int col = sprite.pixels[x + y * sprite.getWidth()]; //col is exactly the same as sprite.pixels[], but we use it because it helps us use the following if-statement...
				if (col != 0xffffffff) {//this if-statement tells the computer to not render pixels of a certain color. This is important, because when you draw stuff in paint, there are background colors you dont want. NOTE: Add an extra ff at the beginning, because...idk. If this doesn't work for you, see Andrew Ton!
					pixels[xs + ys * width] = col;
				}
			}
		}
	}

	public void renderFlash(int xp, int yp, int size) {
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for (int y = 0; y < size; y++) {
			int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
			for (int x = 0; x < size; x++) {
				int xa = x + xp;
				if (xa < -size || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
				if (xa < 0) xa = 0;
					pixels[xa + ya * width] = HexConverter.makeLighter(pixels[xa + ya * width], 100);
				
			}
		}
	}
	
	public void renderRange(int xp, int yp, double range, int color){
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		for(double i = 0; i < 360; i += 0.2){
			int xs = (int) (range * Math.cos(Math.toRadians(i))) + xp;
			int ys = (int) (range * Math.sin(Math.toRadians(i))) + yp;
			
			if (xs >= width || xs < 0) {
				continue;
			}
			
			if (ys >= height || ys < 0) {
				continue;
			}
			pixels[xs + ys * width] = color;
		}
	}
	
	public void renderVision(boolean[] redPixels, int levelH, int levelW){

		for (int y = (int) yOffset; y < yOffset + height; y++) {
			for (int x = (int) xOffset; x < xOffset + width; x++){
				if (x <= 0|| y <= 0 || redPixels.length <= (x + y * levelW * 16) || x >= levelW*16 || y >= levelH* 16) continue;
				if (redPixels[x + y * (levelW * 16)]){
					if(pixelVisible[(int) (x - xOffset + (y - yOffset) * width)] == false) { //GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
						pixels[(int) (x - xOffset + (y - yOffset) * width)] = HexConverter.makeLighter(pixels[(int) (x - xOffset + (y - yOffset) * width)], 50); //CHANGE THE 50 TO A 10 IF YOU WANT OVERLAPPING VISION
						pixelVisible[(int) (x - xOffset + (y - yOffset) * width)] = true; //GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
					} 		//GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
				
				}
			}
		}
	}
	
	//renders the visual aspect of fog. Results in a 150 fps drop. This is because it is RAM intensive since the vision for all units is constantly updated.
	/*
	public void renderVision(int xp, int yp, double range, double dir) {
		xp -= xOffset; //subtracting the Offset accounts for player movement. and makes xp relative to the screen.
		yp -= yOffset;
		

		
		for (int y = (int) (-1 * range); y < range; y++) {
			//xa and ya refer to the ABSOLUTE POSITION OF THE SPRITE IN REFERENCE TO THE LEVEL
			int ya = y + yp;
			for (int x = (int) (-1 * range); x < range; x++) { //x = 0, renders a semi-circle
				int xa = x + xp;
				double dist = MathMachine.distance(x,y);//Don't ask me how this works, just know that it gives an approximate value of the square root of a number
				if (dist <= range) { 
					if (xa <= 0 || xa >= width || ya <= 0 || ya >= height) continue;
					if(pixelVisible[xa + ya * width] == false) { //GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
						pixels[xa + ya * width] = HexConverter.makeLighter(pixels[xa + ya * width], 50); //CHANGE THE 50 TO A 10 IF YOU WANT OVERLAPPING VISION
						pixelVisible[xa + ya * width] = true; //GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
					} 		//GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
				}
			}
		}
		
	}
		
		*/
		

		/*
		for (int y = (int) (-1 * range); y < range; y++) {
			//xa and ya refer to the ABSOLUTE POSITION OF THE SPRITE IN REFERENCE TO THE LEVEL
			int ya = y + yp;
			for (int x = (int) (-1 * range); x < range; x++) { //x = 0, renders a semi-circle
				int xa = x + xp;
				double dist = MathMachine.distance(x,y);//Don't ask me how this works, just know that it gives an approximate value of the square root of a number
				if (dist <= range) { 
					if (xa <= 0 || xa >= width || ya <= 0 || ya >= height) continue;
					if(pixelVisible[xa + ya * width] == false) { //GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
						pixels[xa + ya * width] = HexConverter.makeLighter(pixels[xa + ya * width], 50); //CHANGE THE 50 TO A 10 IF YOU WANT OVERLAPPING VISION
						pixelVisible[xa + ya * width] = true; //GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
					} 		//GET RID OF THIS LINE IF YOU WANT OVERLAPPING VISION
				}
			}
		}
		*/
		
	

	public void setOffset(int xOffset, int yOffset) { //this method is called by Level.render
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void renderInterface(int xp, int yp, int iWidth, int iHeight, int group, int type, boolean clicked, boolean visible) {
		xp -= xOffset; //subtracting the Offset accounts for player movement. This also somehow reverses the inversion of the controls. Understand why we are subtracting as opposed to adding.
		yp -= yOffset;
		
		//if x or y covers one pixel in a for loop, that is for a boundary
		//the button variable detects what type of button it is
		if(group == 0) {
			if(type == 0) {
				for (int y = 0; y < iHeight; y++) {
					int ya = y + yp; // ya refers to absolute position in terms of screen. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
					for (int x = 0; x < iWidth; x++) {
						int xa = x + xp;
						if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
						if (xa < 0) xa = 0;
					
						if(y == 0 || x == 337 || x == 216 || x == 95) {
							pixels[xa + ya * width] = 0xffffffff;
						} else {
							pixels[xa + ya * width] = 0xaaaaaaaa;
						}
					}	
				}	
			} else if(type == 1) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == 30 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else if(y < 30) {
								pixels[xa + ya * width] = 0xffF2CF91;
							} else if(x == 54) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else {
								pixels[xa + ya * width] = 0xffEBCB98;
							}
						}
					}
				}
			} else if(type == 2) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else {
								pixels[xa + ya * width] = 0xffBFBFBF;
							}
						}
					}
				}
			} else if(type == 3) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
							
							if(y == 0 || y == 15 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else if(y < 15) {
								pixels[xa + ya * width] = 0xff70D16B;
							} else {
								pixels[xa + ya * width] = 0xff54BA4E;
							}
						}
					}
				}
			} else if(type == 4) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
					
							pixels[xa + ya * width] = 0xffCCB42B;
						}
					}
				}
			} else if(type == 5) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
					
							if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else {
								pixels[xa + ya * width] = 0xff00cc66;
							}
						}
					}
				}
			} 
		 
	
		} else if(group == 1) {
			if(type == 0 || type == 1) {
				if(visible == true) {
					if(clicked == false) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
						
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffC29313;
								} else {
									pixels[xa + ya * width] = 0xfedcbabc;
								}
							}
						}
					}
			
					else if(clicked == true) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
						
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffCFBD4A;
								} else {
									pixels[xa + ya * width] = 0xffFCE483;
								}
							}
						}
			
					}
				}
			} else if(type == 2) {
			
				if(clicked == false) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffC29313;
							} else {
								pixels[xa + ya * width] = 0xffA1F173;
							}
						}
					}
				}
			
				else if(clicked == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xff81F249;
							} else {
								pixels[xa + ya * width] = 0xff19FC00;
							}
						}
					}
				}
			}
		}
		
		else if(group == 2) {
			if(type == 0 || type == 1) {
				if(visible == true) {
					if(clicked == false) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
					
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffC29313;
								} else {
									pixels[xa + ya * width] = 0xfedcbabc;
								}
							}
						}
					}
		
					else if(clicked == true) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
					
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffCFBD4A;
								} else {
									pixels[xa + ya * width] = 0xffFCE483;
								}
							}
						}
		
					}
				}
			}
		}
		
		else if(group == 3) {
			if(type == 0) {
				for (int y = 0; y < iHeight; y++) {
					int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
					for (int x = 0; x < iWidth; x++) {
						int xa = x + xp;
						if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
						if (xa < 0) xa = 0;
						
						if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
							pixels[xa + ya * width] = 0xffFFFFFF;
						} else {
							pixels[xa + ya * width] = 0xffE3BC7D;
						}
					}
				}
			} else if(type == 1) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else {
								pixels[xa + ya * width] = 0xff86BECF;
							}
						}
					}
				}
			}
		}
		
		else if(group == 4) {
			if(type == 0 || type == 1 || type == 2) {
				if(visible == true) {
					if(clicked == false) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
								
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffC29313;
								} else {
									pixels[xa + ya * width] = 0xfedcbabc;
								}
							}
						}
					}
					
					else if(clicked == true) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
								
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffCFBD4A;
								} else {
									pixels[xa + ya * width] = 0xffFCE483;
								}
							}
						}
					
					}
				}
			}
		}
		
		else if(group == 5) {
			if(type == 0) {
				for (int y = 0; y < iHeight; y++) {
					int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
					for (int x = 0; x < iWidth; x++) {
						int xa = x + xp;
						if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
						if (xa < 0) xa = 0;
						
						if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
							pixels[xa + ya * width] = 0xffFFFFFF;
						} else {
							pixels[xa + ya * width] = 0xffE3BC7D;
						}
					}
				}
			} else if(type == 1) {
				if(visible == true) {
					if(clicked == false) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
							
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffFFFFFF;
								} else {
									pixels[xa + ya * width] = 0xff86BECF;
								}
							}
						}
					} else if(clicked == true) {
						for (int y = 0; y < iHeight; y++) {
							int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
							for (int x = 0; x < iWidth; x++) {
								int xa = x + xp;
								if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
								if (xa < 0) xa = 0;
							
								if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
									pixels[xa + ya * width] = 0xffCFBD4A;
								} else {
									pixels[xa + ya * width] = 0xffFCE483;
								}
							}
						}
					}
				}
			}
		}
		
		else if(group == 6) {
			if(type == 0 || type == 1 || type == 2) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else {
								pixels[xa + ya * width] = 0xff86BECF;
							}
						}
					}
				}
			}
		} 
		
		else if(group == 7) {
			if(type == 0 || type == 1 || type == 2 || type == 3 || type == 4 || type == 5) {
				if(visible == true) {
					for (int y = 0; y < iHeight; y++) {
						int ya = y + yp; // ya refers to absolute position. y is the rendering thing moving from left to right. yp is the offset. The reason for needing an absolute position is that this method only renders a single tile
						for (int x = 0; x < iWidth; x++) {
							int xa = x + xp;
							if (xa < -32 || xa >= width || ya < 0 || ya >= height) break; //width and height refer to the screen width and height. this if statement insures that only tiles on the screen are rendered! There are 4 bounds representing top, bottom, left and right. the far left bound isn't zero because...see Andrew ton.
							if (xa < 0) xa = 0;
						
							if(y == 0 || y == 75 || y == iHeight - 1 || x == 0 || x == iWidth - 1) {
								pixels[xa + ya * width] = 0xffFFFFFF;
							} else {
								pixels[xa + ya * width] = 0xffB5A1B5;
							}
						}
					}
				}
			}
		}
}
	

}
