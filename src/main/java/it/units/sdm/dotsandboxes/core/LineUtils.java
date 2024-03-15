package it.units.sdm.dotsandboxes.core;

public class LineUtils {
    public static Line normalize(Line line) {
        if (line.p1().x() > line.p2().x() || line.p1().y() > line.p2().y()) {
            line = new Line(line.color(), line.p2(), line.p1());
        }
        return line;
    }

    public static Line stripOfColor(Line line) {
        return new Line(line.p1(), line.p2());
    }
}
