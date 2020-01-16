import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Car {
    private static final int LEFT = -1, RIGHT = 1;
    private  int x,y,dir,speed,imageNum;
    private  Image pic;
    private  Rectangle rect;
    public  Car(int lane,int level){
        Random rand=new Random();
        x=0;
        if((lane % 2) == 0) {
            dir=RIGHT;

        } else{
            dir=LEFT ;

        }
        y=30*lane;
        imageNum=rand.nextInt(3);
        String file = String.format("Obstacle/%d/car%d.png", dir, imageNum);
        pic = new ImageIcon(file).getImage();
        rect=new Rectangle(x,y,pic.getWidth(null),pic.getHeight(null));
        speed=20*level;
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
