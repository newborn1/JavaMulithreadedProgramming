package netProgramming;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author 鑫
 */
public class InetAddressDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try{
            InetAddress address1=InetAddress.getByName("www.baidu.com");
            System.out.println(address1.getHostName());
            System.out.println(address1.getHostAddress());
            System.out.println("**********");
            
            String ipstr="192.168.0.109";
            String[] iparray=ipstr.split("\\.");
            byte[] ip=new byte[4];
            for(int i=0;i<4;i++){
                ip[i]=(byte)(Integer.parseInt(iparray[i]));
            }
            InetAddress address2 = InetAddress.getByAddress(ip);  
            System.out.println("计算机名" + address2.getHostName());  
            System.out.println("IP地址" + address2.getHostAddress());
            System.out.println("**********");
            
            InetAddress address3=InetAddress.getLocalHost();
            System.out.println("计算机名："+address3.getHostName());
            System.out.println("IP地址："+address3.getHostAddress());
            byte[] bytes = address3.getAddress();
            System.out.println("字节数组形式的IP" + Arrays.toString(bytes));  
            System.out.println(address3);  

        }catch(UnknownHostException e){
            e.printStackTrace();
        }       
    }

}
