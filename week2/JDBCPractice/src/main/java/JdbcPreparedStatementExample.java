import java.sql.*;

public class JdbcPreparedStatementExample {
    private static final String URL      = "jdbc:postgresql://localhost:5432/postgres";

    public static void main(String[] args) {
        String insertSql = "INSERT INTO users(name, email) VALUES (?, ?)";
        String selectSql = "SELECT id, name, email FROM users WHERE email = ?";

        try (Connection conn = DriverManager.getConnection(URL)) {
            // INSERT with PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, "Alice");
                pstmt.setString(2, "alice@example.com");
                int affected = pstmt.executeUpdate();
                System.out.println("Rows inserted: " + affected);

                // Get generated key
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        System.out.println("New user ID: " + keys.getLong(1));
                    }
                }
            }

            // SELECT with PreparedStatement
            try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
                pstmt.setString(1, "alice@example.com");
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        System.out.printf("User: %d, %s, %s%n",
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
