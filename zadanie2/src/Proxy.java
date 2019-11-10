public class Proxy {
    private ActivationQueue activationQueue;
    //private ExecutorService executor;

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
    public MethodRequest produce(int numberOfElements) {
        MethodRequest request = new MethodRequest("produce", numberOfElements);
        activationQueue.enqueue(request);
        return request;
    }

    public MethodRequest consume(int numberOfElements) {
        MethodRequest request = new MethodRequest("consume", numberOfElements);
        activationQueue.enqueue(request);
        return request;
    }

    public boolean checkRequest(MethodRequest request) {
        return request.isDone();
    }
}
