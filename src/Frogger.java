/*
FILE NAME:Frogger.Java
BY:Jana Jandal Alrifai, Catherine Sun
SUMMARY:Where all game mechanics and move occur
 */

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
	private GameOver gg;
	private Counter counter;
	private ArrayList<Car> cars;
	private ArrayList<Log> logs;
	private MouseEvent e;
	private ArrayList<Rectangle>homes,empty;
	private ArrayList<Turtle> turtles;
	private Snake serpent;
	private boolean gotLady;
	
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
		counter = new Counter(1000);
		player = new Frog(w / 2, 520);
		points = 0;
		lvl = 1;
		reset();
		int pos= randint(0,logs.size()-1);
		lady = new Lady(logs.get(pos));
		highscore = checkHS();
		gotLady=false;
		lives=4;
		reset();
	}
	public void reset() {
		counter= new Counter(1000);
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
			//if the player finished all of the levels, a window opens.
			Win win= new Win();
			setVisible(false);
		}
		if(empty.isEmpty()) {
			//if all frogs have reached home, player progresses to another level
			lvl++;
			points+=1000+(counter.left()*10); //for all 5 homes player gains 1000 points and 10 points for each remaning time beat
			reset(); //restarts all changed variables to 0 to start new level
		}
	}
	
	public void movePlayer() {
		//moves player per to the key pressed
		counter.countDown();
		if(player.newLife()) {
			player = new Frog(w/2, 520);
		}
		if(keys[KeyEvent.VK_UP] && player.getY() > 64){
			player.verticalMove(-1);
			player.stayStill();
			keys[KeyEvent.VK_UP] = false;
			points+=10; //for every move forward the player gains 10 points
		} else if(keys[KeyEvent.VK_DOWN] && player.getY() < 558){
			player.verticalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_DOWN] = false;

		} else if(keys[KeyEvent.VK_RIGHT]){
			if(player.getY() >= 292 && player.getX() >= 490) {
				return; //stops the player from moving offscreen
			}
			player.horizontalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_RIGHT] = false;
		} else if(keys[KeyEvent.VK_LEFT]){
			if(player.getY() >= 292 && player.getX() <= 34) {
				return;//stops the player from moving offscreen
			}
			player.horizontalMove(-1);
			player.stayStill();
			keys[KeyEvent.VK_LEFT] = false;
		}
		player.frogJump();//changes the frame of the frog

		}

	
	public void moveObstacles() {
		//moves all obstacles and checks for collision with player
		for(Car c : cars) {
			c.move();
			if(c.checkCollision(player)) { //kills the frog if he is hit by car
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
			if(serpent.checkCollision(player)) {  //kills the frog if he collides with serpent
				lives--;
				player.frogDeath();
			}
		}

		if(player.getY() == 64) { //if the player is close to homes
			boolean dead = true; //player is presumed dead unless saved by reaching home
			for(Rectangle r : empty) {
				if(r.intersects(player.getRect())) { //if the player reaches a home
					dead = false;
					homes.add(r); //we added the home to the occupied list
					points+=50+(10*counter.left()); //gets 50 points adn 10 for each remaining clock beat
				}
			}
			empty.remove(homes.get(homes.size() - 1));
			if(dead) {
				lives--;
				player.frogDeath();

			} else {
				player = new Frog(w/2, 520);
				counter= new Counter(1000);
			}
		} else if(player.getY() < 292){		//if the player is close to the water
			boolean drown = true; //player assumed dead unless saved by log/turtle
			for(Log l : logs) {
				drown = l.checkCollision(player) ? false : drown;
			}
			for(Turtle t : turtles) {
				drown = t.checkCollision(player) ? false : drown;
			}
			if(drown) {
				lives--;
				player.frogDeath();
				counter= new Counter(1000);
			} else {
				int dir = ((player.getY() - 64)/38)%2 == 0 ? 1 : -1; //makes the player drift with the log or turtle
				player.drift(lvl*dir);
			}
		}
		if(counter.left()<=0){ //if player runs out of time, they lose a live
			lives--;
			player.frogDeath();
			player= new Frog(w/2,520); //makes a new frog and restarts time
			counter=new Counter(1000);
		}

		if(lady.checkCollision(player)){ //if frog gets lady he gets extra 200 points
			points+=200;
			gotLady=true;
		}
		if(gotLady){ //if lady is on frog, she will copy his movement
			lady.followFrog(player);
			lady.LadyJump();
			lady.stayStill();
		}
		else{
			lady.followLog(); //follows log otherwise
		}
	}

	public int checkHS(){ //checks what is the highest previous score is
		Scanner inFile = null;
		try {
			inFile = new Scanner(new BufferedReader(new FileReader("highScore.txt")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (inFile.hasNextInt()) { //if file is not empty read what is the score
			int hs=inFile.nextInt();
			inFile.close();
			return hs;
		} else { //if empty score is considered 0
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
		if(highscore<points){ //if current score is higher than previous highscore rewrite the highscore
			outFile.println(points);
			highscore=points;
		}
		else {
			outFile.println(highscore);
		}
		outFile.close();
	}

	public void load(int lvl) { //loads all objects into their lanes and x pos
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
			serpent = new Snake(); //addes extra obstacles as you level up
		}
	}


	public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
    	keys[e.getKeyCode()] = false;
    }
    
    public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low); //randomly chose a number from the limit
	}
    
    public void paint(Graphics g) { //draws all graphics
		if(lives<=0){
			setVisible(false); //removes current screen
			gg= new GameOver(); //displays game over screen if player has no more lives
			Writescore(); //writes the current score to file
		}

		g.drawImage(back, 0, 0, 542, 600, null); //draws background

		//lady.draw(g);

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
    		player.die(g); //draws death sequence
    	} else {
    		player.jump(g);
    	}
    	
    	for(int i = 0; i < homes.size(); i++) {
    		g.drawImage(sit, homes.get(i).x + 7, 60, 38, 38, null); //draws frogs at home
    	}
    	//sets the font colour and type, displays score and time
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