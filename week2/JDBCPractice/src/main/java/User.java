import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;

    public User() {} // default constructor, mandatory for Hibernates plumbing
    public User(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    public List<Address> getAddresses() { return addresses; }
    public void addAddress(Address a) {
        addresses.add(a);
        a.setUser(this);
    }
    public void removeAddress(Address a) {
        addresses.remove(a);
        a.setUser(null);
    }
}