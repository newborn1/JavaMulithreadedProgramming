package gui;

import adduserclass.AbstractUser;
import adduserclass.Operator;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author 鑫
 * 每个菜单项目响应对象均和用户类和对应的函数bidding,不在AbstractUser等里定义的是为了分离功能
 */
public class ActionMenuItemFrame extends JFrame {
    private int width;
    private  int height;

    private AbstractUser user;
//    private Method method;
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    public ActionMenuItemFrame(AbstractUser user,Method method,String title,int nowTabIndex){
        this.user = user;
//        this.method = method;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        setTitle(title);

        width = screenSize.width/3;
        height = screenSize.width/3;
        setSize(width,height);
        setLocationByPlatform(true);

        addAllComponent(nowTabIndex);

        this.add(tabbedPane);
        tabbedPane.setVisible(true);
        setVisible(true);
    }

    private void addAllComponent(int nowIndex){
        //管理员特有的功能要分开
        try {
            if (getTitle() == "用户管理") {
                //增加Tab
                makeTab("修改用户信息",user.getClass().getMethod("changeUserInfo", JPanel.class));
                makeTab("删除用户", user.getClass().getMethod("delAbstractUser", JPanel.class));
                makeTab("增加用户", user.getClass().getMethod("addAbstractUser", JPanel.class));
                makeTab("显示所有用户", user.getClass().getMethod("listAbstractUser", JPanel.class));

            } else if (getTitle() == "档案管理") {
                if (user instanceof Operator) {
                    makeTab("上传文件", user.getClass().getMethod("uploadFile", JPanel.class));
                }
                makeTab("下载文件", user.getClass().getMethod("downloadFile", JPanel.class));
                makeTab("显示文件列表", user.getClass().getMethod("showFileList", JPanel.class));
            } else {
                makeTab("修改个人信息", user.getClass().getMethod("changeSelfInfo" , JPanel.class));
            }
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
        /**
         * 实时更新当前标签页的状态,删除重新加载,注意新的要add进去
         */
        tabbedPane.setSelectedIndex(nowIndex);
        tabbedPane.addChangeListener(e -> {
            int newIndex = tabbedPane.getSelectedIndex();
            this.remove(tabbedPane);
            tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            addAllComponent(newIndex);
//            tabbedPane.setSelectedIndex(nowIndex);
            this.add(tabbedPane);
            tabbedPane.setVisible(true);
        });

    }

    /**
     * method和类的重叠了
     * @param text
     * @param method
     */
    private void makeTab(String text,Method method){
        JPanel panel = new JPanel();
        //画panel
        try {
                method.invoke(user,panel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        tabbedPane.addTab(text,panel);
    }
}
