import com.mongodb.client.*;
import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;

public class MongoDBCrud {
    public static void main(String[] args) {
        try (MongoClient client = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = client.getDatabase("mydb");
            MongoCollection<Document> users = db.getCollection("users");

            // Create
            Document user = new Document("name", "Alice").append("age", 25);
            users.insertOne(user);

            // Read
            Document result = users.find(eq("name", "Alice")).first();
            System.out.println("Read: " + result.toJson());

            // Update
            users.updateOne(eq("name", "Alice"), new Document("$set", new Document("age", 26)));

            // Delete
            users.deleteOne(eq("name", "Alice"));
        }
    }
}