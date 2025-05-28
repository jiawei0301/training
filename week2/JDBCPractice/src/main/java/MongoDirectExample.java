import com.mongodb.client.*;
import org.bson.Document;

public class MongoDirectExample {
    public static void main(String[] args) {
        try (MongoClient mongo = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongo.getDatabase("testdb");
            MongoCollection<Document> coll = db.getCollection("people");

            // Create
            coll.insertOne(new Document("name", "Bob").append("age", 30));

            // Read
            Document doc = coll.find(new Document("name", "Bob")).first();
            System.out.println(doc.toJson());

            // Update
            coll.updateOne(new Document("name", "Bob"),
                    new Document("$set", new Document("age", 31)));

            // Delete
            coll.deleteOne(new Document("name", "Bob"));
        }
    }
}
