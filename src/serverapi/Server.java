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
    private JTextField enterField; // inputs message from user
    private JTextArea displayArea; // display information to user
    private ObjectOutputStream output; // output stream to client
    private ObjectInputStream input; // input stream from client
    private ServerSocket server; // server socket
    private Socket connection; // connection to client
    private int counter = 1; // counter of number of connections
    private String uploadFilepath;
    private String downloadFilepaht;

    // set up GUI
    public Server()
    {
        super( "Server" );

        enterField = new JTextField(); // create enterField
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

        displayArea = new JTextArea(); // create displayArea
        add( new JScrollPane( displayArea ), BorderLayout.CENTER );

        setSize( 300, 150 ); // set size of window
        setVisible( true ); // show window
    } // end Server constructor

    // set up and run server
    public void runServer()
    {
        try // set up server to receive connections; process connections
        {
            server = new ServerSocket( 12345, 100 ); // create ServerSocket

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

    // wait for connection to arrive, then display connection info
    private void waitForConnection() throws IOException
    {
        displayMessage( "Waiting for connection\n" );
        /**
         * TODO �������Ҫ�����ؿͻ��˵���Ϣ
         */
        connection = server.accept(); // allow server to accept connection,һֱ�ȴ�ֱ�����յ��ͻ��˵�����
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

    // process connection with client
    private void processConnection() throws IOException
    {
        String message = "Connection successful";
        sendData( message ); // send connection successful message

        // enable enterField so server user can send messages
        setTextFieldEditable( true );

        do // process messages sent from client
        {
            try // read message and display it
            {
                message = ( String ) input.readObject(); // read new message
                displayMessage( "\n" + message ); // display message
                if(message.equals("CLIENT>>> UPLOAD" ) ){
                    uploadFilepath = (String)input.readObject();
                    FileInputStream filestream = null;
                    filestream = new FileInputStream(uploadFilepath);
                    /**
                     * ����ļ�����
                     */
                    String[] filenames = uploadFilepath.split("\\\\");
                    try {
                        Files.createFile(Paths.get(FileSystem.REMOTE_PATH));
                    } catch (IOException ioe) {

                    }
                    try {
                        if (!files.createNewFile()) {
                            JOptionPane.showConfirmDialog(buttonYes,"���ļ����ϴ��������ظ��ϴ���","����",JOptionPane.OK_CANCEL_OPTION);
                            System.out.println("���ļ����ϴ��������ظ��ϴ�!");
                            return;
                        }
                    } catch (IOException ioE) {
                        JOptionPane.showConfirmDialog(buttonYes,"�ϴ�ʧ�ܣ�","����",JOptionPane.OK_CANCEL_OPTION);
                        System.out.println("�ϴ�ʧ��!");
                        ioE.printStackTrace();
                        return;
                    }
                    /**
                     * TODO ��out.writeObject��Ϊ�������������
                     */
                    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(files))) {
                        try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
                            //���ĵ����浽HashTable��ָ��Ŀ¼���ϴ�ʧ��
                            getClient().sendFile(oin,filenames[filenames.length - 1]);
                        }
                    } catch (IOException | ClassNotFoundException exception) {
                        System.out.println("�ļ��ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�");
                        JOptionPane.showConfirmDialog(buttonYes,"�ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�","����",JOptionPane.OK_CANCEL_OPTION);
                        return;
                    }
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
    public void sendFile(String filename){
        File files = new File(FileSystem.REMOTE_PATH+"\\"+filename);
        /*������д��*/
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(files);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            output.writeObject(objectOutputStream);
            output.flush();
        } catch (IOException e) {

        }
    }
    /**
     * close streams and socket
     */
    private void closeConnection()
    {
        displayMessage( "\nTerminating connection\n" );
        setTextFieldEditable( false ); // disable enterField

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

    // send message to client
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

    // manipulates displayArea in the event-dispatch thread
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

    // manipulates enterField in the event-dispatch thread
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



