import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;

import java.net.InetSocketAddress;
import java.util.UUID;

public class CassandraCrud {
    public static void main(String[] args) {
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
                .withLocalDatacenter("datacenter1")
                .withKeyspace("mykeyspace")
                .build()) {

            UUID id = UUID.randomUUID();

            // Create
            session.execute("INSERT INTO users (id, name, age) VALUES (?, ?, ?)",
                    id, "Bob", 25);

            // Read
            Row row = session.execute("SELECT * FROM users WHERE id = ?", id).one();
            System.out.println("Read: " + row.getString("name") + ", age " + row.getInt("age"));

            // Update
            session.execute("UPDATE users SET age = ? WHERE id = ?", 26, id);

            // Delete
            session.execute("DELETE FROM users WHERE id = ?", id);
        }
    }
}
