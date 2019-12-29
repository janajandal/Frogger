public class Obstacle {
    //pic/type are either going to be ints or pics depending on how we want to code
    private static int x,y,dir,speed;
    private static String type;
    private static final int LEFT = -1, RIGHT = 1;

    public Obstacle(int lane,String type){
        this.type=type;
        if((lane % 2) == 0) {
            dir=RIGHT; //y=wherever we decide these lanes are;
            x=0;
        } else{
            dir=LEFT ; //y=wherever we decide these lanes are;
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
