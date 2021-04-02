package deadlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {
 
    //need atleast 2 locks for deadlock to occur
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    
    public static void main(String[] args) {
        
        Deadlock deadlock = new Deadlock();

        Thread t1 = new Thread(()->{

            try {
                deadlock.worker1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread t2 = new Thread(()->{

            try {
                deadlock.worker2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();

    }


    public void worker1() throws InterruptedException{

        lock1.lock();
        System.out.println("Thread 1 acquires lock1");

        Thread.sleep(300);
        lock2.lock();
        System.out.println("Thread 1 acquires lock2");

        lock1.unlock();
        lock2.unlock();
    }

    public void worker2() throws InterruptedException{
        lock2.lock();
        System.out.println("Thread 2 acquires lock2");

        Thread.sleep(300);
        lock1.lock();
        System.out.println("Thread 2 acquires lock1");

        lock2.unlock();
        lock1.unlock();
    }

}
