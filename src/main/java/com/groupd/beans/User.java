package com.groupd.beans;

public class User {
    private String id;
    private String role;
    private String firstName;
    private String lastName;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(String id, String role, String firstName, String lastName) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Getter and Setter for id
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    // Getter and Setter for role
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    // Getter and Setter for firstName
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    // Getter and Setter for lastName
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
}
