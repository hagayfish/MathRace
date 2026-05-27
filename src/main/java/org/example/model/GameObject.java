package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "objects")
public class GameObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "object_name")
    private String objectName;

    public GameObject() {}

    public Long getId() { return id; }
    public String getObjectName() { return objectName; }
    public void setObjectName(String objectName) { this.objectName = objectName; }
}