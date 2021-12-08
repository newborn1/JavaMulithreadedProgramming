package gui;

import adduserclass.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * @author ��
 */
public abstract class AbstractFrame extends JFrame {
    private int width;
    private int height;
    private AbstractUser user = null;

    public AbstractUser getUser(){
        return user;
    }

    public AbstractFrame(AbstractUser user){
        this.user = user;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        width = screenSize.width/2;
        height = screenSize.height*2/3;
        setSize(width,height);
        setLocationByPlatform(true);
    }

    public abstract void addAllComponent();


    /**
     * ʹ���ڲ������򻯴���
     */
    public void makeItem(String text, JMenu menu, Method method,int nowTabIndex){
        /**
         * ���÷���bidding��ͬ�ĺ���
         */
        JMenuItem menuItem = new JMenuItem(text);
        menu.add(menuItem);
        /**
         * Ϊÿһ���˵���biddingһ������������
         */
        menuItem.addActionListener(actionEvent -> {
            /**����ActionMenuItemFrame���
            *�ܵ���������̵�������������Ϣ��ͬ��Ӧ
            */
            new ActionMenuItemFrame(user,method,menu.getText(),nowTabIndex);
            return;
        });
    }
}
