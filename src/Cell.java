public class Cell {
    private int[][] matrix;
    private int x; // X-coordinate of the top-left corner or center
    private int y; // Y-coordinate of the top-left corner or center
    private int width; // Width of the light patch
    private int height;
    private int averageIntensity;// Height of the light patch


    // Constructor
    public Cell(int x, int y, int width, int height, int[][] matrix, int averageIntensity) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.matrix = matrix;
        this.averageIntensity = averageIntensity;
    }

    // Getter methods
    public int getX()
    { return x; }
    public int getY()
    { return y; }
    public int getWidth()
    { return width; }
    public int getHeight()
    { return height; }
    public int[][] getMatrix()
    {return matrix;}
    public int getAverageIntensity()
    {return averageIntensity;}


    // Setter methods
    public void setX(int x)
    { this.x = x; }
    public void setY(int y)
    { this.y = y; }
    public void setWidth(int width)
    { this.width = width; }
    public void setHeight(int height)
    { this.height = height; }
    public void setMatrix(int[][] matrix)
    {this.matrix= matrix;}
    public void setAverageIntensity()
    {this.averageIntensity=averageIntensity;}

    // To String method for easy printing
    @Override
    public String toString() {
        return "[x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
    }

}

