import java.util.HashMap;
import java.util.Map;
public class Board {
    private Map<Integer, Line> lines;
    private int x_dimension, y_dimension;

    public Board(int xDimension, int yDimension) {
        x_dimension = xDimension;
        y_dimension = yDimension;
        this.lines = new HashMap<>();
    }

    public boolean isBoxCompleted(int x, int y) {
        //box identified by the upper left point
        Integer upperSideHash = new Line(x, y, x+1, y).hashCode();
        Integer lowerSideHash = new Line(x, y+1, x+1, y+1).hashCode();
        Integer leftSideHash = new Line(x, y, x, y+1).hashCode();
        Integer rightSideHash = new Line(x+1, y, x+1, y+1).hashCode();


        return lines.containsKey(upperSideHash) &&
                lines.containsKey(lowerSideHash) &&
                lines.containsKey(leftSideHash) &&
                lines.containsKey(rightSideHash);
    }

    private boolean isBoardFull() {
        // since we check the validity of every line drawn onto the board we can check if the board has been completely filled
        // (i.e. the games has ended) by simply checking if the number of lines is 2*n*m - n - m which is the amount of possible lines
        // for a n*m board
        return lines.size() == (2*x_dimension*y_dimension)-x_dimension-y_dimension;
    }

    public void addLine(Line line) {
        if (Math.pow((line.x2() - line.x1()), 2) + Math.pow((line.y2() - line.y1()), 2) != 1) {
            throw new IllegalArgumentException("Illegal line");
        } else this.lines.put(new Line(null, line).hashCode(), line);
        // the hashcode is calculated based on the line stripped of its color in order to avoid putting to lines of different colors in the same place
    }

    public int getX_dimension() {
        return x_dimension;
    }

    public int getY_dimension() {
        return y_dimension;
    }
}
