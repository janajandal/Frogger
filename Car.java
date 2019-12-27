public class Car {
    //pic/type are either going to be ints or pics depending on how we want to code
    private static int x,y,dir,speed;
    private static final int LEFT = -1, RIGHT = 1;

    public Car(int lane){
        if(lane==1 || lane==3 || lane==5) {
            dir=LEFT; //y=wherever we decide these lanes are;
            x=0;
        } else if(lane==2 || lane==4){
            dir=RIGHT ; //y=wherever we decide these lanes are;
            x=0;
        }

    }
    public static void move(){
        x+=speed;
    }
    public static boolean collide(Frog frog){
        return (frog.getX()==x && frog.getY()==y);
    }
}
