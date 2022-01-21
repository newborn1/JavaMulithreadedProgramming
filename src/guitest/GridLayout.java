package guitest;

import javax.swing.*;

/**
 * @author öÎ
 */
public class GridLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(20,20,400,400);
        JPanel panel = new JPanel(new java.awt.GridLayout(0,1));
//        frame.setLayout(new java.awt.GridLayout(3,1));
        JButton button1 = new JButton("button1");
        JButton button2 = new JButton("button2");
        panel.add(button1);
//        frame.add(button1,2);
        panel.add(button2);
        frame.add(panel);
        frame.setVisible(true);
    }
}
