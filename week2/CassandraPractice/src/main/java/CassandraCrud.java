
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

import java.net.InetSocketAddress;
import java.util.UUID;

public class CassandraCrud {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1") // change if using a different DC
                .withKeyspace("mykeyspace")
                .build()) {

            UUID id = UUID.randomUUID();

            // Create
            session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)",
                    id, "Alice", 25);
            System.out.println("Inserted user Alice");

            // Read
            ResultSet rs = session.execute("SELECT * FROM users WHERE id = ?", id);
            Row row = rs.one();
            if (row != null) {
                System.out.println("Read user: " + row.getString("name") + ", age " + row.getInt("age"));
            }

            // Update
            session.execute("UPDATE users SET age = ? WHERE id = ?", 26, id);
            System.out.println("Updated age to 26");

            // Delete
            session.execute("DELETE FROM users WHERE id = ?", id);
            System.out.println("Deleted user");
        }
    }
}