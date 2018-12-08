import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class OnlineNumberListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        ServletContext application = se.getSession().getServletContext();
        Integer online_number = (Integer) application.getAttribute("online_number");
        if (null == online_number) {
            online_number = 0;
        }
        online_number++;
        application.setAttribute("online_number", online_number);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext application = se.getSession().getServletContext();
        Integer online_number = (Integer) application.getAttribute("online_number");
        if (null == online_number) {
            online_number = 0;
        } else {
            online_number--;
        }
        application.setAttribute("online_number", online_number);
    }
}
