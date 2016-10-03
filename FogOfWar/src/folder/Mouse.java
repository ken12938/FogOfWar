package folder;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener{

	//we set these values as -1, -1, -1 because these are impossible coordinates for the value. Therefore if they equal -1, something wrong is happening with the code. This is static cuz the methods that refer to them are static. 
	private static int mouseX = -1; //x and y coordinates of mouse
	private static int mouseY = -1;
	private static int mouseB = -1; //detects if button is clicked. The value becomes 1 if it is clicked.
	private static boolean mouseClicked = false;
	private static boolean mouseOver = false;
	
	public static int getX() {
		return mouseX;
	}
	
	public static int getY() {
		return mouseY;
	}
	
	public static int getButton() {
		return mouseB;
	}
	
	public static boolean getClicked(){
		return mouseClicked;
	}
	
	public static void resetClick(){
		mouseClicked = false;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX(); //allows us to click and drag.
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClicked = true; 
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseB = e.getButton();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseB = -1; //this makes sure that when you release the button, the computer knows its not pressed anymore.
	}

}
