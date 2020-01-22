import javax.swing.*;
import java.awt.*;

public class Frog {
    private int x,y,lives,dir,points,home, col;
	private Image[][][] frogPics;
	private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, GREEN = 0, PURPLE = 1;
	private double frame;

	
    public Frog(int x,int y) {
        this.x=x;
        this.y=y;
        frame = 4;
        lives=3; // TODO: 2020-01-21 picture of lives disappear should be easy 
        //points=0;
        home=0;
		dir = UP;
		col = GREEN;

		loadPics();
    }
    public boolean isDead(){ // TODO: 2020-01-21 for drowning/hit graphics 
        return (lives<=0);

    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void loseLive(){
        lives--;
        x=310;
        y=654;
        dir=UP;
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
    public void addPoints(int points){
        this.points+=points;
    }
    public void incHome(){ // TODO: 2020-01-21 frog stays home graphic 
        home++;
        points+=50;
        System.out.println("home sweet home");
        if (home==5){
            points+=1000;
            System.out.println("You won"); // TODO: 2020-01-17 win game level up
        }
    }

    public int getPoints() {
        return points;
    }

    public void keepMove(Log log){
        Math.min(log.getLength(),x+10*dir);
    }

/*
    public void hasLady(Lady frog){
        if(frog.isLady() && (frog.getX()==x) && (frog.getY()==y)){
            points+=200;
        }
    }


 */
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

