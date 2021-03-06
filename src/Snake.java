import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Snake {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, lane, limit, replace, speed, w, h;
	private double frame;
	private Image[] pics;
    private  Rectangle rect;

    public Snake() {
    	pics = new Image[5];
    	for(int i = 0; i < pics.length; i++) {
	    	String file = String.format("road/snake%d.png", i);
			pics[i] = new ImageIcon(file).getImage();
    	}
    	w = pics[0].getWidth(null);
    	h = pics[0].getHeight(null);
    	
    	dir = RIGHT;
    	replace = -w;
    	limit = 542;
    	frame = 0;
    	
    	speed = 3;
    	rect = new Rectangle(-w, 482 - 5*38, w, h);
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
