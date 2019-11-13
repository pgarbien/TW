import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ActivationQueue {

    private LinkedList<MethodRequest> requests = new LinkedList<MethodRequest>();

    private ReentrantLock lock = new ReentrantLock();
    private Condition isEmpty = lock.newCondition();

    public void enqueue(MethodRequest request) {
        lock.lock();
        try {
            requests.add(request);
            isEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public MethodRequest dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (requests.isEmpty()) {
                isEmpty.await();
            }

            return requests.poll();
        } finally {
            lock.unlock();
        }
    }

}