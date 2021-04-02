package producerConsumer;

import java.util.*;


class Processor {

	private List<Integer> list = new ArrayList<>();
	private final int LIMIT = 5;
	private final int BOTTOM = 0;
	
	private int value = 0;

    //extrinsic lock
    private final Object lock = new Object();
	
	public void producer() throws InterruptedException {

		synchronized (lock) {
			
			while(true) {
				
				if( list.size() == LIMIT ) {
					System.out.println("Waiting for removing items from the list...");
					//releases the lock and puts current thread in waiting state
                    lock.wait();
				
                } else {
					System.out.println("Adding: "+value);
					list.add(value);
					value++;
					//notifies the other threads and wakes them up from waiting state
                    lock.notify();
				}
				
				Thread.sleep(500);
			}
		}
	}

	public void consumer() throws InterruptedException {

		synchronized (lock) {
			
			while(true) {
				
				if( list.size() == BOTTOM ) {
					System.out.println("Waiting for adding items to the list...");
					lock.wait();
				} else {
					System.out.println("Removed: "+list.remove(--value));
					lock.notify();
				}
				
				Thread.sleep(500);
			}
			
		}
		
	}
}

public class App {

    //create processor object
	static Processor processor = new Processor();

	public static void main(String[] args) {

        // run the threads on processor object
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					processor.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t1.start();
		t2.start();
	}
}
