import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;


public class Frogger extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel game;

    public Frogger() {
        super("Frogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myTimer = new Timer(10, this);	 // trigger every 10 ms
		game = new GamePanel(this);
		game.setPreferredSize(new Dimension(672, 744));
		add(game);
		setResizable(false);
		pack();		
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
    private Frog lady;
    private Image backPic = new ImageIcon("back.png").getImage();
    private Car[]cars= new Car[5];
    private Log[]logs= new Log[5];
    private ArrayList<Rectangle>homes=new ArrayList<Rectangle>();
    private Image back = new ImageIcon("back.png").getImage();


    public GamePanel(Frogger m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        mainFrame = m;

        player = new Frog(310, 162,false);
        load(1); // TODO: 2020-01-16 level up
        Random rand= new Random();
        int pos= rand.nextInt(9);
        //lady= new Frog(logs[pos].getX()+10,logs[pos].getY(),true);
        home();
        addKeyListener(this);
    }

    public void home(){
        for(int i=0;i<5;i++){
            Rectangle rect=new Rectangle(40*i,72*i,60,41);
            homes.add(rect);
        }
    }



    public void load(int level){
        int lane=1;
        for(int i=0;i<5;i++) {
            for(int j=1;j<4;j++){
                cars[i]=new Car(j,lane,level);
                logs[i]=new Log(j,lane,level);
            }
            lane++;
            if (lane==5){
                lane=0;
            }
        }
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }


    public void move() {
        for (Car car: cars) {
            car.move();
            car.checkHit(player);
        }
        for (Log log:logs) {
            log.move();
            log.checkFall(player);
        }

        for (Rectangle h: homes) {
            if(h.contains(player.getY(),player.getX())){
                player.incHome();
            }
        }

		if(player.getY() == 60) {
            System.out.println("fun");

		} else if(keys[KeyEvent.VK_UP]){
			player.verticalMove(-1);
			player.stayStill();
			player.addPoints(10);
			keys[KeyEvent.VK_UP] = false;
		} else if(keys[KeyEvent.VK_DOWN]){
			player.verticalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_DOWN] = false;
		} else if(keys[KeyEvent.VK_RIGHT]){
			player.horizontalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_RIGHT] = false;
		} else if(keys[KeyEvent.VK_LEFT]){
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


    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void paint(Graphics g){
		g.drawImage(backPic, 0, 0, 672, 744, null);
        player.jump(g);

        for (Car car: cars) {
            car.draw(g);

        }
        for (Log log:logs) {

            log.draw(g);
        }
        g.setColor(Color.white);
        Font font=new Font("Copperplate Gothic Bold",3,15);
        g.setFont(font);
        g.drawString(Integer.toString(player.getPoints()),119,17);
    }
}
