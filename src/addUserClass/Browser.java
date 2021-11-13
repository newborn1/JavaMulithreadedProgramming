package addUserClass;

public class Browser extends User {
	public Browser(String name,String password,String role) {
		super(name, password, role);//调用的构造器只能是本类的构造器。如何初始化父类的：用super
	}
	public void showMenu() {
		final String[] allLine = {"************欢迎进入档案浏览员菜单******************\n",
								  "\t\t\t1、下载文件\n",
								  "\t\t\t2、文件列表\n",
								  "\t\t\t3、修改密码\n",
								  "\t\t\t4、退出\n",
								  "***************************************************\n"
		};
		
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);
		
		return;
	}
}
