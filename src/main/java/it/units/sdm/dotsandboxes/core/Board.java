package it.units.sdm.dotsandboxes.core;

import java.util.HashMap;
import java.util.Map;
public class Board {
    private final Map<Integer, Line> lines;
    private final int x_dimension, y_dimension;

    public Board(int xDimension, int yDimension) {
        x_dimension = xDimension;
        y_dimension = yDimension;
        this.lines = new HashMap<>();
    }

    public boolean isBoxCompleted(Point p) {
        //box identified by the upper left point
        Integer upperSideHash = new Line(p.x(), p.y(), p.x()+1, p.y()).hashCode();
        Integer lowerSideHash = new Line(p.x(), p.y()+1, p.x()+1, p.y()+1).hashCode();
        Integer leftSideHash = new Line(p.x(), p.y(), p.x(), p.y()+1).hashCode();
        Integer rightSideHash = new Line(p.x()+1, p.y(), p.x()+1, p.y()+1).hashCode();

        return lines.containsKey(upperSideHash) &&
                lines.containsKey(lowerSideHash) &&
                lines.containsKey(leftSideHash) &&
                lines.containsKey(rightSideHash);
    }

    public boolean isBoardFull() {
        // since we check the validity of every line drawn onto the board we can check if the board has been completely filled
        // (i.e. the games has ended) by simply checking if the number of lines is 2*n*m - n - m which is the amount of possible lines
        // for a n*m board
        return lines.size() == (2*x_dimension*y_dimension)-x_dimension-y_dimension;
    }

    public void addLine(Line line) {
        if (length(line) != 1)
            throw new IllegalArgumentException("Line is too long!");
        if (lineIsOutsideBoard(line))
            throw new IllegalArgumentException("Line sits outside the bounds of the board!");
        if (isNotNormalized(line)) {
            line = normalizer(line);
        }
        if (lines.containsKey (new Line(null, line).hashCode())) {
            throw new IllegalArgumentException("Line already exists!");
        }
        // the hashcode is calculated based on the line stripped of its color in order to avoid putting to lines of different colors in the same place
        lines.put(new Line(null, line).hashCode(), line);
    }

    private static double length(Line line) {
        return Math.pow((line.p2().x() - line.p1().x()), 2) + Math.pow((line.p2().y() - line.p1().y()), 2);
    }

    public static boolean isNotNormalized(Line line) {
        return line.p1().x() > line.p2().x() || line.p1().y() > line.p2().y();
    }

    public static Line normalizer(Line line) {
        return new Line(line.color(), new Point(line.p2().x(), line.p2().y()), new Point(line.p1().x(), line.p1().y()));
    }

    private boolean lineIsOutsideBoard(Line line) {
        return line.p1().x() < 0 || line.p1().x() >= x_dimension || line.p1().y() < 0 || line.p1().y() >= y_dimension ||
                line.p2().x() < 0 || line.p2().x() >= x_dimension || line.p2().y() < 0 || line.p2().y() >= y_dimension;
    }

    public int getX_dimension() {
        return x_dimension;
    }

    public int getY_dimension() {
        return y_dimension;
    }

    public Map<Integer, Line> getLines() {
        return lines;
    }
}
