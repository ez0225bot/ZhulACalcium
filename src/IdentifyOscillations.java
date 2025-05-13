import java.util.List;

public class IdentifyOscillations {

    // Method to detect oscillations for a single Cell based on its intensity changes over time
    public int detectOscillations(List<Integer> cellIntensities) {
        if (cellIntensities == null || cellIntensities.size() < 3) {
            return 0;  // Need at least 3 values to detect an oscillation
        }

        int oscillationCount = 0;
        boolean isIncreasing = cellIntensities.get(1) > cellIntensities.get(0); // Initial trend

        for (int i = 1; i < cellIntensities.size(); i++) {
            int previousIntensity = cellIntensities.get(i - 1);
            int currentIntensity = cellIntensities.get(i);

            if (isIncreasing && currentIntensity < previousIntensity) {
                // trend changed from High to Low
                isIncreasing = false;
            } else if (!isIncreasing && currentIntensity > previousIntensity) {
                // trend changed from Low to High = count as an oscillation
                oscillationCount++;
                isIncreasing = true;
            }
        }

        return oscillationCount;
    }
}
