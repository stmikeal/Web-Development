
package beans;

import tools.Point;
import tools.PointDB;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Named("mainBean")
@ApplicationScoped
public class MainBean implements Serializable {

    private String x = "0";
    private String y = "0";
    private String r = "1";
    private List<Point> points;
    private final PointDB pointDB = new PointDB();
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public MainBean() {
        points = new ArrayList<>();
        String idString = ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getId();
        System.out.println(idString);
        pointDB.findAll(idString);
    }

    public String getDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd.MM.yyyy");
        return LocalDateTime.now().format(formatter);
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getR() {
        return r;
    }

    public List<Point> getPoints() {
        return points;
    }


    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setR(String r) {
        this.r = r;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }

    public void clearPoints() {
        points.clear();
        executor.execute(pointDB::clear);
    }

    public void addPoint() {
        Point point = new Point(x, y, r);
        executor.execute(() -> pointDB.add(point));
        points.add(point);
    }

    public void showClearMessage() {
    }
}