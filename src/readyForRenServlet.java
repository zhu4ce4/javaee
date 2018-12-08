import net.sf.json.JSONSerializer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadyForRenServlet extends HttpServlet {

    public static int numNow = 0;
    public static int totalNumofMes = LPDAO.getMessNum();

    public static String getJSONstr(String loginOrNot, User aUser, int numFrom, int totalGetNum) {
        Map<String, Object> res = new HashMap<>();
        if ("true".equals(loginOrNot)) {
            res.put("login", loginOrNot);
            res.put("user", aUser);
            List<LostPeople> lps = LPDAO.getLatestLP(numFrom, totalGetNum);
            res.put("res", lps);
            System.out.println("成功");
            System.out.println(JSONSerializer.toJSON(res).toString());
            System.out.println("成功");
            return JSONSerializer.toJSON(res).toString();
        } else {
            res.put("login", loginOrNot);
            System.out.println("失败");
            return JSONSerializer.toJSON(res).toString();
        }
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        User aUser = (User) request.getSession().getAttribute("user");
        System.out.println("reayforren是否得到auser:" + aUser);

//        if (null == userName) {
//        String userName = null;
//            Cookie[] cookies = request.getCookies();
//            if (null != cookies) {
//                for (int i = 0; i < cookies.length; i++) {
//                    if ("userName".equals(cookies[i].getName())) {
//                        userName = cookies[i].getValue();
//                        userName = URLDecoder.decode(userName, StandardCharsets.UTF_8);
//                    }
//                }
//            } else {
//                response.getWriter().print(getJSONstr(false, "", 0, 0));
//                return;
//            }
//            response.getWriter().print(getJSONstr(false, "", 0, 0));
//            return;
//        }

        String demand = request.getParameter("demand");
        if ("newestFive".equals(demand)) {
            System.out.println("new");
            //todo:未接之谜:序列化后的key是哪里指定的？
            response.getWriter().print(getJSONstr("true", aUser, 0, 5));
            System.out.println("new");
            numNow = 0;
        } else if ("nextFive".equals(demand)) {
            if (numNow + 5 < totalNumofMes) {
                numNow += 5;
            }
            System.out.println("next");
            response.getWriter().print(getJSONstr("true", aUser, numNow, 5));
            System.out.println("next");
        } else if ("lastFive".equals(demand)) {
            if (numNow - 5 >= 0) {
                numNow -= 5;
            }
            System.out.println("last");
            response.getWriter().print(getJSONstr("true", aUser, numNow, 5));
            System.out.println("last");
        }
    }
}
