import java.util.Random;

public class Consumer extends Thread {
    private int size;
    private Random generator = new Random();
    private Proxy proxy;

    public Consumer(int size, ActivationQueue activationQueue) {
        this.size = size;
        this.proxy = new Proxy(activationQueue);
    }

    @Override
    public void run() {
        while (true) {
            proxy.consume(generator.nextInt(size) + 1);
            while (!proxy.checkRequest()) {
                calculate();
            }
        }
    }

    private void calculate() {
        long start = System.currentTimeMillis();
        int result = 103231;
        while (System.currentTimeMillis() < start + 100) {
            result = (result * 1311) % 2000;
        }
    }
}
