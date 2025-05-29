import java.sql.*;
import javax.sql.DataSource;

public class JDBCExample {
    public static void main(String[] args) {
        DataSource ds = DataSourceUtil.getDataSource();

        try (Connection conn = ds.getConnection()) {
            // transaction demo
            conn.setAutoCommit(false);
            try (PreparedStatement ps1 = conn.prepareStatement("INSERT INTO users(email,name) VALUES(?,?)");
                 PreparedStatement ps2 = conn.prepareStatement("UPDATE stats SET count = count + 1 WHERE id = ?")) {

                // SQL injection safe
                ps1.setString(1, "alice@example.com");
                ps1.setString(2, "Alice");
                ps1.executeUpdate();

                ps2.setInt(1, 1);
                ps2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }

            // simple SELECT
            try (PreparedStatement ps = conn.prepareStatement("SELECT id, email, name FROM users WHERE email = ?")) {
                ps.setString(1, "alice@example.com");
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        System.out.println(
                                "User: " + rs.getLong("id") + ", " +
                                        rs.getString("email") + ", " +
                                        rs.getString("name")
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


}
