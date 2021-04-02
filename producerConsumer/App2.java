package producerConsumer;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Worker {

    public Lock custLock = new ReentrantLock();
    Condition condition = custLock.newCondition();

    public void producer() throws InterruptedException {

        custLock.lock();

        System.out.println("Producer method");
        condition.await();

        System.out.println("In producer again...");

        custLock.unlock();
    }



    public void consumer() {

        custLock.lock();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer method");
        condition.signal();
        System.out.println("In Consumer again...");
        custLock.unlock();

    }

}

public class App2 {

    public static void main(String[] args) {

        Worker worker = new Worker();

        Thread t1 = new Thread(()->{

            try {
                worker.producer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        Thread t2 = new Thread(()->{

            worker.consumer();

        });

        t1.start();
        t2.start();

    }
}
