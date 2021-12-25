package gui;

import adduserclass.AbstractUser;
import clientapi.Client;
import dataprocessing.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


/**
 * @author ��
 * @date 202��12��03
 */
public class MainSurfaceFrame extends JFrame {
    private int width;
    private int height;
    private JTextField userNameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public MainSurfaceFrame(Client client){
        /**
         * ���ÿ�ʼ�˵��Ľ��泤����λ�ú�ͼ��
         */
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        width = screenSize.width/3;
        height = screenSize.height/2;
        /**
         * ����
         * setSize(width,height);
         * setLocatoionByPlatform(true);
         */
        setBounds(width,height/2,width,height);
        /**
         * TODO ͼ���޷���ʾ
         */
        Image img = new ImageIcon("icon.gif").getImage();
        setIconImage(img);
        /**
         * ���ÿ�ܵı���
         */
        setTitle("ϵͳ��¼");

        /**
         * �������
         */
        addAllComponent();
        setVisible(true);

        loginButton.addActionListener(actionEvent -> {
            String name = "";
            String password = "";
            AbstractUser u = null;
            name = userNameTextField.getText() ;
            password = new String(passwordField.getPassword());

            while (true) {
                try {
                    while (DataProcessing.searchUser(name) == null) {
                        JOptionPane.showConfirmDialog(loginButton,"�����ڸ��û������������롣","����",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("�����ڸ��û���");
                        return;
                    }

                    u = DataProcessing.searchUser(name, password);
                    u.setClient(client);

                    if (u == null) {
                        JOptionPane.showConfirmDialog(loginButton,"�������!","����",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("�������!");
                        return;
                    }else{
                        break;
                    }
                } catch (SQLException e) {
                    if ("Not Connected to Database".equals(e.getMessage())) {
                        DataProcessing.init();
                    }
                    JOptionPane.showConfirmDialog(loginButton,"����������!","����",JOptionPane.OK_CANCEL_OPTION);
                    System.out.println("����������!");
                    return;
                }
            }
            u.showMenu();

        });
    }

    public void addAllComponent(){
        final int columnsSize = 20;
        /**
         * ע��Ҫnew BorderLayout()������Ĭ��������
         */
        JPanel panel = new JPanel(new GridLayout(0,1));
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        userNameTextField = new JTextField("",columnsSize);
        passwordField = new JPasswordField("",columnsSize);
        /**
         * ������ǩ���,�����������panel�����,ע���ǩ����ҳҪ����
         */

        JLabel userNameLabel = new JLabel("�û���:");

        panel1.add(userNameLabel);
        panel1.add(userNameTextField);
        JLabel passwordLabel = new JLabel("    ����:");
        panel2.add(passwordLabel);
        panel2.add(passwordField);

        loginButton = new JButton("��¼");
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
         * ���ֱ߿�
         */

        /**
         * �˳������ѹر�ԭ����
         */
    }


    public static void main(String[] args) {
        MainSurfaceFrame surfaceFrame = new MainSurfaceFrame(null);
        surfaceFrame.addAllComponent();
        surfaceFrame.setVisible(true);
    }
}


