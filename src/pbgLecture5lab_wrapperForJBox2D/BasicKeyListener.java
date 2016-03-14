package pbgLecture5lab_wrapperForJBox2D;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BasicKeyListener extends KeyAdapter {
	/* Author: Michael Fairbank
	 * Creation Date: 2016-01-28
	 * Significant changes applied:
	 */
	private static boolean rightKeyPressed, leftKeyPressed, spaceKeyPressed; 

	public static boolean isRightKeyPressed() {
		return rightKeyPressed;
	}

	public static boolean isLeftKeyPressed() {
		return leftKeyPressed;
	}
        
        public static boolean isSpaceKeyPressed() {
            return spaceKeyPressed;
        }
        public static void falseSpaceKey() {
            spaceKeyPressed=false;
        }

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
                    leftKeyPressed=true;
                    break;
		case KeyEvent.VK_RIGHT:
                    rightKeyPressed=true;
                    break;
                case KeyEvent.VK_SPACE:
                    spaceKeyPressed=true;
                    break;
		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_LEFT:
                    leftKeyPressed=false;
                    break;
		case KeyEvent.VK_RIGHT:
                    rightKeyPressed=false;
                    break;
                case KeyEvent.VK_SPACE:
                    spaceKeyPressed=false;
                    break;
		}
	}
}
