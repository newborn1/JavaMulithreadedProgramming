package gui;

import adduserclass.AbstractUser;
import adduserclass.Operator;

import javax.swing.*;

/**
 * @author ��
 * menuBar ����ϲ�Ĳ˵���
 */
public class BrowserFrame extends AbstractFrame {
    private JMenuBar menuBar = new JMenuBar();


    public BrowserFrame(AbstractUser user){
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
        try {
            int i = 0;
            if(getUser() instanceof Operator){
                makeItem("�ϴ��ļ�",mainMenu[0],c.getMethod("uploadFile",JPanel.class),i++);
                mainMenu[0].addSeparator();
            }
            makeItem("�����ļ�",mainMenu[0],c.getMethod("downloadFile",JPanel.class),i++);
            mainMenu[0].addSeparator();
            makeItem("��ʾ�ļ��б�",mainMenu[0],c.getMethod("showFileList",JPanel.class),i);
            makeItem("�޸ĸ�����Ϣ",mainMenu[1],c.getMethod("changeSelfInfo",JPanel.class),0);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        setVisible(true);
    }

}