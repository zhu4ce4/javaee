import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class LPDAO {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get("f:/pj/myweb/src/usermysql.properties"))) {
            prop.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String url = prop.getProperty("jdbc.url");
        String username = prop.getProperty("jdbc.username");
        String pwd = prop.getProperty("jdbc.password");
        return DriverManager.getConnection(url, username, pwd);
    }

    public static int getMessNum() {
        int num = 0;
        String sql = "select count(*) from lostpeople";
        try (Connection c = getConnection(); Statement ps = c.createStatement()) {
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                num = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    public static void addMessage(LostPeople aLostpeople) {
        String sql = "insert into lostpeople values(null,?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, aLostpeople.getName());
            ps.setString(2, aLostpeople.getMessages());
            ps.setString(3, aLostpeople.getHaoma());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //看最新的id
    public static int getNewestId() {
        String sql = "select id from lostpeople order by id DESC limit 1;";
        try (Connection c = getConnection(); Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery(sql);
            int newestId = -1;
            while (rs.next()) {
                newestId = rs.getInt(1);
            }
            return newestId;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //按照id倒排序（最新的在前面）从最新的数据开始取totalnum条数据
    public static List<LostPeople> getLatestLP(int idStart, int totalNum) {
        String sql = "select * from lostpeople order by id desc LIMIT ?,?";
        List<LostPeople> lps = new ArrayList<>();
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idStart);
            ps.setInt(2, totalNum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
//                int id = rs.getInt(1);
                String name = rs.getString(2);
                String described = rs.getString(3);
                String hao = rs.getString(4);
                LostPeople lp = new LostPeople(name, described, hao);
                lps.add(lp);
            }
            return lps;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
