package gui;

import adduserclass.AbstractUser;
import clientapi.Client;
import dataprocessing.DataProcessing;
import filesystem.FileSystem;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


/**
 * @author 鑫
 * @date 202、12、03
 */
public class MainSurfaceFrame extends JFrame {
    private int width;
    private int height;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public MainSurfaceFrame(Client client){
        /**
         * 设置开始菜单的界面长、宽、位置和图标
         */
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        width = screenSize.width/3;
        height = screenSize.height/2;
        /**
         * 或者
         * setSize(width,height);
         * setLocatoionByPlatform(true);
         */
        setBounds(width,height/2,width,height);
        /**
         * TODO 图标无法显示
         */
        Image img = new ImageIcon("icon.gif").getImage();
        setIconImage(img);
        /**
         * 设置框架的标题
         */
        setTitle("系统登录");

        /**
         * 设置组件
         */
        addAllComponent();
        setVisible(true);

        loginButton.addActionListener(actionEvent -> {

            String name = "";
            String password = "";
            AbstractUser u = null;
            name = userNameTextField.getText();
            password = new String(passwordField.getPassword());

            u = FileSystem.verifyUser(name,password);
            if(u == null){
                return;
            }
            setVisible(false);
            u.setClient(client);
            u.showMenu();

        });
    }

    public void addAllComponent(){
        final int columnsSize = 20;
        /**
         * 注意要new BorderLayout()，否者默认流布局
         */
        JPanel panel = new JPanel(new GridLayout(0,1));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        userNameTextField = new JTextField("",columnsSize);
        passwordField = new JPasswordField("",columnsSize);
        /**
         * 创建标签组件,并将组件加入panel面板中,注意标签与主页要靠近
         */

        JLabel userNameLabel = new JLabel("用户名:");

        panel1.add(userNameLabel);
        panel1.add(userNameTextField);
        JLabel passwordLabel = new JLabel("    密码:");
        panel2.add(passwordLabel);
        panel2.add(passwordField);

        loginButton = new JButton("登录");
        panel3.add(loginButton);

        panel.add(new JPanel());
        panel.add(panel1);
        panel.add(panel2);
        panel.add(panel3);
        panel.add(new JPanel());
        add(panel);
        pack();

    }

    public static void main(String[] args) {
        MainSurfaceFrame surfaceFrame = new MainSurfaceFrame(null);
        surfaceFrame.addAllComponent();
        surfaceFrame.setVisible(true);
    }
}
