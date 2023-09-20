package servlet;

import beans.JustBean;
import beans.Point;
import tools.JSONConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

public class AreaCheckServlet extends HttpServlet {
    private JustBean justbean;

    @Override
    public void init() {
        justbean = new JustBean();
        System.out.println("Servlet initialized.");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("/controller");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double x = null;
        Double y = null;
        Integer r = null;
//        Integer time = null;
        System.out.println("Start checked");

        try {
            x = Double.parseDouble(request.getParameter("x").trim().replaceAll(",", "."));
            System.out.println("parsed x = " + x);
        } catch (NumberFormatException e) {
            System.out.println("Failed while parsing x.\n Redirected to controller without x-parameter");
            response.sendRedirect("/controller");
        }
        try {
            y = Double.parseDouble(request.getParameter("y").trim().replaceAll(",", "."));
            System.out.println("parsed y = " + y);
        } catch (NumberFormatException e) {
            System.out.println("Failed while parsing y.\n Redirected to controller without y-parameter");
            response.sendRedirect("/controller");
        }
        try {
            r = Integer.parseInt(request.getParameter("r").trim());
        } catch (NumberFormatException e) {
            System.out.println("Failed while parsing r.\n Redirected to controller without r-parameter");
            response.sendRedirect("/controller");
        }
       /* try {
            time = Integer.parseInt(request.getParameter("time").trim());
        } catch (NumberFormatException e) {
            System.out.println("Failed while parsing time.\n Redirected to controller without time-parameter");
            response.sendRedirect("/controller");
        }*/

        try (PrintWriter out = response.getWriter()) {
            if (x != null && y != null && r != null) {
                System.out.println("For if");
                Point point = new Point(x, y, r);
                justbean.add(point);
                out.println(JSONConverter.toJSON(point));
                System.out.println(JSONConverter.toJSON(point));
                System.out.println("after if");
            }
        }
    }
}
