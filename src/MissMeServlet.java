import javax.servlet.http.Cookie;
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

//        String userName = (String) request.getSession().getAttribute("userName");
        String userName = null;

//        if (null == userName) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies) {
            response.getWriter().print("发帖失败，请注册/登录后发帖");
            return;
        } else {
            boolean loginOrNot = false;
            for (Cookie cookie : cookies) {
                //todo:此处判断用户是否登录太薄弱
                if ("userName".equals(cookie.getName())) {
                    loginOrNot = true;
                    break;
                }
            }
            if (!loginOrNot) {
                response.getWriter().print("发帖失败，请注册/登录后发帖");
                return;
            }
        }
//        }

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
        response.getWriter().print("发帖成功！");
    }
}
