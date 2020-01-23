import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Random;
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
		game.checkProgress();
		game.moveObstacles();
		game.movePlayer();
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
	private Image back, sit;
	private int w, h, lives,lvl,highscore,points;
	private Lady lady;
	private Counter counter;
	private ArrayList<Car> cars;
	private ArrayList<Log> logs;
	private MouseEvent e;
	private ArrayList<Rectangle>homes,empty;
	private ArrayList<Turtle> turtles;
	private Snake serpent;
	
	public GamePanel(Frogger m) {
		mainFrame = m;
		keys = new boolean[KeyEvent.KEY_LAST + 1];
		addKeyListener(this);
		addMouseListener(mouse);
		gameFont();

		back = new ImageIcon("back.png").getImage();    //542x600
		w = back.getWidth(this);
		h = back.getHeight(this);
		setPreferredSize(new Dimension(w, h));

		sit = new ImageIcon("frog/sit.png").getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
		counter = new Counter(450);
		player = new Frog(w / 2, 520);
		points = 0;
		lvl = 1;
		reset();
		Random rand = new Random();
		int pos = rand.nextInt(logs.size());
		System.out.println(pos);
		lady = new Lady(logs.get(pos));
		highscore = checkHS();
		reset();
	}
	public void reset() {
		counter= new Counter(450);
		empty = new ArrayList<Rectangle>();
		homes = new ArrayList<Rectangle>();
		cars = new ArrayList<Car>();
		logs = new ArrayList<Log>();
		turtles = new ArrayList<Turtle>();

		load(lvl);
	}
	
	public void addNotify() {
		super.addNotify();
		requestFocus();
        mainFrame.start();
	}
	
	public void checkProgress() {
		if(lvl == 3) {
			System.out.println("close the program you're done");
		}
		if(empty.isEmpty()) {
			lvl++;
			points+=1000+(counter.left()*10);
			System.out.println(lvl);
			reset();
		}
	}
	
	public void movePlayer() {
		counter.countDown();
		if(player.newLife()) {
			player = new Frog(w/2, 520);
		}
		if(keys[KeyEvent.VK_UP] && player.getY() > 64){
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
		player.frogJump();

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		if(keys[KeyEvent.VK_R]){
            System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
		}
	}
	
	public void moveObstacles() {
		for(Car c : cars) {
			c.move();
			if(c.checkCollision(player)) {
				lives--;
				player.frogDeath();
			}
		}
		for(Log l : logs) {
			l.move();
		}
		for(Turtle t : turtles) {
			t.move();
		}
		if(lvl == 2) {
			serpent.move();
			if(serpent.checkCollision(player)) {
				lives--;
				player.frogDeath();
			}
		}
		
		if(player.getY() == 64) {
			boolean dead = true;
			for(Rectangle r : empty) {
				if(r.intersects(player.getRect())) {
					dead = false;
					homes.add(r);
					points+=50;
				}
			}
			empty.remove(homes.get(homes.size() - 1));
			if(dead) {
				lives--;
				player.frogDeath();

			} else {
				player = new Frog(w/2, 520);
				counter= new Counter(450);
			}
		} else if(player.getY() < 292){		
			boolean drown = true;
			for(Log l : logs) {
				drown = l.checkCollision(player) ? false : drown;
			}
			for(Turtle t : turtles) {
				drown = t.checkCollision(player) ? false : drown;
			}
			if(drown) {
				lives--;
				player.frogDeath();
				counter= new Counter(450);
			} else {
				int dir = ((player.getY() - 64)/38)%2 == 0 ? 1 : -1;
				player.drift(lvl*dir);
			}
		}
		if(counter.left()<=0){
			lives--;
			player.frogDeath();
			player= new Frog(w/2,520);
			counter=new Counter(450);
		}

		if(lady.checkCollision(player)){
			points+=200;
			lady.followFrog(player);
		}
	}

	public int checkHS(){
		Scanner inFile = null;
		try {
			inFile = new Scanner(new BufferedReader(new FileReader("highScore.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inFile.hasNextInt()) {
			int hs=inFile.nextInt();
			inFile.close();
			return hs;
		} else {
			inFile.close();
			return 0;
		}

	}
	public void Writescore(){
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

	public void load(int lvl) {
		for(int i = 0; i < 5; i++) {
			empty.add(new Rectangle(30 + 108*i, 60, 50, 38));
		}
		for(int lane = 0; lane < 5; lane++) {
			for(int i = 0; i < randint(1, lvl + 1); i++) {
				cars.add(new Car(lvl, lane, randint(100, 300)*i));
			}
		}
		for(int i = 0; i < 3; i++) {
			logs.add(new Log(lvl, 7, randint(100, 160)*i));
		}
		for(int i = 0; i < 2; i++) {
			logs.add(new Log(lvl, 8, randint(300, 400)*i));
			logs.add(new Log(lvl, 10, randint(300, 400)*i));
		}
		for(int i = 0; i < randint(3, 4); i++) {
			turtles.add(new Turtle(lvl, 6, randint(120, 200)*i));
			turtles.add(new Turtle(lvl, 9, randint(120, 200)*i));
		}

		if(lvl == 2) {
			serpent = new Snake();
		}
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
					reset();
				}
			}
		}
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
    
    public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low);
	}
    
    public void paint(Graphics g) {

		if(lives<=0){
			gameOver(g);
			Writescore();
		}


		g.drawImage(back, 0, 0, 542, 600, null);
		
		for(Car c : cars) {
			c.draw(g);
		}
		for(Log l : logs) {
			l.draw(g);
		}
		for(Turtle t : turtles) {
			t.draw(g);
		}
		if(lvl == 2) {
			serpent.draw(g);
		}
		
    	if(player.isDead()) {
    		player.die(g);
    	} else {
    		player.jump(g);
    	}
    	
    	for(int i = 0; i < homes.size(); i++) {
    		g.drawImage(sit, homes.get(i).x + 7, 60, 38, 38, null);
    	}
    	
    	g.setColor(Color.white);
    	//g.setFont(font.deriveFont(11f));
    	g.drawString("SCORE", 5, 15);
    	g.drawString("HIGH SCORE", 205, 15);
    	
    //	g.setFont(font.deriveFont(18f));
    	g.drawString("TIME", 400, 590);
		g.drawString(Integer.toString(points),119,15);
		g.drawString(counter.toString(),440,590);
		g.drawString(String.valueOf(highscore),446,15);
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