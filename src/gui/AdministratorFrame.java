package gui;

import adduserclass.AbstractUser;

import javax.swing.*;

/**
 * @author ��
 * menuBar ����ϲ�Ĳ˵���
 */
public class AdministratorFrame extends AbstractFrame {
    private JMenuBar menuBar = new JMenuBar();


    public AdministratorFrame(AbstractUser user){
        super(user);
        setTitle("��ӭ���뵵������Ա�������");
        addAllComponent();
    }

    @Override
    public void addAllComponent(){
        setJMenuBar(menuBar);
        /**
         * �����˵����󲢼���˵���
         */

        JMenu[] mainMenu = {new JMenu("�û�����"), new JMenu(("��������")), new JMenu("������Ϣ����")};
        for (JMenu foreach : mainMenu) {
            menuBar.add(foreach);
        }
        Class c = super.getUser().getClass();
        try {
            makeItem("�޸��û���Ϣ",mainMenu[0],c.getMethod("changeUserInfo",JPanel.class),0);
            mainMenu[0].addSeparator();
            makeItem("ɾ���û�",mainMenu[0],c.getMethod("delAbstractUser",JPanel.class),1);
            mainMenu[0].addSeparator();
            makeItem("�����û�",mainMenu[0],c.getMethod("addAbstractUser",JPanel.class),2);
            mainMenu[0].addSeparator();
            makeItem("��ʾ�����û�",mainMenu[0],c.getMethod("listAbstractUser",JPanel.class),3);
            makeItem("�����ļ�",mainMenu[1],c.getMethod("downloadFile",JPanel.class),0);
            mainMenu[1].addSeparator();
            makeItem("��ʾ�ļ��б�",mainMenu[1],c.getMethod("showFileList",JPanel.class),1);
            makeItem("�޸ĸ�����Ϣ",mainMenu[2],c.getMethod("changeSelfInfo", String.class),0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }
    /**
     * ʹ���ڲ������򻯴���
     */

}
