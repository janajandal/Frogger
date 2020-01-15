import javax.swing.*;
import java.awt.*;

public class Frog {
    private int x,y,lives,dir,points,home, col;
	private Image[][][] frogPics;
	private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, GREEN = 0, PURPLE = 1;
	private double frame;
    private boolean lady; 
	
    public Frog(int x,int y,boolean lady) {
        this.x=x;
        this.y=y;
        frame = 4;
        lives=3;
		dir = UP;
		col = GREEN;
		this.lady=lady;
		loadPics();
    }
    public boolean isDead(){
        return lives<=0;
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }


    public void loseLive(){
        lives--;
        x=210;
        y=420;
        isDead();
    }
    public void horizontalMove(int dist) {
        if(dist < 0) {
            dir = LEFT;
        } else {
            dir = RIGHT;
        }
        x += dist * 48;

    }
	public void verticalMove(int dist) {
		if(dist < 0) {
			dir = UP;
		} else {
			dir = DOWN;
    }

		y += dist * 48;
    }
    public void setPoints(int points){
        this.points=points;
    }
    public void incHome(){
        home++;
        points+=50;
        if (home==5){
            points+=1000;
            System.out.println("You won");
        }
    }

    public boolean isLady() {
        return lady;
    }

    public boolean hasLady(Frog frog){
        return (frog.isLady() && (frog.getX()==x) && (frog.getY()==y));
    }

    public void stayStill () {
        frame = 0;
    }
    public void frogJump(){
        frame = frame + 0.125< frogPics.length ? frame + 0.125: 4;

    }
    public void jump (Graphics g){
        Image sprite = frogPics[col][dir][(int) Math.round(frame)];
        g.drawImage(sprite, x, y, 48, 48, null);
    }
	
	public void loadPics() {
		frogPics = new Image[2][4][5];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 5; j ++) {
				String file = String.format("green/frog%d/frog%d.png", i, j);
				Image img = new ImageIcon(file).getImage();
				frogPics[GREEN][i][j] = img.getScaledInstance(48,48,Image.SCALE_SMOOTH);
			}
		}
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 5; j ++) {
				String file = String.format("purple/frog%d/frog%d.png", i, j);
				Image img = new ImageIcon(file).getImage();
				frogPics[PURPLE][i][j] = img.getScaledInstance(48,48,Image.SCALE_SMOOTH);
			}
		}
    }

}

