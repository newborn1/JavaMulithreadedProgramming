package gui;

import adduserclass.AbstractUser;
import dataprocessing.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public MainSurfaceFrame(){
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
        setTitle("欢迎进入档案系统登录界面");

        /**
         * 设置组件
         */

    }

    public void addAllComponent(){
        final int columnsSize = 20;
        /**
         * 注意要new BorderLayout()，否者默认流布局
         */
        JPanel panel = new JPanel();
        userNameTextField = new JTextField("",columnsSize);
        passwordField = new JPasswordField("",columnsSize);
        /**
         * 创建标签组件,并将组件加入panel面板中,注意标签与主页要靠近
         */
        /*,JLabel.RIGHT,BorderLayout.NORTH*/
        JLabel userNameLabel = new JLabel("用户名:");
        panel.add(userNameLabel);
        panel.add(userNameTextField);
        JLabel passwordLabel = new JLabel("密码:");
        panel.add(passwordLabel);
        panel.add(passwordField);

        loginButton = new JButton("登录");
        panel.add(loginButton);
        add(panel);
        /**
         * 布局边框
         */

        loginButton.addActionListener(new ButtonActionOperator());
        /**
         * 退出函数已关闭原窗口
         */
    }
    class ButtonActionOperator implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            String name = "";
            String password = "";
            AbstractUser u = null;
            name = userNameTextField.getText() ;
            password = new String(passwordField.getPassword());

            while (true) {
                try {
                    while (DataProcessing.searchUser(name) == null) {
                        JOptionPane.showConfirmDialog(loginButton,"不存在该用户！请重新输入。","警告",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("不存在该用户。");
                        return;
                    }

                    u = DataProcessing.searchUser(name, password);

                    if (u == null) {
                        JOptionPane.showConfirmDialog(loginButton,"密码错误!","警告",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("密码错误!");
                        return;
                    }else{
                        break;
                    }
                } catch (SQLException e) {
                    if ("Not Connected to Database".equals(e.getMessage())) {
                        DataProcessing.init();
                    }
                    JOptionPane.showConfirmDialog(loginButton,"请重新输入!","警告",JOptionPane.OK_CANCEL_OPTION);
                    System.out.println("请重新输入!");
                    return;
                }
            }

            u.showMenu();


        }
    }
}


