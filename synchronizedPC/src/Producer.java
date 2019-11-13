import java.util.Random;

public class Producer extends Thread{
    private Buffer buffer;
    private int size;
    private int numberOfCalculations;
    private Random generator = new Random(13);
    public Producer(Buffer buffer, int size, int numberOfCalculations) {
        this.buffer = buffer;
        this.size = size;
        this.numberOfCalculations = numberOfCalculations;
    }

    @Override
    public void run() {
        try {
            while (true){
                buffer.produce(generator.nextInt(size-1)+1);
                for (int i=0; i<numberOfCalculations; i++){
                    Math.sin(i);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
