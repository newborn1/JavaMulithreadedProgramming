package netProgramming;

import java.io.IOException;
import java.net.URL;

/**
 * @author ��
 */
public class URLDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try{
           URL url = new URL("http://www.runoob.com/index.html?language=cn#j2se");
           System.out.println("URL Ϊ��" + url.toString());
           System.out.println("Э��Ϊ��" + url.getProtocol());
           System.out.println("��֤��Ϣ��" + url.getAuthority());
           System.out.println("�ļ��������������" + url.getFile());
           System.out.println("��������" + url.getHost());
           System.out.println("·����" + url.getPath());
           System.out.println("�˿ڣ�" + url.getPort());
           System.out.println("Ĭ�϶˿ڣ�" + url.getDefaultPort());
           System.out.println("���������" + url.getQuery());
           System.out.println("��λλ�ã�" + url.getRef());
        }catch(IOException e){
           e.printStackTrace();
        }
    }

}
