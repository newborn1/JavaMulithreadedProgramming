package guitest;

import javax.swing.*;
import java.awt.*;

/**
 * @author ��
 */
public class View extends JPanel {
    /**
     * ��panel�ϻ�
     * @param panel
     */
    public View(JPanel panel){
        Label nameLabel = new Label("�û���");
        JComboBox nameBox = new JComboBox();
        nameBox.addItem(null);
        nameBox.addItem(this.getName());
        panel.add(nameLabel);
        panel.add(nameBox);
        Label passwordLabel = new Label("����");
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel);
        panel.add(passwordField);
        Label roleLabel = new Label("���");
        JComboBox roleBox = new JComboBox();
        roleBox.addItem(null);
//        roleBox.addItem(this.getRole());
        panel.add(roleLabel);
        panel.add(roleBox);
    }
}
