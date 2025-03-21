package com.averagesize.averagesize.entity;

import java.time.LocalDate;

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
    private long id_link;

    @Column(name = "url_original", nullable = false)
    private String url_original;

    @Column(name = "url_short", nullable = false)
    private String url_short;

    @Column(name = "create_at", nullable = false)
    private LocalDate create_at;

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
}
