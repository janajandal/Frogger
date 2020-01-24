import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Fly {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, w, h;
	private Image pic;
    private  Rectangle rect;

    public Fly(Rectangle home) {
    	dir = rand() ? LEFT : RIGHT;
    	
    	String file = String.format("river/fly%d.png", dir);
    	pic = new ImageIcon(file).getImage();
    	w = pic.getWidth(null);
    	h = pic.getHeight(null);
    	
    	rect = new Rectangle(home.x + 6, home.y + 2, w, h);
    }
    
    public boolean checkCollision(Frog player){
    	return rect.intersects(player.getRect());
    }

    public void draw(Graphics g){
        g.drawImage(pic, rect.x, rect.y, w, h, null);
    }
    
    public static boolean rand(){
    	Random r = new Random();
    	return r.nextBoolean();
    }
}