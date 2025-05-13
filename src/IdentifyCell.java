import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class IdentifyCell {

    // Directions for 8-neighbor connectivity
    private static final int[][] DIRECTIONS = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}, // Up, Down, Left, Right
            {-1, -1}, {-1, 1}, {1, -1}, {1, 1} // Diagonal
    };

    public static List<Cell> findLightPatches(int[][] matrix, int threshold) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        boolean[][] visited = new boolean[rows][cols];
        List<Cell> patches = new ArrayList<>();

        // Traverse the matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j] && matrix[i][j] > threshold) {
                    patches.add(floodFill(matrix, visited, i, j, threshold));
                }
            }
        }
        return patches;
    }

    private static Cell floodFill(int[][] matrix, boolean[][] visited, int startX, int startY, int threshold) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});
        visited[startX][startY] = true;

        int minX = startX, minY = startY, maxX = startX, maxY = startY;
        int totalIntensity = 0, count = 0;

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0], y = point[1];

            // Update bounding box
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);

            totalIntensity += matrix[x][y];
            count++;

            // Check 8-neighbor connectivity
            for (int[] dir : DIRECTIONS) {
                int newX = x + dir[0], newY = y + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols &&
                        !visited[newX][newY] && matrix[newX][newY] > threshold) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        // Calculate dimensions
        int width = maxX - minX + 1;
        int height = maxY - minY + 1;

        // Compute center coordinates
        int centerX = minX + width / 2;
        int centerY = minY + height / 2;

        // Ensure centerX and centerY are within bounds
        centerX = Math.max(0, Math.min(centerX, rows - 1));
        centerY = Math.max(0, Math.min(centerY, cols - 1));

        // Get intensity at the center coordinates
        int sumIntensity = matrix[centerX][centerY];

        // Calculate average intensity of the whole cell
        int avgIntensity = (count > 0) ? (totalIntensity / count) : 0;

        return new Cell(centerX, centerY, width, height, matrix, avgIntensity);
    }




    public ArrayList<Integer> getCellIntensities(Video video, Cell targetCell, int threshold) {
        ArrayList<Integer> cellIntensities = new ArrayList<>();
        int frameCount = video.getFrameCount();

        for (int i = 0; i < frameCount; i++) {
            int[][] matrix = video.getFrame(i);

            // Extract the submatrix for the target cell using its coordinates and size
            int startX = targetCell.getX();
            int startY = targetCell.getY();
            int width = targetCell.getWidth();
            int height = targetCell.getHeight();

            int sumIntensity = 0;
            int pixelCount = 0;

            for (int x = startX; x < startX + width; x++) {
                for (int y = startY; y < startY + height; y++) {
                    if (x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length) {
                        // Disregard pixels with intensity below the threshold
                        if (matrix[x][y] >= threshold) {
                            sumIntensity += matrix[x][y];
                            pixelCount++;
                        }
                    }
                }
            }

            // Compute the average intensity for this cell in the current frame, if pixelCount > 0
            int cellAvgIntensity = (pixelCount > 0) ? sumIntensity / pixelCount : 0;
            cellIntensities.add(cellAvgIntensity);
        }

        return cellIntensities;
    }






    // Test function

    }

