
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.json.JSONObject;


@WebServlet(name = "HelloServlet1", urlPatterns = {"get"}, loadOnStartup = 1)
public class GetStuff extends HttpServlet {

    private ListUtil lu = new ListUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print(lu.getvalue());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String somedata = request.getParameter("somedata");
        
        
        //JSONObject jsonObj = new JSONObject("{"+"\"data\":"+lu.getValuebasedOnLocation(somedata)+"}");
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("{"+"\"data\":"+lu.getValuebasedOnLocation(somedata)+"}");
        
        
        //request.setAttribute("user", lu.getValuebasedOnLocation(somedata));
        //request.getRequestDispatcher("index.html").forward(request, response);
    }

}
