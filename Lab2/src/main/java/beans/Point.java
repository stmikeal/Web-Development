package beans;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Point implements Serializable {
    private static  final long serialVersionUID = 388769L;
    private static int nextID = 1;

    private final int ID;
    private final double x;
    private final double y;
    private final int r;
    private final LocalDateTime time;
    private long script_time;
    private final boolean hit;

    public Point(double x, double y, int r) {
        long startTime = System.currentTimeMillis();
        this.ID = nextID++;
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = LocalDateTime.now();
        this.hit = checkHit();
        this.script_time = System.currentTimeMillis() - startTime;
        this.script_time = this.script_time < 5 ? 5 : this.script_time;
    }

    private boolean checkTriangle() {
        return x <= 0 && y <= 0 && x + y * 2 + r >= 0;
    }

    private boolean checkCircle() {
        return x <= 0 && y >= 0 && x * x + y * y <= r * r;
    }

    private boolean checkRectangle() {
        return x >= 0 && y <= 0 && x * 2 <= r && y >= -r;
    }

    private boolean checkHit() {
        return checkTriangle() || checkCircle() || checkRectangle();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getID() {
        return ID;
    }

    public int getR() {
        return r;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public long getScript_time() {
        return script_time;
    }

    public boolean getHit() {
        return hit;
    }
}
