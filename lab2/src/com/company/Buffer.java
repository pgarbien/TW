package com.company;

public class Buffer {
    private int value;

    public Buffer() {
        this.value = 0;
    }

    public int getValue() {
        return value;
    }

    public synchronized void produce(int val) throws InterruptedException{
        while (value != 0 ){
            wait();
        }
        System.out.println("producing value " + val);
        value = val;
        notifyAll();
    }

    public synchronized void consume() throws InterruptedException{
        while (value == 0)
            wait();
        value = 0;
        System.out.println("consuming value, setting to 0");
        notifyAll();
    }
}
