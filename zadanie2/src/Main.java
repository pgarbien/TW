import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        int numberOfProducers = 4;
        int numberOfConsumers = 4;
        int MAX_SIZE = 4;
        ActivationQueue activationQueue = new ActivationQueue();
        Scheduler scheduler = new Scheduler(activationQueue);
        for (int i = 0; i < numberOfProducers; i++) {
            threads.add(new Producer(MAX_SIZE, activationQueue));
        }
        for (int i = 0; i < numberOfConsumers; i++) {
            threads.add(new Consumer(MAX_SIZE, activationQueue));
        }
        scheduler.start();
        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        scheduler.join();
    }
}
