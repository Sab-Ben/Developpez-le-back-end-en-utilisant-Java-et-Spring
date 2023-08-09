package com.chatop.chatopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@Table(name="rentals")
public class Rental extends DateTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double surface;
    private double price;
    private String picture;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private Long owner_id;
}
