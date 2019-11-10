import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(10);
        List<Thread> threads = new ArrayList<>();
        int numberOfProducers = 16;
        int numberOfConsumers = 16;
        for (int i = 0; i < numberOfProducers; i++) {
            threads.add(new Producer(buffer, 6));
        }
        for (int i = 0; i < numberOfConsumers; i++) {
            threads.add(new Consumer(buffer, 6));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }
}
