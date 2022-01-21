package guitest;

import javax.swing.*;
import java.awt.*;

/**
 * @author öÎ
 */
public class SetBounds {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400,400);
        frame.setLocationByPlatform(true);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        JButton button1 = new JButton("button1");
//        Toolkit toolkit = panel.getToolkit();
//        Dimension dimension = toolkit.getScreenSize();
        Dimension dimension = frame.getSize();
        int width = dimension.width/10;
//        int width = panel.getWidth();
        int height = dimension.width/10;
        System.out.println(dimension);
//        button1.setBounds(dimension.width/5,dimension.height/5,50,70);
//        System.out.println(width);
        button1.setBounds(width,height,80,30);
//        JButton button2 = new JButton("button2");
        panel.add(button1);
        frame.add(panel);
        frame.setVisible(true);
    }
}
