package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    static void main(String[] args) {
        System.out.println("Hello");
        Users u1 = new Users();
        u1.setUid("12w3-4y02");
        u1.setName("James");
        u1.setEmail("james@email.com");
        u1.setPassword("1234");
        u1.setRole("user");
        System.out.println(u1.toString());

        Configuration config = new Configuration();
        config.addAnnotatedClass(Users.class);
        config.configure();

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(u1);
        transaction.commit();
        session.close();
        factory.close();

    }
}
