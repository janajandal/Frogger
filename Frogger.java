public class Frog {
    private int x,y;
    private String place;
    private int lives;
    private int dir;
    private static final int FOR = 1, BACK = -1, LEFT = -1, RIGHT = 1;
    public Frog() {
        x=0;
        y=0;
        lives=3;
        place="grass";
        dir=FOR;
    }
    public boolean isDead(){
        return lives<=0;
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public void setDir(int dir) {
        this.dir = dir;
    }

    public void sidewaysMove(){ //in main we check for button , if button is left we setDir to 1, if down dir=-1, then call sidewaysMove
        x+=dir;
    }
    public void verticalMove(){ //same concept as sideways move
        y+=dir;
    }
    public void changePlace(){
        /*
        if statements regarding positon on screen and then change the string example:
            if(x>670){
                place="water";
         */
    }


}
