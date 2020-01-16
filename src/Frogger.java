import java.awt.*;
import java.awt.event.*;
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
    private Car[]cars= new Car[9];
    private Log[]logs= new Log[9];
    private Image back = new ImageIcon("back.png").getImage();


    public GamePanel(Frogger m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        mainFrame = m;

        player = new Frog(336, 648,false);
        load(1);
        Random rand= new Random();
        int pos= rand.nextInt(9);
        lady= new Frog(logs[pos].getX()+10,logs[pos].getY(),true);

        addKeyListener(this);
    }
    public void load(int level){
        int lane=0;
        for(int i=0;i<9;i++) {
            Car car[i]=new Car(lane,level);
            cars[i]=car[i];
            Log log[i]=new Log(lane,level);
            lane++;
            if (lane==4){
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
        if(player.getY()==55){ //homes for the frogs, idk if measurement is right
           //if(0=<player.getX()>=55 || 85<player.getX()>105|| 135<=player.getX()>=165 || 190<=player.getX()>=220 || 260=<player.getX()>=310){
                //dont know if sizes work
                player.incHome();
                player.hasLady()
            }
		if(player.getY() == 60) {
			System.out.println("ribbit");
			player.loseLive();

		} else if(keys[KeyEvent.VK_UP] && player.getY() > 120){
			player.verticalMove(-1);
			player.stayStill();
			player.setPoints(10);
			keys[KeyEvent.VK_UP] = false;
		} else if(keys[KeyEvent.VK_DOWN] && player.getY() < 696){
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

		/*Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point offset = getLocationOnScreen();
		System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");*/
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
        /*
        for (Car car: cars) {
            car.draw(g);
        }
        for (Log log:logs) {
            log.draw(g);
        }

         */
    }
}
