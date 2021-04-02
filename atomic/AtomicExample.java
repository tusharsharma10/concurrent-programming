package atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {

    static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {

        AtomicExample ob = new AtomicExample();

        Thread t1 = new Thread(() -> {

            ob.increment();
        });

        Thread t2 = new Thread(() -> {

            ob.decrement();
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

    public void increment() {

        for (int i = 0; i < 10000; i++)

            counter.incrementAndGet();

    }

    public void decrement() {

        for (int i = 0; i < 10000; i++)
            counter.decrementAndGet();

    }

}
