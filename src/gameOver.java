import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class gameOver extends JFrame {
    private JLayeredPane layeredPane = new JLayeredPane();
    private int highscore;
    private JButton exit,playag;
    public gameOver(){
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
            highscore=inFile.nextInt();
            if(highscore<score){
                PrintWriter outFile = new PrintWriter(new BufferedWriter(new FileWriter("highScore.txt")));
                outFile.println(score);
                outFile.close();
                highscore=score;
            }
            inFile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getScore(){
        return String.valueOf(highscore);
    }
    public void main(String[] args) {
        gameOver gg= new gameOver();
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

