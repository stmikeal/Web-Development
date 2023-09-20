package tools;

import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.faces.view.facelets.FaceletContext;
import javax.persistence.*;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "POINTS")
public class Point implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @Column(name = "x")
    private double x;
    @Column(name = "y")
    private double y;
    @Column(name = "r")
    private double r;
    @Column(name = "scriptTime")
    private long scriptTime;
    @Column(name = "sendTime")
    private String sendTime;
    @Column(name = "hit")
    private String hit;
    @Column(name = "sessionid")
    private String sessionid;

    public Point() {
    }

    public Point(String xParam, String yParam, String rParam) {
        this.scriptTime = System.currentTimeMillis();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.sendTime = LocalDateTime.now().format(formatter);
        setFields(xParam, yParam, rParam);
        this.hit = checkHit() ? "Hitted" : "Miss";
        this.sessionid = ((HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false)).getId();
        this.scriptTime = System.currentTimeMillis() - this.scriptTime;
        this.scriptTime = this.scriptTime > 0 ? this.scriptTime : 3;
    }

    private void setFields(String xParam, String yParam, String rParam) {
        try {
            this.x = Double.parseDouble(xParam);
        } catch (Exception e) {
            System.out.println("Not valid x-parameter. Setting default value.");
            this.x = 0D;
        }
        try {
            this.y = Double.parseDouble(yParam.replaceAll(",", "."));
        } catch (Exception e) {
            System.out.println("Not valid y-parameter. Setting default value.");
            this.y = 0D;
        }
        try {
            this.r = Double.parseDouble(rParam);
        } catch (Exception e) {
            System.out.println("Not valid r-parameter. Setting default value.");
            this.r = 1D;
        }
    }

    private boolean checkHit() {
        return checkCircle() || checkRectangle() || checkTriangle();
    }

    private boolean checkTriangle() {
        return y - 2 * x <= r && y >= 0 && x <= 0;
    }

    private boolean checkCircle() {
        return x * x + y * y <= r * r && x >= 0 && y <= 0;
    }

    private boolean checkRectangle() {
        return x >= 0 && y >= 0 && y <= r && 2 * x < r;
    }

    public long getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getR() {
        return r;
    }

    public long getScriptTime() {
        return scriptTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getHit() {
        return hit;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setId(long ID) {
        this.id = ID;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setR(double r) {
        this.r = r;
    }

    public void setScriptTime(long script_time) {
        this.scriptTime = script_time;
    }

    public void setSendTime(String send_time) {
        this.sendTime = send_time;
    }

    public void setHit(String hit) {
        this.hit = hit;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
