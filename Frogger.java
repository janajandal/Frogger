import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Frogger extends JFrame implements ActionListener{
	Timer myTimer;   
	GamePanel game;
	
	public Frogger() {
		super("Frogger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400,500);

		myTimer = new Timer(10, this);	 // trigger every 10 ms
		
		game = new GamePanel(this);
		add(game);
		
		setResizable(false);
		setVisible(true);
	}
	
	public void start(){
		myTimer.start();
	}

	public void actionPerformed(ActionEvent evt) {
		game.move();
		game.repaint();
	}
	
	public static void main(String[] args) {
		Frogger frame = new Frogger();
	}
}


class GamePanel extends JPanel implements KeyListener {
	private boolean[]keys;
	private Frogger mainFrame;
	private Frog player;
	
	public GamePanel(Frogger m) {
		keys = new boolean[KeyEvent.KEY_LAST+1];
		mainFrame = m;
		player = new Frog(200, 250);
		addKeyListener(this);
	}
	
	public void addNotify() {
		super.addNotify();
		requestFocus();
        mainFrame.start();
	}
	
	public void move() {
		if(keys[KeyEvent.VK_UP]){
			player.verticalMove(-20);
		} else if(keys[KeyEvent.VK_DOWN]){
			player.verticalMove(20);
		} else if(keys[KeyEvent.VK_RIGHT]){
			player.horizantalMove(20);
		} else if(keys[KeyEvent.VK_LEFT]){
			player.horizantalMove(-20);
		}
		player.frogJump();
	}
	
	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
    	keys[e.getKeyCode()] = false;
    }
    
    public void paint(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0,0,400, 500);
    	player.jump(g);
    }
}

import javax.swing.*;
import java.awt.*;

public class Frog {
    private int x,y;
    private int lives;
    private int dir;
    private Image[]pics;
    private int frame;
    private static final int FOR = 1, BACK = -1, LEFT = -1, RIGHT = 1;
    public Frog() {
        x=0;
        y=0;
        frame = 0;
        lives=3;
        pics = new Image[4];
        for(int i = 0; i<pics.length; i++){
            Image img = new ImageIcon("Frog/frog"+i+".png").getImage();
            pics[i] = img.getScaledInstance(30,50,Image.SCALE_SMOOTH);
            
        }
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

    public void Move(){ //in main we check for button , if button is left we setDir to 1, if down dir=-1, then call sidewaysMove
        x+=dir;
    }
    public void verticalMove(){ //same concept as sideways move
        y=y+(dir*20);
    }
    public void horizontalMove(){ //same concept as sideways move
        x=x+(dir*20);
    }
    
   
    public void frogJump() {
        frame = frame + 1 < pics.length ? frame + 1 : 0;
        delay(100);

    }
    public void jump(Graphics g){
        int w = pics[frame].getWidth(null);
        int h = pics[frame].getHeight(null);
        g.drawImage(pics[frame], x + w, y, -w, h, null);
    }

    public static void delay (long len){
        try	{
            Thread.sleep (len);
        }
        catch (InterruptedException ex)	{
            System.out.println("I hate when my sleep is iterrupted");
        }
    }


}
