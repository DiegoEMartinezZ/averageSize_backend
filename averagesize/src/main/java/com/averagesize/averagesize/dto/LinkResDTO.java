package com.averagesize.averagesize.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LinkResDTO {
    private Long idLink;
    private String urlOriginal;
    private String urlShort;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime expirationDate;
    private boolean active;
    private String userId;

}
