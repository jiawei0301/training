import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.IndexOptions;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

@Entity("users")
public class UserDoc {
    @Id
    private ObjectId id;

    @Indexed(options = @IndexOptions(unique = true))
    private String email;
    private String name;

    public UserDoc() {}

    public UserDoc(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public ObjectId getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}