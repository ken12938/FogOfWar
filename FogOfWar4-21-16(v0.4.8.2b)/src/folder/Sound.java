package folder;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	
	public static final AudioClip death = Applet.newAudioClip(Sound.class.getResource("/audio/death.wav")); //background music
	public static final AudioClip map_theme = Applet.newAudioClip(Sound.class.getResource("/audio/map_theme.wav")); //background music
	public static final AudioClip menu_theme = Applet.newAudioClip(Sound.class.getResource("/audio/menu_theme.wav")); //background music
	public static final AudioClip selection_theme = Applet.newAudioClip(Sound.class.getResource("/audio/selection_theme.wav")); //background music
	public static final AudioClip turn_theme = Applet.newAudioClip(Sound.class.getResource("/audio/turn_theme.wav")); //background music

}
