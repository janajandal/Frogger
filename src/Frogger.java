import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;


public class Frogger extends JFrame implements ActionListener{
	Timer myTimer;
	GamePanel game;

	public Frogger() {
		super("Frogger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		myTimer = new Timer(10, this);	 // trigger every 10 ms

		game = new GamePanel(this);
		add(game);

		pack();
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
	private Frogger mainFrame;
	private boolean[] keys;
	private Font font;
	private Frog player;
	private gameOver gg;
	private Image back;
	private int w, h, lives;
	private Counter counter;
	private ArrayList<Car> cars;
	private ArrayList<Log> logs;
	private int points;
	//private ArrayList<Turtle> turtles;

	public GamePanel(Frogger m) {
		mainFrame = m;
		keys = new boolean[KeyEvent.KEY_LAST+1];
		addKeyListener(this);
		gameFont();

		back = new ImageIcon("back.png").getImage();	//542x600
		w = back.getWidth(this);
		h = back.getHeight(this);
		setPreferredSize(new Dimension(w, h));

		player = new Frog(w/2, 520);

		lives = 3;

		cars = new ArrayList<Car>();
		logs = new ArrayList<Log>();
		//turtles = new ArrayList<Turtle>();
		load(1);
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
        mainFrame.start();
	}

	public void move() {
		if(player.getY() == 64 || player.newLife()) {
			System.out.println("new");
			player = new Frog(w/2, 520);
		} else if(keys[KeyEvent.VK_UP] && player.getY() > 64){
			player.verticalMove(-1);
			player.stayStill();
			keys[KeyEvent.VK_UP] = false;
		} else if(keys[KeyEvent.VK_DOWN] && player.getY() < 558){
			player.verticalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_DOWN] = false;
		} else if(keys[KeyEvent.VK_RIGHT]){
			if(player.getY() >= 292 && player.getX() >= 490) {
				return;
			}
			player.horizontalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_RIGHT] = false;
		} else if(keys[KeyEvent.VK_LEFT]){
			if(player.getY() >= 292 && player.getX() <= 34) {
				return;
			}
			player.horizontalMove(-1);
			player.stayStill();
			keys[KeyEvent.VK_LEFT] = false;
		}
		for(Car c : cars) {
			c.move();
			if(c.checkCollision(player)) {
				lives--;
				player.frogDeath();
			}
		}
		boolean drown = true;
		for(Log l : logs) {
			l.move();
			if(player.getY() < 292 && l.checkCollision(player)) {
				drown = false;
			}
		}
		if(player.getY() < 292 && drown) {
			lives--;
			player.frogDeath();
		}

		player.frogJump();

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		if(keys[KeyEvent.VK_R]){
            System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
		}
	}

    public void load(int lvl) {
        for(int i = 0; i < 5; i++) {
        	cars.add(new Car(lvl, i));
        }
        //turtles.add(new Turtle(lvl, 0));
        logs.add(new Log(lvl, 1));
        logs.add(new Log(lvl, 2));
        //turtles.add(new Turtle(lvl, 3));
        logs.add(new Log(lvl, 4));
    }

	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
    	keys[e.getKeyCode()] = false;
    }

    public void paint(Graphics g) {
		if(player.isDead()){
			gg.setVisible(true);
			gg.write(points);
		}
		g.drawImage(back, 0, 0, 542, 600, null);
		for(Car c : cars) {
			c.draw(g);
		}
		for(Log l : logs) {
			l.draw(g);
		}
    	if(player.isDead()) {
    		player.die(g);
    	} else {
    		player.jump(g);
    	}

    	g.setFont(font.deriveFont(18f));
    	g.drawString("TIME", 400, 590);
    	g.setColor(Color.white);
		g.drawString(Integer.toString(points),119,22);
		g.drawString(counter.toString(),570,727);
		g.drawString(gg.getScore(),546,22);
    }

    public void gameFont() {
    	InputStream is = GamePanel.class.getResourceAsStream("frogger_font.ttf");
    	try {
    		font = Font.createFont(Font.TRUETYPE_FONT, is);
    	} catch(IOException ex) {
    		System.out.println(ex);
    	} catch(FontFormatException ex) {
    		System.out.println(ex);
    	}
    }
}