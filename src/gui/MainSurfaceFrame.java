package gui;

import adduserclass.AbstractUser;
import dataprocessing.DataProcessing;

import javax.swing.*;
import javax.swing.border.Border;
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
        setTitle("系统登录");

        /**
         * 设置组件
         */

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
        /*,JLabel.RIGHT,BorderLayout.NORTH*/

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
//        add(panel,BorderLayout.CENTER);
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

    public static void main(String[] args) {
        MainSurfaceFrame surfaceFrame = new MainSurfaceFrame();
        surfaceFrame.addAllComponent();
        surfaceFrame.setVisible(true);
    }
}


