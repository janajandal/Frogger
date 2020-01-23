import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class StartMenu extends JFrame {
	private JLayeredPane layeredPane = new JLayeredPane();
	private ImageIcon backPic, titlePic;
	private JLabel back, title;
	private JButton play;
	private Font font;
	private int w, h;

    public StartMenu() {
    	super("Frogger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	backPic = new ImageIcon("back.png");
    	w = backPic.getIconWidth();
    	h = backPic.getIconHeight();
    	
		layeredPane.setPreferredSize(new Dimension(w, h));
    	
    	back = new JLabel(backPic);
    	back.setBounds(0, 0, w, h);
    	layeredPane.add(back, JLayeredPane.DEFAULT_LAYER);
		
		gameFont();
		
    	play = new JButton("CLICK HERE TO START");
    	play.setFont(font);
    	play.setForeground(Color.white);
    	play.setBounds(w/2 - 150, 380, 300, 30);
    	play.setFocusPainted(false);
		play.setContentAreaFilled(false);
		play.setBorderPainted(false);
		play.addMouseListener(new HoverColour());
    	play.addActionListener(new ClickStart());
    	layeredPane.add(play, JLayeredPane.PALETTE_LAYER);
    	
    	titlePic = new ImageIcon("title.png");
    	title = new JLabel(titlePic);
    	w = w/2 - titlePic.getIconWidth()/2;
    	h = 140;
    	title.setBounds(w, h, titlePic.getIconWidth(), titlePic.getIconHeight());
    	layeredPane.add(title, JLayeredPane.MODAL_LAYER);
    	
		setContentPane(layeredPane);
		pack();
		setResizable(false);
		setVisible(true);
    }
    
    public void gameFont() {
    	InputStream is = StartMenu.class.getResourceAsStream("frogger_font.ttf");
    	try {
    		font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(14f);
    	} catch(IOException | FontFormatException ex) {
    		System.out.println(ex);	
    	}
	}


    class ClickStart implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent evt) {
    		Frogger frame = new Frogger();
    		frame.start();
    		setVisible(false);
    	}
    }
    
    
    class HoverColour implements MouseListener {
    	public void mouseEntered(MouseEvent e) {
    		play.setForeground(Color.green);
    	}
    	
    	public void mouseExited(MouseEvent e) {
    		play.setForeground(Color.white);
    	}
    	
    	public void mousePressed(MouseEvent e){}
    	
        public void mouseReleased(MouseEvent e){}
        
        public void mouseClicked(MouseEvent e) {}
    }

    public static void main(String[] arguments) {
		StartMenu menu = new StartMenu();
    }
}


