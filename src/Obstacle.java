import java.util.Random;
import javax.swing.*;
import java.awt.*;

public class Obstacle {
    //pic/type are either going to be ints or pics depending on how we want to code
    private static int x,y,dir,speed,imageNum;
    private static String type;
    private Image pic;
    private int frame;
    private static final int LEFT = -1, RIGHT = 1;
    Random random=new Random();
    public Obstacle(int lane,String type,int level){
        this.type=type;
        if((lane % 2) == 0) {
            dir=RIGHT; //y=wherever we decide these lanes are;
            x=0;
        } else{
            dir=LEFT ; //y=wherever we decide these lanes are;
            x=0;
        }
        if(type=="Car"){
            imageNum=random.nextInt(5);
        }
        else if(type=="Log"){
            imageNum=5+(random.nextInt(3));
        }
        Image img = new ImageIcon("Obstacle"+imageNum+".png").getImage();
        pic = img.getScaledInstance(30,50,Image.SCALE_SMOOTH);

        speed=20*level;
    }
    public static void move(){
        x+=speed;
    }
    public void obstmove(Graphics g){
        g.drawImage(pic, x + 30, y, -30, 30, null);
    }

    public static boolean collide(Frog frog){
        return (frog.getX()==x && frog.getY()==y);
    }

    public static void delay (long len){
        try	{
            Thread.sleep (len);
        }
        catch (InterruptedException ex)	{
            System.out.println("I hate when my sleep is interrupted");
        }
    }


}
