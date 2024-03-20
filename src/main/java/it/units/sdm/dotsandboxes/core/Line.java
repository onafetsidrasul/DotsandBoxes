package it.units.sdm.dotsandboxes.core;

import java.util.Objects;

/**
 * Record that models a line on a Dots and Boxes board.
 * Lines do not formally have a direction as it's not required by the game,
 * although it can be inferred from the implicit order of the endpoints.
 *
 * @param color Color of the line
 * @param p1    First endpoint of the line
 * @param p2    Second endpoint of the line
 */
public record Line(Color color, Point p1, Point p2) {
    public Line(int x1, int y1, int x2, int y2) {
        this(null, new Point(x1, y1), new Point(x2, y2));
    }

    public Line(Point p1, Point p2) {
        this(p1.x(), p1.y(), p2.x(), p2.y());
    }

    /**
     * Returns a line with the same endpoints as the original one but with a new color.
     * Color cannot be null.
     *
     * @param color Color of the new line
     * @param line  Line to color
     */
    public Line(Color color, Line line) {
        this(Objects.requireNonNull(color), line.p1(), line.p2());
    }

    public double length() {
        return Math.pow(Math.pow((this.p2().x() - this.p1().x()), 2) + Math.pow((this.p2().y() - this.p1().y()), 2), 0.5);
    }

    @Override
    public String toString() {
        return "Line{" +
                "color=" + color +
                ", p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
