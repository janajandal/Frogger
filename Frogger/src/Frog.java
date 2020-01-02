import javax.swing.*;
import java.awt.*;

public class Frog {
    private int x,y;
    private int lives;
    private int dir;
    private Image[][]pics;
    private int points;
    private int frame;
    private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
    public Frog(int x,int y) {
        this.x=x;
        this.y=y;
        frame = 0;
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
    public void horizantalMove(int dist) {
        if(dist < 0) {
            dir = LEFT;
        } else {
            dir = RIGHT;
        }
        x += dist;
    }
	public void verticalMove(int dist) {
		if(dist < 0) {
			dir = UP;
		} else {
			dir = DOWN;
    }
		y += dist;
    }

    public void stayStill () {
        frame = 0;
    }
    public void frogJump(){
        frame = frame + 1 < pics.length ? frame + 1 : 4;
        delay(100);
    }
    public void jump (Graphics g){
        Image sprite = pics[dir][frame];
        g.drawImage(sprite, x + 30, y, -30, 30, null);
    }


    public void delay(int len) {
        try {
            Thread.sleep(len);
        } catch (InterruptedException ex) {
            System.out.println("I hate when my sleep is interrupted");
        }
    }
}

