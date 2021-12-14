package adduserclass;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.sun.org.apache.xpath.internal.XPathAPI;
import dataprocessing.Doc;
import gui.AdministratorFrame;
import dataprocessing.DataProcessing;

import javax.swing.*;

import static filesystem.FileSystem.NotConnectedToDatabase;

/**
 * feature ��������Ա��,�������˵���Ϣ����ͳһ�����̳��˳����û���
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Administrator extends AbstractUser {
	public Administrator(String name,String password,String role) {
		super(name,password,role);
	}
	@Override
	public void showMenu() {
		JFrame mainFrame = new AdministratorFrame(this);
		mainFrame.setVisible(true);


		Integer selector = 0;

		final String[] allLine = {"**************��ӭ�������Ա�˵�**********************\n",
								  "\t\t\t1����ʾ�ļ��б�\n",
								  "\t\t\t2�������ļ�\n",
								  "\t\t\t3���޸ĸ�����Ϣ\n",
								  "\t\t\t4���޸��û���Ϣ\n",
								  "\t\t\t5��ɾ���û�\n",
								  "\t\t\t6�������û�\n",
								  "\t\t\t7����ʾ�����û�\n",
								  "\t\t\t8���˳�\n",
								  "*****************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);


		System.out.print("���������ֽ���ѡ��:");
		switch(selector) {
			case 1:
				try{
					super.showFileList();break;
				}catch(SQLException e) {
					System.out.println(e.getMessage());
					System.out.println("����������!");
					break;
				}
			case 2:
				this.downloadFile(null);break;
			case 3:
				try {
					super.changeSelfInfo(super.getPassword());
				} catch (SQLException sqlE) {
					System.out.println(sqlE.getMessage());
					System.out.println("Please do it against.");
					if(NotConnectedToDatabase.equals(sqlE.getMessage())){
						DataProcessing.init();
					}
				}
				break;
			case 4:
				/**
				 * ������������ж�
				 */
				this.changeUserInfo(null);
