package game;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GUI{

  public static void main(String[] args) {

    JFrame startf = new JFrame("Abalone");
    startf.setSize(500, 500);
    startf.setLocationRelativeTo(null);
    final JTextArea textArea = new JTextArea(10, 40);
    startf.getContentPane().add(BorderLayout.CENTER, textArea);
    final JButton sbutton = new JButton("Start Game");
    startf.getContentPane().add(BorderLayout.SOUTH, sbutton);
    sbutton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            textArea.append("Button was clicked\n");
        }
    });

    startf.setVisible(true);

  }

}