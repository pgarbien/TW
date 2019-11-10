package com.company;

import java.util.concurrent.Future;

public class MethodRequest {
    private String methodName;
    private int size;
    private boolean isDone;


    public MethodRequest(String methodName, int size) {
        this.methodName = methodName;
        this.size = size;
        this.isDone = false;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getSize() {
        return size;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
