package stopThread;

class Worker implements Runnable {

    private volatile boolean terminated = false;

    @Override
    public void run() {

        while (!terminated) {

            System.out.println("Worker thread is running...");

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }

}

public class App {

    public static void main(String[] args) {
        
        Worker worker = new Worker();

        Thread t1 = new Thread(worker);
        t1.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        worker.setTerminated(true);
        System.out.println("Worker thread is exiting...");
    }
}
