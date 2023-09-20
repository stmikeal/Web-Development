package tools;

import beans.Point;

public class JSONConverter {
    public static String toJSON(Point point) {
        return String.format("{\"x\":\"%f\",\"y\":\"%f\",\"r\":\"%d\","+
                "\"currentTime\":\"%s\",\"scriptTime\":\"%d\",\"hit\":\"%s\",\"ID\":\"%d\"}",
                point.getX(), point.getY(), point.getR(), point.getTime().toString(),
                point.getScript_time(),
                point.getHit() ? "Hitted" : "Miss", point.getID());
    }
}
