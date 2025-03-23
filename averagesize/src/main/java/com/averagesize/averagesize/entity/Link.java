package com.averagesize.averagesize.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "links")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_link", nullable = false)
    private long idLink;

    @Column(name = "url_original", nullable = false)
    private String urlOriginal;

    @Column(name = "url_short", nullable = false, unique = true)
    private String urlShort;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
