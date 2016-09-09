package folder.tile;

import folder.Screen;
import folder.Sprite;

//this will be the superclass for types of tiles such as rock, grass, etc.
//each tile needs to have a position, sprite (allows it to be visible), solid?
public class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile grass_vis = new GrassTile(Sprite.grass); // notice: Tile is set equal to GrassTile which is its subclass...understand?
	public static Tile rock_vis = new RockTile(Sprite.rock);
	public static Tile flower_vis = new FlowerTile(Sprite.flower);
	public static Tile bush_vis = new BushTile(Sprite.bush);
	public static Tile grain_vis = new GrainTile(Sprite.grain);
	public static Tile wall_vis = new WallTile(Sprite.wall);
	public static Tile wall2_vis = new WallTile(Sprite.wall2);
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);

	public static Tile ceilingstdalon = new WallTile(Sprite.ceilingstdalon);
	public static Tile ceilingupperleft = new WallTile(Sprite.ceilingupperleft);
	public static Tile ceilingupper = new WallTile(Sprite.ceilingupper);
	public static Tile ceilingupperright = new WallTile(Sprite.ceilingupperright);
	public static Tile ceilingcenterleft = new WallTile(Sprite.ceilingcenterleft);
	public static Tile ceilingcenter = new WallTile(Sprite.ceilingcenter);
	public static Tile ceilingcenterright = new WallTile(Sprite.ceilingcenterright);
	public static Tile ceilinglowerleft = new WallTile(Sprite.ceilinglowerleft);
	public static Tile ceilinglower = new WallTile(Sprite.ceilinglower);
	public static Tile ceilinglowerright = new WallTile(Sprite.ceilinglowerright);
	public static Tile wallstandalone = new GrassTile(Sprite.wallstandalone);
	public static Tile wallleft = new GrassTile(Sprite.wallleft);
	public static Tile wallcenter = new GrassTile(Sprite.wallcenter);
	public static Tile wallright = new GrassTile(Sprite.wallright);
	public static Tile singlewallupper = new WallTile(Sprite.singlewallupper);
	public static Tile singlewalluppercenter = new WallTile(Sprite.singlewalluppercenter);
	public static Tile singlewallcenter = new WallTile(Sprite.singlewallcenter);
	public static Tile singlewalllowercenter = new WallTile(Sprite.singlewalllowercenter);
	public static Tile singlewalllower = new WallTile(Sprite.singlewalllower);
	public static Tile singlewallright = new WallTile(Sprite.singlewallright);
	public static Tile singlewallrightcenter = new WallTile(Sprite.singlewallrightcenter);
	public static Tile singlewallleftcenter = new WallTile(Sprite.singlewallleftcenter);
	public static Tile singlewallleft = new WallTile(Sprite.singlewallleft);
	
	public static Tile concrete = new GrassTile(Sprite.concrete);
	public static Tile brickceilingcrack1 = new WallTile(Sprite.brickceilingcrack1);
	public static Tile brickceilingcrack2 = new WallTile(Sprite.brickceilingcrack2);
	public static Tile brickceilingcrack3 = new WallTile(Sprite.brickceilingcrack3);
	public static Tile roadconcreteupperleft = new GrassTile(Sprite.roadconcreteupperleft);
	public static Tile roadconcreteupper = new GrassTile(Sprite.roadconcreteupper);
	public static Tile roadconcreteupperright = new GrassTile(Sprite.roadconcreteupperright);
	public static Tile roadconcretecenterleft = new GrassTile(Sprite.roadconcretecenterleft);
	public static Tile roadconcretecenter = new GrassTile(Sprite.roadconcretecenter);
	public static Tile roadconcretecenterright = new GrassTile(Sprite.roadconcretecenterright);
	public static Tile roadconcretelowerleft = new GrassTile(Sprite.roadconcretelowerleft);
	public static Tile roadconcretelowercenter = new GrassTile(Sprite.roadconcretelowercenter);
	public static Tile roadconcretelowerright = new GrassTile(Sprite.roadconcretelowerright);
	public static Tile roadhorizontal = new GrassTile(Sprite.roadhorizontal);
	public static Tile roadverticle = new GrassTile(Sprite.roadverticle);
	public static Tile brickceilingstandalone = new WallTile(Sprite.brickceilingstandalone);
	public static Tile brickceilingupperleft = new WallTile(Sprite.brickceilingupperleft);
	public static Tile brickceilingupper = new WallTile(Sprite.brickceilingupper);
	public static Tile brickceilingupperright = new WallTile(Sprite.brickceilingupperright);
	public static Tile brickceilingcenterleft = new WallTile(Sprite.brickceilingcenterleft);
	public static Tile brickceilingcenter = new WallTile(Sprite.brickceilingcenter);
	public static Tile brickceilingcenterright = new WallTile(Sprite.brickceilingcenterright);
	public static Tile brickceilinglowerleft = new WallTile(Sprite.brickceilinglowerleft);
	public static Tile brickceilinglower = new WallTile(Sprite.brickceilinglower);
	public static Tile brickceilinglowerright = new WallTile(Sprite.brickceilinglowerright);
	public static Tile brickceiling = new WallTile(Sprite.brickceiling);
	public static Tile bricksinglewallhorizontal = new WallTile(Sprite.bricksinglewallhorizontal);
	public static Tile bricksinglewallvertical = new WallTile(Sprite.bricksinglewallvertical);
	public static Tile bricksinglewallupper = new WallTile(Sprite.bricksinglewallupper);
	public static Tile bricksinglewallcenter = new WallTile(Sprite.bricksinglewallcenter);
	public static Tile bricksinglewalllower = new WallTile(Sprite.bricksinglewalllower);
	public static Tile bricksinglewallleft = new WallTile(Sprite.bricksinglewallleft);
	public static Tile bricksinglewallright = new WallTile(Sprite.bricksinglewallright);
	public static Tile bricksinglewalltright = new WallTile(Sprite.bricksinglewalltright);
	public static Tile bricksinglewalltlower = new WallTile(Sprite.bricksinglewalltlower);
	public static Tile bricksinglewalltleft = new WallTile(Sprite.bricksinglewalltleft);
	public static Tile bricksinglewalltupper = new WallTile(Sprite.bricksinglewalltupper);
	public static Tile concretemoss = new GrassTile(Sprite.concretemoss);
	public static Tile concretecrack1 = new GrassTile(Sprite.concretecrack1);
	public static Tile concretecrack2 = new GrassTile(Sprite.concretecrack2);
	public static Tile concretefirehydrant = new GrassTile(Sprite.concretefirehydrant);
	public static Tile brickwallstandalone = new GrassTile(Sprite.brickwallstandalone);
	public static Tile brickwallleft = new GrassTile(Sprite.brickwallleft);
	public static Tile brickwallcenter = new GrassTile(Sprite.brickwallcenter);
	public static Tile brickwallright = new GrassTile(Sprite.brickwallright);
	
	
	
	
	
	//Visit MapTile Key in the google drive to modify and add 
	// keeps track of the color on the pixel-size map that refers to grass. There is one for each tile. See getTile method in Level class.
	public static int col_grass = 0xff4CFF00;
	public static int col_rock = 0xff404040;
	public static int col_flower = 0xffFFD800;
	public static int col_bush = 0xff267F00;
	public static int col_grain = 0xff7F6A00;
	public static int col_wall = 0xff000000;
	public static int col_wall2 = 0xff7F7F7F;
	
	public static int col_ceilingstdalone = 0xff98FB98;
	public static int col_ceilingupperleft = 0xffADFF2F;
	public static int col_ceilingupper = 0xff7FFF00;
	public static int col_ceilingupperright = 0xff7CFC00;
	public static int col_ceilingcenterleft = 0xff00FF00;
	public static int col_ceilingcenter = 0xff00FF7F;
	public static int col_ceilingcenterright = 0xff00FA9A;
	public static int col_ceilinglowerleft = 0xff90EE90;
	public static int col_ceilinglower = 0xff32CD32;
	public static int col_ceilinglowerright = 0xff3CB371;
	public static int col_wallstandalone = 0xff8A2BE2;
	public static int col_wallleft = 0xff9400D3;
	public static int col_wallcenter = 0xff9932CC;
	public static int col_wallright = 0xff8B008B;
	public static int col_singlewallupper = 0xffFFFFFF;
	public static int col_singlewalluppercenter = 0xffF5FFFA;
	public static int col_singlewallcenter = 0xffF0FFFF;
	public static int col_singlewalllowercenter = 0xffF0FFF0;
	public static int col_singlewalllower = 0xffE0FFFF;
	public static int col_singlewallright = 0xffF0F8FF;
	public static int col_singlewallrightcenter = 0xffF5F5F5;
	public static int col_singlewallleftcenter = 0xffF8F8FF;
	public static int col_singlewallleft = 0xffFFFFF0;
	
	public static int col_concrete = 0xffC0C0C0;
	public static int col_brickceilingcrack1 = 0xff000001;
	public static int col_brickceilingcrack2 = 0xff000002;
	public static int col_brickceilingcrack3 = 0xff000003;
	public static int col_roadconcreteupperleft = 0xff000004;
	public static int col_roadconcreteupper = 0xff000005;
	public static int col_roadconcreteupperright = 0xff000006;
	public static int col_roadconcretecenterleft = 0xff000007;
	public static int col_roadconcretecenter = 0xff000008;
	public static int col_roadconcretecenterright = 0xff000009;
	public static int col_roadconcretelowerleft = 0xff000010;
	public static int col_roadconcretelowercenter = 0xff000011;
	public static int col_roadconcretelowerright = 0xff000012;
	public static int col_roadhorizontal = 0xff000013;
	public static int col_roadverticle = 0xff000014;
	public static int col_brickceilingstandalone = 0xff000015;
	public static int col_brickceilingupperleft = 0xff000016;
	public static int col_brickceilingupper = 0xff000017;
	public static int col_brickceilingupperright = 0xff000018;
	public static int col_brickceilingcenterleft = 0xff000019;
	public static int col_brickceilingcenter = 0xff000020;
	public static int col_brickceilingcenterright = 0xff000021;
	public static int col_brickceilinglowerleft = 0xff000022;
	public static int col_brickceilinglower = 0xff000023;
	public static int col_brickceilinglowerright = 0xff000024;
	public static int col_brickceiling = 0xff000025;
	public static int col_bricksinglewallhorizontal = 0xff000026;
	public static int col_bricksinglewallvertical = 0xff000027;
	public static int col_bricksinglewallupper = 0xff000028;
	public static int col_bricksinglewallcenter = 0xff000029;
	public static int col_bricksinglewalllower = 0xff000030;
	public static int col_bricksinglewallleft = 0xff000031;
	public static int col_bricksinglewallright = 0xff000032;
	public static int col_bricksinglewalltright = 0xff000033;
	public static int col_bricksinglewalltlower = 0xff000034;
	public static int col_bricksinglewalltleft = 0xff000035;
	public static int col_bricksinglewalltupper = 0xff000036;
	public static int col_concretemoss = 0xff000037;
	public static int col_concretecrack1 = 0xff000038;
	public static int col_concretecrack2 = 0xff000039;
	public static int col_concretefirehydrant = 0xff000040;
	public static int col_brickwallstandalone = 0xff000041;
	public static int col_brickwallleft = 0xff000042;
	public static int col_brickwallcenter = 0xff000043;
	public static int col_brickwallright = 0xff000044;

	public Tile(Sprite sprite) { // constructor
		this.sprite = sprite;
	}

	public void render(int x, int y, Screen screen) {
	}

	public boolean solid() { // makes the tile solid by default. must be overriden in subclass to make it collideable
		return false;
	}

	public boolean breakable() { // makes the tile unbreakable, but subclasses can overrirde
		return false;
	}
}
