import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Lady {
    private static final int LEFT = -1, RIGHT = 1;
	private int dir, limit, replace, lane, speed, w, h, jump;
	private double frame;
	private Image[][] ladyPics;
    private Rectangle rect;
    private Log log;
    
    public Lady(int lvl, Log log){
    	w = 38;
    	h = 38;
    	
		ladyPics = new Image[2][5];
		for(int i = 0; i < 2; i++){
			for(int j = 0; j < 5; j++) {
				String file = String.format("lady/lady%d/frog%d.png", i, j);
				Image img = new ImageIcon(file).getImage();
				ladyPics[i][j] = img.getScaledInstance(w ,h ,Image.SCALE_SMOOTH);
			}
		}
		frame = 4;
        this.log = log;
        speed= lvl;
        dir = RIGHT;
        jump = 0;
		
		if(log.getDir() == LEFT) {
    		replace = 542;
            limit = -w;
    	} else {
    		replace = -w;
    		limit = 542;
    	}
        
    	rect = new Rectangle(log.getX(), log.getY(), w, h);
    }
    
    public boolean checkCollision(Frog player){
    	rect = new Rectangle(log.getX(), log.getY(), w, h);
        return rect.intersects(player.getRect());
    }
    
    public boolean notJumping() {
    	return frame == 4;
    }
	
	public void move() {
        frame = frame + 0.125 <= 4 ? frame + 0.125 : 4;
		rect.setLocation(log.getX() + jump*w, rect.y);
        if((rect.x > limit && dir == RIGHT) || (rect.x < limit && dir == LEFT)){
            rect.x = replace;
        }
	}
	
	public void frogJump() {
		frame = 0;
		dir = jump == 0 ? RIGHT : LEFT;
		dir = jump == 1 ? LEFT : RIGHT;
		jump += dir;
	}

    public void draw(Graphics g) {
    	int i = dir == LEFT ? 0 : 1;
        g.drawImage(ladyPics[i][(int)Math.round(frame)], log.getX() + jump*w, rect.y, w, h, null);
    }
    
    public static boolean rand(){
    	Random r = new Random();
    	return r.nextBoolean();
    }
}