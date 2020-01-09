import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Frogger extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel game;

    public Frogger() {
        super("Frogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,700);
	setPreferredSize(new Dimension(500,550)); //wrong

        myTimer = new Timer(10, this);	 // trigger every 10 ms

        game = new GamePanel(this);
        add(game);


<<<<<<< HEAD

=======
        setPreferredSize(new Dimension(420,464));
>>>>>>> master
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
    private Car car1;
    private Log log1;
    private Image back = new ImageIcon("back.png").getImage();

    public GamePanel(Frogger m) {
        keys = new boolean[KeyEvent.KEY_LAST+1];
        mainFrame = m;

        player = new Frog(235, 490);
        player = new Frog(195, 404);
        addKeyListener(this);
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void move() {
        car1.checkHit(player);
        log1.checkFall(player);
		if(player.getY() == 60) {
			System.out.println("ribbit");
			player.loseLive();

		} else if(keys[KeyEvent.VK_UP] && player.getY() > 60){
			player.verticalMove(-1);
			player.stayStill();
			keys[KeyEvent.VK_UP] = false;
		} else if(keys[KeyEvent.VK_DOWN] && player.getY() < 520){
			player.verticalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_DOWN] = false;
		} else if(keys[KeyEvent.VK_RIGHT] && player.getX() < 520){
			player.horizantalMove(1);
			player.stayStill();
			keys[KeyEvent.VK_RIGHT] = false;
		} else if(keys[KeyEvent.VK_LEFT] && player.getX() > 0){
			player.horizantalMove(-1);
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

        //g.setColor(Color.black);
        //g.fillRect(0,0,450,390);
        g.drawImage(back,0,0,500,550,null);

        g.drawImage(back,0,0,420,464,null);

        player.jump(g);

    }
}
