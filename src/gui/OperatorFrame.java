package gui;

import adduserclass.AbstractUser;

import javax.swing.*;

/**
 * @author ��
 */
public class OperatorFrame extends AbstractFrame {
    private JMenuBar menuBar = new JMenuBar();

    public OperatorFrame(AbstractUser user){
        super(user);
        addAllComponent();
        setTitle("��ӭ���뵵������Ա�������");
    }

    @Override
    public void addAllComponent(){
        setJMenuBar(menuBar);
        /**
         * �����˵����󲢼���˵���
         */

        JMenu[] mainMenu = {new JMenu(("��������")),new JMenu("������Ϣ����")};
        for(JMenu foreach:mainMenu) {
            menuBar.add(foreach);
        }
        /**
         * ��ÿ���˵���������Ӳ˵���ָ������Ӳ˵�(�˴�����Ҫ�Ӳ˵�)
         * ��ȡ��ķ��䷽����bidding
         */
        Class c = super.getUser().getClass();
        /**
         * ���ӷָ���:addSeparator();
         */
        try {
            makeItem("�ϴ��ļ�",mainMenu[0],c.getMethod("uploadFile",JPanel.class),0);
            mainMenu[0].addSeparator();
            makeItem("�����ļ�",mainMenu[0],c.getMethod("downloadFile",JPanel.class),1);
            mainMenu[0].addSeparator();
            makeItem("��ʾ�ļ��б�",mainMenu[0],c.getMethod("showFileList",JPanel.class),2);
            makeItem("�޸ĸ�����Ϣ",mainMenu[1],c.getMethod("changeSelfInfo", JPanel.class),0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

    public static void main(String[] args) {

    }
}
