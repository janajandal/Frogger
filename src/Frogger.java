//FILENAME: Frogger.java
//BY: Jana Jandal Alrifai and Catherine Sun
//SUMMARY: main program consisting of the JFrame and JPanel for the game interface

import java.awt.*;
import java.awt.event.*;
import javax.sound.midi.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;
import java.util.Scanner;


public class Frogger extends JFrame implements ActionListener {
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
		if(game.running()) {
			game.moveObstacles();
			game.movePlayer();
		}
		game.repaint();
	}

    public static void main(String[] args) {
		Frogger slide = new Frogger();
	}
}


class GamePanel extends JPanel implements KeyListener, MouseListener{
	private Frogger mainFrame;
	private boolean[] keys;
	private Font[] font;	//custom font
	private Frog player;

	private Image[] sitPics;
	private Image back, heartPic, timePic;
	private int w, h, lives, lvl, highscore, score, dist, slide;
	private Point hover;
	private boolean pause, click;
	private String endMsg;

	private ArrayList<Rectangle> empty, homes;
	private ArrayList<Integer> cols;

	//obstacles
	private ArrayList<Car> cars;
	private ArrayList<Log> logs;
	private ArrayList<Turtle> turtles;
	private Snake serpent;
	private Lady lady;
	private Fly fly;
	private Counter counter;

	public GamePanel(Frogger m) {
		mainFrame = m;
		keys = new boolean[KeyEvent.KEY_LAST+1];
		addKeyListener(this);
		addMouseListener(this);

		font = new Font[2];
		gameFont();

		//loading images

		back = new ImageIcon("back.png").getImage();	//542x600
		w = back.getWidth(this);
		h = back.getHeight(this);
		setPreferredSize(new Dimension(w, h));

		sitPics = new Image[2];
		for(int i = 0 ; i < 2; i++) {
			String file = String.format("frog/sit%d.png", i);
			sitPics[i] = new ImageIcon(file).getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
		}
		heartPic = new ImageIcon("heart.png").getImage();
		timePic = new ImageIcon("time.png").getImage();

		lvl = 1;
		highscore = checkHS();	//highscore from txt
		score = 0;
		slide = -w;
		click = false;	//true when mouse pressed

		reset();	//initiate clear variables
	}

	public void reset() {
		player = new Frog(w/2, 520);
		counter = new Counter(lvl*2000);

		lives = lvl == 1 ? 3 : 5;
		dist = -1;

		empty = new ArrayList<Rectangle>();
		homes = new ArrayList<Rectangle>();
		cols = new ArrayList<Integer>();
    	cars = new ArrayList<Car>();
		logs = new ArrayList<Log>();
		turtles = new ArrayList<Turtle>();
		fly = null;
		lady = null;

		load(lvl);
	}

	public void newFrog() {
		player = new Frog(w/2, 520);
		counter = new Counter(lvl*2000);
	}

	public void deadFrog() {
		lives--;
		player.frogDeath();
	}

	public boolean running() {	//pause the game
		return !pause;
	}

	public void addNotify() {
		super.addNotify();
		requestFocus();
        mainFrame.start();
	}

	public void checkProgress() {
		pause = lives == 0|| lvl == 3 ? true : false;
		endMsg = lvl == 3 ? "YOU WON!" : "GAME OVER";
		if(!pause) {
			counter.countDown();
		}
		highscore = Math.max(score, highscore);
		if(lives == 0) {
			writeScore();
		}
		if(counter.left() == 0) {	//out of time, frog dies
			deadFrog();
		}
		if(player.newLife()) {
			newFrog();
		}
		if(empty.isEmpty()) {	//all homes are occupied, level complete
			if(slide < w) {
				slide += 6;
			} else {
				lvl++;
				score += 1000;
				reset();
			}
		}

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		hover = new Point(mouse.x - offset.x, mouse.y - offset.y);
	}

	public void movePlayer() {
		if(!player.isDead()) {
			if(keys[KeyEvent.VK_UP] && player.getY() > 64){
				score = Math.max(dist, player.getLane() + 1) == dist ? score : score + 10;
				dist = Math.max(dist, player.getLane() + 1);
				player.verticalMove(-1);
				player.stayStill();
				keys[KeyEvent.VK_UP] = false;
			} else if(keys[KeyEvent.VK_DOWN] && player.getY() < 520){
				player.verticalMove(1);
				player.stayStill();
				keys[KeyEvent.VK_DOWN] = false;
			} else if(keys[KeyEvent.VK_RIGHT]){
				if(player.getY() >= 292 && player.getX() >= 490) {
					return;
				}
				player.horizantalMove(1);
				player.stayStill();
				keys[KeyEvent.VK_RIGHT] = false;
			} else if(keys[KeyEvent.VK_LEFT]){
				if(player.getY() >= 292 && player.getX() <= 34) {
					return;
				}
				player.horizantalMove(-1);
				player.stayStill();
				keys[KeyEvent.VK_LEFT] = false;
			}
		}
		player.frogJump();	//frames
	}

