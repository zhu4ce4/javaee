import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
                try {
                    items = upload.parseRequest(request);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }

                String accountName = null;
                String password = null;

                Iterator iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = (FileItem) iter.next();
                    if (item.isFormField()) {
//                    当item.isFormField返回true的时候，就表示是常规字段。
                        if ("accountName".equals(item.getFieldName())) {
                            accountName = item.getString();
                            accountName = new String(accountName.getBytes("ISO-8859-1"), "UTF-8");
//                            System.out.println("登录时账户名："+accountName);
                            continue;
                        } else if ("password".equals(item.getFieldName())) {
                            password = item.getString();
                            password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
//                            System.out.println("登录时密码："+password);
                            continue;
                        }
                    }
                }

                if (UserDAO.loginable(accountName, password)) {
                    //todo:怎么发送cookies,并在页面显示用户名
//                    Cookie name = new Cookie("name", URLEncoder.encode(accountName,"UTF-8"));
//                    name.setMaxAge(60*60*24);
//                    response.addCookie(name);
                    response.sendRedirect("hello.html");
                    response.getWriter().println("跳转了");
                } else {
                    //select数据库是否匹配，不匹配则错误提醒
                    response.sendRedirect("login.html");
                    response.getWriter().println("账号密码错误，请重新输入！");
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
