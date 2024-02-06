import java.util.HashSet;
import java.util.Set;
public class Board {
    private Set<Line> lines;
    private int x_dimension, y_dimension;

    public Board(int xDimension, int yDimension) {
        x_dimension = xDimension;
        y_dimension = yDimension;
        this.lines = new HashSet<>();
    }

    public boolean checkCompletedBox(int x, int y) {
        //box identified by the upper left point
        Line upper = new Line(x, y, x+1, y);
        Line lower = new Line(x, y+1, x+1, y+1);
        Line left = new Line(x, y, x, y+1);
        Line right = new Line(x+1, y, x+1, y+1);

        return lines.stream().anyMatch(line -> line.equalsIgnoringPlayer(upper)) &&
                lines.stream().anyMatch(line -> line.equalsIgnoringPlayer(lower)) &&
                lines.stream().anyMatch(line -> line.equalsIgnoringPlayer(left)) &&
                lines.stream().anyMatch(line -> line.equalsIgnoringPlayer(right));
    }

    private boolean isBoardFull() {
        // since we check the validity of every line drawn onto the board we can check if the board has been completely filled
        // (i.e. the games has ended) by simply checking if the number of lines is 2*n*m - n - m which is the amount of possible lines
        // for a n*m board
        return lines.size() == (2*x_dimension*y_dimension)-x_dimension-y_dimension;
    }

    public void addMove(Line line) {
        this.lines.add(line);
    }

    public int getX_dimension() {
        return x_dimension;
    }

    public int getY_dimension() {
        return y_dimension;
    }
}
