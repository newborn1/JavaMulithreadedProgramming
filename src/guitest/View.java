package guitest;

import javax.swing.*;
import java.awt.*;

/**
 * @author 鑫
 */
public class View extends JPanel {
    /**
     * 在panel上画
     * @param panel
     */
    public View(JPanel panel){
        Label nameLabel = new Label("用户名");
        JComboBox nameBox = new JComboBox();
        nameBox.addItem(null);
        nameBox.addItem(this.getName());
        panel.add(nameLabel);
        panel.add(nameBox);
        Label passwordLabel = new Label("密码");
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordLabel);
        panel.add(passwordField);
        Label roleLabel = new Label("身份");
        JComboBox roleBox = new JComboBox();
        roleBox.addItem(null);
//        roleBox.addItem(this.getRole());
        panel.add(roleLabel);
        panel.add(roleBox);
    }
}
