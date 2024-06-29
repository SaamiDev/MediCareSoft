package com.microservice.user_management.Entities;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "roles", indexes = {
        @Index(name = "idx_role_name", columnList = "DES_NAME")
})
public class Role {

    private static final String PREFIX = "ROLE-";
    private static final int ID_LENGTH = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "COD_ROLE_ID", updatable = false, nullable = false)
    private String roleId;

    @Column(name = "DES_NAME", unique = true, nullable = false)
    private String name;

    @Column(name = "DATE_CREATED", nullable = false, updatable = false)
    private LocalDateTime dateCreated;


    public Role(String roleId,String name, LocalDateTime dateCreated) {
        this.roleId = roleId;
        this.name = name;
        this.dateCreated = dateCreated;
    }

    public Role() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String role_id) {
        this.roleId = roleId;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @PrePersist
    protected void onCreate() {
        this.roleId = PREFIX + generateRandomId(ID_LENGTH);
        //this.roleId = UUID.randomUUID().toString();
        this.dateCreated = LocalDateTime.now();
    }

    private String generateRandomId(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
