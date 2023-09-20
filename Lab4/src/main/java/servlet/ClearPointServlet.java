package servlet;

import bean.pointBean;
import org.json.JSONObject;
import tools.LoginCheckerFactory;
import tools.PointManagerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/clear")
public class ClearPointServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String command = "";
        String result = "";
        try{
            command = request.getParameter("command").trim();
            System.out.println("Command parsed: " + command);
        }catch(Exception e){
            System.out.println("Exception during parsing command.");
            e.printStackTrace();
        }
        if (command.equals("clear")) {
            String username = null;
            try{
                username = request.getParameter("username").trim();
                System.out.println("Username parsed: " + username);
            }catch(Exception e){
                System.out.println("Exception during parsing username.");
                e.printStackTrace();
            }
            String token = "";
            try{
                token = request.getParameter("token").trim();
                System.out.println("token parsed: " + token);
            }catch(Exception e){
                System.out.println("Exception during parsing token.");
                e.printStackTrace();
            }
            result = PointManagerFactory.getManager().clearPoint(username).toString();
        }
        try (PrintWriter out = response.getWriter()) {
            JSONObject answer = new JSONObject();
            answer.put("correct", result);
            out.println(answer.toString());
        } catch(Exception e) {
            System.out.println("Exception during answering.");
        }
    }

}