import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //不能一上来就setcontenttype
//        response.setContentType("text/html;charset=UTF-8");

        String uri = request.getRequestURI();

        //如果要用到missmeservlet或者readyforrenservlet就应该检查是否登录，否则直接放行
        if (!uri.endsWith("MissMe") || uri.endsWith("readyForRen")) {
            filterChain.doFilter(request, response);
            return;
        }


        //利用正则表达式
//        String uri = request.getRequestURI();
//        System.out.println("uri: " + uri);
//        String pattern = ".*\\.(js|jpg|png|css)";
//        // 首先判断是否是访问的login.html和login，因为这两个页面就是在还没有登陆之前就需要访问的
//        if (uri.endsWith("login.html") || uri.endsWith("login") || Pattern.matches(pattern, uri)) {
//            filterChain.doFilter(request, response);
//            return;
//        }


        String userName = (String) request.getSession().getAttribute("userName");
        System.out.println("userName  : " + userName);
        if (null == userName) {
            response.setContentType("text/html;charset=UTF-8");
            System.out.println(198848);
            response.getWriter().print(ReadyForRenServlet.getJSONstr("false", "", 0, 0));
            System.out.println(20181209);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
