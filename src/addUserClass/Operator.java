package adduserclass;

/**
 * TODO ��������Ա���Ժ�̨���ļ�����ͳһ�����̳��˳����û��� 
 *
 * @author ֣ΰ��
 * @data 2021/11/19
 */
public class Operator extends AbstractUser{
	public Operator(String name,String password,String role) {
		super(name,password,role);
	}
	
	@Override
	public void showMenu() {
		final String[] allLine = {"*****************��ӭ������������Ա�˵�****************\n",
						  		  "\t\t\t1����ʾ�ļ��б�\n",
						  		  "\t\t\t2�������ļ�\n",
						  		  "\t\t\t3���ϴ��ļ�\n",
						  		  "\t\t\t4���޸�����\n",
						  		  "\t\t\t5���˳�\n",
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
