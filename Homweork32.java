import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

class TreeSetTread {
	TreeSet<Integer> treeset;
	ArrayList<Integer> high = new ArrayList<Integer>();
	ArrayList<Integer> low = new ArrayList<Integer>();
	public TreeSetTread() {
		treeset = new TreeSet<Integer>();
	}
	public void print(ArrayList<Integer> list) {
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + " ");
		}
		System.out.println();
	}
	public synchronized void insertVal(int val) {
		treeset.add(val);
		if (high.size() < 5) {
			high.add(val);
		}
		else if (high.size() == 5 && !high.contains(val)) {
			for (int i = 0; i < high.size(); i++) {
				int num = high.get(i);
				if (val > num) {
					high.set(i, val);
					break;
				}
			}
		}
		if (low.size() < 5) {
			low.add(val);
		}
		else if (low.size() == 5 && !low.contains(val)) {
			for (int i = low.size() - 1; i >= 0 ; i--) {
				int num = low.get(i);
				if (val < num) {
					low.set(i, val);
					break;
				}
			}
		}
		Collections.sort(low);
		Collections.sort(high);
		print(low);
		print(high);
	}
	
	public synchronized int large() {
		return high.get(4);
	}
	public synchronized int small() {
		return low.get(0);
	}
}

public class Homweork32 {
	private static Thread[] threadArray;
	public static void main(String [] args) {
		TreeSetTread treeset = new TreeSetTread();
		long start = System.currentTimeMillis();
		threadArray = new Thread[8];
		for (int i = 0; i < threadArray.length; i++) {
			final int threadi = i;
			threadArray[threadi] = new Thread(new Runnable() {
				public void run() {
					AtomicInteger counter = new AtomicInteger(0);
					while (counter.get() < 60) {
						int random = (int)(Math.random() * (70 - (-100) + 1) + -100 );
						treeset.insertVal(random);
						counter.getAndIncrement();
						if (counter.get() % 10 == 0) {
							System.out.println(treeset.large() - treeset.small());
						}
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
