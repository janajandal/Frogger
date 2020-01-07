import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Car {
    private static final int LEFT = -1, RIGHT = 1;
    private static int x,y,dir,speed,imageNum;
    private static Image pic;
    private static Rectangle rect;
    public static Car(int lane){
        Random rand=new Random();
        rect=new Rectangle(x,y,30,30);
        if((lane % 2) == 0) {
            dir=RIGHT; //y=wherever we decide these lanes are;


        } else{
            dir=LEFT ; //y=wherever we decide these lanes are;

        }
        y=30*lane;
        imageNum=rand.nextInt(5);
        Image img = new ImageIcon("Obstacle"+imageNum+".png").getImage();
        pic = img.getScaledInstance(30,30,Image.SCALE_SMOOTH);
        speed=20;
    }
    public static void move(){
        rect.move(x+speed,y);
    }
    public static void checkHit(Frog frog){
        if(rect.contains(frog.getX(),frog.getY()){
            frog.loseLive();
            frog.setY(210);
        }
    }
}
