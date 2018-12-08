import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OnlineNumberServlet extends HttpServlet {
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html;charset=UTF-8");

//        在JSP中使用application对象， application对象是ServletContext接口的实例
//也可以通过 request.getServletContext()来获取。
//所以 application == request.getServletContext() 会返回true
//application映射的就是web应用本身。

        Integer online_number = (Integer) req.getServletContext().getAttribute("online_number");
        System.out.println("当前用户数：" + online_number);
        res.getWriter().print(online_number);
    }
}
