package adduserclass;
import gui.OperatorFrame;
import dataprocessing.DataProcessing;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;

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
		new OperatorFrame(this);

		System.out.print("档案操作员菜单");

		return;
	}

	/**
	 * 档案上传:输入新的档案文件属性信息，保存至Hashtable中，并将档案文件拷贝至指定目录中
	 * TODO 将文档保存方式改变一下，并且进行上传有效性验证
	 * TODO 将上传文件的去向变为服务端，而不是本地
	 *
	 * @return boolean
	 */
	public boolean uploadFile(JPanel panel) {
		panel.setLayout(new GridLayout(0,1));
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JLabel idLabel = new JLabel("   档案号");
		JTextField idFiled = new JTextField("", 20);
		panel1.add(idLabel);
		panel1.add(idFiled);
		JLabel descriptionLabel = new JLabel("档案描述");
		JTextArea descriptionArea = new JTextArea("", 5, 20);
		panel2.add(descriptionLabel);
		panel2.add(descriptionArea);
		JLabel filenameLabel = new JLabel("档案文件名");
		JTextField filename = new JTextField("", 15);
		panel3.add(filenameLabel);
		panel3.add(filename);

		JButton buttonYes = new JButton("上传");
		panel3.add(buttonYes);

		panel.add(new JPanel());
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(new JPanel());
		/**
		 * 用lambda表达示才能调用this.getName()
		 */
		buttonYes.addActionListener(actionListener -> {
			JFileChooser fileChooser = new JFileChooser();
			//设置当前的所在目录
			fileChooser.setCurrentDirectory(new File("."));
			//设置可选文件和文件夹
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//显示文件对话框为打开,result来保存status
			int result = fileChooser.showOpenDialog(panel);
			switch (result) {
				case JFileChooser.CANCEL_OPTION:
					JOptionPane.showConfirmDialog(buttonYes, "上传失败!", "警告", JOptionPane.OK_CANCEL_OPTION);
					System.out.println("上传失败");
					break;
				case JFileChooser.APPROVE_OPTION:
					String path = fileChooser.getSelectedFile().getPath();
					try {
						System.out.println("uploading...");
						getClient().sendFile(path);
					} catch (IOException | ClassNotFoundException fileE) {
						JOptionPane.showConfirmDialog(buttonYes, "找不到该文件，上传失败!", "警告", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("找不到该文件，上传失败！");
						return;
					}
					/**
					 * 获得文件名称
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
					JOptionPane.showConfirmDialog(buttonYes,"upload file is successful！","警告",JOptionPane.OK_CANCEL_OPTION);
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
