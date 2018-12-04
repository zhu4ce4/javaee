import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MissMeServlet extends HttpServlet {
    private static String getSubString(String aStr, int lastIndex) {
        aStr = aStr.substring(0, lastIndex - 1);
        return aStr;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("name");
        String hao = request.getParameter("hao");
        String content = request.getParameter("content");
        if (name.length() > 20) {
            name = getSubString(name, 20);
        }
        if (hao.length() > 20) {
            hao = getSubString(hao, 20);
        }
        if (content.length() > 100) {
            content = getSubString(content, 100);
        }
        LostPeople alp = new LostPeople(name, content, hao);
        LPDAO.addMessage(alp);
        response.getWriter().print("留言成功");
    }
}
