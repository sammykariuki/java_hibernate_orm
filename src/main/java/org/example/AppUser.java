package org.example;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class AppUser {
    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID uid;
    private String name;
    @NaturalId
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private String role = "user";
    @OneToMany(mappedBy = "author")
    private List<Blog> blogs = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }
    public void addBlog(Blog blog) {
        blogs.add(blog);
        blog.setAuthor(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
