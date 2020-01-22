import javax.swing.*;
import java.awt.*;


public class Car {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, lane, limit, replace, speed, w, h;
	private Image pic;
    private  Rectangle rect;

    public Car(int lane) {
    	String file = String.format("road/%d.png", lane);
    	pic = new ImageIcon(file).getImage();
    	w = pic.getWidth(null);
    	h = pic.getHeight(null);
    	
    	if(lane%2 == 0) {
    		dir = LEFT;
    		replace = 488;
            limit = 0;
    	} else {
    		dir = RIGHT;
    		replace = 0;
            limit = 488;
    	}
    	speed = 1;
    	rect = new Rectangle(replace, 482 - lane*38, w, h);
    }
    
    public void move() {
    	rect.setLocation(rect.x + speed*dir, rect.y);
        if((rect.x > limit && dir == RIGHT) || (rect.x < limit && dir == LEFT)){
            rect.x = replace;
        }
    	
    }
    
    public boolean checkCollision(Frog player){
    	return rect.intersects(player.getRect());
    }

    public void draw(Graphics g){
        g.drawImage(pic, rect.x, rect.y, w, h, null);
    }
}