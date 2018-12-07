import net.sf.json.JSONSerializer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadyForRenServlet extends HttpServlet {

    public static int numNow = 0;
    public static int totalNumofMes = LPDAO.getMessNum();

    public static String getJSONstr(boolean loginOrNot, String userName, int numFrom, int totalGetNum) {
        Map<String, Object> res = new HashMap<>();
        if (loginOrNot) {
            res.put("login", loginOrNot);
            res.put("userName", userName);
            List<LostPeople> lps = LPDAO.getLatestLP(numFrom, totalGetNum);
            res.put("res", lps);
            return JSONSerializer.toJSON(res).toString();
        } else {
            res.put("login", false);
            return JSONSerializer.toJSON(res).toString();
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String userName = (String) request.getSession().getAttribute("userName");
        if (null == userName) {
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (int i = 0; i < cookies.length; i++) {
                    if ("userName".equals(cookies[i].getName())) {
                        userName = cookies[i].getValue();
                        userName = URLDecoder.decode(userName, StandardCharsets.UTF_8);
                    }
                }
            } else {
                response.getWriter().print(getJSONstr(false, "", 0, 0));
                return;
            }
        }

        String demand = request.getParameter("demand");
        if ("newestFive".equals(demand)) {
            //todo:未接之谜:序列化后的key是哪里指定的？
            response.getWriter().print(getJSONstr(true, userName, 0, 5));
            numNow = 0;
        } else if ("nextFive".equals(demand)) {
            if (numNow + 5 < totalNumofMes) {
                numNow += 5;
            }
            response.getWriter().print(getJSONstr(true, userName, numNow, 5));
        } else if ("lastFive".equals(demand)) {
            if (numNow - 5 >= 0) {
                numNow -= 5;
            }
            response.getWriter().print(getJSONstr(true, userName, numNow, 5));
        }
    }
}
