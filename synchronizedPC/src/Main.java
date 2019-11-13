import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // zmienne programu:
        // liczba procesow, kwant dodatkowego zadania, sleep podczas produkcji, konsumpcji
        int numberOfThreads = Integer.parseInt(args[0]);
        int numberOfOperations = Integer.parseInt(args[1]);
        int sleepTime = Integer.parseInt(args[2]);
        int bufferSize = 100;

        Buffer buffer = new Buffer(bufferSize, sleepTime);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Producer(buffer,bufferSize/2 - 1, numberOfOperations));
        }
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Consumer(buffer,bufferSize/2 - 1, numberOfOperations));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads){
            t.join();
        }
    }

}
