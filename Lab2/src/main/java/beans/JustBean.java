package beans;

import java.util.LinkedList;

public class JustBean {
    private final LinkedList<Point> points = new LinkedList<>();

    public JustBean(){}

    public void add(Point point) {
        this.points.add(point);
    }

    public LinkedList<Point> getPoints() {
        return points;
    }
}
