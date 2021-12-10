package main;

import javax.swing.*;
import java.awt.*;

/**
 * @author öÎ
 */
public class BorderLayout {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setBounds(10,20,400,400);
        JPanel panel = new JPanel(new java.awt.BorderLayout());
//        JPanel panel = new  JPanel();
        JButton button1 = new JButton("button1");
//        panel.add(button1, java.awt.BorderLayout.EAST);
        panel.add(button1);
//        button1.setContentAreaFilled(false);
//        button1.setSize(100,100);
        frame.add(panel, java.awt.BorderLayout.CENTER);
        JButton button2 = new JButton("button2");
        frame.add(button2, java.awt.BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
