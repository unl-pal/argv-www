package day06;
/*
 * ������������һ����װ������
 * ���⣺��������Ķ����Ժ����ֱ��ͨ��������.���ԡ��ķ�ʽ����Ӧ�Ķ������Ը�ֵ�Ļ���
 * 	   ���ܻ���ֲ�����ʵ����������⣬���ǿ��ǲ��ö�����ֱ���������ԣ�
 *   ����ͨ��������.����������ʽ�������ƶ�������Եķ��ʣ�
 *   ʵ������У������Ե�Ҫ��Ϳ���ͨ������������
 *   
 *����ķ���������װ�Ե�˼�룩
 *1.���������˽�л���
 *2.�ṩ�����ķ�����setter & getter����ʵ�ֵ���
 */

import java.util.Random;

/*
 * Ȩ�����η�  public private protected ȱʡ
 *     ���������������ԣ�����
 *    ע�⣺(1)Ȩ�޴Ӵ�С���У�public protected ȱʡ private
 *    	  (2)�������Ȩ���У� public ȱʡ
 *    
 */
public class TestAnimal {
	
}

class Animal{
	
//	String name; //���������
//	int legs;  //�ȵ�����
	
	//private���ε����ԣ�ֻ���ڱ����б����ã����˴��࣬�Ͳ��ܱ�������
	private String name; //���������
	private int legs;  //�ȵ�����
	String color;
	
	public void eat(){
	}
	
	public void sleep(){
	}
	
	public void info(){
	}
	
	//�����������
	public void setLegs(int l){
		Random rand = new Random();
		if(l>0 && rand.nextBoolean()){
			legs=l;
		}else{
		}
	}
	
	//��ȡ�������
	public int getLegs(){
		return legs;
	}
	
public String getName(){
		return name;
	}
	
}