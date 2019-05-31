package day06;


/*
 * �������������ж��������ࣺPerson��TestPerson�ࡣ
 * �������£�
 * ��setAge()�����˵ĺϷ�����(0~130),��getAge()�����˵�����
 * ��TestPerson����ʵ����Person��Ķ���b������setAge()��getAge()����
 * ���java�ķ�װ��
 */


public class TestPerson {
	
}

class Person{
	private int age;
	private String name;
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int a){
		if(a>0 && a<=130){
			age=a;
		}else{
			//System.out.println("���������������");
			//�׳��쳣
			throw new RuntimeException("���������������");
		}
	}
	
	public String getName(){
		return name;
	}
	
	
}