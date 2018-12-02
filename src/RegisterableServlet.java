import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.ServerException;


public class RegisterableServlet extends HttpServlet {
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServerException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");

        String nameTobeChecked = request.getParameter("name");
        if (UserDAO.registerable(nameTobeChecked)) {
            response.getWriter().print("<b>账户名可以使用</b>");
        } else {
            response.getWriter().print("<b style=\"color: red\">账户名已被注册,请重新输入</b>");
        }
    }
}
