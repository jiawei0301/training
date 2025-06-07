package com.example.util;

import com.example.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // 1) Load settings from hibernate.cfg.xml on the classpath
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
        System.out.println("using config:" + registry.getClass().getName());
        // 2) Tell Hibernate exactly which annotated classes to include
        MetadataSources sources = new MetadataSources(registry)
                .addAnnotatedClass(User.class);

        // 3) Build the Metadata and the SessionFactory
        Metadata metadata = sources.getMetadataBuilder().build();

        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        sessionFactory.getMetamodel().getEntities().forEach(entity -> {
            System.out.println("mapped entity: " + entity.getName());
        });

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
