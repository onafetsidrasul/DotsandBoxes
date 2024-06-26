package it.units.sdm.dotsandboxes.core;

/**
 * Record that models a line on a Dots and Boxes board.
 * Lines do not formally have a direction as it's not required by the game,
 * although it can be inferred from the implicit order of the endpoints.
 *
 * @param color Color of the line
 * @param p1    First endpoint of the line
 * @param p2    Second endpoint of the line
 */
public record Line(
        Color color,
        Point p1,
        Point p2
) {
    public Line(int x1, int y1, int x2, int y2) {
        this(null, new Point(x1, y1), new Point(x2, y2));
    }

    public Line(Point p1, Point p2) {
        this(p1.x(), p1.y(), p2.x(), p2.y());
    }

    /**
     * Creates a line with the same endpoint as the original one but a new color
     *
     * @param color Color of the new line
     * @param line  Line to color
     */
    public Line(Color color, Line line) {
        this(color, line.p1(), line.p2());
    }

    public double length() {
        return Math.pow(Math.pow(p2.x() - p1.x(), 2) + Math.pow(p2.y() - p1.y(), 2), 0.5);
    }

    /**
     * Returns a normalized version of the line
     *
     */
    public static Line normalize(Line line) {
        if (line.p1().x() > line.p2().x() || line.p1().y() > line.p2().y()) {
            return new Line(line.color(), new Point(line.p2().x(), line.p2().y()), new Point(line.p1().x(), line.p1().y()));
        }
        return line;
    }

    public String toString(){
        return color + ", " + p1.toString() + " -> " + p2.toString();
    }
}
