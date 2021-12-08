package gui;

import adduserclass.AbstractUser;

import javax.swing.*;

/**
 * @author 鑫
 * menuBar 框架上层的菜单栏
 */
public class AdministratorFrame extends AbstractFrame {
    private JMenuBar menuBar = new JMenuBar();


    public AdministratorFrame(AbstractUser user){
        super(user);
        setTitle("欢迎进入档案操作员管理界面");
        addAllComponent();
    }

    @Override
    public void addAllComponent(){
        setJMenuBar(menuBar);
        /**
         * 建立菜单对象并加入菜单栏
         */

        JMenu[] mainMenu = {new JMenu("用户管理"), new JMenu(("档案管理")), new JMenu("个人信息管理")};
        for (JMenu foreach : mainMenu) {
            menuBar.add(foreach);
        }
        Class c = super.getUser().getClass();
        try {
            makeItem("修改用户信息",mainMenu[0],c.getMethod("changeUserInfo",JPanel.class),0);
            mainMenu[0].addSeparator();
            makeItem("删除用户",mainMenu[0],c.getMethod("delAbstractUser",JPanel.class),1);
            mainMenu[0].addSeparator();
            makeItem("增加用户",mainMenu[0],c.getMethod("addAbstractUser",JPanel.class),2);
            mainMenu[0].addSeparator();
            makeItem("显示所有用户",mainMenu[0],c.getMethod("listAbstractUser",JPanel.class),3);
            makeItem("下载文件",mainMenu[1],c.getMethod("downloadFile",JPanel.class),0);
            mainMenu[1].addSeparator();
            makeItem("显示文件列表",mainMenu[1],c.getMethod("showFileList",JPanel.class),1);
            makeItem("修改个人信息",mainMenu[2],c.getMethod("changeSelfInfo", String.class),0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
    /**
     * 使用内部类来简化代码
     */

}
