package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    static void main(String[] args) {
        System.out.println("Hello");
        Users u1 = new Users();
        u1.setUid("12w3-4y03");
        u1.setName("Mary");
        u1.setEmail("mary@email.com");
        u1.setPassword("1234");
        u1.setRole("user");
        System.out.println(u1.toString());

        Configuration config = new Configuration();
        config.addAnnotatedClass(Users.class);
        config.configure();

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        //Use transaction only for insert,update,delete not read
        Transaction transaction = session.beginTransaction();

        //create
        session.persist(u1);
        //read
        //session.find(Users.class, "12w3-4y02");
        //update
        //session.merge(u1);
        //delete
        //Users uToDelete = session.find(Users.class, "12w3-4y02");
        //session.remove(uToDelete);

        transaction.commit();

        session.close();
        factory.close();

    }
}
