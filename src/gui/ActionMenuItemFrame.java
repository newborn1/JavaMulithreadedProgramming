package gui;

import adduserclass.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * @author 鑫
 * 每个菜单项目响应对象均和用户类和对应的函数bidding,不在AbstractUser等里定义的是为了分离功能
 */
public class ActionMenuItemFrame extends JFrame {
    private int width;
    private  int height;

    private AbstractUser user;
    private Method method;
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    public ActionMenuItemFrame(AbstractUser user,Method method){
        this.user = user;
        this.method = method;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        width = screenSize.width/2;
        height = screenSize.width/2;
        setSize(width,height);
        setLocationByPlatform(true);

        addAllComponent();

        setVisible(true);
    }

    private void addAllComponent(){
        //增加Tab
        makeTab();
    }

    private void makeTab(String next){
        JPanel panel = new JPanel();

        //调用函数画panel
        method.invoke(user,panel);
        add(text,panel);

        add(tabbedPane);
    }
}
