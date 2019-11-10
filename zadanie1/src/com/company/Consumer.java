package com.company;

import java.util.Random;

public class Consumer extends Thread {
    private Buffer buffer;
    private int size;
    private Random generator = new Random();

    public Consumer(Buffer buffer, int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.consume(generator.nextInt(size) + 1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
