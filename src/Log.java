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
        rect=new Rectangle(x,y,30,30);
        if((lane % 2) == 0) {
            dir=RIGHT;

        } else{
            dir=LEFT ;

        }
        y=30*lane;
        imageNum=rand.nextInt(5);
        Image img = new ImageIcon("Obstacle"+imageNum+".png").getImage();
        pic = img.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        speed=20*level;
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
