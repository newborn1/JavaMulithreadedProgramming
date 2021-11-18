package addUserClass;

public class Operator extends User{
	public Operator(String name,String password,String role) {
		super(name,password,role);
	}
	public void showMenu() {
		final String[] allLine = {"*****************欢迎来到档案操作员菜单****************\n",
						  		  "\t\t\t1、显示文件列表\n",
						  		  "\t\t\t2、下载文件\n",
						  		  "\t\t\t3、上传文件\n",
						  		  "\t\t\t4、修改密码\n",
						  		  "\t\t\t5、退出\n",
						  		  "*******************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);
		
		return;
	}
	public boolean uploadFile() {
		System.out.print("uploading...");
		System.out.printf("upload file is sucessful");
		return true;
	}
}
