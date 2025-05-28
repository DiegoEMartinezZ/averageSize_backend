package com.averagesize.averagesize.entity;

import java.time.LocalDateTime;
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
    private UUID idUser;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createAt;

    @Column(name = "active", nullable = false)
    private boolean active;

    // User that be able to use the application in alfa version
    @Column(name = "is_beta_tester", nullable = false)
    private boolean isBetaTester;

    @Column(name = "magic_link_token")
    private String magicLinkToken;

    @Column(name = "magic_link_expiry")
    private LocalDateTime magicLinkExpiry;

    // Last login tracking
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    // When user change it will change links only for new links or change in users
    // (not remove)
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Link> links;
}
