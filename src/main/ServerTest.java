package main;

import javax.swing.*;
import serverapi.Server;

/**
 * @author ��
 */
public class ServerTest {
    public static void main(String[] args)
    {
        //���������
        Server application = new Server();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //���з���˵�С����application��
        application.runServer();
    }
}
