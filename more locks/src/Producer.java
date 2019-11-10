import java.util.Random;

public class Producer extends Thread{
    private Buffer buffer;
    private int size;
    private Random generator = new Random();
    public Producer(Buffer buffer,int size) {
        this.buffer = buffer;
        this.size = size;
    }

    @Override
    public void run() {
        try {
            while (true){
                buffer.produce(generator.nextInt(size-1)+1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
