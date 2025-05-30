import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class MorphiaUtil {
    // static-block allows more complex initialization and error handling
    private static final Datastore ds;
    static {
        String uri = System.getenv().getOrDefault("MONGODB_URI", "mongodb://localhost:27017");
        MongoClient client = MongoClients.create(uri);
        ds = Morphia.createDatastore(client, "myappdb");
        ds.getMapper().getEntityModel(UserDoc.class);
    }

    public static Datastore getDatastore() {
        return ds;
    }
}
