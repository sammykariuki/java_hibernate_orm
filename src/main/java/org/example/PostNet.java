package org.example;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Scanner;

public class PostNet {
    static void PostMain(AppUser user, SessionFactory factory, Scanner scanner) {
        String name = user.getName();
        String option;
        boolean exit = false;
        System.out.println("%------------%");
        System.out.println("Welcome " + name);
        System.out.println("%------------%");
        while(!exit){
            System.out.println("1. View Blogs");
            System.out.println("2. Your Blogs");
            System.out.println("3. Create Blog");
            System.out.print("4. Log out / Exit (1-4): ");
            option = scanner.nextLine();
            switch (option){
                case "1" -> viewBlogs();
                case "2" -> yourBlogs(factory, scanner, user);
                case "3" -> createBlog(factory, scanner, user);
                case "4" -> {
                    exit = true;
                }
                default -> {
                    System.out.println("Invalid choice");
                    option = "";
                }
            }
        }


    }
    static void viewBlogs() {

    }
    static void yourBlogs(SessionFactory factory, Scanner scanner, AppUser user) {
        System.out.println("""
               ֍ YOUR BLOGS ֍
               """);
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {


            transaction.commit();


        } catch (org.hibernate.HibernateException ex) {
            transaction.rollback();
            System.out.println("Unexpected Database Error. Unable to fetch your blogs");
        }


    }
    static void createBlog(SessionFactory factory, Scanner scanner, AppUser user) {
        String title = "";
        String content = "";
        String genre = "";

        while (title.isEmpty()) {
            System.out.print("Title: ");
            title = scanner.nextLine().toUpperCase();
        }
        while (content.isEmpty()) {
            System.out.print("Content: ");
            content = scanner.nextLine();
        }
        while (genre.isEmpty()) {
            System.out.print("Genre: ");
            genre = scanner.nextLine();
        }
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setGenre(genre);
        blog.setAuthor(user);
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(blog);
            transaction.commit();
            System.out.println("Blog created successfully");
        } catch (org.hibernate.HibernateException ex) {
            transaction.rollback();
            System.out.println("%--------------------------------------%");
            System.out.println("Unexpected Database Error. Blog not created");
            System.out.println("%--------------------------------------%");
        }
    }
}
