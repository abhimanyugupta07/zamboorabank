package com.springapp.mvc.utilities;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author: abgupta
 * @Date: 1/3/14
 * @Time: 3:20 PM
 */
public class HibernateUtilities {
    private static SessionFactory sessionFactory;

    private static void configureSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    private HibernateUtilities() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            configureSessionFactory();
        }
        return sessionFactory;
    }
}
