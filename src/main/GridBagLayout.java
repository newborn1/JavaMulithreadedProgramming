package main;

import javax.swing.*;
import java.awt.*;

/**
 * @author öÎ
 */
public class GridBagLayout {
    public static void main(String[] args) {
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        JFrame frame = new JFrame();
        frame.setBounds(10,20,400,400);
        JPanel panel = new JPanel();
        panel.setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 100;
        constraints.weighty = 100;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 2;
//        constraints.fill = GridBagConstraints.CENTER;
//        constraints.anchor = GridBagConstraints.CENTER;
        JButton button1 = new JButton("button1");
        panel.add(button1,constraints);
        frame.add(panel);
        JButton button2 = new JButton("button2");
        panel.add(button2,constraints);
        frame.setVisible(true);
    }
}
