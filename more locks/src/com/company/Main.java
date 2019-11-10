package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer(20);
        List<Thread> threads = new ArrayList<>();
        int numberOfProducers = 20;
        int numberOfConsumers = 20;
        for (int i = 0; i < numberOfProducers; i++) {
            threads.add(new Producer(buffer,9));
        }
        for (int i = 0; i < numberOfConsumers; i++) {
            threads.add(new Consumer(buffer,9));
        }
        Long start = System.currentTimeMillis();
        for (Thread t : threads) {
            t.start();
        }

        /*for (Thread t : threads){
            t.join();
        }*/
        while (buffer.timesProduced < 10000){

        }
    }

}
