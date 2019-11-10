import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(10);
        List<Thread> threads = new ArrayList<>();
        int numberOfProducers = 1;
        int numberOfConsumers = 50;
        for (int i = 0; i < numberOfProducers; i++) {
            threads.add(new Producer(buffer));
        }
        for (int i = 0; i < numberOfConsumers; i++) {
            threads.add(new Consumer(buffer));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
        // dodac aby byla tablica w zasobie a nie jeden element
        /*
        Thread producer = new Producer(buffer);
        Thread consumer = new Consumer(buffer);
        consumer.start();
        producer.start();


        producer.join();
        consumer.join();
        */

    }

}
