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
    private Obstacle car1;


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
        car1.obstmove(g);
    }
}