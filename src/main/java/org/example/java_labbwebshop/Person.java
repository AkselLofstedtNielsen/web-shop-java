package org.example.java_labbwebshop;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;
    String name;
    String email;
    int age;

}
