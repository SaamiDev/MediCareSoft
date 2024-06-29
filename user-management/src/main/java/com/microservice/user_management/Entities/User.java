package com.microservice.user_management.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_user_email", columnList = "DES_EMAIL"),
        @Index(name = "idx_user_dni", columnList = "COD_DNI")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COD_USER_ID", updatable = false, nullable = false)
    private String user_id;

    @Column(name = "DES_NAME")
    private String name;

    @Column(name = "DES_SURNAMES")
    private String surnames;

    @Column(name = "DES_EMAIL", unique = true)
    private String email;

    @Column(name = "DES_PASS", nullable = false)
    private String password;

    @Column(name = "COD_DNI", unique = true, nullable = false)
    private String dni;

    @Column(name = "DATE_CREATED", nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    @Column(name = "DATE_UPDATED")
    private LocalDateTime dateUpdated;

    /*@ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "COD_USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "COD_ROLE_ID")
    )*/

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    public User() {}
    @PrePersist
    protected void onCreate() {
        this.user_id = UUID.randomUUID().toString();
        this.dateCreated = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
