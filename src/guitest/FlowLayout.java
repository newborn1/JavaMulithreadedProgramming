package guitest;

import javax.swing.*;

/**
 * @author öÎ
 */
public class FlowLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.setLocationByPlatform(true);
        JPanel panel = new JPanel(new java.awt.GridLayout(0,1));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JButton button1 = new JButton("button1");
        JButton button2 = new JButton("button2");
        panel2.add(button1);
        panel2.add(button2);
        panel.add(new JPanel());
        panel.add(panel1);
        panel.add(panel2);
        frame.add(panel);
//        frame.pack();
        frame.setVisible(true);
    }
}
