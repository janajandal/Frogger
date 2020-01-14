import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JFrame {
	private JLayeredPane layeredPane = new JLayeredPane();

    public StartMenu() {
    	super("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layeredPane.setPreferredSize(new Dimension(672, 744));
    	ImageIcon backPic = new ImageIcon("menu.png");
    	JLabel back = new JLabel(backPic);
    	back.setBounds(0, 0, backPic.getIconWidth(), backPic.getIconHeight());
    	layeredPane.add(back, 1);
		JButton startBtn = new JButton("Play");
		startBtn.addActionListener(new ClickStart());
    	startBtn.setSize(100,30);
    	startBtn.setLocation(380, 420);
		layeredPane.add(startBtn, 2);
		setContentPane(layeredPane);
		pack();
		setVisible(true);
    }

    class ClickStart implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent evt) {
    		Frogger play = new Frogger();
    		setVisible(false);
    	}
    }

    public static void main(String[] arguments) {
		StartMenu frame = new StartMenu();
    }
}
