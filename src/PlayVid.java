import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlayVid {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }  // Load OpenCV library

    private static LinkedList<Mat> frameList;
    private static Video video;

    public PlayVid(String videoPath) {
        video = new Video();
        frameList = new LinkedList<>();
        loadFrames(videoPath);
    }

    private void loadFrames(String videoPath) {
        VideoCapture videoCapture = new VideoCapture(videoPath);

        if (!videoCapture.isOpened()) {
            System.out.println("Error: Cannot open video file!");
            return;
        }

        Mat frame = new Mat();
        while (videoCapture.read(frame)) {
            frameList.add(frame.clone());  // Store a copy of the frame
        }

        videoCapture.release();
        System.out.println("Frames loaded: " + frameList.size());
    }

    public LinkedList<Mat> getFrameList() {
        return frameList;
    }

    public void displayFrames() {
        for (Mat frame : frameList) {
            HighGui.imshow("Frame", frame);
            HighGui.waitKey(30);  // Wait for 30 ms before displaying the next frame
        }
        HighGui.destroyAllWindows();
    }

    public int[][] convertToReducedMatrix(Mat frame, int scaleFactor) {
        int rows = frame.rows();
        int cols = frame.cols();

        // New dimensions after scaling
        int newRows = rows / scaleFactor;
        int newCols = cols / scaleFactor;

        int[][] reducedMatrix = new int[newRows][newCols];

        for (int i = 0; i < newRows; i++) {
            for (int j = 0; j < newCols; j++) {
                int sum = 0;
                int count = 0;

                // Sum all values in the block
                for (int x = 0; x < scaleFactor; x++) {
                    for (int y = 0; y < scaleFactor; y++) {
                        int origRow = i * scaleFactor + x;
                        int origCol = j * scaleFactor + y;

                        if (origRow < rows && origCol < cols) { // Avoid out-of-bounds
                            sum += (int) frame.get(origRow, origCol)[0];
                            count++;
                        }
                    }
                }

                // Compute the average value for the block
                reducedMatrix[i][j] = sum / count;
            }
        }
        return reducedMatrix;
    }


    public void loadFrameMatrices() {
        for (int i = 0; i < frameList.size(); i++) {
            //System.out.println("Frame " + i + " Matrix:");
            int[][] matrix = convertToReducedMatrix(frameList.get(i), 15);
            video.addFrame(matrix);
            //printMatrix(matrix);
        }
    }

    void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int pixel : row) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String videoPath = "output_video.avi";  // Change this to your AVI file path

        PlayVid loader = new PlayVid(videoPath);
        loader.displayFrames();

        loader.loadFrameMatrices();

        // test line
        System.out.println("Example Frame 1:");
        loader.printMatrix(video.getFrame(1));

        IdentifyCell bob = new IdentifyCell();

        List<Cell> patches = bob.findLightPatches(video.getFrame(1), 15);
        IdentifyOscillations charlie = new IdentifyOscillations();



        int oscillationCount = 0;
        System.out.println("Detected Cells:");
        int count = 0;
        for (Cell patch : patches) {
            count++;
            List<Integer> cellIntensities = bob.getCellIntensities(video, patch, 10);

            System.out.println(cellIntensities);
            System.out.print("Cell " + count + " ");
            System.out.println(patch);
            System.out.println("Oscillations: " + charlie.detectOscillations(cellIntensities));
            oscillationCount += charlie.detectOscillations(cellIntensities);

            // Ensure the graph is opened in the event dispatch thread
            final int finalCount = count;
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Graph of Cell " + finalCount);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Prevents closing all windows
                frame.setSize(600, 400);
                frame.setContentPane(new Grapher(cellIntensities));
                frame.setVisible(true);
            });

            // pause to allow graphs to be properly displayed
            try {
                Thread.sleep(500); // delay length
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        JFrame frame = new JFrame("Final Overview");
        JOptionPane.showMessageDialog(frame, "Average number of Oscillations: " + oscillationCount / (count));
        JOptionPane.showMessageDialog(frame, "Total number of Cells: " +(count));
        JOptionPane.showMessageDialog(frame,"Thanks for visiting!");
        System.exit(0);



    }
}


