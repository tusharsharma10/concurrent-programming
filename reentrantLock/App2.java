package reentrantLock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class App2 {

    private static int counter = 0;
    // locking mechanism
    private static Lock custLock = new ReentrantLock();

    public static void increment() {

        try {

            custLock.lock();

            for (int i = 0; i < 10000; i++) {

                counter++;
            }
        }

        finally {
            custLock.unlock();
        }
    }

    public static void main(String[] args) {

        Thread t1 = new Thread(new Runnable() {
            public void run() {
                increment();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            public void run() {
                increment();
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(counter);

    }
}