import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Grapher extends JPanel {
    private List<Integer> values; // Data points to graph

    public Grapher(List<Integer> values) {
        this.values = values;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int padding = 40; // Space for axis labels

        // Draw Axes
        g2.setColor(Color.BLACK);
        g2.drawLine(padding, height - padding, width - padding, height - padding); // X-axis
        g2.drawLine(padding, height - padding, padding, padding); // Y-axis

        // Add Axis Labels
        g2.setFont(new Font("Arial", Font.BOLD, 14));
        g2.drawString("Time (frames)", width / 2 - 40, height - 10); // X-axis label

        // Rotate and draw Y-axis label
        g2.rotate(-Math.PI / 2);
        g2.drawString("Light Intensity", -height / 2, 15);
        g2.rotate(Math.PI / 2); // Rotate back

        if (values.isEmpty()) return;

        int numPoints = values.size();
        int stepX = (width - 2 * padding) / (numPoints - 1);
        int maxY = values.stream().max(Integer::compare).orElse(1);

        // Plot points and lines
        for (int i = 0; i < numPoints - 1; i++) {
            int x1 = padding + i * stepX;
            int y1 = height - padding - (values.get(i) * (height - 2 * padding) / maxY);
            int x2 = padding + (i + 1) * stepX;
            int y2 = height - padding - (values.get(i + 1) * (height - 2 * padding) / maxY);

            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

}
