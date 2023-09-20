package servlet;

import org.json.JSONObject;
import tools.LoginCheckerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        try (PrintWriter out = response.getWriter()) {
            JSONObject answer = new JSONObject();
            answer.put("time", LocalDateTime.now().toString());
            LoginCheckerFactory.getChecker();
            out.println(answer.toString());
        } catch(Exception e) {
            System.out.println("Exception during answering.");
        }
    }
}
