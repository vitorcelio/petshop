package com.metaway.petshop.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "archives")
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "base_64", columnDefinition = "TEXT", nullable = false)
    private String base64;

    @Column(name = "base_64_mini", columnDefinition = "TEXT")
    private String base64Mini;

}
