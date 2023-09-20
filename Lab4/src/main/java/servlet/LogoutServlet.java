package servlet;

import org.json.JSONObject;
import tools.LoginCheckerFactory;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

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
        String username = "";
        if (command.equals("logout")) {
            try{
                username = request.getParameter("username").trim();
                System.out.println("Username parsed: " + username);
            }catch(Exception e){
                System.out.println("Exception during parsing username.");
                e.printStackTrace();
            }
        }
        try (PrintWriter out = response.getWriter()) {
            JSONObject answer = new JSONObject();
            answer.put("correct", "yes");
            out.println(answer.toString());
        } catch(Exception e) {
            System.out.println("Exception during answering.");
        }
    }

}
