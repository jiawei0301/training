package com.example.test;

import com.example.entity.User;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class HibernateTest {
    public static void main(String[] args) {
        // Obtain SessionFactory and open a session
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Hibernate Session opened successfully.");

            // Begin a transaction
            Transaction tx = session.beginTransaction();

            // Create and persist a test user
            User testUser = new User("TestName", "test@example.com");
            session.persist(testUser);

            // Commit the transaction
            tx.commit();
            System.out.println("Test user persisted with ID: " + testUser.getId());

            // Fetch the user back to verify
            User fetched = session.get(User.class, testUser.getId());
            if (fetched != null) {
                System.out.println("Fetched User: ID=" + fetched.getId()
                        + ", Name=" + fetched.getName()
                        + ", Email=" + fetched.getEmail());
            } else {
                System.err.println("Failed to fetch the persisted user.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Optionally shut down Hibernate
            HibernateUtil.shutdown();
            System.out.println("Hibernate SessionFactory shut down.");
        }
    }
}
