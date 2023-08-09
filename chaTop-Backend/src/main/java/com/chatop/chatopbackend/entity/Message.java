package com.chatop.chatopbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="messages")
public class Message extends DateTableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rental_id;

    private int user_id;

    @Column(length = 2000)
    private String message;

}
