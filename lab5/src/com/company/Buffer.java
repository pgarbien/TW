package com.company;

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

    private boolean firstProducerWaiting = false;
    private boolean firstConsumerWaiting = false;

    public Long start;
    public Long timesProduced;
    public Long timesConsumed;

    public Buffer(int size) {
        this.list = new LinkedList<>();
        this.size = size;

        timesConsumed = 0L;
        timesProduced = 0L;
        start = System.currentTimeMillis();
    }

    public void produce(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            if (firstProducerWaiting){
                otherProducers.await();
            }
            firstProducerWaiting = true;

            //System.out.println("trying to produce " + numberOfElements);
            while (size - list.size() < numberOfElements) {
                firstProducer.await();
            }
            for (int i = 0; i < numberOfElements; i++) {
                list.add(list.size());
            }
            firstProducerWaiting = false;
            //System.out.println("producing " + numberOfElements + ", buffer size: " + list.size());
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
            //System.out.println("trying to consume " + numberOfElements);
            firstConsumerWaiting = true;
            while (list.size() < numberOfElements)
                firstConsumer.await();
            for (int i = 0; i < numberOfElements; i++) {
                list.removeFirst();
            }
            firstConsumerWaiting = false;
            //System.out.println("consuming " + numberOfElements + ", buffer size: " + list.size());
            timesConsumed++;
            if(timesConsumed > 1_000_000){
                System.out.println(System.currentTimeMillis() - start);
                System.exit(1);
            }
            otherConsumers.signal();
            firstProducer.signal();
        } finally {
            lock.unlock();
        }
    }
}
