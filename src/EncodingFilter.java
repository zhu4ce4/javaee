import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        与Servlet需要配置自启动才会随着tomcat的启动而执行init()方法不一样。
//        Filter一定会随着tomcat的启动自启动。
//        Filter是web应用非常重要的一个环节，如果Filter启动失败，或者本身有编译错误，不仅这个Filter不能使用，整个web应用会启动失败，导致用户无法访问页面
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        request.setCharacterEncoding("UTF-8");
//        过滤器放行，表示继续运行下一个过滤器，或者最终访问的某个servlet,jsp,html等等
        chain.doFilter(request, response);
    }
}
