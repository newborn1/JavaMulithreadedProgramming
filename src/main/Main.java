package main;

import filesystem.*;

import java.awt.*;

/**
 * ��Ϊ��������������
 * 
 * @author ֣ΰ��
 * @data 2021/11/19
 */
public class Main1 {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/**
		 * run GUI programmer
		 */
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try{
					FileSystem.showMainUserSurface();
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});
	}

}
