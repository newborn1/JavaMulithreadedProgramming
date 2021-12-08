package adduserclass;

import gui.OperatorFrame;
import dataprocessing.DataProcessing;
import dataprocessing.Doc;
import filesystem.FileSystem;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;

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
		JFrame mainFrame = new OperatorFrame(this);
		mainFrame.setVisible(true);

		Integer selector = 0;
//		Operator operator = (Operator) this;

		final String[] allLine = {"*****************��ӭ������������Ա�˵�****************\n",
				"\t\t\t1����ʾ�ļ��б�\n",
				"\t\t\t2�������ļ�\n",
				"\t\t\t3���ϴ��ļ�\n",
				"\t\t\t4���޸�����\n",
				"\t\t\t5���˳�\n",
				"*******************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for (String s : allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);

		while (true) {
			System.out.print("���������ֽ���ѡ��:");
			try {
//				selector = in.nextInt();
				break;
			} catch (InputMismatchException inputMatchE) {
				System.out.println("���������������ȷ�����֣�");
//				in.nextLine();
			}
		}
		switch (selector) {
			case 1:
				try {
					super.showFileList();
				} catch (SQLException sqe) {
					System.out.println(sqe.getMessage());
					System.out.println("The problem has been solved.Please input the selector against.");
				}
				break;
			case 2:
				this.downloadFile(null);
				break;
			case 3:
				this.uploadFile(null);
				break;
			case 4:
				this.setPassword(super.getPassword());
				break;
			case 5:
				this.exitSystem();
				break;
			default:
				System.out.println("�����ֵ��Ч�����������룡");
				break;
		}

	}

	/**
	 * �����ϴ�:�����µĵ����ļ�������Ϣ��������Hashtable�У����������ļ�������ָ��Ŀ¼��
	 * TODO ���ĵ����淽ʽ�ı�һ�£����ҽ����ϴ���Ч����֤
	 *
	 * @return boolean
	 */
	public boolean uploadFile(JPanel panel) {
		JLabel idLabel = new JLabel("������");
		JTextField idFiled = new JTextField("", 20);
		panel.add(idLabel);
		panel.add(idFiled);
		JLabel descriptionLabel = new JLabel("��������");
		JTextArea descriptionArea = new JTextArea("", 10, 20);
		panel.add(descriptionLabel);
		panel.add(descriptionArea);
		JLabel filenameLabel = new JLabel("�����ļ���");
		JTextField filename = new JTextField("", 20);
		panel.add(filenameLabel);
		panel.add(filename);

		JButton bottonYes = new JButton("�ϴ�");
		panel.add(bottonYes);
		/**
		 * ��lambda���ʾ���ܵ���this.getName()
		 */
		bottonYes.addActionListener(actionListener -> {
			JFileChooser fileChooser = new JFileChooser();
			//���õ�ǰ������Ŀ¼
			fileChooser.setCurrentDirectory(new File("."));
			//���ÿ�ѡ�ļ����ļ���
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//��ʾ�ļ��Ի���Ϊ��,result������status
			int result = fileChooser.showOpenDialog(panel);
			switch (result) {
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showConfirmDialog(bottonYes,"�ϴ�ʧ��!","����",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("�ϴ�ʧ��");
					break;
				case JFileChooser.APPROVE_OPTION:
					String path = fileChooser.getSelectedFile().getPath();
					File file = new File(path);
					FileInputStream filestream = null;
					try {
						System.out.println("uploading...");
						filestream = new FileInputStream(file);
					} catch (FileNotFoundException fileE) {
						JOptionPane.showConfirmDialog(bottonYes,"�Ҳ������ļ����ϴ�ʧ��!","����",JOptionPane.OK_CANCEL_OPTION);
						System.out.println("�Ҳ������ļ����ϴ�ʧ�ܣ�");
						return;
					}
					/**
					 * ����ļ�����
					 */
					String[] filenames = path.split("\\\\");
					String id = idFiled.getText();
					String description = descriptionArea.getText();
					try {
						Files.createFile(Paths.get(FileSystem.REMOTE_PATH));
					} catch (IOException ioe) {

					}
					File files = new File(FileSystem.REMOTE_PATH + "\\" + filenames[filenames.length - 1]);
					Doc doc = null;
					try {
						if (!files.createNewFile()) {
							JOptionPane.showConfirmDialog(bottonYes,"���ļ����ϴ��������ظ��ϴ���","����",JOptionPane.OK_CANCEL_OPTION);
							System.out.println("���ļ����ϴ��������ظ��ϴ�!");
							return;
						}
					} catch (IOException ioE) {
						JOptionPane.showConfirmDialog(bottonYes,"�ϴ�ʧ�ܣ�","����",JOptionPane.OK_CANCEL_OPTION);
						System.out.println("�ϴ�ʧ��!");
						ioE.printStackTrace();
						return;
					}
					try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(files))) {
						try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
							//���ĵ����浽HashTable��ָ��Ŀ¼���ϴ�ʧ��
							doc = (Doc) oin.readObject();
							out.writeObject(doc);
						}
					} catch (IOException | ClassNotFoundException exception) {
						System.out.println("�ļ��ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�");
						JOptionPane.showConfirmDialog(bottonYes,"�ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�","����",JOptionPane.OK_CANCEL_OPTION);
						return;
					}

					Timestamp timestamp = new Timestamp(System.currentTimeMillis());

					while (true) {
						try {
							DataProcessing.insertDoc(id, this.getName(), timestamp, description, doc.getFilename());
							break;
						} catch (SQLException sqlE) {
							DataProcessing.init();
						}
					}

					System.out.println("upload file is successful");
					JOptionPane.showConfirmDialog(bottonYes,"upload file is successful��","����",JOptionPane.OK_CANCEL_OPTION);
					break;
				default:
					break;
			}
		});
		return false;
	}
}
