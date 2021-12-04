package gui;

import adduserclass.AbstractUser;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

/**
 * @author ��
 * ÿ���˵���Ŀ��Ӧ��������û���Ͷ�Ӧ�ĺ���bidding,����AbstractUser���ﶨ�����Ϊ�˷��빦��
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
        //����Tab
        makeTab();
    }

    private void makeTab(String next){
        JPanel panel = new JPanel();

        //���ú�����panel
        method.invoke(user,panel);
        add(text,panel);

        add(tabbedPane);
    }
}
