import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Car {
    private static final int LEFT = -1, RIGHT = 1;
    private  int dir,speed,imageNum,place,restart;
    private  Image pic;
    private  Rectangle rect;
    public  Car(int lane,int place,int level){
        Random rand=new Random();
        if(lane % 2 == 0) {
            dir=LEFT;
            this.place=473+(place*2);
            restart=0;
        }
        else {
            dir=RIGHT;
            this.place=0;
            restart=654;
        }
        imageNum=rand.nextInt(3);
        String file = String.format("Obstacle/%d/car%d.png", dir, imageNum);
        pic = new ImageIcon(file).getImage();
        rect=new Rectangle(this.place,48*lane,pic.getWidth(null),pic.getHeight(null));
        speed=2*level;
    }
    public void move(){
        rect.move(rect.x+speed,rect.y);
        if((rect.x>restart && dir==RIGHT) || (rect.x<restart && dir==LEFT)) {
            rect.x = place;
        }
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
        g.drawImage(pic, rect.x + 30, rect.y, -48, 48, null);
    }
}
