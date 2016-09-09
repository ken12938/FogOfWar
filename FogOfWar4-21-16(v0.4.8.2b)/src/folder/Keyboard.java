package folder;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[120]; //stores, two states for every key on the keyboard. true or false 
	public boolean up, down, left, right, enter, mKey, sKey;
	public int lastKey;
	
	public void update () { //constantly check to see if a key is being pressed/released
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		
		mKey = keys[KeyEvent.VK_M];
		sKey = keys[KeyEvent.VK_S];
		enter = keys[KeyEvent.VK_ENTER];
		
		for (int i = 0; i < keys.length; i++){
			if (keys[i]){
			System.out.println("KEY: " + i);
			lastKey = i;
			}
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) { //This is an automatically generated method for keyBoard. e is coool as opposed to arg0. its arbitrary
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) { //This is an automatically generated method for keyBoard. e is coool as opposed to arg0. its arbitrary
		keys[e.getKeyCode()] = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) { //This is an automatically generated method for keyBoard. e is coool as opposed to arg0. its arbitrary
		// TODO Auto-generated method stub
	}

}
