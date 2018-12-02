import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
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
        String sql = "select count(*) from liuyan";
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
        String sql = "insert into liuyan values(?,?,?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, aLostpeople.getName());
            ps.setString(2, aLostpeople.getHaoma());
            ps.setString(3, aLostpeople.getMessages());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
