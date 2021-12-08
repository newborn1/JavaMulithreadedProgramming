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
 * feature 档案操作员，对后台的文件进行统一管理，继承了抽象用户类
 *
 * @author 郑伟鑫
 * @data 2021/11/19
 */
public class Operator extends AbstractUser {
	public Operator(String name, String password, String role) {
		super(name, password, role);
	}

	/**
	 * TODO 修改各个类的showMenu()并且实现各个函数的功能即可
	 */
	@Override
	public void showMenu() {
		JFrame mainFrame = new OperatorFrame(this);
		mainFrame.setVisible(true);

		Integer selector = 0;
//		Operator operator = (Operator) this;

		final String[] allLine = {"*****************欢迎来到档案操作员菜单****************\n",
				"\t\t\t1、显示文件列表\n",
				"\t\t\t2、下载文件\n",
				"\t\t\t3、上传文件\n",
				"\t\t\t4、修改密码\n",
				"\t\t\t5、退出\n",
				"*******************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for (String s : allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);

		while (true) {
			System.out.print("请输入数字进行选择:");
			try {
//				selector = in.nextInt();
				break;
			} catch (InputMismatchException inputMatchE) {
				System.out.println("输入错误，请输入正确的数字！");
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
				System.out.println("输入的值无效，请重新输入！");
				break;
		}

	}

	/**
	 * 档案上传:输入新的档案文件属性信息，保存至Hashtable中，并将档案文件拷贝至指定目录中
	 * TODO 将文档保存方式改变一下，并且进行上传有效性验证
	 *
	 * @return boolean
	 */
	public boolean uploadFile(JPanel panel) {
		JLabel idLabel = new JLabel("档案号");
		JTextField idFiled = new JTextField("", 20);
		panel.add(idLabel);
		panel.add(idFiled);
		JLabel descriptionLabel = new JLabel("档案描述");
		JTextArea descriptionArea = new JTextArea("", 10, 20);
		panel.add(descriptionLabel);
		panel.add(descriptionArea);
		JLabel filenameLabel = new JLabel("档案文件名");
		JTextField filename = new JTextField("", 20);
		panel.add(filenameLabel);
		panel.add(filename);

		JButton bottonYes = new JButton("上传");
		panel.add(bottonYes);
		/**
		 * 用lambda表达示才能调用this.getName()
		 */
		bottonYes.addActionListener(actionListener -> {
			JFileChooser fileChooser = new JFileChooser();
			//设置当前的所在目录
			fileChooser.setCurrentDirectory(new File("."));
			//设置可选文件和文件夹
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//显示文件对话框为打开,result来保存status
			int result = fileChooser.showOpenDialog(panel);
			switch (result) {
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showConfirmDialog(bottonYes,"上传失败!","警告",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("上传失败");
					break;
				case JFileChooser.APPROVE_OPTION:
					String path = fileChooser.getSelectedFile().getPath();
					File file = new File(path);
					FileInputStream filestream = null;
					try {
						System.out.println("uploading...");
						filestream = new FileInputStream(file);
					} catch (FileNotFoundException fileE) {
						JOptionPane.showConfirmDialog(bottonYes,"找不到该文件，上传失败!","警告",JOptionPane.OK_CANCEL_OPTION);
						System.out.println("找不到该文件，上传失败！");
						return;
					}
					/**
					 * 获得文件名称
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
							JOptionPane.showConfirmDialog(bottonYes,"该文件已上传，请勿重复上传！","警告",JOptionPane.OK_CANCEL_OPTION);
							System.out.println("该文件已上传，请勿重复上传!");
							return;
						}
					} catch (IOException ioE) {
						JOptionPane.showConfirmDialog(bottonYes,"上传失败！","警告",JOptionPane.OK_CANCEL_OPTION);
						System.out.println("上传失败!");
						ioE.printStackTrace();
						return;
					}
					try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(files))) {
						try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
							//将文档保存到HashTable和指定目录中上传失败
							doc = (Doc) oin.readObject();
							out.writeObject(doc);
						}
					} catch (IOException | ClassNotFoundException exception) {
						System.out.println("文件上传失败，请检查文件是否损坏！");
						JOptionPane.showConfirmDialog(bottonYes,"上传失败，请检查文件是否损坏！","警告",JOptionPane.OK_CANCEL_OPTION);
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
					JOptionPane.showConfirmDialog(bottonYes,"upload file is successful！","警告",JOptionPane.OK_CANCEL_OPTION);
					break;
				default:
					break;
			}
		});
		return false;
	}
}
