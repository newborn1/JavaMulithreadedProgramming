package gui;

import adduserclass.AbstractUser;
import adduserclass.Operator;

import javax.swing.*;

/**
 * @author 鑫
 * menuBar 框架上层的菜单栏
 */
public class BrowserFrame extends AbstractFrame {
    private JMenuBar menuBar = new JMenuBar();


    public BrowserFrame(AbstractUser user){
       super(user);
       addAllComponent();
       setTitle("欢迎进入档案浏览员管理界面");
    }

    @Override
    public void addAllComponent(){
        setJMenuBar(menuBar);
        /**
         * 建立菜单对象并加入菜单栏
         */

        JMenu[] mainMenu = {new JMenu(("档案管理")),new JMenu("个人信息管理")};
        for(JMenu foreach:mainMenu) {
            menuBar.add(foreach);
        }
        /**
         * 向每个菜单对象中添加菜单项，分隔符和子菜单(此处不需要子菜单)
         * 获取类的放射方法来bidding
         */
        Class c = super.getUser().getClass();
        try {
            int i = 0;
            if(getUser() instanceof Operator){
                makeItem("上传文件",mainMenu[0],c.getMethod("uploadFile",JPanel.class),i++);
                mainMenu[0].addSeparator();
            }
            makeItem("下载文件",mainMenu[0],c.getMethod("downloadFile",JPanel.class),i++);
            mainMenu[0].addSeparator();
            makeItem("显示文件列表",mainMenu[0],c.getMethod("showFileList",JPanel.class),i);
            makeItem("修改个人信息",mainMenu[1],c.getMethod("changeSelfInfo",JPanel.class),0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

}