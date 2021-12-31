package serverapi;

import filesystem.FileSystem;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;

/**
 * @author ��
 */
public class Server extends JFrame
{
    /**
     * enterField:inputs message from user
     * displayArea:display information to user
     * output:output stream to client
     * server:server socket
     * connection:connection to client
     * counter:counter of number of connections
     */
    private JTextField enterField;
    private JTextArea displayArea;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private ServerSocket server;
    private Socket connection;
    private int counter = 1;
    private String uploadFilename;
    private String downloadFilename;

    /**
     * set up GUI
     */
    public Server()
    {
        super( "Server" );
        // create enterField
        enterField = new JTextField();
        enterField.setEditable( false );
        enterField.addActionListener(
                new ActionListener()
                {
                    // send message to client
                    @Override
                    public void actionPerformed(ActionEvent event )
                    {
                        sendData( event.getActionCommand() );
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
    } // end Server constructor

    /**
     * set up and run server
     */
    public void runServer()
    {
        try // set up server to receive connections; process connections
        {
            // create ServerSocket
            server = new ServerSocket( 12345, 100 );

            while ( true )
            {
                try
                {
                    waitForConnection(); // wait for a connection
                    getStreams(); // get input & output streams
                    processConnection(); // process connection
                } // end try
                catch ( EOFException eofException )
                {
                    displayMessage( "\nServer terminated connection" );
                } // end catch
                finally
                {
                    closeConnection(); //  close connection
                    counter++;
                } // end finally
            } // end while
        } // end try
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        } // end catch
    } // end method runServer

    /**
     * wait for connection to arrive, then display connection info
     * @throws IOException
     */
    private void waitForConnection() throws IOException
    {
        displayMessage( "Waiting for connection\n" );
        /**
         * TODO �������Ҫ�����ؿͻ��˵���Ϣ
         */
        // allow server to accept connection,һֱ�ȴ�ֱ�����յ��ͻ��˵�����
        connection = server.accept();
        displayMessage( "Connection " + counter + " received from: " +
                connection.getInetAddress().getHostName() );
    } // end method waitForConnection

    /**
     * get streams to send and receive data
     * TODO �����ͷ��˺ͷ���˵����������ϵ
     */
    private void getStreams() throws IOException
    {
        // set up output stream for objects����÷�����׽��ֵ����������ʱ����֮�����Ŀͷ��˵����룩
        output = new ObjectOutputStream( connection.getOutputStream() );
        /**
         * TODO ��������ͨ��flush()
         */
        output.flush(); // flush output buffer to send header information

        // set up input stream for objects����ÿͷ����׽��ֵ�������
        input = new ObjectInputStream( connection.getInputStream() );

        displayMessage( "\nGot I/O streams\n" );
    } // end method getStreams

    /**
     * process connection with client
     * @throws IOException
     */
    private void processConnection() throws IOException
    {
        String message = "Connection successful";
        // send connection successful message
        sendData( message );

        // enable enterField so server user can send messages
        setTextFieldEditable( true );

        do // process messages sent from client
        {
            try // read message and display it
            {
                // read new message
                message = ( String ) input.readObject();
                // display message
                displayMessage( "\n" + message );
                if(message.equals("CLIENT>>> UPLOAD" ) ){
                    uploadFilename = (String)input.readObject();
                    /**
                     * ����ļ���·������
                     */
                    try {
                        Files.createFile(Paths.get(FileSystem.REMOTE_PATH));
                    } catch (IOException ioe) {}
                    File files = new File(FileSystem.REMOTE_PATH+"\\"+ uploadFilename);
                    try {
                        if (!files.createNewFile()) {
                            JOptionPane.showConfirmDialog(null,"���ļ����ϴ��������ظ��ϴ���","����",JOptionPane.OK_CANCEL_OPTION);
                            System.out.println("���ļ����ϴ��������ظ��ϴ�!");
                            return;
                        }
                    } catch (IOException ioE) {
                        JOptionPane.showConfirmDialog(null,"�ϴ�ʧ�ܣ�","����",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("�ϴ�ʧ��!");
                        ioE.printStackTrace();
                        return;
                    }
                    /**
                     * TODO ��out.writeObject��Ϊ�������������
                     */
                    try (FileOutputStream out = new FileOutputStream(files)) {
                        byte[] bytes=new byte[1024];
                        int len=0;
                        int sizeLength=0;
                        long fileLength= input.readLong();
                        System.out.println(fileLength);
                        while((len=input.read(bytes,0,bytes.length))>0)
                        {
                            out.write(bytes,0,len);
                            out.flush();
                            sizeLength+=len;
                            if(sizeLength==fileLength)
                            {
                                break;
                            }
                        }
                    } catch (IOException exception) {
                        System.out.println("�ļ��ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�");
                        JOptionPane.showConfirmDialog(null,"�ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�","����",JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
                    System.out.println("��ʼ�ϴ�");
                }else if("CLIENT>>> DOWNLOAD".equals(message)){
                    downloadFilename = (String) input.readObject();
                    sendData("RESPONSE_DOWNLOAD");
                    File files = new File(FileSystem.REMOTE_PATH+"\\"+downloadFilename);
                    /*Ҫ��Ӧ*/
                    output.writeLong(files.length());
                    output.flush();
                    System.out.println(files.length());
                    /*������д��*/
                    try(FileInputStream fileInputStream = new FileInputStream(files)){
                        System.out.println("��ʼ�����ļ�");
                        //Ҫ�ͽ��յ�һ��
                        byte[] bytes=new byte[1024];
                        int len=0;
                        while((len=fileInputStream.read(bytes))!=-1)
                        {
                            System.out.write(bytes,0,len);
                            output.write(bytes,0,len);
                            output.flush();
                        }
                        output.flush();
                        System.out.println("�Ѿ��������ļ�");
                    } catch (IOException e) {}
                }
            } // end try
            catch ( ClassNotFoundException classNotFoundException )
            {
                displayMessage( "\nUnknown object type received" );
            } // end catch
        } while ( !message.equals( "CLIENT>>> TERMINATE" ) );
    } // end method processConnection

    public void getFile(String filename) throws IOException{
        try {
            Files.createFile(Paths.get(FileSystem.REMOTE_PATH));
        } catch (IOException e) {}
        File files = new File(FileSystem.REMOTE_PATH + "\\"+filename);
        try {
            if (!files.createNewFile()) {
                throw new IOException("�ļ����ϴ�");
            }
        }catch (IOException ioE){
            throw new IOException("�ϴ�ʧ��");
        }
    }

    /**
     * close streams and socket
     */
    private void closeConnection()
    {
        displayMessage( "\nTerminating connection\n" );
        // disable enterField
        setTextFieldEditable( false );
        try
        {
            output.close(); // close output stream
            input.close(); // close input stream
            connection.close(); // close socket
        } // end try
        catch ( IOException ioException )
        {
            ioException.printStackTrace();
        } // end catch
    } // end method closeConnection


    /**
     * send message to client
     * @param message
     */
    private void sendData( String message )
    {
        try // send object to client
        {
            output.writeObject( "SERVER>>> " + message );
            output.flush(); // flush output to client
            displayMessage( "\nSERVER>>> " + message );
        } // end try
        catch ( IOException ioException )
        {
            displayArea.append( "\nError writing object" );
        } // end catch
    } // end method sendData

    /**
     *  manipulates displayArea in the event-dispatch thread
     * @param messageToDisplay
     */
    private void displayMessage( final String messageToDisplay )
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    @Override
                    public void run() // updates displayArea
                    {
                        displayArea.append( messageToDisplay ); // append message
                    } // end method run
                } // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

    /**
     * manipulates enterField in the event-dispatch thread
     * @param editable
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
                }  // end inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method setTextFieldEditable
} // end class Server
