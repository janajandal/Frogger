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
    public Log(int place,int lane,int level){
        Random rand=new Random();
        if(lane % 2 == 0) {
            dir=LEFT;
            this.place=541+(place*2);
            restart=0;
        }
        else {
            dir=RIGHT;
            this.place=30;
            restart=674;
        }
        imageNum=1;

        String file = String.format("Obstacle/%d/log%d.png", dir, imageNum);
        pic = new ImageIcon(file).getImage();
        rect=new Rectangle(this.place,130+(lane*48),pic.getWidth(null),pic.getHeight(null));
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
        rect.setLocation(rect.x+speed*dir,rect.y);
        if((rect.x>restart && dir==RIGHT) || (rect.x<restart && dir==LEFT)){
            rect.x=place;
        }

    }
    public void checkFall(Frog frog){
        boolean near = false;
       if (near) {
            if (rect.contains(frog.getX(), frog.getY())) {
                frog.setX(rect.x);
                frog.setY(rect.y);
            } else {
                frog.loseLive();
                frog.isDead();
                    // TODO: 2020-01-17 call function that draws gameOver from Frog
                }
       }

    }

    public void draw(Graphics g){
        g.drawImage(pic, rect.x, rect.y, 48, 48, null);
        g.drawRect(rect.x,rect.y,rect.width,rect.height);
    }
}
