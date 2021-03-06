import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

//开启文件(图片)上传功能，否则getpart取不到文件从而报错
@MultipartConfig
public class RegisterServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String name = request.getParameter("accountName");
        String password = request.getParameter("password");
        String picDirectoryName = "usersPhoto";

        if (UserDAO.registerable(name)) {
            System.out.println(0);
            Part p = request.getPart("filepath");
            int userIdPredict = UserDAO.nextId();
            //todo:此处强制格式为jpg是否妥当?
            String filename = userIdPredict + ".jpg";
            String photoFolder = request.getServletContext().getRealPath(picDirectoryName);
            String realPicpath = photoFolder + File.separator + filename;
            p.write(realPicpath);

            String picStoredPath = picDirectoryName + File.separator + filename;
            User newUser = new User(name, password, picStoredPath);
            UserDAO.add(newUser, userIdPredict);

//            Cookie user = new Cookie("userName", URLEncoder.encode(name, StandardCharsets.UTF_8));
//            Cookie user = new Cookie("userName", name);
//            user.setMaxAge(60 * 60 * 24);
//            response.setStatus(200);
//            response.addCookie(user);
            //注册后不给cookies，应该要登录后才给
//            request.getSession().setAttribute("userName", name);
//            request.setAttribute("userName", name);
//            request.getSession().setAttribute("userName", name);
            response.getWriter().print("注册成功");
        } else {
            response.getWriter().print("账号已被注册，请重新注册");
        }


//        try {
//            request.setCharacterEncoding("UTF-8");
//            response.setContentType("text/html; charset=UTF-8");
//
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            List items = null;
//            try {
//                items = upload.parseRequest(request);
//            } catch (FileUploadException e) {
//                e.printStackTrace();
//            }
//
//            String accountName = null;
//            String password = null;
//            String filename = null;
//            int userId = UserDAO.nextId();
//            String photoFolder = null;
//
//            Iterator iter = items.iterator();
//            while (iter.hasNext()) {
//                FileItem item = (FileItem) iter.next();
//                if (item.isFormField()) {
////                    当item.isFormField返回true的时候，就表示是常规字段。
//                    if ("accountName".equals(item.getFieldName())) {
//                        accountName = item.getString();
//                        accountName = new String(accountName.getBytes("ISO-8859-1"), "UTF-8");
//                    } else if ("password".equals(item.getFieldName())) {
//                        password = item.getString();
//                        password = new String(password.getBytes("ISO-8859-1"), "UTF-8");
//                    }
//                } else {
//                    //如果不是form常规字段而是user头像
//                    //todo:头像应上传至mysql数据库，且命名应为：数据库内的id，应为id是惟一的;
//                    if (-1 == userId) {
//                        //如果返回的是-1,则证明没能够拿到下一个id,可能数据库连接有误！break掉，免得生成无用的头像文件
//                        break;
//                    }
//                    filename = userId + ".jpg";
//                    //通过getRealPath获取上传文件夹，如果项目在e:/project/j2ee/web,那么就会自动获取到 e:/project/j2ee/web/uploaded
//                    photoFolder = request.getServletContext().getRealPath("usersPhoto");
//                    File f = new File(photoFolder, filename);
//                    f.getParentFile().mkdirs();
//
//                    // 通过item.getInputStream()获取浏览器上传的文件的输入流
//                    InputStream is = item.getInputStream();
//
//                    // 复制文件
//                    FileOutputStream fos = new FileOutputStream(f);
//                    byte b[] = new byte[1024 * 1024];
//                    int length = 0;
//                    while (-1 != (length = is.read(b))) {
//                        fos.write(b, 0, length);
//                    }
//                    fos.close();
//                }
//            }
//
//            //在mysql中查询是否有同name的，同名则不允许注册
//            if (!UserDAO.registerable(accountName)) {
//                //另外应该在JavaScript里面输入用户名之后就应该立刻进行判断并显示！
//                //todo:应该有浏览器提示是否注册成功后跳转到注册页面
//                response.getWriter().println("已有账户名，请另外选择一个账户名");
//            } else {
//                //在mysql中查询是否有同名，没有同名则注册成功后跳转
//                //保存用户名密码头像图片到mysql
//                String realPicpath = photoFolder + "\\" + userId + ".jpg";
//                User newUser = new User(accountName, password, realPicpath);
//                UserDAO.add(newUser, userId);
//                //todo:应该有浏览器提示是否注册成功后跳转
//                response.sendRedirect("hello.html");
//            }
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
