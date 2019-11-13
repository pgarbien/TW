public class Proxy {
    private ActivationQueue activationQueue;
    //private ExecutorService executor;
    MethodRequest currentMethodRequest;
    public Proxy(ActivationQueue activationQueue) {
        //this.executor = Executors.newSingleThreadExecutor();
        this.activationQueue = activationQueue;
    }

    /*public Future<Boolean> produce(int numberOfElements) {
        return executor.submit(() -> {
            MethodRequest request = new MethodRequest("produce", numberOfElements);
            activationQueue.enqueue(request);
            while (!request.isDone()) {
                Thread.sleep(1);
            }
            return Boolean.TRUE;
        });
    }*/
    public void produce(int numberOfElements) {
        MethodRequest request = new MethodRequest("produce", numberOfElements);
        activationQueue.enqueue(request);
        currentMethodRequest= request;
    }

    public void consume(int numberOfElements) {
        MethodRequest request = new MethodRequest("consume", numberOfElements);
        activationQueue.enqueue(request);
        currentMethodRequest = request;
    }

    public boolean checkRequest() {
        return currentMethodRequest.isDone();
    }
}
