package adduserclass;
import gui.OperatorFrame;
import dataprocessing.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * feature ��������Ա���Ժ�̨���ļ�����ͳһ�����̳��˳����û���
 *
 * @author ֣ΰ��
 * @data 2021/11/19
 */
public class Operator extends AbstractUser {
	public Operator(String name, String password, String role) {
		super(name, password, role);
	}

	/**
	 * TODO �޸ĸ������showMenu()����ʵ�ָ��������Ĺ��ܼ���
	 */
	@Override
	public void showMenu() {
		new OperatorFrame(this);

		System.out.print("��������Ա�˵�");

		return;
	}

	/**
	 * �����ϴ�:�����µĵ����ļ�������Ϣ��������Hashtable�У����������ļ�������ָ��Ŀ¼��
	 * TODO ���ĵ����淽ʽ�ı�һ�£����ҽ����ϴ���Ч����֤
	 * TODO ���ϴ��ļ���ȥ���Ϊ����ˣ������Ǳ���
	 *
	 * @return boolean
	 */
	public boolean uploadFile(JPanel panel) {
		panel.setLayout(new GridLayout(0,1));
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JLabel idLabel = new JLabel("   ������");
		JTextField idFiled = new JTextField("", 20);
		panel1.add(idLabel);
		panel1.add(idFiled);
		JLabel descriptionLabel = new JLabel("��������");
		JTextArea descriptionArea = new JTextArea("", 5, 20);
		panel2.add(descriptionLabel);
		panel2.add(descriptionArea);
		JLabel filenameLabel = new JLabel("�����ļ���");
		JTextField filename = new JTextField("", 15);
		panel3.add(filenameLabel);
		panel3.add(filename);

		JButton buttonYes = new JButton("�ϴ�");
		panel3.add(buttonYes);

		panel.add(new JPanel());
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(new JPanel());
		/**
		 * ��lambda���ʾ���ܵ���this.getName()
		 */
		buttonYes.addActionListener(actionListener -> {
			JFileChooser fileChooser = new JFileChooser();
			//���õ�ǰ������Ŀ¼
			fileChooser.setCurrentDirectory(new File("."));
			//���ÿ�ѡ�ļ����ļ���
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//��ʾ�ļ��Ի���Ϊ��,result������status
			int result = fileChooser.showOpenDialog(panel);
			switch (result) {
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showConfirmDialog(buttonYes, "�ϴ�ʧ��!", "����", JOptionPane.OK_CANCEL_OPTION);
					System.out.println("�ϴ�ʧ��");
					break;
				case JFileChooser.APPROVE_OPTION:
					String path = fileChooser.getSelectedFile().getPath();
					try {
						System.out.println("uploading...");
						getClient().sendFile(path);
					} catch (IOException | ClassNotFoundException fileE) {
						JOptionPane.showConfirmDialog(buttonYes, "�Ҳ������ļ����ϴ�ʧ��!", "����", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("�Ҳ������ļ����ϴ�ʧ�ܣ�");
						return;
					}
					/**
					 * ����ļ�����
					 */
					String[] filenames = path.split("\\\\");
					String id = idFiled.getText();
					String description = descriptionArea.getText();

					Timestamp timestamp = new Timestamp(System.currentTimeMillis());

					while (true) {
						try {
							DataProcessing.insertDoc(id, this.getName(), timestamp, description, filenames[filenames.length - 1]);
							break;
						} catch (SQLException sqlE) {
//							DataProcessing.init();
						}
					}

					System.out.println("upload file is successful");
					JOptionPane.showConfirmDialog(buttonYes,"upload file is successful��","����",JOptionPane.OK_CANCEL_OPTION);
					break;
				default:
					break;
			}
		});
		return false;
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(400,400);
		JPanel panel = new JPanel();
		Operator operator = new Operator("hhh","123","operator");
		operator.uploadFile(panel);
		frame.add(panel);

		frame.setVisible(true);
	}
}
