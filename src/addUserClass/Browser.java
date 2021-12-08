package adduserclass;

import gui.BrowserFrame;

import javax.swing.*;
import java.sql.SQLException;

/**
 * feature ����ߣ���ĳЩ������Ϣ�����޸ģ�����ļ����̳��˳����û���
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Browser extends AbstractUser {

	public Browser(String name,String password,String role) {
		//���õĹ�����ֻ���Ǳ���Ĺ���������γ�ʼ������ģ���super
		super(name, password, role);
	}
	
	@Override
	public void showMenu() {
		JFrame mainFrame = new BrowserFrame(this);
		mainFrame.setVisible(true);

		Integer selector = 0;

		final String[] allLine = {"************��ӭ���뵵�����Ա�˵�******************\n",
								  "\t\t\t1�������ļ�\n",
								  "\t\t\t2���ļ��б�\n",
								  "\t\t\t3���޸�����\n",
								  "\t\t\t4���˳�\n",
								  "***************************************************\n"
		};

		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);


		System.out.print("���������ֽ���ѡ��:");
//		selector = in.nextInt();
		switch(selector) {
			case 1:
				this.downloadFile(null);
				break;
			case 2:
				try{
					super.showFileList();
				}catch (SQLException sqe){
					System.out.println(sqe.getMessage());
					System.out.println("The problem has been solved.Please input the selector against.");
				}
				break;
			case 3:
				/**
				 * TODO �޸��ļ�
				 */
				try {
					changeSelfInfo(super.getPassword());
				} catch (SQLException e) {
					e.printStackTrace();
				}
				break;
			case 4:
				this.exitSystem();
				break;
			default:
				break;
		}

		return;
	}


}
