import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class GameOver extends JFrame {
    private JFrame layeredPane = new JFrame();
    private int highscore;
    private JLabel gg;
    private JButton exit,playag;
    public GameOver(){
        super("Frogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        layeredPane.setPreferredSize(new Dimension(542,600));
        // TODO: 2020-01-21 make it appear in the middle
        gg.setBounds(0, 0, 542,600);
        exit= new JButton("EXIT");
        exit.setLocation(113,331);
        exit.addActionListener(new ClickStart());
        playag= new JButton("PLAY AGAIN");
        playag.setLocation(360,331);
        playag.addActionListener(new ClickStart());
        layeredPane.add(gg, 1);
        setContentPane(layeredPane);
        pack();
        setVisible(true);
    }

    public void main(String[] args) {
        GameOver gg= new GameOver();
    }
    class ClickStart implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            if(evt.getSource()==exit){
                System.exit(0);
            }
            else{
                setVisible(false);
                Frogger frame= new Frogger();
                frame.start();
            }

        }
    }
}

