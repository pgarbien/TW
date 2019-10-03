package com.company;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException{
        for (int j = 0; j < 100; j++) {
	        int numberOfThreads = 100;
	        int numberOfOperations = 10000;
	        Container container = new Container();
            ArrayList<Thread> threads = new ArrayList<>();

            for (int i = 0; i< numberOfThreads; i++){
                threads.add(new IncrementThread(container, numberOfOperations));
                threads.add(new DecrementThread(container, numberOfOperations));
            }

            for (Thread thread: threads){
                thread.start();
            }

            for (Thread thread:threads){
                thread.join();
            }
            System.out.println(container.getValue());
        }
    }
}
