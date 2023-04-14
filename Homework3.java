import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Node {
	public Integer data;
	int val;
	Node next;
	boolean marked;
	Lock lock; 

	public Node(int temp) {
		data = temp;
		val = data.hashCode();
		next = null;
		marked = false;
		lock = new ReentrantLock();
	}
	public void lockNode() {
		lock.lock();

	}
	public void unlockNode() {
		lock.unlock();

	}

}

class List {
	public Node head;
	public Node tail;
	//	private Lock lock = new ReentrantLock();
	public List() {
		head = new Node(Integer.MIN_VALUE);
		head.val = Integer.MIN_VALUE;
		tail = new Node(Integer.MAX_VALUE);
		tail.val = Integer.MAX_VALUE;
		head.next = tail;
	}

	private boolean val(Node temp, Node next) {
		return  !temp.marked && !next.marked && temp.next == next;
	}

	public boolean add(Integer item) {
		int key = (int)(Math.random() * Integer.MAX_VALUE - 1);
		while (true) {
			Node temp = head;
			Node next = head.next;
			while (next.val < key) {
				temp = next; next = next.next;
			}
			try {
				temp.lockNode();
				next.lockNode();
				try {
					if (val(temp, next)) {
						if (next.val != key) {
							Node node = new Node(item);
							node.next = next;
							temp.next = node;
							
							return true;
						} else {
							return false;
						}
					}
				} 
				finally {
					next.unlockNode();
				}
			} 
			finally {
				temp.unlockNode();
			}
		}
	}


	public boolean remove() {
		if (head.next == tail) {
			return false;
		}
		int val = head.next.val;
		while (true) {
			Node temp = head;
			Node next = head.next;
			while (next.val < val) {
				temp = next; 
				next = next.next;
			}
			try {
				temp.lockNode();
				next.lockNode();
				try {
					if (val(temp, next)) {
						if (next.val == val) {
							next.marked = true;
							temp.next = next.next;
							return true;
						} else {
							return false;
						}
					}
				} finally {
					next.unlockNode();
				}
			} finally {
				temp.unlockNode();
			}
		}
	}



}

public class Homework3 {
	private static int number = 500000;
	private static int nums = Integer.MAX_VALUE;
	private static int numthreads = 4;
	private static Thread[] threadArray;
	private static AtomicInteger counter = new AtomicInteger(0);
	private static List list = new List();
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		threadArray = new Thread[numthreads];
		for (int i = 0; i < threadArray.length; i++) {
			final int threadi = i;
			threadArray[threadi] = new Thread(new Runnable() {
				public void run() {
					int random = (int)(Math.random() * nums);
					while (counter.get() < number) {
						list.add(random);
						list.remove();
						random = (int)(Math.random() * nums);
						counter.getAndIncrement();
					}
				}
			});
		}
		for (int i = 0; i < threadArray.length; i++) {
			threadArray[i].start();
		}
		for (int i = 0; i < threadArray.length; i++) {
			try {
				threadArray[i].join();
			} catch (InterruptedException e) {
				System.out.println(e);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println((end - start)/1000.00 + " seconds");
	}
}
