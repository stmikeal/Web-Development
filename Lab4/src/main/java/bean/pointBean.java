package bean;

import org.json.JSONObject;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "points")
public class pointBean implements Serializable {

    public pointBean(){}

    public pointBean(double x, double y, double r, String owner){
        this.script = System.currentTimeMillis();
        this.x = x;
        this.y = y;
        this.r = r;
        this.owner = owner;
        this.time = LocalDateTime.now().toString();
        this.hit = Math.abs(x)<=Math.abs(r)&&x*r>=0&&Math.abs(y)<=Math.abs(r)&&y*r>=0||
                   Math.abs(y-2*x)<=Math.abs(r)&&x*y<=0&&x*r<=0||
                   x*x+y*y<=r*r/4&&x*y<=0&&x*r>=0;
        this.script = System.currentTimeMillis() - this.script;

    }

    public JSONObject toJSON(){
        JSONObject answer = new JSONObject();
        answer.put("x", Double.toString(x));
        answer.put("y", Double.toString(y));
        answer.put("r", Double.toString(r));
        answer.put("time", time);
        answer.put("script", Long.toString(script));
        answer.put("hit", Boolean.toString(hit));
        return answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getScript() {
        return script;
    }

    public void setScript(long script) {
        this.script = script;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="x")
    private double x;

    @Column(name="y")
    private double y;

    @Column(name="r")
    private double r;

    @Column(name="time")
    private String time;

    @Column(name="script")
    private long script;

    @Column(name="session")
    private String session;

    @Column(name="hit")
    private boolean hit;

    @Column(name="owner")
    private String owner;
}
