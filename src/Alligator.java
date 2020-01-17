import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Alligator {
    private static final int LEFT = -1, RIGHT = 1;
    private  int x,y,dir,speed,imageNum;
    private Image pic;
    private  Rectangle rect;
    public Alligator(int home,int level){
        Random rand=new Random();
        if(rand.nextInt(RIGHT)==RIGHT){
            dir=RIGHT;
        }
        else{
            dir=LEFT;
        }
        x=home;
        y=30;
        String file = String.format("Obstacle/%d/alligator%d.png", dir, imageNum);
        pic = new ImageIcon(file).getImage();
        rect=new Rectangle(x,y,pic.getWidth(null),pic.getHeight(null));
        speed=2*level;
    }
    public void checkEat(Frog frog){
        if(rect.contains(frog.getX(),frog.getY())){
            frog.loseLive();
        }
    }

}
