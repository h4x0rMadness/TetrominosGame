import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JLabel;

// The class that sets up the user interface
// for the Tetrominos game. You will not need to
// edit this file, but it might be worth reading through it.

public class Tetrominos {
    public static int speedRate(long startTime) {
        long elapseTime = System.currentTimeMillis() - startTime;
        int passingMinute = (int) (elapseTime / 10000);
        System.out.println("passingMinute: " + passingMinute);
        System.out.println("rate given: " + (passingMinute + 1));
        System.out.println("called Interval: " + 1000 / (passingMinute + 1));
        return passingMinute + 1;

    }
    private static void createAndShowGUI(long startTime) {
        //Create and set up the window.
        JFrame frame = new JFrame("Tetrominos!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Board board = new Board(20, 40);

        frame.getContentPane().add(board);

        //Display the window.
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);


        Timer timer = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                board.nextTurn();

            }

        });


        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                //System.out.println("key pressed " + e.getKeyCode());
                if(key == KeyEvent.VK_W) {
                    board.rotate();
                } else if (key == KeyEvent.VK_S) {
                    board.speedUp();
                } else if (key == KeyEvent.VK_A) {
                    board.slide(-1);
                } else if (key == KeyEvent.VK_D) {
                    board.slide(1);
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        timer.start();
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(startTime);
            }
        });

    }

}