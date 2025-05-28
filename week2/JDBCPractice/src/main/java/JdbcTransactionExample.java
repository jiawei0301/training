import java.sql.*;

public class JdbcTransactionExample {
    private static final String URL      = "jdbc:postgresql://localhost:5432/postgres";
    //private static final String USER     = "postgres";
    //private static final String PASSWORD = "yourpassword";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 1. Establish connection
            conn = DriverManager.getConnection(URL);

            // 2. Turn off auto-commit (start transaction)
            conn.setAutoCommit(false);

            // 3. Perform multiple operations
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("INSERT INTO accounts(id, balance) VALUES (1, 1000)");
                stmt.executeUpdate("INSERT INTO accounts(id, balance) VALUES (2, 2000)");
                // Force an error to test rollback:
                stmt.executeUpdate("UPDATE non_existing_table SET x = 1");
            }

            // 4. If all succeed, commit
            conn.commit();
            System.out.println("Transaction committed successfully");

        } catch (SQLException e) {
            System.err.println("Error occurred, rolling back: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Rollback completed");
                } catch (SQLException rollbackEx) {
                    System.err.println("Rollback failed: " + rollbackEx.getMessage());
                }
            }
        } finally {
            // 5. Clean up
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }
}
