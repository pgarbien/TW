package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Buffer buffer = new Buffer();
        List<Thread> threads = new ArrayList<>();
        int numberOfThreads = 2;
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Producer(buffer));
            threads.add(new Consumer(buffer));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads){
            t.join();
        }
        // dodac aby byla tablica w zasobie a nie jeden element
        /*
        Thread producer = new Producer(buffer);
        Thread consumer = new Consumer(buffer);
        consumer.start();
        producer.start();


        producer.join();
        consumer.join();
        */

        System.out.println(buffer.getValue());
    }

}
