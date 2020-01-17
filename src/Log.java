import java.awt.image.ImageObserver;
import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Log {
    private int dir,speed,imageNum,place,restart;
    private Image pic;
    private int frame;
    private  Rectangle rect;
    private static final int LEFT = -1, RIGHT = 1;
    Random random=new Random();
    public Log(int lane,int place,int level){
        Random rand=new Random();
        if(lane % 2 == 0) {
            dir=LEFT;
            this.place=343*place;
            restart=0;
        }
        else {
            dir=RIGHT;
            this.place=0;
            restart=654;
        }

        // imageNum=rand.nextInt(3);
        imageNum=1;
        String file = String.format("Obstacle/%d/log%d.png", dir, imageNum);
        pic = new ImageIcon(file).getImage();
        rect=new Rectangle(this.place,113*lane,pic.getWidth(null),pic.getHeight(null));
        speed=1*level;
    }

    public int getY() {
        return rect.y;
    }

    public int getX() {
        return rect.x;
    }

    public int getLength(){
        return pic.getHeight(null);
    }

    public void move(){
        rect.move(rect.x+speed*dir,rect.y);
        if(rect.x<543){
            rect.x=restart;
        }
        // TODO: 2020-01-16 go back after moving off screen

    }
    public void checkFall(Frog frog){
        if(!(rect.contains(frog.getX(),frog.getY()))){
            frog.loseLive();
            if(frog.isDead()){

            }
        }
    }
    public void draw(Graphics g){
        g.drawImage(pic, rect.x+30, rect.y, -48, 48, null);
    }

}
