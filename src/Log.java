import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Log {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, limit, replace, speed, w, h;
	private Image pic;
    private  Rectangle rect;

    public Log(int lane) {
    	String file = String.format("river/%d.png", lane);
    	pic = new ImageIcon(file).getImage();
    	w = pic.getWidth(null);
    	h = pic.getHeight(null);
    	
    	if(lane % 2==0) {
    		dir = RIGHT;
    		replace = 0;
            limit = 488;
    	} else {
    		dir = LEFT;
    		replace = 488;
            limit = 0;
    	}
    	speed = 1;
    	rect = new Rectangle(replace, 254 - lane*38, w, h);
    }
    
    public int getX() {
    	return rect.x;
    }
    
    public int getY() {
    	return rect.y;
    }

    public int getSpeed() {
        return speed;
    }
    public int getDir(){
        return dir;
    }

    public void move() {
    	rect.setLocation(rect.x + speed*dir, rect.y);
        if((rect.x > limit && dir == RIGHT) || (rect.x < limit && dir == LEFT)){
            rect.x = replace;
        }
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean checkCollision(Frog player){
    	return rect.intersects(player.getRect());
    }

    public void draw(Graphics g){
        g.drawImage(pic, rect.x, rect.y, w, h, null);
    }
}
