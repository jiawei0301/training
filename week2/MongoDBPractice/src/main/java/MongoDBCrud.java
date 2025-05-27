import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class MongoDBCrud {
    public static void main(String[] args) {
        // Connect to MongoDB
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase database = client.getDatabase("mydb");
            MongoCollection<Document> users = database.getCollection("users");

            // Create
            Document user = new Document("name", "Alice").append("age", 25);
            users.insertOne(user);
            System.out.println("Inserted user");

            // Read
            Document result = users.find(eq("name", "Alice")).first();
            if (result != null) {
                System.out.println("Read user: " + result.toJson());
            }

            // Update
            users.updateOne(eq("name", "Alice"), new Document("$set", new Document("age", 26)));
            System.out.println("Updated age");

            // Delete
            users.deleteOne(eq("name", "Alice"));
            System.out.println("Deleted user");
        }
    }
}