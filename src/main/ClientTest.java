package main;

import filesystem.*;
import clientapi.Client;

import javax.swing.*;
import java.awt.*;

/**
 * ��Ϊ��������������
 * 
 * @author ֣ΰ��
 * @data 2021/11/19
 */
public class ClientTest {
	public static void main(String[] args) {
		/**
 		* TODO Ϊʲô���ﲻ�ܷ���run��������
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
