/*
FILE NAME:Log.Java
BY:Jana Jandal Alrifai, Catherine Sun
SUMMARY:Log objects in the water includes frame, move and collision
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Log {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, lane, limit, replace, speed, w, h;
	private Image pic;
    private  Rectangle rect;

    public Log(int lvl, int lane, int x) {
    	String file = String.format("river/%d.png", lane);
    	pic = new ImageIcon(file).getImage();
    	w = pic.getWidth(null);
    	h = pic.getHeight(null);
    	
    	if(lane%2 == 0) { //determines direction and limit based on lane
    		dir = LEFT;
    		replace = 542;
            limit = -w;
    	} else {
    		dir = RIGHT;
    		replace = -w;
    		limit = 542;
    	}
    	speed = lvl; //changes the speed in each level
    	rect = new Rectangle(x, 482 - lane*38, w, h); //makes a rectangle for the log
    }

    
    public void move() { //keeps the logs moving on and off screen in a loop
    	rect.setLocation(rect.x + speed*dir, rect.y);
        if((rect.x > limit && dir == RIGHT) || (rect.x < limit && dir == LEFT)){
            rect.x = replace;
        }
    }
    
    public boolean checkCollision(Frog player){
    	return rect.intersects(player.getRect());
    } //checks if intersects with player

    public void draw(Graphics g){
        g.drawImage(pic, rect.x, rect.y, w, h, null);
    } //draws the log in diff pos

	//getters and setters used in Lady
	public int getSpeed() {
    	return speed;
	}

	public Rectangle getRect() {
    	return rect;
	}

	public int getDir() {
    	return dir;
	}
}
