package gui;

import adduserclass.AbstractUser;

import javax.swing.*;

/**
 * @author 鑫
 */
public class OperatorFrame extends AbstractFrame {
    private JMenuBar menuBar = new JMenuBar();

    public OperatorFrame(AbstractUser user){
        super(user);
        addAllComponent();
        setTitle("欢迎进入档案操作员管理界面");
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
        /**
         * 增加分隔符:addSeparator();
         */
        try {
            makeItem("上传文件",mainMenu[0],c.getMethod("uploadFile",JPanel.class),0);
            mainMenu[0].addSeparator();
            makeItem("下载文件",mainMenu[0],c.getMethod("downloadFile",JPanel.class),1);
            mainMenu[0].addSeparator();
            makeItem("显示文件列表",mainMenu[0],c.getMethod("showFileList",JPanel.class),2);
            makeItem("修改个人信息",mainMenu[1],c.getMethod("changeSelfInfo", JPanel.class),0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {

    }
}
