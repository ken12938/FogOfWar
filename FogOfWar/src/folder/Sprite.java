package folder;




public class Sprite { //this object keeps track of INDIVIDUAL sprites.
	public final int SIZE; //size of the sprite, usually it will be 1 tile by 1 tile, but it could be bigger
	private int x, y; // position of the sprite in the spritesheet
	private int width, height;
	public int[] pixels; //the pixels data of the sprite
	private SpriteSheet sheet; //keeps track of which spritesheet the sprite it is located in
	
	//this block of code is where you initialize block types. They are by default fog tiles.
	
	// exact copy of the Sprites above but lighter.
	public static Sprite grass = new Sprite(16, 4, 0, SpriteSheet.tiles); //By creating a sprite in the sprite class, it makes the sprite final and unchangeable afterward.
	public static Sprite rock = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite flower = new Sprite(16, 2, 0, SpriteSheet.tiles);
	public static Sprite bush = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite grain = new Sprite(16, 3, 0, SpriteSheet.tiles);
	public static Sprite wall = new Sprite(16, 15, 1, SpriteSheet.tiles);
	public static Sprite wall2 = new Sprite(16, 8, 0, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite (16, 0x08A7FC); // creates a voidSprite which serves as a default filler tile that has a solid color. in this case, "0" indicates black

	public static Sprite ceilingstdalon = new Sprite(16, 10, 6, SpriteSheet.tiles);
	public static Sprite ceilingupperleft = new Sprite(16, 11, 4, SpriteSheet.tiles);
	public static Sprite ceilingupper = new Sprite(16, 12, 4, SpriteSheet.tiles);
	public static Sprite ceilingupperright = new Sprite(16, 13, 4, SpriteSheet.tiles);
	public static Sprite ceilingcenterleft = new Sprite(16, 11, 5, SpriteSheet.tiles);
	public static Sprite ceilingcenter = new Sprite(16, 12, 5, SpriteSheet.tiles);
	public static Sprite ceilingcenterright = new Sprite(16, 13, 5, SpriteSheet.tiles);
	public static Sprite ceilinglowerleft = new Sprite(16, 11, 6, SpriteSheet.tiles);
	public static Sprite ceilinglower = new Sprite(16, 12, 6, SpriteSheet.tiles);
	public static Sprite ceilinglowerright = new Sprite(16, 13, 6, SpriteSheet.tiles);
	public static Sprite wallstandalone = new Sprite(16, 10, 7, SpriteSheet.tiles);
	public static Sprite wallleft = new Sprite(16, 11, 7, SpriteSheet.tiles);
	public static Sprite wallcenter = new Sprite(16, 12, 7, SpriteSheet.tiles);
	public static Sprite wallright = new Sprite(16, 13, 7, SpriteSheet.tiles);
	public static Sprite singlewallupper = new Sprite(16, 12, 8, SpriteSheet.tiles);
	public static Sprite singlewalluppercenter = new Sprite(16, 12, 9, SpriteSheet.tiles);
	public static Sprite singlewallcenter = new Sprite(16, 12, 10, SpriteSheet.tiles);
	public static Sprite singlewalllowercenter = new Sprite(16, 12, 11, SpriteSheet.tiles);
	public static Sprite singlewalllower = new Sprite(16, 12, 12, SpriteSheet.tiles);
	public static Sprite singlewallright = new Sprite(16, 14, 10, SpriteSheet.tiles);
	public static Sprite singlewallrightcenter = new Sprite(16, 13, 10, SpriteSheet.tiles);
	public static Sprite singlewallleftcenter = new Sprite(16, 11, 10, SpriteSheet.tiles);
	public static Sprite singlewallleft = new Sprite(16, 10, 10, SpriteSheet.tiles);
	
	public static Sprite concrete = new Sprite(16, 4, 8, SpriteSheet.tiles);
	public static Sprite brickceilingcrack1 = new Sprite(16, 7, 3, SpriteSheet.tiles);
	public static Sprite brickceilingcrack2 = new Sprite(16, 8, 3, SpriteSheet.tiles);
	public static Sprite brickceilingcrack3 = new Sprite(16, 9, 3, SpriteSheet.tiles);
	public static Sprite roadconcreteupperleft = new Sprite(16, 3, 4, SpriteSheet.tiles);
	public static Sprite roadconcreteupper = new Sprite(16, 4, 4, SpriteSheet.tiles);
	public static Sprite roadconcreteupperright = new Sprite(16, 5, 4, SpriteSheet.tiles);
	public static Sprite roadconcretecenterleft = new Sprite(16, 3, 5, SpriteSheet.tiles);
	public static Sprite roadconcretecenter = new Sprite(16, 4, 5, SpriteSheet.tiles);
	public static Sprite roadconcretecenterright = new Sprite(16, 5, 5, SpriteSheet.tiles);
	public static Sprite roadconcretelowerleft = new Sprite(16, 3, 6, SpriteSheet.tiles);
	public static Sprite roadconcretelowercenter = new Sprite(16, 4, 6, SpriteSheet.tiles);
	public static Sprite roadconcretelowerright = new Sprite(16, 5, 6, SpriteSheet.tiles);
	public static Sprite roadhorizontal = new Sprite(16, 6, 4, SpriteSheet.tiles);
	public static Sprite roadverticle = new Sprite(16, 6, 5, SpriteSheet.tiles);
	public static Sprite brickceilingstandalone = new Sprite(16, 6, 6, SpriteSheet.tiles);
	public static Sprite brickceilingupperleft = new Sprite(16, 7, 4, SpriteSheet.tiles);
	public static Sprite brickceilingupper = new Sprite(16, 8, 4, SpriteSheet.tiles);
	public static Sprite brickceilingupperright = new Sprite(16, 9, 4, SpriteSheet.tiles);
	public static Sprite brickceilingcenterleft = new Sprite(16, 7, 5, SpriteSheet.tiles);
	public static Sprite brickceilingcenter = new Sprite(16, 8, 5, SpriteSheet.tiles);
	public static Sprite brickceilingcenterright = new Sprite(16, 9, 5, SpriteSheet.tiles);
	public static Sprite brickceilinglowerleft = new Sprite(16, 7, 6, SpriteSheet.tiles);
	public static Sprite brickceilinglower = new Sprite(16, 8, 6, SpriteSheet.tiles);
	public static Sprite brickceilinglowerright = new Sprite(16, 9, 6, SpriteSheet.tiles);
	public static Sprite brickceiling = new Sprite(16, 15, 15, SpriteSheet.tiles);
	public static Sprite bricksinglewallhorizontal = new Sprite(16, 7, 10, SpriteSheet.tiles);
	public static Sprite bricksinglewallvertical = new Sprite(16, 8, 10, SpriteSheet.tiles);
	public static Sprite bricksinglewallupper = new Sprite(16, 8, 9, SpriteSheet.tiles);
	public static Sprite bricksinglewallcenter = new Sprite(16, 8, 11, SpriteSheet.tiles);
	public static Sprite bricksinglewalllower = new Sprite(16, 8, 12, SpriteSheet.tiles);
	public static Sprite bricksinglewallleft = new Sprite(16, 6, 10, SpriteSheet.tiles);
	public static Sprite bricksinglewallright = new Sprite(16, 9, 10, SpriteSheet.tiles);
	public static Sprite bricksinglewalltright = new Sprite(16, 5, 8, SpriteSheet.tiles);
	public static Sprite bricksinglewalltlower = new Sprite(16, 6, 8, SpriteSheet.tiles);
	public static Sprite bricksinglewalltleft = new Sprite(16, 6, 9, SpriteSheet.tiles);
	public static Sprite bricksinglewalltupper = new Sprite(16, 5, 9, SpriteSheet.tiles);
	public static Sprite concretemoss = new Sprite(16, 3, 7, SpriteSheet.tiles);
	public static Sprite concretecrack1 = new Sprite(16, 4, 7, SpriteSheet.tiles);
	public static Sprite concretecrack2 = new Sprite(16, 5, 7, SpriteSheet.tiles);
	public static Sprite concretefirehydrant = new Sprite(16, 3, 8, SpriteSheet.tiles);
	public static Sprite brickwallstandalone = new Sprite(16, 6, 7, SpriteSheet.tiles);
	public static Sprite brickwallleft = new Sprite(16, 7, 7, SpriteSheet.tiles);
	public static Sprite brickwallcenter = new Sprite(16, 8, 7, SpriteSheet.tiles);
	public static Sprite brickwallright = new Sprite(16, 9, 7, SpriteSheet.tiles);

	//unit sprites
		
		public static Sprite marine_g_front_left = new Sprite(16, 3, 0, SpriteSheet.units);
		public static Sprite marine_g_front_left_idle_1 = new Sprite(16, 0, 8, SpriteSheet.units);
		public static Sprite marine_g_front_left_frame_2 = new Sprite(16, 1, 8, SpriteSheet.units);
		public static Sprite marine_g_front_left_frame_3 = new Sprite(16, 2, 8, SpriteSheet.units);
		public static Sprite marine_g_front_right = new Sprite(16, 7, 0, SpriteSheet.units);
		public static Sprite marine_g_front_right_idle_1 = new Sprite(16, 0, 10, SpriteSheet.units);
		public static Sprite marine_g_front_right_frame_2 = new Sprite(16, 1, 10, SpriteSheet.units);
		public static Sprite marine_g_front_right_frame_3 = new Sprite(16, 2, 10, SpriteSheet.units);
		public static Sprite marine_g_back_left = new Sprite(16, 9, 0, SpriteSheet.units);
		public static Sprite marine_g_back_left_idle_1 = new Sprite(16, 0, 11, SpriteSheet.units);
		public static Sprite marine_g_back_left_frame_2 = new Sprite(16, 1, 11, SpriteSheet.units);
		public static Sprite marine_g_back_left_frame_3 = new Sprite(16, 2, 11, SpriteSheet.units);
		public static Sprite marine_g_back_right = new Sprite(16, 5, 0, SpriteSheet.units);
		public static Sprite marine_g_back_right_idle_1 = new Sprite(16, 0, 9, SpriteSheet.units);
		public static Sprite marine_g_back_right_frame_2 = new Sprite(16, 1, 9, SpriteSheet.units);
		public static Sprite marine_g_back_right_frame_3 = new Sprite(16, 2, 9, SpriteSheet.units);
		
		public static Sprite marine_r_front_left = new Sprite(16, 4, 0, SpriteSheet.units);
		public static Sprite marine_r_front_left_idle_1 = new Sprite(16, 0, 12, SpriteSheet.units);
		public static Sprite marine_r_front_left_frame_2 = new Sprite(16, 1, 12, SpriteSheet.units);
		public static Sprite marine_r_front_left_frame_3 = new Sprite(16, 2, 12, SpriteSheet.units);
		public static Sprite marine_r_front_right = new Sprite(16, 8, 0, SpriteSheet.units);
		public static Sprite marine_r_front_right_idle_1 = new Sprite(16, 0, 14, SpriteSheet.units);
		public static Sprite marine_r_front_right_frame_2 = new Sprite(16, 1, 14, SpriteSheet.units);
		public static Sprite marine_r_front_right_frame_3 = new Sprite(16, 2, 14, SpriteSheet.units);
		public static Sprite marine_r_back_left = new Sprite(16, 10, 0, SpriteSheet.units);
		public static Sprite marine_r_back_left_idle_1 = new Sprite(16, 0, 15, SpriteSheet.units);
		public static Sprite marine_r_back_left_frame_2 = new Sprite(16, 1, 15, SpriteSheet.units);
		public static Sprite marine_r_back_left_frame_3 = new Sprite(16, 2, 15, SpriteSheet.units);
		public static Sprite marine_r_back_right = new Sprite(16, 6, 0, SpriteSheet.units);
		public static Sprite marine_r_back_right_idle_1 = new Sprite(16, 0, 13, SpriteSheet.units);
		public static Sprite marine_r_back_right_frame_2 = new Sprite(16, 1, 13, SpriteSheet.units);
		public static Sprite marine_r_back_right_frame_3 = new Sprite(16, 2, 13, SpriteSheet.units);
		
		
		public static Sprite tank_g_up_frame_1 = new Sprite(16, 0, 2, SpriteSheet.units);
		public static Sprite tank_g_up_frame_2 = new Sprite(16, 1, 2, SpriteSheet.units);
		public static Sprite tank_g_up_frame_3 = new Sprite(16, 2, 2, SpriteSheet.units);
		public static Sprite tank_g_right_frame_1 = new Sprite(16, 0, 1, SpriteSheet.units);
		public static Sprite tank_g_right_frame_2 = new Sprite(16, 1, 1, SpriteSheet.units);
		public static Sprite tank_g_right_frame_3 = new Sprite(16, 2, 1, SpriteSheet.units);
		public static Sprite tank_g_down_frame_1 = new Sprite(16, 0, 3, SpriteSheet.units);
		public static Sprite tank_g_down_frame_2 = new Sprite(16, 1, 3, SpriteSheet.units);
		public static Sprite tank_g_down_frame_3 = new Sprite(16, 2, 3, SpriteSheet.units);
		public static Sprite tank_g_left_frame_1 = new Sprite(16, 0, 0, SpriteSheet.units);
		public static Sprite tank_g_left_frame_2 = new Sprite(16, 1, 0, SpriteSheet.units);
		public static Sprite tank_g_left_frame_3 = new Sprite(16, 2, 0, SpriteSheet.units);
		
		public static Sprite tank_r_up_frame_1 = new Sprite(16, 0, 6, SpriteSheet.units);
		public static Sprite tank_r_up_frame_2 = new Sprite(16, 1, 6, SpriteSheet.units);
		public static Sprite tank_r_up_frame_3 = new Sprite(16, 2, 6, SpriteSheet.units);
		public static Sprite tank_r_right_frame_1 = new Sprite(16, 0, 5, SpriteSheet.units);
		public static Sprite tank_r_right_frame_2 = new Sprite(16, 1, 5, SpriteSheet.units);
		public static Sprite tank_r_right_frame_3 = new Sprite(16, 2, 5, SpriteSheet.units);
		public static Sprite tank_r_down_frame_1 = new Sprite(16, 0, 7, SpriteSheet.units);
		public static Sprite tank_r_down_frame_2 = new Sprite(16, 1, 7, SpriteSheet.units);
		public static Sprite tank_r_down_frame_3 = new Sprite(16, 2, 7, SpriteSheet.units);
		public static Sprite tank_r_left_frame_1 = new Sprite(16, 0, 4, SpriteSheet.units);
		public static Sprite tank_r_left_frame_2 = new Sprite(16, 1, 4, SpriteSheet.units);
		public static Sprite tank_r_left_frame_3 = new Sprite(16, 2, 4, SpriteSheet.units);
		
		public static Sprite grenadier_g_front_left = new Sprite(16, 3, 1, SpriteSheet.units);
		public static Sprite grenadier_g_front_left_idle_1 = new Sprite(16, 3, 8, SpriteSheet.units);
		public static Sprite grenadier_g_front_left_frame_2 = new Sprite(16, 4, 8, SpriteSheet.units);
		public static Sprite grenadier_g_front_left_frame_3 = new Sprite(16, 5, 8, SpriteSheet.units);
		public static Sprite grenadier_g_front_right = new Sprite(16, 7, 1, SpriteSheet.units);
		public static Sprite grenadier_g_front_right_idle_1 = new Sprite(16, 3, 10, SpriteSheet.units);
		public static Sprite grenadier_g_front_right_frame_2 = new Sprite(16, 4, 10, SpriteSheet.units);
		public static Sprite grenadier_g_front_right_frame_3 = new Sprite(16, 5, 10, SpriteSheet.units);
		public static Sprite grenadier_g_back_left = new Sprite(16, 9, 1, SpriteSheet.units);
		public static Sprite grenadier_g_back_left_idle_1 = new Sprite(16, 3, 11, SpriteSheet.units);
		public static Sprite grenadier_g_back_left_frame_2 = new Sprite(16, 4, 11, SpriteSheet.units);
		public static Sprite grenadier_g_back_left_frame_3 = new Sprite(16, 5, 11, SpriteSheet.units);
		public static Sprite grenadier_g_back_right = new Sprite(16, 5, 1, SpriteSheet.units);
		public static Sprite grenadier_g_back_right_idle_1 = new Sprite(16, 3, 9, SpriteSheet.units);
		public static Sprite grenadier_g_back_right_frame_2 = new Sprite(16, 4, 9, SpriteSheet.units);
		public static Sprite grenadier_g_back_right_frame_3 = new Sprite(16, 5, 9, SpriteSheet.units);
		public static Sprite grenadier_g_front_left_firing = new Sprite(16, 3, 6, SpriteSheet.units);
		public static Sprite grenadier_g_front_right_firing = new Sprite(16, 7, 6, SpriteSheet.units);
		public static Sprite grenadier_g_back_left_firing = new Sprite(16, 9, 6, SpriteSheet.units);
		public static Sprite grenadier_g_back_right_firing = new Sprite(16, 5, 6, SpriteSheet.units);
		
		public static Sprite grenadier_r_front_left = new Sprite(16, 4, 1, SpriteSheet.units);
		public static Sprite grenadier_r_front_left_idle_1 = new Sprite(16, 3, 12, SpriteSheet.units);
		public static Sprite grenadier_r_front_left_frame_2 = new Sprite(16, 4, 12, SpriteSheet.units);
		public static Sprite grenadier_r_front_left_frame_3 = new Sprite(16, 5, 12, SpriteSheet.units);
		public static Sprite grenadier_r_front_right = new Sprite(16, 8, 1, SpriteSheet.units);
		public static Sprite grenadier_r_front_right_idle_1 = new Sprite(16, 3, 14, SpriteSheet.units);
		public static Sprite grenadier_r_front_right_frame_2 = new Sprite(16, 4, 14, SpriteSheet.units);
		public static Sprite grenadier_r_front_right_frame_3 = new Sprite(16, 5, 14, SpriteSheet.units);
		public static Sprite grenadier_r_back_left = new Sprite(16, 10, 1, SpriteSheet.units);
		public static Sprite grenadier_r_back_left_idle_1 = new Sprite(16, 3, 15, SpriteSheet.units);
		public static Sprite grenadier_r_back_left_frame_2 = new Sprite(16, 4, 15, SpriteSheet.units);
		public static Sprite grenadier_r_back_left_frame_3 = new Sprite(16, 5, 15, SpriteSheet.units);
		public static Sprite grenadier_r_back_right = new Sprite(16, 6, 1, SpriteSheet.units);
		public static Sprite grenadier_r_back_right_idle_1 = new Sprite(16, 3, 13, SpriteSheet.units);
		public static Sprite grenadier_r_back_right_frame_2 = new Sprite(16, 4, 13, SpriteSheet.units);
		public static Sprite grenadier_r_back_right_frame_3 = new Sprite(16, 5, 13, SpriteSheet.units);
		public static Sprite grenadier_r_front_left_firing = new Sprite(16, 4, 6, SpriteSheet.units);
		public static Sprite grenadier_r_front_right_firing = new Sprite(16, 8, 6, SpriteSheet.units);
		public static Sprite grenadier_r_back_left_firing = new Sprite(16, 10, 6, SpriteSheet.units);
		public static Sprite grenadier_r_back_right_firing = new Sprite(16, 6, 6, SpriteSheet.units);
		
		public static Sprite sniper_g_front_left = new Sprite(16, 3, 3, SpriteSheet.units);
		public static Sprite sniper_g_front_left_idle_1 = new Sprite(16, 6, 8, SpriteSheet.units);
		public static Sprite sniper_g_front_left_frame_2 = new Sprite(16, 7, 8, SpriteSheet.units);
		public static Sprite sniper_g_front_left_frame_3 = new Sprite(16, 8, 8, SpriteSheet.units);
		public static Sprite sniper_g_front_right = new Sprite(16, 7, 3, SpriteSheet.units);
		public static Sprite sniper_g_front_right_idle_1 = new Sprite(16, 6, 10, SpriteSheet.units);
		public static Sprite sniper_g_front_right_frame_2 = new Sprite(16, 7, 10, SpriteSheet.units);
		public static Sprite sniper_g_front_right_frame_3 = new Sprite(16, 8, 10, SpriteSheet.units);
		public static Sprite sniper_g_back_left = new Sprite(16, 9, 3, SpriteSheet.units);
		public static Sprite sniper_g_back_left_idle_1 = new Sprite(16, 6, 11, SpriteSheet.units);
		public static Sprite sniper_g_back_left_frame_2 = new Sprite(16, 7, 11, SpriteSheet.units);
		public static Sprite sniper_g_back_left_frame_3 = new Sprite(16, 8, 11, SpriteSheet.units);
		public static Sprite sniper_g_back_right = new Sprite(16, 5, 3, SpriteSheet.units);
		public static Sprite sniper_g_back_right_idle_1 = new Sprite(16, 6, 9, SpriteSheet.units);
		public static Sprite sniper_g_back_right_frame_2 = new Sprite(16, 7, 9, SpriteSheet.units);
		public static Sprite sniper_g_back_right_frame_3 = new Sprite(16, 8, 9, SpriteSheet.units);
		public static Sprite sniper_g_front_left_firing = new Sprite(16, 3, 4, SpriteSheet.units);
		public static Sprite sniper_g_front_right_firing = new Sprite(16, 7, 4, SpriteSheet.units);
		public static Sprite sniper_g_back_left_firing = new Sprite(16, 9, 4, SpriteSheet.units);
		public static Sprite sniper_g_back_right_firing = new Sprite(16, 5, 4, SpriteSheet.units);
		
		public static Sprite sniper_r_front_left = new Sprite(16, 4, 3, SpriteSheet.units);
		public static Sprite sniper_r_front_left_idle_1 = new Sprite(16, 6, 12, SpriteSheet.units);
		public static Sprite sniper_r_front_left_frame_2 = new Sprite(16, 7, 12, SpriteSheet.units);
		public static Sprite sniper_r_front_left_frame_3 = new Sprite(16, 8, 12, SpriteSheet.units);
		public static Sprite sniper_r_front_right = new Sprite(16, 8, 3, SpriteSheet.units);
		public static Sprite sniper_r_front_right_idle_1 = new Sprite(16, 6, 14, SpriteSheet.units);
		public static Sprite sniper_r_front_right_frame_2 = new Sprite(16, 7, 14, SpriteSheet.units);
		public static Sprite sniper_r_front_right_frame_3 = new Sprite(16, 8, 14, SpriteSheet.units);
		public static Sprite sniper_r_back_left = new Sprite(16, 10, 3, SpriteSheet.units);
		public static Sprite sniper_r_back_left_idle_1 = new Sprite(16, 6, 15, SpriteSheet.units);
		public static Sprite sniper_r_back_left_frame_2 = new Sprite(16, 7, 15, SpriteSheet.units);
		public static Sprite sniper_r_back_left_frame_3 = new Sprite(16, 8, 15, SpriteSheet.units);
		public static Sprite sniper_r_back_right = new Sprite(16, 6, 3, SpriteSheet.units);
		public static Sprite sniper_r_back_right_idle_1 = new Sprite(16, 6, 13, SpriteSheet.units);
		public static Sprite sniper_r_back_right_frame_2 = new Sprite(16, 7, 13, SpriteSheet.units);
		public static Sprite sniper_r_back_right_frame_3 = new Sprite(16, 8, 13, SpriteSheet.units);
		public static Sprite sniper_r_front_left_firing = new Sprite(16, 4, 4, SpriteSheet.units);
		public static Sprite sniper_r_front_right_firing = new Sprite(16, 8, 4, SpriteSheet.units);
		public static Sprite sniper_r_back_left_firing = new Sprite(16, 10, 4, SpriteSheet.units);
		public static Sprite sniper_r_back_right_firing = new Sprite(16, 6, 4, SpriteSheet.units);
		
		public static Sprite assassin_g_front_left = new Sprite(16, 3, 2, SpriteSheet.units);
		public static Sprite assassin_g_front_left_idle_1 = new Sprite(16, 9, 8, SpriteSheet.units);
		public static Sprite assassin_g_front_left_frame_2 = new Sprite(16, 10, 8, SpriteSheet.units);
		public static Sprite assassin_g_front_left_frame_3 = new Sprite(16, 11, 8, SpriteSheet.units);
		public static Sprite assassin_g_front_right = new Sprite(16, 7, 2, SpriteSheet.units);
		public static Sprite assassin_g_front_right_idle_1 = new Sprite(16, 9, 10, SpriteSheet.units);
		public static Sprite assassin_g_front_right_frame_2 = new Sprite(16, 10, 10, SpriteSheet.units);
		public static Sprite assassin_g_front_right_frame_3 = new Sprite(16, 11, 10, SpriteSheet.units);
		public static Sprite assassin_g_back_left = new Sprite(16, 9, 2, SpriteSheet.units);
		public static Sprite assassin_g_back_left_idle_1 = new Sprite(16, 9, 11, SpriteSheet.units);
		public static Sprite assassin_g_back_left_frame_2 = new Sprite(16, 10, 11, SpriteSheet.units);
		public static Sprite assassin_g_back_left_frame_3 = new Sprite(16, 11, 11, SpriteSheet.units);
		public static Sprite assassin_g_back_right = new Sprite(16, 5, 2, SpriteSheet.units);
		public static Sprite assassin_g_back_right_idle_1 = new Sprite(16, 9, 9, SpriteSheet.units);
		public static Sprite assassin_g_back_right_frame_2 = new Sprite(16, 10, 9, SpriteSheet.units);
		public static Sprite assassin_g_back_right_frame_3 = new Sprite(16, 11, 9, SpriteSheet.units);
		public static Sprite assassin_g_front_left_attack = new Sprite(16, 3, 7, SpriteSheet.units);
		public static Sprite assassin_g_front_right_attack = new Sprite(16, 7, 7, SpriteSheet.units);
		public static Sprite assassin_g_back_left_attack = new Sprite(16, 9, 7, SpriteSheet.units);
		public static Sprite assassin_g_back_right_attack = new Sprite(16, 5, 7, SpriteSheet.units);
		
		public static Sprite assassin_r_front_left = new Sprite(16, 4, 2, SpriteSheet.units);
		public static Sprite assassin_r_front_left_idle_1 = new Sprite(16, 9, 12, SpriteSheet.units);
		public static Sprite assassin_r_front_left_frame_2 = new Sprite(16, 10, 12, SpriteSheet.units);
		public static Sprite assassin_r_front_left_frame_3 = new Sprite(16, 11, 12, SpriteSheet.units);
		public static Sprite assassin_r_front_right = new Sprite(16, 8, 2, SpriteSheet.units);
		public static Sprite assassin_r_front_right_idle_1 = new Sprite(16, 9, 14, SpriteSheet.units);
		public static Sprite assassin_r_front_right_frame_2 = new Sprite(16, 10, 14, SpriteSheet.units);
		public static Sprite assassin_r_front_right_frame_3 = new Sprite(16, 11, 14, SpriteSheet.units);
		public static Sprite assassin_r_back_left = new Sprite(16, 10, 2, SpriteSheet.units);
		public static Sprite assassin_r_back_left_idle_1 = new Sprite(16, 9, 15, SpriteSheet.units);
		public static Sprite assassin_r_back_left_frame_2 = new Sprite(16, 10, 15, SpriteSheet.units);
		public static Sprite assassin_r_back_left_frame_3 = new Sprite(16, 11, 15, SpriteSheet.units);
		public static Sprite assassin_r_back_right = new Sprite(16, 6, 2, SpriteSheet.units);
		public static Sprite assassin_r_back_right_idle_1 = new Sprite(16, 9, 13, SpriteSheet.units);
		public static Sprite assassin_r_back_right_frame_2 = new Sprite(16, 10, 13, SpriteSheet.units);
		public static Sprite assassin_r_back_right_frame_3 = new Sprite(16, 11, 13, SpriteSheet.units);
		public static Sprite assassin_r_front_left_attack = new Sprite(16, 4, 7, SpriteSheet.units);
		public static Sprite assassin_r_front_right_attack = new Sprite(16, 8, 7, SpriteSheet.units);
		public static Sprite assassin_r_back_left_attack = new Sprite(16, 10, 7, SpriteSheet.units);
		public static Sprite assassin_r_back_right_attack = new Sprite(16, 6, 7, SpriteSheet.units);

		public static Sprite rocketeer_g_front_left = new Sprite(16, 11, 4, SpriteSheet.units);
		public static Sprite rocketeer_g_front_left_idle_1 = new Sprite(16, 12, 8, SpriteSheet.units);
		public static Sprite rocketeer_g_front_left_frame_2 = new Sprite(16, 13, 8, SpriteSheet.units);
		public static Sprite rocketeer_g_front_left_frame_3 = new Sprite(16, 14, 8, SpriteSheet.units);
		public static Sprite rocketeer_g_front_right = new Sprite(16, 13, 4, SpriteSheet.units);
		public static Sprite rocketeer_g_front_right_idle_1 = new Sprite(16, 12, 10, SpriteSheet.units);
		public static Sprite rocketeer_g_front_right_frame_2 = new Sprite(16, 13, 10, SpriteSheet.units);
		public static Sprite rocketeer_g_front_right_frame_3 = new Sprite(16, 14, 10, SpriteSheet.units);
		public static Sprite rocketeer_g_back_right = new Sprite(16, 12, 4, SpriteSheet.units);
		public static Sprite rocketeer_g_back_right_idle_1 = new Sprite(16, 12, 9, SpriteSheet.units);
		public static Sprite rocketeer_g_back_right_frame_2 = new Sprite(16, 13, 9, SpriteSheet.units);
		public static Sprite rocketeer_g_back_right_frame_3 = new Sprite(16, 14, 9, SpriteSheet.units);
		public static Sprite rocketeer_g_back_left = new Sprite(16, 14, 4, SpriteSheet.units);
		public static Sprite rocketeer_g_back_left_idle_1 = new Sprite(16, 12, 11, SpriteSheet.units);
		public static Sprite rocketeer_g_back_left_frame_2 = new Sprite(16, 13, 11, SpriteSheet.units);
		public static Sprite rocketeer_g_back_left_frame_3 = new Sprite(16, 14, 11, SpriteSheet.units);
		public static Sprite rocketeer_g_front_left_firing = new Sprite(16, 3, 5, SpriteSheet.units);
		public static Sprite rocketeer_g_front_right_firing = new Sprite(16, 7, 5, SpriteSheet.units);
		public static Sprite rocketeer_g_back_left_firing = new Sprite(16, 9, 5, SpriteSheet.units);
		public static Sprite rocketeer_g_back_right_firing = new Sprite(16, 5, 5, SpriteSheet.units);
		
		public static Sprite rocketeer_r_front_left = new Sprite(16, 11, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_front_left_idle_1 = new Sprite(16, 12, 12, SpriteSheet.units);
		public static Sprite rocketeer_r_front_left_frame_2 = new Sprite(16, 13, 12, SpriteSheet.units);
		public static Sprite rocketeer_r_front_left_frame_3 = new Sprite(16, 14, 12, SpriteSheet.units);
		public static Sprite rocketeer_r_front_right = new Sprite(16, 13, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_front_right_idle_1 = new Sprite(16, 12, 14, SpriteSheet.units);
		public static Sprite rocketeer_r_front_right_frame_2 = new Sprite(16, 13, 14, SpriteSheet.units);
		public static Sprite rocketeer_r_front_right_frame_3 = new Sprite(16, 14, 14, SpriteSheet.units);
		public static Sprite rocketeer_r_back_right = new Sprite(16, 12, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_back_right_idle_1 = new Sprite(16, 12, 13, SpriteSheet.units);
		public static Sprite rocketeer_r_back_right_frame_2 = new Sprite(16, 13, 13, SpriteSheet.units);
		public static Sprite rocketeer_r_back_right_frame_3 = new Sprite(16, 14, 13, SpriteSheet.units);
		public static Sprite rocketeer_r_back_left = new Sprite(16, 14, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_back_left_idle_1 = new Sprite(16, 12, 15, SpriteSheet.units);
		public static Sprite rocketeer_r_back_left_frame_2 = new Sprite(16, 13, 15, SpriteSheet.units);
		public static Sprite rocketeer_r_back_left_frame_3 = new Sprite(16, 14, 15, SpriteSheet.units);
		public static Sprite rocketeer_r_front_left_firing = new Sprite(16, 4, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_front_right_firing = new Sprite(16, 8, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_back_left_firing = new Sprite(16, 10, 5, SpriteSheet.units);
		public static Sprite rocketeer_r_back_right_firing = new Sprite(16, 6, 5, SpriteSheet.units);
		
		
		
		public static Sprite zombie_front_left = new Sprite(16, 0, 0, SpriteSheet.zombies);
		public static Sprite zombie_front_left_idle_1 = new Sprite(16, 0, 1, SpriteSheet.zombies);
		public static Sprite zombie_front_left_frame_2 = new Sprite(16, 1, 1, SpriteSheet.zombies);
		public static Sprite zombie_front_left_frame_3 = new Sprite(16, 2, 1, SpriteSheet.zombies);
		public static Sprite zombie_front_right = new Sprite(16, 2, 0, SpriteSheet.zombies);
		public static Sprite zombie_front_right_idle_1 = new Sprite(16, 0, 3, SpriteSheet.zombies);
		public static Sprite zombie_front_right_frame_2 = new Sprite(16, 1, 3, SpriteSheet.zombies);
		public static Sprite zombie_front_right_frame_3 = new Sprite(16, 2, 3, SpriteSheet.zombies);
		public static Sprite zombie_back_left = new Sprite(16, 3, 0, SpriteSheet.zombies);
		public static Sprite zombie_back_left_idle_1 = new Sprite(16, 0, 4, SpriteSheet.zombies);
		public static Sprite zombie_back_left_frame_2 = new Sprite(16, 1, 4, SpriteSheet.zombies);
		public static Sprite zombie_back_left_frame_3 = new Sprite(16, 2, 4, SpriteSheet.zombies);
		public static Sprite zombie_back_right = new Sprite(16, 1, 0, SpriteSheet.zombies);
		public static Sprite zombie_back_right_idle_1 = new Sprite(16, 0, 2, SpriteSheet.zombies);
		public static Sprite zombie_back_right_frame_2 = new Sprite(16, 1, 2, SpriteSheet.zombies);
		public static Sprite zombie_back_right_frame_3 = new Sprite(16, 2, 2, SpriteSheet.zombies);
		
		public static Sprite tower_n_frame_1 = new Sprite(32, 0, 0, SpriteSheet.largesprites);
		public static Sprite tower_n_frame_2 = new Sprite(32, 1, 0, SpriteSheet.largesprites);
		public static Sprite tower_n_frame_3 = new Sprite(32, 2, 0, SpriteSheet.largesprites);
		public static Sprite tower_n_frame_4 = new Sprite(32, 3, 0, SpriteSheet.largesprites);
		public static Sprite tower_n_frame_5 = new Sprite(32, 4, 0, SpriteSheet.largesprites);
		public static Sprite tower_n_frame_6 = new Sprite(32, 5, 0, SpriteSheet.largesprites);
		
		public static Sprite tower_r_frame_1 = new Sprite(32, 0, 1, SpriteSheet.largesprites);
		public static Sprite tower_r_frame_2 = new Sprite(32, 1, 1, SpriteSheet.largesprites);
		public static Sprite tower_r_frame_3 = new Sprite(32, 2, 1, SpriteSheet.largesprites);
		public static Sprite tower_r_frame_4 = new Sprite(32, 3, 1, SpriteSheet.largesprites);
		public static Sprite tower_r_frame_5 = new Sprite(32, 4, 1, SpriteSheet.largesprites);
		public static Sprite tower_r_frame_6 = new Sprite(32, 5, 1, SpriteSheet.largesprites);
		
		public static Sprite tower_g_frame_1 = new Sprite(32, 0, 2, SpriteSheet.largesprites);
		public static Sprite tower_g_frame_2 = new Sprite(32, 1, 2, SpriteSheet.largesprites);
		public static Sprite tower_g_frame_3 = new Sprite(32, 2, 2, SpriteSheet.largesprites);
		public static Sprite tower_g_frame_4 = new Sprite(32, 3, 2, SpriteSheet.largesprites);
		public static Sprite tower_g_frame_5 = new Sprite(32, 4, 2, SpriteSheet.largesprites);
		public static Sprite tower_g_frame_6 = new Sprite(32, 5, 2, SpriteSheet.largesprites);
		
		public static Sprite castle_r = new Sprite(32, 0, 0, SpriteSheet.castles);
		public static Sprite castle_g = new Sprite(32, 1, 0, SpriteSheet.castles);

	//projectile sprites
	public static Sprite projectile_laser = new Sprite(16, 0 , 1, SpriteSheet.projectiles);
	public static Sprite projectile_tank = new Sprite(16, 2, 0, SpriteSheet.projectiles);
	public static Sprite projectile_mortar_1 = new Sprite(16, 3, 1, SpriteSheet.projectiles);
	public static Sprite projectile_mortar_2 = new Sprite(16, 3, 0, SpriteSheet.projectiles);
	public static Sprite projectile_sniper = new Sprite(16, 0, 0, SpriteSheet.projectiles);

	//building sprites
	
	public static Sprite building = new Sprite(16, 2, 0, SpriteSheet.tiles);
	
	//health sprites
	public static Sprite health_10 = new Sprite(16, 0, 11, SpriteSheet.interactives);
	public static Sprite health_9 = new Sprite(16, 0, 10, SpriteSheet.interactives);
	public static Sprite health_8 = new Sprite(16, 0, 9, SpriteSheet.interactives);
	public static Sprite health_7 = new Sprite(16, 0, 8, SpriteSheet.interactives);
	public static Sprite health_6 = new Sprite(16, 0, 7, SpriteSheet.interactives);
	public static Sprite health_5 = new Sprite(16, 0, 6, SpriteSheet.interactives);
	public static Sprite health_4 = new Sprite(16, 0, 5, SpriteSheet.interactives);
	public static Sprite health_3 = new Sprite(16, 0, 4, SpriteSheet.interactives);
	public static Sprite health_2 = new Sprite(16, 0, 3, SpriteSheet.interactives);
	public static Sprite health_1 = new Sprite(16, 0, 2, SpriteSheet.interactives);
	
	//Interactive sprites
	public static Sprite player = new Sprite(32, 0, 0, SpriteSheet.interactives); //creates a 32x32 size sprite. Since the size is twice that of a normal sprite, we count in the tiles in pairs inside the spritesheet. Thus, 
	public static Sprite playerSelecting = new Sprite(32, 1, 0, SpriteSheet.interactives); //creates a 32x32 size sprite. Since the size is twice that of a normal sprite, we count in the tiles in pairs inside the spritesheet. Thus, 
	public static Sprite selectCursor = new Sprite(16, 5, 0, SpriteSheet.interactives); //if unit is selected..
	public static Sprite commandCursor = new Sprite(16, 4, 0, SpriteSheet.interactives); //if unit has a stored command...
	public static Sprite wayPoint = new Sprite(16, 6, 0, SpriteSheet.interactives);
	public static Sprite aura = new Sprite(16, 7, 0, SpriteSheet.interactives);
	
	public Sprite (int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int [SIZE * SIZE];
		this.x = x * size; //x and y are the coordinates of the sprite in the sprite sheet IN TERMS OF TILES, NOT PIXELS
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite (int width, int height, int colour) { //creates a non-square sprite
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		SIZE = -1; //cuz we wont use it in this case.
		setColour(colour);
	}
	
	
	
// this is an alternate constructor if you want sprites that are just filled with a single color instead of something drawn in a spritesheet.
	public Sprite(int size, int colour) { // note colour instead of color
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}
	
	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	
	private void load() { //loads the sprite from the spritesheet class into computer
		for (int y = 0; y < SIZE; y++) {
			for (int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y)*sheet.SIZE]; //the x versus this.x distinction is difficult.
			}
		}
	}
}
