
package pong;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 * @author Beau Marwaha
 */
public class paddleRight {
    
    private int dy;
    private int x;
    private int y;
    private final ImageIcon II = new ImageIcon(this.getClass().getResource("images/paddle.png"));
    private final Image IMAGE = II.getImage();
    //use of booleans with movement prevents lag when instantly switching directions
    private boolean up = false;
    private boolean down = false;
    
    /**
     * Standard paddle object with default values
     */
    public paddleRight() {
        x = 1130;
        y = 300;
    }
    
    /**
     * Changes the y value by dy allowing the paddle to move onscreen
     */
    public void move() {
        if(up){
            dy = -4;
        }
        if(down){
            dy = 4;
        }
        y += dy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    public void setdy(int dy) {
        this.dy = dy;
    }
    
    public Image getImage() {
        return IMAGE;
    }
    
    public int getWidth() {
        return IMAGE.getWidth(null);
    }
    
    public int getHeight() {
        return IMAGE.getHeight(null);
    }
    
    /**
     * Handles certain key presses made by the user
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        //Moves the paddle up
        if(key == KeyEvent.VK_UP){
            up = true;
        }

        //Moves the paddle down
        if(key == KeyEvent.VK_DOWN){
            down = true;
        }
    }
 
    /**
     * Handles certain key releases made by the user
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        //Stops paddle movement
        if (key == KeyEvent.VK_UP) {
            up = false;
            dy = 0;
        }

        //Stops paddle movement
        if (key == KeyEvent.VK_DOWN) {
            down = false;
            dy = 0;
        }
    }
}
