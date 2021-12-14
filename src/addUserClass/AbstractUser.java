package adduserclass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;

import dataprocessing.DataProcessing;
import dataprocessing.Doc;

import javax.swing.*;
import javax.swing.border.Border;

import static filesystem.FileSystem.NotConnectedToDatabase;


/**
 * feature �����û��࣬Ϊ���û������ṩģ��
 * 
 * @author ֣ΰ��
 * @date 2021/11/19
 */
public abstract class AbstractUser {
	private String name;
	private String password;
	private String role;
	static final double EXCEPTION_PROBABILITY=0.9;

	private JButton buttonYes = new JButton("ȷ��");
	private JButton buttonNo = new JButton("ȡ��");

	AbstractUser(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;				
	}
	
	@Override
	public String toString(){
		return  "name = " + name
				+"password = " + password
				+",role = " + role;
	}

	/**
	 * �Ṱ��������������
	 *
	 * @param panel
	 * @return
	 */
	public boolean changeSelfInfo(JPanel panel){
		/**
		 * ����û��������룬��ɫ����ť�������ȷ����ť
		 */
		panel.setLayout(new GridLayout(0,1));
		JPanel panel1 = new JPanel();
		Label nameLabel = new Label("�û���");
		JTextField nameField = new JTextField(this.getName());
		nameField.setEditable(false);
		panel1.add(nameLabel);
		panel1.add(nameField);
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		Label passwordOldLabel = new Label("������");
		Label passwordNewLabel = new Label("������");
		JPasswordField passwordOldField = new JPasswordField(20);
		JPasswordField passwordNewField = new JPasswordField(20);
		panel2.add(passwordOldLabel);
		panel2.add(passwordOldField);
		panel3.add(passwordNewLabel);
		panel3.add(passwordNewField);
		JPanel panel4 = new JPanel();
		Label roleLabel = new Label("���");
		JTextField roleField = new JTextField(this.getRole());
		roleField.setEditable(false);
		panel4.add(roleLabel);
		panel4.add(roleField);
		/**
		 * ÿ�ζ�Ҫnewһ������Ȼֻ����ʾ���һ��
		 */
		JPanel panel5 = new JPanel();
		JButton buttonYes  = new JButton("ȷ��");
		JButton buttonNo = new JButton("ȡ��");
		panel5.add(buttonYes);
		panel5.add(buttonNo);

		panel.add(new JPanel());
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		panel.add(panel5);
		panel.add(new JPanel());

		final boolean[] flag = {true};
		/**
		 * TODO ������������
		 */
		buttonYes.addActionListener( e->{
				flag[0] = false;
				try {
				String oldPassword ="", newPassword="";

				System.out.print("��������µ�����:");
				oldPassword = new String(passwordOldField.getPassword());
				if(!this.getPassword().equals(oldPassword)){
					JOptionPane.showConfirmDialog(buttonYes,"�������!","����",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("������������´���");
					System.out.println("�������");
					return;
				}
				newPassword = new String(passwordNewField.getPassword());
				if (!DataProcessing.updateUser(this.getName(), newPassword, this.getRole())) {
					return;
				}
				JOptionPane.showConfirmDialog(buttonYes,"�޸ĳɹ�!","��ʾ",JOptionPane.OK_CANCEL_OPTION);
			} catch (SQLException sqlE) {
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
					DataProcessing.init();
				}
			}
		});
		return true;
	}
	/**
	 *
	 * @param password �û��������֤����
	 * @return �ж��Ƿ��޸ĳɹ���
	 * @throws SQLException
	 */
	public boolean changeSelfInfo(String password) throws SQLException {
		//��ʾGUI����

		//д�û���Ϣ���洢
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			JOptionPane.showConfirmDialog(buttonYes,"�޸ĳɹ���","��ʾ",JOptionPane.OK_CANCEL_OPTION);
			System.out.println("�޸ĳɹ�");
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * չʾ�ļ��Ĳ˵�����Ҫ��ʵ��
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();

	/**
	 * ��������:���ݵ������ڹ�ϣ���в��ҵõ��ļ���Ϣ����δ�漰����֮ǰ��ֻ��ʵ���ڵ����Ͻ���Ӧ�ļ�������ָ��Ŀ¼��
	 *
	 * @return
	 */
	public boolean downloadFile(JPanel panel){
		panel.setLayout(new BorderLayout());
		//��ʾ�ļ���
		JPanel panel1 = new JPanel();
		showFileList(panel1);
		JButton buttonYes = new JButton("ȷ��");
		JPanel panel2 = new JPanel();
		panel2.add(buttonYes);
		panel.add(panel1,BorderLayout.CENTER);
		panel.add(panel2,BorderLayout.SOUTH);
		buttonYes.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			//���õ�ǰ������Ŀ¼
			fileChooser.setCurrentDirectory(new File("."));
			//���ÿ�ѡ�ļ����ļ���
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//��ʾ�ļ��Ի���Ϊ����,result������status
			int result = fileChooser.showSaveDialog(panel);
			switch (result) {
				case JFileChooser.ERROR_OPTION:
					System.out.println("��ʧ��");
					break;
				case JFileChooser.CANCEL_OPTION:
					System.out.println("����ʧ��");
					break;
				case JFileChooser.APPROVE_OPTION:
					/**
					 * ���JTable���
					 */
					JPanel tempPanel = (JPanel) panel.getComponent(0);
					int count = tempPanel.getComponentCount();
					JTable tableData = null;
					for(int i = 0;i < count;i++) {
						Object component = tempPanel.getComponent(i);
						if(component instanceof JScrollPane) {
							JScrollPane scrollPane = ((JScrollPane) component);
							tableData = (JTable) scrollPane.getViewport().getComponent(0);
						}
					}
					int selectedRow = tableData.getSelectedRow();

					Doc doc = null;
					String id = tableData.getValueAt(selectedRow, 0).toString();
					try {
						doc = DataProcessing.searchDoc(id);
					} catch (SQLException sqlE) {
						System.out.println(sqlE.getMessage());
						System.out.println("Connecting to Database...");
						JOptionPane.showConfirmDialog(buttonYes, "�������ݿ�ʧ�ܣ����������롣", "����", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("���������룡");
					}
					if (doc == null) {
						JOptionPane.showConfirmDialog(buttonYes, "�Ҳ�����Ӧ�ĵ�����", "����", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("�Ҳ�����Ӧ�ĵ���������������:");
					}
					System.out.println("�����ļ���......");
					String path = fileChooser.getSelectedFile().getPath();
					File file = new File(path + "\\" + doc.getFilename());

					try {
						Files.createDirectories(Paths.get(path));
					} catch (IOException ioE) {

					}

					try {
						if (!file.createNewFile()) {
							JOptionPane.showConfirmDialog(buttonYes, "���ļ������ص����أ������ظ�����!", "����", JOptionPane.OK_CANCEL_OPTION);
							System.out.println("���ļ������ص����أ������ظ�����!");
							return;
						} else {
							try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
								out.writeObject(doc);
							}
						}
					} catch (IOException ioE) {
						JOptionPane.showConfirmDialog(buttonYes, "����ʧ��!", "����", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("����ʧ��!");
						ioE.printStackTrace();
						return;
					}
					JOptionPane.showConfirmDialog(buttonYes, "���سɹ�!", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
					System.out.println("���سɹ�");
					break;
				default:
					break;
			}

		});

		return true;
	}





	public void showFileList(JPanel panel){
		Object[][] tableData = new Object[100][5];
		try {
			Enumeration<Doc> document = DataProcessing.listDoc();
			Doc doc = null;
			int index = 0;
			while(document.hasMoreElements()){
				doc = document.nextElement();

				tableData[index][0] = doc.getId();
				tableData[index][1] = doc.getCreator();
				tableData[index][2] = doc.getTimestamp();
				tableData[index][3] = doc.getFilename();
				tableData[index][4] = doc.getDescription();

				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] tableName = {"������","������","ʱ��","�ļ���","����"};
		JTable fileTable = new JTable(tableData,tableName);
		panel.add(new JScrollPane(fileTable));
		try {
			showFileList();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("The problem has been solved.Please input the selector against.");
			System.out.println("����������!");
			/**
			 * TODO �쳣����
			 */
//			JOptionPane.showConfirmDialog(buttonYes,"����ʧ�ܣ�����������!","����",JOptionPane.OK_CANCEL_OPTION);
		}
	}
	/**
	 * ������ѯ:ʵ�ְ�������ѯ��Ӧ�ĵ����ļ���Ϣ��Ҳ�ɼ�Ϊչʾ���е����ļ���Ϣ.��δ�漰���ݿ�֮ǰ��������Ϣ�����Hashtable��
	 *
	 * @param
	 * @return void
	 * @throws SQLException 
	*/
	public void showFileList() throws SQLException{
		double ranValue=Math.random();
		if (ranValue > EXCEPTION_PROBABILITY) {
			throw new SQLException("Error in accessing file DB");
		}
		System.out.println("...........Document list...........");
		Enumeration<Doc> document = DataProcessing.listDoc();
		while(document.hasMoreElements()){
			System.out.println(document.nextElement());
		}
	}
	
	
	/**
	 *
	 * @param 
	 * @return void
	 * @throws  
	*/
	public void exitSystem(){
		filesystem.FileSystem.in.close();
		JOptionPane.showConfirmDialog(buttonYes,"ϵͳ�Ƴ���ллʹ��!","����",JOptionPane.OK_CANCEL_OPTION);
		System.out.println("ϵͳ�˳�, ллʹ�� ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		System.out.println("�޸����롣");
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
