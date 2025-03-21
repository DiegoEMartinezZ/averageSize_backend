package com.averagesize.averagesize.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user", unique = true, nullable = false)
    private UUID id_user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_at", nullable = false)
    private LocalDate create_at;

    // When user change it will change links only for new links or change in users
    // (not remove)
    @OneToMany(mappedBy = "id_user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Link> links;
}
