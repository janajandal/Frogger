import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Turtle {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, lane, limit, replace, speed, w, h;
	private double frame;
	private Image[] pics;
    private  Rectangle rect;

    public Turtle(int lvl, int lane, int x) {
    	w = 28;
    	h = 38;
    	
    	if(lane%2 == 0) {
    		dir = LEFT;
    		replace = 542;
            limit = -w;
    	} else {
    		dir = RIGHT;
    		replace = -w;
    		limit = 542;
    	}
    	
    	frame = 0;
    	
    	pics = new Image[5];
    	for(int i = 0; i < pics.length; i++) {
	    	String file = String.format("river/swim%d/turtle%d.png", dir, i);
			Image img = new ImageIcon(file).getImage();
			pics[i] = img.getScaledInstance(w, h,Image.SCALE_SMOOTH);
    	}
    	
    	speed = lvl;
    	rect = new Rectangle(x, 482 - lane*h - 5, w, h);
    }
    
    public int getW() {
    	return w;
    }
    
    public void move() {
        frame = frame + 0.025 <= 4 ? frame + 0.025 : 0;
    	rect.setLocation(rect.x + speed*dir, rect.y);
        if((rect.x > limit && dir == RIGHT) || (rect.x < limit && dir == LEFT)){
            rect.x = replace;
        }
    }
    
    public boolean checkCollision(Frog player){
    	return rect.intersects(player.getRect());
    }

    public void draw(Graphics g){
        g.drawImage(pics[(int)Math.round(frame)], rect.x, rect.y, w, h, null);
    }
}
