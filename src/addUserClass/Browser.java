package adduserclass;
import gui.BrowserFrame;

import javax.swing.*;

/**
 * feature 浏览者，对某些个人信息进行修改，浏览文件，继承了抽象用户类
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Browser extends AbstractUser {

	public Browser(String name, String password, String role) {
		//调用的构造器只能是本类的构造器。如何初始化父类的：用super
		super(name, password, role );
	}
	
	@Override
	public void showMenu() {
		new BrowserFrame(this);

		System.out.print("进入档案浏览员界面");

		return;
	}

}
