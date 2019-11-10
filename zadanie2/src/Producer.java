import java.util.Random;

public class Producer extends Thread {
    private int size;
    private Random generator = new Random();
    private Proxy proxy;

    public Producer(int size, ActivationQueue activationQueue) {
        this.size = size;
        this.proxy = new Proxy(activationQueue);
    }

    @Override
    public void run() {
        while (true) {
            MethodRequest request = proxy.produce(generator.nextInt(size) + 1);
            while (!proxy.checkRequest(request)) {
                calculate();
            }
        }
    }

    private void calculate() {
        Long start = System.currentTimeMillis();
        int result = 103231;
        while (System.currentTimeMillis() < start + 100) {
            result = (result * 1311) % 2000;
        }
    }
}
