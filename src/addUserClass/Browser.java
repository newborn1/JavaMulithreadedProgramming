package adduserclass;


/**
 * TODO ����ߣ���ĳЩ������Ϣ�����޸ģ�����ļ����̳��˳����û���
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Browser extends AbstractUser {
	public Browser(String name,String password,String role) {
		//���õĹ�����ֻ���Ǳ���Ĺ���������γ�ʼ������ģ���super
		super(name, password, role);
	}
	
	@Override
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
