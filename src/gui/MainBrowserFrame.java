package gui;

import adduserclass.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 鑫
 * menuBar 框架上层的菜单栏
 */
public class MainBrowserFrame extends JFrame {
    private int width;
    private int height;
    //    private JMenu menu = null;
    private JMenuBar menuBar = new JMenuBar();
    private AbstractUser user = null;


    public MainBrowserFrame(AbstractUser user){
        this.user = user;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        width = screenSize.width/2;
        height = screenSize.height*2/3;
        setSize(width,height);
        setLocationByPlatform(true);
        setTitle("欢迎进入档案操作员管理界面");
    }

    public void addAllComponent(){
        setJMenuBar(menuBar);
        /**
         * 建立菜单对象并加入菜单栏
         */

        JMenu[] mainMenu = {new JMenu("用户管理"),new JMenu(("档案管理")),new JMenu("个人信息管理")};
        for(JMenu foreach:mainMenu) {
            menuBar.add(foreach);
        }
        /**
         * 向每个菜单对象中添加菜单项，分隔符和子菜单(此处不需要子菜单)
         * 获取类的放射方法来bidding
         */
        Class c = user.getClass();
        try {
//            makeItem("上传文件",mainMenu[1],c.getMethod("uploadFile"));
            makeItem("下载文件",mainMenu[1],c.getMethod("downloadFile"));
            makeItem("修改个人信息",mainMenu[2],c.getMethod("changeSelfInfo", String.class));
            makeItem("显示文件列表",mainMenu[2],c.getMethod("showFileList"));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
    /**
     * 使用内部类来简化代码
     */
    public void makeItem(String text, JMenu menu,Method method){
        /**
         * 利用反射bidding不同的函数
         */
        JMenuItem menuItem = new JMenuItem(text);
        menu.add(menuItem);
        /**
         * 增加分隔符
         */
        menu.addSeparator();
        /**
         * 为每一个菜单项bidding一个动作监听器
         */
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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