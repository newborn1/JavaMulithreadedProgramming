package clientapi;

import com.sun.corba.se.pept.encoding.InputObject;
import filesystem.FileSystem;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;
import javax.tools.FileObject;

/**
 * @author ��
 */
public class Client extends JFrame
{
    private JTextField enterField; // enters information from user
    private JTextArea displayArea; // display information to user
    private ObjectOutputStream output; // output stream to server
    private ObjectInputStream input; // input stream from server
    private String message = ""; // message from server
    private String chatServer; // host server for this application
    private Socket client; // socket to communicate with server
    private String downloadFilepath;//���ص��ļ�·��
    private String uploadFilepath;//�ϴ��ļ�·��

    /**
     * initialize chatServer and set up GUI
     */
    public Client( String host )
    {
        super( "Client" );

        chatServer = host; // set server to which this client connects

        enterField = new JTextField(); // create enterField
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
             * TODO �����ȴ�����˷�����Ϣ
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

    // get streams to send and receive data
    private void getStreams() throws IOException
    {
        // set up output stream for objects
        output = new ObjectOutputStream( client.getOutputStream() );

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
                 * TODO �Ҽ���Ĳ��Գ������
                 */
                message = ( String ) input.readObject(); // read new message
                displayMessage( "\n" + message ); // display message
                if(message.equals("SERVER>>> " )){
                    /* ��ȡ�ļ����� */
                }else if(message.equals("SERVER>>> RESPONSE_DOWNLOAD")){
                    File file = new File(downloadFilepath);

                    try {
                        Files.createDirectories(Paths.get(downloadFilepath));
                    } catch (IOException ioE) {}
                    /**
                     * TODO ��Ϊ���紫�䣺��������ʱ��Ϊ�ӷ���˶�ȡ,������Ҫ���ⲿ�ִ������server������Ҫ��Ϊ��server���,ԭ����Ҳ��Ҫ��
                     * �ļ������ļ����ݲ�һ��
                     */
                    try {
                        if (!file.createNewFile()) {
                            JOptionPane.showConfirmDialog(null, "���ļ������ص����أ������ظ�����!", "����", JOptionPane.OK_CANCEL_OPTION);
                            System.out.println("���ļ������ص����أ������ظ�����!");
                            return;
                        } else {
                            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
								out.writeObject(input.readObject());
                            }
                        }
                    } catch (IOException ioE) {
                        JOptionPane.showConfirmDialog(null, "����ʧ��!", "����", JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("����ʧ��!");
                        ioE.printStackTrace();
                        return;
                    }
                    JOptionPane.showConfirmDialog(null, "���سɹ�!", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
                    System.out.println("���سɹ�");
                }
            } // end try
            catch ( ClassNotFoundException classNotFoundException )
            {
                displayMessage( "\nUnknown object type received" );
            } // end catch

        } while ( !message.equals( "SERVER>>> TERMINATE" ) );
    } // end method processConnection

    // close streams and socket
    private void closeConnection()
    {
        displayMessage( "\nClosing connection" );
        setTextFieldEditable( false ); // disable enterField

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
        output.writeObject("CLIENT>>> UPLOAD");
        /**
         * ����ļ�����
         */
        String[] filenames = filepath.split("\\\\");
        /*�����ļ���*/
        output.writeObject(filenames[filenames.length - 1]);
        File file = new File(uploadFilepath);
        FileInputStream fileInputStream = new FileInputStream(file);
        /*�����ļ�*/
        output.writeObject(fileInputStream.read());
        /*ֻ��Ҫһ����flush��*/
        output.flush();
    }

    public void getFile(String filename,String filepath) throws IOException {
        this.downloadFilepath = filepath;
        sendData("CLIENT>>> DOWNLOAD");
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

