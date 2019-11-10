package com.company;

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

    public void produce(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("trying to produce " + numberOfElements);
            while (list.size() + numberOfElements > size) {
                listFull.await();
            }
            for (int i = 0; i < numberOfElements; i++) {
                list.add(list.size());
            }

            System.out.println("producing " + numberOfElements + ", buffer size: " + list.size());
            listEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consume(int numberOfElements) throws InterruptedException {
        lock.lock();
        try {
            System.out.println("trying to consume " + numberOfElements);
            while (list.size() < numberOfElements)
                listEmpty.await();
            for (int i = 0; i < numberOfElements; i++) {
                list.removeFirst();
            }

            System.out.println("consuming " + numberOfElements+ ", buffer size: " + list.size());
            listFull.signal();
        } finally {
            lock.unlock();
        }
    }
}
