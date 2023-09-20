package tools;

public class PointManagerFactory {
    private static PointManager manager= new PointManager();

    public static PointManager getManager() {
        return manager;
    }
}
