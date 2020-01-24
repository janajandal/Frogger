//FILE:Car.java
//BY:Jana Jandal ALrifai and Catherine Sun
//SUMMARY: An Obstacle on the road that contains move, collision and x,y coordinates
import javax.swing.*;
import java.awt.*;


public class Car {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, lane, limit, replace, speed, w, h;
	private Image pic;
    private  Rectangle rect;
    //All objects are modeled on the same basis
    public Car(int lvl, int lane, int x) {
	//gets image from file and gets the width and height
    	String file = String.format("road/%d.png", lane);
    	pic = new ImageIcon(file).getImage();	
    	w = pic.getWidth(null);
    	h = pic.getHeight(null);
 	//determines direction based on alternating lanes   	
    	if(lane%2 == 0) {
    	    dir = LEFT;
    	    replace = 542;
            limit = -w; //sets limit to allow the car to loop back on screen after going off screen
    	} else {
    	    dir = RIGHT;
    	    replace = -w;
            limit = 542;
    	}
    	speed = lvl; //each level the cars move faster
    	rect = new Rectangle(x, 482 - lane*38, w, h); //used to check for collision
    }
    
    public int getW() {
    	return w;
    }
    
    public void move() {
    	rect.setLocation(rect.x + speed*dir, rect.y); //moves the rectangle
        if((rect.x > limit && dir == RIGHT) || (rect.x < limit && dir == LEFT)){ //if car goes off screen it will appear on the other side of the screen 
            rect.x = replace;
        }
    	
    }
    
    public boolean checkCollision(Frog player){
    	return rect.intersects(player.getRect()); //checks collision with player Rect
    }

    public void draw(Graphics g){
        g.drawImage(pic, rect.x, rect.y, w, h, null); //draws picture
    }
}
