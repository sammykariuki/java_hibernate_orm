package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class Main {
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("-------");
        System.out.println("PostNet");
        System.out.println("-------");
        System.out.println("Welcome to Postnet");
        System.out.println("Create an account and get to upload blogs and view others blogs.");
        System.out.println("****************************************************************");
        System.out.println(" ------------------- ");
        System.out.print("| Name: ");
        AppUser u1 = new AppUser();
        u1.setName("Alexander");
        u1.setEmail("alex@email.com");
        u1.setPassword("1234");
        u1.setRole("user");
        System.out.println(u1.toString());

        Blog b1 = new Blog();
        b1.setTitle("Alexander 101");
        b1.setContent("How does a day in the life of alex looks like?" +
                "My Day starts at 5 AM where I ...");
        b1.setGenre("Biography");
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
        scanner.close();

    }
}
