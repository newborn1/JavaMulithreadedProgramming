package main;

import filesystem.*;

import java.awt.*;

/**
 * 作为主程序驱动程序
 * 
 * @author 郑伟鑫
 * @data 2021/11/19
 */
public class Main {
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
