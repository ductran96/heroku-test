
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HelloServlet", urlPatterns = {"add"}, loadOnStartup = 1)
public class HelloServlet1 extends HttpServlet {

    private ListUtil lu = new ListUtil();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.getWriter().print("Hello, amir!");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String somedata = request.getParameter("somedata");

        if (somedata == null) {
            somedata = "is null";
        }else{
            lu.addValue(somedata);
        }

        request.setAttribute("user", lu.getvalue());
         request.getRequestDispatcher("index.html").forward(request, response);
    }

}
