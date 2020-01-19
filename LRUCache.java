import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class LRUCache {

	final Node head = new Node();
	final Node tail = new Node();
	Map<Integer, Node> nodeMap;
	int cacheCapacity;

	public LRUCache(int capacity) {
		nodeMap = new HashMap(capacity);
		this.cacheCapacity = capacity;
		head.next = tail;
		tail.prev = head;
	}

	public void addToHead(Node node) {
		node.next = head.next;
		node.next.prev = node;
		node.prev = head;
		head.next = node;
	}

	public int get(int key) {
//needs changes
		int result = -1;
		Node node = nodeMap.get(key);
		if (node != null) {
			result = node.value;
			remove(node);
			add(node);
		}
		return result;
	}

	public void put(int key, int value) {

		Node node = nodeMap.get(key);
		if (node != null) {
			remove(node);
			node.value = value;
			add(node);
		} else {
			if (nodeMap.size() == cacheCapacity) {
				nodeMap.remove(tail.prev.key);
				remove(tail.prev);
			}
			Node newNode = new Node();
			newNode.key = key;
			newNode.value = value;
			nodeMap.put(key, newNode);
			add(newNode);
		}
	}

	public void add(Node node) {
		Node headNext = head.next;
		head.next = node;
		node.prev = head;
		node.next = headNext;
		headNext.prev = node;
	}

	public void remove(Node node) {
		Node nextNode = node.next;
		Node prevNode = node.prev;
		nextNode.prev = prevNode;
		prevNode.next = nextNode;
	}

	void printValues() {
//		for (Map.Entry<Integer, Node> entry : nodeMap.entrySet()) {
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().value);
//		}
		nodeMap.forEach((k, v) -> System.out.println("Key = " + k + ", Value = " + v.value));
		System.out.println("====================");
	}

	class Node {
		int key;
		int value;
		Node next;
		Node prev;
	}

	public static void main(String[] args) {
		LRUCache cache = new LRUCache(3);
		cache.put(1, 3);
		cache.put(2, 6);
		cache.put(3, 9);
		// Case 1: If Exists value in cache
		cache.get(6);
		cache.printValues();
		// now 1 9 63
		// Case 2: if Not exists and cache size is max filled
		cache.put(3, 12);
		cache.printValues();
	}
}
