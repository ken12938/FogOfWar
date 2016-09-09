package folder;

import java.awt.Color;
//rafael's hexconverter method. It darkens/lightens a pixel
public class HexConverter {
	public static int getColorAsInt(Color color) {
		return color.hashCode();
	}
	public static Color getColorFromInt(int hexColor) {
		return new Color(hexColor);
	}
	public static int makeLighter(int hexColor, int amountInRgbToLighten){
		Color startColor = getColorFromInt(hexColor);
		Color colorToReturn = new Color(
				Math.min(startColor.getRed() + amountInRgbToLighten, 255), 
				Math.min(startColor.getGreen() + amountInRgbToLighten, 255),
				Math.min(startColor.getBlue() + amountInRgbToLighten, 255),
				startColor.getAlpha());
		return getColorAsInt(colorToReturn);
	}
	public static int makeDarker(int hexColor, int amountInRgbToDarken){
		Color startColor = getColorFromInt(hexColor);
		Color colorToReturn = new Color(
				Math.max(startColor.getRed() - amountInRgbToDarken, 0), 
				Math.max(startColor.getGreen() - amountInRgbToDarken, 0),
				Math.max(startColor.getBlue() - amountInRgbToDarken, 0),
				startColor.getAlpha());
		return getColorAsInt(colorToReturn);
	}
	
	/*public static int decreaseOpacity(int hexColor, int opacityDecrease) {
		Color startColor = getColorFromInt(hexColor);
		Color colorToReturn = new Color(
				startColor.getRed(), 
				startColor.getGreen(), 
				startColor.getBlue(),
				Math.min(startColor.getAlpha() + opacityDecrease, 255));
		return getColorAsInt(colorToReturn);
				
	}*/
}

