package com.metaway.petshop.models;

import com.metaway.petshop.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false, insertable = false, updatable =
            false)
    private User customer;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "breed_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    private Breed breed;

    @Column(name = "breed_id", nullable = false)
    private Integer breedId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "archive_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Archive archive;

    @Column(name = "archive_id")
    private Integer archiveId;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

}
