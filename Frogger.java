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
			player.stayStill();
			keys[KeyEvent.VK_UP] = false;
		} else if(keys[KeyEvent.VK_DOWN]){
			player.verticalMove(20);
			player.stayStill();
			keys[KeyEvent.VK_DOWN] = false;
		} else if(keys[KeyEvent.VK_RIGHT]){
			player.horizantalMove(20);
			player.stayStill();
			keys[KeyEvent.VK_RIGHT] = false;
		} else if(keys[KeyEvent.VK_LEFT]){
			player.horizantalMove(-20);
			player.stayStill();
			keys[KeyEvent.VK_LEFT] = false;
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

class Frog {
	private int x, y;
	private Image[][]pics;
	private static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	private int frame, dir = UP;
	
	public Frog(int x, int y) {
		this.x = x;
		this.y = y;
		frame = 0;
		
		pics = new Image[4][5];		
		for(int i = 0; i<pics.length; i++){
			for(int j = 0; j<5; j ++) {
				String file = String.format("Frog/frog%d/frog%d.png", i, j);
				Image img = new ImageIcon(file).getImage();
				pics[i][j] = img.getScaledInstance(30,30,Image.SCALE_SMOOTH);
			}
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void horizantalMove(int dist) {
		if(dist < 0) {
			dir = LEFT;
		} else {
			dir = RIGHT;
		}
		x += dist;
	}
	
	public void verticalMove(int dist) {
		if(dist < 0) {
			dir = UP;
		} else {
			dir = DOWN;
		}
		y += dist;
	}
	
	public void stayStill() {
		frame = 0;
	}
	
	public void frogJump() {
		frame = frame + 1 < pics.length ? frame + 1 : 4;
		delay(100);
		
	}
	public void jump(Graphics g){
		Image sprite = pics[dir][frame];
		g.drawImage(sprite, x + 30, y, -30, 30, null);
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