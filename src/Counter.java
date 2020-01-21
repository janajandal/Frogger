public class Counter {
    private int len, current;
    public Counter(int len){
        this.len=len;
    }
    public void countDown(){
        current =len--;
    }
    public String toString(){
        return (String.valueOf(current));
    }
    public int left(){
        return current;
    }
}
