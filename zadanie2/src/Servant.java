import java.util.LinkedList;

public class Servant {
    private int size;
    private LinkedList<Integer> buffer;

    public Servant(int size) {
        this.size = size;
        this.buffer = new LinkedList<>();
    }

    public int getFreeSize() {
        return size - buffer.size();
    }

    public int getTakenSize() {
        return buffer.size();
    }

    public void produce(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            buffer.add(1);
        }
    }

    public void consume(int numberOfElements) {
        for (int i = 0; i < numberOfElements; i++) {
            buffer.pop();
        }
    }
}
