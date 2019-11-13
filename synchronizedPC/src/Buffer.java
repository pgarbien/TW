import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Buffer {
    private LinkedList<Integer> list;
    private int size;
    private int sleepTime;
    private ReentrantLock lock = new ReentrantLock();
    private Condition firstProducer = lock.newCondition();
    private Condition otherProducers = lock.newCondition();
    private Condition firstConsumer = lock.newCondition();
    private Condition otherConsumers = lock.newCondition();

    private boolean firstProducerWaiting = false;
    private boolean firstConsumerWaiting = false;

    public Long start;
    public Long timesProduced;
    public Long timesConsumed;

    public Buffer(int size, int sleepTime) {
        this.list = new LinkedList<>();
        this.size = size;
        this.sleepTime = sleepTime;
        timesConsumed = 0L;
        timesProduced = 0L;
        start = System.currentTimeMillis();
    }

    public void produce(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            if (firstProducerWaiting) {
                otherProducers.await();
            }
            firstProducerWaiting = true;

            while (size - list.size() < numberOfElements) {
                firstProducer.await();
            }
            for (int i = 0; i < numberOfElements; i++) {
                list.add(list.size());
            }
            firstProducerWaiting = false;
            sleep(sleepTime);
            otherProducers.signal();
            firstConsumer.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            if (firstConsumerWaiting)
                otherConsumers.await();
            firstConsumerWaiting = true;
            while (list.size() < numberOfElements)
                firstConsumer.await();
            for (int i = 0; i < numberOfElements; i++) {
                list.removeFirst();
            }
            firstConsumerWaiting = false;
            timesConsumed++;
            if (timesConsumed > 1_000_000) {
                System.out.println(System.currentTimeMillis() - start);
                System.exit(1);
            }
            sleep(sleepTime);
            otherConsumers.signal();
            firstProducer.signal();
        } finally {
            lock.unlock();
        }
    }
}
