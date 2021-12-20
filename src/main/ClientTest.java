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
		// TODO Auto-generated method stub
/**
 * TODO Ϊʲô���ﲻ�ܷ���run��������
 */
		Client application; // declare client application

		// if no command line args
		if ( args.length == 0 ) {
			application = new Client( "127.0.0.1" ); // connect to localhost
		} else {
			application = new Client( args[ 0 ] ); // use args to connect
		}

		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		application.runClient(); // run client application

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


	}

}
