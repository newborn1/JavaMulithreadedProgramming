package main;

import filesystem.*;
import clientapi.Client;

import javax.swing.*;
import java.awt.*;

/**
 * 作为主程序驱动程序
 * 
 * @author 郑伟鑫
 * @data 2021/11/19
 */
public class ClientTest {
	public static void main(String[] args) {
		/**
 		* TODO 为什么这里不能放在run函数里面
 		*/
		// declare client application
		Client application;

		// if no command line args
		if ( args.length == 0 ) {
			// connect to localhost
			application = new Client( "127.0.0.1" );
		} else {
			// use args to connect
			application = new Client( args[ 0 ] );
		}

		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

		/**
		 * run GUI programmer
		 */
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try{
					FileSystem.showMainUserSurface(application);
				}catch (Exception e){
					e.printStackTrace();
				}

			}
		});
		// run client application
		application.runClient();

	}

}
