package it.units.sdm.dotsandboxes.core;

import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Line> lines;
    private final int width, height;

    /**
     * @param x Width of the board
     * @param y Height of the board
     */
    public Board(int x, int y) {
        width = x;
        height = y;
        lines = new HashMap<>();
    }

    public boolean isBoxCompleted(Point p) {
        //box is identified by the upper left point
        Integer upperSideHash = new Line(p.x(), p.y(), p.x() + 1, p.y()).hashCode();
        Integer lowerSideHash = new Line(p.x(), p.y() + 1, p.x() + 1, p.y() + 1).hashCode();
        Integer leftSideHash = new Line(p.x(), p.y(), p.x(), p.y() + 1).hashCode();
        Integer rightSideHash = new Line(p.x() + 1, p.y(), p.x() + 1, p.y() + 1).hashCode();

        return lines.containsKey(upperSideHash) &&
                lines.containsKey(lowerSideHash) &&
                lines.containsKey(leftSideHash) &&
                lines.containsKey(rightSideHash);
    }

    public boolean isBoardFull() {
        // since we check the validity of every line drawn onto the board we can check if the board has been completely filled
        // (i.e. the games has ended) by simply checking if the number of lines is 2*n*m - n - m which is the amount of possible lines
        // for a n*m board
        return lines.size() == (2 * width * height) - width - height;
    }

    public void addLine(Line line) {
        if (line.length() != 1)
            throw new IllegalArgumentException("Line is too long!");

        if (line.p1().x() < 0 || line.p1().x() >= width || line.p1().y() < 0 || line.p1().y() >= height ||
                line.p2().x() < 0 || line.p2().x() >= width || line.p2().y() < 0 || line.p2().y() >= height)
            throw new IllegalArgumentException("Line sits outside the bounds of the board!");

        if (this.lines.put(LineUtils.normalize(LineUtils.stripOfColor(line)).hashCode(), line) != null)
            // the hashcode is calculated based on the normalized line stripped of its color because the place between two points
            // on the board is occupied regardless of the color of the line or the order of its endpoints
            throw new IllegalArgumentException("Line already exists!");
    }

    /**
     * @return returns the x dimension of the board
     */
    public int width() {
        return width;
    }

    /**
     * @return returns the y dimension of the board
     */
    public int height() {
        return height;
    }

    /**
     * @return returns the map representing the lines drawn onto the board
     */
    public Map<Integer, Line> linesDrawn() {
        return lines;
    }
}
