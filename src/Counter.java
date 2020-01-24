//FILENAME:Counter.java
//BY: Jana Jandal Alrifai and Catherine Sun
//SUMMARY: used to keep track of time in Frogger
public class Counter {
    private int len, current;
    
    public Counter(int len) {
        this.len = len; //length of seconds needed multiplied by 10
    }
    
    public void countDown() {
        current = len--; //counts down the seconds
    }
    
    public String toString() {
        return (String.valueOf(current)); //used to display the time
    }
    
    public int left(){ //checks how many seconds are left to be used for points
        return current;
    }
}
