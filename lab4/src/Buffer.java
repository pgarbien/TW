import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private LinkedList<Integer> list;
    private int size;

    private ReentrantLock lock = new ReentrantLock();
    private Condition firstProducer = lock.newCondition();
    private Condition otherProducers = lock.newCondition();
    private Condition firstConsumer = lock.newCondition();
    private Condition otherConsumers = lock.newCondition();

    public Buffer(int size) {
        this.list = new LinkedList<>();
        this.size = size;
    }

    public void produce(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            if (lock.hasWaiters(firstProducer))
                otherProducers.await();
            System.out.println("trying to produce " + numberOfElements);
            while (size - list.size() < numberOfElements) {
                firstProducer.await();
            }
            for (int i = 0; i < numberOfElements; i++) {
                list.add(list.size());
            }

            System.out.println("producing " + numberOfElements + ", buffer size: " + list.size());
            otherProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            if (lock.hasWaiters(firstConsumer))
                otherConsumers.await();
            System.out.println("trying to consume " + numberOfElements);

            while (list.size() < numberOfElements)
                firstConsumer.await();
            for (int i = 0; i < numberOfElements; i++) {
                list.removeFirst();
            }

            System.out.println("consuming " + numberOfElements + ", buffer size: " + list.size());
            otherConsumers.signal();
            firstProducer.signal();
        } finally {
            lock.unlock();
        }
    }
}
