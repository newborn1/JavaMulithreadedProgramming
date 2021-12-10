package gui;

import adduserclass.AbstractUser;
import adduserclass.Operator;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author ��
 * ÿ���˵���Ŀ��Ӧ��������û���Ͷ�Ӧ�ĺ���bidding,����AbstractUser���ﶨ�����Ϊ�˷��빦��
 */
public class ActionMenuItemFrame extends JFrame {
    private int width;
    private  int height;

    private AbstractUser user;
    private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    public ActionMenuItemFrame(AbstractUser user,Method method,String title,int nowTabIndex){
        this.user = user;

        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();

        setTitle(title);

        width = screenSize.width/3;
        height = screenSize.width/3;
        setSize(width,height);
        setLocationByPlatform(true);

        addAllComponent(nowTabIndex);

        this.add(tabbedPane);
        pack();
        tabbedPane.setVisible(true);
        setVisible(true);
    }

    private void addAllComponent(int nowIndex){
        //����Ա���еĹ���Ҫ�ֿ�
        try {
            if (getTitle() == "�û�����") {
                //����Tab
                makeTab("�޸��û���Ϣ",user.getClass().getMethod("changeUserInfo", JPanel.class));
                makeTab("ɾ���û�", user.getClass().getMethod("delAbstractUser", JPanel.class));
                makeTab("�����û�", user.getClass().getMethod("addAbstractUser", JPanel.class));
                makeTab("��ʾ�����û�", user.getClass().getMethod("listAbstractUser", JPanel.class));

            } else if (getTitle() == "��������") {
                if (user instanceof Operator) {
                    makeTab("�ϴ��ļ�", user.getClass().getMethod("uploadFile", JPanel.class));
                }
                makeTab("�����ļ�", user.getClass().getMethod("downloadFile", JPanel.class));
                makeTab("��ʾ�ļ��б�", user.getClass().getMethod("showFileList", JPanel.class));
            } else {
                makeTab("�޸ĸ�����Ϣ", user.getClass().getMethod("changeSelfInfo" , JPanel.class));
            }
        }catch (NoSuchMethodException e){
            e.printStackTrace();
        }
        /**
         * ʵʱ���µ�ǰ��ǩҳ��״̬,ɾ�����¼���,ע���µ�Ҫadd��ȥ
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
     * method������ص���
     * @param text
     * @param method
     */
    private void makeTab(String text,Method method){
        JPanel panel = new JPanel();
        //��panel
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
