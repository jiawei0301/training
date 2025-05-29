import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateSingleExample {
    public static void main(String[] args) {
        // Obtain a Session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Begin transaction
            Transaction tx = session.beginTransaction();

            // Create and persist
            User u = new User("bob@example.com", "Bob");
            session.persist(u);

            // Fetch by id
            User fetched = session.byId(User.class)
                    .load(u.getId());
            System.out.println("Fetched User: id=" + fetched.getId()
                    + ", email=" + fetched.getEmail()
                    + ", name=" + fetched.getName());

            // Commit and close
            tx.commit();
        }
    }
}