import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartMenu extends JFrame {
	private JLayeredPane layeredPane = new JLayeredPane();

    public StartMenu() {
    	super("Frogger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		layeredPane.setPreferredSize(new Dimension(672, 744));

    	ImageIcon backPic = new ImageIcon("menu.png");

    	JLabel back = new JLabel(backPic);
    	back.setBounds(0, 0, backPic.getIconWidth(), backPic.getIconHeight());
    	layeredPane.add(back, 1);

		ImageIcon startPic = new ImageIcon("keyStart.png");
		JButton startBtn = new JButton(startPic);
		startBtn.addActionListener(new ClickStart());
    	startBtn.setSize(startPic.getIconWidth(), startPic.getIconHeight());
    	startBtn.setBorderPainted(false);
    	startBtn.setLocation(backPic.getIconWidth()/2 - startPic.getIconWidth()/2, 420);
		layeredPane.add(startBtn, 2);
;
		setContentPane(layeredPane);
		pack();
		setVisible(true);
    }

    class ClickStart implements ActionListener {
    	@Override
    	public void actionPerformed(ActionEvent evt) {
    		Frogger frame = new Frogger();
    		frame.start();
    		setVisible(false);
    	}
    }

    public static void main(String[] arguments) {
		StartMenu menu = new StartMenu();
    }
}
