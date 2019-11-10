public class DecrementThread extends Thread {
    private Container container;
    private int numberOfOperations;

    public DecrementThread(Container container, int numberOfOperations) {
        this.container = container;
        this.numberOfOperations = numberOfOperations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfOperations; i++) {
            container.decrement();
        }
    }
}
