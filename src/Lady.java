import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Lady {
    private int x,y,dir,limitx,limity;
    private double frame;
    private Log log;
    private Image[][] frogPics;
    private Rectangle rect;
    private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, GREEN = 0, PURPLE = 1;
    public Lady(Log log){
        this.log=log;
        limitx=log.getRect().width;
        limity=log.getRect().height;
        dir=log.getDir();
        y=log.getRect().y;
        Random rand=new Random();
        if(limity<0){
            limity*=-1;
        }
        if(limitx<0){
            limitx*=-1;
        }
        x=rand.nextInt(limitx);
        rect= new Rectangle(x,y,38,38);
        frame=4;
        load();
    }
    public void followLog(){
        x+=dir*log.getSpeed();
    }
    public void followFrog(Frog frog){
        x=frog.getX();
        y=frog.getY();
        dir=frog.getDir();
    }
    public boolean checkCollision(Frog frog){
        return rect.intersects(frog.getRect());
    }
    public void draw(Graphics g) {
        Image sprite = frogPics[dir][(int)Math.round(frame)];
        g.drawImage(sprite, x, y, 38, 38, null);
    }
    public void load(){
        frogPics= new Image[4][5];
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j ++) {
                String file = String.format("purple/frog%d/frog%d.png", i, j);
                Image img = new ImageIcon(file).getImage();
                frogPics[i][j] = img.getScaledInstance(38 ,38 ,Image.SCALE_SMOOTH);
            }
        }
    }

}