	public void moveObstacles() {	//move and check for collisions
		for(Car c : cars) {
			c.move();
			if(c.checkCollision(player)) {
				deadFrog();
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
				deadFrog();
			}
		}
		if(lady == null) {
			lady = randint(0, 200) == 0 && player.isSingle() ? new Lady(lvl, logs.get(randint(0, logs.size() - 1))) : lady;
		} else {
			lady.move();
			if(lady.checkCollision(player)) {
				lady = null;
				score += 200;
				player.changeColour();
			}
			if(randint(0, 200) == 0 && lady.notJumping()) {	//leave frames undisturbed
				lady.frogJump();
			}
		}
		if(!empty.isEmpty()) {	//there are still unoccupied homes for flies to live
			if(fly == null) {
				fly = randint(0, 200) == 0 ? new Fly(empty.get(randint(0, empty.size() - 1))) : null;
			} else {
				if(fly.checkCollision(player)) {	//disappear when eaten
					fly = null;
				} else if (randint(0, 400/lvl) == 0) {	//may disappear randomly
					fly = null;
				}
			}
		}
		if(player.getY() == 64) {
			Rectangle toRemove = null;
			for(Rectangle r : empty) {
				if(r.intersects(player.getRect())) {	//frog arrived home
					toRemove = r;
					score += 50 + counter.left()%2;
				}
			}
			if(toRemove == null) {
				deadFrog();
			} else {
				score += player.isSingle() ? 0 : 200;	//add to score
				int i = player.isSingle() ? 0 : 1;
				cols.add(i);
				homes.add(toRemove);
				empty.remove(toRemove);
				dist = -1;
				newFrog();
			}
		}
		if(player.getY() < 292 && player.getY() >= 102) {
			boolean drown = true;
			for(Log l : logs) {
				drown = l.checkCollision(player) ? false : drown;
			}
			for(Turtle t : turtles) {
				drown = t.checkCollision(player) ? false : drown;
			}
			if(drown) {
				deadFrog();
			} else {	//player is sitting on a log or turtle and drifts along the current
				int dir = player.getLane()%2 == 0 ? -1 : 1;
				player.drift(lvl*dir);
			}
		}
	}

