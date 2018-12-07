import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDAO {
    //维护一个static的总用户数,包含哪些

    public static Connection getConnection() throws SQLException {

        //已过期但必须加否则报错！！！
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("f:/pj/myweb/src/usermysql.properties"))) {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = props.getProperty("jdbc.url");
        String username = props.getProperty("jdbc.username");
        String pwd = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, pwd);
    }

    //利用mysql中的id自增特性（包括清空用户之后，id不是又回到1开始自增，id仍保持从原来最大的id向上+1自增的特性，以此记录所有包含已删除的id的总数）
//    也即利用自增id的可预测性
    public static int nextId() {
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT auto_increment FROM information_schema.`TABLES` WHERE TABLE_SCHEMA='javamysql' AND TABLE_NAME='users'";
            ResultSet rs = s.executeQuery(sql);
            int nextId = -1;
            while (rs.next()) {
                nextId = rs.getInt(1);
            }
            return nextId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static int queryUserNum() {
        int num = 0;
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            String sql = "select count(*) from users";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    //add前需先进行校验操作，看是否有重名
    public static boolean registerable(String aname) {
        String sql = "select count(*) from users where username=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, aname);
            ResultSet rs = ps.executeQuery();
            int result = 1;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            return result == 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean loginable(String aname, String pwd) {
        String sql = "select count(*) from users where username=? and userpwd=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, aname);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            int result = 0;
            while (rs.next()) {
                result = rs.getInt(1);
            }
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void add(User aUser, int idDeleteIncase) throws IOException {
        String sql = "insert into users values(null,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aUser.getName());
            ps.setString(2, aUser.getPassword());
            ps.setString(3, aUser.getPicpath());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                aUser.setId(id);
            }
        } catch (SQLException e) {
            //todo:事务回滚
            delete(idDeleteIncase);
            //若失败，将之前产生的对应的userphoto.id.jpg删除
            Path failedIdPhotoPath = Paths.get(String.format("f:/pj/myweb/out/artifacts/myweb_war_exploded/usersPhoto/%d.jpg", idDeleteIncase));
            Files.delete(failedIdPhotoPath);
            e.printStackTrace();
        }
    }

    public static void delete(int deleteId) {
        String sql = "delete from users where id=?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, deleteId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新前需先进行校验操作
    public void update(User aUser, String sthTobeModified, String newValue) {
        String sql = String.format("update users set %s=? where id=?", sthTobeModified);
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, newValue);
            ps.setInt(2, aUser.getId());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getAuser(int id) {
        User aUser = null;
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            String sql = String.format("select & from users where id=%d", id);
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString(2);
                String password = rs.getString(3);
                String picpath = rs.getString(4);
                aUser = new User(name, password, picpath);
                aUser.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aUser;
    }

    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    public List<User> list(int start, int count) {
        List<User> users = new ArrayList<>();
        String sql = "select * from users order by id desc limit ?,?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User aUser = new User();
                int id = rs.getInt(1);
                String name = rs.getString(2);
                aUser.setId(id);
                aUser.setName(name);
                users.add(aUser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
