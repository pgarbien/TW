import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private LinkedList<Integer> list;
    private int size;
    private Lock lock = new ReentrantLock();
    private Condition listFull = lock.newCondition();
    private Condition listEmpty = lock.newCondition();

    public Buffer(int size) {
        this.list = new LinkedList<>();
        this.size = size;
    }

    public void produce(int val) throws InterruptedException {
        lock.lock();
        try {
            while (list.size() == size) {
                listFull.await();
            }
            System.out.println("producing value " + list.size());
            list.add(list.size());
            listEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume() throws InterruptedException {
        lock.lock();
        try {
            while (list.size() == 0)
                listEmpty.await();
            list.removeFirst();

            System.out.println("consuming value");
            listFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
