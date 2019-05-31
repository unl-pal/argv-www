import java.util.HashMap;

public class LRUCache {
public static void main(String[] args){
	LRUCacheMap<Integer, String> cacheMap = new LRUCacheMap<Integer, String>(2);
	cacheMap.set(1, "1");
	cacheMap.set(2, "2");
	cacheMap.get(1);
	cacheMap.set(3, "3");
	System.out.println(cacheMap.get(2));
}
}

class LRUCacheMap<K, V>{
	private HashMap<K,DoublyLinkedlistNode<K,V>> map = null;
	
	private DoublyLinkedlistNode<K, V> head, rear = null;
	int capacity = 0;
	int count = 0;
	
	public LRUCacheMap(int capacity){
		this.capacity = capacity;
		this.count = 0;
		this.map = new HashMap<K, DoublyLinkedlistNode<K, V>>();
	}
	
	private void setHead(DoublyLinkedlistNode<K, V> node){
		removeNode(node);
		
		node.next = head;
		if(head != null)
		{
			head.prev = node;
		}
		else{
			rear = node;
		}
		head = node;
	}

	private void removeNode(DoublyLinkedlistNode<K, V> node) {
		if(node == rear)
		{
			rear = node.prev;
		}
		
		if(node.prev != null)
		{
			node.prev.next = node.next;
		}
		if(node.next != null)
		{
			node.next.prev = node.prev;
		}
	}
	
	public V get(K k){
		DoublyLinkedlistNode<K, V> node = map.get(k);
		if(node != null)
		{
			setHead(node);
			return node.val;	
		}
		return null;
	}
	
	public void set(K k, V v){
		DoublyLinkedlistNode<K, V> node = new DoublyLinkedlistNode<K, V> (k, v);
		if(count < capacity)
		{
			map.put(k, node);
			setHead(node);
			count++;
		}
		else
		{
			//Remove oldest node which is rear;
			map.remove(rear.key);
			
			if(rear.prev != null)
			{
				rear.prev.next = null;
				rear = rear.prev;
			}
			map.put(k, node);
			setHead(node);
		}
	}
}

class DoublyLinkedlistNode<K,V>{
	K key;
	V val;
	DoublyLinkedlistNode<K, V> prev, next;
	
	public DoublyLinkedlistNode(K k, V v)
	{
		this.key = k;
		this.val = v;
	}
}
