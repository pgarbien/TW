public class IncrementThread extends Thread {
    private Container container;
    private int numberOfOperations;

    public IncrementThread(Container container, int numberOfOperations) {
        this.container = container;
        this.numberOfOperations = numberOfOperations;
    }

    @Override
    public void run() {
        for (int i = 0; i < numberOfOperations; i++) {
            container.increment();
        }
    }
}
