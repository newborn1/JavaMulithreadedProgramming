package addUserClass;

public class Browser extends User {
	public Browser(String name,String password,String role) {
		super(name, password, role);//���õĹ�����ֻ���Ǳ���Ĺ���������γ�ʼ������ģ���super
	}
	public void showMenu() {
		final String[] allLine = {"************��ӭ���뵵�����Ա�˵�******************\n",
								  "\t\t\t1�������ļ�\n",
								  "\t\t\t2���ļ��б�\n",
								  "\t\t\t3���޸�����\n",
								  "\t\t\t4���˳�\n",
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
