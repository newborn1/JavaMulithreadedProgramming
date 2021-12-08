package gui;

import adduserclass.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * @author 鑫
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
     * 使用内部类来简化代码
     */
    public void makeItem(String text, JMenu menu, Method method,int nowTabIndex){
        /**
         * 利用反射bidding不同的函数
         */
        JMenuItem menuItem = new JMenuItem(text);
        menu.add(menuItem);
        /**
         * 为每一个菜单项bidding一个动作监听器
         */
        menuItem.addActionListener(actionEvent -> {
            /**创建ActionMenuItemFrame框架
            *受到面向对象编程的启发：发送消息不同响应
            */
            new ActionMenuItemFrame(user,method,menu.getText(),nowTabIndex);
            return;
        });
    }
}