	public int checkHS(){ //checks what is the highest previous score is
		Scanner inFile = null;
		try {
			inFile = new Scanner(new BufferedReader(new FileReader("highscore.txt")));
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

	public void writeScore(){
		PrintWriter outFile = null;
		try {
			outFile = new PrintWriter(new BufferedWriter(new FileWriter("highscore.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		outFile.println(highscore);	//update highscore in highscore.txt
		outFile.close();
	}

    public void load(int lvl) {	//generate obstacles randomly at random distances from each other and at random positions
    	int x, space, offset;
    	for(int i = 0; i < 5; i++) {
    		empty.add(new Rectangle(30 + 108*i, 60, 50, 38));
    	}
    	for(int lane = 0; lane < 5; lane++) {
    		x = randint(0, w);
    		space = randint(50, 160);
    		for(int i = 0; i < randint(1, lvl + 1); i++) {
    			offset = cars.isEmpty() || i == 0 ? 0 : cars.get(cars.size() - 1).getW() + space;
    			cars.add(new Car(lvl, lane, x + offset));
    		}
    	}
    	for(int lane = 7; lane < 11; lane += 2) {
    		x = randint(0, w);
    		space = randint(30, 150);
    		lane = lane == 9 ? lane = 8 : lane;
    		for(int i = 0; i < randint(2, 3); i++) {
    			offset = logs.isEmpty() || i == 0 ? 0 : logs.get(logs.size() - 1).getW() + space;
    			logs.add(new Log(lvl, lane, x + offset));
    		}
    	}
    	for(int lane = 6; lane < 10; lane += 3) {
    		x = randint(0, w);
    		space = randint(120, 160);
    		for(int i = 0; i < randint(2, 3); i++) {
    			offset = turtles.isEmpty() || i == 0 ? 0 : turtles.get(turtles.size() - 1).getW() + space;
    			for(int j = 0; j < randint(1, 3); j++) {
    				turtles.add(new Turtle(lvl, lane, x + offset + 30*j));
    			}
    		}
    	}
    	if(lvl == 2) {
    		serpent = new Snake();
    	}
    }

    public void paint(Graphics g) {
		g.drawImage(back, 0, 0, 542, 600, null);

		//draw obstacles
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
		if(fly != null) {
			fly.draw(g);
		}

    	//draw frogs in home
    	for(int i = 0; i < homes.size(); i++) {
    		g.drawImage(sitPics[cols.get(i)], homes.get(i).x + 7, 60, 38, 38, null);
    	}

    	for(int i = 0; i < lives; i++) {
    		g.drawImage(heartPic, (10 + 5)*i + 5, 565, 10, 11, null);	//lives left
    	}

    	if(player.isDead()) {
    		player.die(g);
    	} else {
    		player.jump(g);
    	}

    	if(lady != null) {
    		lady.draw(g);
    	}

    	//text in border of screen
    	g.setColor(Color.white);
    	//g.setFont(font[0].deriveFont(11f));
    	g.drawString("SCORE", 5, 20);
    	g.drawString("HIGH SCORE", 205, 20);
    	//g.setFont(font[0].deriveFont(14f));
		g.drawString(Integer.toString(score), 75, 25);
		g.drawString(Integer.toString(highscore), 330, 25);

    	//g.setFont(font[0].deriveFont(18f));
    	g.drawString("TIME", 400, 586);
    	g.drawImage(timePic, 140, 570, 250, 18, null);
    	g.setColor(Color.black);
    	g.fillRect(140, 570, 250 - (int)counter.left()/(lvl*8), 18);


//    	g.setFont(font[1].deriveFont(50f));
    	g.setColor(new Color(252, 186, 3));
    	if(empty.isEmpty()) {
    		g.drawString("LEVEL UP", slide, 220);
       	}

       	//game is paused when the player wins or loses
       	//messages vary depending on the player's result
       	if(pause) {
       		g.setColor(Color.black);
       		g.fillRect(w/2 - 150, h/2 - 130, 300, 200);
       		g.setColor(Color.white);
    		//g.setFont(font[1].deriveFont(24f));
       		g.drawRect(w/2 - 150, h/2 - 130, 300, 200);
       		g.drawString(endMsg, w/2 - 100, h/2 - 80);

       		//g.setFont(font[0].deriveFont(11f));
       		ArrayList<String> recap = generateStats();
       		for(int i = 0; i < recap.size(); i++) {
       			g.drawString(recap.get(i), w/2 - 130, 255 + 30*i);
       		}

       		g.setColor(new Color(3, 252, 236));
    		String compliment = score == highscore ? "New Personal Best!" : "";
    		for(int i = 0; i < compliment.length(); i++) {
    			delay(2);
    			g.drawString(compliment.substring(i, i + 1), 185 + 10*i, 350 + randint(-3 ,3));
    		}


    		//player may choose to play again
    		Rectangle playAgain = new Rectangle(w/2 - 85, 385, 170, 28);

       		//g.setFont(font[0].deriveFont(16f));
       		g.setColor(Color.black);
       		g.fillRect(playAgain.x, playAgain.y, 170, 28);
    		g.setColor(Color.white);
       		g.drawRect(playAgain.x, playAgain.y, 170, 28);
    		g.drawString("PLAY AGAIN", playAgain.x + 5, 405);

    		Image highlight = new ImageIcon("highlight.png").getImage();
    		if(playAgain.contains(hover)) {
    			g.drawImage(highlight, playAgain.x, playAgain.y, 170, 28, null);	//hover colour
    			if(click) {
    				pause = false;
    				score = 0;	//new game, new score
    				lvl = 1;
    				reset();
    			}
    		}
       	}
    }

    public ArrayList<String> generateStats() {
    	ArrayList<String> arr = new ArrayList<String>();

   		String endScore = String.format("score:%-5d", score);
   		String endBest = String.format("best:%-5d", highscore);
   		String endLvl = String.format("level reached:%-5d", lvl);
   		String endLives = String.format("lives left:%-5d", lives);

   		arr.add(String.format("%-24s", endScore));
   		arr.add(String.format("%-24s", endLvl));
   		arr.add(String.format("%-24s", endLives));

   		return arr;

    }

    public void gameFont() {
    	for(int i = 0; i < 2; i++) {
    		String file = String.format("font%d.ttf", i);
    		InputStream is = GamePanel.class.getResourceAsStream(file);
    		try {
    			font[i] = Font.createFont(Font.TRUETYPE_FONT, is);
    		} catch(IOException ex) {
    			System.out.println(ex);
    		} catch(FontFormatException ex) {
    			System.out.println(ex);
    		}
    	}
    }

    public static void delay (long len){
		try	{
		    Thread.sleep (len);
		}
		catch (InterruptedException ex)	{
		    System.out.println("I hate when my sleep is iterrupted");
		}
    }

    public static int randint(int low, int high){
		return (int)(Math.random()*(high-low+1)+low);
	}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
    	keys[e.getKeyCode()] = false;
    }

	public void keyTyped(KeyEvent e) {}

    // ------------ MouseListener ------------------------------------------
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {
    	click = false;
    }
    public void mouseClicked(MouseEvent e){
    	click = true;
    }

    public void mousePressed(MouseEvent e){}
}