import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;


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
	private MouseListener mouse;
	private Frogger mainFrame;
	private boolean[] keys;
    private Frog player;
    private Font font;
    private int home;
	private GameOver gg;
	private Image back;
	private int w, h, lives;
	private int highscore;
	private Counter counter= new Counter(450);
	private ArrayList<Car> cars= new ArrayList<Car>();
	private ArrayList<Log> logs=new ArrayList<Log>();
	private int points;
	private MouseEvent e;
	//private ArrayList<Turtle> turtles;

	public GamePanel(Frogger m) {
		mainFrame = m;
		keys = new boolean[KeyEvent.KEY_LAST+1];
		addKeyListener(this);
		addMouseListener(mouse);
		gameFont();

		back = new ImageIcon("back.png").getImage();	//542x600
		w = back.getWidth(this);
		h = back.getHeight(this);
		setPreferredSize(new Dimension(w, h));

		player = new Frog(w/2, 520);
        points=0;
		lives = 3;

		//turtles = new ArrayList<Turtle>();
		load();

	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
        mainFrame.start();
	}

	public void move() {
	    counter.countDown();
		if(player.getY() == 64 || player.newLife()) {
			System.out.println("new");
			player = new Frog(w/2, 520);
		} else if(keys[KeyEvent.VK_UP] && player.getY() > 64){
			player.verticalMove(-1);
			player.stayStill();
			keys[KeyEvent.VK_UP] = false;
			points+=10;
		} else if(keys[KeyEvent.VK_DOWN] && player.getY() < 558){
			player.verticalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_DOWN] = false;

		} else if(keys[KeyEvent.VK_RIGHT]){
			if(player.getY() >= 292 && player.getX() >= 490) {
				points+=50;
				home++;
			}
			player.horizontalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_RIGHT] = false;
		} else if(keys[KeyEvent.VK_LEFT]){
			if(player.getY() >= 292 && player.getX() <= 34) {
				points+=50;
				home++;
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
				counter=new Counter(450);
			}
		}
		for(Log l : logs) {
			l.move();
		}
		if(player.getY() < 292 && player.getY() > 64){		
			boolean drown = true;
			Log on;
			for(Log l : logs) {
				if (!l.checkCollision(player) && drown) {
					drown = true;

				}
				else {
					drown = false;
					player.followLog(l.getSpeed(),l.getDir());
				}

			}
			if(drown) {
				lives--;
				counter=new Counter(450);
				player.frogDeath();
			}

		}
		if(counter.left()<=0){
            lives--;
            player.frogDeath();
            counter=new Counter(450);
        }
		if(home==5){
		  restart();
        }
		player.frogJump();

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		if(keys[KeyEvent.VK_R]){
            System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
		}
	}
	public void checkHS(){
		Scanner inFile = null;
		try {
			inFile = new Scanner(new BufferedReader(new FileReader("highScore.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inFile.hasNextInt()) {
			highscore=inFile.nextInt();
		} else {
			highscore=0;
		}
		inFile.close();
	}
	public void score(){
		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(new BufferedWriter(new FileWriter("highScore.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(highscore<points){
			outFile.println(points);
			highscore=points;
		}
		else {
			outFile.println(highscore);
		}
		outFile.close();
	}

    public void load() {
        for(int i = 0; i < 5; i++) {

				cars.add(new Car(i));
				logs.add(new Log(i));
			}
        /*
        //turtles.add(new Turtle(lvl, 0));
        logs.add(new Log(1));
        logs.add(new Log(2));
        //turtles.add(new Turtle(lvl, 3));
        logs.add(new Log(4));

         */
    }
	public void gameOver(Graphics g){
		setBackground(Color.BLACK);
		Rectangle exit= new Rectangle(113,331,60,60);
		Rectangle playag= new Rectangle(360,331,60,60);
		g.drawRect(113,331,60,60);
		g.drawString("EXIT",121,341);
		g.drawRect(360,331,60,60);
		g.drawString("PLAY AGAIN",368,341);
		if(mousePressed(e)){
			if(mouseReleased(e)){
				Point pos = MouseInfo.getPointerInfo().getLocation();
				if(exit.contains(pos)){
					System.exit(0);
				}
				else if(playag.contains(pos)){
					restart();
				}
			}
		}
	}
	public void restart(){
		points+=1000+(counter.left()*10);
		home=0;
		player= new Frog(w/2,520);
		lives=3;
		counter=new Counter(450);
	}
	public boolean mousePressed(MouseEvent e){
		return true;
	}

	public boolean mouseReleased(MouseEvent e){
		return true;
	}

	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
    	keys[e.getKeyCode()] = false;
    }

    public void paint(Graphics g) {

		if(lives<=0){
			gameOver(g);
			checkHS();
			score();
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

    	//g.setFont(font.deriveFont(18f));

    	g.setColor(Color.white);
    	g.drawString("TIME", 320, 590);
    	g.drawString("HIGHSCORE",320,30);
    	g.drawString("SCORE",20,30);
		g.drawString(Integer.toString(points),119,30);
		g.drawString(counter.toString(),400,590);
		g.drawString(String.valueOf(highscore),546,22);
    }

    public void gameFont() {
    	InputStream is = GamePanel.class.getResourceAsStream("frogger_font.ttf");
    	try {
            font = Font.createFont(Font.TRUETYPE_FONT, is);
    	} catch(IOException | FontFormatException ex) {
    		System.out.println(ex);
    	}
	}
}