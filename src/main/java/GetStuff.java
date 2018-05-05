
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServlet1", urlPatterns = {"get"}, loadOnStartup = 1)
public class GetStuff extends HttpServlet {

    private ListUtil lu = new ListUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("Hello, amir! "+lu.getvalue());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String somedata = request.getParameter("somedata");

        

        request.setAttribute("user", lu.getValuebasedOnLocation(somedata));
        request.getRequestDispatcher("response.jsp").forward(request, response);
    }

}
