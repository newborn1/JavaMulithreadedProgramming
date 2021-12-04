package gui;

import adduserclass.AbstractUser;
import dataprocessing.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public MainSurfaceFrame(){
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
        setTitle("��ӭ���뵵��ϵͳ��¼����");

        /**
         * �������
         */

    }

    public void addAllComponent(){
        final int columnsSize = 20;
        /**
         * ע��Ҫnew BorderLayout()������Ĭ��������
         */
        JPanel panel = new JPanel();
        userNameTextField = new JTextField("",columnsSize);
        passwordField = new JPasswordField("",columnsSize);
        /**
         * ������ǩ���,�����������panel�����,ע���ǩ����ҳҪ����
         */
        /*,JLabel.RIGHT,BorderLayout.NORTH*/
        JLabel userNameLabel = new JLabel("�û���:");
        panel.add(userNameLabel);
        panel.add(userNameTextField);
        JLabel passwordLabel = new JLabel("����:");
        panel.add(passwordLabel);
        panel.add(passwordField);

        loginButton = new JButton("��¼");
        panel.add(loginButton);
        add(panel);
        /**
         * ���ֱ߿�
         */

        loginButton.addActionListener(new ButtonActionOperator());
        /**
         * �˳������ѹر�ԭ����
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
                        JOptionPane.showConfirmDialog(loginButton,"�����ڸ��û������������롣","����",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("�����ڸ��û���");
                        return;
                    }

                    u = DataProcessing.searchUser(name, password);

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


        }
    }
}


