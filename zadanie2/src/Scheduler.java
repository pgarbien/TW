import java.util.LinkedList;

public class Scheduler extends Thread {
    private ActivationQueue mainQueue;
    private LinkedList<MethodRequest> consumerQueue = new LinkedList<>();
    private LinkedList<MethodRequest> producerQueue = new LinkedList<>();
    private Servant servant = new Servant(10);

    public Scheduler(ActivationQueue mainQueue) {
        this.mainQueue = mainQueue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (consumerQueue.isEmpty() && producerQueue.isEmpty()) {
                    MethodRequest request = mainQueue.dequeue();
                    System.out.println("getting request " + request.getMethodName() + " " + request.getSize());
                    if (request.getMethodName().equals("consume")) {
                        if (servant.getTakenSize() >= request.getSize()) {
                            servant.consume(request.getSize());
                            request.setDone(true);
                            System.out.println("Consuming " + request.getSize() + " done");
                        } else {
                            consumerQueue.add(request);
                            System.out.println("Cannot consume  " + request.getSize() + " putting in queue");
                        }
                    }
                    if (request.getMethodName().equals("produce")) {
                        if (servant.getFreeSize() >= request.getSize()) {
                            servant.produce(request.getSize());
                            request.setDone(true);
                            System.out.println("Producing " + request.getSize() + " done");
                        } else {
                            producerQueue.add(request);
                            System.out.println("Cannot produce  " + request.getSize() + " putting in queue");
                        }
                    }
                } else if (!consumerQueue.isEmpty()) {
                    if (consumerQueue.getFirst().getSize() <= servant.getTakenSize()) {
                        consumerQueue.getFirst().setDone(true);
                        System.out.println("Taking first from consumer queue  " + consumerQueue.getFirst().getSize());
                        servant.consume(consumerQueue.pop().getSize());
                    } else {
                        MethodRequest request = mainQueue.dequeue();
                        if (request.getMethodName().equals("consume")) {
                            consumerQueue.add(request);
                            System.out.println("Another consumer, putting in queue");
                        } else {
                            servant.produce(request.getSize());
                            request.setDone(true);
                            System.out.println("We have consumer in queue, producing ..");
                        }
                    }
                } else if (!producerQueue.isEmpty()) {
                    if (producerQueue.getFirst().getSize() <= servant.getFreeSize()) {
                        producerQueue.getFirst().setDone(true);
                        System.out.println("Taking first from producer queue  " + producerQueue.getFirst().getSize());
                        servant.produce(producerQueue.pop().getSize());
                    } else {
                        MethodRequest request = mainQueue.dequeue();
                        if (request.getMethodName().equals("produce")) {
                            producerQueue.add(request);
                            System.out.println("Another producer, putting in queue");
                        } else {
                            servant.consume(request.getSize());
                            request.setDone(true);
                            System.out.println("We have producer in queue, consuming ..");
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
