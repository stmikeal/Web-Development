package servlet;

import org.json.JSONObject;
import tools.LoginCheckerFactory;
import tools.LoginUsers;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

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
        String password = "";
        if (command.equals("login")) {
            try{
                username = request.getParameter("username").trim();
                System.out.println("Username parsed: " + username);
            }catch(Exception e){
                System.out.println("Exception during parsing username.");
                e.printStackTrace();
            }
            try{
                password = request.getParameter("password").trim();
                System.out.println("Password parsed: " + password);
            }catch(Exception e){
                System.out.println("Exception during parsing password.");
                e.printStackTrace();
            }
            result = LoginCheckerFactory.getChecker().authorisation(username, password);
        }
        try (PrintWriter out = response.getWriter()) {
            JSONObject answer = new JSONObject();
            answer.put("token", result);
            answer.put("username", username);
            out.println(answer.toString());
            LoginUsers.addSession(request.getSession().getId());
            LoginUsers.addToken(request.getSession().getId(), result);
            LoginUsers.addUsername(request.getSession().getId(), username);
        } catch(Exception e) {
            System.out.println("Exception during answering.");
        }
    }

}
