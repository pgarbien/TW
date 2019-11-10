package com.company;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private LinkedList<Integer> list;
    private int size;

    private ReentrantLock producersLock = new ReentrantLock();
    private ReentrantLock consumersLock = new ReentrantLock();
    private ReentrantLock lock = new ReentrantLock();

    private Condition producers = lock.newCondition();
    private Condition consumers = lock.newCondition();
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
        producersLock.lock();
        try {
            lock.lock();
            try {
                //System.out.println("trying to produce " + numberOfElements);
                while (size - list.size() < numberOfElements) {
                    producers.await();
                }
                for (int i = 0; i < numberOfElements; i++) {
                    list.add(list.size());
                }

                //System.out.println("producing " + numberOfElements + ", buffer size: " + list.size());
                //producers.signal();
                consumers.signal();
            } finally {
                lock.unlock();
            }
        } finally {
            producersLock.unlock();
        }
    }


    public void consume(int numberOfElements) throws InterruptedException {
        consumersLock.lock();
        try {
            lock.lock();
            try {
                //System.out.println("trying to consume " + numberOfElements);
                while (list.size() < numberOfElements)
                    consumers.await();
                for (int i = 0; i < numberOfElements; i++) {
                    list.removeFirst();
                }
                //System.out.println("consuming " + numberOfElements + ", buffer size: " + list.size());
                //consumers.signal();
                timesConsumed++;
                if(timesConsumed > 1_000_000){
                    System.out.println(System.currentTimeMillis() - start);
                    System.exit(1);
                }
                producers.signal();
            } finally {
                lock.unlock();
            }
        } finally {
            consumersLock.unlock();
        }
    }
}
