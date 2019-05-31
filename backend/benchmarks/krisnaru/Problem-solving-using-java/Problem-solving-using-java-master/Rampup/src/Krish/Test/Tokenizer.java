
package Krish.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;

class Element {
Element(int i, int j) {
this.i = i;
this.j = j;
}
 
int i, j;
 
@Override
public int hashCode() {
System.out.println("Inside HashCode Method");
int k = i + j;
return k;
}
 
@Override
public boolean equals(Object obj) {
 
System.out.println("Inside Equals Method");
if (i == ((Element) obj).i && j == ((Element) obj).j)
return true;
else
return false;
}
 
@Override
public String toString() {
return String.valueOf(i).concat(String.valueOf(j));
}
 
}
 
class TestThread extends Thread
{
	public void run()
	{
		for(int i=0; i<5; ++i)
		{
			System.out.println(i);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class AgeComparator implements Comparator{  
public int compare(Object o1,Object o2){  
Student s1=(Student)o1;  
Student s2=(Student)o2;  
  
if(s1.age==s2.age)  
return 0;  
else if(s1.age>s2.age)  
return 1;  
else  
return -1;  
}  
}  
class Student 
{
	int rollno;
	String name;
	int age;
	
	Student(int rollno, String name, int age){
		this.rollno = rollno;
		this.name = name;
		this.age = age;
	}
//	@Override
//	public int compareTo(Object arg0) {
//		Student st = (Student)arg0;
//		if(this.age == st.age)
//		{
//			return 0;
//		}
//		else if (this.age > st.age)
//		{
//			return 1;
//		}
//		else
//		{
//			return -1;
//		}
//	}
	
	public  String toString()
	{
		return this.rollno+","+this.name+","+this.age;
	}
}
public class Tokenizer {
	public static void main(String[] args) throws InterruptedException
	{
//		ArrayList al=new ArrayList();  
//		al.add(new Student(101,"Vijay",23));  
//		al.add(new Student(106,"Ajay",27));  
//		al.add(new Student(105,"Jai",21));  
//		  
//		Collections.sort(al, new AgeComparator()); 
//		Iterator it = al.iterator();
//		while(it.hasNext())
//		{
//			System.out.println(it.next());
//		}
//		System.out.print(isPalindrome("abkkba"));
		findDuplicates();
	}

	private static boolean isPalindrome(String s)
	{
		char[] strChars = s.toCharArray();
		int length = s.length();
		for(int i=0; i<=length/2; ++i)
		{
			if(strChars[i] != strChars[length-i-1])
			{
				return false;
			}
		}
		return true;
	}
	
	private static void findDuplicates()
	{
	 int[] items = {1,2,3,1,4,1,2};
	 for(int i=0; i<items.length; ++i)
	 {
		 int index = Math.abs(items[i]);
		 if(items[index] < 0)
		 {
			System.out.println("Duplicate found:"+ index);
		 }
		 else
		 {
			 items[index] = -items[index]; 
		 }
	 }
	}
	
	private static void findFirstNonrepeated()
	{
		String input = "ThisiskrishT";
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for(Character ch : input.toCharArray())
		{
			if(map.containsKey(ch))
			{
				Integer freq = map.get(ch);
				freq = freq + 1;
				map.put(ch, freq);
			}
			else
			{
				map.put(ch, 1);
			}
		}
		
		for(Character ch:input.toCharArray())
		{
			Integer freq = map.get(ch);
			if(freq == 1)
			{
				System.out.println(ch);
				break;
			}
		}
	}
	
	private static void Tokenize() {
		String s = "This is Krish, refreshing my java skills";
		StringTokenizer strToken = new StringTokenizer(s, ",");
		while(strToken.hasMoreElements())
		{
			System.out.println(strToken.nextElement());
		}
	}
    private static void mapTest(){
    	Map map = new HashMap();
    	map.put(new Element(1,2), 1);
    	map.put(new Element(2,3), 2);
    	map.put(new Element(3,4), 3);
    	for(Object key: map.keySet()){
    		System.out.println(key)
    		;
    	}
    }
    
	private static void listTest()
	{
		ArrayList<Integer> list = new ArrayList();
		list.add(1);
		list.add(2);
		list.add(0, 3);
		list.add(1, 4);
		System.out.println("length:"+list.size());
		Collections.sort(list);
		ListIterator<Integer> it = list.listIterator();
		while(it.hasNext())
		{
		  System.out.println(it.next());	
		}
		while(it.hasPrevious())
		{
		  System.out.println(it.previous());	
		}

	}
}
