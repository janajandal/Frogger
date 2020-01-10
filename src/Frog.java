import javax.swing.*;
import java.awt.*;

public class Frog {
    private int x,y,lives,dir,points,home;
    private Image[][]pics;
    private boolean lady;
    private double frame;
    private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
    public Frog(int x,int y) {
        this.x=x;
        this.y=y;
        frame = 4;
        lives=3;
        pics = new Image[4][5];
        for(int i = 0; i<pics.length; i++){
            for(int j = 0; j<5; j ++) {
                String file = String.format("Frog/frog%d/frog%d.png", i, j);
                Image img = new ImageIcon(file).getImage();
                pics[i][j] = img.getScaledInstance(30,30,Image.SCALE_SMOOTH);
            }
        }

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
    /*
    public void takeLady(Lady lady){
        if(lady.getX()==x && lady.getY()==y)
    }

     */

    public void loseLive(){
        lives--;
        x=210;
        y=420;
    }
    public void horizontalMove(int dist) {
        if(dist < 0) {
            dir = LEFT;
        } else {
            dir = RIGHT;
        }
        x += dist * 30;

    }
	public void verticalMove(int dist) {
		if(dist < 0) {
			dir = UP;
		} else {
			dir = DOWN;
    }

		y += dist * 30;
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

    public void stayStill () {
        frame = 0;
    }
    public void frogJump(){
        frame = frame + 0.25< pics.length ? frame + 0.25: 4;

    }
    public void jump (Graphics g){
        Image sprite = pics[dir][(int) Math.round(frame)];
        g.drawImage(sprite, x, y, 30, 30, null);
    }

}

