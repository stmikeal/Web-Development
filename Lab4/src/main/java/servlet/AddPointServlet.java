package servlet;

import bean.PointBeanList;
import org.json.JSONObject;
import tools.LoginUsers;
import tools.PointManagerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@WebServlet("/point")
public class AddPointServlet extends HttpServlet {

    private PointBeanList collection = new PointBeanList();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        String command = "";
        JSONObject result = new JSONObject().put("token", "");
        try{
            command = request.getParameter("command").trim();
            System.out.println("Command parsed: " + command);
        }catch(Exception e){
            System.out.println("Exception during parsing command.");
            e.printStackTrace();
        }
        String owner = null;
        String token = null;
        if (command.equals("point")) {
            Double x = null;
            Double y = null;
            Double r = null;
            try{
                owner = request.getParameter("username").trim();
                System.out.println("Username parsed: " + owner);
            }catch(Exception e){
                System.out.println("Exception during parsing username.");
                e.printStackTrace();
            }
            try{
                token = request.getParameter("token").trim();
                System.out.println("Username parsed: " + owner);
            }catch(Exception e){
                System.out.println("Exception during parsing username.");
                e.printStackTrace();
            }
            try{
                x = Double.parseDouble(request.getParameter("x").trim().replaceAll(",", "."));
                System.out.println("Username parsed: " + x);
            }catch(Exception e){
                System.out.println("Exception during parsing username.");
                e.printStackTrace();
            }
            try{
                y = Double.parseDouble(request.getParameter("y").trim().replaceAll(",", "."));
                System.out.println("Password parsed: " + y);
            }catch(Exception e){
                System.out.println("Exception during parsing password.");
                e.printStackTrace();
            }
            try{
                r = Double.parseDouble(request.getParameter("r").trim().replaceAll(",", "."));
                System.out.println("Password parsed: " + r);
            }catch(Exception e){
                System.out.println("Exception during parsing password.");
                e.printStackTrace();
            }
            if (x!=null&&y!=null&&r!=null&&owner!=null)
                result = PointManagerFactory.getManager().addPoint(x, y, r, owner).toJSON();
                collection.add(result, owner);

        }
        //if (LoginUsers.checkSession(request.getSession().getId().toString(), token, owner));
        try (PrintWriter out = response.getWriter()) {
            System.out.println(collection.toJSON().toString());
            out.println(collection.toJSON().toString());
        } catch(Exception e) {
            System.out.println("Exception during answering.");
        }
    }

}