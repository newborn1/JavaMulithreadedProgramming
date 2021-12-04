package gui;

import adduserclass.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ��
 * menuBar ����ϲ�Ĳ˵���
 */
public class MainAdministratorFrame extends JFrame {
    private int width;
    private int height;
//    private JMenu menu = null;
    private JMenuBar menuBar = new JMenuBar();
    private AbstractUser user = null;


    public MainAdministratorFrame(AbstractUser user){
        this.user = user;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        width = screenSize.width/2;
        height = screenSize.height*2/3;
        setSize(width,height);
        setLocationByPlatform(true);
        setTitle("��ӭ���뵵������Ա�������");
    }

    public void addAllComponent(){
        setJMenuBar(menuBar);
        /**
         * �����˵����󲢼���˵���
         */

        JMenu[] mainMenu = {new JMenu("�û�����"),new JMenu(("��������")),new JMenu("������Ϣ����")};
        for(JMenu foreach:mainMenu) {
            menuBar.add(foreach);
        }
        /**
         * ��ÿ���˵���������Ӳ˵���ָ������Ӳ˵�(�˴�����Ҫ�Ӳ˵�)
         * ��ȡ��ķ��䷽����bidding
         */
        Class c = user.getClass();
        try {
            makeItem("�޸��û���Ϣ",mainMenu[0],c.getMethod("changeUserInfo"));
            makeItem("ɾ���û�",mainMenu[0],c.getMethod("delAbstractUser"));
            makeItem("�����û�",mainMenu[0],c.getMethod("addAbstractUser"));
            makeItem("��ʾ�����û�",mainMenu[0],c.getMethod("listAbstractUser"));
            makeItem("�ϴ��ļ�",mainMenu[1],c.getMethod("uploadFile"));
            makeItem("�����ļ�",mainMenu[1],c.getMethod("downloadFile"));
            makeItem("�޸ĸ�����Ϣ",mainMenu[2],c.getMethod("changeSelfInfo", String.class));
            makeItem("��ʾ�ļ��б�",mainMenu[2],c.getMethod("showFileList"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
    /**
     * ʹ���ڲ������򻯴���
     */
    public void makeItem(String text, JMenu menu,Method method){
        /**
         * ���÷���bidding��ͬ�ĺ���
         */
        JMenuItem menuItem = new JMenuItem(text);
        menu.add(menuItem);
        /**
         * ���ӷָ���
         */
        menu.addSeparator();
        /**
         * Ϊÿһ���˵���biddingһ������������
         */
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //����JTabbedPane�͸���ѡ�Tab����JTabbedPane�ڶ���
//                JTabbedPane pane = new JTabbedPane(JTabbedPane.TOP);
//                makeTab();
                //�ܵ���������̵�������������Ϣ��ͬ��Ӧ
                try {
                    method.invoke(user,null);
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
                return;
            }
        });
    }
}
