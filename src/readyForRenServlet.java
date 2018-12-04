import net.sf.json.JSONSerializer;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ReadyForRenServlet extends HttpServlet {

    public static int numNow = 0;
    public static int totalNumofMes = LPDAO.getMessNum();

    public void service(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String demand = request.getParameter("demand");

        //网页加载完毕自动发出首页信号，返回最新的5条数据，首页永远都是从idstart=0开始
        if ("newestFive".equals(demand)) {
            List<LostPeople> lps = LPDAO.getLatestLP(0, 5);
            //todo:未接之谜:序列化后的key是哪里指定的？
            String result = JSONSerializer.toJSON(lps).toString();
            numNow = 5;
            response.getWriter().print(result);
        } else if ("nextFive".equals(demand)) {
            int totalNum = 5;
            if (totalNumofMes - numNow < 5) {
                totalNum = totalNumofMes - numNow;
            } else {
                numNow += 5;
            }

            List<LostPeople> lps = LPDAO.getLatestLP(numNow, totalNum);
            String result = JSONSerializer.toJSON(lps).toString();
            response.getWriter().print(result);

            //前面的5个数据
        } else if ("lastFive".equals(demand)) {
            //逻辑优先！优先理清逻辑
            int totalNum = 5;
            if (numNow - 5 < 0) {
                if (numNow != 0) {
                    totalNum = numNow;
                    numNow = 0;
                }
            } else {
                numNow -= 5;
            }
            List<LostPeople> lps = LPDAO.getLatestLP(numNow, totalNum);
            String result = JSONSerializer.toJSON(lps).toString();
            response.getWriter().print(result);
        }
    }
}
