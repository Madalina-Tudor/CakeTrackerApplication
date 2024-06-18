package org.datavid.cake_tracker.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "members", uniqueConstraints = @UniqueConstraint(columnNames = {"first_name", "last_name", "birth_date", "country", "city"}))
public class Member {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name", nullable = false)
        private String firstName;

        @Column(name = "last_name", nullable = false)
        private String lastName;

        @Column(name = "birth_date", nullable = false)
        private LocalDate birthDate;

        @Column(name = "country", nullable = false)
        private String country;

        @Column(name = "city", nullable = false)
        private String city;
}
