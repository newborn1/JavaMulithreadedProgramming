package adduserclass;
import gui.BrowserFrame;

import javax.swing.*;

/**
 * feature ����ߣ���ĳЩ������Ϣ�����޸ģ�����ļ����̳��˳����û���
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Browser extends AbstractUser {

	public Browser(String name, String password, String role) {
		//���õĹ�����ֻ���Ǳ���Ĺ���������γ�ʼ������ģ���super
		super(name, password, role );
	}
	
	@Override
	public void showMenu() {
		new BrowserFrame(this);

		System.out.print("���뵵�����Ա����");

		return;
	}

}
