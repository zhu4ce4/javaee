//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadException;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.Iterator;
//import java.util.List;
//
//public class UploadPhotoServlet extends HttpServlet {
//
//    public void doPost(HttpServletRequest request, HttpServletResponse response) {
//
//        response.setContentType("text/html; charset=UTF-8");
//
//        String filename = null;
//        try {
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//            ServletFileUpload upload = new ServletFileUpload(factory);
//            // 设置上传文件的大小限制为1M
//            factory.setSizeThreshold(1024 * 1024);
//
//            List items = null;
//            try {
//                items = upload.parseRequest(request);
//            } catch (FileUploadException e) {
//                e.printStackTrace();
//            }
//
//            Iterator iter = items.iterator();
//            while (iter.hasNext()) {
//                FileItem item = (FileItem) iter.next();
//                if (!item.isFormField()) {
//
//                    //todo:头像应上传至mysql数据库，且命名应为：数据库内的id，应为id是惟一的;bug:上传photo后页面跳转到/uploadPhoto
////                    int userNum=UserDAO.queryUserNum();
//                    int userNum = UserDAO.nextId();
//                    filename = userNum + ".jpg";
//
//                    //通过getRealPath获取上传文件夹，如果项目在e:/project/j2ee/web,那么就会自动获取到 e:/project/j2ee/web/uploaded
//                    String photoFolder = request.getServletContext().getRealPath("usersPhoto");
//
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
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
