package clientapi;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @author 鑫
 * enterField:enters information from user
 * displayArea:display information to user
 * output:output stream to server
 * input:input stream from server
 * message:message from server
 * chatServer:host server for this application
 * client:socket to communicate with server
 * downloadFilepath:下载的文件路径
 * uploadFilepath:上传的文件路径
 */
public class Client extends JFrame
{
    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String chatServer;
    private Socket client;
    private String downloadFilepath;
    private String uploadFilepath;

    /**
     * initialize chatServer and set up GUI
     * host:set server to which this client connects
     */
    public Client( String host )
    {
        super( "Client" );

        chatServer = host;

        // create enterField
        enterField = new JTextField();
        enterField.setEditable( false );
        enterField.addActionListener(
                new ActionListener()
                {
                    // send message to server
                    @Override
                    public void actionPerformed(ActionEvent event )
                    {
                        sendData( event.getActionCommand());
                        enterField.setText( "" );
                    } // end method actionPerformed
                } // end anonymous inner class
        ); // end call to addActionListener

        add( enterField, BorderLayout.NORTH );

        // create displayArea
        displayArea = new JTextArea();
        add( new JScrollPane( displayArea ), BorderLayout.CENTER );

        // set size of window
        setSize( 300, 150 );
        // show window
        setVisible( true );
    } // end Client constructor

    /**
     * connect to server and process messages from server
     */
    public void runClient()
    {
        try // connect to server, get streams, process connection
        {
            connectToServer(); // create a Socket to make connection
            getStreams(); // get the input and output streams
            /**
             * TODO 这里会等待服务端发送消息
             */
            processConnection(); // process connection
        } // end try
        catch ( EOFException eofException )
        {
            displayMessage( "\nClient terminated connection" );
        } // end catch
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        } // end catch
        finally
        {
            closeConnection(); // close connection
        } // end finally
    } // end method runClient

    /**
     * connect to server
     */
    private void connectToServer() throws IOException
    {
        displayMessage( "Attempting connection\n" );

        // create Socket to make connection to server
        client = new Socket( InetAddress.getByName( chatServer ), 12345 );

        // display connection information
        displayMessage( "Connected to: " +
                client.getInetAddress().getHostName() );
    } // end method connectToServer

    /**
     * get streams to send and receive data
     * @throws IOException
     */
    private void getStreams() throws IOException
    {
        // set up output stream for objects
        output = new ObjectOutputStream( client.getOutputStream());

        output.flush(); // flush output buffer to send header information

        // set up input stream for objects
        input = new ObjectInputStream( client.getInputStream() );

        displayMessage( "\nGot I/O streams\n" );
    } // end method getStreams

    /** process connection with server
     *
     * @throws IOException
     */
    public void processConnection() throws IOException
    {
        // enable enterField so client user can send messages
        setTextFieldEditable( true );

        do // process messages sent from server
        {
            try // read message and display it
            {
                /**
                 * TODO 我加入的测试程序代码
                 */
                // read new message
                message = ( String ) input.readObject();
                // display message
                displayMessage( "\n" + message );
                if(message.equals("SERVER>>> RESPONSE_DOWNLOAD")){
                    File file = new File(downloadFilepath);
                    /**
                     * TODO 改为网络传输：即将下载时改为从服务端读取,并且需要把这部分代码放在server，这里要改为从server获得,原来的也需要改
                     * 文件类与文件内容不一样
                     */
                    try {
                        if (!file.createNewFile()) {
                            JOptionPane.showConfirmDialog(null, "该文件已下载到本地，请勿重复下载!", "警告", JOptionPane.OK_CANCEL_OPTION);
                            System.out.println("该文件已下载到本地，请勿重复下载!");
                            input.readLong();
                            return;
                        } else {
                            /**
                             * TODO need to see
                             */
                            try (FileOutputStream out = new FileOutputStream(file)) {
                                byte[] bytes=new byte[1024];
                                int len=0;
                                int sizeLength=0;
                                long fileLength= input.readLong();
                                System.out.println(fileLength);
                                while((len=input.read(bytes,0,bytes.length))>0)
                                {
                                    System.out.write(bytes,0,len);
                                    out.write(bytes,0,len);
                                    out.flush();
                                    sizeLength+=len;
                                    if(sizeLength==fileLength)
                                    {
                                        break;
                                    }
                                }
                            }
                        }
                    } catch (IOException ioE) {
                        JOptionPane.showConfirmDialog(null, "下载失败!", "警告", JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("下载失败!");
                        ioE.printStackTrace();
                        return;
                    }
                    JOptionPane.showConfirmDialog(null, "下载成功!", "提示", JOptionPane.OK_CANCEL_OPTION);
                    System.out.println("下载成功");
                }
            } // end try
            catch ( ClassNotFoundException classNotFoundException )
            {
                displayMessage( "\nUnknown object type received" );
            } // end catch

        } while ( !message.equals( "SERVER>>> TERMINATE" ) );
    } // end method processConnection

    /**
     * close streams and socket
     */
    private void closeConnection()
    {
        displayMessage( "\nClosing connection" );
        // disable enterField
        setTextFieldEditable( false );

        try
        {
            output.close(); // close output stream
            input.close(); // close input stream
            client.close(); // close socket
        } // end try
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        } // end catch
    } // end method closeConnection

    public void sendFile(String filepath) throws IOException,ClassNotFoundException{
        uploadFilepath = filepath;
        sendData("UPLOAD");
        /**
         * 获得文件名称
         */
        String[] filenames = filepath.split("\\\\");
        /*传输文件名*/
        output.writeObject(filenames[filenames.length - 1]);
        File file = new File(uploadFilepath);
        FileInputStream fileInputStream = new FileInputStream(file);
        /*传输文件*/
        output.writeLong(file.length());
        System.out.println("开始传输文件");
        //要和接收的一致
        byte[] bytes=new byte[1024];
        int len=0;
        while((len=fileInputStream.read(bytes))!=-1)
        {
            output.write(bytes,0,len);
            output.flush();
        }
        System.out.println("文件传输结束");
        //只需要一次性flush吗
        output.flush();
    }

    public void getFile(String filename,String filepath) throws IOException {
        this.downloadFilepath = filepath;
        sendData("DOWNLOAD");
        output.writeObject(filename);
        output.flush();
    }

    /**
     * send message to server
     */
    private void sendData(String message)
    {
        try // send object to server
        {
            output.writeObject( "CLIENT>>> " + message );
            output.flush(); // flush data to output
            displayMessage( "\nCLIENT>>> " + message );
        } // end try
        catch ( IOException ioException )
        {
            displayArea.append( "\nError writing object" );
        } // end catch
    } // end method sendData

    /**
     * manipulates displayArea in the event-dispatch thread
     */
    private void displayMessage( final String messageToDisplay )
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run() // updates displayArea
                    {
                        displayArea.append( messageToDisplay );
                    } // end method run
                }  // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

    /**
     * manipulates enterField in the event-dispatch thread
     */
    private void setTextFieldEditable( final boolean editable )
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run() // sets enterField's editability
                    {
                        enterField.setEditable( editable );
                    } // end method run
                } // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method setTextFieldEditable
} // end class Client
