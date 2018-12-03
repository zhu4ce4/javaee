import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MissMeServlet extends HttpServlet {
    public static String getSubString(String aStr, int lastIndex) {
        aStr = aStr.substring(0, lastIndex - 1);
        return aStr;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {


//        try {
//            response.setContentType("text/html;charset=UTF-8");
//
//            String looked = request.getParameter("bxzname");
//            if (looked.length() > 20) {
//                looked = getSubString(looked, 20);
//            }
//            String qqweixin = request.getParameter("haoma");
//            if (qqweixin.length() > 20) {
//                qqweixin = getSubString(qqweixin, 20);
//            }
//            String liuyan = request.getParameter("leavemessage");
//            if (liuyan.length() > 100) {
//                liuyan = getSubString(liuyan, 100);
//            }
//            LostPeople alp = new LostPeople(looked, qqweixin, liuyan);
//            LPDAO.addMessage(alp);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
    }
}
