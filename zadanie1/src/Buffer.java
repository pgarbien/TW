import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private Integer[] array;
    private LinkedList<Integer> producerTickets;
    private LinkedList<Integer> consumerTickets;

    private ReentrantLock lock = new ReentrantLock();
    private Condition producers = lock.newCondition();
    private Condition consumers = lock.newCondition();

    public Buffer(int size) {
        this.array = new Integer[size];
        this.producerTickets = new LinkedList<>();
        this.consumerTickets = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            array[i] = 0;
            producerTickets.add(i);
        }
    }

    public void produce(int numberOfElements) throws InterruptedException {
        Integer[] tickets = producerGetTickets(numberOfElements);
        changeBuffer(tickets);
        producerReturnTickets(tickets);
    }

    public void consume(int numberOfElements) throws InterruptedException {
        Integer[] tickets = consumerGetTickets(numberOfElements);
        changeBuffer(tickets);
        consumerReturnTickets(tickets);
    }

    public Integer[] producerGetTickets(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            while (producerTickets.size() < numberOfElements) {
                producers.await();
            }

            Integer[] givenTickets = new Integer[numberOfElements];
            int numberOfGivenTickets = 0;
            while (numberOfGivenTickets < numberOfElements) {
                givenTickets[numberOfGivenTickets] = producerTickets.pop();
                numberOfGivenTickets++;
            }

            Arrays.stream(givenTickets).forEach(ticket -> {
                System.out.println("Giving ticket: " + ticket + " to producer");
            });

            return givenTickets;
        } finally {
            lock.unlock();
        }
    }

    public void producerReturnTickets(Integer[] returnedTickets) {
        lock.lock();
        try {
            Arrays.stream(returnedTickets).forEach(ticket -> {
                consumerTickets.add(ticket);
                System.out.println("Producer returned ticket no " + ticket);
            });
            producers.signal();
            consumers.signal();
        } finally {
            lock.unlock();
        }
    }

    public void changeBuffer(Integer[] tickets) {
        Arrays.stream(tickets).forEach(ticket -> {
            array[ticket] = array[ticket] > 0 ? 0 : 1;
            System.out.println("Changing buffer at: " + ticket + " to value: " + array[ticket]);
        });
    }

    public Integer[] consumerGetTickets(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            while (consumerTickets.size() < numberOfElements) {
                consumers.await();
            }
            Integer[] givenTickets = new Integer[numberOfElements];
            int numberOfGivenTickets = 0;
            while (numberOfGivenTickets < numberOfElements) {
                givenTickets[numberOfGivenTickets] = consumerTickets.pop();
                numberOfGivenTickets++;
            }
            Arrays.stream(givenTickets).forEach(ticket -> {
                System.out.println("Giving ticket: " + ticket + " to consumer");
            });
            return givenTickets;
        } finally {
            lock.unlock();
        }
    }

    public void consumerReturnTickets(Integer[] returnedTickets) {
        lock.lock();
        try {
            Arrays.stream(returnedTickets).forEach(ticket -> {
                producerTickets.add(ticket);
                System.out.println("Consumer returned ticket no " + ticket);
            });
            consumers.signal();
            producers.signal();
        } finally {
            lock.unlock();
        }
    }
}