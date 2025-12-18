package org.example;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static void main(String[] args) {
        Configuration config = new Configuration();
        config.addAnnotatedClass(AppUser.class);
        config.addAnnotatedClass(Blog.class);
        config.configure();
        SessionFactory factory = config.buildSessionFactory();

        String option;
        boolean exit = false;
        System.out.println("**********");
        System.out.println("PostNet📰");
        System.out.println("**********");
        System.out.println("Welcome to Post net, your Blog centre");
        System.out.println("-------------------------------------");

        while(!exit){
        System.out.println("1. Create an account and get to upload blogs and view others blogs.");
        System.out.println("2. Already have an account? Log in");
        System.out.print("3. Exit? (1-3): ");
        option = scanner.nextLine();
        switch (option) {
            case "1" -> createAccount(factory);
            case "2" -> login(factory);
            case "3" -> exit = true;
            default -> {
                System.out.println("--------------");
                System.out.println("Invalid Choice");
                System.out.println("--------------");
            }
        }
        }
        factory.close();
        scanner.close();

    }
    static void createAccount(SessionFactory factory) {
        String name = "";
        String email = "";
        String password = "";
        while(name.isEmpty()){
        System.out.print("Enter your name: ");
        name = scanner.nextLine();
        }
        while(email.isEmpty()){
            System.out.print("Enter your email: ");
            email = scanner.nextLine().toLowerCase();
            if(!email.contains("@") || !email.contains(".")){
                System.out.println("Invalid email address");
                email = "";
            }
        }
        while (password.isEmpty()) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
        }
        AppUser u1 = new AppUser();
        u1.setName(name);
        u1.setEmail(email);
        u1.setPassword(password);
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(u1);
            transaction.commit();
            System.out.println("Account created successfully");
        } catch (org.hibernate.HibernateException ex) { //here we are handling the exception in general
            transaction.rollback();

            if (ex instanceof org.hibernate.exception.ConstraintViolationException) { //here we specify so that if the exeption is email related we give detailed description
                System.out.println("Email already exists");
            } else {
                System.out.println("Unexpected database error"); //here we handle any other ensuring our program doesn't crash
                //ex.printStackTrace();
            }
        } finally {
        session.close();
        }
    }
    static void login(SessionFactory factory) {
        String email = "";
        String password = "";

        while (email.isEmpty()) {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().toLowerCase();

            if(!email.contains("@") || !email.contains(".")){
                System.out.println("--------------");
                System.out.println("Invalid email");
                System.out.println("--------------");
                email = "";
            }
        }
        while (password.isEmpty()) {
            System.out.print("Enter your password: ");
            password = scanner.nextLine();
        }
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            AppUser user = session.byNaturalId(AppUser.class).using("email", email).load();
            transaction.commit();
            if(user == null){
                System.out.println("--------------------");
                System.out.println("Invalid credentials");
                System.out.println("--------------------");
            } else {
                String userPassword = user.getPassword();
                if(password.equals(userPassword)){
                    System.out.println("----------------");
                    System.out.println("Login Successful");
                    System.out.println("----------------");
                } else {
                    System.out.println("--------------------");
                    System.out.println("Invalid credentials");
                    System.out.println("--------------------");
                }
            }

        } catch (org.hibernate.HibernateException ex) {
            transaction.rollback();
            System.out.println("An Error Occured");
            //ex.printStackTrace();

        } finally {
            session.close();
        }
    }
}
