import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Lady {
    private int x,y,dir,frame,limitx,limity;
    private Log log;
    private Image[][][] frogPics;
    private Rectangle rect;
    private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, GREEN = 0, PURPLE = 1;
    public Lady(Log log){
        this.log=log;
        limitx=log.getRect().width;
        limity=log.getRect().height;
        y=log.getRect().y;
        Random rand=new Random();
        x=rand.nextInt(limitx);
        rect= new Rectangle(x,y,38,38);
        frame=4;
        load();
    }
    public void followLog(){
        x+=log.getDir()*log.getSpeed();
    }
    public void followFrog(Frog frog){
        x=frog.getX();
        y=frog.getY();
    }
    public boolean checkCollision(Frog frog){
        return rect.intersects(frog.getRect());
    }
    public void draw(Graphics g) {
        Image sprite = frogPics[PURPLE][dir][(int)Math.round(frame)];
        g.drawImage(sprite, x, y, 38, 38, null);
    }
    public void load(){
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j ++) {
                String file = String.format("purple/frog%d/frog%d.png", i, j);
                Image img = new ImageIcon(file).getImage();
                frogPics[PURPLE][i][j] = img.getScaledInstance(38 ,38 ,Image.SCALE_SMOOTH);
            }
        }
    }

}
