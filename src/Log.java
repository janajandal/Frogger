import java.awt.image.ImageObserver;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Log {
    private int x,y,dir,speed,imageNum,level;
    private Image pic;
    private int frame;
    private  Rectangle rect;
    private static final int LEFT = -1, RIGHT = 1;
    Random random=new Random();
    public Log(int lane,int level){
        Random rand=new Random();
        x=0;
        if((lane % 2) == 0) {
            dir=RIGHT;

        } else{
            dir=LEFT ;

        }
        y=30*lane;
       // imageNum=rand.nextInt(3);
        imageNum=1;
        String file = String.format("Obstacle/%d/log%d.png", dir, imageNum);
        pic = new ImageIcon(file).getImage();
        rect=new Rectangle(x,y,pic.getWidth(null),pic.getHeight(null));
        speed=20*level;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public int getLength(){
        return pic.getHeight(null);
    }

    public void move(){
        rect.move(x+speed,y);
    }
    public void checkFall(Frog frog){
        if(!(rect.contains(frog.getX(),frog.getY()))){
            frog.loseLive();
            if(frog.isDead()){

            }
        }
    }
    public void draw(Graphics g){
        g.drawImage(pic, x + 30, y, -30, 30, null);
    }

}
