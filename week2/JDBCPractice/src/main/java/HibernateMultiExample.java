import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateMultiExample {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();

            // Create user and address
            User u = new User("carol@example.com", "Carol");
            Address addr = new Address(u, "123 Main St", "Springfield", "12345");
            u.addAddress(addr);

            session.persist(u);

            // Fetch and display
            User fetched = session.byId(User.class)
                    .load(u.getId());
            System.out.println("User " + fetched.getEmail() + " has addresses:");
            fetched.getAddresses().forEach(a ->
                    System.out.println(" - " + a.getStreet() + ", " + a.getCity())
            );

            tx.commit();
        }
    }
}