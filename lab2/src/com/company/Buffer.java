package com.company;

import java.util.LinkedList;
import java.util.List;

public class Buffer {
    private List<Integer> list;
    private int size;

    public Buffer(int size) {
        this.list = new LinkedList<>();
        this.size = size;
    }

    public synchronized void produce(int val) throws InterruptedException {
        while (list.size() == size) {
            wait();
        }
        System.out.println("producing value " + val);
        list.add(val);
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        while (list.size() == 0)
            wait();
        list.remove(0);
        
        System.out.println("consuming value, setting to 0");
        notify();
    }
}