//				while(!this.changeUserInfo(null)){
//					System.out.println("������������´���");
//				}
				break;
			case 5:
				this.delAbstractUser(null);break;
			case 6:
				this.addAbstractUser(null);break;
			case 7:
				listAbstractUser(null);break;
			case 8:
				this.exitSystem();break;
			default:
				break;
		}

		return;
	}
	
	/**
	 * feature ����������Ϣ�޸��û�����Ϣ
	 * 
	 * @throws SQLException
	 */
	public void changeUserInfo(JPanel panel) {
		panel.setLayout(new GridLayout(0,1));
		panel.add(new JPanel());
		Label nameLabel = new Label("�û���");
		JComboBox nameBox = new JComboBox();
		nameBox.addItem(null);
		ResultSet allUsers = null;
		try {
			allUsers = DataProcessing.listUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//����������е��û�
		while(true){
			try {
				if (!allUsers.next()) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			AbstractUser user = DataProcessing.newUser(allUsers);
			nameBox.addItem(user.getName());
		}


		JPanel panel1 = new JPanel();
		panel1.add(nameLabel);
		panel1.add(nameBox);
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		Label passwordNewLabel = new Label("����");
		JPasswordField passwordNewField = new JPasswordField(20);
		panel2.add(passwordNewLabel);
		panel2.add(passwordNewField);
		panel.add(panel2);
		JPanel panel3 = new JPanel();
		Label roleLabel = new Label("���");
		JComboBox roleBox = new JComboBox();
		roleBox.addItem("administrator");
		roleBox.addItem("browser");
		roleBox.addItem("operator");

		/**
		 * TODO �Ż���ʾ
		 */
		if(nameBox.getSelectedIndex()!=0){
			roleBox.setSelectedIndex(0);
		}
		panel3.add(roleLabel);
		panel3.add(roleBox);
		panel.add(panel3);
		/**
		 * ÿ�ζ�Ҫnewһ������Ȼֻ����ʾ���һ��
		 */
		JPanel panel4 = new JPanel();
		JButton buttonYes  = new JButton("ȷ��");
		JButton buttonNo = new JButton("ȡ��");
		panel4.add(buttonYes);
		panel4.add(buttonNo);
		panel.add(panel4);
		panel.add(new JPanel());
		buttonYes.addActionListener(actionEvent -> {
				try {
					String name, password, role;

					System.out.print("��������µ��û���:");
					name = nameBox.getSelectedItem().toString();
					System.out.print("��������µ�����:");
					password = new String(passwordNewField.getPassword());
					System.out.print("��������µĽ�ɫ:");
					role = roleBox.getSelectedItem().toString();

					if (!DataProcessing.updateUser(name, password, role)) {
						JOptionPane.showConfirmDialog(buttonYes,"�������!","����",JOptionPane.OK_CANCEL_OPTION);
						System.out.println("������������´���");
						return;
					}
				} catch (SQLException sqlE) {
					System.out.println(sqlE.getMessage());
					System.out.println("Please do it against.");
					if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
						DataProcessing.init();
					}
				}
				nameBox.setSelectedIndex(0);
				passwordNewField.setText("");
				roleBox.setSelectedIndex(0);
				JOptionPane.showConfirmDialog(buttonYes,"�޸�!","��ʾ",JOptionPane.OK_CANCEL_OPTION);
		});

	}
	
	public boolean delAbstractUser(JPanel panel) throws NullPointerException{
		panel.setLayout(new BorderLayout());
		JPanel panel1 = new JPanel();
		listAbstractUser(panel1);
		panel.add(panel1,BorderLayout.CENTER);
		JPanel panel2 = new JPanel();
		JButton buttonYes = new JButton("ȷ��");
		JButton buttonNo = new JButton("ȡ��");
		panel2.add(buttonYes);
		panel2.add(buttonNo);
		panel.add(panel2,BorderLayout.SOUTH);

		buttonYes.addActionListener(actionListener -> {
			/**
			 * ���JTable���
			 */
			JPanel getPanel = (JPanel) panel.getComponent(0);
			int count = getPanel.getComponentCount();
			JTable tableData = null;
			for(int i = 0;i < count;i++) {
				Object component = getPanel.getComponent(i);
				if(component instanceof JScrollPane) {
					JScrollPane scrollPane = ((JScrollPane) component);
					tableData = (JTable) scrollPane.getViewport().getComponent(0);
				}
			}
			int selectedRow = tableData.getSelectedRow();

			System.out.print("�����뽫ɾ�����û�����");
			JOptionPane.showConfirmDialog(buttonYes,"ɾ���û��ɹ�","����",JOptionPane.OK_CANCEL_OPTION);
			String name = tableData.getValueAt(selectedRow, 0).toString();
			try {
				DataProcessing.deleteUser(name);
			}catch (SQLException sqlE){
				/**
				 * TODO ��bug
				 */
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				JOptionPane.showConfirmDialog(buttonYes,"Please do it against.","����",JOptionPane.OK_CANCEL_OPTION);
				if(NotConnectedToDatabase.equals(sqlE.getMessage())){
					DataProcessing.init();
				}
				return;
			}
			panel.removeAll();
			delAbstractUser(panel);
		});



		return true;
	}
	
	public boolean addAbstractUser(JPanel panel) {
		panel.setLayout(new GridLayout(0,1));
		panel.add(new JPanel());
		JPanel panel1 = new JPanel();
		Label nameLabel = new Label("�û���");
		JTextField nameField = new JTextField("",20);
		panel1.add(nameLabel);
		panel1.add(nameField);
		panel.add(panel1);
		Label passwordLabel = new Label("����");
		JPasswordField passwordField = new JPasswordField("",20);
		JPanel panel2 = new JPanel();
		panel2.add(passwordLabel);
		panel2.add(passwordField);
		panel.add(panel2);
		Label roleLabel = new Label("���");
		JComboBox roleBox = new JComboBox();
		roleBox.addItem("browser");
		roleBox.addItem("operator");
		roleBox.addItem("administrator");
		JPanel panel3 = new JPanel();
		panel3.add(roleLabel);
		panel3.add(roleBox);
		panel.add(panel3);
		JButton buttonYes = new JButton("ȷ��");
		JButton buttonNo = new JButton("ȡ��");
		JPanel panel4 = new JPanel();
		panel4.add(buttonYes);
		panel4.add(buttonNo);
		panel.add(panel4);
		panel.add(new JPanel());

		buttonYes.addActionListener(actionListener -> {
			String role = "";
			String password = "";
			String name = "";

			System.out.print("���������֣�");
			name = nameField.getText();
			System.out.print("���������룺");
			password = new String(passwordField.getPassword());
			System.out.print("�������ɫ��");
			role = roleBox.getSelectedItem().toString();

			try {
				DataProcessing.insertUser(name, password, role);
				JOptionPane.showConfirmDialog(buttonYes,"�����û��ɹ�","����",JOptionPane.OK_CANCEL_OPTION);
			} catch (SQLException sqlE) {
				/**
				 * TODO ��bug,���:�����׳��쳣������ܴ���
				 */
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				JOptionPane.showConfirmDialog(buttonYes,"����ʧ��,����������!","����",JOptionPane.OK_CANCEL_OPTION);
				if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
					DataProcessing.init();
				}
			}
			//�����ݻָ�,���Ӱ�ȫ
			nameField.setText("");
			passwordField.setText("");
			roleBox.setSelectedIndex(0);
		});

		return true;
	}

	public boolean listAbstractUser(JPanel panel){
		Object[][] tableData = new Object[100][5];
		try {
			ResultSet users = DataProcessing.listUser();
			AbstractUser user = null;
			int index = 0;
			while(users.next()){
				user = DataProcessing.newUser(users);

				tableData[index][0] = user.getName();
				tableData[index][1] = user.getPassword();
				tableData[index][2] = user.getRole();

				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] tableName = {"�û���","����","���"};
		JTable fileTable = new JTable(tableData,tableName);
		panel.add(new JScrollPane(fileTable));
		try {
			showFileList();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("The problem has been solved.Please input the selector against.");
			/**
			 * TODO �����buttonYes�ܲ��ܻ���panel,Ϊʲô������Ҳ���ܰ�ע�͵����ӽ�ȥ��
			 */
//			JOptionPane.showConfirmDialog(new Button(),"The problem has been solved.Please input the selector against.","����",JOptionPane.OK_CANCEL_OPTION);
			System.out.println("����������!");
//			JOptionPane.showConfirmDialog(new Button(),"����������!","����",JOptionPane.OK_CANCEL_OPTION);
		}
		return true;
	}

	public boolean listAbstractUser() {
		ResultSet e = null;
		try{
			e = DataProcessing.listUser();
		}catch (SQLException sqlE){
			System.out.println(sqlE.getMessage());
			return false;
		}
		while(true) {
			try {
				if (!e.next()) {
					break;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			System.out.println(DataProcessing.newUser(e));
		}
		return true;
	}

	/**
	 * ���쳣�ľ���Ҫ���أ�Ϊ��ʵ��GUI��
	 */
}
