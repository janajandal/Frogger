import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class GameOver extends JFrame {
    private JLayeredPane layeredPane = new JLayeredPane();
    private int highscore;
    private JButton exit,playag;
    public GameOver(){
        super("Frogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layeredPane.setPreferredSize(new Dimension(235, 244));

        ImageIcon ggPic = new ImageIcon("gg.png");
        // TODO: 2020-01-21 make it appear in the middle 
        JLabel gg= new JLabel(ggPic);
        gg.setBounds(0, 0, ggPic.getIconWidth(), ggPic.getIconHeight());
        exit= new JButton();
        exit.setLocation(45,196);
        exit.addActionListener(new ClickStart());
        playag= new JButton();
        playag.setLocation(22,196);
        playag.addActionListener(new ClickStart());
        layeredPane.add(gg, 1);
        setContentPane(layeredPane);
        pack();

    }
    public void write(int score){
        try {
            Scanner inFile = new Scanner(new BufferedReader(new FileReader("highScore.txt")));
            if (inFile.next() == null) {
                highscore=0;
            } else {
                highscore=inFile.nextInt();
            }
            inFile.close();
            PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("highScore.txt")));
            if(highscore<score){
                outFile.println(score);
                highscore=score;
            }
            else {
                outFile.println(highscore);
            }
            outFile.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }
    public String getScore(){
        return String.valueOf(highscore);
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

