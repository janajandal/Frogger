/*
FILE NAME:Win.Java
BY:Jana Jandal Alrifai, Catherine Sun
SUMMARY:a Jframe displayed when all 3 levels of Frogger are completed
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Win extends JFrame {
    private JLayeredPane layeredPane = new JLayeredPane();
    private ImageIcon backPic, titlePic;
    private JLabel back, title;
    private JButton exit,play;
    private Font font;
    private int w, h;
    //used GameOver as a template, no new comments here
    public Win() {
        super("Frogger");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backPic = new ImageIcon("back.png");
        w = 542;
        h = 600;

        layeredPane.setPreferredSize(new Dimension(w, h));

        back = new JLabel(backPic);
        back.setBounds(0, 0, w, h);
        layeredPane.add(back, JLayeredPane.DEFAULT_LAYER);

        gameFont();
        titlePic = new ImageIcon("win.PNG");
        title = new JLabel(titlePic);
        w = w/2 - titlePic.getIconWidth()/2;
        h = 140;
        title.setBounds(w, h, titlePic.getIconWidth(), titlePic.getIconHeight());
        layeredPane.add(title, JLayeredPane.MODAL_LAYER);

        play= new JButton("PLAY AGAIN");
        play.setFont(font);
        play.setForeground(Color.white);
        play.setBounds(w/2- 50, 380, 300, 30);
        play.setFocusPainted(false);
        play.setContentAreaFilled(false);
        play.setBorderPainted(false);
        play.addMouseListener(new HoverColour());
        play.addActionListener(new ClickStart());
        layeredPane.add(play, JLayeredPane.PALETTE_LAYER);
        exit= new JButton("EXIT");
        exit.setFont(font);
        exit.setForeground(Color.white);
        exit.setBounds(w/2 + 250, 380, 300, 30);
        exit.setFocusPainted(false);
        exit.setContentAreaFilled(false);
        exit.setBorderPainted(false);
        exit.addMouseListener(new HoverColour());
        exit.addActionListener(new ClickStart());
        layeredPane.add(exit, JLayeredPane.PALETTE_LAYER);



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

            if(evt.getSource()==exit){
                System.out.println("bye");
                System.exit(0);
            }
            else {

                Frogger frame= new Frogger();
                frame.start();
                setVisible(false);
            }
        }
    }


    class HoverColour implements MouseListener {
        public void mouseEntered(MouseEvent e) {
            if(e.getSource()==exit){
                exit.setForeground(Color.GREEN);
            }
            else{
                play.setForeground(Color.GREEN);
            }

        }

        public void mouseExited(MouseEvent e) {
            if(e.getSource()==exit){
                exit.setForeground(Color.WHITE);
            }
            else{
                play.setForeground(Color.WHITE);
            }


        }

        public void mousePressed(MouseEvent e){}

        public void mouseReleased(MouseEvent e){}

        public void mouseClicked(MouseEvent e) {}
    }

    public static void main(String[] arguments) {
        StartMenu menu = new StartMenu();
    }
}

