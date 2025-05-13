import org.opencv.core.Mat;
import java.util.LinkedList;

public class Video {
    private LinkedList<int[][]> frames;
    private String fileName;

    public Video() {
        this.frames = new LinkedList<int[][]>();
    }

    public Video(String file) {
        fileName = file;
        this.frames = new LinkedList<int[][]>();
    }

    public void addFrame(int[][] frameData) {
        frames.add(frameData);  // Ensure deep copy
    }

    public int getFrameCount() {
        return frames.size();
    }

    public int[][] getFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            throw new IndexOutOfBoundsException("Invalid frame index");
        }
        return frames.get(index);
    }


}
