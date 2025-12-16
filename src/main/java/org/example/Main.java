package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    static void main(String[] args) {
        System.out.println("Hello");
        AppUser u1 = new AppUser();
        u1.setUid("12w3-4y02");
        u1.setName("Joseph");
        u1.setEmail("jose@email.com");
        u1.setPassword("1234");
        u1.setRole("user");
        System.out.println(u1.toString());

        Blog b1 = new Blog();
        b1.setBid("37x2-2y02");
        b1.setTitle("Blog 1");
        b1.setContent("This is the first blog");
        b1.setGenre("Action");
        b1.setAuthor(u1);

        Configuration config = new Configuration();
        config.addAnnotatedClass(AppUser.class);
        config.addAnnotatedClass(Blog.class);
        config.configure();

        SessionFactory factory = config.buildSessionFactory();
        Session session = factory.openSession();

        //Use transaction only for insert,update,delete not read
        Transaction transaction = session.beginTransaction();

        //create
        session.persist(u1);
        session.persist(b1);
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
