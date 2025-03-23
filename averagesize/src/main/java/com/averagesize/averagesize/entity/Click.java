package com.averagesize.averagesize.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clicks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_click", nullable = false)
    private long idClick;

    @ManyToOne
    @JoinColumn(name = "id_link", nullable = false)
    private Link link;

    @Column(name = "ip_user", nullable = false)
    private String ipUser;

    @Column(name = "navigator", nullable = false)
    private String navigator;

    @Column(name = "device", nullable = false)
    private String device;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "click_at", nullable = false)
    private LocalDateTime clickAt;
}
