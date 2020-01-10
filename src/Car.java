import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Car {
    private static final int LEFT = -1, RIGHT = 1;
    private  int x,y,dir,speed,imageNum;
    private  Image pic;
    private  Rectangle rect;
    public  Car(int lane){
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
        speed=20;
    }
    public void move(){
        rect.move(x+speed,y);
    }
    public void checkHit(Frog frog){
        if(rect.contains(frog.getX(),frog.getY())){
            frog.loseLive();
            if(frog.isDead()){
                System.out.println("You lost");
            }
        }
    }
    public void draw(Graphics g){
        g.drawImage(pic, x + 30, y, -30, 30, null);
    }
}
