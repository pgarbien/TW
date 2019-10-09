package com.company;

import java.util.Random;

public class Producer extends Thread{
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true){
                buffer.produce(10);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
