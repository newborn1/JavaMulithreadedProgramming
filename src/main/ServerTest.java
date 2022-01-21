package main;

import javax.swing.*;
import serverapi.Server;

/**
 * @author 鑫
 */
public class ServerTest {
    public static void main(String[] args)
    {
        //创建服务端
        Server application = new Server();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //运行服务端的小程序（application）
        application.runServer();
    }
}
