import javax.swing.*;
import java.awt.*;


public class Frog {
	private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, GREEN = 0, PURPLE = 1;
	private int x, y, dir, col;
	private Point pos;
	private boolean rebirth;
	private double frame;
	
	private Image[][][] frogPics;
	private Image[] deadPics;
	
	
	public Frog(int x, int y) {
		this.x = x;
		this.y = y;
		dir = UP;
		col = GREEN;
		pos = new Point(0, 0);
		rebirth = false;
		frame = 4;
		
		loadPics();
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, 38, 38);
	}
	
	public boolean newLife() {
		return rebirth;
	}
	
	public boolean isDead() {
		return pos.x != 0 && pos.y != 0;
	}
	
	public void horizontalMove(int dist) {
		dir = dist < 0 ? LEFT : RIGHT;
		x += dist*38;
	}
	
	public void verticalMove(int dist) {
		dir = dist < 0 ? UP : DOWN;
		y += dist*38;
	}
	
	public void drift(int dist) {
		x += dist;
	}
	
    public void stayStill() {
        frame = 0;
    }
    
    public void frogJump(){
        frame = frame + 0.125 <= 4 ? frame + 0.125 : 4;
    }
    
    public void jump (Graphics g) {
        Image sprite = frogPics[col][dir][(int)Math.round(frame)];
        g.drawImage(sprite, x, y, 38, 38, null);
    }
    
    public void frogDeath() {
    	pos = new Point(x, y);
		x = 1000;
		y = 1000;
		frame = 0;
	}
	
	public void die(Graphics g) {
		g.drawImage(deadPics[(int)Math.round(frame)], pos.x, pos.y, 38, 38, null);
		rebirth = frame == 4 ? true : false;
	}
	public int getDir(){
		return dir;
	}

    public void loadPics() {
		frogPics = new Image[2][4][5];
		deadPics = new Image[5];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 5; j ++) {
				String file = String.format("frog/green/frog%d/frog%d.png", i, j);
				Image img = new ImageIcon(file).getImage();
				frogPics[GREEN][i][j] = img.getScaledInstance(38 ,38 ,Image.SCALE_SMOOTH);
			}
		}
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 5; j ++) {
				String file = String.format("frog/purple/frog%d/frog%d.png", i, j);
				Image img = new ImageIcon(file).getImage();
				frogPics[PURPLE][i][j] = img.getScaledInstance(38 ,38 ,Image.SCALE_SMOOTH);
			}
		}
		for(int i = 0; i < 5; i++) {
			String file = String.format("frog/dead/dead%d.png", i);
			Image img = new ImageIcon(file).getImage();
			deadPics[i] = img.getScaledInstance(38, 38, Image.SCALE_SMOOTH);
		}
    }
}